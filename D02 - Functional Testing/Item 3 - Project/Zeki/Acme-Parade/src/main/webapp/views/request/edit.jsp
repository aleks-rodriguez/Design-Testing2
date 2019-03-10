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

	function random(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

//var jsArray = [ 
//              <jstl:forEach var="item" items="${numeros}">
//                <jstl:out value="${item}"/>,
//               </jstl:forEach>
//             ];

function mode() {
//	var checkBox = document.getElementById("check");
	var row = document.getElementById("row");
	var column = document.getElementById("column");
//	var i, j, h0, h1;

//	if (checkBox.checked == true) {
//		for (i = 0; i < jsArray.length; i++) {
//			for(j = 0; j < jsArray.length; j++){
//			s = jsArray[i];
//			s= jsArray.split(".");
//			h0 = jsArray[i];
//			h1 = jsArray[j];
			row.value = random(1,4);
			column.value = random(1,50);
		}
//	}
//}
//}

</script>

<form:form modelAttribute="request"	action="${requestURI}?procId=${procId}">
	<form:hidden path="id" />
	<security:authorize access="hasRole('MEMBER')">
		<form:hidden path="marchRow" value="1" />
		<form:hidden path="marchColumn" value="1" />
	</security:authorize>
	
	
	<form:select path="status" onchange="change(this);">
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
			<form:checkbox path="${check}" value="${request.status == 'PENDING'}" disabled="${view}" onclick="mode();" id="check"/>
			
				<acme:textbox code="request.row" path="marchRow" id="row" readonly="${view}" />
				<acme:textbox code="request.column" path="marchColumn" id="column" readonly="${view}" />
				<acme:textbox code="request.reason" path="record" id="record" readonly="${view}" />
			
		
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
			<spring:message code="request.reason"></spring:message>:
			<jstl:out value="${request.record}"></jstl:out>
		</jstl:if>
	</security:authorize>

	<br>
	<jstl:if test="${not view}">
		<acme:submit name="save" code="request.save" />
		<jstl:if test="${request.id != 0}">
			<jstl:if test="${request.status == 'PENDING' }">
				<acme:submit name="delete" code="request.delete" />
			</jstl:if>
		</jstl:if>
	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="request.cancel" />