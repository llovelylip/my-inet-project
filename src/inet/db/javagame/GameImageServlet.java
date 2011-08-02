package inet.db.javagame;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;
public class GameImageServlet extends HttpServlet {
    JavaGameDAO dao = null;
    //Initialize global variables
    public void init() throws ServletException {
        dao = new JavaGameDAO();
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String sId = (String)req.getParameter("id");
        String sType   = (String)req.getParameter("type"); //=OTB
        if (sId == null || "".equals(sId)) {
            res.setContentType("text/plain");
            java.io.PrintWriter out = res.getWriter();
            out.println("?");
            return;
        }
        sId = sId.trim();
        javax.servlet.ServletOutputStream out = res.getOutputStream();
        try {
            
                res.setContentType("image/gif");
                byte[] contentGif =null;
                if("wap".equalsIgnoreCase(sType)){
                	contentGif=dao.getWapImage(new BigDecimal(sId));
                }else{
                	contentGif=dao.getImage(new BigDecimal(sId));
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
