<%@include file="../init.jsp"%>

<%
GuestbookSearchDisplayContext guestbookSearchDisplayContext = (GuestbookSearchDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>
<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" 
	value="/guestbook/view_search.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewURL">
	<portlet:param 
		name="mvcPath" 
		value="/guestbook/view.jsp" 
	/>
</portlet:renderURL>

<aui:form action="${searchURL}" name="fm">

	<liferay-ui:header backURL="${viewURL}" title="back" />

	<div class="row">
		<div class="col-md-8">
			<aui:input inlineLabel="left" label="" name="keywords" placeholder="search-entries" size="256" />
		</div>

		<div class="col-md-4">
			<aui:button type="submit" value="search" />
		</div>
	</div>
</aui:form>

<liferay-ui:search-container delta="10" 
	emptyResultsMessage="no-entries-were-found" 
	total="<%= guestbookSearchDisplayContext.getEntriesCount() %>">
		<liferay-ui:search-container-results
				results="<%= guestbookSearchDisplayContext.getEntries() %>"
/>

	<liferay-ui:search-container-row
			className="com.liferay.docs.guestbook.model.GuestbookEntry"
			keyProperty="entryId" modelVar="entry" escapedModel="<%=true%>">

        <liferay-ui:search-container-column-text name="guestbook"
            value="<%= guestbookSearchDisplayContext.getGuestbookName() %>" />

        <liferay-ui:search-container-column-text property="message" />

        <liferay-ui:search-container-column-text property="name" />
                
        <liferay-ui:search-container-column-jsp
            path="/guestbook/entry_actions.jsp"
            align="right" />
   </liferay-ui:search-container-row>
   
   <liferay-ui:search-iterator />

</liferay-ui:search-container>

<%
guestbookSearchDisplayContext.getLog();
%>