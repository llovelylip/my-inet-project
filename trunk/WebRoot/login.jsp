<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.util.Md5"%> 
<%@page import="inet.chat.SessionListenner"%>
<%@page import="inet.chat.IMGroup" %>

<jsp:useBean id="imNickDAO" class="inet.db.sms2.IMNickDAO" scope="session"/>
<jsp:useBean id="imGroupDAO" class="inet.db.sms2.IMGroupDAO" scope="session" />

<%
		request.setCharacterEncoding("utf-8");
		String sAction = request.getParameter("action");
		String messLogin = "";
		
		
		if("login".equalsIgnoreCase(sAction)){
			String sUsername = request.getParameter("txt_username");
			String sPassword = request.getParameter("txt_pwd");
			//out.println(sUsername+" - "+sPassword);
			
			if (sUsername != null && sPassword != null && !"".equals(sUsername.trim()) && !"".equals(sPassword.trim())) {			
				// Tim nickname trong db
				IMNick imNick = imNickDAO.getRow(sUsername);			
				// Neu tim thay
				if (imNick != null) {
					// Neu dung password
					if (imNick.getPassword().equals(Md5.Hash(sPassword))) {
						if(imNick.getStatus()!=0){
							imNick.setIp(request.getRemoteHost());			
							imNick.setIsOnline(1);
							session.setAttribute("INET_CHAT_USER", imNick);
							// Max time session alive
							// Dùng cho dịch vụ CHAT
							session.setMaxInactiveInterval(900);					
							SessionListenner.addOnline(imNick.getNickname(), true);					
							imNickDAO.setOnline(imNick.getNickname(), true);
							// Dùng cho dịch vụ BLOG
							IMGroup group = imGroupDAO.getGroup(imNick.getNickname(), IMGroup.DEFAULT_GROUP);
							if (group == null) {
								imGroupDAO.insertGroup(IMGroup.DEFAULT_GROUP, imNick.getNickname());
							}
							
							if(session.getAttribute("page_back")==null){
								response.sendRedirect(request.getContextPath()+"/");
							} else{
								String sPageBack = session.getAttribute("page_back").toString();
								if("blog".equalsIgnoreCase(sPageBack)){
									response.sendRedirect(request.getContextPath()+"/blog/");	
								}else{
									response.sendRedirect(request.getContextPath()+"/");
								}
							}
						} else{
							//Status == 0 khi nick bị khóa
							//request.setAttribute("SYSTEM_MESS", "<div class='textnormal acenter'><font color='#ff0000'>Tài khoản đã bị khóa!</font></div>");
							//forwardToPage(jspPage, request, response);
							messLogin = "<div class='tieude-time' style='text-align:center;'><font color='#ff0000'>Tài khoản đã bị khóa!</font></div>";
						}
					} else {
						//request.setAttribute("SYSTEM_MESS", "<div class='textnormal acenter'><font color='#ff0000'>Sai mật khẩu!</font></div>");
						//forwardToPage(jspPage, request, response);
						messLogin = "<div class='tieude-time' style='text-align:center;'><font color='#ff0000'>Sai mật khẩu!</font></div>";
					}				
				} else {
					//request.setAttribute("SYSTEM_MESS", "<div class='textnormal acenter'><font color='#ff0000'>Không tồn tại username: "+nickname+" !</font></div>");
					//forwardToPage(jspPage, request, response);
					messLogin = "<div class='tieude-time' style='text-align:center;'><font color='#ff0000'>Không tồn tại username: "+sUsername+"</font></div>";
				}		
			}
			
		} else if("logout".equalsIgnoreCase(sAction)){
			session.removeAttribute("INET_CHAT_USER");
		}
