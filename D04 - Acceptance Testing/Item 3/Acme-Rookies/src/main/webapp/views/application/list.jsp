<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="application.show">
		<security:authorize access="hasRole('ROOKIE')">
			<a href="application/rookie/show.do?idApplication=${row.id}"><spring:message
					code="application.show" /></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<jstl:if
				test="${!(row.answer == null) and row.status == 'SUBMITTED'}">
				<a href="application/company/update.do?idApplication=${row.id}"><spring:message
						code="application.update" /></a>
			</jstl:if>
			<jstl:if
				test="${row.status != 'SUBMITTED'}">
				<a href="application/company/show.do?idApplication=${row.id}"><spring:message
						code="application.show" /></a>
			</jstl:if>
		</security:authorize>
	</display:column>
	<display:column titleKey="application.applicationMoment">
		<jstl:out value="${row.applicationMoment}" />
	</display:column>
	<display:column titleKey="application.moment">
		<jstl:out value="${row.moment}" />
	</display:column>
	<display:column titleKey="application.status" sortable="true">
		<jstl:out value="${row.status}" />
	</display:column>
	<display:column titleKey="application.problem">
		<security:authorize access="hasRole('ROOKIE')">
			<a href="problem/rookie/show.do?id=${row.problem.id}"> <jstl:out
					value="${row.problem.title}"></jstl:out></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<a href="problem/company/show.do?id=${row.problem.id}"> <jstl:out
					value="${row.problem.title}"></jstl:out></a>
		</security:authorize>
	</display:column>
	<display:column titleKey="application.position">
		<jstl:out value="${row.position.title}" />
	</display:column>
	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="application.createAnswer">
			<jstl:if test="${row.answer == null}">
				<a href="answer/rookie/create.do?idApplication=${row.id}"><spring:message
						code="application.create.answer" /> </a>
			</jstl:if>
			<jstl:if test="${!(row.answer == null)}">
				<a href="answer/rookie/show.do?idApplication=${row.id}"><spring:message
						code="application.show.answer" /> </a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="application.show.answer">
			<jstl:if test="${!(row.answer == null)}">
				<a href="answer/company/show.do?idApplication=${row.id}"><spring:message
						code="application.show.answer" /> </a>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>
<!--  
<a href="problem/company/create.do"><spring:message code="problem.add" /></a>-->
