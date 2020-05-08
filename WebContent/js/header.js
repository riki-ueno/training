function drawHeader() {
    'use strict';

	let header = "<header><nav><ul>"

	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/auth/self',
		dataType: "json",
		success: function() {
			header +=
				'<li><a href="/EmployeesInfoManageTool/html/expense/index.html">支払申請管理</a></li>' +
	  			'<li><a href="/EmployeesInfoManageTool/html/employee/password.html">パスワード変更</a></li>' +
				'<li><a href="/EmployeesInfoManageTool/html/employee/index.html">社員一覧</a></li>' +
				'<li><a href="/EmployeesInfoManageTool/html/department/index.html">部署一覧</a></li>' +
				'<li><a href="#" onclick="logout()">ログアウト</a></li>'
			header += "</ul></nav></header>"
			$("body").prepend(header)
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status === 401) {
				header +=
					'<li><a href="/EmployeesInfoManageTool/html/employee/index.html">社員一覧</a></li>' +
					'<li><a href="/EmployeesInfoManageTool/html/department/index.html">部署一覧</a></li>' +
					'<li><a href="/EmployeesInfoManageTool/html/auth/login.html">ログイン</a></li>'
				header += "</ul></nav></header>"
				$("body").prepend(header)
			} else {
				alert("通信に失敗しました")
			}
		}
	})
}

function logout() {
	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/auth/logout',
		dataType: "json",
		complete: function() {
			alert("ログアウトしました。")
			location.href = "/EmployeesInfoManageTool/html/auth/login.html"
		}
	})
}

$(document).ready(function () {
    'use strict';

    drawHeader()
})
