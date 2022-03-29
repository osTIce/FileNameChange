let index = {
	
	// 엑셀 서식 파일 다운로드
	excelfile: function() {
		$.ajax({
			type : "GET",
			url : "/file/excelfiledown.do",
		}).done(function() {
			alert("다운로드가 성공적으로 완료되었습니다.\n다운로드 폴더를 확인하시기 바랍니다.");
		}).fail(function() {
			alert("다운로드에 실패하였습니다.\nC:/Users/사용자명/Downloads 경로가 맞는지 확인부탁드립니다.");
		});
	},

	// 엑셀 서식 파일 작성 후 완료된 엑셀 파일 확인
	excelchangefile: function() {
		let fileValue = $("#customFile").val();
		
		// 파일 이름 길이
		let fileLength = fileValue.length;
		
		// 확장자 구하기 위해서 .의 마지막 인덱스 숫자 구함 (+1 이유는 .표시 이후에 확장자만 구하기 위해)
		let fileExt = fileValue.lastIndexOf(".") + 1;
		
		// 확장자 이름
		let fileName = fileValue.substring(fileExt, fileLength).toLowerCase();

		if(fileName != "xlsx" && fileName != "xls"){
			alert("xlsx, xls 확장자 파일만 업로드 가능합니다.");
		}else{
		
			if (fileValue == "") {
				alert("양식 파일을 등록해주시기 바랍니다.")
			} else {
				let confirmChk = confirm('정말 해당 파일로 확정하시겠습니까?');
				if (confirmChk) {
	
					let formData = new FormData();
					let inputFile = $("#customFile");
					let files = inputFile[0].files;
	
					formData.append("uploadFile", files[0]);
					
					$.ajax({
						type : "POST",
						url : "/file/exceluploadfile.do",
						enctype : "multipart/form-data",
						processData : false,
						contentType : false,
						data : formData
					}).done(function() {
						alert("파일 업로드에 성공하였습니다.");
					}).fail(function() {
						alert("파일 업로드에 실패하였습니다.");
					});
	
				}
			}
		}
	},
	
	// 파일 이름 변경하려는 리스트 확인
	changefilelist: function(){
		
		// 선택한 파일들 리스트 확인하기 위해 파일 아이디 값 가져오기
		let fileList = document.getElementById("changeFile");
		
		// files 속성 이용해 파일들 배열로 변경
		let files = fileList.files;
		let file;
		
		let tbl = document.getElementById("tableRow");
		
		for(let i = 0; i < files.length; i++){
			
			file = files[i];
			
			// tr 태그 생성
			let row = document.createElement("tr");
			
			// td 태그 생성
			let firsttd = document.createElement("td");

			// td에 class로 fileListTd 값 부여
			firsttd.setAttribute("class", "fileListTd");
			
			// td에 colSpan으로 값 3부여 (colSpan, S가 대문자인거 주의)
			firsttd.setAttribute("colSpan", "3");
			
			// td에 파일 이름 텍스트 추가
			firsttd.innerText = file.name;
			
			
			row.appendChild(firsttd);
			tbl.appendChild(row);
			
		}
	},
	
	// 파일 이름 변경하려는 리스트 삭제
	changefilelistremove: function(){
		$(".fileListTd").remove();
		document.getElementById("fileChangeCheck").hidden = true;
	},
	
	// 변경할 파일 리스트 전달
	changefilelistupload: function(){
		
		// class 개수 확인
		let fileCount = $(".fileListTd").length
		
		if(fileCount >= 1){
			document.getElementById("fileChangeCheck").hidden = false;
		} else {
			document.getElementById("fileChangeCheck").hidden = true;
		}
		
	},
	
	// 파일 이름 일괄 변경
	allfilenamechange: function(){
		
		let allFileChange = $("#changeFile")[0].files;
		let excelFile = $("#fileName").val();
		let formData = new FormData();
		
		console.log("엑셀 파일 값 확인 : " + excelFile);
		
		// java 단에 파일 리스트 길이 전달 
		formData.append("fileLength", allFileChange.length);
		
		// 이름 변경하고자 하는 엑셀 양식 파일이 존재할 때 ajax를 통해 값 전달
		if(excelFile != "") {
			
			// excel 파일 이름 전달
			formData.append("excelFileName", excelFile);
			
			for(let i = 0; i < allFileChange.length; i++){
				
				formData.append("fileName" + i, allFileChange[i]);
				
			}
			
			$.ajax({
				type : "POST",
				url : "/file/allfilechange.do",
				enctype : "multipart/form-data",
				processData : false,
				contentType : false,
				data : formData
			}).done(function(){
				alert("성공적으로 완료되었습니다."); 
			}).fail(function(){
				alert("엑셀 양식이나 파일 이름을 다시 한 번 확인해 주시기 바랍니다.");
			});
			
		} else {
			alert("업로드 완료된 파일 확인을 클릭하셔서\n양식 파일을 선택해주시기 바랍니다.");
		}
		
	}
	
}
