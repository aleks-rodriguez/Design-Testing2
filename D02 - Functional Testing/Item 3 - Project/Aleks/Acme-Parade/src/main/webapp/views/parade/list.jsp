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

<p>
	<spring:message code="parade.list" />
</p>

<display:table name="parades" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="parade.status" sortable="true">
		<jstl:out value="${row.status}" />
	</display:column>
	<display:column titleKey="parade.ticker">
		<jstl:out value="${row.ticker}" />
	</display:column>
	<display:column titleKey="parade.title">
		<jstl:out value="${row.title}" />
	</display:column>


	<!--<display:column property="description" titleKey="parade.description" />-->
<security:authorize access="!hasRole('SPONSOR')">
	<display:column titleKey="parade.float">
		<a href="float/list.do?idParade=${row.id}"><spring:message
				code="parade.float" /></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('SPONSOR')">
	<display:column title="sponsorship.create.title">
		<a href="sponsorship/sponsor/create.do?idParade=${row.id}"><spring:message
				code="sponsorship.create" /></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CHAPTER')">
		<display:column titleKey="parade.show">
			<a href="parade/chapter/show.do?idParade=${row.id}"><spring:message
					code="parade.show" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="parade.copy">
			<jstl:if test="${row.finalMode}">
				<a href="parade/brotherhood/copy.do?id=${row.id}"><spring:message
						code="parade.copy" /></a>
			</jstl:if>
		</display:column>
		<display:column titleKey="parade.show">
			<a href="parade/show.do?idParade=${row.id}"><spring:message
					code="parade.show" /></a>
		</display:column>

		<display:column titleKey="parade.segments">
			<a href="segment/list.do?parade=${row.id}"><spring:message
					code="parade.segments.list" /></a>
			<br>
			<jstl:if test="${own}">
				<security:authorize access="hasRole('BROTHERHOOD')">
					<a href="segment/create.do?parade=${row.id}"><spring:message
							code="parade.segments.create" /> </a>
				</security:authorize>
			</jstl:if>
		</display:column>
		<display:column titleKey="parade.request">
			<a href="request/brotherhood/list.do?procId=${row.id}"><spring:message
					code="parade.request" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="!hasRole('BROTHERHOOD')">
		<display:column titleKey="parade.segments">
			<a href="segment/list.do?parade=${row.id}"><spring:message
					code="parade.segments.list" /> </a>

		</display:column>
	</security:authorize>
	<jstl:choose>
		<jstl:when test="${general eq 'false'}">
		</jstl:when>
		<jstl:otherwise>
			<security:authorize access="hasRole('BROTHERHOOD')">
				<display:column titleKey="parade.edit">
					<jstl:if test="${row.finalMode eq 'false'}">
						<a href="parade/brotherhood/update.do?idParade=${row.id}"><spring:message
								code="parade.edit" /></a>
					</jstl:if>
				</display:column>

				<display:column titleKey="parade.delete">
					<jstl:if test="${row.finalMode eq 'false'}">
						<a href="parade/brotherhood/delete.do?id=${row.id}"><spring:message
								code="parade.delete" /></a>
					</jstl:if>
				</display:column>

			</security:authorize>
		</jstl:otherwise>
	</jstl:choose>
	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${own}">
			<display:column titleKey="parade.request">
				<a href="request/member/list.do?procId=${row.id}"><spring:message
						code="parade.request" /></a>
			</display:column>
			<display:column titleKey="parade.request.create">
				<a href="request/member/create.do?procId=${row.id}"><spring:message
						code="parade.request.create" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>


	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>

<script>
	var table = document.getElementById("row");
	var tbody = table.getElementsByTagName("tbody")[0];
	var row = tbody.getElementsByTagName("tr");

	for ( var i = 0; i < row.length; i++) {
		var value = trim(row[i].getElementsByTagName("td")[0].firstChild.nodeValue);

		if (value == 'SUBMITTED') {
			row[i].style.backgroundColor = "grey";
		} else if (value == 'ACCEPTED') {
			row[i].style.backgroundColor = "green";
		} else if (value == 'REJECTED') {
			row[i].style.backgroundColor = "red";
		}
	}
	
	// Tomado de https://www.lawebdelprogramador.com/foros/JavaScript/23270-Como-quitar-un-espacio-es-blanco.html
	
	function trim(cadena) {
		// USO: Devuelve un string como el
		// parámetro cadena pero quitando los
		// espacios en blanco de los bordes.

		var retorno = cadena.replace(/^\s+/g, '');
		retorno = retorno.replace(/\s+$/g, '');
		return retorno;
	}
</script>