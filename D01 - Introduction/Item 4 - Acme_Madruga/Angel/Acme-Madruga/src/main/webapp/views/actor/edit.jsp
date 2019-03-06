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
								return res;
							} else {
								return false;
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
	<form:hidden path="authority" />

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.middlename" path="middleName" />
	<acme:textbox code="actor.phone" path="phone" id="phone" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.adress" path="adress" />
	<acme:textbox code="actor.photo" path="photo" />
	<acme:textbox code="actor.account.username" path="account.username" />
	<acme:textbox code="actor.account.password" path="account.password" />
	<acme:textbox code="actor.account.password2" path="password2" />

	<jstl:if test="${authority eq 'BROTHERHOOD'}">
		<acme:textbox code="actor.brotherhood.title" path="title" />
		<acme:textarea code="actor.brotherhood.pictures" path="pictures" />
	</jstl:if>
	<acme:submit name="save" code="actor.save" />
</form:form>
<acme:cancel url="/welcome/index.do" code="actor.cancel" />