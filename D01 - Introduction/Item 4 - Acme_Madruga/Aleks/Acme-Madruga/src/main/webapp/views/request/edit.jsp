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
	function change(o) {
		if (o.value == 'APPROVED') {
			document.getElementById("row").style.display = 'block';
			document.getElementById("column").style.display = 'block';
			document.getElementById("record").style.display = 'none';
		} else if (o.value == 'REJECTED') {
			document.getElementById("row").style.display = 'none';
			document.getElementById("column").style.display = 'none';
			document.getElementById("record").style.display = 'block';
		} else {
			document.getElementById("row").style.display = 'block';
			document.getElementById("column").style.display = 'block';
			document.getElementById("record").style.display = 'block';
		}
	}
	
	function random(min, max) {
	    min = Math.ceil(min);
	    max = Math.floor(max);
	    return Math.floor(Math.random() * (max - min + 1)) + min;
	}
	
	function mode() {
		  var checkBox = document.getElementById("check");
		  var row = document.getElementById("row");
		  var column = document.getElementById("column");
		  
		  if (checkBox.checked == true){
		    row.value = random(1,100);
		    column.value = random(1,100);
		  } 
	} 
</script>

<form:form modelAttribute="request" action="${requestURI}">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:select path="status" onchange="change(this);"	disabled="${disabled}">
		<form:option value="0" label="---" />
		<security:authorize access="hasRole('BROTHERHOOD')">
			<form:options items="${status}" />
		</security:authorize>
		<security:authorize access="hasRole('MEMBER')">
			<form:options items="${statusMember}" />
		</security:authorize>
	</form:select>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${request.id !=0 && request.status == 'PENDING'}">
		<spring:message code="request.check"></spring:message>
		<br><form:checkbox path="${check}" value="${request.status == 'PENDING'}" onclick="mode();" id="check"/>
			<acme:textbox code="request.row" path="marchRow" id="row" />
			<acme:textbox code="request.column" path="marchColumn" id="column" />
			<acme:textbox code="request.reason" path="record" id="record" />
		</jstl:if>
		<jstl:if test="${request.id !=0 && request.status == 'APPROVED'}">
			<br>
			<spring:message code="request.row"></spring:message>
			<jstl:out value="${request.marchRow}"></jstl:out> <br>
			<spring:message code="request.column"></spring:message>
			<jstl:out value="${request.marchColumn}"></jstl:out>
		</jstl:if>
		<jstl:if test="${request.id !=0 && request.status == 'REJECTED'}">
			<br>
			<spring:message code="request.reason"></spring:message>:
			<jstl:out value="${request.record}"></jstl:out>
		</jstl:if>
	</security:authorize>
	
	<br>
	<acme:submit name="save" code="request.save" />
	<jstl:if test="${request.id != 0}">
		<jstl:if test="${request.status == 'PENDING' }">
			<acme:submit name="delete" code="request.delete" />
		</jstl:if>
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="request.cancel" />