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

<p>
	<spring:message code="actor.action.list" />
</p>

<display:table name="actors" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="name" titleKey="actor.name" />
	<display:column property="middleName" titleKey="actor.middlename" />
	<display:column property="surname" titleKey="actor.surname" />
	<display:column property="photo" titleKey="actor.photo" />
	<display:column property="email" titleKey="actor.email" />
	<display:column property="phone" titleKey="actor.phone" />
	<display:column property="adress" titleKey="actor.adress" />
	<display:column property="account.username" titleKey="actor.user" />
	<display:column property="account.enabled" titleKey="actor.enabled" />
	<display:column property="suspicious" titleKey="actor.suspicious" />

	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${ban}">
			<display:column>
				<a href="customisation/administrator/ban.do?id=${row.id}" class="btn"><spring:message
						code="actor.ban" /></a>
			</display:column>
		</jstl:if>
		<jstl:if test="${unban}">
			<display:column>
				<a href="customisation/administrator/unban.do?id=${row.id}"
					class="btn"><spring:message code="actor.unban" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>