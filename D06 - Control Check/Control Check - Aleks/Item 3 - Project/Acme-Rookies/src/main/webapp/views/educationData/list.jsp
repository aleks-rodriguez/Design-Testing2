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

<display:table name="eduDatas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="eduData.show">
		<security:authorize access="hasRole('ROOKIE')">
			<a href="educationData/rookie/show.do?id=${row.id}"><spring:message
					code="eduData.show" /></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<a href="educationData/company/show.do?id=${row.id}"><spring:message
					code="eduData.show" /></a>
		</security:authorize>
	</display:column>
	<display:column titleKey="eduData.degree">
		<jstl:out value="${row.degree}" />
	</display:column>
	<display:column titleKey="eduData.institution">
		<jstl:out value="${row.institution}" />
	</display:column>
	<display:column titleKey="eduData.startDate">
		<jstl:out value="${row.startDate}" />
	</display:column>
	<display:column titleKey="eduData.endDate">
		<jstl:out value="${row.endDate}" />
	</display:column>
	<security:authorize access="hasRole('ROOKIE')">
		<jstl:if test="${!copy}">
			<display:column titleKey="eduData.edit">

				<a href="educationData/rookie/edit.do?id=${row.id}"><spring:message
						code="eduData.edit" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('ROOKIE')">
	<jstl:if test="${check and !copy}">
		<a href="educationData/rookie/create.do?curricula=${param['id']}"><spring:message
				code="eduData.create" /></a>
	</jstl:if>
</security:authorize>