function initialize() {
	'use strict'

	let parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent(parameter)
	const id = parameter.split('=')[1]

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/edit',
		dataType: "json",
		data: {id: id},
		statusCode: {
			200: function(department) {
				setDepartmentValue(department)
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

function setDepartmentValue(department) {
	$("#department_id").val(department.id)
	$("#department_name").val(department.name)
}

function submit() {
	'use strict'

	const id = $("#department_id").val()
	const name = $("#department_name").val()

	const requestQuery = {id: id, name: name}
	console.log(requestQuery)
	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/department/edit',
		dataType: "json",
		data: requestQuery,
		statusCode: {
			200: function(result) {
				if (result === true) {
					location.href="./result.html?result=success&from=update"
				} else {
					location.href="./result.html?result=failure&from=update"
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

$(document).ready(function () {
	'use strict';

	initialize();

	$("#submit").bind("click", submit);
});