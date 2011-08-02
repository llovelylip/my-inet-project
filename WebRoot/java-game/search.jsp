<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.db.javagame.*"%> 
<%@page import="inet.db.game.*"%>
<%@page import="inet.util.my.*"%>
<%@page import="java.math.BigDecimal"%>

<jsp:useBean id="inetGameDAO" class="inet.db.game.GameDAO" scope="session"/>
<jsp:useBean id="inetCatDAO" class="inet.db.game.GameCategoryDAO" scope="session"/>
<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session"/>
<jsp:useBean id="javaCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session"/>

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
	<%
		String linkSmsSyntax = "sms:8785?body=GZ 8";
		String smsSyntax = "GZ 8";
	
    	String sKeyword = request.getParameter("txt_search");
  %>
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
					<input name="txt_search" type="text" value="<%=sKeyword != null ? sKeyword :"" %>" size="25" maxlength="60" class="text-search" />
					<a href="javascript:void(0);" onclick="document.search_game.submit();">
						<img src="../images/search.gif" align="absmiddle" hspace="5" vspace="2" border="0" /></a>
				</div>
			</div>
		</form>
		
		<div class="tieude-tinnoibat">
			<div class="nen-tinnoibat">
				<div style="padding:5px; text-align:left; ">Kết quả tìm kiếm</div>
			</div>
			<div class="both"></div>
		</div>
		<%
			if(sKeyword != null){
	    		sKeyword = sKeyword.trim();
	    	}
	    	if(sKeyword != null && sKeyword.length() > 2 && (sKeyword.charAt(0) == '9' || sKeyword.charAt(0) == '8'))
		    	sKeyword = sKeyword.substring(1);
		    		
	    	String sPage = request.getParameter("page");
			String sRows = request.getParameter("rows");
	    	if (sPage == null || "".equals(sPage.trim())) {
				sPage = "1"; //first page
			}
			if (sRows == null || "".equals(sRows.trim())) {
				sRows = "20";
			}
			int currPage=Integer.parseInt(sPage);
			int rowsPerPage=Integer.parseInt(sRows);
			int totalRecs = javaGameDAO.countWapSearch(sKeyword, sKeyword);
			
			int numOfPages = totalRecs / rowsPerPage;
	    	if (totalRecs % rowsPerPage > 0) numOfPages++;
	    	
			Vector vList = (Vector)javaGameDAO.findByPageWapSearch(sKeyword, sKeyword, currPage, rowsPerPage);
			boolean isJavaGame = false;
			if(vList!=null && vList.size()>0){	
				isJavaGame = true;
			}else{
				totalRecs = inetGameDAO.getCount(sKeyword);
				vList = (Vector)inetGameDAO.findByPage(StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(sKeyword)), currPage, rowsPerPage);
			}
			if(isJavaGame){
				if(vList!=null && vList.size()>0){
					String info = "";
					JavaGame fGame = null;
					for(int j=0; j<vList.size(); j++){
						//inet.db.fmcgame.Game fGame = (inet.db.fmcgame.Game)  vList.get(j);
						fGame = (JavaGame)vList.get(j);
						info = fGame.getDesc();
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
		%>
					<div class="noidunggame">
						<a href="fdetail.jsp?id=<%=fGame.getId() %>">
							<img src="../javagame-image/fGameImage.gif?type=WAP&id=<%=fGame.getId() %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>
						</a>
						<a href="fdetail.jsp?id=<%=fGame.getId() %>" style="text-decoration: none;">
							<strong><%=fGame.getName() %></strong>
						</a>
						<br/>
						<span style="color:#666666 "><%=info %></span><br/>
						<img src="../images/icon-down.png" align="absmiddle" hspace="5" vspace="3" />
						<a  class="link-tai" href="<%=linkSmsSyntax + fGame.getMediaCode()+fGame.getInetCode() %>"  onclick="sendSMS('<%=smsSyntax+fGame.getMediaCode()+fGame.getInetCode() %>', '8785');">Tải về</a> <br/>
				  		<%if(MobileTool.checkCompatiblePhone(MobileProfilesChecker.getListModelPhone(request), fGame.getModel())){ %>
				  			&nbsp;<em>(Soạn <%=smsSyntax+fGame.getMediaCode()+fGame.getInetCode() %> gửi 8785)</em>
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
			} else {
				if(vList!=null&&vList.size()>0){
					String info = "";
					inet.db.game.Game iGame = null;
					for(int i=0; i<vList.size(); i++){
						iGame = (inet.db.game.Game) vList.get(i);
						info = iGame.getInfo();
						info = info.replaceAll("<br/>","");
						info = info.replaceAll("<br>","");
						info = info.replaceAll("<Br>","");
						info = info.replaceAll("<b/>","");
						info = info.replaceAll("<b>","");
						//info = info.replaceAll(iGame.getName(),"");
						info = info.trim();
						if(info.length()>150){
							info = info.substring(0,100) + " ...";
						}
		%>
					<div class="noidunggame">
						<a href="idetail.jsp?id=<%=iGame.getId() %>">
							<img src="../iGameImage.gif?type=WAP&id=<%=iGame.getId() %>" class="border-pic" vspace="5" hspace="5" align="left" width="80px" height="80px"/>
						</a>
						<a href="idetail.jsp?id=<%=iGame.getId() %>" style="text-decoration: none;">
							<strong><%=iGame.getName() %></strong>
						</a>
						<br/>
						<span style="color:#666666 "><%=info %></span><br/>
						<img src="../images/icon-down.png" align="absmiddle" hspace="5" vspace="3" />
						<%BigDecimal gameId = iGame.getId(); %>
						<%if(imNick!=null){ %>
						<a  class="link-tai" href="../freeGame/<%=gameId %>.jar?id=<%=gameId %>" >Tải về</a>
						<%} else{%>
						<a  class="link-tai" href="../login.jsp" >Đăng nhập để tải (0 VNĐ)</a><br/>
						hoặc <em><b>AM 88<%=gameId %></b> gửi <b>8185</b></em><br/>
						(Kết nối GPRS hoặc 3G)
						<%} %>
						<%if(MobileTool.checkCompatiblePhone(MobileProfilesChecker.getListModelPhone(request), iGame.getModel())){ %>
				  			
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
	  	
		<%if(vList == null || vList.size() == 0 ) out.println("<span style=\"color:blue \"><center><small><em>Không tìm thấy game phù hợp</em></small></center></span>"); %>
		
		<%if(numOfPages>1){ %>
			<div class="linktrang" style="padding:2px 4px 4px 4px; text-align:left; ">
			<%
			    for(int ilink = 1; ilink <= numOfPages; ilink++) {
			        if (ilink == currPage) {
			%>
	          	[<b><%=ilink%></b>]
			<%
			        } else {
			%>
	             	[<a class="linktrang" href="?txt_search=<%=sKeyword %>&page=<%=ilink%>"><%=ilink%></a>]
			<%
			        }
			    }
			%>
		</div>	
		<%} %>
		
		<div class="tieudeDVkhac">
			<div style="padding:4px 0 0 5px; ">HOT GAME</div>
		</div>
		
		<%
			//GameCat gameFmcCat =null;
			JavaGameCat javaGameCat = null;
			//Vector catList=(Vector)fmcCatDAO.findAllRoots();
			Vector catList = (Vector)javaCatDAO.findAllRoots();
			for(int i=0;i<catList.size();i++){
				javaGameCat = (JavaGameCat)catList.get(i);
		%>
		<div class="menu-theloaikhac"><img src="../images/icon-tron.png" align="absmiddle" hspace="10" vspace="6" />
			<a href="list.jsp?g=f&cid=<%=javaGameCat.getId() %>"><%=javaGameCat.getName() %></a>
		</div>
		<%} %>
		
		<div class="tieudeDVkhac">
			<div style="padding:4px 0 0 5px; ">FREE GAME</div>
		</div>
		
		<%
			GameCategory gameInetCat = null;
			Vector inetCatList = (Vector)inetCatDAO.findAllRoots();
			for(int i=0;i<inetCatList.size();i++){
				gameInetCat = (GameCategory)inetCatList.get(i);
		%>
		<div class="menu-theloaikhac"><img src="../images/icon-tron.png" align="absmiddle" hspace="10" vspace="6" />
			<a href="list.jsp?g=i&cid=<%=gameInetCat.getId() %>"><%=gameInetCat.getName() %></a>
		</div>
		<%} %>
		
		<%@include file="/include/menu_bottom.jsp" %>
	</body>
</html>