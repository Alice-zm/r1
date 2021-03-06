<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<html>

<head>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/back/style.css" rel="stylesheet">
	
<script>

function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id).focus();
		return false;
	}
	return true;
}

function checkNumber(id, name){
	if(!checkEmpty(id,name))
		return false;
	var val = $("#"+id).val();
	if(isNaN(val)){
		alert(name+ "必须是数字");
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}

function checkInt(id, name){
	if(!checkEmpty(id,name))
		return false;
	var val = $("#"+id).val();
	if(parseInt(val)!=val){
		alert(name+ "必须是整数");
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}

$(function(){
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确认要删除");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});
})
</script>	
</head>
<body>

