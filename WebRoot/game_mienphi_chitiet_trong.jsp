<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="inet.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.game.*"%>
<jsp:useBean id="gameDAO" class="inet.db.game.GameDAO" scope="session" />
<jsp:useBean id="gameCatDAO" class="inet.db.game.GameCategoryDAO" scope="session" />

<%
	String sId=request.getParameter("id");
	Game game=gameDAO.getRow(new BigDecimal(sId));
	GameCategory gameCat=gameCatDAO.getRow(game.getCatId());
%>
	<div class="title-center">
		<div style="padding: 10px; "><h1 style="font-size: 13px; color: #333333;"><a href="<%=request.getContextPath() %>/game-mien-phi/"><span style="color: #333333;">Game Miễn phí</span></a> <img src="<%=request.getContextPath() %>/images/icon-muiten.png" align="absmiddle" hspace="5">
		<a href="/game-mien-phi/<%=game.getCatId() %>/<%=game.getName()%>.html" ><span style="color: #333333;"><%=gameCat.getName() %></span></a>
		</h1></div>
	</div>
	<div class="noidungchitiet" style="padding-top:20px; ">
		<img src="<%=request.getContextPath() %>/gameservlet/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" hspace="10" align="left" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
	 	<strong><%=game.getName() %> </strong><br><br>
	   <%=game.getInfo().replaceAll("\n","<br/>") %>
	</div>

	<div class="both"></div>
	<div style="height:20px; "></div>
	
	<div class="cuphap break">
		<a href="http://sms.vn/gprs/">
		<img src="<%=request.getContextPath() %>/images/gamemobile-bannerhuongdanGPRS.gif" align="right" border="0"></a>
		  <p>Để tải game về điện thoại, h&atilde;y soạn tin: </p>
		  <p><strong>DA 98<%=game.getId() %> </strong>gửi <strong>8185 </strong></p>
		  <p>Để gửi cho bạn b&egrave; soạn tin: </p>
		  <p><strong>DA 98<%=game.getId() %> SĐTnhận </strong> gửi <strong>8185</strong> </p>
   	</div>
	
	<div style="padding:20px 0 10px 0; text-align:left; " class="text-gray">
		<%=game.getModel() %>
	</div>
