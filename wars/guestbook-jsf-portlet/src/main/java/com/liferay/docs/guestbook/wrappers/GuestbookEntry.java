package com.liferay.docs.guestbook.wrappers;

import com.liferay.docs.guestbook.model.GuestbookEntryWrapper;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;

public class GuestbookEntry extends GuestbookEntryWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5954021605453874378L;

	public GuestbookEntry(com.liferay.docs.guestbook.model.GuestbookEntry guestbookEntry) {
		super(guestbookEntry);
		// TODO Auto-generated constructor stub
	}

	public static final String GUESTBOOKENTRY_MODEL =
		"com.liferay.docs.guestbook.model.GuestbookEntry";

	private Boolean viewable;

	public Boolean getViewable() {

		if (viewable == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			viewable =
				LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker().hasPermission(
					scopeGroupId, GUESTBOOKENTRY_MODEL, getEntryId(), ActionKeys.VIEW);
		}

		return viewable;
	}

}
