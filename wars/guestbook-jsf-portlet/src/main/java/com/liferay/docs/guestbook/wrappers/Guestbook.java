package com.liferay.docs.guestbook.wrappers;

import com.liferay.docs.guestbook.model.GuestbookWrapper;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;

public class Guestbook extends GuestbookWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709856504813975602L;

	public Guestbook(com.liferay.docs.guestbook.model.Guestbook guestbook) {
		super(guestbook);
		// TODO Auto-generated constructor stub
	}

	private Boolean deleteable;
	private Boolean permissible;
	private Boolean updateable;

	public Boolean getDeleteable() {

		if (deleteable == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			deleteable =
				LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker().hasPermission(
					scopeGroupId, GUESTBOOK_MODEL, getGuestbookId(), ActionKeys.DELETE);
		}

		return deleteable;
	}

	public Boolean getPermissible() {

		if (permissible == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			permissible =
				LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker().hasPermission(
					scopeGroupId, GUESTBOOK_MODEL, getGuestbookId(), ActionKeys.PERMISSIONS);
		}

		return permissible;
	}

	public Boolean getUpdateable() {

		if (updateable == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			updateable =
				LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker().hasPermission(
					scopeGroupId, GUESTBOOK_MODEL, getGuestbookId(), ActionKeys.UPDATE);
		}

		return updateable;
	}

	public static final String GUESTBOOK_MODEL = "com.liferay.docs.guestbook.model.Guestbook";

	private Boolean viewable;

	public Boolean getViewable() {

		if (viewable == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			viewable = LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker()
				.hasPermission(scopeGroupId, GUESTBOOK_MODEL, getGuestbookId(), ActionKeys.VIEW);
		}

		return viewable;
	}

}
