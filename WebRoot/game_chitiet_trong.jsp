<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="java.math.*"%>
<%@page import="inet.db.javagame.*"%>

<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session" />
<jsp:useBean id="javaGameCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session" />

<%
	String sId	= request.getParameter("id");
	//Game game = fmcGameDAO.getGameById(new BigDecimal(sId));
	JavaGame game = javaGameDAO.getGameById(new BigDecimal(sId));
	//GameCat gameCat = fmcGameCatDAO.getRow(game.getCatId());
	JavaGameCat gameCat = null;
	if(game != null){
		gameCat = javaGameCatDAO.getRow(game.getCatId());
	}else{
		System.out.println("javaGame null"); 
	}
 %>
	<div class="title-center">
		<div style="padding:10px; "><h1 style="font-size: 13px; color: #333333;"><a href="http://javagame.sms.vn"><span style="color: #333333;">Java game</span></a> <img src="<%=request.getContextPath() %>/images/icon-muiten.png" align="absmiddle" hspace="5"><%=gameCat.getName() %></h1></div>
	</div>
	
	<div class="noidungchitiet" style="padding-top:20px; ">
		<div style="float:left;text-align:center">
			<img width=128 height=128 src="<%=request.getContextPath() %>/javagame-image/<%=inet.util.UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
			<br>
			<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode()%>
		</div>
		<strong><%=game.getName() %> </strong><br><br>
	   	<%=game.getDesc() %>
	</div>
	
	<div class="both"></div>
	<div style="height:20px; "></div>
	
	<div class="cuphap break">
		<a href="http://sms.vn/gprs/">
			<img src="<%=request.getContextPath() %>/images/gamemobile-bannerhuongdanGPRS.gif" align="right" border="0">
		</a>
	  	<p>Để tải game về điện thoại, h&atilde;y soạn tin: </p>
	  	<p><strong>GAME 9<%=game.getMediaCode()%><%=game.getInetCode()%> </strong>gửi <strong>8785 </strong></p>
	  	<p>Để gửi cho bạn b&egrave; soạn tin: </p>
	  	<p><strong>GAME 9<%=game.getMediaCode()%><%=game.getInetCode()%> SĐTnhận </strong> gửi <strong>8785</strong> </p>
      	Gi&aacute; game:15.000đ				
	</div>
	
	<div style="padding:20px 0 10px 0; text-align:left; " class="text-gray">
		<%=game.getModel() %>
	</div>
