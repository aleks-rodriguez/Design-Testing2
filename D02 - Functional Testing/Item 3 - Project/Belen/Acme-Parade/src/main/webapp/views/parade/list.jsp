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
	<spring:message code="procession.list" />
</p>

<display:table name="parades" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="procession.show">
		<a href="parade/show.do?idParade=${row.id}"><spring:message
				code="procession.show" /></a>
	</display:column>
	</security:authorize>
	<display:column property="ticker" titleKey="procession.ticker" />
	<display:column property="title" titleKey="procession.title" />
	<display:column property="description"
		titleKey="procession.description" />

	<display:column titleKey="procession.float">
		<a href="float/list.do?idParade=${row.id}"><spring:message
				code="procession.float" /></a>
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${row.finalMode eq 'false'}">
			<display:column titleKey="procession.edit">
				<a href="parade/brotherhood/update.do?idParade=${row.id}"><spring:message
						code="procession.edit" /></a>
			</display:column>
		</jstl:if>
		<jstl:if test="${row.finalMode eq 'false'}">
			<display:column titleKey="procession.delete">
				<a href="parade/brotherhood/delete.do?id=${row.id}"><spring:message
						code="procession.delete" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('MEMBER')">
		<display:column titleKey="procession.request">
			<a href="request/member/list.do?procId=${row.id}"><spring:message
					code="procession.request" /></a>
		</display:column>
		<display:column titleKey="procession.request.create">
			<a href="request/member/create.do?procId=${row.id}"><spring:message
					code="procession.request.create" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="procession.request">
			<a href="request/brotherhood/list.do?procId=${row.id}"><spring:message
					code="procession.request">
				</spring:message></a>
		</display:column>
	</security:authorize>
	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>