<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="inet.db.ichipgame.*"%>
<jsp:useBean id="ichipGameDAO" class="inet.db.ichipgame.GameDAO" scope="session" />
<%
	Game game = ichipGameDAO.getGameBySeq(7);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Java Game | Game dien thoai | Game <%=game.getName() %></title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="Java game dành cho điện thoại di động, game <%=game.getName() %>">
		
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
						<div class="title-center">
							<div style="padding:10px; "><h1 style="font-size: 13px; color: #333333;"><a href="http://javagame.sms.vn"><span style="color: #333333;">Java game</span></a> </h1></div>
						</div>
						
						<div class="noidungchitiet" style="padding-top:20px; ">
							<div style="float:left;text-align:center">
							<img src="/images/game-co-tuong.gif" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
							<br>
							<strong>Mã số:</strong> 957
							</div>
						<strong><%=game.getName() %> </strong><br><br>
						   <%=game.getDesc() %>
						</div>
						
						<div class="both"></div>
						<div style="height:20px; "></div>
						
						<div class="cuphap break">
						<a href="http://sms.vn/gprs/">
						<img src="<%=request.getContextPath() %>/images/gamemobile-bannerhuongdanGPRS.gif" align="right" border="0"></a>
						  <p>Để tải game về điện thoại, h&atilde;y soạn tin: </p>
						  <p><strong>GAME 957 </strong>gửi <strong>8785 </strong></p>
						  <p>Để gửi cho bạn b&egrave; soạn tin: </p>
						  <p><strong>GAME 957 SĐTnhận </strong> gửi <strong>8785</strong> </p>
						      Gi&aacute; game:15.000đ				</div>
						
						<div style="padding:20px 0 10px 0; text-align:left; " class="text-gray">
						<%=game.getModel1() %><br/>
						<%=game.getModel2() %><br/>
						<%=game.getModel3() %><br/>
						</div>
						
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
