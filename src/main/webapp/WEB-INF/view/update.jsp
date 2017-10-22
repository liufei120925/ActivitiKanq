<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<div align="center">当前任务流程：${taskId }</div>
		
		<div align="center"> 
			<br>流程名称:<input id="addText"  value=""><input type="button" id="add" value="添加任务" onclick='add()'> 
			<br>任务id:<input id="updateText"  value=""><input type="button" id="commit" value="完成任务" onclick='commited()'>
			<br>查看任务id:<input id="findText"  value=""><input type="button" id="find" value="查看任务" onclick='find()'> 
		</div>
		
		<div align="center">当前任务id：${id }</div>
		<div align="center">当前任务名称：${name }</div>
		<div align="center">操作类型：${type }</div>
		<div align="center">备注：${reamark }</div>
		<div align="center">备注：${lcdyId }</div>
		<div align="center">备注：${lcslId }</div>

			<div class="content style-7">
				<c:if test="${type=='query' }">
					<c:forEach var="arrLeave" items="${leaveList}"> 
						<div>${arrLeave.id }=>${arrLeave.name }=>${arrLeave.createDate }=>${arrLeave.assignee }=>${arrLeave.processInstanceId }
						=>${arrLeave.executionId }=>${arrLeave.processDefinitionId }</div>
					</c:forEach>
				</c:if>
			</div>
	</body>
	
	<script>
		function add() {
			var param = document.getElementById("addText").value;
			window.location.href="/ActivitiWebDemo/Test/Cont.shtml?type=add&param="+param;
		}
		function del() {
			var param = document.getElementById("delText").value;
			location.href="/ActivitiWebDemo/Test/Cont.shtml?type=del&param="+param;
			
		}
		function commited() {
			var param = document.getElementById("updateText").value;
			location.href="/ActivitiWebDemo/Test/Cont.shtml?type=update&param="+param;
			
		}
		function find() {
			var param = document.getElementById("findText").value;
			location.href="/ActivitiWebDemo/Test/Cont.shtml?type=find&param="+param;
			
		}
	</script>
</html>
