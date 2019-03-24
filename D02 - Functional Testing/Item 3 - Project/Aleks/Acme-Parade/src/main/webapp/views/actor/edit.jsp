<%--
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<script>
	$(document).ready(
			function() {
				$("#form").submit(
						function(event) {
							var phone = $("#phone").val();
							var regex = new RegExp(
									"(((\\+){0,1}\\s{0,1}([1-9]|[1-9][0-9]|[1-9][0-9][0-9])){0,1}\\s{0,1}(\\(([1-9]|[1-9][0-9]|[1-9][0-9][0-9])\\)){0,1}\\s{0,1}([1-9]{4,9}$))",
									"m");

							if (regex.test(phone) == false) {
								if (confirm("<spring:message code = 'phone.confirm1' />")) {
									var res = confirm("<spring:message code = 'phone.confirm2' />");
									return res;
								} else {
									return false;
								}
								console.log(res);
							}

							var id = $("#id").val();

							if (id == '0') {
								var res = confirm("<spring:message code = 'terms' />");
								if (!res) { return false; }
							}

							if (phone.startsWith("+") == false) {
								var pref = "${prefix}";
								var fix = "+".concat(pref + " ", phone);
								$("#phone").val(fix);
							}
						});
			});
</script>
<form:form action="actor/edit.do" modelAttribute="actor" id="form">

	<form:hidden path="id" id="id" />
	<jstl:if test="${check eq 'true'}">
		<form:hidden path="authority" />
	</jstl:if>
	<acme:textbox code="actor.name" path="name" readonly="${not check}" />
	<acme:textbox code="actor.surname" path="surname"
		readonly="${not check}" />
	<acme:textbox code="actor.middlename" path="middleName"
		readonly="${not check}" />
	<acme:textbox code="actor.phone" path="phone" id="phone"
		readonly="${not check}" />
	<acme:textbox code="actor.email" path="email" readonly="${not check}" />
	<acme:textbox code="actor.adress" path="adress" readonly="${not check}" />
	<acme:textbox code="actor.photo" path="photo" readonly="${not check}" />

	<jstl:if test="${check eq 'true'}">
		<acme:textbox code="actor.account.username" path="account.username" />
		<acme:textbox code="actor.account.password" path="account.password" />
	</jstl:if>

	<jstl:if test="${check eq 'true'}">
		<acme:textbox code="actor.account.password2" path="password2" />
		<jstl:if test="${authority eq 'BROTHERHOOD'}">
			<acme:textbox code="actor.brotherhood.title" path="title" />
			<acme:textarea code="actor.brotherhood.pictures" path="pictures" />
		</jstl:if>
		<jstl:if test="${authority eq 'CHAPTER'}">
			<acme:textbox code="actor.brotherhood.title" path="title" />
		</jstl:if>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<spring:message code="actor.spam"></spring:message>: <jstl:out value="${spammer}"></jstl:out>
			 <a href="customisation/administrator/ban.do"><spring:message code="actor.spam.link"></spring:message> </a><br>
			<spring:message code="actor.polarity"></spring:message>: <jstl:out value="${polarity}"></jstl:out><br>
				
		</security:authorize>
				<security:authorize access="!hasRole('ADMINISTRATOR')">
		<input type="submit" name="save"
			value="<spring:message code="actor.save" />" />
			</security:authorize>
	</jstl:if>
</form:form>
<acme:cancel url="/welcome/index.do" code="actor.cancel" />