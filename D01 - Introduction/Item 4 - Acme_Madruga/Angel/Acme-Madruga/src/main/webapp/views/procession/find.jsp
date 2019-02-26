<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="finder" action="${requestURI}">

	<acme:textbox code="finder.singleKey" path="singleWord" />
	<!-- Find all areas -->
	
	<acme:date code="finder.minDate" path="minimunDate" id="minDate" />
	<acme:date code="finder.maxDate" path="maximumDate" id="maxDate" />
	<acme:submit name="save" code="finder.save"/>
	<a href="fixuptask/handyworker/searchList.do?id=${finder.id}"><spring:message
			code="finder.previous" /></a>
</form:form>

<acme:cancel url="${requestCancel}" code="finder.cancel"/>