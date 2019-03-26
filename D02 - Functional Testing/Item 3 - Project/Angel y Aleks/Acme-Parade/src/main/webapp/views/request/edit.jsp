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
			document.getElementById("row").style.display = 'none';
			document.getElementById("column").style.display = 'none';
			document.getElementById("record").style.display = 'none';
		}
	}

var jsArray = [ 
              <jstl:forEach var="item" items="${numeros}">
                <jstl:out value="${item}"/>,
               </jstl:forEach>
             ];

function mode() {
	var checkBox = document.getElementById("check");
	var row = document.getElementById("row");
	var column = document.getElementById("column");
	if (checkBox.checked == true) {
			row.value = jsArray[0];
			column.value = jsArray[0];
	}
}
</script>

<form:form modelAttribute="request"
	action="${requestURI}?procId=${procId}">
	<form:hidden path="id" />
	<security:authorize access="hasRole('MEMBER')">
		<form:hidden path="marchRow" value="1" />
		<form:hidden path="marchColumn" value="1" />
	</security:authorize>


	<form:select path="status" onchange="change(this);" disabled="${view}">
		<form:option value="0" label="---" disabled="${view}" />
		<security:authorize access="hasRole('BROTHERHOOD')">
			<form:options items="${status}" disabled="${view}" />
		</security:authorize>
		<security:authorize access="hasRole('MEMBER')">
			<form:options items="${statusMember}" disabled="${view}" />
		</security:authorize>
	</form:select>


	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${request.id !=0 && request.status == 'PENDING'}">
			<br>
			<spring:message code="request.check" />
			<form:checkbox path="${check}" value="${request.status == 'PENDING'}"
				disabled="${view}" onclick="mode();" id="check" />

			<acme:textbox code="request.row" path="marchRow" id="row"
				readonly="${view}" />
			<acme:textbox code="request.column" path="marchColumn" id="column"
				readonly="${view}" />
			<acme:textbox code="request.record" path="record" id="record"
				readonly="${view}" />


		</jstl:if>
		<jstl:if test="${request.id !=0 && request.status == 'APPROVED'}">
			<br>

			<spring:message code="request.row"></spring:message>
			<jstl:out value="${request.marchRow}"></jstl:out>
			<br>
			<spring:message code="request.column"></spring:message>
			<jstl:out value="${request.marchColumn}"></jstl:out>

		</jstl:if>
		<jstl:if test="${request.id !=0 && request.status == 'REJECTED'}">
			<br>
			<spring:message code="request.record"></spring:message>:
			<jstl:out value="${request.record}"></jstl:out>
		</jstl:if>
	</security:authorize>

	<br>
	<jstl:if test="${not view}">
		<acme:submit name="save" code="request.save" />
	</jstl:if>
</form:form>

<acme:cancel code="request.cancel" />