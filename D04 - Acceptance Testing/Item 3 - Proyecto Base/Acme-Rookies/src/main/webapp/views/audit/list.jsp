

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
		<jstl:out value="row.moment" />
	</display:column>
	<display:column titleKey="audit.text">
		<jstl:out value="row.text" />
	</display:column>
	<display:column titleKey="audit.score">
		<jstl:out value="row.score" />
	</display:column>
	<display:column titleKey="audit.show">
		<a href="audit/edit.do?id=${row.id}"><spring:message
				code="audit.show" /></a>
	</display:column>
	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="audit.edit">
			<a href="audit/edit.do?id=${row.id}"><spring:message
					code="audit.edit" /></a>
		</display:column>
	</security:authorize>
</display:table>

