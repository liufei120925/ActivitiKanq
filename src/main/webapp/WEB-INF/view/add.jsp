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
<form action="/ActivitiWebDemo/Test/add.shtml" method="get">
	<table width="650" height="212" border="1">
	  <tr>
	    <td width="67" height="47" align="center" valign="middle">id</td>
	    <td width="168" align="center" valign="middle"><input type="text" name="id" id="id" /></td>
	    <td width="109" align="center" valign="middle">创建时间</td>
	    <td width="216" align="center" valign="middle"><input type="text" value='${createDate }--' name="createDate" id="createDate" /></td>
	  </tr>
	  <tr>
	    <td height="39" align="center" valign="middle">请假人</td>
	    <td align="center" valign="middle"><input type="text" name="assignee" id="assignee" /></td>
	    <td align="center" valign="middle">所属部门</td>
	    <td align="center" valign="middle"><input type="text" name="department" id="department" /></td>
	  </tr>
	  <tr>
	    <td height="34" align="center" valign="middle">申请时间</td>
	    <td align="center" valign="middle"><input type="text" name="applicationDate" id="applicationDate" /></td>
	    <td align="center" valign="middle">请假天数</td>
	    <td align="center" valign="middle"><input type="text" name="leaveDay" id="leaveDay" /></td>
	  </tr>
	  <tr>
	    <td height="72" align="center" valign="middle">请假理由</td>
	    <td colspan="3" align="center" valign="middle"><label for="remark"></label>
	    <textarea name="remark" id="remark" cols="75" rows="5"></textarea></td>
	  </tr>
	</table>
</form>
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
