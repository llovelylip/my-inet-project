package inet.util;
import java.util.Hashtable;
public class GetContent {
	 public static ReaderWeb readerWeb = new ReaderWeb();
	 private static final long TK_TIMEOUT_DEFAULT = 60*1000*30;//30 minutes
     public static boolean isTimeout(String url) {
    	Long loadTime=null;
    	try {
    		loadTime=(Long)loadTimeTables.get(url);
		} catch (Exception e) {
			System.out.println("Chua khoi tao lan nao==>loadTime=0");
		}
		if(loadTime==null)loadTime=new Long("0");
        long currTime = System.currentTimeMillis();
        if ( (currTime - loadTime.longValue()) > TK_TIMEOUT_DEFAULT) {
            return true;
        }
        return false;
     }
     private static Hashtable infoTables=new Hashtable();
     private static Hashtable loadTimeTables=new Hashtable();
     private static String getInfofromBuffer(String url){
    	return (String)infoTables.get(url);
     } 
     public static String getInfo(String url) {
    	String info=null;
    	info=getInfofromBuffer(url);
    	if (!isTimeout(url) && info != null) return info;
    	//info=lay noi dung tren web
    	info=getContentFromUrl(url);
        loadTimeTables.put(url,new Long(System.currentTimeMillis()+""));
        infoTables.put(url, info);
         return 	info;
     }
	 private static String getContentFromUrl(String url){
		 String domain=null;
		 String uri=null;
		 if(url==null||url.equals("")){
			 return null;
		 }
		 url=url.replaceFirst("http://", "");
		 if(url.indexOf("/")<0){
			 domain=url;
			 uri="/";
		 }else{
			 domain=url.substring(0,url.indexOf("/"));
			 uri=url.substring(url.indexOf("/"));
		 }
		
		 readerWeb.doGet(domain, uri, 80);
	     byte[] b=readerWeb.getContent();
	     String content=null;
	     try {
	    	 content=new String(b,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return content;
	 }
	 public static void main(String[] arg){
		 System.out.println(GetContent.getInfo("http://sms.vn/include/footer.jsp"));
	 }
}
