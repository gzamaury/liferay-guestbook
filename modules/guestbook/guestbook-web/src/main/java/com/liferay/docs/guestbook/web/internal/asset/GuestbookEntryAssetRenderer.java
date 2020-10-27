package com.liferay.docs.guestbook.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.docs.guestbook.constants.GuestbookPortletKeys;
import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestbookEntryAssetRenderer extends BaseJSPAssetRenderer<GuestbookEntry> {

	public GuestbookEntryAssetRenderer(GuestbookEntry entry,
		ModelResourcePermission<GuestbookEntry> modelResourcePermission) {

		_entry = entry;
		_guestbookEntryModelResourcePermission = modelResourcePermission;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return _guestbookEntryModelResourcePermission.contains(permissionChecker, _entry,
				ActionKeys.VIEW);
		} catch (Exception e) {
		}

		return true;
	}

	@Override
	public GuestbookEntry getAssetObject() {
		return _entry;
	}

	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public long getUserId() {

		return _entry.getUserId();
	}

	@Override
	public String getUserName() {
		return _entry.getUserName();
	}

	@Override
	public String getUuid() {
		return _entry.getUuid();
	}

	@Override
	public String getClassName() {
		return GuestbookEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _entry.getEntryId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Name: " + _entry.getName() + ". Message: " + _entry.getMessage();
	}

	@Override
	public String getTitle(Locale locale) {
		return _entry.getMessage();
	}

	@Override
	public boolean include(HttpServletRequest request, HttpServletResponse response,
		String template) throws Exception {
		request.setAttribute("ENTRY", _entry);
		request.setAttribute("HtmlUtil", HtmlUtil.getHtml());
		request.setAttribute("StringUtil", new StringUtil());
		return super.include(request, response, template);
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("gb_entry", _entry);

			return "/asset/entry/" + template + ".jsp";
		} else {
			return null;
		}
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), GuestbookPortletKeys.GUESTBOOK,
			PortletRequest.RENDER_PHASE);
		portletURL.setParameter("mvcPath", "/guestbook/edit_entry.jsp");
		portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));
		portletURL.setParameter("showback", Boolean.FALSE.toString());

		return portletURL;
	}

	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect)
		throws Exception {
		try {
			long plid = PortalUtil.getPlidFromPortletId(_entry.getGroupId(),
				GuestbookPortletKeys.GUESTBOOK);

			PortletURL portletURL;
			if (plid == LayoutConstants.DEFAULT_PLID) {
				portletURL = liferayPortletResponse.createLiferayPortletURL(
					getControlPanelPlid(liferayPortletRequest), GuestbookPortletKeys.GUESTBOOK,
					PortletRequest.RENDER_PHASE);
			} else {
				portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,
					GuestbookPortletKeys.GUESTBOOK, plid, PortletRequest.RENDER_PHASE);
			}

			portletURL.setParameter("mvcPath", "/guestbook/view_entry.jsp");
			portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));

			String currentUrl = PortalUtil.getCurrentURL(liferayPortletRequest);

			portletURL.setParameter("redirect", currentUrl);

			return portletURL.toString();

		} catch (PortalException e) {

		} catch (SystemException e) {
		}

		return noSuchEntryRedirect;
	}

	@Override
	public String getURLView(LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) throws Exception {

		return super.getURLView(liferayPortletResponse, windowState);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final ModelResourcePermission<GuestbookEntry> _guestbookEntryModelResourcePermission;
	private GuestbookEntry _entry;
}
