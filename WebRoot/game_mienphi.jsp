<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.game.*"%>
<jsp:useBean id="gameCatDAO" class="inet.db.game.GameCategoryDAO" scope="session" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
	String sCatId=request.getParameter("catId");
	GameCategory cat=null;
	if(sCatId!=null){
		cat=gameCatDAO.getRow(new BigDecimal(sCatId));
	}else{
		cat=(GameCategory)gameCatDAO.findAllRoots(1).iterator().next();
	}
	String sPage = request.getParameter("page");
	if (sPage == null || "".equals(sPage.trim())) {
		sPage = "1"; //first page
	}
%>
<html>
	<head>
		<title>Java Game | Game dien thoai | Game Mien phi dành cho <%=cat.getName() %> | trang <%=sPage %></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="Danh sách java game dành cho điện thoại di động, game miễn phí dành cho <%=cat.getName() %>, trang <%=sPage %>">
		<link href="<%=request.getContextPath() %>/style.css" rel="stylesheet" type="text/css">
		<link rel="icon" href="/images/icon-game.ico" type="image/x-icon" />
		<%@include file="googleScript.html"%>
	</head>
	
	<body bottommargin="0" topmargin="0" marginheight="0" marginwidth="0">
		<center>
			<!--Header-->
			<jsp:include flush="true" page="header.jsp"></jsp:include>
			<div style="width:960px; ">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="200" valign="top">
						<jsp:include flush="true" page="left.jsp"></jsp:include>
					</td>
					<td width="5"></td>
					<td valign="top">
						<jsp:include flush="true" page="game_mienphi_trong.jsp">
							<jsp:param name="catId" value="<%=cat.getId() %>"/>
						</jsp:include>
					</td>
					<td width="5"></td>
					<td width="200" valign="top">
					<jsp:include flush="true" page="right.jsp"></jsp:include>
					</td>
				  </tr>
				</table>
			</div>
			<jsp:include flush="true" page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
