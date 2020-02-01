function onLoad(){
	
	$.ajax({
		url: 'rest/oglasi/getMojeRecenzije2',
		type: 'get',
		success: function(rec) {
			if(rec==null){
				alert('Nema recenzija, null');
			}else {
			  ispisiRec(rec);
			} 
		}
		
	});
}


function ispisiRec(rec){
	 var list = rec == null ? [] : (rec instanceof Array ? rec : [ rec ]);
	 $.each(rec, function(index, o) {
		 if(o.aktivan===true){
			 let opt = $('<option>'+o.naslov+'</option>');
			 $('select#akt').append(opt);
		 }
		
	 });	
}

$(document).ready(function(){
		$('button#sacuvaj').click(function(){
			let naslov = $('input[name=naslov]').val();
			let sadrzaj = $('input[name=sadrzaj]').val();
			let tacan = $('input[name=tacan]').val();
			let dog = $('input[name=dog]').val();
			let imgSource =  $('#sl').attr('src');
			let stariNaziv = $('select#akt').val();
			let flag = true;
		
			if(flag){
				$.ajax({
					url: 'rest/recenzije/izmjeni/'+stariNaziv+'/'+naslov+'/'+sadrzaj+'/'+tacan+'/'+dog+'/'+imgSource,
					type: 'PUT',
					success: function(){
						$.get({
							url : 'rest/oglasi/getMojeRecenzije2',
							success : function(accounts) {
								 $('select#akt').remove();
								for(acc of accounts){
									ispisiRec(acc);
								}
							}
						});
					}
				});
			}
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
