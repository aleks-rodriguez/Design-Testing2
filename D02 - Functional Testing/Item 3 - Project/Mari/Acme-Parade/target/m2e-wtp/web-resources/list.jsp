<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
	<spring:message code="sponsorship.title" />
</p>

<display:table name="sponsorships" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<display:column titleKey="sponsorship.urlBanner">
		<img src="${row.urlBanner}" height="50" width="75" />
	</display:column>
	<display:column property="linkTPage" titleKey="sponsorship.linkTPage">
		<a href="${row.linkTPage}">${row.linkTPage}</a>
	</display:column>
	<display:column titleKey="sponsorship.sponsor">
		<jstl:out value="${row.sponsor.name}" />
		<jstl:out value="${row.sponsor.surname}" />
		<br>
		<jstl:out value="${row.sponsor.email}" />
	</display:column>
	<jstl:if test="${param.tutorial eq '0'}">
		<display:column titleKey="sponsorship.tutorial">

			<a href="tutorial/sponsor/edit.do?id=${row.tutorial.id}"><spring:message
					code="sponsorship.tutorial" /></a>
		</display:column>
		<display:column titleKey="sponsorship.viewmore">

			<a href="sponsorship/sponsor/edit.do?id=${row.id}"><spring:message
					code="sponsorship.viewmore" /></a>
		</display:column>
	</jstl:if>
	<jstl:forEach items="${errors}" var="error">
		<jstl:out value="${error}" />
	</jstl:forEach>
	<jstl:out value="${oops}" />
	<jstl:out value="${message}" />

</display:table>