<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>


<form>
	<div style="margin-top: 10px; text-align: right;">
		<input type="button" class="btn btn-success" onclick="index.excelfile()" value="Excel 서식 다운로드">
	</div>
	<label> Excel 서식 다운로드 버튼 클릭하셔서 양식 설정하시고, 아래에 올려주시기 바랍니다. </label>
	<div class="custom-file" style="margin-bottom: 5px;">
		<input type="file" class="custom-file-input" id="customFile"> <label class="custom-file-label" for="customFile">Choose file</label>
	</div>
	<div style="text-align: right; margin-bottom: 5px;">
		<input type="button" class="btn btn-secondary" value="해당 파일로 확정" onclick="index.excelchangefile()">
	</div>
	<div style="text-align: right;">
		<input type="button" class="btn btn-primary" value="업로드 완료된 파일 확인" onclick="window.open('/file/excelfilelist.do')">
	</div>
	<div class="form-group">
  		<label for="fileName">선택한 파일 이름</label>
  		<input type="text" class="form-control" id="fileName" readonly>
	</div>
	<label> 이름 변경할 파일들 선택 </label>
	<div class="custom-file" style="margin-bottom: 5px;">
		<input type="file" class="custom-file-input" id="changeFile" multiple> <label class="custom-file-label" for="changeFile">Choose file</label>
	</div>
</form>

<table class="table table-bordered" id = "tableRow">

	<tr>
		<th style="width: 90%; text-align: center; vertical-align: middle;"> 파일 이름 </th>
		<th style="width: 5%"> <input type="button" class="btn btn-secondary" id = "listCheck" value="리스트 확인" onclick="index.changefilelist(); index.changefilelistupload();"> </th>
		<th style="width: 5%"> <input type="button" class="btn btn-warning" value="리스트 삭제" onclick="index.changefilelistremove()"> </th>
	</tr>

</table>

<div style="text-align: right;">
	<input type="button" class="btn btn-primary" value="파일 이름 일괄 변경" onclick="index.allfilenamechange()" hidden="hidden" id = "fileChangeCheck">
</div>

<script>
	// Add the following code if you want the name of the file appear on select
	$(".custom-file-input").on(
			"change",
			function() {
				var fileName = $(this).val().split("\\").pop();
				$(this).siblings(".custom-file-label").addClass("selected")
						.html(fileName);
			});
</script>

<%@ include file="../layout/footer.jsp"%>