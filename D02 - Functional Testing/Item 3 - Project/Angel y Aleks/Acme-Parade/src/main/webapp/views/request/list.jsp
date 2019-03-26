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


<display:table name="requests" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="request.showing">
			<a href="request/brotherhood/show.do?id=${row.id}"><spring:message
					code="request.showing" /></a>
		</display:column>
	</security:authorize>
	<display:column titleKey="request.proccesion">
		<jstl:out value="${procesion.title}" />
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="request.row">
		<jstl:out value="${row.marchRow}" />
	</display:column>
	<display:column titleKey="request.column">
		<jstl:out value="${row.marchColumn}" />
	</display:column>
	</security:authorize>
	<display:column titleKey="request.status" sortable="true">
		<jstl:out value="${row.status}" />
	</display:column>
	<display:column titleKey="request.record">
		<jstl:out value="${row.record}" />
	</display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column>
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="request/brotherhood/edit.do?id=${row.id}"> <spring:message
						code="request.edit">
					</spring:message>
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('MEMBER')">
		<display:column>
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="request/member/delete.do?idRequest=${row.id}"> <spring:message
						code="request.delete">
					</spring:message>
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>

<script>
	var table = document.getElementById("row");
	var tbody = table.getElementsByTagName("tbody")[0];
	var row = tbody.getElementsByTagName("tr");

	for ( var i = 0; i < row.length; i++) {
		var value = trim(row[i].getElementsByTagName("td")[4].firstChild.nodeValue);
		if (value == 'APPROVED') {
			row[i].style.backgroundColor = "lightGreen";
		} else if (value == 'REJECTED') {
			row[i].style.backgroundColor = "orange";
		} else if (value == 'PENDING') {
			row[i].style.backgroundColor = " lightGrey";
		}
	}
	function trim(cadena) {
		// USO: Devuelve un string como el
		// par�metro cadena pero quitando los
		// espacios en blanco de los bordes.

		var retorno = cadena.replace(/^\s+/g, '');
		retorno = retorno.replace(/\s+$/g, '');
		return retorno;
	}
</script>