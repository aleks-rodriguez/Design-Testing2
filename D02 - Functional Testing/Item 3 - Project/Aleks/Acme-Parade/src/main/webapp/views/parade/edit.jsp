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

<form:form action="${requestURI}" modelAttribute="parade">
	<form:hidden path="id" />


	<acme:textbox code="parade.ticker" path="ticker" readonly="true" />
	<acme:textbox code="parade.title" path="title" readonly="${view}" />
	<acme:textbox code="parade.description" path="description"
		readonly="${view}" />
	<acme:date code="parade.momentOrganised" path="momentOrganised" id="1"
		read="${view}" />
	<jstl:if test="${not view}">
		<input type="checkbox" name="finalMode" value="true">
		<spring:message code="parade.finalMode" />
		<br>
		<div
			style="width: 500px; height: 100px; overflow-y: scroll; border-style: solid; border-color: initial;">
			<jstl:forEach items="${floats}" var="f">
				<input type="checkbox" name="floats" value="${f.id}" />
				<jstl:out value="${f.title}" />
				<br>
			</jstl:forEach>
		</div>
		<acme:submit name="save" code="parade.save" />
	</jstl:if>
	<jstl:if test="${view}">
		<jstl:choose>
			<jstl:when test="${parade.finalMode}">
				<spring:message code="parade.isFinalMode"></spring:message>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="parade.isDraftMode"></spring:message>
			</jstl:otherwise>
		</jstl:choose>
		<div
			style="width: 500px; height: 100px; overflow-y: scroll; border-style: solid; border-color: initial;">
			<jstl:if test="${view}">
				<jstl:forEach items="${parade.floats}" var="f">
					<jstl:out value="${f.title}" />
					<br>
				</jstl:forEach>
			</jstl:if>
		</div>
	</jstl:if>

</form:form>
<acme:cancel url="/parade/list.do" code="parade.cancel" />