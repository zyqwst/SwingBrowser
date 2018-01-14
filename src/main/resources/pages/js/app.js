
var setting={}
setting.tableInit = function(str){
	console.log(str);
	var json = JSON.parse(str);
	$("#table-data").tmpl(json.jasperPrinters).appendTo('#printer-table'); 
}