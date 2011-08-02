package inet.util.my;

import java.util.Hashtable;
import java.util.Vector;

public class CamnangSMSTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static Hashtable<String, String> hashConvertCode = null;
	private static Vector<String> vOldCode = null;
	private static Vector<String> vNewCode = null;
	
	static{
		hashConvertCode = new Hashtable<String, String>();
		hashConvertCode.put("NA", "NZ");
		hashConvertCode.put("TUOI", "TZ");
		hashConvertCode.put("NS", "NZ");
		hashConvertCode.put("NSH", "NZH");
		hashConvertCode.put("AG", "MZ");
		hashConvertCode.put("CHA", "CHD");
		hashConvertCode.put("TEN", "TENA");
		hashConvertCode.put("TENH", "TENB");
		hashConvertCode.put("SC", "EP");
		hashConvertCode.put("DD", "DDA");
		hashConvertCode.put("TIN", "TIL");
		hashConvertCode.put("TG", "TZ");
		hashConvertCode.put("VANG", "VAG");
		hashConvertCode.put("BSX", "BSA");
		hashConvertCode.put("VT", "VAN");
		hashConvertCode.put("XD", "XDT");
		hashConvertCode.put("SAO", "SAG");
		hashConvertCode.put("BABY", "BEBE");
		hashConvertCode.put("SDT", "DTD");
		hashConvertCode.put("TUVI", "TUV");
		hashConvertCode.put("VSA", "VSN");
		hashConvertCode.put("AKA", "AW");
		hashConvertCode.put("GPRS", "GPRST");
		hashConvertCode.put("RING", "RIN");
		hashConvertCode.put("CHAT", "CHATM");
		hashConvertCode.put("GN", "GNA");
		
		vOldCode = new Vector<String>();
		vOldCode.add("NA");
		vOldCode.add("TUOI");
		vOldCode.add("NS");
		vOldCode.add("NSH");
		vOldCode.add("AG");
		vOldCode.add("CHA");
		vOldCode.add("TEN");
		vOldCode.add("TENH");
		vOldCode.add("SC");
		vOldCode.add("DD");
		vOldCode.add("TIN");
		vOldCode.add("TG");
		vOldCode.add("VANG");
		vOldCode.add("BSX");
		vOldCode.add("VT");
		vOldCode.add("XD");
		vOldCode.add("SAO");
		vOldCode.add("BABY");
		vOldCode.add("SDT");
		vOldCode.add("TUVI");
		vOldCode.add("VSA");
		vOldCode.add("AKA");
		vOldCode.add("GPRS");
		vOldCode.add("RING");
		vOldCode.add("CHAT");
		vOldCode.add("GN");

		
		vNewCode = new Vector<String>();
		vNewCode.add("NZ");
		vNewCode.add("TZ");
		vNewCode.add("NZ");
		vNewCode.add("NZH");
		vNewCode.add("MZ");
		vNewCode.add("CHD");
		vNewCode.add("TENA");
		vNewCode.add("TENB");
		vNewCode.add("EP");
		vNewCode.add("DDA");
		vNewCode.add("TIL");
		vNewCode.add("TZ");
		vNewCode.add("VAG");
		vNewCode.add("BSA");
		vNewCode.add("VAN");
		vNewCode.add("XDT");
		vNewCode.add("SAG");
		vNewCode.add("BEBE");
		vNewCode.add("DTD");
		vNewCode.add("TUV");
		vNewCode.add("VSN");
		vNewCode.add("AW");
		vNewCode.add("GPRST");
		vNewCode.add("RIN");
		vNewCode.add("CHATM");
		vNewCode.add("GNA");
	}
	
	
	public static String convert2NewCode(String strWithOldCode){
		if(strWithOldCode==null || "".equalsIgnoreCase(strWithOldCode.trim())){
			return strWithOldCode;
		}
		String strResult = null;
		String oldCode = null;
		for(int i=0; i<vOldCode.size(); i++){
			oldCode = vOldCode.get(i);
			if(strWithOldCode.indexOf(oldCode+" ")!=-1){
				strResult = strWithOldCode.replaceAll(oldCode+" ", hashConvertCode.get(oldCode)+" ");
				break;
			}
		}
		return strResult;
		
	}
}
