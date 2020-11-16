package com.liferay.docs.guestbook.jsf.bean;

import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletRequest;

import org.osgi.service.cdi.annotations.Reference;

@Named
@ViewScoped
public class GuestbookBacking extends AbstractBacking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3176305616326164198L;

	public static final String DEFAULT_GUESTBOOK_NAME = "Main";

	private Guestbook originalGuestbook;

	private Guestbook selectedGuestbook;
	private List<Guestbook> guestbooks;

	private GuestbookEntry selectedEntry;
	private List<GuestbookEntry> entries;

	private boolean editingGuestbook;
	private boolean editingEntry;

	public void add() {
		setOriginalGuestbook(getSelectedGuestbook());

		Guestbook guestbook = guestbookLS.createGuestbook(0L);

		guestbook.setGroupId(LiferayPortletHelperUtil.getScopeGroupId());
		setSelectedGuestbook(guestbook);
		editGuestbook();

	}

	public void cancel() {
		logger.info("Cancel Button");
		select(getOriginalGuestbook());
	}

	public void save() {
		Guestbook guestbook = getSelectedGuestbook();

		guestbook.setCompanyId(LiferayPortletHelperUtil.getCompanyId());
		guestbook.setUserId(LiferayPortletHelperUtil.getUserId());

		Long userId = LiferayPortletHelperUtil.getUserId();
		PortletRequest pr =
			(PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		try {
			ServiceContext serviceContext =
				ServiceContextFactory.getInstance(Guestbook.class.getName(), pr);

			if (guestbook.getGuestbookId() == 0) {
				guestbook =
					guestbookLS.addGuestbook(userId, guestbook.getName(), serviceContext);

			} else {
				guestbook = guestbookLS.updateGuestbook(guestbook);
			}

			addGlobalSuccessInfoMessage();
		} catch (Exception e) {
			addGlobalUnexpectedErrorMessage();
			logger.error(e);
		}

		// Go back to master view
		select(guestbook);
	}

	public void select(Guestbook guestbook) {

		if (guestbook == null) {
			setSelectedGuestbook(null);
		} else {
			setSelectedGuestbook(guestbook);
		}

		// Force Guestbooks and Entries to reload
		setGuestbooks(null);
		setEntries(null);

		editingEntry = false;
		editingGuestbook = false;
	}

	public void setEditingEntry(boolean editingEntry) {
		this.editingEntry = editingEntry;
	}

	public void setEditingGuestbook(boolean editingGuestbook) {
		this.editingGuestbook = editingGuestbook;
	}

	public void createMainGuestbook() {

		try {

			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			List<Guestbook> guestbooks = guestbookLS.getGuestbooks(scopeGroupId);

			Guestbook defaultGuestbook = guestbooks.isEmpty() ? null : guestbooks.get(0);

			// Create the default guestbook if it does not exist in the database
			if (defaultGuestbook == null) {
				logger.info("postConstruct: creating a default guestbook named "
					+ DEFAULT_GUESTBOOK_NAME + " ...");

				Guestbook guestbook = guestbookLS.createGuestbook(0L);
				guestbook.setName(DEFAULT_GUESTBOOK_NAME);
				guestbook.setGroupId(scopeGroupId);
				guestbook.setCompanyId(LiferayPortletHelperUtil.getCompanyId());
				guestbook.setUserId(LiferayPortletHelperUtil.getUserId());
				guestbookLS.addGuestbook(guestbook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editEntry() {
		editingEntry = true;
		editingGuestbook = false;
	}

	public void editGuestbook() {
		editingEntry = false;
		editingGuestbook = true;
	}

	public List<GuestbookEntry> getEntries() {

		if (entries == null) {
			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();
			Guestbook selectedGuestbook = getSelectedGuestbook();

			try {
				entries = new ArrayList<GuestbookEntry>();

				if (selectedGuestbook == null) {
					logger.info("getEntries: selectedGuestbook == null ... ");
				} else {
					List<GuestbookEntry> list = guestbookEntryLS
						.getGuestbookEntries(scopeGroupId, selectedGuestbook.getGuestbookId());

					for (GuestbookEntry entry : list) {
						entries.add(entry);
					}
				}

			} catch (SystemException e) {
				logger.error(e);
			}
		}

		return entries;
	}

	public void setEntries(List<GuestbookEntry> entries) {
		this.entries = entries;
	}

	public List<Guestbook> getGuestbooks() {

		if (guestbooks == null) {
			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();

			try {
				guestbooks = new ArrayList<Guestbook>();

				List<Guestbook> list = guestbookLS.getGuestbooks(scopeGroupId);

				for (Guestbook guestbook : list) {
					guestbooks.add(guestbook);
				}
			} catch (SystemException e) {
				logger.error(e);
			}
		}

		logger.info("getGuestbooks: guestbooks.size() = " + guestbooks.size());

		return guestbooks;
	}

	public void setGuestbooks(List<Guestbook> guestbooks) {
		this.guestbooks = guestbooks;
	}

	public Guestbook getOriginalGuestbook() {
		return originalGuestbook;
	}

	public void setOriginalGuestbook(Guestbook originalGuestbook) {
		this.originalGuestbook = originalGuestbook;
	}

	public Guestbook getSelectedGuestbook() {

		if (selectedGuestbook == null) {
			long scopeGroupId = LiferayPortletHelperUtil.getScopeGroupId();

			try {

				List<Guestbook> guestbooks = guestbookLS.getGuestbooks(scopeGroupId);

				Guestbook firstGuestbookByName = guestbooks.isEmpty() ? null : guestbooks.get(0);

				if (firstGuestbookByName == null) {
					logger.info(
						"getSelectedGuestbook: No Guestbook named " + DEFAULT_GUESTBOOK_NAME);
				} else {
					selectedGuestbook = firstGuestbookByName;
				}
			} catch (SystemException e) {
				logger.error(e);
			}
		}

		return selectedGuestbook;
	}

	public GuestbookEntry getSelectedEntry() {
		return selectedEntry;
	}

	public void setSelectedEntry(GuestbookEntry selectedEntry) {
		this.selectedEntry = selectedEntry;
	}

	public void setSelectedGuestbook(Guestbook selectedGuestbook) {
		this.selectedGuestbook = selectedGuestbook;
	}

	public boolean isEditingEntry() {
		return editingEntry;
	}

	public boolean isEditingGuestbook() {
		return editingGuestbook;
	}

	@PostConstruct
	public void postConstruct() {
		createMainGuestbook();
	}

	@Inject
	@Reference
	transient GuestbookEntryLocalService guestbookEntryLS;

	@Inject
	@Reference
	transient GuestbookLocalService guestbookLS;
}
