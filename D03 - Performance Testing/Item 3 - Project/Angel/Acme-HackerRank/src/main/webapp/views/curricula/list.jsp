<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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

<display:table name="curriculas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="curricula.show">
		<a href="curricula/hacker/show.do?id=${row.id}"><spring:message
				code="curricula.show" /></a>
	</display:column>
	<display:column titleKey="curricula.fullName">
		<jstl:out value="${row.fullName}" />
	</display:column>
	<display:column titleKey="curricula.statement">

		<jstl:out value="${row.statement}" />
	</display:column>
	<display:column titleKey="curricula.github">
		<jstl:out value="${row.githubProfile}" />
	</display:column>
	<display:column titleKey="curricula.linkedin">
		<jstl:out value="${row.linkedInProfile}" />
	</display:column>
	<display:column titleKey="curricula.phone">
		<jstl:out value="${row.phoneNumber}" />
	</display:column>
	<display:column titleKey="curricula.edit">
		<a href="curricula/hacker/edit.do?id=${row.id}"><spring:message
				code="curricula.edit" /></a>
	</display:column>

</display:table>
<security:authorize access="hasRole('HACKER')">
	<a href="curricula/hacker/create.do"><spring:message
			code="curricula.create" /></a>
</security:authorize>