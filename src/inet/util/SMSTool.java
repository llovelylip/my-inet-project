package inet.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

public class SMSTool {
    //check the valid destination mobile
	  
	  private static Collection zipCodeList = new Vector();
	  private static Map zipCodeTable = new Hashtable();

	  static
	  {
	    zipCodeTable.put("781", "Bac Lieu");
	    zipCodeTable.put("780", "Ca Mau");
	    zipCodeTable.put("711", "Hau Giang");
	    zipCodeTable.put("710", "Can Tho");
	    zipCodeTable.put("651", "Binh Phuoc");
	    zipCodeTable.put("650", "Binh Duong");
	    zipCodeTable.put("511", "Da Nang");
	    zipCodeTable.put("510", "Quang Nam");
	    zipCodeTable.put("501", "Dac Nong");
	    zipCodeTable.put("500", "Dac Lac");
	    zipCodeTable.put("351", "Ha Nam");
	    zipCodeTable.put("350", "Nam Dinh");
	    zipCodeTable.put("321", "Hung Yen");
	    zipCodeTable.put("320", "Hai Duong");
	    zipCodeTable.put("281", "Bac Can");
	    zipCodeTable.put("280", "Thai Nguyen");
	    zipCodeTable.put("241", "Bac Ninh");
	    zipCodeTable.put("240", "Bac Giang");
	    zipCodeTable.put("231", "Lai Chau");
	    zipCodeTable.put("230", "Dien Bien");
	    zipCodeTable.put("219", "Ha Giang");
	    zipCodeTable.put("218", "Hoa Binh");
	    zipCodeTable.put("211", "Vinh Phuc");
	    zipCodeTable.put("210", "Phu Tho");
	    zipCodeTable.put("79", "Soc Trang");
	    zipCodeTable.put("77", "Kien Giang");
	    zipCodeTable.put("76", "An Giang");
	    zipCodeTable.put("75", "Ben Tre");
	    zipCodeTable.put("74", "Tra Vinh");
	    zipCodeTable.put("73", "Tien Giang");
	    zipCodeTable.put("72", "Long An");
	    zipCodeTable.put("70", "Vinh Long");
	    zipCodeTable.put("68", "Ninh Thuan");
	    zipCodeTable.put("67", "Dong Thap");
	    zipCodeTable.put("66", "Tay Ninh");
	    zipCodeTable.put("64", "Ba Ria Vung Tau");
	    zipCodeTable.put("63", "Lam Dong");
	    zipCodeTable.put("62", "Binh Thuan");
	    zipCodeTable.put("61", "Dong Nai");
	    zipCodeTable.put("60", "Kon Tum");
	    zipCodeTable.put("59", "Gia Lai");
	    zipCodeTable.put("58", "Khanh Hoa");
	    zipCodeTable.put("57", "Phu Yen");
	    zipCodeTable.put("56", "Binh Dinh");
	    zipCodeTable.put("55", "Quang Ngai");
	    zipCodeTable.put("54", "Thua Thien Hue");
	    zipCodeTable.put("53", "Quang Tri");
	    zipCodeTable.put("52", "Quang Binh");
	    zipCodeTable.put("39", "Ha Tinh");
	    zipCodeTable.put("38", "Nghe An");
	    zipCodeTable.put("37", "Thanh Hoa");
	    zipCodeTable.put("36", "Thai Binh");
	    zipCodeTable.put("33", "Quang Ninh");
	    zipCodeTable.put("31", "Hai Phong");
	    zipCodeTable.put("30", "Ninh Binh");
	    zipCodeTable.put("29", "Yen Bai");
	    zipCodeTable.put("27", "Tuyen Quang");
	    zipCodeTable.put("26", "Cao Bang");
	    zipCodeTable.put("25", "Lang Son");
	    zipCodeTable.put("22", "Son La");
	    zipCodeTable.put("20", "Lao Cai");
	    zipCodeTable.put("8", "TP.Ho Chi Minh");
	    zipCodeTable.put("4", "Ha Noi");

	    zipCodeList = zipCodeTable.keySet();
	  }

