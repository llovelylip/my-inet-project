/*******************************************************************************
 * import javax.servlet.http.HttpServlet;
 * *************************************** File name: KrazeGameServlet.java
 * 
 * @author Nguyen Thien Phuong Copyright (c) 2007 iNET Media Corporation Created
 *         on Nov 29, 2007
 ******************************************************************************/

package inet.webservice.krazevina;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KrazeGameServlet extends HttpServlet {

	KrazeGameListDAO gameDAO = null;

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sId = (String) request.getParameter("id");
		String sType = (String) request.getParameter("type");
		if (sId == null || "".equals(sId)) {
			response.setContentType("text/plain");
			java.io.PrintWriter out = response.getWriter();
			out.println("NOIMAGE");
			return;
		}
		sId = sId.trim();
		byte[] content=null;
		javax.servlet.ServletOutputStream out = response.getOutputStream();
		try {
			response.setContentType("image/gif");
			if(sType!=null&&sType.equals("WAP")){
				content= gameDAO.getContentWapImage(new BigDecimal(sId));
			}else{
				content= gameDAO.getContentGif(new BigDecimal(sId));	
			}
			
			response.setContentLength(content.length);
			out.write(content, 0, content.length);
			out.flush();
			out.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		gameDAO = new KrazeGameListDAO();
	}

}