
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="segment">

	<form:hidden path="id" />
	<form:hidden path="parade" />
	
	<!-- Order -->
	<acme:textbox code="segment.number" path="number"
		readonly="${view}" />
	<!-- Coordinate -->
	<acme:textbox code="coordinate.latitude" path="segment.latitude"
		readonly="${view}" />
	<acme:textbox code="coordinate.longitude" path="segment.longitude"
		readonly="${view}" />

	<!-- Hours -->
	<acme:textbox code="segment.arriveTime" path="arriveTime"
		readonly="${view}" />

	<security:authorize access="hasRole('BROTHERHOOD')">

		<jstl:if test="${view eq 'false' and segment.id != 0}">
			<acme:submit name="delete" code="segment.delete" />
		</jstl:if>
		<jstl:if test="${!view}">
			<acme:submit name="save" code="segment.save" />
		</jstl:if>
	</security:authorize>

</form:form>

<acme:cancel code="segment.cancel" />