<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>添加</title>
<jsp:include page="module/head.jsp" />

    <link href="\ActivitiWebDemo\static\sample in bootstrap v3\css\bootstrap-datetimepicker.css" rel="stylesheet" media="screen">
</head>
<body>


	<jsp:include page="module/top.jsp" /> 

	<form id="leaveForm" role="form" action='/ActivitiWebDemo/activiti/leave/LeaveReSendForm.shtml'
		 method="get" onsubmit="return onSub()">
		<input type="hidden"  name="uuid" id="uuid" value="${form.uuid }" />
		<input type="hidden"  name="id" id="id" value="${form.id }" />
		<input type="hidden"  name="definition" id="definition" value="${form.definition }" />
		<input type="hidden"  name="queryType" id="queryType" value="${queryType }" /> 
		<input type="hidden"  name="assignee" id="assignee" value="${form.assignee }" />
		<input type="hidden"  name="taskId" id="taskId" value="${form.taskId }" />
		<input type="hidden"  name="processId" id="processId" value="${form.id }" />
		<input type="hidden"  name="nodePeople" id="nodePeople" value="${form.nodePeople }" />
		<input type="hidden"  name="nodePeoples" id="nodePeoples" value="${form.nodePeoples }" />
		 
		<c:if test="${(form.nodeOne eq '0' or (loginName eq form.assignee and (form.definition eq  'start' or form.nodeOne eq '-1'))) 
		and ((loginName eq form.assignee) or (form.definition eq 'task1' and loginName eq '项目经理李四') or (form.definition eq 'task2' and loginName eq '总经理张三'))}">
			
			
			<c:if test="${loginName ne form.assignee}">
				<button type="button" class="btn btn-default"  onclick="returnSqrForm()">返回申请人</button>
				<button type="submit" class="btn btn-default"  onclick="LeaveSendForm()">
					<c:if test="${loginName eq '总经理张三' }">发送人事部备案</c:if>
					<c:if test="${loginName ne '总经理张三' }">发送总经理审批</c:if>
				</button>
			</c:if> 
			<c:if test="${loginName eq form.assignee and ( form.definition eq  'start' or form.nodeOne eq '-1')}">
				<button type="submit" class="btn btn-default" >发送总经理审批</button>
				<input type="button" class="btn btn-default" onclick="reSaveForm()" value="保存" />
			</c:if> 
		</c:if>
		<div class="container">
			<h3 align="center">
				<label>请假申请</label>
			</h3>
			
			<table class="table table-bordered">
			
				<!-- 可编辑模式 -->
				<c:if test="${loginName eq form.assignee and (form.definition eq  'start' or form.nodeOne eq '-1')}">
					
					<tr> 
						<td height="47" align="center"
							style="vertical-align: middle;">请假人</td>
						<td align="center" style="vertical-align: middle;">
							<input type="text" class="form-control" readonly="readonly" name="assignee" id="assignee" value="${form.assignee }" />
						</td> 
						<td align="center" valign="bottom">所属部门</td>
						<td align="center" style="vertical-align: middle;">
							<input type="text" class="form-control" value='${form.department }' name="department" id="department" />
						</td> 
					</tr>
					<tr> 
						<td align="center" style="vertical-align: middle;">创建时间</td>
						<td align="center" style="vertical-align: middle;">
						<input readonly="readonly"
									value='<fmt:formatDate value="${form.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>'
									type="text" class="form-control" class="form-control"
									name="createDate" id="createDate" />
						</td>
						<td align="center" style="vertical-align: middle;">请假天数</td>
						<td align="center" style="vertical-align: middle;">
						<input type="text" class="form-control" value='${form.leaveDay }'
									name="leaveDay" id="leaveDay" /></td>
					</tr>
					<tr><td height="34" align="center" style="vertical-align: middle;">申请时间</td>
						<td align="center" style="vertical-align: middle;">
						<input type="text" class="form-control applicationDate datepicker"
									value='<fmt:formatDate value="${form.applicationDate }"
									pattern="yyyy-MM-dd HH:mm:ss" />' name="applicationDate"
									id="applicationDate" readonly="readonly" /></td> 
							<c:if test="${queryType eq '2' or queryType eq '1'}">
								<td align="center" >审核状态</td>
								<td align="center" ><c:if test="${form.nodeOne==0 }"><font color="blue">审核中</font></c:if> 
								<c:if test="${form.nodeOne==1 }"><font color="green">已通过</font></c:if> 
								<c:if test="${form.nodeOne==-1 }"><font color="red">未通过</font></c:if></td>
							</c:if>
							<c:if test="${queryType ne '2' and queryType ne '1'}"><td colspan="2"></td></c:if> 
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">请假理由</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
							<textarea class="form-control" name="remark" id="remark" cols="75"rows="5" >${form.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">项目经理审批</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
							<textarea class="form-control" name=nodeSP1 id="nodeSP1" cols="75"
								rows="5" readonly="readonly">${form.nodeSP1 }</textarea>
						</td>
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">总经理审批</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
							<textarea class="form-control" name="nodeSP2" id="nodeSP2" cols="75"
								rows="5" readonly="readonly">${form.nodeSP2 }</textarea>
						</td>
					</tr>
				</c:if> 
				
				<c:if test="${!(loginName eq form.assignee and (form.definition eq  'start' or form.nodeOne eq '-1'))}"> 
					<tr> 
						<td width="67" height="47" align="center"style="vertical-align: middle;">请假人</td>
						<td width="168" align="center" style="vertical-align: middle;">
							<input type="text" class="form-control" readonly="readonly" name="assignee" id="assignee" value="${form.assignee }" />
						</td> 
						<td align="center" valign="bottom">所属部门</td>
						<td align="center" style="vertical-align: middle;">${form.department }</td> 
					</tr>
					<tr> 
						<td width="109" align="center" style="vertical-align: middle;">创建时间</td>
						<td width="216" align="center" style="vertical-align: middle;">
							<fmt:formatDate value="${form.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center" style="vertical-align: middle;">请假天数</td>
						<td align="center" style="vertical-align: middle;">${form.leaveDay }</td>
					</tr>
					<tr>
						<td height="34" align="center" style="vertical-align: middle;">申请时间</td>
						<td align="center" style="vertical-align: middle;">
							<fmt:formatDate value="${form.applicationDate }" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>  
						<c:if test="${queryType eq '2' or queryType eq '1'}">
							<td align="center" >审核状态</td>
							<td align="center" ><c:if test="${form.nodeOne==0 }"><font color="blue">审核中</font></c:if> 
							<c:if test="${form.nodeOne==1 }"><font color="green">已通过</font></c:if> 
							<c:if test="${form.nodeOne==-1 }"><font color="red">未通过</font></c:if></td>
						</c:if>
						<c:if test="${queryType ne '2' and queryType ne '1'}"><td colspan="2"></td></c:if> 
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">请假理由</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
	
							<textarea class="form-control" name="remark" id="remark" cols="75"
								rows="5" readonly="readonly">${form.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">项目经理审批</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
							<textarea class="form-control" name=nodeSP1 id="nodeSP1" cols="75"
								rows="5"
								<c:if test="${!(form.definition eq 'task1' and loginName eq '项目经理李四' and form.nodeOne eq '0')}">
								readonly="readonly"</c:if> >${form.nodeSP1 }</textarea>
						</td>
					</tr>
					<tr>
						<td height="72" align="center" style="vertical-align: middle;">总经理审批</td>
						<td colspan="3" align="center" style="vertical-align: middle;">
							<textarea class="form-control" name="nodeSP2" id="nodeSP2" cols="75"
								rows="5"
								<c:if test="${!(form.definition eq 'task2' and loginName eq '总经理张三' and form.nodeOne eq '0')}">readonly="readonly"</c:if>
								>${form.nodeSP2 }</textarea>
						</td>
					</tr>
				</c:if>
				
			</table>
		</div>
	</form>  
	 
	<div id="countdowner"></div>
<script>
	$(function() {
		var  today = new Date(),
		tomorrow = Math.round(today.setDate(today.getDate() + 1) / 1000);
		$('#countdowner').scojs_countdown({until: tomorrow});
	});
</script>
<jsp:include page="module/bootstrop-model.jsp"></jsp:include>
</body> 
<script type="text/javascript"> 
function reSaveForm() {
	var leaveForm = document.getElementById("leaveForm");
	leaveForm.action = "/ActivitiWebDemo/activiti/leave/LeaveReSaveForm.shtml";		
	var pd = onSub();
	console.log(pd);
	if (pd) {
		leaveForm.submit();
	} 
} 
function LeaveReSendForm() {
	var leaveForm = document.getElementById("leaveForm");
	leaveForm.action = "/ActivitiWebDemo/activiti/leave/LeaveReSendForm.shtml";
	leaveForm.submit();
} 

function returnSqrForm() {
	var leaveForm = document.getElementById("leaveForm");
	leaveForm.action = "/ActivitiWebDemo/activiti/leave/returnSqrForm.shtml";
	leaveForm.submit();
} 

function LeaveSendForm() {
	var leaveForm = document.getElementById("leaveForm"); 
	leaveForm.action = "/ActivitiWebDemo/activiti/leave/LeaveComplete.shtml";
	leaveForm.submit();
}  
</script>
</html>
