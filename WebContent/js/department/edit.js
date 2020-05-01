function initialize() {
	'use strict'

	let parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent(parameter)
	const parameters = parameter.split('&')
	let id = null
	let method = null

	if (parameters.length == 1) {
		method = parameters[0].split('=')[1]
	} else {
		id = parameters[0].split('=')[1]
		method = parameters[1].split('=')[1]
	}

	if (id) {
		$.ajax({
			type : 'GET',
			url : '/EmployeesInfoManageTool/api/department/edit',
			dataType: "json",
			data: {id:id},
			success: function(department) {
				console.log(department)
				$("#department_id").val(department.id)
				$("#department_name").val(department.name)
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				alert("データの通信に失敗しました")
			}
		})
	}
}

function submit() {
	'use strict'

	let parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent(parameter);
	const parameters = parameter.split('&');

	const method = parameters[parameters.length - 1].split('=')[1];
	const id = $("#department_id").val()
	const name = $("#department_name").val()

	const requestQuery = {id: id, name: name, method: method}
	console.log(requestQuery)
	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/department/edit',
		dataType: "json",
		data: requestQuery,
		success: function(result) {
			if (result === true) {
				location.href="./result.html?result=success&from=" + method
			} else {
				location.href="./result.html?result=failure&from=" + method
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