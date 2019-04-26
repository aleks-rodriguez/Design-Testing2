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

<display:table name="miscDatas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="misc.show">
		<security:authorize access="hasRole('ROOKIE')">
			<a href="miscData/rookie/show.do?id=${row.id}"><spring:message
					code="misc.show" /></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<a href="miscData/company/show.do?id=${row.id}"><spring:message
					code="misc.show" /></a>

		</security:authorize>
	</display:column>

	<display:column titleKey="misc.text">
		<jstl:out value="${row.text}" />
	</display:column>

	<display:column titleKey="misc.urls">
		<jstl:out value="${row.urls}" />
	</display:column>
	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="misc.edit">
			<a href="miscData/rookie/edit.do?id=${row.id}"><spring:message
					code="misc.edit" /></a>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('ROOKIE')">
	<jstl:if test="${check}">
		<a href="miscData/rookie/create.do?curricula=${param['id']}"><spring:message
				code="misc.create" /></a>
	</jstl:if>
</security:authorize>