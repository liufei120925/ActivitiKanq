<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

			
	<form id="leaveForm" 
		action="/ActivitiWebDemo/activiti/leave/LeaveAddForm.shtml" method="get" onsubmit="return onSub()">	
			<div >
				<button type="submit" class="btn btn-default">发送总经理审批</button>
				<input type="button" class="btn btn-default" onclick="saveForm()"
					value="保存" />
			</div>
		<div class="container" >
			<h3 align="center">
				<label>请假申请</label>
			</h3>
			<table class="table table-bordered">
				<tr>
					<td width="67" height="47" align="center"
						style="vertical-align: middle;">id</td>
					<td width="168" align="center" style="vertical-align: middle;"><input
						type="text" class="form-control" class="form-control" name="id"
						id="id" readonly="readonly" value="${uuid }"/></td>
					<td width="109" align="center" style="vertical-align: middle;">创建时间</td>
					<td width="216" align="center" style="vertical-align: middle;"><input
						value='${createDate }' type="text" class="form-control"
						class="form-control" name="createDate" id="createDate" readonly="readonly"/></td>
				</tr>
				<tr>
					<td height="39" align="center" style="vertical-align: middle;">请假人</td>
					<td align="center" style="vertical-align: middle;"><input readonly="readonly"
						type="text" class="form-control" name="assignee" id="assignee" value="${loginName }" /></td>
					<td align="center" valign="bottom">所属部门</td>
					<td align="center" style="vertical-align: middle;"><input
						type="text" class="form-control" name="department" id="department" /></td>
				</tr>
				<tr>
					<td height="34" align="center" style="vertical-align: middle;">申请时间</td>
					<td align="center" style="vertical-align: middle;"><input
						type="text" class="form-control datepicker" value='${createDate }'
						name="applicationDate" id="applicationDate" /></td>
					<td align="center" style="vertical-align: middle;">请假天数</td>
					<td align="center" style="vertical-align: middle;"><input
						type="text" class="form-control" name="leaveDay" id="leaveDay" /></td>
				</tr>
				<tr>
					<td height="72" align="center" style="vertical-align: middle;">请假理由</td>
					<td colspan="3" align="center" style="vertical-align: middle;">
						<textarea class="form-control" name="remark" id="remark" cols="75"
							rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td height="72" align="center" style="vertical-align: middle;">项目经理审批</td>
					<td colspan="3" align="center" style="vertical-align: middle;">
						<textarea class="form-control" name="remark" id="remark" cols="75"
							rows="5" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<td height="72" align="center" style="vertical-align: middle;">总经理审批</td>
					<td colspan="3" align="center" style="vertical-align: middle;">
						<textarea class="form-control" name="remark" id="remark" cols="75"
							rows="5" readonly="readonly"></textarea>
					</td>
				</tr>
			</table> 
		</div>
	</form>
<jsp:include page="module/bootstrop-model.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function saveForm() {
		var leaveForm = document.getElementById("leaveForm");
		leaveForm.action = "/ActivitiWebDemo/activiti/leave/LeaveSaveForm.shtml"
		var pd = onSub();
		if (pd) {
			leaveForm.submit();
		}
	}
 
</script>
</html>
