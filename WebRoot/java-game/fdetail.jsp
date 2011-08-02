<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.db.javagame.*"%> 
<%@page import="inet.db.game.GameCategory"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="inet.util.my.MobileTool"%>
<%@page import="inet.util.my.MobileProfilesChecker"%>

<jsp:useBean id="inetCatDAO" class="inet.db.game.GameCategoryDAO" scope="session"/>
<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session"/>
<jsp:useBean id="javaCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session"/>

<%
	String linkSmsSyntax = "sms:8785?body=GZ 8";
	String smsSyntax = "GZ 8";
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title> Game Mobile | Java game | Game di động | SMS | MP3 | Nhạc chuông | Nhạc chờ | Game </title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta name="copyright" content="iNET.vn"/>
		<meta name="keywords" content="wap, 3G, di dong, internet, mobile, nhac chuong, nhạc chuông, mp3, nhạc mp3, download, nghe nhac, tải nhạc, tai nhac, ringtone, hình nền, hinh nen, logo, logo mạng, chat, blog, tin tuc, tin tức, video, clip, java game, xổ số, xo so, bóng đá, bong da, chứng khoán, chung khoan" />
		<meta name="description" content="WAP 3G - Thế giới giải trí trên mobile - Hoàn toàn miễn phí - Tải nhạc chuông, nhạc MP3, Xố Số, Bóng đá, Chứng khoán" />
		<meta name="ROBOTS" content="INDEX, FOLLOW" />
		<meta name="author" content="iNET corporation"/> 
	    <meta http-equiv="Cache-Control" content="no-cache"/> 
	    <meta http-equiv="refresh" content="3600"/> 
	    <%@ include file="../include/scriptAddToGoogle.html" %>
		<link rel="stylesheet" type="text/css" href="../include/styles.css" />	
		
		<script type="text/javascript">
			function sendSMS(content, serviceNumber){	
				alert('Để tải game, soạn tin '+content+' gửi '+serviceNumber+'.\nhttp://3g.wap.vn');
			}
		</script>
	</head>
	<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0"> 
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/banner_buzzcity.jsp" %>
		<%@include file="/include/menu_top.jsp" %>
		<%@include file="/include/time.jsp" %>
		
		<div class="tieudeblue">
			<div style="padding:4px 0 4px 5px; ">
				<a href="../" class="link" style="color:white; text-decoration: none;">Home</a> 
				&raquo; <a class="link" href="./" style="color: #ffffff ; text-decoration: none;">Java game</a>
			</div>
		</div>
		
		<%
			String sId = request.getParameter("id");
			//inet.db.fmcgame.Game game = null;
			JavaGame game = null;
			try{
				//game = fmcGameDAO.getGameById(new BigDecimal(sId));
				game = javaGameDAO.getGameById(new BigDecimal(sId));
			} catch(Exception ex){}
			
			if(game != null){
				String info = game.getDesc();
				info = info.replaceAll("<br/>","");
				info = info.replaceAll("<br>","");
				info = info.replaceAll("<Br>","");
				info = info.replaceAll("<b/>","");
				info = info.replaceAll("<b>","");
				//info = info.replaceAll(iGame.getName(),"");
				info = info.trim();
				if(info.length()>200){
					info = info.substring(0,190) + " ...";
				}
		%>
			<div class="noidunggame">
				<div>
					<a href="fdetail.jsp?id=<%=game.getId() %>">
						<img src="../javagame-image/fGameImage.gif?type=WAP&id=<%=game.getId() %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>
					</a>
					<a href="fdetail.jsp?id=<%=game.getId() %>" style="text-decoration: none;">
						<strong><%=game.getName() %></strong>
					</a>
					<br/>
					<span style="color:#666666 "><%=info %></span><br/>
					<img src="../images/icon-down.png" align="absmiddle" hspace="5" vspace="3" />
					<a  class="link-tai" href="<%=linkSmsSyntax + game.getMediaCode() + game.getInetCode() %>"  onclick="sendSMS('<%=smsSyntax + game.getMediaCode() + game.getInetCode() %>', '8785');">Tải về</a> <br/>
					<%if(MobileTool.checkCompatiblePhone(MobileProfilesChecker.getListModelPhone(request), game.getModel())){ %>
			  			&nbsp;<em>(Soạn <%=smsSyntax + game.getMediaCode() + game.getInetCode() %> gửi 8785)</em>
			  		<%}else{ %>
				  		<span style="color:#FF0000 ">
				  		&nbsp;<em>Không thích hợp cho điện thoại của bạn</em>
				  		</span>
			  		<%} %>
		  		</div>
		  		<div>
		  			<span style="color:#666666 "><%=game.getModel() %></span><br/>
		  		</div>
		  		<div class="both"></div>
		  	</div>
			<div class="both"></div>
	  	<%} %>
		
		<div class="tieudeDVkhac">
			<div style="padding:4px 0 0 5px; ">HOT GAME</div>
		</div>
		<%
		//GameCat gameFmcCat =null;
		JavaGameCat gameFmcCat =null;
		//Vector catList=(Vector)fmcCatDAO.findAllRoots();
		Vector catList=(Vector)javaCatDAO.findAllRoots();
		for(int i=0;i<catList.size();i++){
			gameFmcCat=(JavaGameCat)catList.get(i);
		%>
		<div class="menu-theloaikhac"><img src="../images/icon-tron.png" align="absmiddle" hspace="10" vspace="6" />
			<a href="list.jsp?g=f&cid=<%=gameFmcCat.getId() %>"><%=gameFmcCat.getName() %></a>
		</div>
		<%} %>
		
		<div class="tieudeDVkhac">
			<div style="padding:4px 0 0 5px; ">FREE GAME</div>
		</div>
		<%
		GameCategory gameInetCat =null;
		Vector inetCatList=(Vector)inetCatDAO.findAllRoots();
		for(int i=0;i<inetCatList.size();i++){
			gameInetCat=(GameCategory)inetCatList.get(i);
		%>
		<div class="menu-theloaikhac"><img src="../images/icon-tron.png" align="absmiddle" hspace="10" vspace="6" />
			<a href="list.jsp?g=i&cid=<%=gameInetCat.getId() %>"><%=gameInetCat.getName() %></a>
		</div>
		<%} %>
		
		<%@include file="/include/menu_bottom.jsp" %>
	</body>
</html>	