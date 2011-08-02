package inet.util;

public class SMSSmartTool {
	static char[] PentaTable = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X' };
	public static String Decimal2Binary(int number,int bits){
		String result=Integer.toBinaryString(number);
		if(result.length()>bits){
			result=null;
		}else if(result.length()<bits){
			int lech=bits-result.length();
			for(int i=0;i<lech;i++){
				result="0"+result;
			}
		}
		return result;
	}
	
	
	public static String Decimal2Binary(String number){
		System.out.println("number="+number);
		String result=null;
		if(number!=null&&number.length()>0){
			result="";
			for(int i=0;i<number.length();i++){
				result=result+Decimal2Binary(Integer.parseInt(number.substring(i,i+1)),5);
			}
			
			
		}
		
		return result;
	}
	public static int Binary2Decimal(String binaryString){
		return Integer.parseInt(binaryString, 2);
	}
	//return postition Of Charater in Pental table
	public static String Penta2Decimal(String sPental){
		String result="";
		for(int i=0;i<sPental.length();i++){
			char c=sPental.charAt(i);
			result=result+Penta2Decimal(c);
		}
		
		return result;
	}
	public static int Penta2Decimal(char c){
		int result=0;
		for(int i=0;i<PentaTable.length;i++){
			if(c==PentaTable[i]){
				result=i;
			}
		}
		return result;
	}
	
	//return  Charater Of Postion in Pental table
	public static char Decimal2Pental(int i){
		return PentaTable[i];
	}
	public static void main(String[] arg){
		System.out.println(SMSSmartTool.Decimal2Binary(10,4));
	}
	 public static String reverseString(String source) {
		    int i, len = source.length();
		    StringBuffer dest = new StringBuffer(len);
		    for (i = (len - 1); i >= 0; i--)
		      dest.append(source.charAt(i));
		    return dest.toString();
	 }
	
	
}
