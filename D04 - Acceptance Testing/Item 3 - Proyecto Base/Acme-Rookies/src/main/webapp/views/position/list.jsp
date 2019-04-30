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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="positions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="today" value="${now}" pattern="yyyy/MM/dd" />

	<display:column titleKey="position.company">
		<a href="position/showCompany.do?position=${row.id}"><spring:message
				code="position.company" /></a>
	</display:column>

	<display:column titleKey="position.ticker">
		<jstl:out value="${row.ticker.ticker}" />
	</display:column>

	<display:column titleKey="position.title">
		<jstl:out value="${row.title}" />
	</display:column>

	<display:column titleKey="position.description">
		<jstl:out value="${row.description}" />
	</display:column>

	<jstl:if test="${publica}">
		<display:column titleKey="position.show">
			<a href="position/edit.do?id=${row.id}"> <jstl:if
					test="${row.finalMode}">
					<spring:message code="position.show" />
				</jstl:if> <jstl:if test="${!row.finalMode}">
					<spring:message code="position.edit" />
				</jstl:if>
			</a>
		</display:column>
	</jstl:if>

	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="position.application">
			<jstl:if test="${row.deadline ge now}">
				<a href="application/rookie/create.do?idPosition=${row.id}"><spring:message
						code="position.application.create" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">

		<jstl:if test="${publica}">
			<display:column titleKey="position.application.list">
				<jstl:if test="${row.finalMode}">

					<a href="application/company/list.do?position=${row.id}"><spring:message
							code="position.application.list" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>

	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="position.assign">
			<jstl:if test="${row.finalMode}">
				<a href="position/auditor/assign.do?position=${row.id}"><spring:message
						code="position.assign" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('AUDITOR')">
	<p>
		<jstl:out value="${message} " />
	</p>
</security:authorize>

<security:authorize access="hasRole('COMPANY')">

	<p>
		<spring:message code="alert.twiceProblems" />
	</p>

</security:authorize>