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
	if (lang == 'en') {
		var value = trim(String(row[i].getElementsByTagName("td")[0].firstChild.nodeValue.split("/")));
		console.log(value);
		value = new Date("20" + value[0] + "-" + value[1] + "-" + value[2].substring(0, 2));
	} else if (lang == 'es') {
		var value = trim(String(row[i].getElementsByTagName("td")[0].firstChild.nodeValue.split("-")));
		value = new Date("20" + value[2].substring(0, 2) + "-" + value[1] + "-" + value[0]);
	}

	// Creamos dos variables, una que guarde la fecha de hace un mes y otra que guarde la fecha de hace dos meses
	var beforeOneMonth = new Date();
	var beforeTwoMonths = new Date();

	if (actualDate.getMonth() + 1 == 1) {
		// Si el mes actual es enero, seteamos la fecha hace un mes, sumándole 11 meses y la fecha hace dos meses, sumándole 10 meses
		beforeOneMonth.setFullYear(actualDate.getFullYear() - 1, actualDate.getMonth() + 11, actualDate.getDate());
		beforeTwoMonths.setFullYear(actualDate.getFullYear() - 1, actualDate.getMonth() + 10, actualDate.getDate());

	} else if (actualDate.getMonth() + 1 == 2) {
		// Si el mes actual es febrero, seteamos la fecha hace dos meses, sumándole 10 meses
		beforeOneMonth.setFullYear(actualDate.getFullYear(), actualDate.getMonth() - 1, actualDate.getDate());
		beforeTwoMonths.setFullYear(actualDate.getFullYear() - 1, actualDate.getMonth() + 10, actualDate.getDate());

	} else {
		// Para el resto de fechas "no especiales", tan solo hay que restar 1 mes para obtener la fecha hace un mes y 2 para obtener la fecha hace dos meses
		beforeOneMonth.setFullYear(actualDate.getFullYear(), actualDate.getMonth() - 1, actualDate.getDate());
		beforeTwoMonths.setFullYear(actualDate.getFullYear(), actualDate.getMonth() - 2, actualDate.getDate());
	}

	// A continuación, colocamos las condiciones descritas utilizando las variables creadas anteriormente

	// Si la fecha de publicación tiene menos de un mes de antigüedad
	if (value <= actualDate && value > beforeOneMonth) {
		row[i].style.backgroundColor = "#8a2be2";
		// Si la fecha de publicación tiene entre uno (incluido) y dos meses (no incluido) de antigüedad
	} else if (value <= beforeOneMonth && value > beforeTwoMonths) {
		row[i].style.backgroundColor = "#708090";
		// Si la fecha de publicación tiene más de dos meses (incluido) de antigüedad
	} else {
		row[i].style.backgroundColor = "#d8bfd8";
	}
}
