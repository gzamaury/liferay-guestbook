/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.docs.guestbook.jsf.portlet;

import com.liferay.bean.portlet.LiferayPortletConfiguration;

import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;
import javax.portlet.faces.GenericFacesPortlet;

/**
 * @author gzamaury
 */
@PortletConfiguration(portletName = "guestbook-jsf-portlet",
//	displayName = {
//		@LocaleString("guestbook-jsf-portlet")
//	},
	resourceBundle = "content.Language",
	initParams = {
		@InitParameter(
			name = "javax.portlet.faces.defaultViewId.view",
			value = "/WEB-INF/views/view.xhtml"
		),
//		@InitParameter(
//			name = "javax.portlet.faces.defaultViewId.edit",
//			value = "/WEB-INF/views/portletEditMode.xhtml"
//		),
//		@InitParameter(
//			name = "javax.portlet.faces.defaultViewId.help",
//			value = "/WEB-INF/views/portletHelpMode.xhtml"
//		),
	},
//	keywords = {
//		@LocaleString("guestbook-jsf-portlet")
//	},
	prefs = {
//		@Preference(
//			name = "datePattern",
//			values = "MM/dd/yyyy"
//		),
//		@Preference(
//			name = "recipientEmailAddress",
//			values = "humanresources@some-company-domain.com"
//		)
	},
	roleRefs = {
		@SecurityRoleRef(roleName = "administrator"),
		@SecurityRoleRef(roleName = "guest"),
		@SecurityRoleRef(roleName = "power-user"),
		@SecurityRoleRef(roleName = "user")
	},
//	shortTitle = {
//		@LocaleString("guestbook-jsf-portlet")
//	},
	supports = @Supports(portletModes = {
		"view"
//		,"edit"
//		,"help"
	})
//	,title = {
//		@LocaleString("guestbook-jsf-portlet")
//	}
)
@LiferayPortletConfiguration(
	portletName = "guestbook-jsf-portlet",
	properties = {
		"com.liferay.portlet.ajaxable=false",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.requires-namespaced-parameters=false"
	}
)
public class GuestbookJSFPortlet extends GenericFacesPortlet {
}
