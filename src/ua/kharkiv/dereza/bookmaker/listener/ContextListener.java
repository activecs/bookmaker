package ua.kharkiv.dereza.bookmaker.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.business.TrialObserver;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	
	private static Logger log = Logger.getLogger(ContextListener.class);
	
    /**
     * Default constructor. 
     */
    public ContextListener() {
    }

    public void contextInitialized(ServletContextEvent arg0) {
    	log.info("Application started");
    	
    	// runs daemon
    	new TrialObserver();
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    	log.info("Application shutted down");
    }
	
}
