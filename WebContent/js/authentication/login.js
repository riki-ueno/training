function submit() {
	'use strict'
    const employeeId = $("#employee_id").val();
    const password = $("#password").val();
    const requestQuery = {employeeId: employeeId, password: password}

    $.ajax({
        type : 'POST',
        url : '/EmployeesInfoManageTool/api/login',
        dataType: "json",
        data: requestQuery,
        success: function(result) {
            if (result !== false) {
                sessionStorage.setItem("token", result.token)
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

    $("#submit").bind('click', submit)
});