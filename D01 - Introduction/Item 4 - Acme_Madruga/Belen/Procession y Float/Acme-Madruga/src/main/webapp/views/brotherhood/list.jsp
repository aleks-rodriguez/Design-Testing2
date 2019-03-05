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


<display:table name="brotherhoods" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column property="title" titleKey="brotherhood.title"/>
	<display:column property="establishment" titleKey="brotherhood.establishment" />
	<display:column property="pictures" titleKey="brotherhood.pictures"></display:column>

	<security:authorize access="hasRole('MEMBER')">
	<display:column titleKey="brotherhood.create">
		<a href="enrolment/member/create.do?idBrotherhood=${row.id}"><spring:message
				code="brotherhood.create" /></a>
	</display:column>
	</security:authorize>

</display:table>