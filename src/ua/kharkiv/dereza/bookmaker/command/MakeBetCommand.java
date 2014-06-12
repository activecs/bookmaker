package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Allow clients make bets.
 * 
 * @author Eduard
 * 
 */
public class MakeBetCommand extends Command {

	private static final Logger log = Logger.getLogger(MakeBetCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("application/json");
		log.debug("Command started");
		String forward = null;
		String message = null;

		int trialId = Integer.parseInt(req.getParameter("trialId"));
		int horseId = Integer.parseInt(req.getParameter("horseId"));
		BigDecimal sum = new BigDecimal(req.getParameter("sum"));
		ClientBean clientBean = (ClientBean) req.getSession().getAttribute("clientbean");
		
		log.debug("Client with id->" + clientBean.getId()
				+ ", want to make bet on trialid->" + trialId + ", horseId->"
				+ horseId + ", sum->" + sum);

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		ClientDAO clientDAO = mysqlFactory.getClientDAO();

		// check balance
		ClientDTO clientDTO = clientDAO.findClientByLogin(clientBean.getLogin());
		BigDecimal balance = clientDTO.getBalance();
		if (req.getParameter("sum") == "" || sum.compareTo(new BigDecimal(1)) == -1){
			message = "too low bet";
		} else if (balance.compareTo(sum) == -1) {
			message = "not enough money";
		} else if (sum.compareTo(new BigDecimal("1000")) == 1) {
			message = "you can't make so high bet";
		} else {
			TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
			TrialHorseDTO trialHorseDTO = trialHorseDAO
					.findTrialHorseByTrialIdHorseId(trialId, horseId);
			BetDAO betDAO = mysqlFactory.getBetDAO();
			BetDTO betDTO = new BetDTO();
			betDTO.setClientId(clientBean.getId());
			betDTO.setTrialHoseId(trialHorseDTO.getId());
			betDTO.setValue(sum);
			betDAO.createBet(betDTO);

			log.info("Client with id->" + clientBean.getId()
					+ ", made bet on trialid->" + trialId + ", horseId->"
					+ horseId + ", sum->" + sum);
			
			// updates clientBean and info in db
			clientDTO.setBalance(clientBean.getBalance().subtract(sum));
			clientDAO.updateClient(clientDTO);
			BeanTranformer tr = new BeanTranformer(clientDTO);
			ClientBean updatedBean = (ClientBean)tr.getBean();
			req.getSession(false).setAttribute("clientbean", updatedBean);
			
			message = "your bet is confirmed";
		}

		// create instance of json converter
		Gson gson = new Gson();
		String json = gson.toJson(message);
		try {
			res.getWriter().write(json);
		} catch (IOException ex) {
			message = "Can't write json to response";
			log.error(message, ex);
		}

		log.debug("Command finished");
		return forward;
	}

}