<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="${systemName}" height="200"
		width="350" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<!-- Administrator -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="customisation/administrator/custom.do"><spring:message
						code="master.page.administrator.custom" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.positions" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/administrator/list.do"><spring:message
								code="master.page.position.list" /></a></li>
					<li><a href="position/administrator/create.do"><spring:message
								code="master.page.position.create" /> </a></li>
				</ul></li>
		</security:authorize>
		<!-- Member -->
		<security:authorize access="hasRole('MEMBER')">

			<li><a class="fNiv" href="procession/member/finder.do"><spring:message
						code="master.page.finder" /></a></li>
		</security:authorize>
		<!-- Brotherhood -->
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv" href="procession/brotherhood/create.do"><spring:message
						code="master.page.procession.create" /></a></li>
		</security:authorize>
		<!-- Anonymous -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="about-us/terms.do"><spring:message
						code="master.page.terms" /></a></li>
			<li><a class="fNiv" href="procession/list.do"><spring:message
						code="master.page.procession.list" /></a></li>
			<li><a class="fNiv" href="float/list.do"><spring:message
						code="master.page.float.list" /></a></li>
		</security:authorize>

		<!-- Authenticated -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/list.do"><spring:message
								code="master.page.profile.list" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			<li><a class="fNiv" href="procession/list.do"><spring:message
						code="master.page.procession.list" /></a></li>
				
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

