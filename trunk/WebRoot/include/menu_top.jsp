<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.util.my.Menu3gTool"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/include/style.css" rel="stylesheet" type="text/css">
	
	<%
		request.setCharacterEncoding("UTF-8");

		//out.println(request.getRequestURI());
		//out.println("<br/>");
		//out.println(request.getContextPath());
		
		String key = request.getRequestURI().replaceFirst(request.getContextPath().toString(), "");
		//key=key.replaceAll("index.jsp", "");
		key = key.substring(1);
		if(key.indexOf("/")!=-1){
			key = "/"+key.substring(0, key.indexOf("/")+1);
		} else{
			key = "/"+key;
		}
		//out.println("<br/>");
		//out.println("key="+key);
		
		String strMenu = "";
		Collection topMenuColl = Menu3gTool.hashTopMenu.get(key);
		if(topMenuColl != null && !topMenuColl.isEmpty()){
			String strNameMenuTop = "";
			String strLinkMenuTop = "";
			for(Iterator it = topMenuColl.iterator(); it.hasNext(); ){
				strNameMenuTop = new String(it.next().toString().getBytes(),"UTF-8");
				if(it.hasNext()){
					strLinkMenuTop = new String(it.next().toString().getBytes(),"UTF-8");
					//strLinkMenuTop = strLinkMenuTop.replaceAll("3g.wap.vn", request.getServerName());
				}
				if("".equalsIgnoreCase(strMenu)){
					strMenu = "<a href=\""+strLinkMenuTop+"\">"+strNameMenuTop+"</a>";
				} else{
					strMenu = strMenu + " | <a href=\""+strLinkMenuTop+"\">"+strNameMenuTop+"</a>";
				}
			}	
		}
	%>
<center>
	<%if("".equalsIgnoreCase(strMenu)){ %>
	<div class="menu-top">
		<div>
			<a href="<%=request.getContextPath() %>/">Mới nhất</a> | 
			<a href="<%=request.getContextPath() %>/services.jsp">Dịch vụ</a> | 
			<a href="<%=request.getContextPath() %>/giaoluu.jsp">Giao lưu</a> | 
			<a href="<%=request.getContextPath() %>/profile/">Cá nhân</a>
		</div>
	</div>
	<%} else{ %>
	<div class="menu-top">
		<div>
			<%=strMenu %>
		</div>
	</div>
	<%} %>
</center>