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
</head>
<body>


	<jsp:include page="module/top.jsp" />

	<form id="leaveForm" role="form" method="get">
		<input type="hidden"  name="uuid" id="uuid" value="${form.uuid }" />
		<input type="hidden"  name="id" id="id" value="${form.id }" />
		<input type="hidden"  name="definition" id="definition" value="${form.definition }" />
		<input type="hidden"  name="queryType" id="queryType" value="${queryType }" /> 
		<input type="hidden"  name="assignee" id="assignee" value="${form.assignee }" />
		<input type="hidden"  name="taskId" id="taskId" value="${form.taskId }" />
		<input type="hidden"  name="processId" id="processId" value="${form.id }" />
		<input type="hidden"  name="nodePeople" id="nodePeople" value="${form.nodePeople }" />
		<input type="hidden"  name="nodePeoples" id="nodePeoples" value="${form.nodePeoples }" />

		<c:if test="${  form.nodeOne eq '0' and ((form.definition eq 'start' and loginName eq '阿斯顿') or (form.definition eq 'task1' and loginName eq '项目经理李四') or (form.definition eq 'task2' and loginName eq '总经理张三'))}">
			<c:if test="${form.definition ne  'start'}">
				<button type="button" class="btn btn-default"  onclick="returnSqrForm()">返回申请人</button>
				<button type="submit" class="btn btn-default"  onclick="LeaveSendForm()">发送总经理审批</button>
			</c:if>
			<c:if test="${form.definition eq  'start'}">
				<button type="button" class="btn btn-default" onclick="LeaveReSendForm()">发送总经理审批</button>
				<input type="button" class="btn btn-default" onclick="reSaveForm()" value="保存" />
			</c:if>
		</c:if>
		<div class="container">
			<h3 align="center">
				<label>请假申请</label>
			</h3>
			
			<table class="table table-bordered">
				<tr> 
					<td width="67" height="47" align="center"
						style="vertical-align: middle;">请假人</td>
					<td width="168" align="center" style="vertical-align: middle;">
						<c:if test="${form.definition eq  'start'}">
							<input type="text" class="form-control" readonly="readonly" name="assignee" id="assignee" value="${form.assignee }" />
						</c:if> <c:if test="${form.definition ne 'start'}">
							${form.assignee }
						</c:if>
					</td> 
					<td align="center" valign="bottom">所属部门</td>
					<td align="center" style="vertical-align: middle;"><c:if
							test="${form.definition eq  'start'}">
							<input type="text" class="form-control"
								value='${form.department }' name="department" id="department" />
						</c:if> <c:if test="${form.definition ne 'start'}">
							${form.department }
						</c:if></td> 
				</tr>
				<tr> 
					<td width="109" align="center" style="vertical-align: middle;">创建时间</td>
					<td width="216" align="center" style="vertical-align: middle;">
						<c:if test="${form.definition eq  'start'}">
							<input readonly="readonly"
								value='<fmt:formatDate value="${form.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>'
								type="text" class="form-control" class="form-control"
								name="createDate" id="createDate" />
						</c:if> <c:if test="${form.definition ne 'start'}">
							<fmt:formatDate value="${form.createDate }"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</c:if>
					</td>
					<td align="center" style="vertical-align: middle;">请假天数</td>
					<td align="center" style="vertical-align: middle;"><c:if
							test="${form.definition eq  'start'}">
							<input type="text" class="form-control" value='${form.leaveDay }'
								name="leaveDay" id="leaveDay" />
						</c:if> <c:if test="${form.definition ne 'start'}">
							${form.leaveDay }
						</c:if></td>
				</tr>
				<tr>
					<td height="34" align="center" style="vertical-align: middle;">申请时间</td>
					<td align="center" style="vertical-align: middle;"><c:if
							test="${form.definition eq  'start'}">
							<input type="text" class="form-control"
								value='<fmt:formatDate value="${form.applicationDate }"
								pattern="yyyy-MM-dd HH:mm:ss" />' name="applicationDate"
								id="applicationDate" />
						</c:if> <c:if test="${form.definition ne 'start'}">
							<fmt:formatDate value="${form.applicationDate }"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</c:if></td> 
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
							rows="5"
							<c:if test="${form.definition ne 'start'}">readonly="readonly"</c:if>>${form.remark }</textarea>
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
			</table>
			<!-- <div class='container' align="center" style="padding-bottom: 50px;">如果是自己发送，则form地址不变，否则变为审核地址
				
			</div>  -->
		</div>
	</form>
</body>
<script type="text/javascript">

function reSaveForm() {
	var leaveForm = document.getElementById("leaveForm");
	leaveForm.action = "/ActivitiWebDemo/activiti/leave/LeaveReSaveForm.shtml";
	leaveForm.submit();
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


<%-- 
?assignee=${assignee }&taskId=${data.taskId }&processId=${data.id }&taskType=1
 --%> 
</script>
</html>
