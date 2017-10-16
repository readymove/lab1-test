<%@ page contentType="text/html; charset=UTF-8" import="java.util.LinkedList,java.util.List"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>

    <h1>
        <s:property value="username" />详细信息
    </h1>
<table border="1" align="center">
<tr>
    <td>编号</td>
    <td>姓名</td>
    <td>年龄</td>
    <td>国家</td>
<tr>
    <%List<String> list0 = (List<String>)session.getAttribute("list0");
    for (int i = 0; i < list0.size(); i++) {
  out.print("<td align=\"center\">"+list0.get(i)+"</td>");
} %>
</tr>
</table>
<h1>书籍详细信息</h1>
<table border="1" align="center">
<tr>
    <td>ISBN</td>
    <td>书名</td>
    <td>作者编号</td>
    <td>出版社</td>
    <td>出版日期</td>
    <td>价格</td>
    <td>修改</td>
    <td>删除</td>
<tr>
    <%List<String> list = (List<String>)session.getAttribute("list");
    for (int i = 0; i < list.size(); i+=6) {
  out.print("<td align=\"center\">"+list.get(i)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+1)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+2)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+3)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+4)+"</td>");
  out.print("<td align=\"center\">"+list.get(i+5)+"</td>");
  out.print("<td align=\"center\"><a href=Gotoedit?isbn="+list.get(i)+"><button type=\"button\">修改</button></a></td>");
  out.print("<td align=\"center\"><a href=Delete?isbn="+list.get(i)+"><button type=\"button\">删除</button></a></td>");
} %>
</tr>
</table>
<a href=Search?username=<%out.print(list0.get(1)); %>><button type="button">返回</button></a>
<a href=Login><button type="button">返回主页</button></a>
</body>
</html>