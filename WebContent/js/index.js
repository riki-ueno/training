function initialize() {
	$.ajax({
		type : 'GET',
		url : '/EmployeesInfoManageTool/api/authentication/role',
		dataType: "json",
		success: function(role) {
			const header = $("header nav ul")
			const menuBar =
				'<li><a href="/EmployeesInfoManageTool/html/expense/index.html">支払申請管理</a></li>' +
	  			'<li><a href="/EmployeesInfoManageTool/html/employee/password.html">パスワード変更</a></li>' +
				'<li><a href="/EmployeesInfoManageTool/html/employee/index.html">社員一覧</a></li>' +
				'<li><a href="/EmployeesInfoManageTool/html/department/index.html">部署一覧</a></li>' +
				'<li><a href="#" onclick="logout()">ログアウト</a></li>'
			header.append(menuBar)
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status === 401) {
				const header = $("header nav ul")
				const menuBar =
					'<li><a href="/EmployeesInfoManageTool/html/employee/index.html">社員一覧</a></li>' +
					'<li><a href="/EmployeesInfoManageTool/html/department/index.html">部署一覧</a></li>' +
					'<li><a href="/EmployeesInfoManageTool/html/authentication/login.html">ログイン</a></li>'
				header.append(menuBar)
			} else {
				alert("通信に失敗しました")
			}
		}
	})
}

function logout() {
	$.ajax({
		type : 'POST',
		url : '/EmployeesInfoManageTool/api/authentication/logout',
		dataType: "json",
		success: function() {
			alert("ログアウトしました。")
			location.href = "/EmployeesInfoManageTool/html/authentication/login.html"
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

    initialize()
});