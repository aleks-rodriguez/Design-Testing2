<%--
 * action-2.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="position">
	<form:hidden path="id" />

	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="today" value="${now}" pattern="yyyy/MM/dd" />
	
	<acme:textbox code="position.ticker" path="ticker" readonly="true" />
	<acme:textbox code="position.title" path="title"
		readonly="${view or position.finalMode}" />
	<acme:textbox code="position.description" path="description"
		readonly="${view or position.finalMode}" />
	<acme:date code="position.deadline" path="deadline" id="deadline"
		readonly="${view or position.finalMode}" start="${today}" />
	<acme:textarea code="position.profileRequired" path="profileRequired"
		readonly="${view or position.finalMode}" />
	<acme:textarea code="position.skillsRequired" path="skillsRequired"
		readonly="${view or position.finalMode}" />
	<acme:textarea code="position.technologies" path="technologies"
		readonly="${view or position.finalMode}" />
	<acme:textbox code="position.salary" path="salary"
		readonly="${view or position.finalMode}" />

	<security:authorize access="hasRole('COMPANY')">
		<form:label path="finalMode">
			<spring:message code="position.finalMode" />
		</form:label>
		<form:checkbox path="finalMode"
			disabled="${view or position.finalMode}" />
		<jstl:if test="${!position.finalMode and !view}">
			<acme:submit name="save" code="position.save" />
			<jstl:if test="${position.id != 0}">
				<acme:submit name="delete" code="position.delete" />
			</jstl:if>
		</jstl:if>
		<jstl:if test="${!position.cancel and position.finalMode and !view}">
			<acme:submit name="cancelPos" code="position.cancelPos" />
		</jstl:if>
		<jstl:if test="${position.cancel and position.finalMode}">
			<spring:message code="position.isCancel" />
		</jstl:if>
	</security:authorize>

</form:form>

<acme:cancel url="${requestCancel}" code="position.cancel" />
