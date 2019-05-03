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

<display:table name="companies" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="company.name">
		<jstl:out value="${row.name}" />
	</display:column>

	<display:column titleKey="company.surname">
		<jstl:out value="${row.surname}" />
	</display:column>

	<display:column titleKey="company.phone">
		<a href="tel:${row.phone}"><jstl:out value="${row.phone}" /></a>
	</display:column>

	<display:column titleKey="company.email">
		<a href="mailto:${row.email}"><jstl:out value="${row.email}" /></a>
	</display:column>

	<display:column titleKey="company.commercialName">
		<jstl:out value="${row.commercialName}" />
	</display:column>

	<display:column titleKey="company.vat">
		<jstl:out value="${row.vat}" />
	</display:column>

	<display:column titleKey="company.adress">
		<jstl:out value="${row.adress}" />
	</display:column>

	<display:column titleKey="company.positions">
		<a href="position/list.do?company=${row.id}"><spring:message
				code="company.positions" /></a>
	</display:column>

</display:table>
