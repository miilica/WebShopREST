function load(){
	
	$.ajax({
		url: 'rest/oglasi/',
		type: 'get',
		success: function(oglasi) {
			if(oglasi==null){
			}else {
				
				dodajOglase(oglasi);	
			} 
		}
		
	});
	
	
}

function dodajOglase(oglasi){
	 var list = oglasi == null ? [] : (oglasi instanceof Array ? oglasi : [ oglasi ]);
		
	 $.each(oglasi, function(index, k) {
		 
		 if(k.daLiJeAktivam===true){
			$("#oglas").append("<option>"+k.naziv+"</option>");
		 }
	 
	 });
}

$(document).on('submit', '.poruka', function(e){
	e.preventDefault();
	let id = $('#oglas').val();
	$.ajax({
		type: 'post',
		contentType: 'application/json',
		url: 'rest/poruke/addporuku/'+id,
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
		"naziv_oglasa" : $('#oglas').val(),
		"naslov" : $('#naslov').val(),
		"sadrzaj" : $('#sad').val(),
	});
}
