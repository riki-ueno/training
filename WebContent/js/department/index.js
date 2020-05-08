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
					url : '/EmployeesInfoManageTool/api/department/index',
					dataType: "json",
					statusCode: {
						200: function(departmentList) {
							drawDepartmentTable(departmentList, self)
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

function drawDepartmentTable(departmentList, self) {
	const departmentTableBody = $("#department_table tbody")
	for (let department of departmentList) {
		let html = "<tr><td>" + department.id + "</td><td>" + department.name + "</td>"

		if (self.roleName === "manager") {
			const editLink = 'location.href="./edit.html?id=' + department.id + '"'
			const deleteLink = 'location.href="./delete.html?id=' + department.id + '"'
			const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
			const deleteButton = "<input type='button' department_id='" + department.id + "' onclick='deleteDepartmentRequest(this)' value='削除' />"
			html += "<td>" + editButton + "</td><td>" + deleteButton + "</td>"
		}

		html += "</tr>"

		departmentTableBody.append(html)
	}
}

function redirectLoginPage() {
	alert("ログインしてください")
	location.href = "/EmployeesInfoManageTool/html/authentication/login.html"
}

function deleteDepartmentRequest(button) {
	'use strict'

	const departmentId = $(button).attr("department_id")

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/delete',
		dataType: "json",
		data: {id: departmentId},
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

function disableManagerOperation() {
	$(".manager").attr("hidden", true)
}

$(document).ready(function () {
	'use strict';

	initialize();
});