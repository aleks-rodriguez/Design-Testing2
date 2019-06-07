<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="item">



	
	<form:hidden path="id"/>


	<acme:textbox code="item.name" path="name"
		readonly="${view}" />
	<acme:textbox code="item.description" path="description"
		readonly="${view}" />
	<acme:textarea code="item.urls" path="urls"
		readonly="${view}" />
	<acme:textarea code="item.pictures" path="pictures"
		readonly="${view}" />
		
	<jstl:if test="${not view}">
		<acme:submit name="save" code="item.save" />
	</jstl:if>


	
</form:form>

<acme:cancel url="${requestCancel}" code="item.cancel" />