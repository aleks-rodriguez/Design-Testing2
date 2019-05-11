<%--
 * action-1.jsp
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

<display:table name="problems" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="problem.show">
		<a href="problem/company/show.do?id=${row.id}"><spring:message
				code="problem.show" /></a>
	</display:column>
	<display:column titleKey="problem.title">
		<jstl:out value="${row.title}" />
	</display:column>
	<display:column titleKey="problem.statement">
		<jstl:out value="${row.statement}" />
	</display:column>
	<display:column titleKey="problem.hint">
		<jstl:out value="${row.hint}" />
	</display:column>
	<display:column titleKey="problem.attachments">
		<jstl:out value="${row.attachments}" />
	</display:column>
	<display:column titleKey="problem.position">
	<jstl:forEach items="${row.position}" var="f">
		<jstl:out value="${f.title}" />
		</jstl:forEach>
	</display:column> 
	<display:column titleKey="problem.finalMode">
		<jstl:out value="${row.finalMode}" />
	</display:column>
	<display:column titleKey="problem.edit">
	<jstl:if test="${!row.finalMode}">
		<a href="problem/company/update.do?id=${row.id}"><spring:message
				code="problem.edit" /></a>
				</jstl:if>
	</display:column>
	<display:column titleKey="problem.delete">
	<jstl:if test="${!row.finalMode}">
		<a href="problem/company/delete.do?id=${row.id}"><spring:message
				code="problem.delete" /></a>
	</jstl:if>
	</display:column>
</display:table>

<a href="problem/company/create.do"><spring:message code="problem.add" /></a>
