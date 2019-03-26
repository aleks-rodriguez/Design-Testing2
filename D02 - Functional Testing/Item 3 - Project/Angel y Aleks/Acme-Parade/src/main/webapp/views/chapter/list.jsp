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


<display:table name="chapters" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="chapter.name">
		<jstl:out value="${row.name}" />
	</display:column>
	<display:column titleKey="chapter.middlename">
		<jstl:out value="${row.middleName}" />
	</display:column>
	<display:column titleKey="chapter.surname">
		<jstl:out value="${row.surname}" />
	</display:column>
	<display:column titleKey="chapter.photo">
		<img src="${row.photo}" height="100" width="100" />
	</display:column>
	<display:column titleKey="chapter.email">
		<a href="mailto:${row.email}"><jstl:out value="${row.email}" /></a>
	</display:column>
	<display:column titleKey="chapter.phone">
		<jstl:out value="${row.phone}" />
	</display:column>
	<display:column property="adress" titleKey="chapter.adress">
		<jstl:out value="${row.adress}" />
	</display:column>
	<display:column titleKey="chapter.title">
		<jstl:out value="${row.title}" />
	</display:column>
	<display:column titleKey="chapter.area.name">
		<jstl:out value="${row.area.name}" />
	</display:column>

	<display:column titleKey="chapter.brotherhoods">
		<a href="area/listBrotherhood.do?area=${row.area.id}"><spring:message
				code="chapter.brotherhood" /></a>
	</display:column>

	<display:column titleKey="chapter.proclaims">
		<a href="proclaim/list.do?chapter=${row.id}"><spring:message
				code="chapter.proclaims" /></a>
	</display:column>

</display:table>