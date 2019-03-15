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

	<display:column property="status" titleKey="parade.status" />
	<display:column property="ticker" titleKey="parade.ticker" />
	<display:column property="title" titleKey="parade.title" />


	<display:column property="description" titleKey="parade.description" />

	<display:column titleKey="parade.float">
		<a href="float/list.do?idParade=${row.id}"><spring:message
				code="parade.float" /></a>
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="parade.show">
			<a href="parade/show.do?idParade=${row.id}"><spring:message
					code="parade.show" /></a>
		</display:column>
	</security:authorize>

	<jstl:choose>
		<jstl:when test="${!general}">
		</jstl:when>
		<jstl:otherwise>
			<security:authorize access="hasRole('BROTHERHOOD')">
				<jstl:if test="${row.finalMode eq 'false'}">
					<display:column titleKey="parade.edit">
						<a href="parade/brotherhood/update.do?idParade=${row.id}"><spring:message
								code="parade.edit" /></a>
					</display:column>
				</jstl:if>
				<jstl:if test="${row.finalMode eq 'false'}">
					<display:column titleKey="parade.delete">
						<a href="parade/brotherhood/delete.do?id=${row.id}"><spring:message
								code="parade.delete" /></a>
					</display:column>
				</jstl:if>
				<display:column titleKey="parade.request">
					<a href="request/brotherhood/list.do?procId=${row.id}"><spring:message
							code="parade.request">
						</spring:message></a>
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
		var value = row[i].getElementsByTagName("td")[0].firstChild.nodeValue;
		if (value == 'SUBMITTED') {
			row[i].style.backgroundColor = "grey";
		} else if (value == 'ACCEPTED') {
			row[i].style.backgroundColor = "green";
		} else if (value == 'REJECTED') {
			row[i].style.backgroundColor = "red";
		}
	}
</script>