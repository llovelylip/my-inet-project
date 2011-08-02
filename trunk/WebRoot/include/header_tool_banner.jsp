<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="inet.util.DateProc"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="inet.db.partner.banner.BannerVisitor"%>
<jsp:useBean id="bnLinkDAO" class="inet.db.partner.banner.BannerLinkDAO" />
<jsp:useBean id="bnVisitorDAO" class="inet.db.partner.banner.BannerVisitorDAO" />
<jsp:useBean id="bnLogDAO" class="inet.db.partner.banner.BannerLogDAO" />

<%
	
	String sIdBanner = request.getParameter("id_banner"); // Id cua banner
	String ip_addr = request.getRemoteAddr();// Lay dia chi Ip
	if(session.getAttribute("IP_CLICK") != null){
		//return;
	}else{
		session.setAttribute("IP_CLICK", ip_addr);
		session.setMaxInactiveInterval(300);
		
		Timestamp ts = DateProc.createTimestamp();// Lay timestamp hien tai 
		String today = DateProc.getDateString(ts);// Lay ngay hien tai
		
		BigDecimal bIdBanner = null;
		
		try{
			bIdBanner = new BigDecimal(sIdBanner.trim());
			bnLinkDAO.updateCounter(bIdBanner); // Tang so dem
			// Lay thong tin ve ip truy cap wap co id = bId
			BannerVisitor bannerVisitor = bnVisitorDAO.getRow(bIdBanner, ip_addr); 
			if(bannerVisitor == null){
				// Neu khong co thi insert vao
				bnVisitorDAO.insertRow(bIdBanner, ip_addr, 0, ts, ts); // Ip nay la new - visitor
				BigDecimal counter = bnLogDAO.getCounter(bIdBanner, today);// Lay counter hien tai theo ngay
				if(counter == null)
					counter = BigDecimal.ZERO;
				bnLogDAO.insertRow(bIdBanner, ip_addr, counter.add(BigDecimal.ONE), ts);// Ghi vao bang log
			}else{
				// Neu truy cap roi lay thoi gian dau tien truy cap
				Timestamp first_visit = bannerVisitor.getFirstVisit();
				// Thoi gian dau tien + 24h 
				Timestamp first_visit_add24h = DateProc.getNextDate(first_visit);
				// Kiem tra xem thoi gian dau tien + 24h co nho hon thoi gian truy cap hien tai khong
				if(first_visit_add24h.compareTo(ts) < 0)
					bnVisitorDAO.updateRow(bIdBanner, ip_addr, 1); // Neu co update lai ip nay la return visitor
				else
					bnVisitorDAO.updateRow(bIdBanner, ip_addr); // Neu khong chi update lai thoi gian truy cap cuoi
				boolean hasWrite = bnLogDAO.checkDataByDay(bIdBanner, ip_addr, today); // Kiem tra bang log co ghi ban ghi ve IP nay chua
				if(hasWrite){
					bnLogDAO.updateClickTime(bIdBanner, ip_addr, today);// Co roi thi chi update lai lan cuoi truy cap trong ngay o bang log
					BigDecimal counter = bnLogDAO.getCounter(bIdBanner, today);
					if(counter == null)
						counter = BigDecimal.ZERO;
					bnLogDAO.updateCounter(bIdBanner, ip_addr, today, counter.add(BigDecimal.ONE));// Update lai bien dem click
				}else{
					BigDecimal counter = bnLogDAO.getCounter(bIdBanner, today);
					if(counter == null)
						counter = BigDecimal.ZERO;
					bnLogDAO.insertRow(bIdBanner, ip_addr, counter.add(BigDecimal.ONE), ts);// Chua co thi insert vao log
				}
			}			
		}catch(Exception ex){}
	}
	
%>