package ua.kharkiv.dereza.bookmaker.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

	private static Logger log = Logger.getLogger(SecurityFilter.class);

	public SecurityFilter() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//log.debug("Security filter started");
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;

		// get session
		HttpSession session = httpServletRequest.getSession(false);
		// gets ClientBean from session
		ClientBean clientBean = null;
		if (session != null) {
			clientBean = (ClientBean) session.getAttribute("clientbean");
		}

		// get query string
		String URI = httpServletRequest.getRequestURI();
		//log.trace("Query string --> " + URI);

		// get command
		String command = req.getParameter("command");
		
		// sets command to null, if we got parameter "command" but it is empty
		if(command != null){
			if(command.length() == 0){
				command = null;
			}
		}
		
		// we always grant access to common resource
		if (URI.startsWith(Constants.RESOURCES)) {
			//log.trace("Access to common recources permitted");
			//log.debug("Security filter finished");
			chain.doFilter(req, res);
			return;
		}

		if (clientBean == null && command == null) {
			req.setAttribute("title", "login");
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(Constants.PAGE_LOGIN);
			dispatcher.forward(req, res);
			//log.debug("Forward to login page");
			//log.debug("Security filter finished");
			return;
		}
		
		if (clientBean != null && command == null) {
			req.setAttribute("title", "trials");
			String forward = null;
			if(clientBean.getRole().equals(Constants.ROLE_ADMIN)) forward = Constants.PAGE_ADMIN_TRIALS;
			if(clientBean.getRole().equals(Constants.ROLE_CLIENT)) forward = Constants.PAGE_CLIENT_TRIALS;
			RequestDispatcher dispatcher = req.getRequestDispatcher(forward);
			dispatcher.forward(req, res);
			//log.debug("Forward to login page");
			//log.debug("Security filter finished");
			return;
		}
		
		// we always allow login and registration command
		//log.trace("Command -> " + command);
		if (Constants.COMMAND_LOGIN.equals(command)
				|| Constants.COMMAND_REGISTRATION.equals(command)
				|| Constants.COMMAND_ACCOUNT_ACTIVATION.equals(command)) {
			//log.trace("Allow to -> " + command);
			//log.debug("Security filter finished");
			chain.doFilter(req, res);
			return;
		}
		
		if(clientBean == null){
			req.setAttribute("title", "login");
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(Constants.PAGE_LOGIN);
			dispatcher.forward(req, res);
			log.debug("Forward to login page");
			log.debug("Security filter finished");
			return;
		}
		
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
