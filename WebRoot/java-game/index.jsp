<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.db.javagame.*"%> 
<%@page import="inet.db.game.GameCategory"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="inet.util.my.MobileTool"%>
<%@page import="inet.util.my.MobileProfilesChecker" %>

<jsp:useBean id="inetGameDAO" class="inet.db.game.GameDAO" scope="session"/>
<jsp:useBean id="inetCatDAO" class="inet.db.game.GameCategoryDAO" scope="session"/>
<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session"/>
<jsp:useBean id="javaCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session"/>

<%
	String serviceNumber = "8785";
	String linkSmsSyntax = "sms:"+serviceNumber+"?body=GZ 8";
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
		
		<form name="search_game" method="post" action="search.jsp">
			<div class="tieude-time">
				<div style="padding:2px 0 5px 5px; ">
					<input name="txt_search" type="text" size="25" maxlength="60" class="text-search" />
					<a href="javascript:void(0);" onclick="document.search_game.submit();">
						<img src="../images/search.gif" align="absmiddle" hspace="5" vspace="2" border="0" /></a>
				</div>
			</div>
		</form>
		
		<%
		String sType = request.getParameter("t"); 
		//n: newest; f: free; h: hot
		%>
		<div class="menu-phu" style="float: none; background-color: #FFFFFF;">
			<div style="float:left; width:60px; padding:5px; <%=sType==null||"n".equalsIgnoreCase(sType)?"background-color: #eeeeee;":"" %>">
				<%=sType == null || "n".equalsIgnoreCase(sType)?"Mới nhất":"<a href=\"?t=n\">Mới nhất</a>" %>
			</div>
			<div style="float:left; width:60px; padding:5px; <%="f".equalsIgnoreCase(sType)?"background-color: #eeeeee;":"" %>">
				<%="f".equalsIgnoreCase(sType)?"Miễn phí":"<a href=\"?t=f\">Miễn phí</a>" %>
			</div>
			<div style="float:left; width:90px; padding:5px; <%="h".equalsIgnoreCase(sType)?"background-color: #eeeeee;":"" %>">
				<%="h".equalsIgnoreCase(sType)?"Tải nhiều nhất":"<a href=\"?t=h\">Tải nhiều nhất</a>" %>
			</div>
			<div class="both"></div>
		</div>
		
		<%
		//boolean isFmcGame = true;
		boolean isPartnerGame = true;
		Vector vListGame = null;
		if(sType==null||"n".equalsIgnoreCase(sType)){
			//List newest game 
			//vListGame = (Vector)fmcGameDAO.findNewest(6);
			//vListGame = (Vector)fmcGameDAO.findRandom(6);
			vListGame = (Vector)javaGameDAO.findRandom(6);
			//out.println("vListGame TOP: "+vListGame.size());
		} else if("h".equalsIgnoreCase(sType)){
			//List hottest game 
			//vListGame = (Vector)fmcGameDAO.findHot(6);
			//vListGame = (Vector)fmcGameDAO.findHotRandom(6);
			vListGame = (Vector)javaGameDAO.findHotRandom(6);
			//out.println("vListGame HOT: "+vListGame.size());
		} else if("f".equalsIgnoreCase(sType)){
			//List free Game
			isPartnerGame = false;
			vListGame = (Vector)inetGameDAO.findNewGame(6);
			//out.println("vListGame FREE: "+vListGame.size());
		}
		
		if(vListGame!=null&&vListGame.size()>0){
			String info = "";
			BigDecimal gameId = null;
			int inetCode = -1;
			int mediaCode = -1;
			String gameName = "";
			String compatiblePhone = "";
			for(int j=0; j<vListGame.size(); j++){
				if(isPartnerGame){
					//inet.db.fmcgame.Game fGame = (inet.db.fmcgame.Game)  vListGame.get(j);
					JavaGame fGame = (JavaGame)vListGame.get(j);
					gameId = fGame.getId();
					inetCode = fGame.getInetCode();
					mediaCode = fGame.getMediaCode(); 
					gameName = fGame.getName();
					info = fGame.getDesc();
					compatiblePhone = fGame.getModel();
				} else{
					inet.db.game.Game iGame = (inet.db.game.Game)  vListGame.get(j);
					gameId = iGame.getId();
					gameName = iGame.getName();
					info = iGame.getInfo();
					compatiblePhone = iGame.getModel();
				}
				
				info = info.replaceAll("<br/>","");
				info = info.replaceAll("<br>","");
				info = info.replaceAll("<Br>","");
				info = info.replaceAll("<b/>","");
				info = info.replaceAll("<b>","");
				//info = info.replaceAll(fGame.getName(),"");
				info = info.trim();
				if(info.length()>150){
					info = info.substring(0,100) + " ...";
				}
				
				if(isPartnerGame){
					%>
					<div class="noidunggame">
						<a href="fdetail.jsp?id=<%=gameId %>">
<%--								<img src="../fGameImage.gif?type=WAP&id=<%=gameId %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>--%>
							<img src="../javagame-image/fGameImage.gif?type=WAP&id=<%=gameId %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>
						</a>
						<a href="fdetail.jsp?id=<%=gameId %>" style="text-decoration: none;">
							<strong><%=gameName %></strong>
						</a>
						<br/>
						<span style="color:#666666 "><%=info %></span><br/>
						<img src="../images/icon-down.png" align="absmiddle" hspace="5" vspace="3" />
						<a  class="link-tai" href="<%=linkSmsSyntax+mediaCode+inetCode %>"  onclick="sendSMS('<%=smsSyntax+mediaCode+inetCode %>', '<%=serviceNumber %>');">Tải về</a> <br/>
				  		<%if(MobileTool.checkCompatiblePhone(MobileProfilesChecker.getListModelPhone(request), compatiblePhone)){ %>
				  			&nbsp;<em>(Soạn <%=smsSyntax+mediaCode+inetCode %> gửi 8785)</em>
				  		<%}else{ %>
					  		<span style="color:#FF0000 ">
					  		&nbsp;<em>Không thích hợp cho điện thoại của bạn</em>
					  		</span>
				  		<%} %>
						<div class="both"></div>  	
				  	</div>
					<div class="both"></div>
					<%
				} else{
					%>
					<div class="noidunggame">
						<a href="idetail.jsp?id=<%=gameId %>">
							<img src="../iGameImage.gif?type=WAP&id=<%=gameId %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>
						</a>
						<a href="idetail.jsp?id=<%=gameId %>" style="text-decoration: none;">
							<strong><%=gameName %></strong>
						</a>
						<br/>
						<span style="color:#666666 "><%=info %></span><br/>
						<img src="../images/icon-down.png" align="absmiddle" hspace="5" vspace="3" />
						<%if(imNick!=null){ %>
						<a  class="link-tai" href="../freeGame/<%=gameId %>.jar?id=<%=gameId %>" >Tải về</a>
						<%} else{%>
						<a  class="link-tai" href="../login.jsp" >Đăng nhập để tải (0 VNĐ)</a><br/>
						hoặc <em><b>AM 88<%=gameId %></b> gửi <b>8185</b></em><br/>
						(Kết nối GPRS hoặc 3G)
						<%} %>
						<br/>
				  		<%if(MobileTool.checkCompatiblePhone(MobileProfilesChecker.getListModelPhone(request), compatiblePhone)){ %>
				  			
				  		<%}else{ %>
					  		<span style="color:#FF0000 ">
					  		&nbsp;<em>Không thích hợp cho điện thoại của bạn</em>
					  		</span>
				  		<%} %>
						<div class="both"></div>  	
				  	</div>
					<div class="both"></div>
					<%
				
				}
			}
		}
		
		%>
		
		<div class="tieudeDVkhac">
			<div style="padding:4px 0 0 5px; ">HOT GAME</div>
		</div>
		<%
		//GameCat gameFmcCat =null;
		JavaGameCat javaGameCat = null;
		//Vector catList=(Vector)fmcCatDAO.findAllRoots();
		Vector catList = (Vector)javaCatDAO.findAllRoots();
		for(int i=0;i<catList.size();i++){
			javaGameCat=(JavaGameCat)catList.get(i);
		%>
		<div class="menu-theloaikhac"><img src="../images/icon-tron.png" align="absmiddle" hspace="10" vspace="6" />
			<a href="list.jsp?g=f&cid=<%=javaGameCat.getId() %>"><%=javaGameCat.getName() %></a>
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