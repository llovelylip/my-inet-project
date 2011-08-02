<%@ page language="java" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String sCatId = request.getParameter("catId");
	String sDongmay = request.getParameter("dongmay");
	String sDoimay = request.getParameter("doimay");
	String sTen = request.getParameter("ten");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Java Game | Game dien thoai | Java Game dien thoai | Java Game mobile</title>
		
		<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
		<meta name = "description" content = "Tai java game cho dien thoai di dong, game dua xe, game danh bai, tai game mien phi">
		<link href = "<%=request.getContextPath() %>/style.css" rel = "stylesheet" type = "text/css">
		<link rel = "icon" href = "/images/icon-game.ico" type = "image/x-icon" />
		<%@include file = "googleScript.html"%>
	</head>
	<body bottommargin = "0" topmargin = "0" marginheight = "0" marginwidth="0">
		<center>
			<!--Header-->
			<jsp:include flush="true" page="header.jsp"></jsp:include>
			
			<div style="width:960px; ">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="200" valign="top">
						<jsp:include flush="true" page="left.jsp"></jsp:include>
					</td>
					<td width="3"></td>
					<td valign="top">
						<jsp:include flush="true" page="ketqua.jsp">
							<jsp:param name="catId" value="<%=sCatId %>"/>
							<jsp:param name="dongmay" value="<%=sDongmay%>"/>
							<jsp:param name="doimay" value="<%=sDoimay%>"/>
							<jsp:param name="ten" value="<%=sTen%>"/>
						</jsp:include>
					</td>
					<td width="7"></td>
					<td width="200" valign="top">
					<jsp:include flush="true" page="right.jsp">
						<jsp:param name="catId" value="<%=sCatId %>"/>
						<jsp:param name="dongmay" value="<%=sDongmay%>"/>
						<jsp:param name="doimay" value="<%=sDoimay%>"/>
						<jsp:param name="ten" value="<%=sTen%>"/>
					</jsp:include>
					</td>
				  </tr>
				</table>
			</div>
			<jsp:include flush="true" page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
