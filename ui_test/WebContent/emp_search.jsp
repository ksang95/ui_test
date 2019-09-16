<%@page import="json_jdbc.JsonDB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
	request.setCharacterEncoding("utf-8");
	String ename=request.getParameter("ename");
	String data=JsonDB.searchEmp(ename);
%>

<%=data %>