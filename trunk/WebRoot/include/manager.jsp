	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	
	
	<% 
		request.setCharacterEncoding("UTF-8");
		
		String link2Back = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
		if(request.getQueryString()!=null){
			link2Back = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
		} else{
			link2Back = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
		}
		
		if(link2Back.indexOf("login.jsp") == -1){
			session.setAttribute("back_page", link2Back);
			session.setMaxInactiveInterval(1000*60*15);
			//out.println("<br/>Manager: "+link2Back);
		}
		//out.println("<br/>"+request.getRealPath(link2Back));
		//out.println("<br/>"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI());
		
	%>