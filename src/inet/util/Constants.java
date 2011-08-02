package inet.util;


/**
 * @author cuonghh
 *
 */
public class Constants {
	public final static int CREATE_COMPONENT_SUCCESS = 0;
	public final static int CREATE_COMPONENT_ALREADY_EXIST = 1;
	public final static int CREATE_COMPONENT_FAILED = 2;
	
	public final static int STATUS_POSTER_EDITING = 1;
	public final static int STATUS_POSTER_POSTED = 2;
	public final static int STATUS_REVISER_VIEWING = 3;
	public final static int STATUS_POSTER_RETURNED = 4;
	public final static int STATUS_REVISER_CONFIRMED = 5;
	public final static int STATUS_PUBLISHER_VIEWING = 6;
	public final static int STATUS_REVISER_RETURNED = 7;
	public final static int STATUS_PUBLISHER_CONFIRMED = 8;
	
	public final static String IMAGE_URL_PATH = "/client/images/";
	public final static String BLANK_CHARACTER = "";
	public final static String MESSAGE_BUNDLE = "MessagesBundle";
	
	public static String getStatusString(int status){
		String s = "";
		switch(status){
			case STATUS_POSTER_EDITING:
				s = "?ang so?n th?o";
				break;
			case STATUS_POSTER_POSTED:
				s = "?ã chuy?n lên reviser";
				break;
			case STATUS_REVISER_VIEWING:
				s = "Reviser ?ang xét duy?t";
				break;
			case STATUS_POSTER_RETURNED:
				s = "Reviser tr? l?i poster";
				break;
			case STATUS_REVISER_CONFIRMED:
				s = "Reviser ?ã duy?t";
				break;
			case STATUS_PUBLISHER_VIEWING:
				s = "Publisher ?ang xét duy?t";
				break;
			case STATUS_REVISER_RETURNED:
				s = "Publisher tr? l?i reviser";
				break;
			case STATUS_PUBLISHER_CONFIRMED:
				s = "Publisher ?ã duy?t";
				break;
		}
		return s;
	}
}
