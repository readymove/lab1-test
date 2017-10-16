<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
    <h1>修改信息</h1>
    <center>
    <s:form action="Edit">
        <s:textfield name="isbn" label="ISBN" readonly="true"/>
        <s:textfield name="name" label="作家名"/>
        <s:textfield name="publisher" label="出版社"/>
        <s:textfield name="publishdate" label="出版日期"/>
        <s:textfield name="price" label="价格"/>
        <s:submit value="修改"/>
    </s:form>
    </center>
</body>
</html>