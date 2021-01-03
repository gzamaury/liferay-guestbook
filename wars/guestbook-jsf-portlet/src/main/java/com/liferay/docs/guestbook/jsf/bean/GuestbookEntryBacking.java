package com.liferay.docs.guestbook.jsf.bean;

import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletRequest;

import org.osgi.service.cdi.annotations.Reference;

@Named
@RequestScoped
public class GuestbookEntryBacking extends AbstractBacking {

	public static final String GUESTBOOKENTRY_MODEL =
		"com.liferay.docs.guestbook.model.GuestbookEntry";

	private Boolean hasAddPermission;

	public Boolean getHasAddPermission() {

		if (hasAddPermission == null) {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			hasAddPermission = LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker()
				.hasPermission(scopeGroupId, GuestbookBacking.GUESTBOOK_MODEL,
					guestbookBacking.getSelectedGuestbook().getGuestbookId(), "ADD_ENTRY");
		}

		return hasAddPermission;
	}

	public Boolean isGuestBookEntryViewable(Long guestbookEntryId) {

		long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
		Boolean guestBookEntryViewable =
			LiferayPortletHelperUtil.getThemeDisplay().getPermissionChecker()
				.hasPermission(scopeGroupId, GUESTBOOKENTRY_MODEL, guestbookEntryId, "VIEW");

		return guestBookEntryViewable;
	}

	public void setHasAddPermission(Boolean hasAddPermission) {
		this.hasAddPermission = hasAddPermission;
	}

	public void add() {
		GuestbookEntry entry = guestbookEntryLS.createGuestbookEntry(0L);

		entry.setGroupId(LiferayPortletHelperUtil.getScopeGroupId());
		entry.setGuestbookId(guestbookBacking.getSelectedGuestbook().getGuestbookId());
		guestbookBacking.setSelectedEntry(entry);
		guestbookBacking.editEntry();
	}

	public void cancel() {
		guestbookBacking.select(guestbookBacking.getSelectedGuestbook());
	}

	public void save() {

		GuestbookEntry entry = guestbookBacking.getSelectedEntry();

		entry.setCompanyId(LiferayPortletHelperUtil.getCompanyId());
		entry.setUserId(LiferayPortletHelperUtil.getUserId());

		Long userId = LiferayPortletHelperUtil.getUserId();
		PortletRequest pr =
			(PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		try {

			ServiceContext serviceContext =
				ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), pr);

			if (entry.getEntryId() == 0) {

				guestbookEntryLS.addGuestbookEntry(userId, entry.getGuestbookId(),
					entry.getName(), entry.getEmail(), entry.getMessage(), serviceContext);
			} else {
				guestbookEntryLS.updateGuestbookEntry(userId, entry.getGuestbookId(),
					entry.getEntryId(), entry.getName(), entry.getEmail(), entry.getMessage(),
					serviceContext);
			}

			addGlobalSuccessInfoMessage();
		} catch (Exception e) {
			addGlobalUnexpectedErrorMessage();
			logger.error(e);
		}

		guestbookBacking.select(guestbookBacking.getSelectedGuestbook());
	}

	@Inject
	@Reference
	transient GuestbookEntryLocalService guestbookEntryLS;

	@Inject
	protected GuestbookBacking guestbookBacking;

	public GuestbookBacking getGuestbookBacking() {
		return guestbookBacking;
	}
}
