package com.liferay.docs.guestbook.jsf.bean;

import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

public abstract class AbstractBacking {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractBacking.class);

	protected static final String UNEXPECTED_ERROR_MSG_ID = "an-unexpected-error-occurred";
	protected static final String SUCCESS_INFO_MSG_ID = "your-request-processed-successfully";

	public void addGlobalSuccessInfoMessage() {

		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

	public void addGlobalUnexpectedErrorMessage() {

		FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
	}
}
