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

<display:table name="brotherhoods" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column titleKey="brotherhood.title">
		<jstl:out value="${row.title}" />
	</display:column>
	<display:column titleKey="brotherhood.establishment">
		<jstl:out value="${row.establishment}" />
	</display:column>
	
	<acme:some_pictures titleKey="brotherhood.pictures" items="${row.pictures}"/>

	<display:column titleKey="brotherhood.history">
		<a href="history/listHistory.do?idBrotherhood=${row.id}"><spring:message
				code="brotherhood.history" /></a>
	</display:column>

	<display:column titleKey="brotherhood.procession">

		<security:authorize access="hasRole('CHAPTER')">
			<a href="parade/chapter/list.do?idBrotherhood=${row.id}"><spring:message
					code="brotherhood.procession" /></a>
		</security:authorize>
		<security:authorize access="!hasRole('CHAPTER')">
			<a href="parade/list.do?idBrotherhood=${row.id}"><spring:message
					code="brotherhood.procession" /></a>
		</security:authorize>
	</display:column>

	<display:column titleKey="brotherhood.member">
		<a href="enrolment/listMember.do?idBrotherhood=${row.id}"><spring:message
				code="brotherhood.member" /></a>
	</display:column>


	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${every}">
			<display:column titleKey="brotherhood.create">
				<a href="enrolment/member/create.do?idBrotherhood=${row.id}"><spring:message
						code="brotherhood.create" /></a>
			</display:column>
		</jstl:if>
		<jstl:if test="${own}">
			<display:column titleKey="brotherhood.delete">
				<a href="enrolment/member/delete.do?idBrotherhood=${row.id}"><spring:message
						code="brotherhood.delete" /></a>
			</display:column>
		</jstl:if>

		<jstl:if test="${dis}">
			<display:column titleKey="brotherhood.readmite">
				<a href="enrolment/member/readmite.do?idBrotherhood=${row.id}"><spring:message
						code="brotherhood.readmite" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>