<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="aolet">
	<form:hidden path="id" />

	<jstl:if test="${aolet.id == 0}">
		<form:hidden path="audit" />
	</jstl:if>
	
	<!--<acme:textbox code="aolet.ticker" path="ticker" readonly="true" />-->
	<acme:textbox code="aolet.moment" path="moment" readonly="true" />
	<acme:textarea code="aolet.title" path="title"
		readonly="${view or aolet.finalMode}" />
	<acme:textbox code="aolet.description" path="description"
		readonly="${view or aolet.finalMode}" />
	<acme:textbox code="aolet.image" path="image"
		readonly="${view or aolet.finalMode}" />
	<div>
		<spring:message code="aolet.finalMode" />
		<form:checkbox path="finalMode" disabled="${view or audit.finalMode}" />
	</div>
	<jstl:if test="${!view and !aolet.finalMode}">
		<acme:submit name="save" code="aolet.save" />
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="aolet.cancel" />