package inet.db.fmcgame;

public class GameService {
	GameLogDAO logDAO=new GameLogDAO();
	GameDAO gameDAO=new GameDAO();
	public String getMaxTxId(String username,String password){
		String maxTxId=null;
		if(username.equals("inet")&&password.equals("inet2010")){
			maxTxId=gameDAO.getMaxTdId().toString();
		}
		return maxTxId;
	}
	public boolean logGame(String mobileNumber, String dnid, String gameCode,
            String code, String receiver, String smsRequest, String smsResponse, String serviceNumber,String mobileOperator,String username,String password){
		if(!username.equals("inet")||!password.equals("inet2010")){
			return false;
		}
		return logDAO.insertRow(mobileNumber, dnid, gameCode, code, receiver, smsRequest, smsResponse, serviceNumber, mobileOperator);
	}
}
