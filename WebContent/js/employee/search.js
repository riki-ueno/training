function initialize() {
	'use strict'

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/index',
		dataType: "json",
		success: function(departmentList) {
			const departmentSelectTag = $("#department_id")
			for (let department of departmentList) {
				const html = '<option value="' + department.id + '">' + department.name + '</option>'
				departmentSelectTag.append(html)
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert("データの通信に失敗しました1")
		}
	})
}

function submit() {
	'use strict'

	const departmentId = $("#department_id").val()
	const employeeId   = $("#employee_id").val()
	const nameInclude  = $("#name_include").val()
	const requestQuery = {departmentId: departmentId, employeeId: employeeId, nameInclude: nameInclude}

	$("#employee_table tr").remove()

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/authentication/role',
		dataType: "json",
		success: function(role) {
			$.ajax({
				type : 'GET',
				url : '/EmployeesInfoManageTool/api/employee/search',
				dataType: "json",
				data: requestQuery,
				success: function(result) {
					const message = $("#message")
					const employeeTable = $("#employee_table")

					if (result.length === 0) {
						message.html("登録されている社員がいません。")
						employeeTable.attr("hidden", true)
					} else {
						message.html("")
						employeeTable.attr("hidden", false)
						for (let employee of result) {
							let html
							if (role.roleName === "manager") {
								const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
								const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
								const deleteLink = 'location.href="./delete.html?id=' + employee.id + '"'
								const deleteButton = "<input type='button' employee_id='" +employee.id + "' onclick='employeeDeleteRequest(this)' value='削除' />"
								html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td><td>" + editButton + "</td><td>" + deleteButton + "</td></tr>"
							} else if ( employee.id === role.employeeId) {
								const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
								const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
								html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td><td>" + editButton + "</td>"
							} else {
								html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td>"
							}

							employeeTable.find("tbody").append(html)
						}
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					if (XMLHttpRequest.status === 401) {
						alert("ログインしてください")
						location.href = "/EmployeesInfoManageTool/html/authentication/login.html"
					} else {
						alert("通信に失敗しました")
					}
				}
			})
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status === 401) {
				alert("ログインしてください")
				location.href = "/EmployeesInfoManageTool/html/authentication/login.html"
			} else {
				alert("通信に失敗しました")
			}
		}
	})
}

$(document).ready(function () {
	'use strict';

	initialize();

	$("#submit").bind("click", submit);
});