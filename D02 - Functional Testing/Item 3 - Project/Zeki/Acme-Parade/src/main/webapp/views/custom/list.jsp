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

<table border="1">
	<tr>
		<th></th>
		<th><spring:message code="custom.avg" /></th>
		<th><spring:message code="custom.min" /></th>
		<th><spring:message code="custom.max" /></th>
		<th><spring:message code="custom.stddev" /></th>
	</tr>
	<tr>
		<td><spring:message code="custom.area"></spring:message>
		<td>${marcadorNumerico.get("AVGBrotherhoodPerArea")}</td> <!-- [0] -->
		<td></td>
		<td></td>
		<td>${marcadorNumerico.get("stddevBrotherhoodPerArea")}</td>
	<tr>
		<td><spring:message code="custom.enrolment" /></td>
		<td>${marcadorNumerico.get("AVGEnrolmentMember")}</td>
		<td>${marcadorNumericoArray.get("EnrolMemberMinMax")[0]}</td>
		<td>${marcadorNumericoArray.get("EnrolMemberMinMax")[0]}</td>
		<td>${marcadorNumerico.get("StandarDesviationEnrolmentMember")}</td>
	</tr>
	<tr>
		<td><spring:message code="custom.finder"></spring:message>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[2]}</td> <!-- [0] -->
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[0]}</td>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[1]}</td>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[3]}</td>
	<tr>
</table>

<table border="1">
<tr>
		<th></th>
		<th><spring:message code="custom.area" /></th>
		<th><spring:message code="custom.numero" /></th>
</tr>
<tr>
		<td><spring:message code="custom.status" /></td>
		<td>${dashboardRatio2.get("RatioRequestToMarchByStatus")[1].get("RatioRequestToMarchByStatus")[0]}</td>
		<td>${dashboardRatio2.get("RatioRequestToMarchByStatus")[0].get("RatioRequestToMarchByStatus")[0]}</td>
</tr>
<tr>
		<td><spring:message code="custom.count" /></td>
		<td>${dashboardRatio2.get("CountPerArea")[0]}</td>
		<td>${dashboardRatio2.get("CountPerArea")[1]}</td>
</tr>
<tr>.
		<td><spring:message code="custom.minmax" /></td>
		<td>${dashboardRatio2.get("MinMaxPerArea")[0]}</td>
		<td>${dashboardRatio2.get("MinMaxPerArea")[1]}</td>
</tr>
	</table>


<p>
<h3>
	<spring:message code="custom.ratio" />
</h3>
<p>
<table border="1">
	<tr>
		<th><spring:message code="custom.area" /></th>
		<th><spring:message code="custom.vs" /></th>
		<th><spring:message code="custom.requestToMach" /></th>
	</tr>
	<tr>
		<td>${marcadorNumerico.get('AreaPerBrotherhoodRatio')}</td>
		<td>${marcadorNumerico.get('EmptyVsNotEmpty')}</td>
		<td>${dashboardRatio.get('RatioRequestToMarchOnEachProcession')}</td>
	</tr>
</table>





<p>
<h3>
	<spring:message code="custom.collection" />
</h3>
</p>
<table border="1">
	<tr>
		<th><spring:message
				code="custom.MemberMoreThan10PercentRequestAccepted" /></th>
	</tr>
	<jstl:forEach
		items="${dashboardActor.get('MemberMoreThan10PercentRequestAccepted')}" var="actor">
		<tr>
			<th><jstl:out value="${actor.name}" /> <jstl:out
					value="${actor.surname}" /> </br> <jstl:out value="${actor.email}" /></br>
		</tr>
	</jstl:forEach>
</table>

<table border="1">
	<tr>
		<th><spring:message
				code="custom.ProcessionsInLessThan30Days" /></th>
	</tr>
	<jstl:forEach
		items="${dashboardProcession.get('ProcessionsInLessThan30Days')}"
		var="actor">
		<tr>
			<th><jstl:out value="${actor.name}" /> <jstl:out
					value="${actor.surname}" /> </br> <jstl:out value="${actor.email}" /></br>
		</tr>
	</jstl:forEach>
</table>

