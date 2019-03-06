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

<display:table name="requests" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column titleKey="request.proccesion">${procesion.title}</display:column>
<display:column property="marchRow" titleKey="request.row" />
	<display:column property="marchColumn" titleKey="request.column"/>
	<display:column property="status" titleKey="request.status" sortable="true"/>
	<display:column property="record" titleKey="request.reason"/>
	<display:column>
		<a href="request/member/edit.do?id=${row.id}"> <spring:message code="request.edit">
		</spring:message> </a>
		</display:column>
	
</display:table>