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

	<div class="container" style="padding-top: 50px;">
			<table class="table">
				<tr>
				
					<th>流程名称</th>
					<th>申请人</th> 
					<th>部门</th> 
					<th>节点状态</th> 
					<th>申请时间</th> 
					<th colspan="3">操作</th>
				</tr>
			<c:if test="${dataList[0]!=null}">
				<c:forEach var="data" items="${dataList}">
					<tr>
						<td>[${data.remark1 }]</td>
						<td>${data.assignee }</td> 
						<td>${data.department }</td> 
						<td><fmt:formatDate value="${data.applicationDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>  
						<td><a
							href='LeaveDetails.shtml?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }&uuid=${data.uuid }&queryType=${queryType}'>详情</a></td>
						<td> 
							<c:if test="${queryType eq '0' }">
								<a href='LeaveDel.shtml?assignee=${assignee }&taskId=${data.taskId }&uuid=${data.uuid }'>删除流程</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			</table>
	</div>

	<h3>${remark }</h3>
</body>
<script type="text/javascript">
	function query() {
		var assignee = document.getElementById("assignee").value;
		window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveSp.shtml?assignee="
				+ assignee;
	}
	function agreeAll() {
		var assignee = document.getElementById("assignee").value;
		window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveAllComplete.shtml?assignee="
				+ assignee;
	}
</script>
</html>
