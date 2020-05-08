function initialize() {
	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/auth/self',
		dataType: "json",
		success: function() {
			alert("ログイン済みです。")
			location.href = "/EmployeesInfoManageTool/html/index.html"
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status !== 401) {
				alert("通信に失敗しました")
			}
		}
	})
}

function submit() {
	'use strict'
    const employeeId = $("#employee_id").val();
    const password = $("#password").val();
    const requestQuery = {employeeId: employeeId, password: password}

    $.ajax({
        type : 'POST',
        url : '/EmployeesInfoManageTool/api/authentication/login',
        dataType: "json",
        data: requestQuery,
        success: function(result) {
            if (result !== false) {
            	alert("ログインしました")
                location.href = "/EmployeesInfoManageTool/html/index.html"
            } else {
                $("#message").html("ログインに失敗しました")
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $("#message").html("ログインに失敗しました")
        }
    })
}

$(document).ready(function () {
    'use strict';

    initialize()

    $("#submit").bind('click', submit)
});