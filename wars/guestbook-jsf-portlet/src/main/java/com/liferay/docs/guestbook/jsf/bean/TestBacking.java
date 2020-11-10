package com.liferay.docs.guestbook.jsf.bean;

import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.osgi.service.cdi.annotations.Reference;

@Named
@ViewScoped
public class TestBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@Inject
	@Reference
	transient private GuestbookLocalService guestbookLS;

	@Inject
	@Reference
	transient private UserLocalService userLS;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void submit(ActionEvent actionEvent) {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

	public String getUsersCount() {
		return String.valueOf(userLS.getUsersCount());
	}

	public String getGuestbooksCount() {
		return String.valueOf(guestbookLS.getGuestbooksCount());
	}

}
