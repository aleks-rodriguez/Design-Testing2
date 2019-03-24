<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="segments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="segment.parade">
		<jstl:out value="${row.parade.title}" />
		<br>
		<jstl:out value="${row.parade.description}" />
		<br>
		<a href="parade/show.do?idParade=${row.parade.id}"><spring:message
				code="segment.link" /></a>
	</display:column>
	<display:column titleKey="segment.coordinate">
		<jstl:out value="${row.segment.latitude}" />
		<br>
		<jstl:out value="${row.segment.longitude}" />
	</display:column>
	<display:column titleKey="segment.arriveTime">
		<jstl:out value="${row.arriveTime}" />
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${mine}">
			<display:column titleKey="segment.edit">
				<a href="segment/edit.do?segment=${row.id}"><spring:message
						code="segment.edit" /></a>
			</display:column>
		</jstl:if>
		<jstl:if test="${!mine}">
			<display:column titleKey="segment.viewMore">
				<a href="segment/edit.do?segment=${row.id}"><spring:message
						code="segment.viewMore" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	<security:authorize access="!hasRole('BROTHERHOOD')">

		<display:column titleKey="segment.viewMore">
			<a href="segment/edit.do?segment=${row.id}"><spring:message
					code="segment.viewMore" /></a>
		</display:column>
	</security:authorize>
</display:table>