package inet.db.game;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.*;
public class FreeGameServlet extends HttpServlet{
	GameDAO gameDAO = new GameDAO();
	public void init(){
		
	}
	 private String buildWMLDocument(String title, String content) {
	    String wml = "<?xml version=\"1.0\"?><!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" \"http://www.wapforum.org/DTD/wml_1.1.xml\"><wml><card id=\"card1\" title=\"" + title + "\">" + "<p>" + content + "/<p>" + "</card>" + "</wml>";
	    return wml;
	  }
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException{
		PrintWriter pout;
	    String message;
	    String sId = request.getParameter("id");
	    
	    BigDecimal id =null;
	    try {
	    	id= new BigDecimal(sId);
	    	if (id == null) {
	  	      response.setContentType("text/plain");
	  	      PrintWriter out = response.getWriter();
	  	      System.out.println("GameId is null");
	  	      out.println("Not found");
	  	      return;
	  	    }else if(!gameDAO.checkFree(id)&&!gameDAO.checkQua(id)){
	  	    	response.setContentType("text/plain");
		  	    PrintWriter out = response.getWriter();
		  	    System.out.println("GameId is not free");
		  	    out.println("GameId is not free");
		  	    return;
	  	    	
	  	    }
	  	    ServletOutputStream out = response.getOutputStream();   	
	       byte[] jarFile = gameDAO.getJarFile(id);
	       if (jarFile == null) {
		        System.out.println("GameMienphiDownloadServlet: dao.getJarFile(id=" + id + ") return null!");
		        jarFile = "?".getBytes();
	       }
		    response.setContentType("application/java-archive");
		    response.setHeader("Content-Length", String.valueOf(jarFile.length));
		    System.out.println("lenth=" + jarFile.length);
		    response.setContentLength(jarFile.length);
		    out.write(jarFile, 0, jarFile.length);
		    out.flush();
		    out.close();
	    }
	    catch (Exception ex) {
	      System.out.println("GameMienphiDownloadServlet: " + ex.getMessage());
	      ex.printStackTrace();
	      try {
	    	  pout = new PrintWriter(response.getOutputStream());
	    	  response.setContentType("text/vnd.wap.wml");
		      message = "He thong dang qua tai. Ban vui long thuc hien lai ket noi GPRS.<br/>Xem chi tiet tai http://www.sms.vn hoac goi 1900561588 de duoc ho tro.";
		      pout.println(buildWMLDocument("Wap.vn", message));
		      pout.flush();
		      pout.close();
		  } catch (Exception e) {
				e.printStackTrace();
		  } 
	      return;
	    }
	   
	  }
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	  }
}
