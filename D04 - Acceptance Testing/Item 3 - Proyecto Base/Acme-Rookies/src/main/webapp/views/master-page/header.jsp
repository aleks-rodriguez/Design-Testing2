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
	<a href="#"><img src="${banner}" alt="${systemName}" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Admin -->
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
		<li><a class="fNiv"><spring:message
						code="master.page.administrator.listActor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/listSpammers.do"><spring:message
								code="master.page.administrator.listSpammers" /></a></li>
					<li><a href="actor/listBan.do"><spring:message
								code="master.page.administrator.listEnabled" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.custom" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customisation/administrator/custom.do"><spring:message
								code="master.page.administrator.customAction" /></a></li>
				</ul></li>
			<li><a href="customisation/administrator/dashboard.do"><spring:message
								code="master.page.dashboard" /></a></li>
			<li><a href="actor/createAdmin.do"><spring:message
						code="master.page.actor.admin" /></a></li>

				
		</security:authorize>

		<!-- Company -->
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message
						code="master.page.position" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/list.do"><spring:message
								code="master.page.position.list" /></a></li>
					<li><a href="position/company/create.do"><spring:message
								code="master.page.position.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.problem" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="problem/company/list.do"><spring:message
								code="master.page.company.problem.list" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- Rookie -->

		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv" href="curricula/rookie/list.do"><spring:message
						code="master.page.rookie.curricula" /></a></li>
									<li><a class="fNiv"><spring:message
						code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/rookie/list.do"><spring:message
								code="master.page.application.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		

		<li><a href="actor/createRookie.do"><spring:message
			code="master.page.actor.rookie" /></a></li>
		<li><a href="actor/createCompany.do"><spring:message
			code="master.page.actor.company" /></a></li>
			</security:authorize>
		<li><a class="fNiv" href="position/finder.do"><spring:message
					code="master.page.position.finder" /></a></li>
		<li><a class="fNiv" href="position/listPositions.do"><spring:message
					code="master.page.position.listPublic" /></a></li>
		<li><a class="fNiv" href="position/listCompanies.do"><spring:message
					code="master.page.company.listPublic" /></a></li>
		<li><a class="fNiv" href="about-us/terms.do"><spring:message
					code="master.page.terms" /></a></li>
					
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/list.do"><spring:message
								code="master.page.profile.list" /></a></li>
					<li><a href="actor/personal.do"><spring:message
								code="master.page.edit.data" /></a></li>

					<li><a href="box/list.do"><spring:message
								code="master.page.boxes" /></a></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.message.create" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

