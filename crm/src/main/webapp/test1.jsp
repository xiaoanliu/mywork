<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>
$.ajax({
url:"",
data:{},
type:"",
dataType:"json",
success: function (data){

}

})

//创建时间为当前系统时间
String createTime= DateTimeUtil.getSysTime();
//创建人为当前用户（从session中获取）
String createBy=((User)request.getSession().getAttribute("user")).getName();
</body>
</html>
