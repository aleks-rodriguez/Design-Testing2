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
								} else {
									return false;
								}
							}
							/* if (phone.startsWith("+") == false) {
								var pref = "${prefix}";
								var res = "+".concat(pref + " ", phone);
								$("#phone").val(res);
							} */
						});
			});
</script>
<form:form action="actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="account.authorities" />

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.middleName" path="middleName" />
	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.adress" path="adress" />
	<acme:textbox code="actor.photo" path="photo" />
	<acme:textbox code="actor.account.username"
		path="actor.account.username" />
	<acme:textbox code="actor.account.password"
		path="actor.account.password" />
	<jstl:if test="${account.authorities eq 'BROTHERHOOD'}">
		<acme:textbox code="actor.brotherhood.title" path="title" />
		<acme:textarea code="actor.brotherhood.pictures" path="pictures" />
	</jstl:if>
	<acme:submit name="save" code="actor.save" />
</form:form>
<acme:cancel url="/welcome/index.do" code="actor.cancel"/>