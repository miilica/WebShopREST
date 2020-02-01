function load(){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	console.log(user.korisnicko);
	$.ajax({
		url: 'rest/oglasi/odgovori/'+user.korisnicko,
		type: 'get',
		success: function(oglasi) {
			if(oglasi==null){
			}else {
				for(r of oglasi){
					$("#ogl").append("<option>"+r+"</option>");	
				}
				
			} 
		}
		
	});
	
	
}


$(document).on('submit', '.poruka', function(e){
	e.preventDefault();
	let ogl = $('#ogl').val();
	$.ajax({
		type: 'post',
		url: 'rest/poruke/addporuku/'+ogl,
		contentType: 'application/json',
		dataType: 'json',
		data: formToJson(),
		success: function(data){
			if(data==null){
				alert("data null");
			}else{
				window.location.href="pregled.html"
				alert("dodata poruka");
			}
		}
	});
});

function formToJson(){
	return JSON.stringify({
		"naziv_oglasa" : $('#ogl').val(),
		"naslov" : $('#naslov').val(),
		"sadrzaj" : $('#sad').val(),
	});
}
