<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.util.DateProc"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="inet.db.sms2.IMNick"%>
<%@page import="inet.util.my.MobileProfilesChecker"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/include/style.css" rel="stylesheet" type="text/css">

<div class="tieude-time">
	<div style="float: left;">
	<img src="<%=request.getContextPath() %>/images/icon-dongho.gif" align="absmiddle" hspace="5" vspace="5" />
	<%	
	Timestamp tsp = DateProc.createTimestamp();
	String sTime = DateProc.Timestamp2HHMM(tsp);
	//out.println(sTime.substring(0,2)+"h:"+sTime.substring(2));
	switch(tsp.getDay()){
		case 0: out.println("CN, "); break;
		case 1: out.println("Thứ 2, "); break;
		case 2: out.println("Thứ 3, "); break;
		case 3: out.println("Thứ 4, "); break;
		case 4: out.println("Thứ 5, "); break;
		case 5: out.println("Thứ 6, "); break;
		case 6: out.println("Thứ 7, "); break;
	}
	out.println(DateProc.Timestamp2DDMMYYYY(tsp).substring(0,5));
	
	String modelMobile = MobileProfilesChecker.getPhoneModel(request);
	String showMobile = "";
	if(modelMobile != null){
		showMobile = "- "+modelMobile+" -";
	}
	
	if(imNick != null){
		if(onMobile){
	%>
			&nbsp;<img src="<%=request.getContextPath() %>/images/icon_mobile.png" align="absmiddle" hspace="5" vspace="0" height="10px"/>
	<%	
		} else{
	%>
			&nbsp;<img src="<%=request.getContextPath() %>/images/icon_user.png" align="absmiddle" hspace="0" vspace="0" height="14px" />
	<%	
		}	
	%>
		
		<a href="<%=request.getContextPath() %>/profile/?blogger=<%=imNick.getNickname() %>" style="color:green; font-weight: bold;"><%=imNick.getNickname() %></a>	
		| <a href="<%=request.getContextPath() %>/login.jsp">Thoát</a>
	<%
	} else{ 
	%>	
		| <a href="<%=request.getContextPath() %>/login.jsp">Đăng nhập</a>
		| <a href="<%=request.getContextPath() %>/register.jsp">Đăng kí</a>
	<%
	} 
	%>
	</div>
	<div style="float: right; padding: 0 1px 3px 0; font-size: 9px; color: gray;"><%=showMobile %></div>
	<div class="both"></div>
</div>