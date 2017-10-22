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
<!-- 	<div class="container">

		<div align="center" class="input-group">
			<span class="input-group-addon">代理人姓名</span>
			<div>
				<input type="text" class="form-control" placeholder="项目经理李四"
					id="assignee" name="assignee">
			</div>
			<span onclick="query()" class="btn btn-default input-group-addon">审批查询</span>
			<span onclick="agreeAll()" class="btn btn-default input-group-addon">全部同意</span>
		</div>
	</div> -->
	<%-- 
<c:if test="${TaskList[0]!=null}">
	<c:forEach var="data" items="${TaskList}">  
		--> id：${data.id } 
		--> 运行id：${data.executionId } 
		--> 节点名称：${data.name } 
		--> ProcessDefinitionId：${data.processDefinitionId } <br>
		--> ProcessInstanceId：${data.processInstanceId } 
		--> TaskDefinitionKey：${data.taskDefinitionKey } 
		--> Priority：${data.priority } 
		--> 创建时间：<fmt:formatDate value="${data.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> 
		--> 受理人：${data.assignee }&nbsp; <a href='LeaveComplete.shtml?assignee=${assignee }&taskId=${data.id }&processId=${data.executionId }&taskType=1'>同意</a><br>
	</c:forEach> 
</c:if> --%>
	<div class="container" style="padding-top: 10px;">
		<c:if test="${dataList[0]!=null}">
			<table class="table">
				<tr>
				
					<th>流程名称</th>
					<th>申请人</th>
					<!-- <th>天数</th> -->
					<th>部门</th>
					<!-- <th>审批人</th> -->
					<!-- <th>节点名称</th>
					<th>节点状态</th> -->
					<th>申请时间</th>
				<!-- 	<th>创建时间</th>
					<th>备注</th> -->
					<th colspan="3">操作</th>
				</tr>
				<c:forEach var="data" items="${dataList}">
					<tr>
						<td>[${data.remark1 }]</td>
						<td>${data.assignee }</td>
						<%-- <td>${data.leaveDay }</td> --%>
						<td>${data.department }</td>
						<%-- <td>${data.nodePeople }</td>
						<td>${data.nodeName }</td> --%> 
						<td><fmt:formatDate value="${data.applicationDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<%-- <td><c:if test="${data.nodeOne==0 }">审核中</c:if> <c:if
								test="${data.nodeOne==1 }">已完结</c:if> <c:if
								test="${data.nodeOne==-1 }">未通过</c:if></td>

						<td><fmt:formatDate value="${data.createDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${data.remark }</td> --%>
						<%-- 						<td><a
							href='LeaveComplete.shtml?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }&taskType=1'>同意</a></td>
						<td><a
							href='LeaveComplete.shtml?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }&taskType=-1'>不同意</a></td> --%>

						<td><a
							href='LeaveDetails.shtml?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }&uuid=${data.uuid }&queryType=${queryType}'>详情</a></td>
						<td><a
							href='LeaveDel.shtml?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }'>删除流程</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
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
