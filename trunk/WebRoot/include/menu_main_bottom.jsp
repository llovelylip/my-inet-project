<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/include/style.css" rel="stylesheet" type="text/css">
	
	<%
	
	int real = 0;
	int counter = (new Random()).nextInt(100);
	if(application.getAttribute("counter")!=null){
		real = Integer.parseInt(application.getAttribute("counter").toString());
		counter = Integer.parseInt(application.getAttribute("counter").toString());	
	}
	
	%>
<center>
	<div class="menu-bottom">
	  <div style="padding-top:5px; ">
	  	<a href="http://mp3.wap.vn">Nhạc MP3</a> | 
	  	<a href="../san-pham/">Sản phẩm</a> | 
	  	<a href="../quiz/">Quiz</a> | 
	  	<a href="../ket-ban/">Làm quen</a>
	  	<br/>
	    <br/>
		© 2011 iNET.vn   -   8x85 - Hỗ trợ: 1900561588<br/>
	  	User online: <%=counter %>
	  </div>
	  
	  	<%if(request.getAttribute("page") != null && "index".equalsIgnoreCase(request.getAttribute("page").toString())){ %>
	  	<div class="hinh" style="text-align: center;">
			Giấy phép số: 158/GP-TTĐT<br/> 
			Cấp ngày: 29/07/2010<br/><br/>
		</div>
		<%} %>
	</div>
</center>