<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="pelf">
	<form:hidden path="id" />
	<form:hidden path="audit" />
	<jstl:if test="${pelf.id != 0 }">
		<spring:message code="pelf.ticker"></spring:message>: <jstl:out
			value="${pelf.ticker.ticker}"></jstl:out>
	</jstl:if>
	<acme:textarea code="pelf.body" path="body"
		readonly="${view}" />
	<acme:textbox code="pelf.picture" path="picture"
		readonly="${view}" />
	<div>
		<spring:message code="pelf.finalMode" />
		<form:checkbox path="finalMode" disabled="${view}" />
	</div>
	<jstl:if test="${!view}">
		<acme:submit name="save" code="pelf.save" />
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="pelf.cancel" />