function load(){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	if(user==null){
		$('#status').hide();
	}
	$.ajax({
		url: 'rest/oglasi/getGradove',
		type: 'get',
		success: function(gradovi) {
			if(gradovi==null){
			}else {
				for(g of gradovi){
					//alert(g);
					let opt = $('<option>'+g+'</option>');
					$("#grad").append(opt);
				}
			} 
		}
		
	});
	
	$.ajax({
		url: 'rest/korisnici/getGradove',
		type: 'get',
		success: function(gradovi) {
			if(gradovi==null){
			}else {
				for(g of gradovi){
					let opt = $('<option>'+g+'</option>');
				//	alert(g);
					$("#gradKor").append(opt);
				}
			} 
		}
		
	});
}

$("document").ready(function(){
	 $("input#submitPretragu1").on("click", function(e){
		 e.preventDefault();
		 $('table#oglasi tbody td').remove();
		// $("#oglasi").load(pregled.html.href+" #oglasi>*","");	
			var korisnik = sessionStorage.getItem("korisnik");
			var user = JSON.parse(korisnik);
			
		 	let naziv = $('#naziv').val();
			let minCijena = $('#cijenaMin').val();
			let maxCijena =  $('#cijenaMax').val();
			let minOcjena =  $('#ocjenaMin').val();
			let maxOcjena =  $('#ocjenaMax').val();
			let minDatum =  new Date( $('#datumMin').val()).getTime();
			let maxDatum = new Date($('#datumMax').val()).getTime();
			
			let grad =  $('#grad').val();
			let status = $('#status').val();
			

				$.ajax({
					type : 'GET',
					url : "rest/oglasi/pretraziOglase?ime="+naziv+"&minc="+minCijena+"&maxc="+maxCijena+"&mino="+minOcjena+"&maxo="+maxOcjena+"&mind="+minDatum+"&maxd="+maxDatum+"&grad="+grad+"&status="+status,
					success : function(data) {
						if(data==null){
							alert("Ne postoji oglas sa zeljenim karakteristikama.");
						}else {
							//alert("Postoje oglasi");
							$('table#oglasi tbody td').remove();
							for(r of data){
								odstampajOglas(r);
						}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						   
					}
				});
			});
	 

			
	 
	 $("input#submitPretragu2").on("click", function(e){
		 e.preventDefault();
		 $('table#oglasi tbody td').remove();
		// $("#oglasi").load(pregled.html.href+" #oglasi>*","");	
		 let nazivv = $('#imeKOr').val();
		 let gradd =  $('#gradKor').val();
		 $.get({
				url : 'rest/korisnici/pretraziKorisnike?ime='+nazivv+'&grad='+gradd,
				success : function(data){
					if(data==null){
						alert("Ne postoji korisnik sa zeljenim karakteristikama.");
					}else {
						//alert("Postoje korisnici");
						$('table#oglasi tbody td').remove();
						for(r of data){
							ispisiKorisnika(r);
					}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					}
	    		}		
				
			});
	 });
});



function ispisiKorisnika(u){

	let tr = $('<tr></tr>');
	let tr2 = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>'+u.korisnicko+'</p>');
	let price = $('<p>Cijena: '+u.ime+' </p>');
	let desc = $('<p>Opis: '+u.prezime+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>Like:'+u.uloga+'</label>');
	let neg = $('<label>Dislike: '+u.mail+'</label> ');
	let negss = $('<label>Dislike: '+u.grad+'</label> ');
	td3.append(poz).append(neg).append(negss);
		tr.append(td2);
		tr2.append(td3);
		$('table#oglasi tbody').append(tr);
		$('table#oglasi tbody').append(tr2);
}

function odstampajOglas(ad){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	let tr = $('<tr></tr>');
	let tr2 = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>'+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>Like:'+ad.brLajkova+'</label>');
	let neg = $('<label>Dislike: '+ad.brDislajkova+'</label> ');
	
	let poz1 = $('<label>'+ad.brLajkova+'</label>');
	let neg1= $('<label> '+ad.brDislajkova+'</label> ');
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	td.append(a).append(b).append(c).append(d);
	
	let td4 = $('<td></td>');	
			var addToFavorite = $('<a href="#">Omiljeni</a>');
			addToFavorite.click(function(){
				$.post({
					url : 'rest/oglasi/addToFavorite/'+ad.naziv,
				success : function(){
					alert('Oglas dodat u omiljene!');
				},
				error : function(r){
					alert(r.responseText);
				}
				});
			});
		
		
			let addToCart = $('<a href="#">Dodaj u korpu</a>"');
			addToCart.click(function(){
				
				$.post({
					url : 'rest/oglasi/ukorpu/'+ad.naziv,
				success : function(){
					location.reload(true);
					alert('Oglas dodat u korpu!');
				},
				error : function(r){
					alert(r.responseText);
				}
			});
				
			});
	let plus = $('<a href="#">Like</a>');
	let minus =$('<a href="#">Dislike</a>');
	if(user!=null && user.uloga==="Kupac"){
		td4.append(addToCart);
		td4.append(addToFavorite);
		td3.append(poz1).append(plus);
		td3.append(neg1).append(minus);
	}else{
		td3.append(poz).append(neg);
	}
	
		plus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/likedAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brLajkova = ad.brLajkova +1;
					window.location.reload(true);
				},
				error : function(r){
					alert(r.responseText);
				}
			});
		});
		
		
		minus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/dislikeAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brDislajkova = ad.brDislajkova + 1;
					window.location.reload(true); 
				},
				error: function(response){
					alert(response.responseText);
				}
			});
			
			
		});
		
		tr.append(td2).append(td4).append(td);
		tr2.append(td3);
		$('table#oglasi tbody').append(tr);
		$('table#oglasi tbody').append(tr2);
						
}


