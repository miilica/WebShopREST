function load(){
	$.ajax({
		url: 'rest/oglasi/',
		type: 'get',
		success: function(ogl) {
			if(ogl==null){
			}else {
				
				dodajOgl(ogl);	
			} 
		}
		
	});
}

function dodajOgl(ogl){
	 var list = ogl == null ? [] : (ogl instanceof Array ? ogl : [ ogl ]);
		
	 $.each(ogl, function(index, k) {
		 
		 if(k.status==="dostavljen"){
			$("#oglasi").append("<option>"+k.naziv+"</option>");
			//alert('oglas');
		 }
	 
	 });
}
$(document).ready(function(){
	$("#submitRecenziju").click(function(e){
		e.preventDefault();
		var imeOglasa = $('#oglasi').val();
		$.ajax({
			type: 'post',
			url: 'rest/recenzije/add/'+imeOglasa,
			contentType: 'application/json',
			dataType: 'json',
			data: formToJson(),
			success: function(data){
				if(data==null){
					alert("data null");
				}else{
					window.location.href="pregled.html"
					alert("dodata recenzija"+data.naslov+data.oglas+data.sadrzaj);
				}
			}
		});
	});
	$("input#imgSource").change(function(e) {
	    //  alert('changed!');
	      const image = e.target.files[0];
			const reader = new FileReader();
			reader.readAsDataURL(image);
			
			reader.onload = e => {
				$('img#sl').attr('src',  e.target.result);
			}
			
	  });
});
function formToJson(){

	return JSON.stringify({
		"oglas" : $('#oglasi').val(),
		"naslov" : $('#naslov').val(),
		"sadrzaj" : $('#sadrzaj').val(),
		"slika" :  $('#sl').attr('src'),
		"daLiJeOpisTacan" : $('#tacan').val(),
		"daLiJeDogIspostovan" : $('#ispostovan').val(),		
		
	});
}

