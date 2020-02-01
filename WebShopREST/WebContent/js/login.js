$(document).on('submit','.logovanje',function(e){
	e.preventDefault();
	$.ajax({
		type : 'POST',
		url : "rest/korisnici/uloguj",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			if(data!=null) {
				
				sessionStorage.setItem('korisnik',JSON.stringify(data));
				window.location.href = 'pregled.html';
			}else {
				alert("Niste ukucali ispravno korisnicko ime ili lozinku");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});	
});

function formToJSON() {
	return JSON.stringify({
		"korisnicko":$('#korisnicko').val(),
		"lozinka":$('#lozinka').val()
	});
}
