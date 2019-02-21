<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="profile.title.1" />
</p>

<display:table name="profiles" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="nick" titleKey="profile.nick" />
	<display:column property="socialNetworkName"
		titleKey="profile.socialNetworkName" />
	<display:column property="link" titleKey="profile.link" />
	<display:column titleKey="profile.delete">
		<a href="profile/delete.do?id=${row.id}"><spring:message
				code="profile.delete" /></a>
	</display:column>

	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>

<a href="profile/create.do"><spring:message code="profile.add" /></a>
