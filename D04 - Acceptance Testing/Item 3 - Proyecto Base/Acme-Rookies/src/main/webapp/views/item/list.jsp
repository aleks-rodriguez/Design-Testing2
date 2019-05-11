

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="items" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
		
		<display:column titleKey="item.provider">
		<a href="item/showProvider.do?item=${row.id}"><spring:message
				code="item.provider" /></a>
		</display:column>
		
		
		<display:column titleKey="item.name">
		<jstl:out value="${row.name}" />
		</display:column>
		
		<display:column titleKey="item.description">
		<jstl:out value="${row.description}" />
		</display:column>
		
		<display:column titleKey="item.urls">
		<jstl:out value="${row.urls}" />
		</display:column>
		
		<display:column titleKey="item.pictures">
		<jstl:out value="${row.pictures}" />
		</display:column>
		
		<display:column titleKey="item.showMore">
		<a href="item/show.do?item=${row.id}"><spring:message
				code="item.showMore" /></a>
		</display:column>
	
		<jstl:if test="${provider}">	
		<display:column titleKey="item.delete">
		<a href="item/delete.do?id=${row.id}"><spring:message
				code="item.delete" /></a>
		</display:column>
		</jstl:if>
		
		<jstl:if test="${provider}">	
		<display:column titleKey="item.update">
		<a href="item/update.do?id=${row.id}"><spring:message
				code="item.update" /></a>
		</display:column>
		</jstl:if>

</display:table>

