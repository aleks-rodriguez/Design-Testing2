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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">
	
	
	
	function change(o) {

		if (o.value != 0) {
			document.getElementById("curriculaLink").href = "curricula/hacker/show.do?id=" + o.value;
			document.getElementById("curriculaLink").style.visibility = "visible";
		} else {
			document.getElementById("curriculaLink").style.visibility = "hidden";
		}
	}
</script>
<form:form action="${requestURI}" modelAttribute="application">
	<form:hidden path="id" />
	<form:hidden path="problem" />
	<form:hidden path="position" />
	<acme:date code="application.applicationMoment" id="1"
		path="applicationMoment" readonly="true" />
	<acme:date code="application.moment" id="1" path="moment"
		readonly="true" />
	<acme:textbox code="application.position" path="position.title"
		readonly="true" />
	<acme:textbox code="application.problem" path="problem.title"
		readonly="true" />
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${view}">
			<spring:message code="application.status" />:
			<jstl:out value="${application.status}" />
		</jstl:if>
		<br>
		<jstl:if test="${!view}">
			<form:select path="status">
				<form:option value="0" label="---" />
				<form:options items="${statusCol}" />
			</form:select>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('HACKER')">
		<jstl:if test="${!view}">
			<form:select path="curricula" onchange="change(this);">
				<form:option value="0" label="---" />
				<jstl:set var="i" value="0" />
				<jstl:forEach items="${curriculas}" var="cur">
					<jstl:set var="i" value="${i+1}" />
					<form:option value="${cur.id}">
						<spring:message code="application.curricula" /> ${i}
					</form:option>
				</jstl:forEach>
			</form:select>

			<a id="curriculaLink" style="visibility:hidden"><spring:message
					code="application.curriculaSelected" /></a>
		</jstl:if>
		<jstl:if test="${view}">
			<a href="curricula/hacker/show.do?id=${application.curricula.id}"><spring:message
					code="application.curricula" /></a>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('COMPANY')">
		<a href="curricula/company/show.do?id=${application.curricula.id}"><spring:message
				code="application.curricula" /></a>
	</security:authorize>
	<security:authorize access="hasRole('HACKER')">
		<acme:textbox code="application.status" path="status" readonly="true" />
	</security:authorize>
	<jstl:if test="${not view}">
		<acme:submit name="save" code="application.save" />
	</jstl:if>

</form:form>

<acme:cancel url="${requestCancel}" code="application.cancel" />

