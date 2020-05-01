function initialize() {
	'use strict'
	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/employee/index',
		dataType: "json",
		success: function(employeeList) {
			const tBody = $("#employee_list")
			for (let employee of employeeList) {
				const editLink = 'location.href="./edit.html?id=' + employee.id + '&method=update"'
				const deleteLink = 'location.href="./delete.html?id=' + employee.id + '"'
				const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
				const deleteButton = "<input type='button' employee_id='" +employee.id + "' onclick='employeeDeleteRequest(this)' value='削除' />"
				const html = "<tr><td>" + employee.id + "</td><td>" + employee.employeeDetail.name + "</td><td>" + editButton + "</td><td>" + deleteButton + "</td></tr>"
				tBody.append(html)
			}
		}
	})
}

function employeeDeleteRequest(button) {
	'use strict'

	const employeeId = $(button).attr("employee_id")

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/employee/delete',
		dataType: "json",
		data: {id: employeeId},
		success: function(result) {
			console.log("test")
			if (result === true) {
				location.reload()
			} else {
				$("#message").html("削除に失敗しました")
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			$("#message").html("削除に失敗しました")
		}

	})
}

$(document).ready(function () {
	'use strict';

	initialize();
});