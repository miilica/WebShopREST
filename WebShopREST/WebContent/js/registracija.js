
$(document).on('submit','.registracija',function(e){
	e.preventDefault();

	var dobro = true;
	var telefonEl = document.getElementById("telefon");
	var telefon = telefonEl.value;
	telefonEl.style.border = "2px solid #008080";
	document.getElementById("labelaTelefon").innerHTML="";
	
	var prezimeEl = document.getElementById("prezime");
	var prezime = prezimeEl.value;
	prezimeEl.style.border = "2px solid #008080";
	document.getElementById("labelaPrezime").innerHTML="";

	
	 var pomocna  = prezime.toUpperCase();
	 if(pomocna.charAt(0)!=prezime.charAt(0)){
		 	prezimeEl.style.border = "1px solid red";
			document.getElementById("labelaPrezime").innerHTML="Prvo slovo mora biti veliko.";
			dobro = false;
			
	 }
	var imeEl = document.getElementById("ime");
	var ime = imeEl.value;
	imeEl.style.border = "2px solid #008080";
	document.getElementById("labelaIme").innerHTML="";

	var pomocna1  = ime.toUpperCase();
	 if(pomocna1.charAt(0)!=ime.charAt(0)){
		 imeEl.style.border = "1px solid red";
			document.getElementById("labelaIme").innerHTML="Prvo slovo mora biti veliko.";
			dobro = false;
				
	 }
	
	if(telefon < 1){
		telefonEl.style.border = "1px solid red";
		document.getElementById("labelaTelefon").innerHTML="Pogresan broj.";
		dobro = false;
		
	}
	
	if(dobro == true){
		$.ajax({
			type : 'POST',
			url : "rest/korisnici/reg",
			contentType : 'application/json',
			data: JSON.stringify({
				"korisnicko" : $('#korisnicko').val(),
				"password" : $('#lozinka').val(),
				"ime" : $('#ime').val(),
				"prezime" : $('#prezime').val(),
				"uloga" : "",
				"telefon" : $('#telefon').val(),
				"grad" : $('#grad').val(),
				"mail" : $('#mail').val(),
				"datum" : ""
			}),
			success : function(data) {
				if(data.prezime=="ime"){
					alert("Korisnicko ime je zauzeto");
				
				}else if(data.prezime == "adresa"){
					alert("E-mail adresa je zauzeta");
					
					
				}else if(data.prezime == "telefon"){
					alert("Neispravan broj telefona");
					
					
				}else{
					window.location.href = "login.html";
				}
			},
			error : function(message) {
				alert(message);
			}
		});
	}
});