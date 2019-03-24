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
<p>
	<spring:message code="float.list" />
</p>

<display:table name="floats" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="float.show">
		<a href="float/show.do?idFloat=${row.id}"><spring:message
				code="float.show" /></a>
	</display:column>

	<display:column titleKey="float.title">
		<jstl:out value="${row.title}" />
	</display:column>
	<display:column titleKey="float.description">
		<jstl:out value="${row.description}" />
	</display:column>
	<acme:some_pictures titleKey="float.pictures" items="${row.pictures}"/>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="float.edit">
			<a href="float/brotherhood/update.do?idFloat=${row.id}"><spring:message
					code="float.edit" /></a>
		</display:column>
		<display:column titleKey="float.delete">
			<a href="float/brotherhood/delete.do?id=${row.id}"><spring:message
					code="float.delete" /></a>
		</display:column>
	</security:authorize>
	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>