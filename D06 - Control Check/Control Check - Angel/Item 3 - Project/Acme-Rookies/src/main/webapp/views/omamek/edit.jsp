<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="omamek">
	<form:hidden path="id" />

	<jstl:if test="${omamek.id == 0}">
		<form:hidden path="audit" />
	</jstl:if>
	
	<acme:textbox code="omamek.ticker" path="ticker" readonly="true" />
	<acme:textbox code="omamek.moment" path="moment" readonly="true" />
	<acme:textbox code="omamek.title" path="title"
		readonly="${view or omamek.finalMode}" />
	<acme:textarea code="omamek.description" path="description"
		readonly="${view or omamek.finalMode}" />
	<acme:textbox code="omamek.image" path="image"
		readonly="${view or omamek.finalMode}" />
	<div>
		<spring:message code="omamek.finalMode" />
		<form:checkbox path="finalMode" disabled="${view or omamek.finalMode}" />
	</div>
	<jstl:if test="${!view and !omamek.finalMode}">
		<acme:submit name="save" code="omamek.save" />
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="omamek.cancel" />