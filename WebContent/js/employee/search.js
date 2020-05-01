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
					const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
					const deleteLink = 'location.href="./delete.html?id=' + employee.id + '"'
					const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
					const deleteButton = "<input type='button' employee_id='" +employee.id + "' onclick='employeeDeleteRequest(this)' value='削除' />"
					const html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td><td>" + editButton + "</td><td>" + deleteButton + "</td></tr>"
					employeeTable.find("tbody").append(html)
				}
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert("データの通信に失敗しました")
		}
	})
}

$(document).ready(function () {
	'use strict';

	initialize();

	$("#submit").bind("click", submit);
});