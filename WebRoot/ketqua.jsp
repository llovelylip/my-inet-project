<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.javagame.*"%>

<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session" />
<jsp:useBean id="javaGameCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session" />
<jsp:useBean id="gameDAO" class="inet.db.game.GameDAO" scope="session" />

<%
	request.setCharacterEncoding("UTF-8");
	String sCatId=request.getParameter("catId");
	String dongmay=request.getParameter("dongmay");
	String doimay=request.getParameter("doimay");
	String ten=request.getParameter("ten");
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
	//int totalRecs = fmcGameDAO.getCount(new BigDecimal(sCatId),dongmay,doimay,ten).intValue();
	int totalRecs = javaGameDAO.getCount(new BigDecimal(sCatId),dongmay,doimay,ten).intValue();
	//Collection cGames=fmcGameDAO.findByPage(new BigDecimal(sCatId),dongmay,doimay,ten,currPage,rowsPerPage);
	Collection cGames = javaGameDAO.findByPage(new BigDecimal(sCatId),dongmay,doimay,ten,currPage,rowsPerPage);
	Iterator it = null;
	boolean isJavaGame = false;
	JavaGame javagame=null;
	inet.db.game.Game game = null;
	if(cGames!=null && cGames.size()>0){
		it = cGames.iterator();
		isJavaGame = true;
	}else{
		totalRecs = gameDAO.getCount(new BigDecimal(sCatId),dongmay,doimay,ten);
		cGames = gameDAO.findByPage(new BigDecimal(sCatId),dongmay,doimay,ten, currPage, rowsPerPage);
		it = cGames.iterator();
	}
%>
	<div class="title-center">
		<div style="padding:10px; "><h1 style="font-size: 13px; color: #333333;"><a href="http://javagame.sms.vn"><span style="color: #333333;">Java game</span></a> <img src="<%=request.getContextPath() %>/images/icon-muiten.png" align="absmiddle" hspace="5">Kết quả tìm kiếm</h1></div>
	</div>
<%
	if(isJavaGame && cGames != null && cGames.size() > 0){
		while(it.hasNext()){
%>
	<div style="width:550px; padding-top:10px; background-color:#FFFFFF; ">
		<% javagame = (JavaGame)it.next(); %>
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
		<img width=128 height=128 src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.gif?id=<%=javagame.getId() %>" align="right" class="boder-pic" alt="Java game: <%=javagame.getName() %>" title="Java game: <%=javagame.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=javagame.getMediaCode()%><%=javagame.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=javagame.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=javagame.getName() %></a><br><br>
		<strong>Hãng:</strong> <%=dongmay %><br>
		
		<strong>Download:</strong> <%=javagame.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong><%=javagame.getDesc().length()>110?javagame.getDesc().substring(0,javagame.getDesc().indexOf(" ",100))+"...":javagame.getDesc() %>
		</div>
		
		<div style="width:10px; float:left; ">&nbsp;</div>
		<%if(it.hasNext()){
			javagame = (JavaGame)it.next(); %>
		<div class="GTgame-center break">
		<div style="float:right;text-align:center">
		<img width=128 height=128 src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.gif?id=<%=javagame.getId() %>" align="right" class="boder-pic" alt="Java game: <%=javagame.getName() %>" title="Java game: <%=javagame.getName() %>">
		<br>
		<strong>Mã số:</strong> 9<%=javagame.getMediaCode()%><%=javagame.getInetCode() %>
		</div>
		<a href="<%=request.getContextPath() %>/java-game/<%=javagame.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(javagame.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=javagame.getName() %></a><br><br>
		<strong>Hãng:</strong> <%=dongmay %><br>
		
		<strong>Download:</strong> <%=javagame.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> <%=javagame.getDesc().length()>110?javagame.getDesc().substring(0,javagame.getDesc().indexOf(" ",100))+"...":javagame.getDesc() %>
		</div>
		
		<%} %>
		<div class="both"></div>
	</div>
<%	
		}
	}else if(cGames!=null&&cGames.size()>0){
	 	while(it.hasNext()){
%>	
 	<div style="width:550px; padding-top:10px; background-color:#FFFFFF; ">
		<%game=(inet.db.game.Game)it.next(); %>
		
		<div class="GTgame-center break">
		<img src="<%=request.getContextPath() %>/gameservlet?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
		<a href="<%=request.getContextPath() %>/game-mien-phi-chi-tiet/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br><br>
		<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> <%=game.getInfo().length()>110?game.getInfo().substring(0,game.getInfo().indexOf(" ",100))+"...":game.getInfo() %>
		</div>
		
		<div style="width:10px; float:left; ">&nbsp;</div>
		<%if(it.hasNext()){
			game=(inet.db.game.Game)it.next(); %>
		<div class="GTgame-center break">
		<img src="<%=request.getContextPath() %>/gameservlet?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
		<a href="<%=request.getContextPath() %>/game-mien-phi-chi-tiet/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br><br>
		<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> <%=game.getInfo().length()>110?game.getInfo().substring(0,game.getInfo().indexOf(" ",100))+"...":game.getInfo() %>
		</div>
		<%} %>
		<div class="both"></div>
	</div>
  <%}
  }else{
  		out.println("<br/><div style='color:blue' align='center'>Không tìm thấy game nào phù hợp</div>");
  } 
  %>
	<div style="text-align:center; "><img src="images/gamemobile-loingo.jpg" vspace="15" alt="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung." title="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung."></div>