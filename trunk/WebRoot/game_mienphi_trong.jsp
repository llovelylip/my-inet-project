<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.game.*"%>
<jsp:useBean id="gameDAO" class="inet.db.game.GameDAO" scope="session" />
<jsp:useBean id="gameCatDAO" class="inet.db.game.GameCategoryDAO" scope="session" />
<%
	Collection cRoots = gameCatDAO.findAllRoots(1);
	String sCatId=request.getParameter("catId");
	GameCategory cat=null;
	if(sCatId!=null&&!sCatId.equals("null")){
		cat=gameCatDAO.getRow(new BigDecimal(sCatId));
	}
	if(cat==null){
		cat=(GameCategory)cRoots.iterator().next();
	}
	String sPage = request.getParameter("page");
	String sRows = request.getParameter("rows");
	int totalRecs = gameDAO.getCount(cat.getId());
	if (sPage == null || "".equals(sPage.trim())) {
		sPage = "1"; //first page
	}
	if (sRows == null || "".equals(sRows.trim())) {
		sRows = "10";
	}
	int currPage=Integer.parseInt(sPage);
	int rowsPerPage=Integer.parseInt(sRows);
	Collection cGames=gameDAO.findByPage(cat.getId(),currPage,rowsPerPage);
	Game game=null;
	Iterator it=cGames.iterator();
%>
	<div class="title-center" style="padding-top: 10px;">
	<h1 style="font-size: 13px; color: #333333;">
<%
	Iterator itRoots=cRoots.iterator();
  	GameCategory root=null;
		while(itRoots.hasNext()){
			root=(GameCategory)itRoots.next();
 %>
	  <a href="<%=request.getContextPath() %>/game-mien-phi/<%=root.getId() %>/<%=root.getName().replaceAll(" ","-") %>.html" >
	  <span style="color: #333333;"><%=root.getName() %></span>
	  </a> &nbsp;&nbsp;
	  <%} %> 
	</h1>
</div>
<%
	while(it.hasNext()){
%>
	<div style="width:550px; padding-top:10px; background-color:#FFFFFF; ">
		<%game=(Game)it.next(); %>
		<div class="GTgame-center break">
		<img src="<%=request.getContextPath() %>/gameservlet?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
		<a href="<%=request.getContextPath() %>/game-mien-phi-chi-tiet/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br><br>
		<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> <%=game.getInfo().length()>200?game.getInfo().substring(0,200):game.getInfo() %>
		
		</div>
		
		<div style="width:10px; float:left; ">&nbsp;</div>
		<%if(it.hasNext()){
			game=(Game)it.next(); %>
		<div class="GTgame-center break">
		<img src="<%=request.getContextPath() %>/gameservlet?id=<%=game.getId() %>" align="right" class="boder-pic" alt="Java game: <%=game.getName() %>" title="Java game: <%=game.getName() %>">
		<a href="<%=request.getContextPath() %>/game-mien-phi-chi-tiet/<%=game.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ","-") %>.html" class="tengame"><%=game.getName() %></a><br><br>
		<strong>Download:</strong> <%=game.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong><%=game.getInfo().length()>200?game.getInfo().substring(0,200):game.getInfo() %>
		
		</div>
		<%} %>
		<div class="both"></div>
	</div>
