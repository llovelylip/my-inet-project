<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.db.game.*"%>
<%@ page import="inet.util.*"%>
<%@ page import="inet.db.javagame.*"%>

<jsp:useBean id="gameDAO" class="inet.db.game.GameDAO" scope="session" />
<jsp:useBean id="javaGameCatDAO" class="inet.db.javagame.JavaGameCatDAO" scope="session" />
<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO" scope="session" />

<%
	request.setCharacterEncoding("UTF-8");
	String sCatId = request.getParameter("catId");
	if(sCatId == null)sCatId = "";
	String dongmay = request.getParameter("dongmay");
	if(dongmay == null)dongmay = "";
	String doimay = request.getParameter("doimay");
	if(doimay == null)doimay = "";
	String ten = request.getParameter("ten");
	if(ten == null) ten = "";
%>
	<link href="style.css" rel="stylesheet" type="text/css">
	<form action="<%=request.getContextPath() %>/timkiem.jsp" method="post">
	<div id="nen-search">
		<div style="padding:10px 8px 5px 8px; ">Th&#7875; lo&#7841;i Java Game </div>
		
		<div style="padding:0 8px 0 8px; ">
		<select class="boxselect" name="catId">
		<%
			//Vector cCats = (Vector)fmcGameCatDAO.findAllRoots(1);
			Vector cCats = (Vector)javaGameCatDAO.findAllRoots(1);
			//GameCat cat=null;
			JavaGameCat cat = null;
			for(int i=0;i<cCats.size();i++){
				cat = (JavaGameCat)cCats.get(i);
	 	%>
		 <option value="<%=cat.getId() %>" <%=cat.getId().toString().equalsIgnoreCase(sCatId)?"selected":""%>><%=cat.getName() %></option>
		
		<%} %>
		</select>
		</div>
		<div style="padding:10px 8px 5px 8px; ">H&atilde;ng di &#273;&#7897;ng</div>
		<div style="padding:0 8px 0 8px; ">
			<select class="boxselect" name="dongmay">
				<option value="Nokia" <%="Nokia".equals(dongmay)?"selected":"" %>>Nokia</option>
				<option value="SamSung" <%="SamSung".equals(dongmay)?"selected":"" %>>Sam Sung</option>
				<option value="Motorola" <%="Motorola".equals(dongmay)?"selected":"" %>>Motorola</option>
				<option value="SonyEricsson" <%="SonyEricsson".equals(dongmay)?"selected":"" %>>SonyEricsson</option>
			</select>
		</div>
		<div style="padding:10px 8px 5px 8px; ">Model di &#273;&#7897;ng</div>
		<div style="padding:0 8px 0 8px; ">
			<input type="text" name="doimay" size="15" value="<%=doimay !=null?doimay:""%>">
		</div>
		<div style="padding:10px 8px 5px 8px; ">T&ecirc;n Game </div>
		<div style="padding:0 8px 0 8px; ">
			<input name="ten" type="text" size="26" maxlength="60" value="<%=ten !=null?ten:""%>">
		</div>
		<div style="text-align:center;padding-top: 10px ">
			<input type="image" src="<%=request.getContextPath() %>/images/gamemobile-nutsearch.gif">
		</div>
	</div>
	</form>
	<div class="titlegreen">
		<div style="padding:7px 0 0 0; ">Top Java Game </div>
	</div>
	<%
		//Collection cTops=(Collection)fmcGameDAO.findTop(16);
		Collection cTops = (Collection)javaGameDAO.findTopWeb(16);
		//inet.db.fmcgame.Game top=null;
		JavaGame top = null;
		if(cTops != null && cTops.size()>0){
			Iterator it = cTops.iterator();
			int i=0;
			while(it.hasNext()){
				top=(JavaGame)it.next();
				i++;
	%>
	<div id="game1<%=i%>" class="ten1game" onclick="showRight(1<%=i%>);">
		<div style="padding:5px; "><a href="javascript:showRight(1<%=i%>);" class="tengame"><%=i %>.<%=top.getName() %></a><br><br>
		<div id="chitiet1<%=i%>" style="display: none;">
		<img width="80" height="80" src="<%=request.getContextPath() %>/javagame-image/<%=UTF8Tool.coDau2KoDau(top.getName()).replaceAll(" ","-") %>.gif?id=<%=top.getId() %>&type=WAP" align="right" hspace="3" vspace="5" width="60" height="71" alt="Java game <%=top.getName() %>" title="Java game <%=top.getName() %>">
		<strong>Mã số:</strong> 9<%=top.getMediaCode()%><%=top.getInetCode() %><br>
		<strong>Download:</strong> <%=top.getDownloadCounter() %><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong> 
		<%=top.getDesc().length()>100?top.getDesc().substring(0,100):top.getDesc()%>
		<a href="<%=request.getContextPath() %>/java-game/<%=top.getId().toString() %>/<%=UTF8Tool.coDau2KoDau(top.getName()).replaceAll(" ","-") %>.html"><img src="<%=request.getContextPath() %>/images/gamemobile-nutchitiet.gif" border="0" vspace="6"></a></div>
		</div>				
	</div>
	<%}
	} %>
	
	<div><a href="http://nhacchuong.sms.vn"><img src="<%=request.getContextPath() %>/images/gamemobile-bannernhacchuong.gif" vspace="3" border="0"></a></div>
	<div class="both"></div>
	<div><a href="http://nhaccho.sms.vn/"><img src="<%=request.getContextPath() %>/images/gamemobile-bannernhaccho.gif" border="0" vspace="3"></a></div>
	<div class="both"></div>
	<div><a href="http://javagame.sms.vn/java-game-Jewel-Miner.jsp"><img src="<%=request.getContextPath() %>/images/kim-cuong.gif" vspace="3" border="0"></a></div>
	<div class="both" ></div>
	<div><a href="http://javagame.sms.vn/java-game/174/Dua-xe-3D.html"><img src="<%=request.getContextPath() %>/images/tay-dua-kiet-xuat.gif" border="0"></a></div>
	
<script type="text/javascript">
	function showRight(id){
		var num = <%=cTops.size()%>;
	 	var i=1;
	 	var var1,var2,var3,var4;
	 	var3="game"+id;
	 	var4="chitiet"+id;
	 	for (i=11;i<=(10+num);i++){
		 	var1="game"+i;
		 	var2="chitiet"+i;		 	
			document.getElementById(var1).className='ten1game';
		 	document.getElementById(var2).style.display='none';
	 	}
	  	document.getElementById(var3).className='gioithieu1game'
	  	document.getElementById(var4).style.display='block';
	} 
</script>			
			