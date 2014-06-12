package ua.kharkiv.dereza.bookmaker.util;

import java.util.Comparator;

import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Contains comparators
 * 
 * @author Eduard
 *
 */
public class Sorter {
	// //////////////////////////////////////////////////////////
	// these are comparators
	// //////////////////////////////////////////////////////////
	
	public static final Comparator<TrialHorseDTO> sortTrialHorseByWinCoefficient = new Comparator<TrialHorseDTO>() {
		@Override
		public int compare(TrialHorseDTO o1, TrialHorseDTO o2) {
			return o1.getWinCoefficient().compareTo(o2.getWinCoefficient());
		}
		
	};

}
