$(document).on('submit','.kategorija',function(e){
	e.preventDefault();
	$.ajax({
		type : 'POST',
		url : "rest/kategorije/addkategoriju",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			if(data==null){
				
			}else {
					
					window.location.href = "pregled.html";
					
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			   
		}
	});
});

function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#naziv').val(),
		"opis" : $('#opis').val(),
	});
}