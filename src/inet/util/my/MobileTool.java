package inet.util.my;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

public class MobileTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MobileTool tool = new MobileTool();
//		System.out.println(tool.getStandardMobileNumber("916558266"));
//		System.out.println(tool.getStandardMobileNumber("0916558266"));
//		System.out.println(tool.getStandardMobileNumber("84916558266"));
//		System.out.println(tool.getStandardMobileNumber("840916558266"));
//		System.out.println(tool.getStandardMobileNumber("+84916558266"));
//		System.out.println(tool.getStandardMobileNumber("+840916558266"));
//		
//		
//		Vector result = new Vector();
//		result.add(new Integer(1));
//		result.add(new Integer(2));
//		result.add(new Integer(3));
//		System.out.println(result.contains(new Integer(1)));
//		System.out.println(result.contains(new Integer(2)));
//		System.out.println(result.contains(new Integer(4)));
//		
//		Collection coll = new Vector();
//		coll.add("T1 t1 t2 t3");
//		coll.add("T2 t1 t2 t3");
//		coll.add("T3 t1 t2 t3");
//		System.out.println(coll);
		
		String listCompatiblePhones = "Loại máy hỗ trợ: "+
					"BlackBerry:"+					
					"7130e, 8100, 8110, 8130, 8220, 8230, 8330, 8520, 8700c, 8700r, 8700v, 8703e, 8707, 8707v, 8820, 8830, 9100, 9500, 9520, 9550"+
					"HTC:"+
					"8500, P3400, P3450, P3470, P3650, P4350, P4550, S310, S621, S630, S710, S730, S740"+
					"Nokia:"+
					"1680c, 2330c, 2600c, 2630, 2660, 2680s, 2690, 2760, 2855, 2865, 2865i, 3109, 3109c, 3110, 3110c, 3120c, 3155, 3155i, 3230, 3250, 3500, 3500c, 3555, 3600 slide, 5000, 5000d, 5070, 5070b, 5130, 5200, 5220, 5230, 5235, 5300, 5310, 5500, 5610, 5610d, 5700, 5800, 6060, 6061, 6070, 6080, 6085, 6086, 6101, 6102, 6102i, 6103, 6111, 6120, 6120, 6121c, 6124c, 6125, 6126, 6131, 6133, 6151, 6155, 6165, 6170, 6212, 6220c, 6230i, 6233, 6234, 6260, 6265, 6265i, 6267, 6270, 6275, 6275i, 6280, 6282, 6288, 6290, 6300, 6301, 6303c, 6315i, 6500, 6500c, 6500s, 6555, 6555c, 6600, 6600f, 6600s, 6620, 6630, 6670, 6680, 6681, 6682, 6830, 7070, 7100, 7230, 7270, 7360, 7370, 7373, 7390, 7500, 7610, 7900, 8600, 8800, 8801, C5, C6, E50, E51, E52, E60, E61 , E61i, E62, E63, E65, E70, E72, E75, N70, N71, N72, N73, N75, N76, N77, N78, N79, N80, N81, N85, N86, N90, N91, N93, N93i, N95, N96, N97, X3, X6";
		Vector vList = new Vector();
		vList.add("n3432");
		vList.add("2600c");
		System.out.println(MobileTool.checkCompatiblePhone(vList, listCompatiblePhones));
		
		
		
	}
	
	
	public static String getStandardMobileNumber(String mobileNumber){
		//System.out.print(mobileNumber+":");
		if(mobileNumber==null){
			return null;
		} else if(mobileNumber.length() < 6){
			return null;
		}
		String standardMobileNumber = null;
		
		
		if(mobileNumber.startsWith("0")){
			standardMobileNumber = mobileNumber.substring(1);
		} else if(mobileNumber.startsWith("840")){
			standardMobileNumber = mobileNumber.substring(3);
		} else if(mobileNumber.startsWith("84")){
			standardMobileNumber = mobileNumber.substring(2);
		} else if(mobileNumber.startsWith("+840")){
			standardMobileNumber = mobileNumber.substring(4);
		} else if( mobileNumber.startsWith("+84")){
			standardMobileNumber = mobileNumber.substring(3);
		} else{
			standardMobileNumber = mobileNumber;
		}
		
	    return ("84"+standardMobileNumber);
	}
	
	public static String getMobileNumberWithoutPre(String mobileNumber){
		//System.out.print(mobileNumber+":");
		if(mobileNumber==null){
			return "";
		}
		String standardMobileNumber = "";
		
		if(mobileNumber.startsWith("0")){
			standardMobileNumber = mobileNumber.substring(1);
		} else if(mobileNumber.startsWith("840")){
			standardMobileNumber = mobileNumber.substring(3);
		} else if(mobileNumber.startsWith("84")){
			standardMobileNumber = mobileNumber.substring(2);
		} else if(mobileNumber.startsWith("+840")){
			standardMobileNumber = mobileNumber.substring(4);
		} else if( mobileNumber.startsWith("+84")){
			standardMobileNumber = mobileNumber.substring(3);
		} else{
			standardMobileNumber = mobileNumber;
		}
		return (standardMobileNumber);
	}

	public static boolean checkCompatiblePhone(Vector vPhoneModel, String listCompatiblePhones){
		if(vPhoneModel==null || vPhoneModel.size()==0) return true;
		if(listCompatiblePhones==null || "".equalsIgnoreCase(listCompatiblePhones.trim())) return false;
		String phoneModel = null;
		for(int i=0; i<vPhoneModel.size(); i++){
			phoneModel = vPhoneModel.get(i).toString().toUpperCase();
			if(listCompatiblePhones.toUpperCase().indexOf((phoneModel.toUpperCase()))!=-1) return true;
		}
		return false;
	}

	public static float roundNumber(float r,int precise){ 
		BigDecimal bd = new BigDecimal(r);
		bd = bd.setScale(precise, BigDecimal.ROUND_HALF_UP);
		r = bd.floatValue();
		
		return r;
	}
	
}
