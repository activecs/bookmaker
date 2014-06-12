package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;

/**
 * Log out command.
 * 
 * @author Eduard
 * 
 */
public class LogoutCommand extends Command {

	private static final Logger log = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command starts");
		HttpSession session = req.getSession(false);
		ClientBean clientBean = null;
		
		if(session != null){
		clientBean = (ClientBean) session.getAttribute("clientbean");
		}
		
		if (clientBean != null) {
			String client = clientBean.getLogin();
			log.info("Logged out client --> " + client);
			session.invalidate();
		}
		
		req.setAttribute("title", "login");
		
		log.debug("Command finished");
		String forward = Constants.PAGE_LOGIN;
		return forward;
	}

}