%>
		

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title> Login to Wap 3g | Mobile | SMS | MP3 | Nhạc chuông | Nhạc chờ | Game </title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta name="copyright" content="iNET.vn"/>
		<meta name="google-site-verification" content="7wvuciH5Fqj1xtXvGUFZNWIJXjuEAMhNJM0B45i7VR0" />
		<meta name="keywords" content="wap, 3G, di dong, internet, mobile, nhac chuong, nhạc chuông, mp3, nhạc mp3, download, nghe nhac, tải nhạc, tai nhac, ringtone, hình nền, hinh nen, logo, logo mạng, chat, blog, tin tuc, tin tức, video, clip, java game, xổ số, xo so, bóng đá, bong da, chứng khoán, chung khoan" />
		<meta name="description" content="WAP 3G - Thế giới giải trí trên mobile - Hoàn toàn miễn phí - Tải nhạc chuông, nhạc MP3, Xố Số, Bóng đá, Chứng khoán" />
		<meta name="ROBOTS" content="INDEX, FOLLOW" />
		<meta name="author" content="iNET corporation"/> 
	    <meta http-equiv="Cache-Control" content="no-cache"/> 
	    <meta http-equiv="refresh" content="3600"/> 
		<link rel="stylesheet" type="text/css" href="include/styles.css" />
		<script type="text/javascript">
			function Login2(){
				//alert('Login');
				if(isblank(document.frm_login.txt_username.value)){
					alert("Vui lòng nhập tên nick!");
					document.frm_login.txt_username.focus();
					return false;
				}
				if(isblank(document.frm_login.txt_pwd.value)){
					alert("Vui lòng nhập mật khẩu!");
					document.frm_login.txt_pwd.focus();
					return false;
				}
				frm_login.action.value='login';
				frm_login.submit();
			}
			function Login(){
				//alert('Login');
				if(document.frm_login.txt_username.value==''){
					alert("Vui lòng nhập tên nick!");
					document.frm_login.txt_username.focus();
					return false;
				}
				if(document.frm_login.txt_pwd.value==''){
					alert("Vui lòng nhập mật khẩu!");
					document.frm_login.txt_pwd.focus();
					return false;
				}
				frm_login.action.value='login';
				frm_login.submit();
			}
			function Logout(username){
				//alert('Login');
				if(confirm('Bạn muốn thoát khỏi tài khoản '+username+' trên wap 3G ? \nOk để thoát!')){
					frm_login.action.value='logout';
					frm_login.submit();
				}
			}
			function isblank(s){
				for(var i = 0; i < s.length; i++) {
					var c = s.charAt(i);
					if ((c != ' ') && (c != '\n') && (c != '\t')) return false;
				}
				return true;
			}
			function focus(){
				document.frm_login.txt_username.focus();
			}
		</script>
		
	</head>
	<body onload="focus();" topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0">
		<%@include file="include/header.jsp" %>
		<%@include file="include/freesms.jsp" %>
		<%@include file="include/menu_main_top.jsp" %>
	
		<form name="frm_login" method="post" action="">
		<input name="action" type="hidden" />
		<%if(imNick != null){ %>
			<div class="hinh">
				<br/>
				Xin chào <%=imNick.getNickname() %>!<br/>
				Bạn muốn <a href="javascript:Logout('<%=imNick.getNickname() %>')">thoát</a> để đăng nhập với tài khoản khác ?<br/>
				<br/>
			</div>
			<div class="both"></div>
		<%
		}else{
			out.println(messLogin); 
		%>
			
			<div style="width:100%;font:normal 10px Verdana, Arial, Helvetica, sans-serif; color:#000000; height:25px; "> 
				<div style="padding:5px; ">
					<div style="width:70px; float:left; ">Nick sms</div>
					<div style="float:right; text-align:left;">
						<input name="txt_username" type="text" size="25" maxlength="60" class="text-search" />
					</div>
					<div class="both"></div>
				</div>
			</div>
			
			<div style="width:100%;font:normal 10px Verdana, Arial, Helvetica, sans-serif; color:#000000; height:25px; "> 
				<div style="padding:5px; ">
					<div style="width:70px; float:left; ">Password</div>
					<div style="float:right; text-align:left;">
						<input name="txt_pwd" type="password" size="25" maxlength="60" class="text-search" />
					</div>
					<div class="both"></div>
				</div>
			</div>
			<div class="both"></div>
			<div style="width:100%;font:normal 10px Verdana, Arial, Helvetica, sans-serif; color:#000000; height:30px; "> 
				<div style="padding:5px; ">
					<div style="width:70px; float:left; ">&nbsp;</div>
					<div style="float:left; text-align:left;">
						<a href="javascript:Login()"><img src="images/nut-dangnhap.gif" border="0" /></a>
					</div>
					<div class="both"></div>
				</div>
			</div>
			<div class="both"></div>
			<div style="font:normal 10px Verdana, Arial, Helvetica, sans-serif; color:#003970; text-align:left;">
				<div style="padding:5px; ">
					Bạn chưa có nickname ??? 
					<a href="register.jsp"><img src="images/nut-dangky.gif" align="absmiddle" vspace="3" hspace="5" border="0" /></a> 
					để nhắn tin miễn phí tại <a href="http://3g.wap.vn" style="text-decoration: none;"><span style="color:#d13600;">http://3g.wap.vn</span></a>
				</div>
			</div>
			<div class="both"></div>
		<%} %>
		</form>
		
		<%@include file="include/menu_main_bottom.jsp" %>
	</body>
</html>

