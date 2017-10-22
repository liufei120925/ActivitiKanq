<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>主页</title>
<jsp:include page="module/head.jsp" />
</head>
<body>

<jsp:include page="module/top.jsp" /> 
<div align="center" style="padding-top: 100px;">
	<p><input class="btn btn-primary btn-lg" role="button" onclick="add()" value="请假申请" /></p>
	<p><input class="btn btn-primary btn-lg" role="button" onclick="sp(0)" value="待办件" /></p>
	<p><input class="btn btn-primary btn-lg" role="button" onclick="sp(1)" value="已办件" /></p>
	<p><input class="btn btn-primary btn-lg" role="button" onclick="sp(2)" value="已办结" /></p> <!-- 
	<p><input class="btn btn-primary btn-lg" role="button" onclick="sp()" value="项目经理审批" /></p> -->
 
	<h1>${remark}</h1>
</div>
</body>
<script type="text/javascript">

function add() { 
	window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveAdd.shtml";
}
function query(type) { 
	window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveQuery.shtml?type="+type;
}
function sp(type) { 
	window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveSp.shtml?userName=${loginName}&queryType="+type;
} 
</script>
</html>
