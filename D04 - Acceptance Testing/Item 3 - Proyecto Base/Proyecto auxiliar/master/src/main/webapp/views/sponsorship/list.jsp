

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="sponsorships" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

</display:table>

	<display:column titleKey="sponsorship.banner">
		<img src="${row.banner}" height="50" width="75" />
	</display:column>
	
	<display:column titleKey="sponsorship.target">
		<a href="${row.target}">${row.target}</a>
	</display:column>
	
	<display:column titleKey="sponsorship.position">
		<jstl:out value="${row.position.title}" />
		
	</display:column>
	
		<display:column>
				<a href="sponsorship/provider/show.do?idSponsorship=${row.id}"><spring:message
						code="sponsorship.showmore" /></a>
	
	</display:column>
	
	<display:column>
				<a href="sponsorship/provider/update.do?idSponsorship=${row.id}"><spring:message
						code="sponsorship.edit" /></a>
	
	</display:column>
	
	<jstl:if test="${isActive}">
	<display:column>
				<a href="sponsorship/provider/desactive.do?idSponsorship=${row.id}"><spring:message
						code="sponsorship.desactive" /></a>
	
	</display:column>
	

		</jstl:if>
		
	<jstl:if test="${isNotActive}">
			<display:column>
				<a href="sponsorship/provider/reactive.do?idSponsorship=${row.id}"><spring:message
						code="sponsorship.reactive" /></a>
			</display:column>
	</jstl:if>
