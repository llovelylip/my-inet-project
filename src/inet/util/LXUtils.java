package inet.util;

import java.util.Hashtable;

public class LXUtils {
	public static Hashtable subCodeMTs=new Hashtable();
	public static Hashtable subCodeMNs=new Hashtable();
	static{
		subCodeMTs.put("PY", "Xo so Phu Yen");
    	subCodeMTs.put("TTH", "Xo so Thua Thien Hue");
    	subCodeMTs.put("DLK", "Xo so Dac Lac");
    	subCodeMTs.put("QNM", "Xo so Quang Nam");
    	subCodeMTs.put("DNG", "Xo so Da Nang");
    	subCodeMTs.put("KH", "Xo so Khanh Hoa");
    	subCodeMTs.put("QB", "Xo so Quang Binh");
    	subCodeMTs.put("QT", "Xo so Quang Tri");
    	subCodeMTs.put("BDH", "Xo so Binh Dinh");
    	subCodeMTs.put("QNI", "Xo so Quang Ngai");
    	subCodeMTs.put("DNO", "Xo so Dak Nong");
    	subCodeMTs.put("KT", "Xo so Kon Tum");
    	subCodeMTs.put("GL", "Xo so Gia Lai");
    	subCodeMTs.put("NT", "Xo so Ninh Thuan");
    	subCodeMTs.put("MT", "Xo so Mien Trung");
    	subCodeMNs.put("HCM", "Xo so Ho Chi Minh");
    	subCodeMNs.put("TP", "Xo so Ho Chi Minh");
    	subCodeMNs.put("DT", "Xo so Dong Thap");
    	subCodeMNs.put("CM", "Xo so Ca Mau");
    	subCodeMNs.put("VT", "Xo so Vung Tau");
    	subCodeMNs.put("BTR", "Xo so Ben Tre");
    	subCodeMNs.put("BL", "Xo so Bac Lieu");
    	subCodeMNs.put("DN", "Xo so Dong Nai");
    	subCodeMNs.put("ST", "Xo so Soc Trang");
    	subCodeMNs.put("CT", "Xo so Can Tho");
    	subCodeMNs.put("BTH", "Xo so Binh Thuan");
    	subCodeMNs.put("AG", "Xo so An Giang");
    	subCodeMNs.put("TN", "Xo so Tay Ninh");
    	subCodeMNs.put("BD", "Xo so Binh Duong");
    	subCodeMNs.put("VL", "Xo so Vinh Long");
    	subCodeMNs.put("TV", "Xo so Tra Vinh");
    	subCodeMNs.put("LA", "Xo so Long An");
    	subCodeMNs.put("BP", "Xo so Binh Phuoc");
    	subCodeMNs.put("HG", "Xo so Hau Giang");
    	subCodeMNs.put("TG", "Xo so Tien Giang");
    	subCodeMNs.put("KG", "Xo so Kien Giang");
    	subCodeMNs.put("DL", "Xo so Da Lat");
    	subCodeMNs.put("MN", "Xo so Mien Nam");
    	subCodeMNs.put("BT", "Xo so Ben Tre");
	}
	public static String getCodeByName(String info) {
        if (info == null) return null;
        info = inet.util.StringTool.removeSpecialCharsInString(info);
        String code = null;
        if (info.indexOf("THUDO") >= 0) {
            code = "TD";
        } else if (info.indexOf("HAIPHONG") >= 0) {
            code = "TD";
        } else if (info.indexOf("QUANGNINH") >= 0) {
            code = "TD";
        } else if (info.indexOf("BACNINH") >= 0) {
            code = "TD";
        } else if (info.indexOf("THAIBINH") >= 0) {
            code = "XSTD";
        } else if (info.indexOf("NAMDINH") >= 0) {
            code = "TD";
        } else if (info.indexOf("HANOI") >= 0) {
            code = "TD";
        } else if (info.indexOf("ANGIANG") >= 0) {
            code = "AG";
        } else if (info.indexOf("BINHDUONG") >= 0) {
            code = "BD";
        } else if (info.indexOf("SOCTRANG") >= 0) {
            code = "ST";
        } else if (info.indexOf("KIENGIANG") >= 0) {
            code = "KQ";
        } else if (info.indexOf("TIENGIANG") >= 0) {
            code = "TG";
        } else if (info.indexOf("DONGNAI") >= 0) {
            code = "DN";
        } else if (info.indexOf("TAYNINH") >= 0) {
            code = "TN";
        } else if (info.indexOf("VINHLONG") >= 0) {
            code = "VL";
        } else if (info.indexOf("LONGAN") >= 0) {
            code = "LA";
        } else if (info.indexOf("BINHPHUOC") >= 0) {
            code = "BP";
        } else if (info.indexOf("CANTHO") >= 0) {
            code = "CT";
        } else if (info.indexOf("DANANG") >= 0) {
            code = "DNG";
        } else if (info.indexOf("BACLIEU") >= 0) {
            code = "BL";
        } else if (info.indexOf("TRAVINH") >=0) {
            code = "TV";
        } else if (info.indexOf("BENTRE") >= 0) {
            code = "BTR";
        } else if (info.indexOf("BINHTHUAN") >= 0) {
            code = "BTH";
        } else if (info.indexOf("VUNGTAU") >= 0) {
            code = "VT";
        } else if (info.indexOf("CAMAU") >= 0) {
            code = "CM";
        } else if (info.indexOf("DALAT") >= 0) {
            code = "DL";
        } else if (info.indexOf("DONGTHAP") >= 0) {
            code = "DT";
        } else if (info.indexOf("KHANHHOA") >= 0) {
            code = "KH";
        } else if (info.indexOf("PHUYEN") >= 0) {
            code = "PY";
        } else if (info.indexOf("KHANHHOA") >= 0) {
            code = "KH";
        } else if (info.indexOf("HAUGIANG") >= 0) {
            code = "HG";
        } else if (info.indexOf("KONTUM") >= 0) {
            code = "KT";
        } else if (info.indexOf("DAKLAK") >= 0) {
            code = "DLK";
        } else if (info.indexOf("BINHDINH") >= 0) {
            code = "BDH";
        } else if (info.indexOf("DACNONG") >= 0) {
            code = "DNO";
        } else if (info.indexOf("GIALAI") >= 0) {
            code = "GL";
        } else if (info.indexOf("QUANGBINH") >= 0) {
            code = "QB";
        } else if (info.indexOf("QUANGTRI") >= 0) {
            code = "QT";
        }else if (info.indexOf("SAIGON") >= 0) {
            code = "HCM";
        }else if (info.indexOf("HOCHIMINH") >= 0) {
            code = "HCM";
        }else if (info.indexOf("NINHTHUAN") >= 0) {
            code = "NT";
        }else if (info.indexOf("THUATHIENHUE") >= 0) {
            code = "TTH";
        }else if (info.indexOf("QUANGNGAI") >= 0) {
            code = "QNI";
        }

      //  System.out.println("Code="+code);

        return code;
    }
}
