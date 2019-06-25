

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="omameks" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="omamek.moment">
		<jstl:if test="${lang eq 'en'}">
			<fmt:formatDate value="${row.moment}" pattern="yy/MM/dd" />
		</jstl:if>
		<jstl:if test="${lang eq 'es'}">
			<fmt:formatDate value="${row.moment}" pattern="dd-MM-yyyy" />
		</jstl:if>
	</display:column>

	<display:column titleKey="omamek.ticker">
		<jstl:out value="${row.ticker.ticker}" />
	</display:column>

	<display:column titleKey="omamek.title">
		<jstl:out value="${row.title}" />
	</display:column>

	<display:column titleKey="omamek.description">
		<jstl:out value="${row.description}" />
		<jstl:out value="${row.moment}" />
	</display:column>


	<display:column titleKey="omamek.image">
		<img src="${row.image}" alt="${row.image}" height="80" width="100" />
	</display:column>

	<display:column titleKey="omamek.show">
		<a href="omamek/show.do?id=${row.id}"><spring:message
				code="omamek.show" /></a>
	</display:column>
	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="omamek.edit">
			<jstl:if test="${!row.finalMode}">
				<a href="omamek/company/edit.do?id=${row.id}"><spring:message
						code="omamek.edit" /></a>
			</jstl:if>
		</display:column>
		<display:column titleKey="omamek.delete">
			<jstl:if test="${!row.finalMode}">
				<a href="omamek/company/delete.do?id=${row.id}"><spring:message
						code="omamek.delete" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<script src="scripts/table.js"></script>