package inet.db.game;

import inet.db.sms2.IMNick;
import inet.db.sms2.IMNickDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
public class FreeGameOnWap3GServlet extends HttpServlet {
    //Initialize global variables
	GameDAO gameDAO=null;
	IMNickDAO imNickDAO = null;
    public void init() throws ServletException {
    	gameDAO=new GameDAO();
    	imNickDAO = new IMNickDAO();
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       BigDecimal gameId  = null;
       String sId = request.getParameter("id");
       try{
    	   gameId = new BigDecimal(sId);
       }catch (Exception ex) {
    	   // TODO: handle exception
       }
       
       HttpSession session = request.getSession();
       IMNick imNick = (IMNick)session.getAttribute("INET_CHAT_USER");
       System.out.println("gameId = "+gameId);
       
       if(imNick ==null){
    	   response.setContentType("text/plain");
    	   java.io.PrintWriter out = response.getWriter();
    	   out.println("Vui long dang nhap de tai game!");
       } else{
    	   if(imNick.getCountDownGame() >= 5){
    		   response.setContentType("text/plain");
        	   java.io.PrintWriter out = response.getWriter();
        	   out.println("Xin loi ban, ban chi co the tai toi da 5 game free/ ngay!");    		   
    	   } else{
    		   if (gameId!= null) {         
	   	          	response.setContentType("application/java-archive");
	   	            javax.servlet.ServletOutputStream out = response.getOutputStream();
	   	            try {
	   	            	gameDAO.increaseDownloadCounter(gameId);
	   	                byte[] content = gameDAO.getJarFile(gameId);
	   	                if (content == null) {
	   	                    System.out.println("FreeGameServlet.doGet: gameDAO.getContent(PID=" + gameId + ") return null!");
	   	                    content = "?".getBytes();
	   	                }
	   	                
	   	                imNick.setCountDownGame(imNick.getCountDownGame() + 1);
	   	                session.setAttribute("INET_CHAT_USER", imNick);
	   	                
	   	                response.setHeader("Content-Length", String.valueOf(content.length));
	   	                response.setContentLength(content.length);
	   	                out.write(content, 0,content.length);
	   	                
	   	                imNickDAO.updateCountDownGame(imNick.getId(), imNick.getCountDownGame());
	   	                
	   	                out.flush();
	   	                out.close();
	   	            } catch (Exception ex) {
	   	                System.out.println("FreeGameServlet: " + ex.getMessage());
	   	            }
	   	       }else{
	   	    	   response.setContentType("text/plain");
	   	    	   java.io.PrintWriter out = response.getWriter();
	   	    	   out.println("Game is null");
	   	       }
    	   }
       }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
        doGet(request, response);
    }

    //Clean up resources
    public void destroy() {
    }
}
