<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${not inception}">
<jstl:if test="${not show}">
<p><spring:message code="history.createInception.info"/></p>
<input type="button" name="inception" value="<spring:message code="history.createInception"/>"
onclick="javascript:relativeRedir('inceptionRecord/brotherhood/create.do');"/>
</jstl:if>
</jstl:if>
<jstl:if test="${inception}">
<h2><spring:message code="history.inception" /></h2>
<display:table name="inceptionRecord" id="row"
	requestURI="${requestURI}" pagesize="5"
	class="displaytag">
<display:column titleKey="inception.show">
		<a href="inceptionRecord/show.do?idInception=${row.id}"><spring:message
				code="inception.show" /></a>
</display:column>
<display:column titleKey="history.inception.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column titleKey="history.inception.description">
	 <jstl:out value="${row.description}" />
</display:column>
<display:column titleKey="history.inception.photos">
<jstl:forEach items="${row.photos}" var="photo">
	<img src='<jstl:out value="photo"/>'/>
</jstl:forEach>
</display:column>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<display:column titleKey="inception.edit">
		<a href="inceptionRecord/brotherhood/update.do?idInception=${row.id}"><spring:message
				code="inception.edit" /></a>
</display:column>
</jstl:if>
</security:authorize>
		
</display:table>
<br>
<h2><spring:message code="history.legal" /></h2>
<display:table name="legalRecord" id="row"
	requestURI="${requestURI}" pagesize="5"
	class="displaytag">
<display:column titleKey="legal.show">
		<a href="legalRecord/show.do?idLegal=${row.id}"><spring:message
				code="legal.show" /></a>
</display:column>
<display:column titleKey="history.legal.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column titleKey="history.legal.description">
	<jstl:out value="${row.description}" />
</display:column>
<display:column titleKey="history.legal.legalName">
	<jstl:out value="${row.legalName}" />
</display:column>
<display:column titleKey="history.legal.vat" >
	<jstl:out value="${row.vat}" />
</display:column>
<display:column titleKey="history.legal.laws">
	<jstl:out value="${row.laws}" />
</display:column>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<display:column titleKey="legal.edit">
		<a href="legalRecord/brotherhood/update.do?idLegal=${row.id}"><spring:message
				code="legal.edit" /></a>
</display:column>
<display:column titleKey="legal.delete">
		<a href="legalRecord/brotherhood/delete.do?idLegal=${row.id}"><spring:message
				code="legal.delete" /></a>
</display:column>
</jstl:if>
</security:authorize>
		
</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<a href="legalRecord/brotherhood/create.do"><spring:message
				code="legal.create" /></a>
</jstl:if>
</security:authorize>
<br>
<h2><spring:message code="history.period" /></h2>
<display:table name="periodRecord" id="row" requestURI="${requestURI}" pagesize="5"
	class="displaytag">
<display:column titleKey="period.show">
		<a href="periodRecord/show.do?idPeriod=${row.id}"><spring:message
				code="period.show" /></a>
</display:column>
<display:column titleKey="history.period.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column titleKey="history.period.description">
	<jstl:out value="${row.description}"/>
</display:column>
<display:column property="startDate" titleKey="history.period.startDate"/>
<display:column property="endDate" titleKey="history.period.endDate" />
<display:column titleKey="history.period.photos">
<jstl:forEach items="${row.photos}" var="photo">
	<img src='<jstl:out value="photo"/>'/>
</jstl:forEach>
</display:column>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<display:column titleKey="period.edit">
		<a href="periodRecord/brotherhood/update.do?idPeriod=${row.id}"><spring:message
				code="period.edit" /></a>
</display:column>
<display:column titleKey="period.delete">
		<a href="periodRecord/brotherhood/delete.do?idPeriod=${row.id}"><spring:message
				code="period.delete" /></a>
</display:column>
</jstl:if>
</security:authorize>
</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<a href="periodRecord/brotherhood/create.do"><spring:message
				code="period.create" /></a>
</jstl:if>
</security:authorize>

<h2><spring:message code="history.miscellaneous" /></h2>
<display:table name="miscellaneousRecord" id="row" requestURI="${requestURI}" pagesize="5"
	class="displaytag">
<display:column titleKey="miscellaneous.show">
		<a href="miscellaneousRecord/show.do?idMiscellaneous=${row.id}"><spring:message
				code="miscellaneous.show" /></a>
</display:column>
<display:column titleKey="history.miscellaneous.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column titleKey="history.miscellaneous.description">
	<jstl:out value="${row.description}"/>
</display:column>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<display:column titleKey="miscellaneous.edit">
		<a href="miscellaneousRecord/brotherhood/update.do?idMiscellaneous=${row.id}"><spring:message
				code="miscellaneous.edit" /></a>
</display:column>
<display:column titleKey="miscellaneous.delete">
		<a href="miscellaneousRecord/brotherhood/delete.do?idMiscellaneous=${row.id}"><spring:message
				code="miscellaneous.delete" /></a>
</display:column>
</jstl:if>
</security:authorize>
</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not show}">
<a href="miscellaneousRecord/brotherhood/create.do"><spring:message
				code="miscellaneous.create" /></a>
</jstl:if>
</security:authorize>

<h2><spring:message code="history.link" /></h2>
<display:table name="linkRecord" id="row" requestURI="${requestURI}" pagesize="5"
	class="displaytag">
<display:column titleKey="link.show">
		<a href="linkRecord/show.do?idLink=${row.id}"><spring:message
				code="link.show" /></a>
</display:column>
<display:column titleKey="history.link.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column titleKey="history.link.description">
	<jstl:out value="${row.description}"/>
</display:column>
<display:column titleKey="history.link.brotherhood">
	<a href="brotherhood/personalBrotherhood.do?idBrotherhood=${row.brotherhood.id}">
	<jstl:out value="${row.brotherhood.name}"/>
	</a>
</display:column>
<security:authorize access="hasRole('BROTHERHOOD')">
<display:column titleKey="link.edit">
		<a href="linkRecord/brotherhood/update.do?idLink=${row.id}"><spring:message
				code="link.edit" /></a>
</display:column>
<display:column titleKey="link.delete">
		<a href="linkRecord/brotherhood/delete.do?idLink=${row.id}"><spring:message
				code="link.delete" /></a>
</display:column>
</security:authorize>
</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">
<a href="linkRecord/brotherhood/create.do"><spring:message
				code="link.create" /></a>
</security:authorize>
</jstl:if>