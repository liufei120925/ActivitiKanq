<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/showMessage.html" var="messageUrl" />
		<a href="/ActivitiWebDemo/Test/Cont.shtml">Click to enter</a><br>
		<a href="/ActivitiWebDemo/activiti/leave/LeaveMain.shtml">请假申请</a> 
	</body>
</html>
