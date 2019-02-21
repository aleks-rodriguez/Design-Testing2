<%--
 * action-2.jsp
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

<form:form action="profile/edit.do" modelAttribute="profile">
<form:hidden path="id"/>
<form:hidden path="version"/>

<form:label path="nick">
<spring:message code="user.nick"></spring:message> 
</form:label>
<form:input path="nick"/>
<form:errors cssClass="error" path="nick"></form:errors>
<br>

<form:label path="socialNetworkName">
<spring:message code="user.socialNetworkName"></spring:message>
</form:label>
<form:input path="socialNetworkName"/>
<form:errors cssClass="error" path="socialNetworkName"></form:errors>
<br>

<form:label path="link">
<spring:message code="user.link"></spring:message> 
</form:label>
<form:input path="link" placeholder="http://....." />
<form:errors cssClass="error" path="link"></form:errors>
<br>


	<input type="submit" name="save"
		value="<spring:message code="user.save"/>" />
</form:form>
 
<input type="button" name="cancel"
	value="<spring:message code="user.cancel"/>"
	onclick="javascript:relativeRedir('/welcome/index.do');" />

