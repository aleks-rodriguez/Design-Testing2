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


<display:table name="areas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="name" titleKey="area.name" />
	<display:column property="pictures" titleKey="area.pictures" />

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="area.edit">

			<a href="area/administrator/edit.do?id=${row.id}"><spring:message
					code="area.edit" /></a>
		</display:column>
	</security:authorize>
	<jstl:if test="${check eq 'true'}">
		<security:authorize access="hasRole('BROTHERHOOD')">
			<display:column titleKey="area.assign">
				<a href="area/brotherhood/assign.do?area=${row.id}"><spring:message
						code="area.assign" /></a>
			</display:column>
		</security:authorize>
	</jstl:if>
</display:table>