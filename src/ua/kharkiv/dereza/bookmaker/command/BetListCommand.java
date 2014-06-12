package ua.kharkiv.dereza.bookmaker.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.BetBean;
import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * List of all bets.
 * 
 * @author Eduard
 *
 */
public class BetListCommand extends Command {

	private static final Logger log = Logger.getLogger(BetListCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		
		ClientBean clientBean = (ClientBean) req.getSession(false).getAttribute("clientbean");
		int clientId = clientBean.getId();
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		BetDAO betDAO = mysqlFactory.getBetDAO();
		List<BetDTO> betDTOList= betDAO.findBetsByClientId(clientId);
		log.trace("Found in db client's bets (clientId="+ clientId + ")->" + betDTOList);
		List<BetBean> betBeanList = new LinkedList<BetBean>();
		BeanTranformer bt = null;
		for(BetDTO dto: betDTOList){
			log.trace("Give betDTO ->" + dto);
			bt = new BeanTranformer(dto);
			BetBean betBean = (BetBean)bt.getBean(); 
			betBeanList.add(betBean);
			log.trace("Get betDTO ->" + betBean);
		}
		log.trace("Bets ->" + betBeanList);
		// sorts bets
		Collections.sort(betBeanList);
		
		req.setAttribute("title", "Bets");
		req.setAttribute("bets", betBeanList);
		forward = Constants.PAGE_BETS;
		
		log.debug("Command finished");
		return forward;
	}

}
