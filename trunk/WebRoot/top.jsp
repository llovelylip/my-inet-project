<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.fmcgame.*"%>
<%@ page import="inet.db.javagame.*"%>

<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session" />

<%
	//Collection cTops = fmcGameDAO.findTop(4);
	Collection cTops = javaGameDAO.findTopWeb(4);
	Iterator it = cTops.iterator();
%>
	<div class="title-center">
		<div style="padding:10px; "><h1 style="font-size: 13px; color: #333333;"><a href="http://javagame.sms.vn"><span style="color: #333333;">Java game</span></a> <img src="<%=request.getContextPath() %>/images/icon-muiten.png" align="absmiddle" hspace="5"> <span style="color: #333333;">Top Game</span></h1></div>
	</div>
	<%while(it.hasNext()){ %>
	<div style="width:550px; padding-top:10px; background-color:#FFFFFF; ">
	   <%if(it.hasNext()){
	   		//Game game=(Game)it.next();
	   		JavaGame game = (JavaGame)it.next();
	    %>	
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
<%--		<img src="<%=request.getContextPath() %>/game-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=game.getName() %>" title="Java game mobile,Game <%=game.getName() %>">--%>
		<img width="128" height="128" src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=game.getName() %>" title="Java game mobile,Game <%=game.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br>
	   	<%--<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		
		<strong>Gi&#7899;i thi&#7879;u:</strong>--%> <%=game.getDesc().length()>110?game.getDesc().substring(0,game.getDesc().indexOf(" ",100))+"...":game.getDesc() %>
		
		</div>
		<%} %>
		<div style="width:10px; float:left; ">&nbsp;</div>
		<%if(it.hasNext()){
	   		//Game game=(Game)it.next();
	   		JavaGame javagame = (JavaGame)it.next();
	    %>
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
		<img width="128" height="128" src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.gif?id=<%=javagame.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=javagame.getName() %>" title="Java game mobile,Game <%=javagame.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=javagame.getMediaCode()%><%=javagame.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=javagame.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=javagame.getName() %></a><br>	
		
		<%--<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		
		<strong>Gi&#7899;i thi&#7879;u:</strong>--%><%=javagame.getDesc().length()>110?javagame.getDesc().substring(0,javagame.getDesc().indexOf(" ",100))+"...":javagame.getDesc() %>
		
		</div>
		<%} %>
		
	</div>
	<div class="both"></div>
	<%} %>
	
	
	
	
	<div style="height:15px; "></div>
	<div class="title-center">
		<div style="padding:10px; "><h1 style="font-size: 13px; color: #333333;">Java Game m&#7899;i nh&#7845;t</h1></div>
	</div>
<%
	//Collection cNews=fmcGameDAO.findNewest(4);
	Collection cNews = javaGameDAO.findNewest(4);
	if(cNews!=null && cNews.size() > 0){
		Iterator itNews = cNews.iterator();
 %>	
	<%while(itNews.hasNext()){ %>
	<div style="width:550px; padding-top:10px; background-color:#FFFFFF; ">
	   <%
	   		//Game game=(Game)itNews.next();
	   		if(itNews.hasNext()){
	   			JavaGame game = (JavaGame)itNews.next();
	    %>	
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
<%--		<img src="<%=request.getContextPath() %>/game-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=game.getName() %>" title="Java game mobile,Game <%=game.getName() %>">--%>
		<img width="128" height="128" src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=game.getName() %>" title="Java game mobile,Game <%=game.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br>
		<%--<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong>--%> <%=game.getDesc().length()>110?game.getDesc().substring(0,game.getDesc().indexOf(" ",100))+"...":game.getDesc() %>
		</div>
		<%} %>
		<div style="width:10px; float:left; ">&nbsp;</div>
		<%if(itNews.hasNext()){
	       //game=(Game)itNews.next();
	       JavaGame game = (JavaGame)itNews.next();
	    %>
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
		<img width="128" height="128" src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.gif?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game mobile,Game <%=game.getName() %>" title="Java game mobile,Game <%=game.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br>
		<%--<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong>--%><%=game.getDesc().length()>110?game.getDesc().substring(0,game.getDesc().indexOf(" ",100))+"...":game.getDesc() %>
		
		</div>
		<%} %>
		
	</div>
	<div class="both"></div>
	<%}
	} %>
	
	<div style="text-align:center; "><img src="images/gamemobile-loingo.jpg" vspace="15"  alt="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung." title="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung."></div>