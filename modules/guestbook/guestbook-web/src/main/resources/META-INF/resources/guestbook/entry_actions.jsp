<%@include file="../init.jsp"%>

<%
	String mvcPath = ParamUtil.getString(request, "mvcPath");

	ResultRow row =
		(ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

	GuestbookEntry entry = (GuestbookEntry) row.getObject();
	
	String currPage = (String) row.getParameter("currPage");
	_log.debug("currPage: " + currPage);
%>

<liferay-ui:icon-menu>

	<portlet:renderURL var="viewEntryURL">
		<portlet:param name="entryId"
			value="<%=String.valueOf(entry.getEntryId())%>" />
		<portlet:param name="mvcPath" value="/guestbook/view_entry.jsp" />
		<portlet:param name="currPage" value="<%=currPage%>" />
	</portlet:renderURL>

	<liferay-ui:icon message="View" url="<%=viewEntryURL.toString()%>"
		image="view" />

	<c:if
		test="<%=GuestbookEntryPermission.contains(permissionChecker,
						entry.getEntryId(), ActionKeys.UPDATE)%>">
		<portlet:renderURL var="editURL">
			<portlet:param name="entryId"
				value="<%=String.valueOf(entry.getEntryId())%>" />
			<portlet:param name="mvcPath" value="/guestbook/edit_entry.jsp" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" message="Edit"
			url="<%=editURL.toString()%>" />
	</c:if>

	<c:if
		test="<%=GuestbookEntryPermission.contains(permissionChecker,
						entry.getEntryId(), ActionKeys.PERMISSIONS)%>">

		<liferay-security:permissionsURL
			modelResource="<%=GuestbookEntry.class.getName()%>"
			modelResourceDescription="<%=entry.getMessage()%>"
			resourcePrimKey="<%=String.valueOf(entry.getEntryId())%>"
			var="permissionsURL"
			windowState="<%=LiferayWindowState.POP_UP.toString()%>" />

		<liferay-ui:icon image="permissions" url="<%=permissionsURL%>"
			useDialog="true" />

	</c:if>

	<c:if
		test="<%=GuestbookEntryPermission.contains(permissionChecker,
						entry.getEntryId(), ActionKeys.DELETE)%>">

		<portlet:actionURL name="deleteEntry" var="deleteURL">
			<portlet:param name="entryId"
				value="<%=String.valueOf(entry.getEntryId())%>" />
			<portlet:param name="guestbookId"
				value="<%=String.valueOf(entry.getGuestbookId())%>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete image="delete" url="<%=deleteURL.toString()%>" />
	</c:if>

</liferay-ui:icon-menu>

<%!private static Log _log = LogFactoryUtil.getLog("html.guestbook.entry_actions_jsp");%>