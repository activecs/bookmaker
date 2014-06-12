package ua.kharkiv.dereza.bookmaker.core;

import java.math.BigDecimal;

/**
 * Contains common constants
 * 
 * @author Eduard
 * 
 */
public interface Constants {
	
	// Bookmaker margin
	public static final BigDecimal MARGIN = new BigDecimal(0.15);
	
	// application name
	public static final String APPLICATION_NAME = "/";
	public static final String APPLICATION_HOST = "http://localhost:8080";
	public static final String APPLICATION_CONTROLLER = "controller";

	// email settings
	public static final String MAIL_SMTP_HOST = "smtp.gmail.com";
	public static final String MAIL_SMTP_SOCKETFACTORY_PORT = "465";
	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
	public static final String MAIL_SMTP_AUTH = "true";
	public static final String MAIL_SMTP_PORT = "465";
	public static final String MAIL_ADRESS = "payment.systems24@gmail.com";
	public static final String MAIL_USERNAME = "payment.systems24";
	public static final String MAIL_PASSWORD = "EDUARD_activex27";
	public static final String MAIL_ADMIN = "activecs@mail.ru";

	// velocity
	public static final String VELOCITY_TEMPLATES_PATH = "/WEB-INF/templates/templates.jar";
	public static final String VELOCITY_TEMPLATE_ACCOUNT_ACTIVATION = "AccountActivation.vm";

	// resources
	public static final String RESOURCES = APPLICATION_NAME + "resources/";

	// pages
	public static final String PAGE_ADMIN_TRIALS = "/WEB-INF/jsp/admin/adminTrials.jsp";
	public static final String PAGE_CLIENT_TRIALS = "/WEB-INF/jsp/client/clientTrials.jsp";
	public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String PAGE_ADD_TRIAL = "/WEB-INF/jsp/admin/addTrial.jsp";
	public static final String PAGE_EDIT_TRIAL = "/WEB-INF/jsp/admin/editTrial.jsp";
	public static final String PAGE_ADD_TRIAL_HORSE = "/WEB-INF/jsp/admin/addTrialHorse.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/errorPage.jsp";
	public static final String PAGE_EDIT_TRIAL_HORSE = "/WEB-INF/jsp/admin/editTrialHorse.jsp";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE_BETS ="/WEB-INF/jsp/client/bets.jsp";
	public static final String PAGE_CLIENTS = "/WEB-INF/jsp/admin/clients.jsp";
	public static final String PAGE_TRIAL_HISTORY = "/WEB-INF/jsp/admin/trialHistory.jsp";
	
	// commands
	public static final String COMMAND_LOGIN = "login";
	public static final String COMMAND_REGISTRATION = "registration";
	public static final String COMMAND_ACCOUNT_ACTIVATION = "activation";

	// error code
	public static final int ERROR_CODE_ALL_IS_OK = 0;
	public static final int ERROR_CODE_CONTAIN_ERROR = 1;

	// roles
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_CLIENT = "client";

	// I18nTag default locale
	public static final String LOCALE_DEFAULT = "en_US";
	public static final String LOCALE_RU = "ru_RU";
	public static final String LOCALE_EN = "en_US";
	
	// client's statuses
	public static final String CLIENT_STATUSES_ACTIVE = "active";
	public static final String CLIENT_STATUSES_NOT_ACTIVE = "not active";
	public static final String CLIENT_STATUSES_BANNED = "banned";
	
	// trial's statuses
	public static final String TRIAL_STATUSES_IDLE = "idle";
	public static final String TRIAL_STATUSES_CANCELED = "canceled";
	public static final String TRIAL_STATUSES_FINISHED = "finished";
	
	// horse's statuses
	public static final String HORSE_STATUSES_ACTIVE = "active";
	public static final String HORSE_STATUSES_DNF = "dnf";
}
