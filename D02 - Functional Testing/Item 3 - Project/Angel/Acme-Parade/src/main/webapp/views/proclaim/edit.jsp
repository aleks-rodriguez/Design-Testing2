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

<script>
	$(document).ready(function() {
		$("#form").submit(function(event) {
			var mod = $("#mode").val();
			if (mod) {
				var conf = confirm("<spring:message code='proclaim.confirm1'/>");
				if (conf) {
					var conf2 = confirm("<spring:message code='proclaim.confirm2'/>");
					if (!conf2) { return false; }
				} else {
					return false;
				}
			}
		});
	});
</script>

<form:form modelAttribute="proclaim" action="${requestURI}" id="form">

	<form:hidden path="id" />
	<acme:textbox code="proclaim.date" path="moment" readonly="true" />
	<acme:textarea code="proclaim.description" path="text"
		readonly="${proclaim.finalMode}" />
	<jstl:if test="${proclaim.finalMode eq 'false'}">
		<spring:message code="proclaim.finalMode" />
		<form:checkbox path="finalMode" id="mode" />
		<acme:submit name="save" code="proclaim.save" />
	</jstl:if>

	<jstl:if test="${proclaim.id != 0 and proclaim.finalMode eq 'false'}">
		<acme:submit name="delete" code="proclaim.delete" />
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="proclaim.cancel" />