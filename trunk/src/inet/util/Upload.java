package inet.util;

import java.util.*;
import java.io.*;
import javax.servlet.http.*;

/**
 * @author TuanLA
 * Class used to upload a file to server
 */
public class Upload {
	String filename = " ";
	String dir = " ";
	HttpServletRequest request;

	// Phuong thuc luu doi tuong request tu jsp
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}

	// Phuong thuc dat ten thu muc de ghi file
	public void setDir(String dir){
		this.dir = dir;
	}

	// Phuong thuc dat ten file
	public void setFilename(String filename){
		this.filename = filename;
	}
	public String getFilename(){
		return filename;
	}

	// Phuong thuc nhan du lieu va ghi vao file
	public boolean saveFile(){
		try{
			int m_currentIndex=0;

			String contentType = request.getContentType();
			// lay kieu noi dung cua client duoc gui den tu form multipart/form-data

			if(contentType==null && contentType.indexOf("multipart/form-data") == -1) return false;

			// lay chieu dai cua du lieu
			int m_totalBytes = request.getContentLength();

			// khai bao mang arr dung de luu tru noi dung
			byte[] arr = new byte[m_totalBytes];

			// lay du lieu tu luong
			int totalRead = 0;
			int readBytes = 0;
			for(; totalRead < m_totalBytes; totalRead += readBytes) {
			           request.getInputStream();
			           readBytes = request.getInputStream().read(arr, totalRead, m_totalBytes - totalRead);
			}


		/* ------------------------------------------------------------------------------*/
		/* Lay duong bien cua vung du lieu cua file upload                               */

	        String m_boundary= " ";
	        boolean found = false;
	        for(; !found && m_currentIndex < m_totalBytes; m_currentIndex++)
	            if(arr[m_currentIndex] == 13)
	                found = true;
	            else m_boundary = m_boundary + (char)arr[m_currentIndex];
	         m_currentIndex++;


		/* ------------------------------------------------------------------------------*/
		/* Lay header du lieu cua file upload                               			 */

	        int start = m_currentIndex;
	        int end = 0;
	        found = false;
	        while(!found)
	            if(arr[m_currentIndex] == 13 && arr[m_currentIndex + 2] == 13)
	            {
	                found = true;
	                end = m_currentIndex - 1;
	                m_currentIndex = m_currentIndex + 2;
	            } else {
	                m_currentIndex++;
	            }
	      	String dataHeader = new String(arr, start, (end - start) + 1);

	        m_currentIndex = m_currentIndex + 2;

		/* ------------------------------------------------------------------------------*/
		/* Lay noi dung cua file, vung can upload              							 */

			int searchPos = m_currentIndex;
  			int keyPos = 0;

  			int boundaryLen = m_boundary.length();

  			int m_startData = m_currentIndex;
  			int m_endData = 0;
  			do {
        		if(searchPos >= m_totalBytes) break;

        		if(arr[searchPos] == (byte)m_boundary.charAt(keyPos))
        		{
            		if(keyPos == boundaryLen - 1)
            		{
                		m_endData = ((searchPos - boundaryLen) + 1) - 2;
                		break;
            		}
            		searchPos++;
            		keyPos++;
        		} else
        			{
            			searchPos++;
            			keyPos = 0;
        			}
    		} while(true);

		/* ------------------------------------------------------------------------------*/
		/* Lay ten file 																 */
	 		String filename = dataHeader.substring(dataHeader.indexOf("filename=\"")+10);
 			filename = filename.substring(0,filename.indexOf("\n"));
 			filename = filename.substring(filename.lastIndexOf("\\")+1,filename.indexOf("\"")); 			
		/* ------------------------------------------------------------------------------*/
  		/* Ghi du lieu vao file															 */
		    FileOutputStream fos;
			String filepath = "";
			if ("".equals(dir)) filepath = filename;
			else filepath = dir + "/" + filename;

			setFilename(filename);

			fos = new FileOutputStream(filepath);
		    for (int n=m_startData; n<m_endData; n++){
		    	fos.write(arr[n]);
		    }
		    fos.flush();
		    fos.close();
		    return true;
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
	}
	
	public List IMAGES_FILE_VIEW(String basepath) {		
		List imageList = new ArrayList(); 
		File[]		isfile = new File(basepath).listFiles();		
		boolean test;
		String  extend = " ";
		for (int i=0;i<isfile.length;i++) {
			if(isfile[i].isFile()){					
				extend=this.fileextend(isfile[i].getName());			
				test=(extend.toUpperCase().equals(".JPG"))||(extend.toUpperCase().equals(".BMP"))||(extend.toUpperCase().equals(".GIF"));
				test=test||(extend.toUpperCase().equals(".PNG"));
				if (test){
					imageList.add(isfile[i].getName());				
				}						
			}			
		}
		
		return imageList;
	}
	
	
	/**
	 * get extension file name
	 * @param myfile
	 * @return extension name
	 */
	public String fileextend(String myfile){
		String name=" ";
		int pindex=0 ;
		pindex = myfile.lastIndexOf(".");
		if(pindex==-1){
			pindex= myfile.length();			
		}
		name=myfile.substring(pindex,myfile.length());		
		return name;
	}
}
