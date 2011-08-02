<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.db.game.*"%>
<%@ page import="inet.util.*"%>
<%@ page import="inet.db.javagame.*"%>

<jsp:useBean id="gameDAO" class="inet.db.game.GameDAO" scope="session" />
<jsp:useBean id="javaGameCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session" />

	<div class="titlegray">
		<div>Th&#7875; lo&#7841;i Java Game </div>
	</div>

<%
	//Vector cCats=(Vector)fmcGameCatDAO.findAllRoots(1);
	Vector cCats = (Vector)javaGameCatDAO.findAllRoots(1);
	JavaGameCat cat = null;
	for(int i = 0; i < cCats.size(); i++){
		cat=(JavaGameCat)cCats.get(i);
%>
	<div class="menugray"><img src="<%=request.getContextPath() %>/images/icon-tamgiac.png" align="absmiddle" hspace="10" vspace="6">
	<a href="<%=request.getContextPath() %>/the-loai-game/<%=cat.getId() %>/<%=UTF8Tool.coDau2KoDau(cat.getName().trim()).replaceAll(" ","-") %>.html"><%=cat.getName() %></a>
	</div>
	<%} %>
	<div style="padding-top:5px; "><a href="<%=request.getContextPath() %>/game-mien-phi/"><img src="<%=request.getContextPath() %>/images/gamemobile-titlegamefree.gif" border="0"></a></div>
<%
	Vector frees = (Vector)gameDAO.findFreeGame(1,10);
	inet.db.game.Game free=null;
	for(int j=0;j<frees.size();j++){
		free=(inet.db.game.Game)frees.get(j);
%>
	<div id="game<%=j+1 %>" class="ten1game" onclick="showLeft(<%=j+1 %>);">
		<div style="padding:5px; "><a href="javascript:showLeft(<%=j+1 %>);" class="tengame"><%=j+1 %>.<%=free.getName() %></a><br><br>
		<div id="chitiet<%=j+1 %>" style="display: none;">
		<img src="<%=request.getContextPath() %>/gameservlet/<%=UTF8Tool.coDau2KoDau(free.getName()).replaceAll(" ","-") %>.gif?id=<%=free.getId() %>" align="right" hspace="3" vspace="5" width="60" height="71">
		<strong>Mã số:</strong> 98<%=free.getId() %><br>
		<strong>Download:</strong> <%=free.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> 
		<%=free.getInfo().length()>=100?free.getInfo().substring(0,100):free.getInfo() %>
		<br>
		<a href="<%=request.getContextPath() %>/game-mien-phi-chi-tiet/<%=free.getId() %>/<%=UTF8Tool.coDau2KoDau(free.getName().trim().replaceAll(" ","-")) %>.html"><img src="<%=request.getContextPath() %>/images/gamemobile-nutchitiet.gif" border="0" vspace="6"></a></div>
		</div>	
	</div>
<%} %>
	<div style="padding-top:5px; "><a href="http://sms.vn/gprs/"><img src="<%=request.getContextPath() %>/images/gamemobile-bannerGPRS.gif" border="0"></a></div>
	<div class="both"></div>
	<div style="padding-top:5px; "><a href="http://javagame.sms.vn/java-game-co-tuong.jsp"><img src="<%=request.getContextPath() %>/images/co-tuong.gif" border="0"></a></div>

<script type="text/javascript">
	function showLeft(id){
	 	var i=1;
	 	var var1,var2,var3,var4;
	 	var3="game"+id;
	 	var4="chitiet"+id;
	 	for (i=1;i<=10;i++){
		 	var1="game"+i;
		 	var2="chitiet"+i;
			document.getElementById(var1).className='ten1game';
		 	document.getElementById(var2).style.display='none';
	 	}
	  	document.getElementById(var3).className='gioithieu1game'
	  	document.getElementById(var4).style.display='block';
	} 
</script>			
			