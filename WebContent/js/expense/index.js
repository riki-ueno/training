function initialize() {
	'use strict'
	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/expense/own',
		dataType: "json",
		statusCode: {
			200: function(data) {
				drawOwnExpenseTable(data)
			},
			401: function() {
				redirectLoginPage()
			}
		}
	})

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/expense/index',
		dataType: "json",
		statusCode: {
			200: function(data) {
				drawUnapprovedExpenseTable(data)
			},
			401: function() {
				redirectLoginPage()
			},
			403: function() {
				$(".manager").attr("hidden", true)
			}
		}
	})
}

function drawOwnExpenseTable(ownExpenseList) {
	const ownExpenseTableBody = $("#own_expense_table tbody")
	for (let ownExpense of ownExpenseList) {
		const html =
			"<tr>" +
				"<td>" + ownExpense.id + "</td>" +
				"<td>" + ownExpense.claimedAt + "</td>" +
				"<td>" + (ownExpense.updatedAt ? ownExpense.updatedAt : "none") + "</td>" +
				"<td>" + ownExpense.claimerId + "</td>" +
				"<td>" + ownExpense.title + "</td>" +
				"<td>" + ownExpense.amount + "</td>" +
				"<td>" + conversionStatusCodeToStatusName(ownExpense.status) + "</td>" +
				"<td><a href='./show.html?id=" + ownExpense.id + "'>詳細</a></td>" +
			"</tr>"
		ownExpenseTableBody.append(html)
	}
}

function drawUnapprovedExpenseTable(expenseList) {
	const expenseTableBody = $("#expense_table tbody")
	for (let expense of expenseList) {
		const html =
			"<tr>" +
				"<td>" + expense.id + "</td>" +
				"<td>" + expense.claimedAt + "</td>" +
				"<td>" + (expense.updatedAt ? expense.updatedAt : "none") + "</td>" +
				"<td>" + expense.claimerId + "</td>" +
				"<td>" + expense.title + "</td>" +
				"<td>" + expense.amount + "</td>" +
				"<td>" + conversionStatusCodeToStatusName(expense.status) + "</td>" +
				"<td><a href='./show.html?id=" + expense.id + "'>詳細</a></td>" +
				"<td><button expense_id='" + expense.id + "' onclick='updateStatusCode(this, 1)'>承認</button></td>" +
				"<td><input type='text' />" +
				"<td><button expense_id='" + expense.id + "' onclick='updateStatusCode(this, 2)'>却下</button></td>" +
			"</tr>"
		expenseTableBody.append(html)
	}
}

function updateStatusCode(button, newStatusCode) {
	const expenseId = $(button).attr("expense_id")

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/manager/expense/accept',
		dataType: "json",
		data: {expenseId: expenseId},
		statusCode: {
			200: function(result) {
				if (result === true) {
					alert("更新しました")
				} else {
					alert("更新に失敗しました")
				}
				location.reload()
			},
			401: function() {
				redirectLoginPage()
			},
			403: function() {
				alert("権限がありません")
			}
		}
	})
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

$(document).ready(function () {
	'use strict';

	initialize();
});