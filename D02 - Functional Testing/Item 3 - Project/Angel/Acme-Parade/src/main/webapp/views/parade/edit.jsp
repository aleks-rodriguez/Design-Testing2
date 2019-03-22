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
		if ($("#id").val() != "0") {
			var v = "${view}";
			var col = $("#floats2").val();
			col = col.match(/id=\d+/g);
			var sp = col.toString().match(/\d+/g);

			$("input[type='checkbox'][name='floats']").each(function() {
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
		$("#form").submit(function(event) {
			var stat = $("#statusParade").val();
			var text = $("#rejectedText").val();
			if (stat == "REJECTED" || stat == "RECHAZADO") {
				if (text == "") {
					alert("<spring:message code='parade.chapter.action'/>");
					return false;
				}
			}
		});
	});
</script>
<form:form action="${requestURI}" modelAttribute="parade" id="form">
	<form:hidden path="id" id="id" />


	<acme:textbox code="parade.ticker" path="ticker" readonly="true" />
	<acme:textbox code="parade.title" path="title" readonly="${view}" />
	<acme:textbox code="parade.description" path="description"
		readonly="${view}" />
	<acme:date code="parade.momentOrganised" path="momentOrganised" id="1"
		read="${view}" />
	<div
		style="width: 500px; height: 100px; overflow-y: scroll; border-style: solid; border-color: initial;">
		<input type="hidden" id="floats2" value="${floats}" />
		<jstl:forEach items="${floatsBro}" var="f">
			<input type="checkbox" name="floats" value="${f.id}" />
			<jstl:out value="${f.title}" />
			<br>
		</jstl:forEach>
	</div>
	<jstl:if test="${not view}">
		<input type="checkbox" name="finalMode" value="true">
		<spring:message code="parade.finalMode" />
		<br>
		<acme:submit name="save" code="parade.save" />
		<br>
	</jstl:if>
	<jstl:if test="${view}">
		<form:hidden path="finalMode" />
		<jstl:choose>
			<jstl:when test="${parade.finalMode}">
				<spring:message code="parade.isFinalMode"></spring:message>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="parade.isDraftMode"></spring:message>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>

	<form:label path="status">
		<spring:message code="parade.status" />
	</form:label>
	<security:authorize access="hasRole('BROTHERHOOD')">

		<form:hidden path="status" />
		<jstl:out value=": ${parade.status}" />
		<acme:textarea code="parade.description.rejected" path="whyRejected"
			readonly="true" />
	</security:authorize>
	<security:authorize access="hasRole('CHAPTER')">
		<jstl:if test="${parade.status != 'ACCEPTED'}">
			<form:select path="status" id="statusParade">
				<jstl:forEach items="${status}" var="p">
					<form:option value="${p}" label="${p}" />
				</jstl:forEach>
			</form:select>
		</jstl:if>
		<jstl:if test="${parade.status == 'ACCEPTED'}">
			<form:hidden path="status" />
			<jstl:out value="${parade.status}" />
		</jstl:if>
		<acme:textarea code="parade.description.rejected" path="whyRejected"
			readonly="${parade.status == 'ACCEPTED'}" id="rejectedText" />
		<jstl:if test="${parade.status != 'ACCEPTED'}">
			<acme:submit name="save" code="parade.save" />
		</jstl:if>
	</security:authorize>
</form:form>
<acme:cancel code="parade.cancel" />