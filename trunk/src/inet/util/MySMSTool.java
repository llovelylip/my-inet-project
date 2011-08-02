package inet.util;

public class MySMSTool extends SMSTool {

	public MySMSTool() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("moi day");
	}
 
    public static String reBuildInfo(String info,String commandCode,String serviceNumber){
		   String s=info.replaceAll("code", commandCode);
		   s=s.replaceAll("number", serviceNumber);
		   return s;
	}
    

	

}
