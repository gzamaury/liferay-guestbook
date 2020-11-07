package com.liferay.docs.test.jsf.bean;

import com.liferay.faces.util.context.FacesContextHelperUtil;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@Named
@RequestScoped
public class TestBacking {

//	@Inject
//	protected GuestbookBacking guestbookBacking;

	private String name;

	// @Inject
	// @Reference
	// private GuestbookLocalService guestbookLS;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void submit(ActionEvent actionEvent) {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

}
