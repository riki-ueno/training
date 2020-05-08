function initialize() {
	let parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent(parameter)
	const expenseId = parameter.split("=")[1]

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/expense/show',
		dataType: "json",
		data: {expenseId: expenseId},
		statusCode: {
			200: function(expense) {
				setExpenseValues(expense)
			},
			401: function() {
				redirectLoginPage()
			},
			403: function() {

			}
		}
	})
}

function setExpenseValues(expense) {
	$("#id").val(expense.id)
	$("#claimer_id").val(expense.claimerId)
	$("#claimed_at").val(expense.claimedAt)
	$("#title").val(expense.title)
	$("#payment_destination").val(expense.paymentDestination)
	$("#amount").val(expense.amount)
	$("#status").val(conversionStatusCodeToStatusName(expense.status))
	$("#reason").val(expense.reason)
	$("#updater_id").val(expense.updaterId)
	$("#updated_at").val(expense.updatedAt)
}

function redirectLoginPage() {
	alert("ログインしてください")
	location.href = "/EmployeesInfoManageTool/html/auth/login.html"
}

function conversionStatusCodeToStatusName(statusCode) {
	switch (statusCode) {
		case 0:
			return "申請中"
		case 1:
			return "承認"
		case 2:
			return "却下"
	}
}

function submit() {
	const id = $("#id").val()
	const title = $("#title").val()
	const paymentDestination = $("#payment_destination").val()
	const amount = $("#amount").val()
	const requestQuery = {id: id, title: title, paymentDestination: paymentDestination, amount: amount}

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/expense/show',
		dataType: "json",
		data: requestQuery,
		statusCode: {
			200: function(result) {
				if (result === true) {
					alert("変更を保存しました")
					location.reload()
				} else {
					alert("変更の保存に失敗しました")
				}
			},
			401: function() {
				redirectLoginPage()
			},
			403: function() {

			}
		}
	})
}

$(document).ready(function () {
	'use strict';

	initialize();
	$("#submit").click(submit)
});