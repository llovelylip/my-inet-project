package inet.db.ichipgame;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GameLog {
	private BigDecimal id;
	private String result;
	private String userId;
	private String serviceId;
	private String commandCode;
	private String messageContent;
	private BigDecimal requestId;
	private Timestamp timeSend;
	public String getCommandCode() {
		return commandCode;
	}
	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Timestamp getTimeSend() {
		return timeSend;
	}
	public void setTimeSend(Timestamp timeSend) {
		this.timeSend = timeSend;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
