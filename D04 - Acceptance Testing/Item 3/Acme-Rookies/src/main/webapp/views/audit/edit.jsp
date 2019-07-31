<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="audit">
	<form:hidden path="id" />
	<form:hidden path="position" />
	<acme:textbox code="audit.moment" path="moment" readonly="true" />
	<acme:textarea code="audit.text" path="text"
		readonly="${view or audit.finalMode}" />
	<acme:textbox code="audit.score" path="score"
		readonly="${view or audit.finalMode}" />
	<div>
		<spring:message code="audit.finalMode" />
		<form:checkbox path="finalMode" disabled="${view or audit.finalMode}" />
	</div>
	<jstl:if test="${!view and !audit.finalMode}">
		<acme:submit name="save" code="audit.save" />
		<jstl:if test="${audit.id != 0}">
			<acme:submit name="delete" code="audit.delete" />
		</jstl:if>
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="audit.cancel" />