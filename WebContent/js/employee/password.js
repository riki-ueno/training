function submit() {
	const password = $("#password").val()
	const passwordConfirmation = $("#password_confirmation").val()
	const requestQuery = {password: password, passwordConfirmation: passwordConfirmation}
	console.log(requestQuery)

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/member/employee/password',
		dataType: "json",
		data: requestQuery,
		success: function(result) {
			if (result === true) {
				alert("変更しました")
				location.reload()
			} else {
				alert("変更に失敗しました。")
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert("通信に失敗しました")
		}

	})
}

$(document).ready(function () {
	'use strict';

	$("#submit").click(submit)
});