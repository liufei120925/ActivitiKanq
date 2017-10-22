<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${loginName==null }">
	<%-- ${ sessionScope.age} --%>
	<div align="center" class="input-group">
		<span class="input-group-addon">登陆人：</span>
		<div>
			<input type="text" class="form-control" placeholder="阿斯顿"
				id="userName" name="userName">
		</div>
		<span onclick="login()" class="btn btn-default input-group-addon">登陆</span>
	</div>
</c:if>
<c:if test="${loginName!=null }">
	<%-- ${ sessionScope.age} --%>
	<a type="button" class="btn btn-default btn-sm" href='/ActivitiWebDemo/activiti/leave/LeaveLogout.shtml'>
		<span class="glyphicon glyphicon-user"></span> 登陆人：${loginName}
	</a>
</c:if>
<div align="right" style="float: right;">
	<a class="btn btn-default input-group" role="button"
		href='/ActivitiWebDemo/activiti/leave/LeaveMain.shtml'>返回首页</a>
</div>
<!-- <span style="color: #fff"> 阿斯顿 <br>总经理张三 <br>项目经理李四
</span> -->

<script>
function login() { 
	var username = $("#userName").val();
	window.location.href = "/ActivitiWebDemo/activiti/leave/LeaveLogin.shtml?userName="+username;
}
</script>