function submit() {
	const name = $("#name").val()
	const requestQuery = {name: name}

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/department/create',
		dataType: "json",
		data: requestQuery,
		statusCode: {
			200: function(result) {
				if (result === true) {
					location.href="./result.html?result=success&from=create"
				} else {
					location.href="./result.html?result=failure&from=create"
				}
			},
			401: function() {
				redirectLoginPage()
			},
			403: function() {
				alert("権限がありません")
				location.href = "./index.html"
			}
		}
	})
}

function redirectLoginPage() {
	alert("ログインしてください")
	location.href = "/EmployeesInfoManageTool/html/auth/login.html"
}

$(document).ready(function () {
	'use strict';

	$("#submit").bind("click", submit);
});
