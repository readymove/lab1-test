<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
    <h1>请先添加作家</h1>
    <center>
    <s:form action="Addauthor">
        <s:textfield name="isbn" label="ISBN"/>
        <s:textfield name="authorid" label="编号" readonly="true"/>
        <s:textfield name="name" label="作家名" readonly="true"/>
        <s:textfield name="age" label="年龄"/>
        <s:textfield name="country" label="国籍"/>
        <s:submit value="添加"/>
    </s:form>
    </center>
</body>
</html>