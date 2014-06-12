package ua.kharkiv.dereza.bookmaker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import ua.kharkiv.dereza.bookmaker.bean.BetBean;
import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.bean.HorseBean;
import ua.kharkiv.dereza.bookmaker.bean.TrackBean;
import ua.kharkiv.dereza.bookmaker.bean.TrialBean;
import ua.kharkiv.dereza.bookmaker.dao.ClientStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.CoverDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.DistanceDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.LocaleDAO;
import ua.kharkiv.dereza.bookmaker.dao.OwnerDAO;
import ua.kharkiv.dereza.bookmaker.dao.RoleDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientStatusDTO;
import ua.kharkiv.dereza.bookmaker.dto.CoverDTO;
import ua.kharkiv.dereza.bookmaker.dto.DistanceDTO;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.HorseStatusDTO;
import ua.kharkiv.dereza.bookmaker.dto.LocaleDTO;
import ua.kharkiv.dereza.bookmaker.dto.OwnerDTO;
import ua.kharkiv.dereza.bookmaker.dto.RoleDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrackDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrackTypeDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;

/**
 * Transforms dto to bean. Works with: 
 * - HorseDTO -> HorseBean (You sould use constructor (Object horseDTO, int trialId))
 * - HorseDTO -> HorseBean (without fields 'wincoefficient', 'place', 'status' constructor(Object horseDTO))
 * - ClientDTO-> ClientBean
 * - TrackDTO -> TrackBean
 * - TrialDTO -> TrialBean
 * - BetDTO   -> BetBean
 * 
 * @author Eduard
 * 
 */
public class BeanTranformer {
	
	private Object dto = null;
	private int id = 0;
	
	public BeanTranformer(Object dto) {
		this.dto = dto;
	}
	
	public BeanTranformer(Object dto, int id){
		this.dto = dto;
		this.id = id;
	}

