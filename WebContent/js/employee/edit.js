function initialize() {
	'use strict'

	let parameter  = location.search.substring( 1, location.search.length )
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

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/department/index',
		dataType: "json",
		success: function(departmentList) {
			const departmentSelectTag = $("#department_list")
			for (let department of departmentList) {
				const html = '<option value="' + department.id + '">' + department.name + '</option>'
				departmentSelectTag.append(html)
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert("データの通信に失敗しました1")
		}
	})

	if (id) {
		$.ajax({
			type : 'GET',
			url : '/EmployeesInfoManageTool/api/employee/edit',
			dataType: "json",
			data: {id:id},
			success: function(employee) {
				console.log(employee)
				$("#id").val(employee.id)
				$("#name").val(employee.employeeDetail.name)
				$("#age").val(employee.employeeDetail.age)
				$("input[name=sex]").val([employee.employeeDetail.sex])
				$("#postal_code").val(employee.employeeDetail.postalCode)
				$("#pref_name").val(employee.employeeDetail.prefName)
				$("#address").val(employee.employeeDetail.address)
				$("#department_list").val(employee.employeeDetail.departmentId)
				$("#joined_at").val(employee.employeeDetail.joinedAt)
				$("#retired_at").val(employee.employeeDetail.retiredAt)
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				alert("データの通信に失敗しました2")
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

	const id = $("#id").val()
	const name = $("#name").val()
	const age = $("#age").val()
	const sex = $("input[name=sex]:checked").val()
	const postalCode = $("#postal_code").val()
	const prefName = $("#pref_name").val()
	const address = $("#address").val()
	const departmentId = $("#department_list").val()
	const joinedAt = $("#joined_at").val()
	const retiredAt = $("#retired_at").val()

	const requestQuery = {
		id: id,
		employeeId: id,
		name: name,
		age: age,
		sex: sex,
		postalCode: postalCode,
		prefName: prefName,
		address: address,
		departmentId: departmentId,
		joinedAt: joinedAt,
		retiredAt: retiredAt,
		method: method
	}

	console.log(requestQuery)

	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/employee/edit',
		dataType: "json",
		data: requestQuery,
		success: function(result) {
			console.log(result)
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