	  public static String buildMobileOperator(String mobileNumber)
	  {
	    String mobileOperator = null;
	    if ((mobileNumber.startsWith("8491")) || (mobileNumber.startsWith("091"))
	    	|| (mobileNumber.startsWith("8494")) || (mobileNumber.startsWith("094"))
	    	|| (mobileNumber.startsWith("84123")) || (mobileNumber.startsWith("0123"))
	    	|| (mobileNumber.startsWith("84124")) || (mobileNumber.startsWith("0124")) 
	    	|| (mobileNumber.startsWith("84125")) || (mobileNumber.startsWith("0125")) 
	    	|| (mobileNumber.startsWith("84127")) || (mobileNumber.startsWith("0127"))
	    	|| (mobileNumber.startsWith("84129")) || (mobileNumber.startsWith("0129")))
	    {
	      mobileOperator = "GPC";
	    } else if ((mobileNumber.startsWith("8490")) || (mobileNumber.startsWith("090")) || 
	    			(mobileNumber.startsWith("8493")) || (mobileNumber.startsWith("093")) ||
	    			(mobileNumber.startsWith("84121")) || (mobileNumber.startsWith("0121")) || 
	    			(mobileNumber.startsWith("84122")) || (mobileNumber.startsWith("0122")) ||
	    			(mobileNumber.startsWith("84126")) || (mobileNumber.startsWith("0126")) ||
	    			(mobileNumber.startsWith("84128"))||(mobileNumber.startsWith("0128")) ||
	    		    (mobileNumber.startsWith("0120"))|| (mobileNumber.startsWith("84120")))
	    {
	      mobileOperator = "VMS";
	    } else if ((mobileNumber.startsWith("8497")) || (mobileNumber.startsWith("097")) ||
	    		(mobileNumber.startsWith("8498")) || (mobileNumber.startsWith("098")) || 
	    		(mobileNumber.startsWith("84165")) || (mobileNumber.startsWith("0165"))||
	    		(mobileNumber.startsWith("84163")) || (mobileNumber.startsWith("0163"))||
	    		(mobileNumber.startsWith("84164")) || (mobileNumber.startsWith("0164"))||
	    		(mobileNumber.startsWith("84166")) || (mobileNumber.startsWith("0166"))||
	    		(mobileNumber.startsWith("84167")) || (mobileNumber.startsWith("0167"))||
	    		(mobileNumber.startsWith("84168")) || (mobileNumber.startsWith("0168"))||
	    		(mobileNumber.startsWith("84169")) || (mobileNumber.startsWith("0169")))
	    {
	      mobileOperator = "VIETEL";
	    } else if ((mobileNumber.startsWith("8492")) || (mobileNumber.startsWith("092"))|| 
	    		(mobileNumber.startsWith("0188"))|| (mobileNumber.startsWith("84188")))
	      mobileOperator = "HNT";
	    else if ((mobileNumber.startsWith("84199")) || (mobileNumber.startsWith("0199")))
		      mobileOperator = "BEELINE";
	    else if ((mobileNumber.startsWith("8495")) || (mobileNumber.startsWith("095")))
	      mobileOperator = "SFONE";
	    else if ((mobileNumber.startsWith("8496")) || (mobileNumber.startsWith("096")))
	      mobileOperator = "EVN";
	    else if (mobileNumber.length() > 6)
	    {
	      String shortcode = removeZipCode(mobileNumber);
	      System.out.println("shortcode="+shortcode);
	      if (shortcode.startsWith("2"))
	        mobileOperator = "EVN";
	      else if (shortcode.startsWith("4"))
	        mobileOperator = "VTC";
	      else if (shortcode.startsWith("5"))
	        mobileOperator = "SPT";
	      else if (shortcode.startsWith("6"))
	        mobileOperator = "VIETEL";
	      else if ((shortcode.startsWith("3")) || (shortcode.startsWith("8")))
	        mobileOperator = "GPC";
	      else if (shortcode.startsWith("7"))
	        mobileOperator = "FPT";
	    }
	    else
	      mobileOperator = "UNKNOWN";

	    return mobileOperator;
	  }

