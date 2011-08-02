package inet.util;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import java.math.*;
public class FileTool {
    public FileTool() {
    }
    // returns a vector of <code>File</code> objects
    public static Vector getAllFiles(java.io.File location, String fileExt) {
      Vector v = new Vector();
      java.io.File[] list = location.listFiles();
      for (int i = 0; i < list.length; i++) {
          if (list[i].toString().endsWith(fileExt)) {
              v.addElement(list[i]);
          }
          /* also list file in subfolders */
//               if ((list[i]).isDirectory()) {
//                   getAllFiles(list[i]);
//              }
      }
      return v;
  }


    public static void copy(String source_name, String dest_name) throws IOException {
        File source_file = new File(source_name);
        File dest_file = new File(dest_name);
        copy(source_file, dest_file);
    }

    public static void copy(File source_file, File dest_file) throws IOException {
        FileInputStream source = null;
        FileOutputStream destination = null;

        byte[] buffer;
        int bytes_read;

        // First make sure the specified source file
        // exists, is a file, and is readable.
        if (!source_file.exists() || !source_file.isFile())
            throw new FileCopyException("FileCopy: no such source file: " +
                                        source_file.getPath());
        if (!source_file.canRead())
            throw new FileCopyException("FileCopy: source file is unreadable: " +
                                        source_file.getPath());
        // If we've gotten this far, then everything is okay;
        // we can copy the file.
        source = new FileInputStream(source_file);
        destination = new FileOutputStream(dest_file);
        buffer = new byte[1024];
        while ((bytes_read=source.read(buffer)) != -1) {
            destination.write(buffer, 0, bytes_read);
        }
        // No matter what happens, always close any streams we've opened.
        try {
            if (source != null)
                source.close();
            if (destination != null)
                destination.close();
        } catch (IOException e) {}
    }
    public static void move(String source_name, String dest_name) throws IOException {
        File source_file = new File(source_name);
        File dest_file = new File(dest_name);

        copy(source_file, dest_file);
        source_file.delete();
    }

    public static void move(File source_file, File dest_file) throws IOException {
        copy(source_file, dest_file);
        source_file.delete();
    }

    public static byte[] readFile(String filename) throws IOException {
        byte[] buffer = null;
        FileInputStream fin = new FileInputStream(filename);
        buffer = new byte[fin.available()];
        fin.read(buffer);
        return buffer;
    }
    public static void saveToFile(byte[] output, String filename, boolean append) {
        try {
            FileOutputStream out = new FileOutputStream(filename, append);
            out.write(output);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean saveToFile(byte[] output, String filename) {
    	boolean result=true;
    	 try {
        	
            File f = new File(filename);
            if(!f.exists())f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, false);
            out.write(output);
            out.close();
            System.out.println("save xong roi");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	result=false;
        	return result;
            
        }
        return result;
        
    }
    
    public static boolean saveToFile(byte[] output, File file) {
    	boolean result=true;
    	 try {
        	FileOutputStream out = new FileOutputStream(file, false);
            out.write(output);
            out.close();
         } catch (Exception e) {
        	System.out.println(e.getMessage());
        	result=false;
        	return result;
            
        }
        return result;
        
    }
    
    
    public static void updateToFile(byte[] output, String filename) {
        try {
        	
            File f = new File(filename);
            if(!f.exists()){
            	f.createNewFile();
            }else{
            	f.delete();
            	f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f,true);
            out.write(output);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


   
    public static boolean saveToFile(String output, String filename,boolean append) {
    	boolean result=true;
    	 try {
        	
            File f = new File(filename);
            if(!f.exists())f.createNewFile();
            FileWriter out = new FileWriter(f,append);
            out.write(output+"\n");
            
            out.close();
            
            System.out.println("save xong roi");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	result=false;
        	return result;
            
        }
        return result;
        
    }
    public static void main(String[] arg){
    	//FileTool.saveToFile("09123456789 GHA 123", "D://test.txt", true);
    	try {
    		BufferedReader in
     	   = new BufferedReader(new FileReader("D://test.txt"));
    		String s=null;
    		while((s=in.readLine())!=null){
    			System.out.println(s);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    }

}

class FileCopyException extends IOException {
    public FileCopyException(String msg) {
        super(msg);
    }
}


