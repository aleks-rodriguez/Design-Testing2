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

<h2>
	<spring:message code="curricula.persData" />
</h2>
<form:form action="${requestURI}" modelAttribute="curricula">

	<form:hidden path="id" />

	<acme:textbox code="curricula.fullName" path="fullName" readonly="true" />
	<acme:textarea code="curricula.statement" path="statement"
		readonly="${view}" />
	<acme:textbox code="curricula.phone" path="phoneNumber"
		readonly="${view}" />
	<acme:textbox code="curricula.github" path="githubProfile"
		readonly="${view}" />
	<acme:textbox code="curricula.linkedin" path="linkedInProfile"
		readonly="${view}" />

	<jstl:if test="${!view}">
		<acme:submit name="save" code="curricula.save" />
		<jstl:if test="${curricula.id != 0}">
			<acme:submit name="delete" code="curricula.delete" />
		</jstl:if>

	</jstl:if>

</form:form>

<acme:cancel url="${requestCancel}" code="curricula.cancel" />

<jstl:if test="${view}">
	<div>
		<h2>
			<spring:message code="curricula.posData" />
		</h2>
		<jsp:include page="../positionData/list.jsp" />
	</div>
	<div>
		<h2>
			<spring:message code="curricula.miscData" />
		</h2>
		<jsp:include page="../miscellaneousData/list.jsp" />
	</div>
	<div>
		<h2>
			<spring:message code="curricula.eduData" />
		</h2>
		<jsp:include page="../educationData/list.jsp" />
	</div>
</jstl:if>