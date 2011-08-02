<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.util.my.Menu3gTool"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/include/style.css" rel="stylesheet" type="text/css">
	
	<%
	
	int real = 0;
	int counter = (new Random()).nextInt(100);
	if(application.getAttribute("counter")!=null){
		real = Integer.parseInt(application.getAttribute("counter").toString());
		counter = Integer.parseInt(application.getAttribute("counter").toString());	
	}
	
	String keyBottom = request.getRequestURI().replaceFirst(request.getContextPath().toString(), "");
	//keyBottom = keyBottom.replaceAll("index.jsp", "");
	keyBottom = keyBottom.substring(1);
	if(keyBottom.indexOf("/")!=-1){
		keyBottom = "/"+keyBottom.substring(0, keyBottom.indexOf("/")+1);
	} else{
		keyBottom = "/"+keyBottom;
	}
	//out.println(keyBottom);
	String strMenuBottom = "";
	Collection bottomMenuColl = Menu3gTool.hashBottomMenu.get(keyBottom);
	if(bottomMenuColl != null && !bottomMenuColl.isEmpty()){
		String strNameMenuBottom = "";
		String strLinkMenuBottom = "";
		for(Iterator it = bottomMenuColl.iterator(); it.hasNext(); ){
			strNameMenuBottom = new String(it.next().toString().getBytes(),"UTF-8");
			if(it.hasNext()){
				strLinkMenuBottom = new String(it.next().toString().getBytes(),"UTF-8");
				//strLinkMenuBottom = strLinkMenuBottom.replaceAll("3g.wap.vn", request.getServerName());
			}
			//out.println("<br/>"+strLinkMenuBottom);
			if("".equalsIgnoreCase(strMenuBottom)){
				strMenuBottom = "<a href=\""+strLinkMenuBottom+"\">"+strNameMenuBottom+"</a>";
			} else{
				strMenuBottom = strMenuBottom + " | <a href=\""+strLinkMenuBottom+"\">"+strNameMenuBottom+"</a>";
			}
		}	
	}
	
	
	
	%>
<center>
	<div class="menu-bottom">
	  <div style="padding-top:5px; ">
	  
	  	<%if("".equalsIgnoreCase(strMenuBottom)){ %>
		  	<a href="http://xoso.wap.vn">Xổ số</a> | 
		  	<a href="http://clip.wap.vn">Clip</a> | 
		  	<a href="http://bongda.wap.vn">Bóng đá</a> | 
		  	<a href="http://mp3.wap.vn">Nhạc MP3</a> 
		<%}else{ %>
			<%=strMenuBottom %>
		<%} %>
		
		
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