function initialize() {
	'use strict'
	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/index',
		dataType: "json",
		success: function(departmentList) {
			const departmentTableBody = $("#department_table tbody")
			for (let department of departmentList) {
				const editLink = 'location.href="./edit.html?id=' + department.id + '&method=update"'
				const deleteLink = 'location.href="./delete.html?id=' + department.id + '"'
				const editButton = "<input type='button' onclick='" + editLink + "' value='編集' />"
				const deleteButton = "<input type='button' department_id='" + department.id + "' onclick='departmentDeleteRequest(this)' value='削除' />"
				const html = "<tr><td>" + department.id + "</td><td>" + department.name + "</td><td>" + editButton + "</td><td>" + deleteButton + "</td></tr>"
				departmentTableBody.append(html)
			}
		}
	})
}

function departmentDeleteRequest(button) {
	'use strict'

	const departmentId = $(button).attr("department_id")

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/delete',
		dataType: "json",
		data: {id: departmentId},
		success: function(result) {
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