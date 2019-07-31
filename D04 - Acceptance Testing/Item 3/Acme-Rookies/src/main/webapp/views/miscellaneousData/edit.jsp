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

<form:form action="${requestURI}" modelAttribute="miscellaneousData">
	<form:hidden path="id" />

	<acme:textbox code="misc.text" path="text" readonly="${view}" />
	<acme:textarea code="misc.urls" path="urls" readonly="${view}" />

	<jstl:if test="${!view}">
		<acme:submit name="save" code="misc.save" />
		<jstl:if test="${miscellaneousData.id != 0}">
			<acme:submit name="delete" code="misc.delete" />
		</jstl:if>
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="misc.cancel" />