function initialize() {
	'use strict'

	let parameter  = location.search.substring(1, location.search.length)
	parameter = decodeURIComponent(parameter)
	const parameters = parameter.split("&")
	const result = parameters[0].split('=')[1]
	const from   = parameters[1].split('=')[1]

	let message;
	if (result === "success") {
		if (from === "create") {
			message = "データベースの登録に成功しました。"
		} else {
			message = "データベースの更新に成功しました。"
		}
	} else {
		if (from === "create") {
			message = "データベースの登録に失敗しました。"
		} else {
			message = "データベースの更新に失敗しました。"
		}
	}
	$("#message").html(message)
}

$(document).ready(function () {
	'use strict';

	initialize();
});