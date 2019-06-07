

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="providers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column titleKey="provider.name">
		<jstl:out value="${row.name}" />
	</display:column>

	<display:column titleKey="provider.surname">
		<jstl:out value="${row.surname}" />
	</display:column>

	<display:column titleKey="provider.phone">
		<a href="tel:${row.phone}"><jstl:out value="${row.phone}" /></a>
	</display:column>

	<display:column titleKey="provider.email">
		<a href="mailto:${row.email}"><jstl:out value="${row.email}" /></a>
	</display:column>

	<display:column titleKey="provider.make">
		<jstl:out value="${row.make}" />
	</display:column>

	<display:column titleKey="provider.vat">
		<jstl:out value="${row.vat}" />
	</display:column>

	<display:column titleKey="provider.adress">
		<jstl:out value="${row.adress}" />
	</display:column>

	
	<display:column titleKey="provider.items">
		<a href="item/listMyItems.do?provider=${row.id}"><spring:message
				code="provider.items" /></a>
	</display:column>

</display:table>

