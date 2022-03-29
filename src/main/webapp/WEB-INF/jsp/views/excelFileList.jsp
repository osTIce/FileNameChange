<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<style>

	th {
		text-align: center;	
	}
	
</style>

<h1 style="padding-top: 5px;"> 파일 선택 리스트 </h1>

<table class="table table-bordered" id = "tableRow">
	
		<tr>
			<th> 파일 번호 </th>
			<th> 파일 이름 </th>
			<th> 업로드 날짜 </th>
			<th> 선택 </th>
		</tr>
	
	<c:forEach var = "fileList" items ="${fileList}">
		
		<tr class = "fileChoice">
			<td style="text-align: center;"> ${fileList.no} </td>
			<td> ${fileList.name} </td>
			<td> ${fileList.uploaddate} </td>
			<td style="text-align: center;"> <input type = "button" class = "btn btn-warning fileCheck" value = "선택"> </td>
		</tr>
	
	</c:forEach>

</table>

<script>

  	$('.fileCheck').click(function(){
		
 		// 선택한 버튼 객체 가져오기
		var checkBtn = $(this);
 		
 		// 선택한 객체의 부모 요소를 찾아감 (2번 작성했으니, input -> td -> tr로 간다고 보면 됌.)
		var tr = checkBtn.parent().parent();
 		
 		// tr에서 해당 자식요소를 찾아  eq의 인덱스 값 1번째 즉 이름을 텍스트로 변환
		var tdFileName = tr.children().eq('1').text();
 		
 		// 해당 페이지를 열었던 부모 페이지에 값 전달 (excelChange.jsp)
		var parentValueCheck = opener.document.getElementById("fileName").value = tdFileName;
 		
 		alert("선택이 완료되었습니다.");
 		
		// 창 닫기
		window.close();
	});  

</script>

<%@ include file="../layout/footer.jsp"%>