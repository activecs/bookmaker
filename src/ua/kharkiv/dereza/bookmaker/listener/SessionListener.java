package ua.kharkiv.dereza.bookmaker.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	
	private static final Logger log = Logger.getLogger(SessionListener.class);
	
    /**
     * Default constructor. 
     */
    public SessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	HttpSession session = sessionEvent.getSession();
    	log.info("Session with id = " + session.getId() + " started.");
    	session.setMaxInactiveInterval(600);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	HttpSession session = sessionEvent.getSession();
    	log.info("Session with id = " + session.getId() + " ended.");
    }
	
}
