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

<display:table name="positions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="position.name">
		<jstl:out value="${row.name}" />
	</display:column>
	<display:column titleKey="position.otherLangs">
		<jstl:forEach items="${row.otherLangs}" var="l">
			<jstl:out value="${l}" />
		</jstl:forEach>
	</display:column>

	<display:column titleKey="position.edit">

		<a href="position/administrator/edit.do?id=${row.id}"><spring:message
				code="position.edit" /></a>

	</display:column>

	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>