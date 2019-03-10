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


<display:table name="members" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="member.show">
			<a href="enrolment/brotherhood/show.do?idMember=${row.id}"><spring:message
					code="member.show" /></a>
		</display:column>
	</security:authorize>
	<display:column property="surname" titleKey="member.surname" />
	<display:column property="name" titleKey="member.name" />
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="member.delete">
			<a href="enrolment/brotherhood/deleteMember.do?idMember=${row.id}"><spring:message
					code="member.delete" /></a>
		</display:column>
	</security:authorize>

</display:table>