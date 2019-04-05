<%--
 * action-2.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script>
	$(document).ready(function() {

		if ($("#id").val() != "0") {
			var v = "${view}";
			var col = $("#position2").val();
			col = col.match(/id=\d+/g);
			var sp = col.toString().match(/\d+/g);

			$("input[type='checkbox'][name='pos']").each(function() {
				for ( var i = 0; i < sp.length; i++) {
					var th = $(this).val();
					var s = sp[i];
					if (s == th) {
						$(this).attr('checked', true);
						if (v == "true") {
							$(this).attr('disabled', true);
						}
					}
				}
			});

		}
	});
</script>

<form:form action="${requestURI}" modelAttribute="problem">
	<form:hidden path="id"/>
	<acme:textbox code="problem.title" path="title" readonly="${view}"/>
	<acme:textbox code="problem.statement" path="statement" readonly="${view}"/>
	<acme:textbox code="problem.hint" path="hint" readonly="${view}"/>
	<acme:textbox code="problem.attachments" path="attachments" readonly="${view}"/>
	<jstl:if test="${not view}">
	<div
		style="width: 500px; height: 100px; overflow-y: scroll; border-style: solid; border-color: initial;">
		<input type="hidden" id="position2" value="${pos}" />
		<jstl:forEach items="${position}" var="f">
<%-- 			<input type="checkbox" name="pos" value="${f.id}" /> --%>
			<form:checkbox path="position" value="${f.id}"/>
			<jstl:out value="${f.title}" />
			<br>
		</jstl:forEach>
	</div>
	</jstl:if>
	<jstl:if test="${view}">
	<spring:message code="problem.positionAssigned"/>
	<jstl:forEach items="${position2}" var="f">
			<jstl:out value="${f.title}" />
		</jstl:forEach>
		<br>
	</jstl:if>
<%-- 	<acme:select items="${position}" itemLabel="title" code="problem.position" path="position" disabled="${view}"/> --%>
	<spring:message code="problem.finalMode"/><form:checkbox path="finalMode" disabled="${view}"/>
	<jstl:if test="${not view}">
	<acme:submit name="save" code="problem.save"/>
	</jstl:if>
</form:form>

