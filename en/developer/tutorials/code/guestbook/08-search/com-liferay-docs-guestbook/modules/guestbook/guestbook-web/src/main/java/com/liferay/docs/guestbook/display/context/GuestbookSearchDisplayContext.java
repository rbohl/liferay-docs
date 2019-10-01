package com.liferay.docs.guestbook.display.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.portal.kernel.log.Log;

public class GuestbookSearchDisplayContext implements Serializable {

	public int getEntriesCount() {
		
		return _guestbookEntriesCount;
		
	}
	
	public void setEntriesCount(int guestbookEntriesCount) {
		_guestbookEntriesCount = guestbookEntriesCount;
	}
	
	public List<GuestbookEntry> getEntries() {
		
		return _guestbookEntries;
		
	}
	
	public void setEntries(List<GuestbookEntry> guestbookEntries) {
		_guestbookEntries = guestbookEntries;
	}
	
	public String getGuestbookName() {
		return _guestbookName;
	}

	public void setGuestbookName(String guestbookName) {

		_guestbookName = guestbookName;

	}
	
	public Log getLog() {
		return _log;
	}
	
	public void setLog(Log log) {
		_log= log;
	}

private int _guestbookEntriesCount;
private List<GuestbookEntry> _guestbookEntries;
private String _guestbookName;
private Log _log;

}