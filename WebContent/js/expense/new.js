function submit() {
	const title = $("#title").val()
	const paymentDestination = $("#payment_destination").val()
	const amount = $("#amount").val()

	const requestQuery = {title: title, paymentDestination: paymentDestination, amount: amount}

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/expense/create',
		dataType: "json",
		data: requestQuery,
		statusCode: {
			200: function(result) {
				if (result === true) {
					alert("申請を受け付けました")
					location.href = "./index.html"
				} else {
					alert("申請内容が正しくありません")
				}
			},
			401: function() {
				redirectLoginPage()
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

	$("#submit").click(submit)
});