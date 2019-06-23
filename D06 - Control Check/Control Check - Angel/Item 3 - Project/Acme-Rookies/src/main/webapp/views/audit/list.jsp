

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="audits" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column titleKey="audit.moment">
		<jstl:out value="${row.moment}" />
	</display:column>
	<display:column titleKey="audit.text">
		<jstl:out value="${row.text}" />
	</display:column>
	<display:column titleKey="audit.score">
		<jstl:out value="${row.score}" />
	</display:column>
	<display:column titleKey="audit.show">
		<a href="audit/show.do?id=${row.id}"><spring:message
				code="audit.show" /></a>
	</display:column>
	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="audit.edit">
			<jstl:if test="${!row.finalMode}">
				<a href="audit/edit.do?id=${row.id}"><spring:message
						code="audit.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="aolet.list">
			<jstl:if test="${row.finalMode}">
				<a href="aolet/auditor/list.do?audit=${row.id}"><spring:message
						code="aolet.list" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column titleKey="aolet.list">
		<jstl:if test="${row.finalMode}">
			<a href="aolet/external.do?audit=${row.id}"><spring:message
					code="aolet.list.external" /></a>
		</jstl:if>
	</display:column>

	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="aolet.create">
			<jstl:if test="${row.finalMode}">
				<a href="aolet/auditor/create.do?audit=${row.id}"><spring:message
						code="aolet.create" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('AUDITOR')">
	<a href="audit/auditor/create.do?position=${param['position']}"><spring:message
			code="audit.create" /></a>
</security:authorize>