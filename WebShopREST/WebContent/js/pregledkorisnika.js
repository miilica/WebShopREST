function onLoad(){
	$.ajax({
		url: 'rest/korisnici/',
		type: 'get',
		success: function(korisnici) {
			if(korisnici==null){
			}else {
				ispisiKorisnike(korisnici);		
			} 
		}
		
	});	
}

$(document).ready(function(){
	$('button#sacuvaj').click(function(){
		let naziv = $('select#kor').val();
		let uloga = $('select#tip').val();
		
	
			$.ajax({
				url: 'rest/korisnici/izmjeni/'+naziv+'/'+uloga,
				type: 'PUT',
				success: function(){
					$.get({
						url : 'rest/kategorije',
						success : function(accounts) {
							onLoad();
							removeAll();
							for(acc of accounts){
								ispisiKorisnike(acc);
							}
						}
					});
				}
			});
		
	});
});


function ispisiKorisnike(korisnici){
	 var list = korisnici == null ? [] : (korisnici instanceof Array ? korisnici : [ korisnici ]);
		
	 $.each(korisnici, function(index, k) {
		 let naziv = $('<td>'+k.korisnicko+'</td>');
		 let tip = $('<td>'+k.uloga+'</td>');
		 let ime = $('<td>'+k.ime+'</td>');
		 let prezime = $('<td>'+k.prezime+'</td>');
		 let mail = $('<td>'+k.mail+'</td>');
		 let datum = $('<td>'+k.datum+'</td>');
		 let grad = $('<td>'+k.grad+'</td>');
		 let tr = $('<tr></tr>');
		tr.append(naziv).append(tip).append(ime).append(prezime).append(mail).append(datum).append(grad);
		$('table#tbKor tbody').append(tr);

		 $("#kor").append('<option>'+k.korisnicko+' </option>');
		 
		 
	 });

	
}

function removeAll() {
	$('table#tbKor tbody tr').remove();
	$('select#kor option').remove();
}