<%} %>	
<%
 	int numOfPages = 0;
	numOfPages = (totalRecs%rowsPerPage != 0)?(totalRecs / rowsPerPage +1):(totalRecs / rowsPerPage);
	if (totalRecs <= rowsPerPage) numOfPages = 1;
	if (totalRecs == 0) numOfPages = 0;
	if (currPage > numOfPages) currPage = numOfPages;
	if(currPage<0) currPage=1;
	 %>
	<div  style="display:block; background-color: #FFFCEF;">
		<%
		if (numOfPages > 10) {
			int PrevPage = currPage - 1;
			if (PrevPage < 1) { PrevPage = 1;}
			int NextPage = currPage + 1;
			if (NextPage > numOfPages) { NextPage = numOfPages;}
			int LastPage = numOfPages;
			int FirstPage = 1;
			%>
			
		<!--first page button-->
			<% if (currPage==FirstPage) { %>
			<img src="<%=request.getContextPath()%>/images/relax_first.png" alt="First" border="0" align="absmiddle" hspace="3" />
			<% }else{ %>
			<a href="<%=request.getContextPath() %>/game-mien-phi/<%=cat.getId() %>/<%=FirstPage %>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(" ","-") %>.html"><img src="<%=request.getContextPath()%>/images/relax_first.png" alt="First"  border="0" align="absmiddle" hspace="3" /></a>
			<% } %>
		<!--previous page button-->
			<% if (PrevPage == currPage) { %>
			<img src="<%=request.getContextPath()%>/images/relax_prev.png" alt="Previous"  border="0" align="absmiddle" hspace="3" />
			<% }else{ %>
			<a href="<%=request.getContextPath() %>/game-mien-phi/<%=cat.getId() %>/<%=PrevPage %>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(" ","-") %>.html"><img src="<%=request.getContextPath()%>/images/relax_prev.png" alt="Previous"  border="0" align="absmiddle" hspace="3" /></a>
			<% } %>
		<!--current page number-->
			<input name="p" type="text" style="height:15px; background-color: #F4F1E3; font-size: 10px; vertical-align: middle; border: 0px" value="<%=currPage%>" size="1.5" readonly="readonly"  />/<input name="countpage" type="text" style="height:15px; background-color: #F4F1E3; font-size: 10px; vertical-align: middle; border: 0px" value="<%=numOfPages%>" size="1.5" readonly="readonly"  />
		<!--next page button-->
			<% if (NextPage == currPage) { %>
			<img src="<%=request.getContextPath()%>/images/relax_next.png" alt="Next"  border="0" align="absmiddle" hspace="3" />
			<% }else{ %>
			<a href="<%=request.getContextPath() %>/game-mien-phi/<%=cat.getId() %>/<%=NextPage %>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(" ","-") %>.html"><img src="<%=request.getContextPath()%>/images/relax_next.png" alt="Next"  border="0" align="absmiddle" hspace="3"  /></a>
			<% } %>
		<!--last page button-->
			<% if (LastPage == currPage) { %>
			<img src="<%=request.getContextPath()%>/images/relax_last.png" alt="Last"  border="0" align="absmiddle" hspace="3" />
			<% }else{ %>
			<a href="<%=request.getContextPath() %>/game-mien-phi/<%=cat.getId() %>/<%=LastPage %>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(" ","-") %>.html" ><img src="<%=request.getContextPath()%>/images/relax_last.png" alt="Last"  border="0" align="absmiddle" hspace="3" /></a>
			<% } %>
		<% }else if(numOfPages>1){ 
				//for(int k=1; k <= numOfPages;k++){
				for(int k=numOfPages; k >= 1;k--){
					if(k!=currPage){
		%>
					<div style="float:right; border:1px solid #F7E8B9;height:15px;margin:0px auto; ">
						&nbsp;<a href="<%=request.getContextPath() %>/game-mien-phi/<%=sCatId %>/<%=k%>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(" ","-") %>.html"><%=k %></a>&nbsp;
					</div>
					<div style="float:right;">
						&nbsp;
					</div>
		<% 			} else{%>
					<div style="float:right; background-color:#FCF2D4; border:1px solid #EF3E00;height:15px;margin:0px auto; font-weight: bolder; color:red;">
						&nbsp;<%=k %>&nbsp;
					</div>
					<div style="float:right;">
						&nbsp;
					</div>
		<%	
					}
				}
		   } %>	
		<div style="clear:both;"></div>
	</div>	
	
	<div style="text-align:center; "><img src="<%=request.getContextPath() %>/images/gamemobile-loingo.jpg" vspace="15" alt="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung." title="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung."></div>