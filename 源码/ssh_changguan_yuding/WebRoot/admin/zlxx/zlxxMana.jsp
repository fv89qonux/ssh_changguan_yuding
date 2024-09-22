<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
		
        <script language="javascript">
           function zlxxDel(id)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path %>/zlxxDel.action?id="+id;
               }
           }
           
           function zlxxAdd()
           {
                 var url="<%=path %>/admin/zlxx/zlxxAdd.jsp";
				 window.location.href=url;
           }
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#86C1FF" align="center" style="margin-top:8px">
				<tr bgcolor="#c0ebf3">
					<td height="14" colspan="8" background="<%=path %>/img/tbg.gif">&nbsp;预订信息登记&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="5%">序号</td>
					<td width="10%">球馆编号</td>
					<td width="10%">客户姓名</td>
					<td width="10%">身份证号</td>
					<td width="10%">预订时间</td>
					<td width="10%">到期时间</td>
					<td width="10%">费用总计</td>
					<td width="10%">操作</td>
		        </tr>	
				<s:iterator value="#request.zlxxList" id="zlxx" status="ss">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#ss.index+1"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.qch.qcbh"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.kehuming"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.kehuzheng"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.rushijian"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.lishijian"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#zlxx.feiyong"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<a href="#" onclick="zlxxDel(<s:property value="#zlxx.id"/>)" class="pn-loperator">删除</a>
					</td>
				</tr>
				</s:iterator>
			</table>
			
			<table width='98%'  border='0'style="margin-top:8px;margin-left: 5px;">
			  <tr>
			    <td>
			      <input type="button" value="添加" style="width: 80px;" onclick="zlxxAdd()" />
			    </td>
			  </tr>
		    </table>
	</body>
</html>
