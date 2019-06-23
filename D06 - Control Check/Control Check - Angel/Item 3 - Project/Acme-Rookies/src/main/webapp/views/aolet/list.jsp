

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="aolets" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="aolet.ticker">
		<jstl:out value="${row.ticker.ticker}" />
	</display:column>

	<display:column titleKey="aolet.title">
		<jstl:out value="${row.title}" />
	</display:column>

	<display:column titleKey="aolet.description">
		<jstl:out value="${row.description}" />
		<jstl:out value="${row.moment}" />
	</display:column>

	<display:column titleKey="aolet.moment">
		<jstl:if test="${lang eq 'en'}">
			<fmt:formatDate value="${row.moment}" pattern="yy/MM/dd" />
		</jstl:if>
		<jstl:if test="${lang eq 'es'}">
			<fmt:formatDate value="${row.moment}" pattern="dd-MM-yyyy" />
		</jstl:if>
	</display:column>
	<display:column titleKey="aolet.image">
		<img src="${row.image}" alt="${row.image}" height="80" width="100" />
	</display:column>

	<display:column titleKey="aolet.show">
		<a href="aolet/show.do?id=${row.id}"><spring:message
				code="aolet.show" /></a>
	</display:column>
	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="aolet.edit">
			<jstl:if test="${!row.finalMode}">
				<a href="aolet/auditor/edit.do?id=${row.id}"><spring:message
						code="aolet.edit" /></a>
			</jstl:if>
		</display:column>
		<display:column titleKey="aolet.delete">
			<jstl:if test="${!row.finalMode}">
				<a href="aolet/auditor/delete.do?id=${row.id}"><spring:message
						code="aolet.delete" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<script>
	
	function trim(cadena) {
		// USO: Devuelve un string como el
		// parametro cadena pero quitando los
		// espacios en blanco de los bordes.

		var retorno = cadena.replace(/^\s+/g, '');
		retorno = retorno.replace(/\s+$/g, '');
		return retorno;
	}
	
  		var table = document.getElementById("row");
 		var tbody = table.getElementsByTagName("tbody")[0];
  		var row = tbody.getElementsByTagName("tr");
 		// Guardamos la fecha actual
  		var actualDate = new Date();
  
  		// Llamamos a la variable lang del controlador
  		var lang = '${lang}';

  		for (i = 0; i < row.length; i++) {
			// Según el idioma, parseamos una fecha u otra, pues cada una posee un patrón único
    			if(lang == 'en') {
      				var value = trim(String(row[i].getElementsByTagName("td")[3].firstChild.nodeValue.split("/")));
      				console.log(value);
      				value = new Date("20"+value[0]+"-"+value[1]+"-"+value[2].substring(0,2));
    			}else if(lang == 'es') {
      				var value = trim(String(row[i].getElementsByTagName("td")[3].firstChild.nodeValue.split("-")));
      				value = new Date("20"+value[2].substring(0,2)+"-"+value[1]+"-"+value[0]);
    		}
	
	// Creamos dos variables, una que guarde la fecha de hace un mes y otra que guarde la fecha de hace dos meses
     		var beforeOneMonth = new Date();
    		var beforeTwoMonths = new Date();
     
     		if(actualDate.getMonth()+1 == 1) {
    	 	// Si el mes actual es enero, seteamos la fecha hace un mes, sumándole 11 meses y la fecha hace dos meses, sumándole 10 meses
    			beforeOneMonth.setFullYear(actualDate.getFullYear()-1, actualDate.getMonth()+11, actualDate.getDate());
    	 		beforeTwoMonths.setFullYear(actualDate.getFullYear()-1, actualDate.getMonth()+10, actualDate.getDate());
     
     		}else if(actualDate.getMonth()+1 == 2){
    	 	// Si el mes actual es febrero, seteamos la fecha hace dos meses, sumándole 10 meses
    			beforeOneMonth.setFullYear(actualDate.getFullYear(), actualDate.getMonth()-1, actualDate.getDate());
    	 		beforeTwoMonths.setFullYear(actualDate.getFullYear()-1, actualDate.getMonth()+10, actualDate.getDate());
     
    	 	}else{
    	 	// Para el resto de fechas "no especiales", tan solo hay que restar 1 mes para obtener la fecha hace un mes y 2 para obtener la fecha hace dos meses
    	 		beforeOneMonth.setFullYear(actualDate.getFullYear(), actualDate.getMonth()-1, actualDate.getDate());
    	 		beforeTwoMonths.setFullYear(actualDate.getFullYear(), actualDate.getMonth()-2, actualDate.getDate());
    		}

     // A continuación, colocamos las condiciones descritas utilizando las variables creadas anteriormente
     
     // Si la fecha de publicación tiene menos de un mes de antigüedad
     	if(value <= actualDate && value > beforeOneMonth) {
     		row[i].style.backgroundColor = "#3F00FF";
     // Si la fecha de publicación tiene entre uno (incluido) y dos meses (no incluido) de antigüedad
     	}else if (value <= beforeOneMonth && value > beforeTwoMonths) {
     		row[i].style.backgroundColor = "#2f4f4f";
     // Si la fecha de publicación tiene más de dos meses (incluido) de antigüedad
     	}else{
     		row[i].style.backgroundColor = "#ffefd5";
     }
  }
</script>