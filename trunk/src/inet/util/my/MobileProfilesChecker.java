package inet.util.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import inet.util.GetContent;
import inet.util.StringTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MobileProfilesChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String urlXml = "http://nds1.nds.nokia.com/uaprof/NokiaC6-00r100.xml";
		urlXml = "http://wap.samsungmobile.com/uaprof/GT-S8500_3G.rdf";
		//urlXml = "http://www.htcmms.com.tw/Android/Common/Bravo/HTC_Desire_A8181.xml";
		System.out.println("1111111: "+testGetPhoneModel(urlXml));
		System.out.println("2222222: "+testGetPhoneModel2(urlXml));
		
		
//		urlXml = urlXml.replaceAll("\"", "");
//		String contentString = GetContent.getInfo(urlXml);
//		String phoneModel = null;
//		if(contentString.indexOf("<prf:Model") != -1){
//			//phoneModel = contentString.substring(contentString.indexOf("<prf:Model>")+"<prf:Model>".length(), contentString.indexOf("</prf:Model>")) ;
//			phoneModel = contentString.substring(contentString.indexOf("<prf:Model"), contentString.indexOf("</prf:Model>")) ;
//			phoneModel = phoneModel.substring(phoneModel.indexOf(">")+1);
//			
//			
//			System.out.println(phoneModel);;
//		}
//		
		//String aaa="NOKIA-C6-00";
		//System.out.println(StringTool.parseString(aaa.replaceAll("NOKIA", "")));
		//System.out.println(getListModelPhone(aaa));
	}
	
	
	public static String getUrlProfile(HttpServletRequest request){
		String urlXml = null;
		if(request.getHeader("x-wap-profile") != null){
			urlXml = request.getHeader("x-wap-profile").toString();
		} else if(request.getHeader("X-WAP-PROFILE") != null){
			urlXml = request.getHeader("X-WAP-PROFILE").toString();
		}
		return urlXml;
	}
	
	
	public static String getPhoneModel(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		if(session.getAttribute("wap3g-phone-model")!=null){
			return session.getAttribute("wap3g-phone-model").toString();
		}
		
		String urlXml = null;
		urlXml = getUrlProfile(request);
		
		if(urlXml == null || "".equalsIgnoreCase(urlXml.trim())) return null;
		
		try {
			urlXml = urlXml.replaceAll("\"", "");
			String contentString = GetContent.getInfo(urlXml);
			String phoneModel = null;
			if(contentString.indexOf("<prf:Model") != -1){
				//phoneModel = contentString.substring(contentString.indexOf("<prf:Model>")+"<prf:Model>".length(), contentString.indexOf("</prf:Model>")) ;
				phoneModel = contentString.substring(contentString.indexOf("<prf:Model"), contentString.indexOf("</prf:Model>")) ;
				phoneModel = phoneModel.substring(phoneModel.indexOf(">")+1);
				
				if(phoneModel!=null){
					session.setAttribute("wap3g-phone-model", phoneModel);
					session.setMaxInactiveInterval(1000*60*60*24);
				}
				return phoneModel;
			}
		} catch (Exception ex) {
			// TODO: handle exception	
		}
		return null;
	}
	
	public static String getPhoneModel2(HttpServletRequest request){
		String phoneModel = null;
		
		HttpSession session=request.getSession(true);
		if(session.getAttribute("wap3g-phone-model")!=null){
			phoneModel=session.getAttribute("wap3g-phone-model").toString();
			return phoneModel;
		}
		
		String urlXml = null;
		urlXml = getUrlProfile(request);
		
		if(urlXml == null || "".equalsIgnoreCase(urlXml.trim())) return null;
		
		try {
			urlXml = urlXml.replaceAll("\"", "");
			URL url = new URL(urlXml);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			int i=0;
			while((line=in.readLine()) != null){
				i++;
				//System.out.println(i+": "+line);
				line = line.trim();
				
				if(line.startsWith("<prf:Model")){
					line = line.replaceAll("<prf:Model","");
					line = line.replaceAll("</prf:Model>","");
					//System.out.println("Model="+line);
					phoneModel  = line;
					break;
				}
			}
			
			//closing resource
			in.close();
			connection=null;
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			System.err.println("XWapProfileReader --> MalformedURLException");
			System.err.println("");
			ex.printStackTrace();
			System.err.println("---------------------------------------end---------------");
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			System.err.println("XWapProfileReader --> IOException");
			//ex.printStackTrace();
		}
		
		if(phoneModel!=null){
			session.setAttribute("wap3g-phone-model", phoneModel);
			session.setMaxInactiveInterval(1000*60*60*24);
		}
		return phoneModel;
	}
	
	
	
	public static Vector getListModelPhone(HttpServletRequest request){
		String modelPhone = getPhoneModel(request);
		if(modelPhone==null) return null;
		Vector<String> vListModelPhone = new Vector();
		vListModelPhone.add(modelPhone);
		modelPhone = modelPhone.toUpperCase();
		String[] listVendor = { "NOKIA", "SAMSUNG", "LG", "HTC", "ERICSSON", "SIEMENS", "SONYERICSSON",
								"LG", "APPLE", "ALCATEL", "BENQ", "BLACKBERRY",  "T-MOBILE", "GOOGLE",
								"HYUNDAI","I-MATE","KYOCERA","MITSUBISHI","MOTOROLA", "SONIM", "NEC", 
								"PALM", "PANASONIC", "PANTECH", "PHILIPS", "SAGEM", "SANYO", "SHARP", 
								};
		
		for(int i = 0; i<listVendor.length; i++){
			if(modelPhone.startsWith(listVendor[i])){
				modelPhone = modelPhone.replaceAll(listVendor[i],"");
				vListModelPhone.add(StringTool.removeSpecialCharsInFront(modelPhone));
				vListModelPhone.add(StringTool.removeSpecialCharsInString(modelPhone));
				vListModelPhone.add(((Vector)StringTool.parseString(modelPhone)).get(0).toString());
				return vListModelPhone;
			}
		}
		return vListModelPhone;
	}
	
	public static Vector getListModelPhone(String modelPhone){
		if(modelPhone==null) return null;
		Vector<String> vListModelPhone = new Vector();
		vListModelPhone.add(modelPhone);
		modelPhone = modelPhone.toUpperCase();
		String[] listVendor = { "NOKIA", "SAMSUNG", "LG", "HTC", "ERICSSON", "SIEMENS", "SONYERICSSON",
								"LG", "APPLE", "ALCATEL", "BENQ", "BLACKBERRY",  "T-MOBILE", "GOOGLE",
								"HYUNDAI","I-MATE","KYOCERA","MITSUBISHI","MOTOROLA", "SONIM", "NEC", 
								"PALM", "PANASONIC", "PANTECH", "PHILIPS", "SAGEM", "SANYO", "SHARP", 
								};
		
		for(int i = 0; i<listVendor.length; i++){
			if(modelPhone.startsWith(listVendor[i])){
				modelPhone = modelPhone.replaceAll(listVendor[i],"");
				vListModelPhone.add(StringTool.removeSpecialCharsInFront(modelPhone));
				vListModelPhone.add(StringTool.removeSpecialCharsInString(modelPhone));
				vListModelPhone.add(((Vector)StringTool.parseString(modelPhone)).get(0).toString());
				return vListModelPhone;
			}
		}
		return vListModelPhone;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String testGetPhoneModel(String urlXml){
		
		if(urlXml == null || "".equalsIgnoreCase(urlXml.trim())) return null;
		try {
			String contentString = GetContent.getInfo(urlXml);
			String phoneModel = null;
			if(contentString.indexOf("</prf:Model>") != -1){
				phoneModel = contentString.substring(contentString.indexOf("<prf:Model"), contentString.indexOf("</prf:Model>")) ;
				phoneModel = phoneModel.substring(phoneModel.indexOf(">")+1);
				return phoneModel;
			}
		} catch (Exception ex) {
			// TODO: handle exception	
		}
		return null;
	}

	public static String testGetPhoneModel2(String urlXml){
		String phoneModel = null;
		
		if(urlXml == null || "".equalsIgnoreCase(urlXml.trim())) return null;
		
		try {
			URL url = new URL(urlXml);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			int i=0;
			while((line=in.readLine()) != null){
				i++;
				//System.out.println(i+": "+line);
				line = line.trim();
				
				if(line.startsWith("<prf:Model")){
					line = line.replaceAll("<prf:Model>","");
					line = line.replaceAll("</prf:Model>","");
					//System.out.println("Model="+line);
					if(line.indexOf(">") != -1){
						line = line.substring(line.indexOf(">")+1);
					}
					phoneModel  = line;
					break;
				}
			}
			
			//closing resource
			in.close();
			connection=null;
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			System.err.println("XWapProfileReader --> MalformedURLException");
			System.err.println("");
			ex.printStackTrace();
			System.err.println("---------------------------------------end---------------");
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			System.err.println("XWapProfileReader --> IOException");
			//ex.printStackTrace();
		}
		
		
		return phoneModel;
	}
	
}
