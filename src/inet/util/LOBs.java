package inet.util;

import java.io.*;
import java.sql.*;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import java.sql.Connection;
/**
 *
 */
public class LOBs {

  public LOBs() {
  }
  public static String readClob(Clob myClob) throws IOException, SQLException {
    Reader rd = myClob.getCharacterStream();
    StringBuffer sbuf = new StringBuffer();
    char[] line = new char[255];
    int i = 0;
    do {
      i = rd.read(line);
      if (i != -1) {
        String newline = new String(line, 0, i);
        sbuf.append(newline);
      }
    }
    while (i != -1);
    return sbuf.toString();
  }

  public static void writeBlob(Blob blob, byte[] src) throws IOException, SQLException {
    OutputStream os = null;
    os = blob.setBinaryStream(0); //get the output stream from the Blob to insert it
    os.write(src);
    os.flush();
    os.close();
  }

  public static String readBlob(Blob myBlob) throws IOException, SQLException {
    InputStream is = myBlob.getBinaryStream();
    StringBuffer sbuf = new StringBuffer();
    byte[] line = new byte[1024];
    int i = 0;

    do {
      i = is.read(line);
      if (i != -1) {
        String newline = new String(line, 0, i);
        sbuf.append(newline);

      }
    }
    while (i != -1);
    return sbuf.toString();
  }

  public static byte[] readByteBlob(Blob myBlob)throws IOException,SQLException {
	  java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
	  InputStream is = myBlob.getBinaryStream();
	    byte[] line = new byte[1024];
	    int i = 0;
	    do {
		      i = is.read(line, 0, line.length);
		      if (i != -1) {
	               bos.write(line, 0, i);
		      }
	    }while (i != -1);
		is.close();
        return bos.toByteArray();
  }
  public static Blob createBlob(Connection conn, byte[] src) throws IOException, SQLException {
	  BLOB blob = BLOB.createTemporary(conn, false, BLOB.DURATION_SESSION);
      OutputStream outputStream = blob.setBinaryStream(0L);
      
      InputStream inputStream = new ByteArrayInputStream(src);
      byte[] buffer = new byte[blob.getBufferSize()];
      int byteread = 0;
      while ((byteread = inputStream.read(buffer)) != -1) {
           outputStream.write(buffer, 0, byteread);
      }
      outputStream.close();
      inputStream.close();
      return blob;
 }

  @SuppressWarnings("deprecation")
public static CLOB getCLOB( String clobData, Connection conn ) throws Exception {
		CLOB tempClob = null;
			
		try {
			//  create a new temporary CLOB
			tempClob = CLOB.createTemporary( conn, true, CLOB.DURATION_SESSION );
			
			// Open the temporary CLOB in readwrite mode to enable writing
			tempClob.open( CLOB.MODE_READWRITE );
			
			// Get the output stream to write
			
			Writer tempClobWriter = tempClob.getCharacterOutputStream(  );
			
			// Write the data into the temporary CLOB
			tempClobWriter.write( clobData );
			
			// Flush and close the stream
			tempClobWriter.flush(  );
			tempClobWriter.close(  );
			
			// Close the temporary CLOB
			tempClob.close(  );
			
		} catch ( Exception exp ) {
			// Free CLOB object
			tempClob.freeTemporary( );
			// do something
		}
		return tempClob;      
	}
 
}
