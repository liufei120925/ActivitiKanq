<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>添加</title>
<jsp:include page="module/head.jsp" />
</head>
<body>

<jsp:include page="module/top.jsp" /> 
<body>
	<p>&nbsp;</p>
	<div class="container"> 
		<a class="btn btn-default input-group" role="button"
			href='/ActivitiWebDemo/activiti/leave/LeaveMain.shtml'>返回首页</a>
		<div align="center" class="input-group">
			<span class="input-group-addon">申请人姓名</span>
			<div>
				<input type="text" class="form-control" placeholder="阿斯顿"
					id="assignee" name="assignee" >
			</div>
			<span onclick="query()" class="btn btn-default input-group-addon">查询</span>
		</div> 
	</div>
	<br>
	<c:if test="${listData[0]!=null}">
		<div class="container">
 
			<table class="table table-hover  table-bordered">
				<tr>
					<th>申请人</th>
					<th>创建时间</th>
					<th>部门</th>
					<th>申请时间</th>
					<th>天数</th>
					<th>备注</th>
					<th>审核状态</th>
					<th colspan="2">操作</th>
				</tr>
				<c:forEach var="data" items="${listData}">

					<tr>
						<td>${data.assignee }</td>
						<td><fmt:formatDate value="${data.createDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${data.department }</td>
						<td><fmt:formatDate value="${data.applicationDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${data.leaveDay }</td>
						<td>${data.remark }</td>
						<td><c:if test="${data.nodeOne==0 }">审核中</c:if> <c:if
								test="${data.nodeOne==1 }">已完结</c:if> <c:if
								test="${data.nodeOne==-1 }">未通过</c:if></td>
						<td><a
							href='LeaveQuery.shtml?assignee=${data.assignee }&executionId=${data.id }'>查询</a></td>
						<td><a
							href='LeaveDel.shtml?assignee=${data.assignee }&processId=${data.id }'>删除</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<%-- <table class="table table-hover" >
	<c:forEach var="data" items="${listData}"> 
		<tr>
			<td>天数</td><td>${data.leaveDay }</td>
			<td>备注</td><td>${data.remark } </td>
			<td>部门</td><td>${data.department } </td>
			<td>审核节点</td><td>${data.nodeName } </td>
			<td>审核人</td><td>${data.nodePeople } </td>
			<td>审核状态</td><td><c:if test="${data.nodeOne==0 }">审核中</c:if>
			<c:if test="${data.nodeOne==1 }">已完结</c:if><c:if test="${data.nodeOne==-1 }">未通过</c:if> </td>
			<td>申请时间</td><td><fmt:formatDate value="${data.applicationDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>创建时间</td><td><fmt:formatDate value="${data.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
			<td>申请人</td><td>${data.assignee }</td>
			<td><a href='LeaveQuery.shtml?assignee=刘菲&executionId=${data.id }'>查询</a></td> 
		</tr> 
	</c:forEach> 
	</table> --%>
	</c:if>
	<h1>${remark }</h1>
</body>
<script type="text/javascript">
	function query() {
		var assignee = document.getElementById("assignee").value;
		window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveQuery.shtml?assignee="
				+ assignee;
	}
<%-- 
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>Bootstrap 实例 - 悬停表格</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<table class="table table-hover">
	<caption>悬停表格布局</caption>
	<thead>
		<tr>
			<th>申请人</th>
			<th>创建时间</th>
			<th>部门</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>天数</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Tanmay</td>
			<td>Bangalore</td>
			<td>560001</td>
		</tr>
		<tr>
			<td>Sachin</td>
			<td>Mumbai</td>
			<td>400003</td>
		</tr>
		<tr>
			<td>Uma</td>
			<td>Pune</td>
			<td>411027</td>
		</tr>
	</tbody>
</table>

</body>
</html>
 --%>
	
</script>
</html>
