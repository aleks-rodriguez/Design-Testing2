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

<security:authorize access="hasRole('ADMIN')">
	<form:form action="${requestURI}"
		modelAttribute="category">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${category.id != 0}">
			<form:hidden path="categories" />
		</jstl:if>
		<form:label path="name">
			<spring:message code="category.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
		<form:label path="otherlanguages">
			<spring:message code="category.otherlanguages" />
		</form:label>
		<form:textarea path="otherlanguages" />
		<form:errors cssClass="error" path="otherlanguages"></form:errors>
		<spring:message code="category.other.note" />
		<br />


		<input type="submit" name="save"
			value="<spring:message code="category.save" />" />
		<jstl:if test="${category.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="category.delete" />" />
		</jstl:if>
	</form:form>

	<input type="submit" name="cancel"
		value="<spring:message code ="warranty.cancel"/>"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
</security:authorize>