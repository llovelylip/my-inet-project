/*******************************************************************************
 * File name: Sniffer.java
 * @author Nguyen Thien Phuong 
 * Copyright (c) 2007 iNET Media Co .Ltd 
 * Created on Jun 12, 2007 3:47:57 PM
 ******************************************************************************/

package inet.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Sniffer implements HttpSessionListener {
	private static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent se) {
		activeSessions++;
	}
 
	public void sessionDestroyed(HttpSessionEvent se) {
		if(activeSessions > 0)
		activeSessions--;
	}
 
	public static int getActiveSessions() {
	    if (activeSessions == 0) activeSessions = 1;
		return activeSessions;
	}
}

