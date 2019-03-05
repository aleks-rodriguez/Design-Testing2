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


<display:table name="enrolments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column property="member.name" titleKey="enrolment.member"/>
	<display:column property="moment" titleKey="enrolment.moment" />

	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="enrolment.edit">
		<a href="enrolment/brotherhood/update.do?idEnrolment=${row.id}"><spring:message
				code="enrolment.edit" /></a>
	</display:column>
	</security:authorize>
<%-- 	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="procession.delete">
		<a href="procession/brotherhood/delete.do?id=${row.id}"><spring:message
				code="procession.delete" /></a>
	</display:column> 
	</security:authorize>--%>

</display:table>