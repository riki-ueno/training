function initialize() {
	'use strict'

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/auth/self',
		dataType: "json",
		statusCode: {
			200: function(self) {
				if (self.roleName !== "manager") disableManagerOperation()

				$.ajax({
					type : 'GET',
					url : '/EmployeesInfoManageTool/api/employee/index',
					dataType: "json",
					statusCode: {
						200: function(employeeList) {
							drawEmployeeTable(employeeList, self)
						},
						401: function() {
							redirectLoginPage()
						}
					}
				})
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

function disableManagerOperation() {
	$(".manager").attr("hidden", true)
}

function drawEmployeeTable(employeeList, self) {
	const employeeTableBody = $("#employee_table tbody")
	for (let employee of employeeList) {
		let html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td>"

		if (self.roleName === "manager") {
			const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
			const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
			const deleteLink = 'location.href="./delete.html?id=' + employee.id + '"'
			const deleteButton = "<input type='button' employee_id='" +employee.id + "' onclick='deleteEmployeeRequest(this)' value='削除' />"
			html += "<td>" + editButton + "</td><td>" + deleteButton + "</td>"
		} else if (self.employeeId === employee.id) {
			const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
			const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
			html += "<td>" + editButton + "</td>"
		}

		html += "</tr>"

		employeeTableBody.append(html)
	}
}

function deleteEmployeeRequest(button) {
	'use strict'

	const employeeId = $(button).attr("employee_id")

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/employee/delete',
		dataType: "json",
		data: {id: employeeId},
		statusCode: {
			200: function(result) {
				if (result === true) {
					location.reload()
				} else {
					$("#message").html("削除に失敗しました")
				}
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

$(document).ready(function () {
	'use strict';

	initialize();
});