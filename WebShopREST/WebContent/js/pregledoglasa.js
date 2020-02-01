function onLoad(){
	$.ajax({
		url: 'rest/oglasi/',
		type: 'get',
		success: function(oglasi) {
			if(oglasi==null){
				alert('Nema oglasa, null');
			}else {
				ispisiOglase(oglasi);		
			} 
		}
		
	});
}

function ispisiOglase(oglasi){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	 var list = oglasi == null ? [] : (oglasi instanceof Array ? oglasi : [ oglasi ]);
		
	 $.each(oglasi, function(index, o) {
		 if(user.uloga==="Prodavac" && user.korisnicko===o.vlasnik){
			 ispisiOglas(o);	 
		 }else if(user.uloga==="Admininstrator"){
			 ispisiOglas(o);
		 }
		
	 });

	
}

function ispisiOglas(o){
	
	let naziv = $('<td>'+o.naziv+'</td>');
	let p = $('<td>'+o.vlasnik+'</td>');
	let cijena = $('<td>'+o.cijena+'</td>');
	let opis = $('<td>'+o.opis+'</td>');
	let brLajk = $('<td>'+o.brLajkova+'</td>');
	let brDis = $('<td>'+o.brDislajkova+'</td>');
	let datumPostavljanja = $('<td>'+o.prviDat+'</td>');
	let datumIsticanja = $('<td>'+o.drugiDat+'</td>');
	let grad = $('<td>'+o.grad+'</td>');
	let status = $('<td>'+o.status+'</td>');
	let slika = $('<td>'+o.imgSlika+'</td>');
	let recenzije = $('<td></td>');
	let s = $('<select></select>');
	let rec = o.listaRecenzija;
		for(r of rec){
			let li = $('<option>'+r.naslov+'</option>');
			s.append(li);
		}
	recenzije.append(s);
	let tdObrisi = $('<td></td>');
	if(o.daLiJeAktivam===true){
		let aObrisi = $('<a href="">Obrisi</a>');
		aObrisi.click(function(event){
			event.preventDefault();
			let id = o.naziv;
			$.ajax({
				url:'rest/oglasi/deloglas/'+id,
				type:'PUT',
				success: function(){
					$.ajax({
						url : 'rest/oglasi',
						type:'get',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiOglas(acc);
							}
						}
					});
				}
			});
		});
		tdObrisi.append(aObrisi);
	}else{
		let aAktiviraj = $('<a href="">Aktiviraj</a>');
		aAktiviraj.click(function(event){
			event.preventDefault();
			let id = o.naziv;
			$.ajax({
				url:'rest/oglasi/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/oglasi',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiOglas(acc);
							}
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}
	let tr = $('<tr></tr>');
	tr.append(naziv).append(p).append(cijena).append(opis).append(brLajk).append(brDis).append(datumPostavljanja).append(datumIsticanja).append(grad).append(status).append(slika).append(recenzije).append(tdObrisi);
	$('table#tbOglasi tbody').append(tr);
	if(o.daLiJeAktivam === true && o.status==="aktivan"){
		let opt = $('<option>'+o.naziv+'</option>');
		$('select#aktOglasi').append(opt);
	}
}
	
	
	function removeAll() {
		$('table#tbOglasi tbody tr').remove();
		$('select#aktOglasi option').remove();
	}
	

	$(document).ready(function(){
		
	    $("input#imgSource").change(function(e) {
	        //  alert('changed!');
	          const image = e.target.files[0];
	  		const reader = new FileReader();
	  		reader.readAsDataURL(image);
	  		
	  		reader.onload = e => {
	  			$('img#sl').attr('src',  e.target.result);
	  		}
	  		
	      });
	    
		$('button#sacuvaj').click(function(){
	
			
			let stariNaziv = $('select#aktOglasi').val();
			
		
				$.ajax({
					url: 'rest/oglasi/izmjeni/'+stariNaziv,
					type: 'PUT',
					contentType : 'application/json',
		    		dataType : "json",
		    		data: formToJson(),
					success: function(){
						$.get({
							url : 'rest/oglasi',
							success : function(accounts) {
								removeAll();
								for(acc of accounts){
									ispisiOglas(acc);
								}
							}
						});
					}
				});
			
		});
	});
	
	function formToJson(){
		return JSON.stringify({
			"naziv" : $('#naziv').val(),
			"cijena" : $('#cijena').val(),
			"opis" : $('#opis').val(),
			"datumIsticanja"  : $('#datumIst').val(),
			"grad" : $('#grad').val(),
			"imgSlika" : $('#sl').attr('src'),
			
		});
	} 

