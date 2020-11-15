package com.liferay.docs.guestbook.jsf.bean;

import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.osgi.service.cdi.annotations.Reference;

@Named
@RequestScoped
public class GuestbookEntryBacking extends AbstractBacking {

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

		try {

			if (entry.getEntryId() == 0) {
				guestbookEntryLS.addGuestbookEntry(entry);
			} else {
				guestbookEntryLS.updateGuestbookEntry(entry);
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
