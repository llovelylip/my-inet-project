<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Java Game | Game điện thoại | Java Game dien thoai | Java Game mobile</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="Tải java game cho dien thoai di động, game đua xe, game đánh bài, tải game miễn phí">
		<link href="style.css" rel="stylesheet" type="text/css">
		<link rel="icon" href="/images/icon-game.ico" type="image/x-icon" />
		<%@include file="/googleScript.html"%>
	</head>
	
	<body bottommargin="0" topmargin="0" marginheight="0" marginwidth="0">
		<center>
			<!--Header-->
			<jsp:include flush="true" page="header.jsp"></jsp:include>
			<div style="width:960px; ">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="200" valign="top">
						<jsp:include flush="true" page="left.jsp"></jsp:include>
					</td>
					<td width="5"></td>
					<td valign="top">
						<jsp:include flush="true" page="top.jsp"></jsp:include>
					</td>
					<td width="5"></td>
					<td width="200" valign="top">
					<jsp:include flush="true" page="right.jsp"></jsp:include>
					</td>
				  </tr>
				</table>
			</div>
			<jsp:include flush="true" page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
