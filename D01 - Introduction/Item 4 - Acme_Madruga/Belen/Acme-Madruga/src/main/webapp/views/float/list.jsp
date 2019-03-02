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

<p>
	<spring:message code="float.list" />
</p>

<display:table name="floats" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column titleKey="float.show">
		<a href="float/brotherhood/show.do?id=${row.id}"><spring:message
				code="float.show" /></a>
	</display:column>
	<display:column property="title" titleKey="float.title" />
	<display:column property="description" titleKey="float.description" />
	<display:column property="pictures" titleKey="float.pictures"></display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="float.edit">
		<a href="float/brotherhood/edit.do?id=${row.id}"><spring:message
				code="float.edit" /></a>
	</display:column>
	<display:column titleKey="float.delete">
		<a href="float/brotherhood/delete.do?id=${row.id}"><spring:message
				code="float.delete" /></a>
	</display:column>
	</security:authorize>
	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>