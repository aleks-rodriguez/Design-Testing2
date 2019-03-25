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

<form:form action="${requestURI}" modelAttribute="enrolment">
<form:hidden path="id"/>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:message code="enrolment.member"></spring:message>
  	<jstl:out value="${enrolment.member.name} ${enrolment.member.surname}"></jstl:out>
	<acme:select items="${position}" itemLabel="name" code="enrolment.position" path="position"/>

	<acme:submit name="save" code="enrolment.save"/>
	</security:authorize>
</form:form>
<acme:cancel code="enrolment.cancel" />>