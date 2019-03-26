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
		<td>${marcadorNumerico.get("AVGBrotherhoodPerArea")}</td>
		<td>${minmaxArea.get("MinAreaBro")}</td>
		<td>${minmaxArea.get("MaxAreaBro")}</td>
		<td>${marcadorNumerico.get("stddevBrotherhoodPerArea")}</td>
	<tr> 
		<td><spring:message code="custom.enrolment" /></td>
		<td>${marcadorNumerico.get("AVGEnrolmentMember")}</td>
		<td>${marcadorNumericoArray.get("EnrolMemberMinMax")[0]}</td>
		<td>${marcadorNumericoArray.get("EnrolMemberMinMax")[1]}</td>
		<td>${marcadorNumerico.get("StandarDesviationEnrolmentMember")}</td>
	</tr>
<tr>
		<td><spring:message code="custom.finder"></spring:message>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[2]}</td>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[0]}</td>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[1]}</td>
		<td>${marcadorNumericoArray.get("NumberOfResultFinder")[3]}</td>
	<tr>
	<tr> 
		<td><spring:message code="custom.recorPerBro" /></td>
		<td>${marcadorNumericoArray.get("MinMaxAvgDttvOfRecordPerBro")[2]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgDttvOfRecordPerBro")[1]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgDttvOfRecordPerBro")[0]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgDttvOfRecordPerBro")[3]}</td>
	</tr>
	<tr> 
		<td><spring:message code="custom.paradePerChapter" /></td>
		<td>${marcadorNumericoArray.get("MinMaxAVGsttdvParadePerChapter")[0]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAVGsttdvParadePerChapter")[1]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAVGsttdvParadePerChapter")[2]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAVGsttdvParadePerChapter")[3]}</td>
	</tr>
	<tr> 
		<td><spring:message code="custom.minmaxSponsorshipSponsor" /></td>
		<td>${marcadorNumericoArray.get("MinMaxAvgSttdvSponsorshipsPerSponsor")[2]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgSttdvSponsorshipsPerSponsor")[1]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgSttdvSponsorshipsPerSponsor")[0]}</td>
		<td>${marcadorNumericoArray.get("MinMaxAvgSttdvSponsorshipsPerSponsor")[3]}</td>
	</tr>
</table>

<p>
<h3>
	<spring:message code="custom.Brotherhood" />
</h3>
<p>
<table border="1">
	<tr>
		<th><spring:message code="custom.largest" /></th>
		<th><spring:message code="custom.smaller" /></th>
	</tr>
	<tr>
		<td>${largestAndSmallerBro.get('LargestBro')}</td>
		<td>${largestAndSmallerBro.get('SmallerBro')}</td>
	</tr>
</table> 

<p>
<h3>
	<spring:message code="custom.BrotherhoodHistory" />
</h3>
<p>
<table border="1">
	<tr>
		<th><spring:message code="custom.largestHistory" /></th>
	</tr>
	<tr>
		<td>${largestBroHistory.get('LargestBro')}</td>
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
		<th><spring:message code="custom.ratioNoCoordinate" /></th>
		<th><spring:message code="custom.ratioActiveSponsorship" /></th>
	</tr>
	<tr>
		<td>${marcadorNumerico.get('AreaPerBrotherhoodRatio')}</td>
		<td>${marcadorNumerico.get('EmptyVsNotEmpty')}</td>
		<td>${marcadorNumerico.get('ratioAreaNoCoordinateChapter')}</td>
		<td>${marcadorNumerico.get('ratioActiveSponsorship')}</td>
	</tr>
</table> 

<p>
<h3>
	<spring:message code="custom.top" />
</h3>
</p>
<table border="1">
	<tr>
		<th><spring:message
				code="custom.top3Sponsor" /></th>
	</tr>
	<jstl:forEach
		items="${top3.get('top')}" var="actor">
		<tr>
			<th><jstl:out value="${actor}" />
		</tr>
	</jstl:forEach>
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
			<th><jstl:out value="${actor.title}" /></th>
		</tr>
	</jstl:forEach>
</table>

<table border="1">
	<tr>
		<th><spring:message
				code="custom.broMoreRecordThanAVG" /></th>
	</tr>
	<jstl:forEach
		items="${broMoreRecordThanAVG.get('BroMoreThanAVG')}"
		var="bro">
		<tr>
			<th><jstl:out value="${bro}" /></th>
		</tr>
	</jstl:forEach>
</table>

<p>
<h3>
		<th><spring:message
				code="custom.ratioRequestbystatus" /></th>
	</h3>
	<p>
<table border="1">
	<jstl:forEach
		items="${ratioStatus.get('RatioRequestToMarchByStatus')}"
		var="request">
		<tr>
			<th><jstl:out value="${request}" /></th>
		</tr>
	</jstl:forEach>
</table>

<p>
<h3>
		<th><spring:message
				code="custom.ratioRequest" /></th>
	</h3>
	<p>
<table border="1">
	<jstl:forEach
		items="${dashboardRatio.get('RatioRequestToMarchOnEachProcession')}"
		var="request">
		<tr>
			<th><jstl:out value="${request}" /></th>
		</tr>
	</jstl:forEach>
</table>

<p>
<h3>
		<th><spring:message
				code="custom.area" /></th>
	</h3>
	<p>
<table border="1">
	<jstl:forEach
		items="${countPerArea.get('AreaPerBrotherhood')}"
		var="request">
		<tr>
			<th><jstl:out value="${request}" /></th>
		</tr>
	</jstl:forEach>
</table>

<p>
<h3>
		<th><spring:message
				code="custom.chapter" /></th>
	</h3>
	<p>
<table border="1">
	<jstl:forEach
		items="${chapter10PorcentMoreParadeThanAVG.get('chapter')}"
		var="chapter">
		<tr>
			<th><jstl:out value="${chapter}" /></th>
		</tr>
	</jstl:forEach>
</table>

<p>
<h3>
	<spring:message code="custom.ratioParade" />
</h3>
<p>
<table border="1">
	<tr>
		<th><spring:message code="custom.paradeFinalMode" /></th>
		<th><spring:message code="custom.paradeNoFinalMode" /></th>
	</tr>
	<tr>
		<td>${marcadorNumericoArray.get('ParadeFinalModevsParadeNoFinalMode')[0]}</td>
		<td>${marcadorNumericoArray.get('ParadeFinalModevsParadeNoFinalMode')[1]}</td>
	</tr>
</table> 

<p>
<h3>
		<th><spring:message
				code="custom.paradeGroupByStatus" /></th>
	</h3>
	<p>
<table border="1">
	<jstl:forEach
		items="${paradesFinalModeGroupByStatus.get('parades')}"
		var="p">
		<tr>
			<th><jstl:out value="${p}" /></th>
		</tr>
	</jstl:forEach>
</table>



