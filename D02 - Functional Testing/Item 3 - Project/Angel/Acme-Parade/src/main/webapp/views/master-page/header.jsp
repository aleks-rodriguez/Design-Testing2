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
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.custom" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customisation/administrator/custom.do"><spring:message
								code="master.page.administrator.custom" /></a></li>
					<li><a href="customisation/administrator/noenabled.do"><spring:message
								code="master.page.administrator.noenabled" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customisation/administrator/dashboard.do"><spring:message
								code="master.page.dashboard" /></a></li>
					<li><a href="customisation/administrator/chart.do"><spring:message
								code="master.page.administrator.chart" /></a></li>
				</ul></li>

			<li><a class="fNiv" href="actor/createAdmin.do"><spring:message
						code="master.page.actor.admin" /></a></li>
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

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message
						code="master.page.procession" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="parade/brotherhood/list.do?idBrotherhood=0"><spring:message
								code="master.page.procession.list" /></a></li>
					<li><a href="parade/brotherhood/create.do"><spring:message
								code="master.page.procession.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message code="master.page.float" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="float/list.do"><spring:message
								code="master.page.float.list" /></a></li>
					<li><a href="float/brotherhood/create.do"><spring:message
								code="master.page.float.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.enrolment" /></a>
				<ul>
					<li><a href="enrolment/brotherhood/list.do"><spring:message
								code="master.page.enrolment.list" /></a></li>
					<li><a href="enrolment/brotherhood/listMember.do"><spring:message
								code="master.page.member.list" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="area/brotherhood/list.do"><spring:message
						code="master.page.brotherhood.area" /></a></li>
			<li><a class="fNiv" href="history/brotherhood/list.do"><spring:message
						code="master.page.brotherhood.history" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('MEMBER')">

			<li><a class="fNiv" href="procession/member/search.do"><spring:message
						code="master.page.finder" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/member/listBrotherhood.do"><spring:message
								code="master.page.brotherhood.list" /></a></li>
					<li><a href="enrolment/member/listOwn.do"><spring:message
								code="master.page.brotherhood.listOwn" /></a></li>
					<li><a href="enrolment/member/listDelete.do"><spring:message
								code="master.page.brotherhood.listDelete" /></a></li>
				</ul>
		</security:authorize>

		<security:authorize access="hasRole('CHAPTER')">
			<li><a class="fNiv" href="area/chapter/list.do"><spring:message
						code="master.page.brotherhood.area" /></a></li>
			<li><a class="fNiv" href="area/chapter/listBrotherhood.do"><spring:message
						code="master.page.brotherhood.listOwn" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.proclaims" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="proclaim/chapter/list.do"><spring:message
								code="master.page.proclaim.list" /></a></li>
					<li><a href="proclaim/chapter/create.do"><spring:message
								code="master.page.proclaim.create" /></a></li>
				</ul></li>
		</security:authorize>


		<!-- Anonymous -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.create" /></a>

				<ul>
					<li class="arrow"></li>
					<li><a href="actor/createMember.do"><spring:message
								code="master.page.actor.member" /></a></li>
					<li><a href="actor/createBrotherhood.do"><spring:message
								code="master.page.actor.brotherhood" /></a></li>
					<li><a href="actor/createChapter.do"><spring:message
								code="master.page.actor.chapter" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="brotherhood/listBrotherhood.do"><spring:message
						code="master.page.brotherhood.list" /></a></li>
			<li><a class="fNiv" href="chapter/listChapter.do"><spring:message
						code="master.page.chapters" /></a></li>
			<li><a class="fNiv" href="about-us/terms.do"><spring:message
						code="master.page.terms" /></a></li>
		</security:authorize>

		<!-- Authenticated -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message
						code="master.page.create" /></a>

				<ul>
					<li class="arrow"></li>
					<li><a href="actor/createMember.do"><spring:message
								code="master.page.actor.member" /></a></li>
					<li><a href="actor/createBrotherhood.do"><spring:message
								code="master.page.actor.brotherhood" /></a></li>
					<li><a href="actor/createChapter.do"><spring:message
								code="master.page.actor.chapter" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="brotherhood/listBrotherhood.do"><spring:message
						code="master.page.brotherhood.list" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/personal.do"><spring:message
									code="master.page.actor.personal" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('BROTHERHOOD')">
						<li><a href="brotherhood/personal.do"><spring:message
									code="master.page.actor.personal" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MEMBER')">
						<li><a href="member/personal.do"><spring:message
									code="master.page.actor.personal" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('CHAPTER')">
						<li><a href="chapter/personal.do"><spring:message
									code="master.page.actor.personal" /></a></li>
					</security:authorize>
					<li><a href="box/list.do"><spring:message
								code="master.page.boxes" /></a></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.message.create" /></a></li>
					<li><a href="profile/list.do"><spring:message
								code="master.page.profile.list" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			<li><a class="fNiv" href="about-us/terms.do"><spring:message
						code="master.page.terms" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

