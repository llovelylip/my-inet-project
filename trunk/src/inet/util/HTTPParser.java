package inet.util;

import java.io.*;
import java.util.Hashtable;

/**
 * Nguyen Trong Tho
 */

public class HTTPParser {
    public HTTPParser () {
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
    // 3. HTML Content (if POST request):...
    //
    // IN : in (InputStream)
    // OUT: httpHeaders - stores HTTP header; with name in lower case
    //      httpContent - stores HTTP content
    //--------------------------------------------------------------------------
    static String requestType = "";
    public static boolean parseRequest(InputStream in, Hashtable httpHeaders, ByteArrayOutputStream httpContent) {


        byte[] match = {13, 10, 13, 10};

        int pos, ch;
        int index = 0;
        String line = "";
        try {
            while ( (ch = in.read()) >= 0) {
                // The HTTP headers and the message body are separated by
                // blank line (a sequence of CR>LF>).  In hex mode,
                // this shows as a sequence '0D0A0D0A' or {13, 10, 13, 10}
                if (ch == match[index] && index <= 3)
                    index++;
                else
                    index = 0;
                if (index == 4) {
                    if (requestType.startsWith("POST") || //a POST request or
                        requestType.startsWith("HTTP")) { //a reponse from server
                        // 3. Gets HTTP Content
                        String contentLength = (String) httpHeaders.get("content-length");
                        readHTTPContent(in, contentLength, httpContent);
                        break;
                    } else if (requestType.startsWith("GET")) {
                        break; //No content
                    } else {
                        System.out.println("HTTP Method is invalid " + requestType);
                        return false;
                    }
                } // end if

                // 2. Gets the request headers
                line += (char)ch;
                if ( (line.length() > 2) && (ch == '\n')) {
                    pos = line.indexOf(':');
                    if (pos != -1) {
                        // <header-name>:<header-value>\n
                        String name = line.substring(0, pos); //.toLowerCase();
                        String value = line.substring(pos + 1, line.length()).trim();
                        httpHeaders.put(name.toLowerCase(), value);
                    } else {
                        // 1. Request Type
                        // e.g: GET / HTTP/1.1 or GET /mmsc HTTP/1.1
                        // <-> http://server/  or http://server/mmsc
                        // or only HTTP/1.1 200 OK   for response from http server
                        requestType = line.substring(0, line.length()).trim();
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
    private static void readHTTPContent(InputStream in, String sContentLength, ByteArrayOutputStream content) throws IOException {

        int bytesToRead = 0;
        int bytesRead = 0;
        int totalBytesRead = 0;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        /************************************************************/
        /** In case server does not send the content-length header **/
        /************************************************************/
        if (sContentLength == null) {
            while ( (bytesRead = in.read(buffer)) >= 0) {
                content.write(buffer, 0, bytesRead);
            }
            return;
        }

        //content-length != null
        int contentLength = Integer.parseInt(sContentLength);
        if (contentLength < bufferSize) {
            bytesToRead = contentLength;
        } else {
            bytesToRead = bufferSize;
        }
        do {
            try {
                bytesRead = in.read(buffer, 0, bytesToRead);
            } catch (InterruptedIOException e) {}
            if (bytesRead == -1) {
                in.close();
                throw new IOException("Connection was closed by client.");
            } else if (bytesRead > 0) {
                //////////////////////////////////////
                content.write(buffer, 0, bytesRead);
                //////////////////////////////////////
                totalBytesRead += bytesRead;
            }
            // Left bytes to read
            if (contentLength - totalBytesRead > bufferSize) {
                bytesToRead = bufferSize;
            } else {
                bytesToRead = contentLength - totalBytesRead;
            }
        } while ( totalBytesRead < contentLength);
        return;
    }
}
