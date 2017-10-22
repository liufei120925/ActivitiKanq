<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<br>任务id:<input id="updateText"  value=""><input type="button" id="commit" value="完成任务" onclick='commited()'>
</body>

<script>
	function add() {
		var param = document.getElementById("addText").value;
		window.location.href = "/ActivitiWebDemo/Test/Cont.shtml?type=add&param="
				+ param;
	}
	function del() {
		var param = document.getElementById("delText").value;
		location.href = "/ActivitiWebDemo/Test/Cont.shtml?type=del&param="
				+ param;

	}
	function commited() {
		var param = document.getElementById("updateText").value;
		location.href = "/ActivitiWebDemo/Test/Cont.shtml?type=update&param="
				+ param;

	}
	function find() {
		var param = document.getElementById("findText").value;
		location.href = "/ActivitiWebDemo/Test/Cont.shtml?type=find&param="
				+ param;

	}
</script>
</html>
