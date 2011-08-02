package inet.util;

//=========================================================================================================================
//Extends   :
//Implements:
//Purpose   : this is a utility class that allow the high-level code to read and write
//          custom properties of the application
//
//=========================================================================================================================
import java.io.*;
import java.util.Properties;

public  class PropertyTool
{
 //---------------------------------------------------------------------------------------------------------------------
 // Class's properties
 //---------------------------------------------------------------------------------------------------------------------
 static String      m_sPropertyFile="conf/config.conf";
 static Properties  m_oProperties=new Properties();
 static public int soiEnable=0;
 static public int soi85Enable=0;
 static public int lxEnable=0;
 static public int timEnable=0;
 static public int lx85Enable=0;
 static public int tim85Enable=0;
 static public int ckEnable=0;
 static public int l6Enable=0;
 static public int xsttEnable=0;
 
 //---------------------------------------------------------------------------------------------------------------------
 // Class's Constructor
 //---------------------------------------------------------------------------------------------------------------------

 //---------------------------------------------------------------------------------------------------------------------
 // Method : loadProperty
 // Input  :
 // Output :
 // Purpose: load all properties from the property file
 // Note   :
 //---------------------------------------------------------------------------------------------------------------------
 static 
 {   try {
	 	FileInputStream is =new FileInputStream(m_sPropertyFile);
		if (is != null) {
			m_oProperties.load(is);
			is.close();
		}
		soiEnable=PropertyTool.getIntProperty("soi_enable");
		soi85Enable=PropertyTool.getIntProperty("soi85_enable");
		lxEnable=PropertyTool.getIntProperty("lx_enable");
		timEnable=PropertyTool.getIntProperty("tim_enable");
		lx85Enable=PropertyTool.getIntProperty("lx85_enable");
		tim85Enable=PropertyTool.getIntProperty("tim85_enable");
		xsttEnable=PropertyTool.getIntProperty("xstt_enable");
		l6Enable=PropertyTool.getIntProperty("l6_enable");
		ckEnable=PropertyTool.getIntProperty("ck_enable");
 	 } catch (Exception e) {
 			e.printStackTrace();
 	 }	
 }  
 //---------------------------------------------------------------------------------------------------------------------
 // Method : saveProperty
 // Input  :
 // Output :
 // Purpose: save all properties to the property file
 // Note   :
 //---------------------------------------------------------------------------------------------------------------------
 public static void saveProperties() throws Exception
 {
     FileOutputStream        oFileOutputStream;
     BufferedOutputStream    oBufferedOutputStream;

         oFileOutputStream = new FileOutputStream(m_sPropertyFile);
         oBufferedOutputStream = new BufferedOutputStream(oFileOutputStream);
         m_oProperties.store(oBufferedOutputStream, m_sPropertyFile);
         oBufferedOutputStream.close();
         
 }

 //---------------------------------------------------------------------------------------------------------------------
 // Method : setProperty
 // Input  : sKey    - key of the property
 //          sValue  - value of the property
 // Output :
 // Purpose: set value to a property specified by sKey parameter
 // Note   :
 //---------------------------------------------------------------------------------------------------------------------
 public static void setProperty(String sKey, String sValue)
 {
     m_oProperties.setProperty(sKey, sValue);
 }
 public static void setPropertyFile(String propertyFile)
 {
     m_sPropertyFile=propertyFile;
 }

 //---------------------------------------------------------------------------------------------------------------------
 // Method : getProperty
 // Input  : sKey    - key of the property
 // Output : string value of the property
 // Purpose: get value of a property specified by sKey parameter
 // Note   :
 //---------------------------------------------------------------------------------------------------------------------
 public static String getProperty(String sKey)
 {
     return m_oProperties.getProperty(sKey);
 }
 public static int getIntProperty(String sKey)
 {
	 try {
		 return Integer.parseInt(m_oProperties.getProperty(sKey));
	} catch (Exception e) {
		return 0;
	}
     
 }
 public static void main(String[] arg){
	 System.out.println(PropertyTool.soiEnable);
	 System.out.println(PropertyTool.xsttEnable);
 }
 
 
}