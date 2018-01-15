
var setting={}
setting.tableInit = function(str){
	var json = JSON.parse(str);
	$("#table-data").tmpl(json.jasperPrinters).appendTo('#printer-table'); 
}