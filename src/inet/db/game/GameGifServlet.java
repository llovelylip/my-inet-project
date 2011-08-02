package inet.db.game;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;


public class GameGifServlet extends HttpServlet {
    private static final String CONTENT_TYPE_GIF = "image/gif";
    private GameDAO gameDAO=null;

    //Initialize global variables
    public void init() throws ServletException {
    	gameDAO=new GameDAO();	
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String sId = (String)req.getParameter("id");
        String sType=req.getParameter("type");
        if (sId == null || "".equals(sId)) {
            res.setContentType("text/plain");
            java.io.PrintWriter out = res.getWriter();
            out.println("?");
            return;
        }
        sId = sId.trim();
        javax.servlet.ServletOutputStream out = res.getOutputStream();
        try {
            res.setContentType(CONTENT_TYPE_GIF);
            byte[] contentGif =null; 
            if("WAP".equals(sType)){
            	contentGif = gameDAO.getWapFile(new BigDecimal(sId));
            }else{
            	contentGif= GameBuffer.lookup(sId);
            } 	
            res.setContentLength(contentGif.length);
            out.write(contentGif, 0, contentGif.length);
            out.flush();
            out.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws
        ServletException, IOException {
        doGet(req, res);
    }

    //Clean up resources
    public void destroy() {
    }
}
