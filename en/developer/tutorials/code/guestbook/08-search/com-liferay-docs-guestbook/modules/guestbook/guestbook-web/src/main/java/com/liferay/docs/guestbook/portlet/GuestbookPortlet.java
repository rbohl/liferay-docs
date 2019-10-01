package com.liferay.docs.guestbook.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.docs.guestbook.constants.GuestbookPortletKeys;
import com.liferay.docs.guestbook.display.context.GuestbookSearchDisplayContext;
import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author sezovr
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.scopeable=true", "javax.portlet.name=" + GuestbookPortletKeys.GUESTBOOK,
		"javax.portlet.display-name=Guestbook", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/", "javax.portlet.init-param.view-template=/guestbook/view.jsp",
		"javax.portlet.resource-bundle=content.Language", "javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html" }, service = Portlet.class)
public class GuestbookPortlet extends MVCPortlet {

	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);

		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		long entryId = ParamUtil.getLong(request, "entryId");

		if (entryId > 0) {

			try {

				_guestbookEntryLocalService.updateGuestbookEntry(serviceContext.getUserId(), guestbookId, entryId,
						userName, email, message, serviceContext);

				response.setRenderParameter("guestbookId", Long.toString(guestbookId));

			} catch (Exception e) {
				System.out.println(e);

				PortalUtil.copyRequestParameters(request, response);

				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}

		} else {

			try {
				_guestbookEntryLocalService.addGuestbookEntry(serviceContext.getUserId(), guestbookId, userName, email,
						message, serviceContext);

				response.setRenderParameter("guestbookId", Long.toString(guestbookId));

				SessionMessages.add(request, "entryAdded");

			} catch (Exception e) {
				SessionErrors.add(request, e.getClass().getName());

				PortalUtil.copyRequestParameters(request, response);

				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}
		}

	}

	public void deleteEntry(ActionRequest request, ActionResponse response) throws PortalException {
		long entryId = ParamUtil.getLong(request, "entryId");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);

		try {

			response.setRenderParameter("guestbookId", Long.toString(guestbookId));

			_guestbookEntryLocalService.deleteGuestbookEntry(entryId);

			SessionMessages.add(request, "entryDeleted");

		}

		catch (Exception e) {
			Logger.getLogger(GuestbookPortlet.class.getName()).log(Level.SEVERE, null, e);

			SessionErrors.add(request, e.getClass().getName());
		}
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), renderRequest);

			String keywords = ParamUtil.getString(renderRequest, "keywords");

			long groupId = serviceContext.getScopeGroupId();

			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");

			List<Guestbook> guestbooks = _guestbookLocalService.getGuestbooks(groupId);

			if (guestbooks.isEmpty()) {
				Guestbook guestbook = _guestbookLocalService.addGuestbook(serviceContext.getUserId(), "Main",
						serviceContext);

				guestbookId = guestbook.getGuestbookId();
			}

			if (guestbookId == 0) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}

			renderRequest.setAttribute("guestbookId", guestbookId);
			renderRequest.setAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT,
					buildSearchDisplayContext(keywords, groupId, renderRequest));
		} catch (Exception e) {
			throw new PortletException(e);
		}

		super.render(renderRequest, renderResponse);
	}

	public GuestbookSearchDisplayContext buildSearchDisplayContext(String keywords, long groupId,
			RenderRequest renderRequest) throws SearchException {

		GuestbookSearchDisplayContext guestbookSearchDisplayContext = new GuestbookSearchDisplayContext();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(renderRequest);
		SearchContext searchContext = SearchContextFactory.getInstance(httpServletRequest);

		searchContext.setKeywords(keywords);
		searchContext.setAttribute("paginationType", "more");
		searchContext.setStart(0);
		searchContext.setEnd(10);

		Indexer<GuestbookEntry> indexer = IndexerRegistryUtil.getIndexer(GuestbookEntry.class);

		Hits hits = indexer.search(searchContext);

		List<GuestbookEntry> entries = new ArrayList<GuestbookEntry>();
		
		guestbookSearchDisplayContext.setEntries(entries);

		for (int i = 0; i < hits.getDocs().length; i++) {
			Document doc = hits.doc(i);

			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			GuestbookEntry entry = null;

			try {
				entry = _guestbookEntryLocalService.getGuestbookEntry(entryId);
			} catch (PortalException pe) {
				_log.error(pe.getLocalizedMessage());
			} catch (SystemException se) {
				_log.error(se.getLocalizedMessage());
			}

			entries.add(entry);
		}

		guestbookSearchDisplayContext.setEntries(entries);
		guestbookSearchDisplayContext.setEntriesCount(entries.size());

		List<Guestbook> guestbooks = _guestbookLocalService.getGuestbooks(groupId);

		for (Guestbook guestbook : guestbooks) {
			
            guestbookSearchDisplayContext.setGuestbookName(guestbook.getName());
		}
		

		return guestbookSearchDisplayContext;

	}

	private static Log _log = LogFactoryUtil.getLog("html.guestbook.view_search_jsp");

	@Reference
	private Portal _portal;

	@Reference
	private GuestbookEntryLocalService _guestbookEntryLocalService;

	@Reference
	private GuestbookLocalService _guestbookLocalService;
}