<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="educationData">

	<form:hidden path="id" />
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="today" value="${now}" pattern="yyyy/MM/dd" />
	
	<acme:textbox code="eduData.degree" path="degree" readonly="${view}" />
	<acme:textbox code="eduData.institution" path="institution"
		readonly="${view}" />
	<acme:textbox code="eduData.mark" path="mark" readonly="${view}" />
	<acme:date code="eduData.startDate" path="startDate" id="startDate"
		readonly="${view}" />
	<acme:date code="eduData.endDate" path="endDate" id="endDate"
		readonly="${view}" />

	<jstl:if test="${!view}">
		<acme:submit name="save" code="eduData.save" />
		<jstl:if test="${educationData.id != 0}">
			<acme:submit name="delete" code="eduData.delete" />
		</jstl:if>

	</jstl:if>
</form:form>

<acme:cancel url="${requestCancel}" code="eduData.cancel" />