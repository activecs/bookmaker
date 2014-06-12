package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;

/**
 * Forwards to page with finished and canceled trials(admin only) 
 * 
 * @author Eduard
 *
 */
public class TrialHistoryForwardCommand extends Command{

	private static final Logger log = Logger.getLogger(TrialHistoryForwardCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		
		req.setAttribute("title", "history");
		forward = Constants.PAGE_TRIAL_HISTORY;
		log.debug("Command finished");
		return forward;
	}

}
