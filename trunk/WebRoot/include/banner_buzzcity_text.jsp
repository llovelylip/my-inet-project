<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="inet.util.StringTool"%>
<%@page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@page import="org.apache.commons.httpclient.HttpClient"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/include/style.css" rel="stylesheet" type="text/css">

	<%
	String userAgent=request.getHeader("User-Agent");
	String ip=request.getRemoteAddr();
	
	String content=null;
	HttpClient client = new HttpClient();
	userAgent=java.net.URLEncoder.encode(userAgent,"UTF-8");
	ip=java.net.URLEncoder.encode(ip,"UTF-8");
	GetMethod method = new GetMethod("http://ads.buzzcity.net/show.php?get=text&partnerid=43637&a="+userAgent+"&i="+ip);	
	try {
		int statusCode = client.executeMethod(method);
	    byte[] responseBody = method.getResponseBody(); 
	    content=new String(responseBody,"UTF-8");
	    //out.println(content);
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
 	Collection cLabel=StringTool.parseString(content, "\n");
	//out.println("<br/>cLabel.size()="+cLabel.size());
	if(cLabel!=null&&cLabel.size()>=2){
    	Iterator it=cLabel.iterator();
    	String label=(String)it.next();
		String cid=(String)it.next(); 
	%>
	<div class="banner-chuchay">
		<div style="padding-top:5px; ">
			<marquee behavior="scroll" scrollamount="2" direction="left">
				<span style="font-size: 11px;font-variant: normal; ">
					<a style="color: white" href="http://click.buzzcity.net/click.php?partnerid=43637&cid=<%=cid %>"><%=label %></a>
				</span>
			</marquee>
		</div>
	</div>
	<%} %>