	/**
	 * Main method that returns beans
	 * 
	 * @return (Object)bean
	 */
	public Object getBean() {
		Object bean = null;
		//log.trace("DTO for converting -> " + dto.toString());

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		// ClientDTO transformer
		if (dto instanceof ClientDTO) {
			ClientDTO clientDTO = (ClientDTO) dto;
			ClientBean clientBean = new ClientBean();

			clientBean.setId(clientDTO.getId());
			clientBean.setLogin(clientDTO.getLogin());
			clientBean.setName(clientDTO.getName());
			clientBean.setSurname(clientDTO.getSurname());
			clientBean.setEmail(clientDTO.getEmail());
			clientBean.setBalance(clientDTO.getBalance());

			// sets role
			RoleDAO roleDAO = mysqlFactory.getRoleDAO();
			RoleDTO roleDTO = roleDAO.findRoleById(clientDTO.getRoleId());
			clientBean.setRole(roleDTO.getName());

			// sets locale
			LocaleDAO localeDAO = mysqlFactory.getLocaleDAO();
			LocaleDTO localeDTO = localeDAO.findLocaleById(clientDTO
					.getLocaleId());
			clientBean.setLocale(localeDTO.getName());

			// sets client status
			ClientStatusDAO clientStatusDAO = mysqlFactory.getClientStatusDAO();
			ClientStatusDTO clientStatusDTO = clientStatusDAO
					.findClientStatusById(clientDTO.getClientStatusId());
			clientBean.setClientStatus(clientStatusDTO.getName());

			bean = clientBean;
		}
		
		// HorseDTO tranformer
		if (dto instanceof HorseDTO) {
			HorseDTO horseDTO = (HorseDTO) dto;
			int trialId = id;
			HorseBean horseBean = new HorseBean();

			horseBean.setId(horseDTO.getId());
			horseBean.setName(horseDTO.getName());
			horseBean.setColor(horseDTO.getColor());
			horseBean.setWeight(horseDTO.getWeight());

			// gets current date and figure out horse age
			Date date = new Date(System.currentTimeMillis());
			String fullDate = date.toString();
			int currentYear = Integer.parseInt(fullDate.substring(fullDate
					.length() - 4));
			int horseAge = currentYear - horseDTO.getBirthYear();
			horseBean.setAge(horseAge);

			// gets full name of owner
			OwnerDAO ownerDAO = mysqlFactory.getOwnerDAO();
			OwnerDTO ownerDTO = ownerDAO.findOwnerById(horseDTO.getOwnerId());
			String ownerFullName = ownerDTO.getName() + " "	+ ownerDTO.getSurname();
			horseBean.setOwnerName(ownerFullName);
			
			if(trialId != 0){
				// gets place
				TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
				TrialHorseDTO trialHorseDTO = trialHorseDAO.findTrialHorseByTrialIdHorseId(trialId, horseDTO.getId());
				horseBean.setPlace(trialHorseDTO.getPlace());
			
				// gets status
				int horseStatusId = trialHorseDTO.getHorseStatusId();
				//log.trace("horse status id -> " + horseStatusId);
				HorseStatusDAO horseStatusDAO = mysqlFactory.getHorseStatusDAO();
				HorseStatusDTO horseStatusDTO = horseStatusDAO.findHorseStatusById(horseStatusId);
				//log.trace("Found in db horseStatusDTO -> " + horseStatusDTO);
				horseBean.setStatus(horseStatusDTO.getName());
			
				// sets win coefficient
				horseBean.setWinCoefficient(trialHorseDTO.getWinCoefficient());
			}
			
			bean = horseBean;
		}
					
		// TrialDTO transformer
		if (dto instanceof TrialDTO) {
			TrialDTO trialDTO = (TrialDTO) dto;
			TrialBean trialBean = new TrialBean();

			trialBean.setId(trialDTO.getId());
			trialBean.setTrackId(trialDTO.getTrackId());
	
			// sets TrackBean
			TrackDAO trackDAO = mysqlFactory.getTrackDAO();
			TrackDTO trackDTO = trackDAO.findTrackById(trialDTO.getTrackId());
			BeanTranformer transformer = new BeanTranformer(trackDTO);
			TrackBean track = (TrackBean)transformer.getBean();
			trialBean.setTrackBean(track);
			
			// sets distance
			DistanceDAO distanceDAO = mysqlFactory.getDistanceDAO();
			DistanceDTO distanceDTO = distanceDAO.findDistanceById(trialDTO.getDistanceId());
			trialBean.setDistance(distanceDTO.getDistance());
			
			// sets start time
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = sdf.format(trialDTO.getStartTime());
			trialBean.setStartTime(startTime);
			
			// sets trial status
			TrialStatusDAO trialStatusDAO = mysqlFactory.getTrialStatusDAO();
			TrialStatusDTO trialStatusDTO = trialStatusDAO.findTrialStatusById(trialDTO.getTrialStatusId());
			trialBean.setTrialStatus(trialStatusDTO.getName());
			
			bean = trialBean;
		}
		
		// TrackDTO transformer
		if (dto instanceof TrackDTO) {
			TrackDTO trackDTO = (TrackDTO) dto;
			TrackBean trackBean = new TrackBean();
			
			trackBean.setId(trackDTO.getId());
			trackBean.setName(trackDTO.getName());
			trackBean.setCountry(trackDTO.getCountry());
			
			// sets cover
			CoverDAO coverDAO = mysqlFactory.getCoverDAO();
			CoverDTO coverDTO = coverDAO.findCoverById(trackDTO.getCoverId());
			trackBean.setCover(coverDTO.getName());
			
			// sets track type
			TrackTypeDAO trackTypeDAO = mysqlFactory.getTrackTypeDAO();
			TrackTypeDTO trackTypeDTO = trackTypeDAO.findTrackTypeById(trackDTO.getTrackTypeId());
			trackBean.setTrackType(trackTypeDTO.getName());
			
			bean = trackBean;
		}
		
		// BetDTO transformer
		if (dto instanceof BetDTO) {
			BetDTO betDTO = (BetDTO) dto;
			BetBean betBean = new BetBean();
			
			// sets id
			betBean.setId(betDTO.getId());
			
			// sets clientId
			betBean.setClientId(betDTO.getClientId());
			
			// sets horse
			TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
			TrialHorseDTO trialHorseDTO = trialHorseDAO.findTrialHorseById(betDTO.getTrialHoseId());
			
			HorseDAO horseDAO = mysqlFactory.getHorseDAO();
			HorseDTO horseDTO = horseDAO.findHorseById(trialHorseDTO.getHorseId());
			BeanTranformer bt = new BeanTranformer(horseDTO, trialHorseDTO.getTrialId());
			betBean.setHorse((HorseBean)bt.getBean());
			
			// sets trial
			TrialDAO trialDAO = mysqlFactory.getTrialDAO();
			TrialDTO trialDTO = trialDAO.findTrialById(trialHorseDTO.getTrialId());
			bt = new BeanTranformer(trialDTO);
			TrialBean trialBean = (TrialBean)bt.getBean();
			betBean.setTrial(trialBean);
			
			// sets value of bet
			betBean.setValue(betDTO.getValue());
			
			// sets trialHorseId
			betBean.setTrialHoseId(betDTO.getTrialHoseId());
			
			bean = betBean;
		}
		//log.trace("Tranformed bean -> " + bean.toString());
		return bean;
	}
}