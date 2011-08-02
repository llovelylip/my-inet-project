<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="inet.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="inet.db.javagame.*"%>

<jsp:useBean id="javaGameDAO" class="inet.db.javagame.JavaGameDAO"
	scope="session" />
<jsp:useBean id="javaGameCatDAO" class="inet.db.javagame.JavaGameCatDAO"
	scope="session" />

<%
	String sCatId = request.getParameter("catId");
	//GameCat cat = null;
	JavaGameCat cat = null;
	if (sCatId != null) {
		cat = javaGameCatDAO.getRow(new BigDecimal(sCatId));
	}
	String sPage = request.getParameter("page");
	//int totalRecs = fmcGameDAO.getCount(new BigDecimal(sCatId)).intValue();
	int totalRecs = javaGameDAO.count(new BigDecimal(sCatId), null);
	int currPage = Integer.parseInt(sPage);
	int rowsPerPage = 10;
	//Collection cGames = fmcGameDAO.findByPage(new BigDecimal(sCatId),currPage,rowsPerPage);
	Collection cGames = javaGameDAO.findByPage(new BigDecimal(sCatId),
			null, currPage, rowsPerPage);
	//Game game = null;
	JavaGame game = null;
	Iterator it = cGames.iterator();
%>
<div class="title-center">
	<div style="padding: 10px;">
		<h1 style="font-size: 13px;">
			<a href="http://javagame.sms.vn"><span style="color: #333333;">Java
					game</span>
			</a>
			<img src="<%=request.getContextPath()%>/images/icon-muiten.png"
				align="absmiddle" hspace="5"><%=cat.getName()%></h1>
	</div>
</div>
<%
	while (it.hasNext()) {
%>
<div style="width: 550px; padding-top: 10px; background-color: #FFFFFF;">
	<%
		game = (JavaGame) it.next();
	%>
	<div class="GTgame-center break">
		<div style="float: right; text-align: center">
			<img width="128" height="128"
				src="<%=request.getContextPath()%>/javagame-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(" ",
								"-")%>.gif?id=<%=game.getId()%>"
				align="right" class="boder-pic"
				alt="Java game: <%=game.getName()%>"
				title="Java game: <%=game.getName()%>">
			<br>
			<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode()%>
		</div>
		<a
			href="<%=request.getContextPath()%>/java-game/<%=game.getId().toString()%>/<%=UTF8Tool.coDau2KoDau(game.getName().trim())
								.replaceAll(" ", "-")%>.html"
			class="tengame"><%=game.getName()%></a>
		<br>
		<br>

		<strong>Download:</strong>
		<%=game.getDownloadCounter()%><br>
		<strong>Gi&#7899;i thi&#7879;u:</strong>
		<%
			String desc = game.getDesc();
			int index = -1;
			if(desc.length() > 0)
				index = desc.indexOf("<br>");
			
			if(index != -1){				
				desc = desc.substring(0, index);
				//System.out.println(index + " : " + desc);
			}				
			if(desc != null && desc.length() > 100)
				desc = desc.substring(0, 100);
			out.println(desc + "...");
		%>		
	</div>

	<div style="width: 10px; float: left;">
		&nbsp;
	</div>
	<%
		if (it.hasNext()) {
				game = (JavaGame) it.next();
	%>
	<div class="GTgame-center break">
		<div style="float: right; text-align: center">
			<img width="128" height="128"
				src="<%=request.getContextPath()%>/javagame-image/<%=UTF8Tool.coDau2KoDau(game.getName()).replaceAll(
									" ", "-")%>.gif?id=<%=game.getId()%>"
				align="right" class="boder-pic"
				alt="Java game: <%=game.getName()%>"
				title="Java game: <%=game.getName()%>">
			<br>
			<strong>Mã số:</strong> 9<%=game.getMediaCode()%><%=game.getInetCode()%>
		</div>
		<a
			href="<%=request.getContextPath()%>/java-game/<%=game.getId().toString()%>/<%=UTF8Tool.coDau2KoDau(game.getName().trim())
									.replaceAll(" ", "-")%>.html"
			class="tengame"><%=game.getName()%></a>
		<br>
		<br>

		<strong>Download:</strong>
		<%=game.getDownloadCounter()%><br>
		<%
			desc = game.getDesc();
			index = -1;
			if(desc.length() > 0)
				index = desc.indexOf("<br>");
			
			if(index != -1){				
				desc = desc.substring(0, index);
				//System.out.println(index + " : " + desc);
			}				
			if(desc != null && desc.length() > 100)
				desc = desc.substring(0, 100);			
		%>
		<strong>Gi&#7899;i thi&#7879;u:</strong><%=desc + "..."%>

	</div>
	<%
		}
	%>
	<div class="both"></div>
</div>
<%
	}
