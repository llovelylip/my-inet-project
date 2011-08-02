package inet.util;

import java.math.BigDecimal;

public class RoundTool {
	public static String roundNumber(float r){
		String s=null;
		BigDecimal bd = new BigDecimal(r);
		bd = bd.setScale( 2, BigDecimal.ROUND_HALF_UP);
		r = bd.floatValue();
		if(r<0){
			s=r+"";
		}else{
			s="+"+r;
		}
		return s;
	}
	public static float roundNumber(float r,int precise){	
		BigDecimal bd = new BigDecimal(r);
		bd = bd.setScale(precise, BigDecimal.ROUND_HALF_UP);
		r = bd.floatValue();
		
		return r;
	}
	public static void main(String[] arg){
		System.out.println(RoundTool.roundNumber((float)(10.005)));
	}
}
