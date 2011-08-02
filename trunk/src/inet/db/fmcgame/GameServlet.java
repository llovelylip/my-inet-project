package inet.db.fmcgame;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;
public class GameServlet extends HttpServlet {
   
	GameDAO dao = null;
    //Initialize global variables
    public void init() throws ServletException {
        dao = new GameDAO();
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        String sId = (String)req.getParameter("id");
        String sType=(String)req.getParameter("type");
        if (sId == null || "".equals(sId)) {
            res.setContentType("text/plain");
            java.io.PrintWriter out = res.getWriter();
            out.println("?");
            return;
        }	
        sId = sId.trim();
        byte[] content=null;
        javax.servlet.ServletOutputStream out = res.getOutputStream();
        try {
                res.setContentType("image/gif");
                if(sType!=null&&sType.equals("WAP")){
                	content= dao.getWapImage(new BigDecimal(sId));
                }else{
                	content= dao.getImage(new BigDecimal(sId));
                } 
                res.setContentLength(content.length);
                out.write(content, 0, content.length);
                out.flush();
                out.close();
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
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
