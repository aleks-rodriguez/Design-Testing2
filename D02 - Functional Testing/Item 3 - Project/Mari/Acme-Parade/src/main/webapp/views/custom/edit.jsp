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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="customisation/administrator/edit.do"
	modelAttribute="customisationSystem">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="custom.system" path="systemName" />
	<acme:textbox code="custom.banner" path="banner" />
	<acme:textarea code="custom.message" path="message" />
	<acme:textbox code="custom.prefix" path="phonePrefix" />
	<acme:select_range end="24" begin="1" code="custom.hoursFinder"
		path="hoursFinder" />
	<acme:select_range end="100" begin="10" code="custom.resultFinder"
		path="resultFinder" />

	<acme:textarea code="custom.goodWords" path="goodWords" />
	<acme:textarea code="custom.badWords" path="badWords" />
	<acme:textarea code="custom.spamWords" path="spamWords" />
	<acme:textarea code="custom.priorities" path="priorities" />
	<acme:textarea code="custom.creditCardMakes" path="creditCardMakes" />
	<acme:textarea code="custom.vat" path="vat" />
	<acme:textarea code="custom.flatFare" path="flatFare" />

	<h3>
		<spring:message code="custom.deactivate.sponsorships"></spring:message>
	</h3>
	<a href="customisation/administrator/deactivateSponsorships.do"> <spring:message
			code="custom.deactivate"></spring:message></a>
	<br>
	<acme:submit name="save" code="custom.save" />
</form:form>

<acme:cancel code="custom.cancel" />