package inet.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

public class MyDateProc extends DateProc{
	public static Timestamp plusToDate(Timestamp ts,int day) {
		if (ts == null)
			return null;
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new java.util.Date(ts.getTime()));
		int iDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, iDay + day);

		java.sql.Timestamp tsNew = new java.sql.Timestamp((calendar.getTime())
				.getTime());
		return tsNew;
	}
	public static Timestamp minusToDate(Timestamp ts,int day) {
		if (ts == null)
			return null;
		
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new java.util.Date(ts.getTime()));
	    calendar.add(Calendar.DAY_OF_MONTH,-day);
	    return new Timestamp(calendar.getTimeInMillis());
	}
	public static Timestamp minusToHour(Timestamp ts,int hour) {
		if (ts == null)
			return null;
		
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new java.util.Date(ts.getTime()));
	    calendar.add(Calendar.HOUR_OF_DAY,-hour);
	    
	    return new Timestamp(calendar.getTimeInMillis());
	}
	
	 public static java.sql.Timestamp String2Timestamp(String strInputDate,int hh,int mm,int ss) {
	        String strDate = strInputDate;
	        int i, nYear, nMonth, nDay;
	        String strSub = null;
	        i = strDate.indexOf("/");
	        if (i < 0)
	            return createTimestamp();
	        strSub = strDate.substring(0, i);
	        nDay = (new Integer(strSub.trim())).intValue();
	        strDate = strDate.substring(i + 1);
	        i = strDate.indexOf("/");
	        if (i < 0)
	            return createTimestamp();
	        strSub = strDate.substring(0, i);
	        nMonth = (new Integer(strSub.trim())).intValue() - 1; // Month begin from 0 value
	        strDate = strDate.substring(i + 1);
	        if (strDate.length() < 4) {
	            if (strDate.substring(0, 1).equals("9"))
	                strDate = "19" + strDate.trim();
	            else
	                strDate = "20" + strDate.trim();
	        }
	        nYear = (new Integer(strDate)).intValue();
	        java.util.Calendar calendar = java.util.Calendar.getInstance();
	        calendar.set(nYear, nMonth, nDay,hh,mm,ss);
	        return new java.sql.Timestamp( (calendar.getTime()).getTime());
	    }
	  public static java.sql.Timestamp String2Timestamp(String strInputDate,String time) {
	        Vector cLabels=(Vector)StringTool.parseStringEx(time);
	        Timestamp timestamp=null;
	       // System.out.println("size="+cLabels.size());
	        if(cLabels.size()!=3){
	        	return null;
	        }else{
	        	String sHH=(String)cLabels.get(0);
	        	String sMM=(String)cLabels.get(1);
	        	String sSS=(String)cLabels.get(2);
	        	int hh=0,mm=0,ss=0;
	        	try {
					hh=Integer.parseInt(sHH);
					if(hh>=24){
						throw new Exception("Gio khong hop le");
					}
					mm=Integer.parseInt(sMM);
					if(mm>=60){
						throw new Exception("Phut khong hop le");
					}
					ss=Integer.parseInt(sSS);
					if(ss>=60){
						throw new Exception("Giay khong hop le");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				timestamp=String2Timestamp(strInputDate, hh, mm, ss);
	        }
	        return timestamp;
	    }
	public static String Timestamp2YYYYMM(java.sql.Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(new java.util.Date(ts.getTime()));
            System.out.println("month="+calendar.MONTH);
            if (calendar.get(calendar.MONTH) + 1 < 10) {
                return  
                     calendar.get(calendar.YEAR)+"0"+(calendar.get(calendar.MONTH)+1);
            } else {
                return  calendar.get(calendar.YEAR) +
                    (calendar.get(calendar.MONTH)+1)+"";
            }
        }
    }
	 public static String Timestamp2DDMM(java.sql.Timestamp ts) {
	        if (ts == null) {
	            return "";
	        } else {
	            java.util.Calendar calendar = java.util.Calendar.getInstance();
	            calendar.setTime(new java.util.Date(ts.getTime()));

	            String strTemp = Integer.toString(calendar.get(calendar.
	                DAY_OF_MONTH));
	            if (calendar.get(calendar.DAY_OF_MONTH) < 10)
	                strTemp = "0" + strTemp;
	            if (calendar.get(calendar.MONTH) + 1 < 10) {
	                return strTemp + "/0" + (calendar.get(calendar.MONTH) + 1) ;
	            } else {
	                return strTemp + "/" + (calendar.get(calendar.MONTH) + 1) ;
	            }
	        }
	    }
	public static void main(String[] arg){
		Timestamp ts=MyDateProc.createTimestamp();
		Timestamp ts1=MyDateProc.minusToDate(ts,30);
		System.out.println(ts1.toString());
	}
	 public static java.sql.Timestamp String2Timestamp(String strInputDate) {
	        String strDate = strInputDate;
	        int i, nYear, nMonth, nDay;
	        String strSub = null;
	        i = strDate.indexOf("/");
	        if (i < 0)
	            return createTimestamp();
	        strSub = strDate.substring(0, i);
	        nDay = (new Integer(strSub.trim())).intValue();
	        strDate = strDate.substring(i + 1);
	        i = strDate.indexOf("/");
	        if (i < 0)
	            return createTimestamp();
	        strSub = strDate.substring(0, i);
	        nMonth = (new Integer(strSub.trim())).intValue() - 1; // Month begin from 0 value
	        strDate = strDate.substring(i + 1);
	        if (strDate.length() < 4) {
	            if (strDate.substring(0, 1).equals("9"))
	                strDate = "19" + strDate.trim();
	            else
	                strDate = "20" + strDate.trim();
	        }
	        nYear = (new Integer(strDate)).intValue();
	        java.util.Calendar calendar = java.util.Calendar.getInstance();
	        calendar.set(nYear, nMonth, nDay);
	        return new java.sql.Timestamp( (calendar.getTime()).getTime());
	    }
	
	 public static String Timestamp2MMDDYYYY(java.sql.Timestamp ts) {
	        if (ts == null) {
	            return "";
	        } else {
	            java.util.Calendar calendar = java.util.Calendar.getInstance();
	            calendar.setTime(new java.util.Date(ts.getTime()));
	            return (calendar.get(calendar.MONTH)+1)+"/"+calendar.get(calendar.DAY_OF_MONTH)+"/"+calendar.get(calendar.YEAR);
	           
	        }
	 }
	 public static String Timestamp2HHMMSS(java.sql.Timestamp ts) {
	        if (ts == null)
	            return "";

	        String sHour = "";
	        String sMinunute = "";
	        String sSecond = "";
	        String strTemp = "";

	        java.util.Calendar calendar = java.util.Calendar.getInstance();
	        calendar.setTime(new java.util.Date(ts.getTime()));
	        // HH
	        if (calendar.get(calendar.HOUR_OF_DAY) < 10)
	            sHour = "0" + Integer.toString(calendar.get(calendar.HOUR_OF_DAY));
	        else
	            sHour =  "" + Integer.toString(calendar.get(calendar.HOUR_OF_DAY));
	        //MM
	        if (calendar.get(calendar.MINUTE) < 10)
	            sMinunute = "0" + calendar.get(calendar.MINUTE);
	        else
	            sMinunute = "" + calendar.get(calendar.MINUTE);
	        //SS
	        if (calendar.get(calendar.SECOND) < 10)
	            sSecond = "0" + calendar.get(calendar.SECOND);
	        else
	            sSecond = "" + calendar.get(calendar.SECOND);

	        return (sHour +":"+ sMinunute +":"+ sSecond);
	    }
	 public static String Timestamp2DDMMYYYYHHMISS(java.sql.Timestamp ts) {
			String s1="";
			if (ts == null) {
	            return "";
	        } else {
	            DateTime t=new DateTime(ts);
	            s1=s1+(t.getDay()<10?"0"+t.getDay():t.getDay()+"")+"/";
	    		s1=s1+(t.getMonth()<10?"0"+t.getMonth():t.getMonth()+"")+"/";
	    		s1=s1+t.getYear()+" ";
	    		s1=s1+t.getHour()+":"+(t.getMinute()<10?("0"+t.getMinute()):t.getMinute()+"")+":"+(t.getSecond()<10?"0"+t.getSecond():t.getSecond()+"");
	    		
	        }return s1;
	   }
	 public static String Timestamp2DayOfWeek(java.sql.Timestamp ts) {
	        if (ts == null) {
	            return "";
	        } else {
	            java.util.Calendar calendar = java.util.Calendar.getInstance();
	            calendar.setTime(new java.util.Date(ts.getTime()));
	            String dayOfWeek=null;
	            int d = calendar.get(Calendar.DAY_OF_WEEK);
	            switch(d) {
	                case Calendar.MONDAY:
	                    dayOfWeek = "Thu hai";
	                    break;
	                case Calendar.TUESDAY:
	                    dayOfWeek = "Thu ba";
	                    break;
	                case Calendar.WEDNESDAY:
	                    dayOfWeek = "Thu tu";
	                    break;
	                case Calendar.THURSDAY:
	                    dayOfWeek = "Thu nam";
	                    break;
	                case Calendar.FRIDAY:
	                    dayOfWeek = "Thu sau";
	                    break;
	                case Calendar.SATURDAY:
	                    dayOfWeek = "Thu bay";
	                    break;
	                case Calendar.SUNDAY:
	                    dayOfWeek = "Chu nhat";
	                    break;
	                default:dayOfWeek = "";
	            }
	            return dayOfWeek;

	        }
	    }



}
