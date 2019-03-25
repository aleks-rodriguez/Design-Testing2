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

<form:form action="${requestURI}" modelAttribute="legalRecord">

<form:hidden path="id"/>
	
	<acme:textbox code="legalRecord.title" path="title" readonly="${view}"/>
	<acme:textarea code="legalRecord.description" path="description" readonly="${view}"/>
	<acme:textbox code="legalRecord.legalName" path="legalName" readonly="${view}"/>
	<acme:textbox code="legalRecord.vat" path="vat" readonly="${view}"/>
	<acme:textarea code="legalRecord.laws" path="laws" readonly="${view}"/>
	<jstl:if test="${not view}">
	<acme:submit name="save" code="legalRecord.save"/>
	</jstl:if>
</form:form>
<input type="submit" onclick="window.history.back()" value="<spring:message code="legalRecord.cancel"/>"/>