package inet.util.my;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

//import sun.org.mozilla.javascript.internal.Synchronizer;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class Menu3gTool {

	//private static final String fileName = Menu3gTool.class.getResource("menu3g.conf").getFile();
	private static final String fileName = "D://config//menu3g.conf";		
	public static Hashtable<String, Collection> hashTopMenu = null;
	public static Hashtable<String, Collection> hashBottomMenu = null;
	
	
	/**
	 * @param args
	 */
	static{
		hashTopMenu = getHashTopMenu();
		hashBottomMenu = getHashBottomMenu();
	}
	
	public static void main(String[] args){
		
	}
	
	public synchronized static Hashtable getHashTopMenu() {
    	//System.out.println("Bat dau load data");
//    	if(hashTopMenu != null) return hashTopMenu;
    	if(hashTopMenu!=null){
    		return hashTopMenu;
    	}
    	
    	Collection nameAndLinkColl = null;
    	Collection sourceColl = null;
    	Collection tempColl = null;
    	String strSource = null;
    	String strListNameAndLinkOfMenu = null;
    	String strTemp = null;
    	int i=0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            hashTopMenu = new Hashtable<String, Collection>();            
            for (; line != null; line = in.readLine()) {            
                line = line.trim();
                if ("".equals(line) || line.startsWith("#")) {
                    continue;
                }
                
                //System.out.println();
                //System.out.println("line="+line);                
                int idx = line.indexOf(":"); 
                
                if (idx <= 0) {
                    System.out.println("   Seperator ':' is NOT found.");
                    continue;
                }                
                
                strSource = line.substring(0, idx);		//source1, source2 ...
                strListNameAndLinkOfMenu = line.substring(idx+1);    //Format: menu1, link1, menu2, link2 ...             
                sourceColl = inet.util.StringTool.parseString(strSource, ",");                
                nameAndLinkColl = inet.util.StringTool.parseString(strListNameAndLinkOfMenu, ",");                
                
//                System.out.println("sourceColl:      "+sourceColl);
//                System.out.println("nameAndLinkColl: "+nameAndLinkColl);
//                System.out.println();
//                System.out.println();
                /*
                 * Tao bang hash anh xa:
                 * source1 - topMenu1
                 * Top menu: 8 gia tri dau trong nameAndLinkColl
                 * Bootom menu: cac gia tri con lai trong nameAndLinkColl
                 */
                
                if(nameAndLinkColl!=null && !nameAndLinkColl.isEmpty()){
                	tempColl = new Vector();
                	i = 0;
                	
                	for (Iterator it = nameAndLinkColl.iterator(); it.hasNext(); i++) {
                		//System.out.println("i="+i);
                		strTemp = it.next().toString();
                		if(i<8){
                			tempColl.add(strTemp);                	
                		}
                    }
                	
                	for (Iterator it = sourceColl.iterator(); it.hasNext();) {
                		hashTopMenu.put(it.next().toString(), tempColl);
                	}
                	
                }                 
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println("Load xong file config vao hashtable");
        return hashTopMenu;       
    }
	
	
	public synchronized static Hashtable getHashBottomMenu() {
		//System.out.println("Bat dau load data");
//    	if(hashTopMenu != null) return hashTopMenu;
		if(hashBottomMenu!=null) return hashBottomMenu;
		
		Collection nameAndLinkColl = null;
		Collection sourceColl = null;
		Collection tempColl = null;
		String strSource = null;
		String strListNameAndLinkOfMenu = null;
		String strTemp = null;
		int i=0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = in.readLine();
			hashBottomMenu = new Hashtable<String, Collection>();            
			for (; line != null; line = in.readLine()) {            
				line = line.trim();
				if ("".equals(line) || line.startsWith("#")) {
					continue;
				}
				
				//System.out.println();
				//System.out.println("line="+line);                
				int idx = line.indexOf(":"); 
				
				if (idx <= 0) {
					System.out.println("   Seperator ':' is NOT found.");
					continue;
				}                
				
				strSource = line.substring(0, idx);		//source1, source2 ...
				strListNameAndLinkOfMenu = line.substring(idx+1);    //Format: menu1, link1, menu2, link2 ...             
				sourceColl = inet.util.StringTool.parseString(strSource, ",");                
				nameAndLinkColl = inet.util.StringTool.parseString(strListNameAndLinkOfMenu, ",");                
				
//                System.out.println("sourceColl:      "+sourceColl);
//                System.out.println("nameAndLinkColl: "+nameAndLinkColl);
//                System.out.println();
//                System.out.println();
				/*
				 * Tao bang hash anh xa:
				 * source1 - topMenu1
				 * Top menu: 8 gia tri dau trong nameAndLinkColl
				 * Bootom menu: cac gia tri con lai trong nameAndLinkColl
				 */
				
				if(nameAndLinkColl!=null && !nameAndLinkColl.isEmpty()){
					tempColl = new Vector();
					i = 0;
					
					for (Iterator it = nameAndLinkColl.iterator(); it.hasNext(); i++) {
						//System.out.println("i="+i);
						strTemp = it.next().toString();
						if(i<8){
							tempColl.add(strTemp);                	
						}
					}
					
					for (Iterator it = sourceColl.iterator(); it.hasNext();) {
						hashBottomMenu.put(it.next().toString(), tempColl);
					}
					
				}
				
				
				
				if(nameAndLinkColl!=null && !nameAndLinkColl.isEmpty()){
                	tempColl = new Vector();
                	i = 0;
                	
                	for (Iterator it = nameAndLinkColl.iterator(); it.hasNext(); i++) {
                		//System.out.println("i="+i);
                		strTemp = it.next().toString();
                		if(i>7&&i<17){
                			tempColl.add(strTemp);                	
                		}
                    }
                	
                	for (Iterator it = sourceColl.iterator(); it.hasNext();) {
                		hashBottomMenu.put(it.next().toString(), tempColl);
                	}
                	
                }
				
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("Load xong file config vao hashtable");
		return hashBottomMenu;       
	}
	

	
	public synchronized static void reloadAllMenu() {
		System.out.println("Reloading wap 3g menu config ...");
		Collection nameAndLinkColl = null;
    	Collection sourceColl = null;
    	Collection tempTopMenuColl = null;
    	Collection tempBottomMenuColl = null;
    	String strSource = null;
    	String strListNameAndLinkOfMenu = null;
    	String strTemp = null;
    	int i=0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            if(hashTopMenu != null){
            	hashTopMenu.clear();         
            } else{
            	hashTopMenu = new Hashtable<String, Collection>();
            }
            if(hashBottomMenu != null){
            	hashBottomMenu.clear();         
            } else{
            	hashBottomMenu = new Hashtable<String, Collection>();
            }
                        
            for (; line != null; line = in.readLine()) {            
                line = line.trim();
                if ("".equals(line) || line.startsWith("#")) {
                    continue;
                }
                
                //System.out.println();
                //System.out.println("line="+line);                
                int idx = line.indexOf(":"); 
                
                if (idx <= 0) {
                    System.out.println("   Seperator ':' is NOT found.");
                    continue;
                }                
                
                strSource = line.substring(0, idx);		//source1, source2 ...
                strListNameAndLinkOfMenu = line.substring(idx+1);    //Format: menu1, link1, menu2, link2 ...             
                sourceColl = inet.util.StringTool.parseString(strSource, ",");                
                nameAndLinkColl = inet.util.StringTool.parseString(strListNameAndLinkOfMenu, ",");                
                
//                System.out.println("sourceColl:      "+sourceColl);
//                System.out.println("nameAndLinkColl: "+nameAndLinkColl);
//                System.out.println();
//                System.out.println();
                /*
                 * Tao bang hash anh xa:
                 * source1 - topMenu1
                 * Top menu: 8 gia tri dau trong nameAndLinkColl
                 * Bootom menu: cac gia tri con lai trong nameAndLinkColl
                 */
                
                if(nameAndLinkColl!=null && !nameAndLinkColl.isEmpty()){
                	tempTopMenuColl = new Vector();
                	tempBottomMenuColl = new Vector();
                	i = 0;
                	
                	for (Iterator it = nameAndLinkColl.iterator(); it.hasNext(); i++) {
                		//System.out.println("i="+i);
                		strTemp = it.next().toString();
                		if(i<8){
                			tempTopMenuColl.add(strTemp);                	
                		}else if(i<17){
                			tempBottomMenuColl.add(strTemp);                	
                		}
                    }
                	
                	for (Iterator it = sourceColl.iterator(); it.hasNext();) {
                		strTemp = it.next().toString();
                		hashTopMenu.put(strTemp, tempTopMenuColl);
                		hashBottomMenu.put(strTemp, tempBottomMenuColl);
                	}
                	
                }                 
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(hashTopMenu);
               
    }
	
	
	public static Collection getIndex(String content, String symbol){
		Collection indexColl = new Vector();
		if(content.toUpperCase().startsWith(symbol)){
			indexColl.add(0);
		}
		int idx=0;
		while(idx!=-1){
			idx=content.indexOf(symbol, idx+symbol.length());
			if(idx!=-1){
				indexColl.add(idx);
			}
		}			
		return indexColl;
	}
	
	public synchronized static void writeFile(String text) throws IOException {
	    FileOutputStream fos = null;
	    try {
	        fos = new FileOutputStream(fileName);
	        fos.write(text.trim().getBytes("UTF-8"));
	    } catch (IOException e) {
	        //close(fos);
	    	fos.close();
	        throw e;
	    }
	}
}
