function load(){
	$.ajax({
		url: 'rest/kategorije/',
		type: 'get',
		success: function(kategorije) {
			if(kategorije==null){
			}else {
				
				dodajKategorije(kategorije);	
			} 
		}
		
	});
	
	
}

$("document").ready(function(){

    $("input#imgSource").change(function(e) {
      //  alert('changed!');
        const image = e.target.files[0];
		const reader = new FileReader();
		reader.readAsDataURL(image);
		
		reader.onload = e => {
			$('img#sl').attr('src',  e.target.result);
		}
		
    });
    
    $("#submitlogin").click(function(event){
    	event.preventDefault();
    	$.ajax({
    		type: 'POST',
    		url: 'rest/oglasi/addoglas',
    		contentType : 'application/json',
    		dataType : "json",
    		data: formToJson(),
    		success: function(data){
    				alert("dodat oglas");
    				window.location.href="pregled.html"
    		}
    	});
    });
});

function dodajKategorije(kategorije){
	 var list = kategorije == null ? [] : (kategorije instanceof Array ? kategorije : [ kategorije ]);
		
	 $.each(kategorije, function(index, k) {
		 
		 if(k.aktivna===true){
			$("#kateg").append("<option>"+k.naziv+"</option>");
		 }
	 
	 });
}


function formToJson(){
	return JSON.stringify({
		"naziv" : $('#naziv').val(),
		"cijena" : $('#cijena').val(),
		"opis" : $('#opis').val(),
		"datumIsticanja"  : $('#datumIsticanja').val(),
		"grad" : $('#grad').val(),
		"imgSlika" : $('#sl').attr('src'),
		"uKategorijama" : $('#kateg').val(),
	});
}





