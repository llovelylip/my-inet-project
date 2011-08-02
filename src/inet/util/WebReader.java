/*
 * Created on Jul 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package inet.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.Hashtable;

/**
 * @author huyenntb
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WebReader {

	private DataInputStream  in  = null;
    private DataOutputStream out = null;

    //e.g: "HTTP/1.1 200 OK"
    private String requestType   = null;
    // key in lowercase
    private Hashtable headers = null;
    // http body
    private byte[] content = null;

	public WebReader() {
        this.headers = new Hashtable();
	}

    //---------------------------------------------------
    public String    getRequestType() { return requestType; }
    public Hashtable getHeaders()     { return headers;     }
    public byte[]    getContent()     { return content;     }
    public String    getHeader(String name) {
        String key = (String) headers.get(name);
        return key;
    }
    //---------------------------------------------------

    // Getting data from URL: http://server:port/uri/...
    public void doGet(String server, String uri, int port) throws Exception {
        try {
            //Socket client = new Socket("localhost", 80);
            Socket client = new Socket(server, port);

            //client.setKeepAlive(true);
            this.in  = new DataInputStream(client.getInputStream());
            this.out = new DataOutputStream(client.getOutputStream());

            //out.writeBytes("GET /vn/logo/top_logo.jsp HTTP/1.0\r\n");
            //--> http://localhost/vn/xxx
            out.writeBytes("GET " + uri + " HTTP/1.0\r\n");

            /* Optional Parameters */
            out.writeBytes("Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n");
            out.writeBytes("Accept-Language: en-us\r\n");
            out.writeBytes("Accept-Encoding: gzip, deflate\r\n");
            out.writeBytes("Host: " + server + "\r\n");
            out.writeBytes("User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)\r\n");
            out.writeBytes("Connection: Keep-Alive\r\n");

//            /* This Header is very important and can't be ignored */
            out.writeBytes("Content-Length: 0\r\n\r\n");
            out.flush();

            ///////////////////////////////
            parseRequest();
            ///////////////////////////////

            out.close();
            in.close();
            client.close();
        } catch (Exception e) {
           e.printStackTrace();
            throw e;
        }
    }


    //==========================================================================
    //==========================================================================
    // From the input stream we extract the message and all the headers
    // (including Nokia HTTP extension headers)
    // Sequence:
    // 1. Request-Type =
    //         POST /uri HTTP/1.1
    //         GET  /uri HTTP/1.1
    // 2. HTML Headers:
    //      Content-Type: application/vnd.wap.mms-message
    //      Content-Length: 22489
    //      User-Agent: Java/1.4.1
    //      Host: localhost:7000
    //      Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
    //      Connection: keep-alive
    //
    // 3. MM Content (if POST request):...
    // IN : this.in
    //--------------------------------------------------------------------------
    private boolean parseRequest() {
        byte[] match = {13, 10, 13, 10};

        int pos, ch;
        int index = 0;
        String line = "";
        try {
            while ( (ch = in.read()) >= 0) {
            	
                // The HTTP headers and the message body are separated by
                // blank line (a sequence of CR>LF>).  In hex mode,
                // this shows as a sequence '0D0A0D0A' or {13, 10, 13, 10}
                if (ch == match[index] && index <= 3) {
                    index++;
                }
                else {
                    index = 0;
                }
                if (index == 4) {
                    content = readHTTPContent();
                   // System.out.println(new String(content));
                    break;
                }

                // 2. Gets the request headers
                line += (char) ch;
                if ( (line.length() > 2) && (ch == '\n')) {
                    pos = line.indexOf(':');
                    if (pos != -1) {
                        // <header-name>:<header-value>\n
                        String name  = line.substring(0, pos); //.toLowerCase();
                        String value = line.substring(pos + 1, line.length()).trim();
                        this.setHeader(name, value);
                    } else {
                        // 1. Request Type
                        // e.g: GET / HTTP/1.1 or GET /mmsc HTTP/1.1
                        // <-> http://server/  or http://server/mmsc
                        this.setRequestType(line.substring(0, line.length()).trim());
                    }
                    line = "";
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //--------------------------------------------------------------------------
    // Store and print header to screen
    private void setHeader(String key, String value) {
        this.headers.put(key.toLowerCase(), value);
        //System.out.println(key + ": " + value);
    }
    
    private void setRequestType(String s) {
        this.requestType = new String(s);
        System.out.println(s);
    }

    //--------------------------------------------------------------------------
    private byte[] readHTTPContent() throws IOException {
        ByteArrayOutputStream baosContent = new ByteArrayOutputStream();
        int contentLength = 0;
        try {
            contentLength = Integer.parseInt( (String) headers.get("content-length"));
        } catch (Exception ex) {
            contentLength = 1024 * 1024;
        }
        int bytesToRead = 0;
        int bytesRead   = 0;
        int totalBytesRead = 0;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        if (contentLength < bufferSize) {
            bytesToRead = contentLength;
        } else {
            bytesToRead = bufferSize;
        }
        do {
            try {
                bytesRead = in.read(buffer, 0, bytesToRead);
            } catch (InterruptedIOException e) {
                /* comms read timeout expired, no problem */
                System.out.println("Timeout reading from socket");
            }
            if (bytesRead == -1) {
                in.close();
               // throw new IOException("Connection was closed by client.");
                break;
            } else if (bytesRead > 0) {
                //////////////////////////////////////
                baosContent.write(buffer, 0, bytesRead);
                //////////////////////////////////////
                totalBytesRead += bytesRead;
            }
            // Left bytes to read
            if (contentLength - totalBytesRead > bufferSize) {
                bytesToRead = bufferSize;
            } else {
                bytesToRead = contentLength - totalBytesRead;
            }
        } while (totalBytesRead < contentLength);
        
            ///////////////////////////////////////
        return baosContent.toByteArray();
        ///////////////////////////////////////
    }
    public  void saveToFile(byte[] output, String filename) {
        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, false);

            out.write(output);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] arg){
    	WebReader conn=new WebReader();
    	String info=null;
    	try {
    		conn.doGet("sms.vn", "/animation/animation_top.jsp", 80);
    		info=new String(conn.getContent());
    		System.out.println(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
	
}