    public static boolean isValidMobileNumber(String userId) {
        if (userId == null || "".equals(userId) || !StringTool.isNumberic(userId)) {
            return false;
        }
        if (((userId.startsWith("90")  || userId.startsWith("91"))   && userId.length() == 9)  ||
           ((userId.startsWith("090")  || userId.startsWith("091"))  && userId.length() == 10) ||
           ((userId.startsWith("8490") || userId.startsWith("8491")) && userId.length() == 11)) {
             return true;
        } else {
            return false;
        }

    }

   
   

    // Call this method before storing or sending info (short message) to mobile
    public static String filterInfo(String info) {
        //1) Remove '\r' (0x0D) character
        if (info == null) return null;
        //Moi lan enter se co 2 ky tu  \r\n
        info = inet.util.StringTool.removeChar(info, '\r'); //de lai \n


        //2) max = 160 chars
        if (info.length() > 160) {
            info = info.substring(0, 160);// 0 -> 159
        }
        return info;
    }

    static char NINE = (char) 0x39;
    static final char ZERO = (char) 0x30;
    //extract 091xx or 090xxx (10 ky tu)
    //[0] --> mobile number
    //[1] --> info without mobile number
    public static String[] extractMobileNumber(String info) {
        if (info == null || info.length() <= 10) return null;

        String mobile = null;
        int index = info.indexOf("09");
        String tempMobile = null;

        while(index > 0 && info.length() >= (index + 10)) {
            tempMobile = info.substring(index, index + 10);
            if (StringTool.isNumberic(tempMobile)) {
                mobile = tempMobile;
                info = info.substring(0, index) + " " + info.substring(index + 10);
                break;
            }
            index = info.indexOf("09", index + 2);
        }
        String[] result = null;
        String[] numbers=new String[]{"0164","0165","0166","0167","0168","0169","0121","0122","0123","0125","0126","0127","0128","0129","0120"};
        if(mobile==null){
        	for(int i=0;i<numbers.length;i++){
        		index=info.indexOf(numbers[i]);
        		 while(index > 0 && info.length() >= (index + 11)) {
        	            tempMobile = info.substring(index, index + 11);
        	            if (StringTool.isNumberic(tempMobile)) {
        	                mobile = tempMobile;
        	                info = info.substring(0, index) + " " + info.substring(index + 11);
        	                break;
        	            }
        	            index = info.indexOf(numbers[i], index + numbers[i].length());
        	      }
        	}
        }
        if (mobile != null) {
            result = new String[2];
            result[0] = mobile;
            result[1] = info;
        }
        return result;
    }
    public static String removeZipCode(String mobileNumber)
    {
      if ((mobileNumber == null) || (!(mobileNumber.startsWith("84"))) || (mobileNumber.length() < 6)) return mobileNumber;

     if(mobileNumber.startsWith("84")){
    	 mobileNumber = mobileNumber.substring(2);
     }else if(mobileNumber.startsWith("0")){
    	 mobileNumber = mobileNumber.substring(1);
     }

      String zipcode = null;
      for (Iterator it = zipCodeList.iterator(); it.hasNext(); ) {
        zipcode = (String)it.next();
        if ((mobileNumber.startsWith(zipcode))){
      	  mobileNumber = mobileNumber.substring(zipcode.length());
        }
      }
      return mobileNumber;
    }
    public static void main(String[] args) {
        SMSTool smsTool = new SMSTool();
       System.out.println(SMSTool.isValidMobileNumber("09441356333"));
    }
}
