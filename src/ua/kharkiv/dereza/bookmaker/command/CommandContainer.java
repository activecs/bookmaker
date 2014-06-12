package ua.kharkiv.dereza.bookmaker.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Contains all commands.
 * 
 * @author Eduard
 *
 */
public class CommandContainer {
	private static final Logger log = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new HashMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("trialList", new TrialListCommand());
		commands.put("horseList", new HorseListCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("settingsForward", new SettingsForwardCommand());
		commands.put("saveSettings", new SaveSettingsCommand());
		
		// client's commands
		commands.put("registration", new RegistrationCommand());
		commands.put("activation", new AccountActivationCommand());
		commands.put("makeBet", new MakeBetCommand());
		commands.put("betList", new BetListCommand());
		
		// admin's commands
		commands.put("addTrialForward", new AddTrialForwardCommand());
		commands.put("editTrialForward", new EditTrialForwardCommand());
		commands.put("deleteTrial", new DeleteTrialCommand());
		commands.put("saveTrial", new SaveTrialCommand());
		commands.put("addHorseTrial", new AddTrialHorseForwardCommand());
		commands.put("editTrialHorse", new EditTrialHorseForwardCommand());
		commands.put("saveTrialHorse", new SaveTrialHorseCommand());
		commands.put("deleteTrialHorse", new DeleteTrialHorseCommand());
		commands.put("clientListForward", new ClientListForwardCommand());
		commands.put("unbanClient", new UnbanClientCommand());
		commands.put("banClient", new BanClientCommand());
		commands.put("deleteClient", new DeleteClientCommand());
		commands.put("trialHistoryForward", new TrialHistoryForwardCommand());
		commands.put("trialHistoryList", new TrialHistoryListCommand());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}
		return commands.get(commandName);
	}
}