%>
<%
	int numOfPages = 0;
	numOfPages = (totalRecs % rowsPerPage != 0) ? (totalRecs
			/ rowsPerPage + 1) : (totalRecs / rowsPerPage);
	if (totalRecs <= rowsPerPage)
		numOfPages = 1;
	if (totalRecs == 0)
		numOfPages = 0;
	if (currPage > numOfPages)
		currPage = numOfPages;
	if (currPage < 0)
		currPage = 1;
%>
<div style="display: block; background-color: #FFFCEF;">
	<%
		if (numOfPages > 10) {
			int PrevPage = currPage - 1;
			if (PrevPage < 1) {
				PrevPage = 1;
			}
			int NextPage = currPage + 1;
			if (NextPage > numOfPages) {
				NextPage = numOfPages;
			}
			int LastPage = numOfPages;
			int FirstPage = 1;
	%>

	<!--first page button-->
	<%
		if (currPage == FirstPage) {
	%>
	<img src="<%=request.getContextPath()%>/images/relax_first.png"
		alt="First" border="0" align="absmiddle" hspace="3" />
	<%
		} else {
	%>
	<a
		href="<%=request.getContextPath()%>/the-loai-game/<%=sCatId%>/<%=FirstPage%>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(
									" ", "-")%>.html"><img
			src="<%=request.getContextPath()%>/images/relax_first.png"
			alt="First" border="0" align="absmiddle" hspace="3" />
	</a>
	<%
		}
	%>
	<!--previous page button-->
	<%
		if (PrevPage == currPage) {
	%>
	<img src="<%=request.getContextPath()%>/images/relax_prev.png"
		alt="Previous" border="0" align="absmiddle" hspace="3" />
	<%
		} else {
	%>
	<a
		href="<%=request.getContextPath()%>/the-loai-game/<%=sCatId%>/<%=PrevPage%>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(
									" ", "-")%>.html"><img
			src="<%=request.getContextPath()%>/images/relax_prev.png"
			alt="Previous" border="0" align="absmiddle" hspace="3" />
	</a>
	<%
		}
	%>
	<!--current page number-->
	<input name="p" type="text"
		style="height: 15px; background-color: #F4F1E3; font-size: 10px; vertical-align: middle; border: 0px"
		value="<%=currPage%>" size="1.5" readonly="readonly" />
	/
	<input name="countpage" type="text"
		style="height: 15px; background-color: #F4F1E3; font-size: 10px; vertical-align: middle; border: 0px"
		value="<%=numOfPages%>" size="1.5" readonly="readonly" />
	<!--next page button-->
	<%
		if (NextPage == currPage) {
	%>
	<img src="<%=request.getContextPath()%>/images/relax_next.png"
		alt="Next" border="0" align="absmiddle" hspace="3" />
	<%
		} else {
	%>
	<a
		href="<%=request.getContextPath()%>/the-loai-game/<%=sCatId%>/<%=NextPage%>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(
									" ", "-")%>.html"><img
			src="<%=request.getContextPath()%>/images/relax_next.png" alt="Next"
			border="0" align="absmiddle" hspace="3" />
	</a>
	<%
		}
	%>
	<!--last page button-->
	<%
		if (LastPage == currPage) {
	%>
	<img src="<%=request.getContextPath()%>/images/relax_last.png"
		alt="Last" border="0" align="absmiddle" hspace="3" />
	<%
		} else {
	%>
	<a
		href="<%=request.getContextPath()%>/the-loai-game/<%=sCatId%>/<%=LastPage%>/<%=UTF8Tool.coDau2KoDau(cat.getName()).replaceAll(
									" ", "-")%>.html"><img
			src="<%=request.getContextPath()%>/images/relax_last.png" alt="Last"
			border="0" align="absmiddle" hspace="3" />
	</a>
	<%
		}
	%>
	<%
		} else if (numOfPages > 1) {
			//for(int k=1; k <= numOfPages;k++){
			for (int k = numOfPages; k >= 1; k--) {
				if (k != currPage) {
	%>

	<div
		style="float: right; border: 1px solid #F7E8B9; height: 15px; margin: 0px auto;">
		&nbsp;
		<a
			href="<%=request.getContextPath()%>/the-loai-game/<%=sCatId%>/<%=k%>/<%=UTF8Tool.coDau2KoDau(cat.getName())
										.replaceAll(" ", "-")%>.html"><%=k%></a>&nbsp;
	</div>
	<div style="float: right;">
		&nbsp;
	</div>
	<%
		} else {
	%>
	<div
		style="float: right; background-color: #FCF2D4; border: 1px solid #EF3E00; height: 15px; margin: 0px auto; font-weight: bolder; color: red;">
		&nbsp;<%=k%>&nbsp;
	</div>
	<div style="float: right;">
		&nbsp;
	</div>
	<%
		}
			}
		}
	%>

</div>
<div style="clear: both;"></div>
<div style="text-align: center;">
	<img src="<%=request.getContextPath()%>/images/gamemobile-loingo.jpg"
		vspace="15"
		alt="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung."
		title="Tải game, tai javagame, java game, game mobile, game cho điện
thoại di động, game mobile, game noka, game samsung." />
</div>