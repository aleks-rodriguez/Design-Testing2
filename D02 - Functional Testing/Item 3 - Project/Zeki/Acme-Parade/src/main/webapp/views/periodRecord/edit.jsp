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

<form:form action="${requestURI}" modelAttribute="periodRecord">

<form:hidden path="id"/>
	
	<acme:textbox code="periodRecord.title" path="title" readonly="${view}"/>
	<acme:textarea code="periodRecord.description" path="description" readonly="${view}"/>
	<acme:date code="periodRecord.startDate" path="startDate" id="1" read="${view}"/>
	<acme:date code="periodRecord.endDate" path="endDate" id="2" read="${view}"/>
	<acme:textarea code="periodRecord.photos" path="photos" readonly="${view}"/>
	<jstl:if test="${not view}">
	<acme:submit name="save" code="periodRecord.save"/>
	</jstl:if>
</form:form>
<acme:cancel code="periodRecord.cancel" />