package com.example.transformers.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.BattleResult;
import com.example.model.Transformer;
import com.example.transformers.util.SortbyRank;

public class TransformerOperations {

	private static final Logger logger = LoggerFactory.getLogger(TransformerOperations.class);

	private List<Transformer> transformerRecords;
	private static TransformerOperations stdregd = null;

	private TransformerOperations() {
		transformerRecords = new ArrayList<Transformer>();
	}

	public static TransformerOperations getInstance() {
		if (stdregd == null) {
			stdregd = new TransformerOperations();
			return stdregd;
		} else {
			return stdregd;
		}
	}

	public void add(Transformer std) {
		transformerRecords.add(std);
	}

	public String updateTransformer(Transformer transformer) {
		for (int i = 0; i < transformerRecords.size(); i++) {
			Transformer tsfr = transformerRecords.get(i);
			if (tsfr.getTransformerName().equals(transformer.getTransformerName())) {
				transformer.setTransformerId(tsfr.getTransformerId());
				transformerRecords.set(i, transformer);// update with the new attributes
				return "Update successful";
			}
		}

		return "Update un-successful";
	}

	public String deleteTransformer(String transformerName) {
		for (int i = 0; i < transformerRecords.size(); i++) {
			Transformer tsfr = transformerRecords.get(i);
			if (tsfr.getTransformerName().equals(transformerName)) {
				transformerRecords.remove(i);// delete the transformer record
				return "Delete successful";
			}
		}

		return "Delete un-successful";
	}

	public BattleResult battleTransformer(Map<String, List<String>> battleList1) {

		List<String> battleList = battleList1.get("names");

		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();

		for (int i = 0; i < transformerRecords.size(); i++) {
			Transformer transformer = transformerRecords.get(i);
			for (int j = 0; j < battleList.size(); j++) {
				if (transformer.getTransformerName().equals(battleList.get(j))
						&& transformer.getTransformerType().equals("D")) {
					decepticons.add(transformer);

				} else if (transformer.getTransformerName().equals(battleList.get(j))
						&& transformer.getTransformerType().equals("A")) {
					autobots.add(transformer);

				}
			}
		}

		// Sorting the decepticon and autobot list based on rank
		Collections.sort(decepticons, new SortbyRank());
		Collections.sort(autobots, new SortbyRank());

		// Displaying the List of Decepticon and Autobot ID's
		for (int i = 0; i < decepticons.size(); i++)
			logger.info(decepticons.get(i).getTransformerId() + "::" + decepticons.get(i).getTransformerType());

		for (int i = 0; i < autobots.size(); i++)
			logger.info(autobots.get(i).getTransformerId() + "::" + autobots.get(i).getTransformerType());

		/* Decide the winner based on the overall rating and other criteria */

		BattleResult battleResult = decideWinner(decepticons, autobots);

		return battleResult;

	}

	public BattleResult decideWinner(List<Transformer> decepticons, List<Transformer> autobots) {

		int numberOfBattles = 0;
		Transformer decepticon, autobot = null;
		List<String> winners = new ArrayList<String>();
		List<String> survivors = new ArrayList<String>();
		List<String> winningTeam = new ArrayList<String>();
		int decepticonRating, decepticonWins = 0;
		int autobotRating, autobotWins = 0;
		String finalWinningTeam = null;
		boolean gameDestroyed = false;
		boolean decepticonSurvivor = false;
		boolean autobotSurvivor = false;
		int survivorCount = decepticons.size() - autobots.size();

		if (survivorCount > 0) {
			decepticonSurvivor = true;
		}

		if (survivorCount < 0) {
			survivorCount = autobots.size() - decepticons.size();
			autobotSurvivor = true;
		}

		for (int i = 0; i < decepticons.size() && i < autobots.size(); i++) {
			decepticon = decepticons.get(i);
			autobot = autobots.get(i);

			// Check if Optimus Prime and Predaking are competing against one another,
			// If so, exit the battle
			if ((decepticon.getTransformerName().equalsIgnoreCase("Optimus Prime")
					|| decepticon.getTransformerName().equalsIgnoreCase("Predaking"))
					&& (autobot.getTransformerName().equalsIgnoreCase("Optimus Prime")
							|| autobot.getTransformerName().equalsIgnoreCase("Predaking"))) {
				winners.add("Tie");
				winningTeam.add("NONE");
				numberOfBattles++;
				gameDestroyed = true;
				break;
			}

			/*
			 * Check whether the autobot or decepticon is either of Optimus Prime or
			 * Predaking. If so, make him the winner without checking any other criteria
			 */
			if (decepticon.getTransformerName().equalsIgnoreCase("Optimus Prime")
					|| decepticon.getTransformerName().equalsIgnoreCase("Predaking")) {
				winners.add(decepticon.getTransformerName());
				winningTeam.add("Decepticons");
				numberOfBattles++;
				decepticonWins++;
				continue;

			} else if (autobot.getTransformerName().equalsIgnoreCase("Optimus Prime")
					|| autobot.getTransformerName().equalsIgnoreCase("Predaking")) {
				winners.add(autobot.getTransformerName());
				winningTeam.add("Autobots");
				numberOfBattles++;
				autobotWins++;
				continue;
			}

			/*
			 * If the (decepticons - autobots) courage difference >=4 and strength
			 * difference is >=3 or (decepticon-autobots) skill difference is >=3 then
			 * decepticon win
			 */
			if ((decepticon.getCourage() - autobot.getCourage() >= 4)
					&& (decepticon.getStrength() - autobot.getStrength() >= 3)
					|| (decepticon.getSkill() - autobot.getSkill() >= 3)) {
				winners.add(decepticon.getTransformerName());
				winningTeam.add("Decepticons");
				numberOfBattles++;
				decepticonWins++;
				continue;

			}
			/*
			 * If the (autobot - decepticon) courage difference >=4 and strength difference
			 * is >=3 or (autobot-decepticon) skill difference is >=3 then autobot win
			 */
			else if ((autobot.getCourage() - decepticon.getCourage() >= 4)
					&& (autobot.getStrength() - decepticon.getStrength() >= 3)
					|| (autobot.getSkill() - decepticon.getSkill() >= 3)) {
				winners.add(autobot.getTransformerName());
				winningTeam.add("Autobots");
				numberOfBattles++;
				autobotWins++;
				continue;
			}

			// Overall rating of the decepticon
			decepticonRating = decepticon.getStrength() + decepticon.getIntelligence() + decepticon.getSpeed()
					+ decepticon.getEndurance() + decepticon.getFirepower();

			// Overall rating of the autobot
			autobotRating = autobot.getStrength() + autobot.getIntelligence() + autobot.getSpeed()
					+ autobot.getEndurance() + autobot.getFirepower();

			// If decepticon overall rating > Autobot rating, then Decepticon is the winner
			if (decepticonRating > autobotRating) {
				winners.add(decepticon.getTransformerName());
				winningTeam.add("Decepticons");
				numberOfBattles++;
				decepticonWins++;
				continue;
			}

			// Else If Autobot overall rating > Decepticon rating, then Autobot is the
			// winner
			else if (autobotRating > decepticonRating) {
				winners.add(autobot.getTransformerName());
				winningTeam.add("Autobots");
				numberOfBattles++;
				autobotWins++;
				continue;
			}
			// Else, Autobot overall rating = Decepticon rating, then it is a tie
			else if (autobotRating == decepticonRating) {
				winners.add("Tie");
				winningTeam.add("NONE");
				numberOfBattles++;
				continue;
			}
		}

		// Deciding the final winning Team based on the total number of wins

		if (decepticonWins > autobotWins) {
			finalWinningTeam = "Decepticons";

		} else if (autobotWins > decepticonWins) {
			finalWinningTeam = "Autobots";

		} else if (autobotWins == decepticonWins) {
			finalWinningTeam = "NONE";
		}

		for (int i = 0; i < winners.size(); i++) {
			logger.info("Winners are:" + winners.get(i));
		}
		
		

		// Finding the number of survivors in the battle
		if (!gameDestroyed && decepticonSurvivor) {
			logger.info("The Survivors are::");
			for (int i = autobots.size(); i < decepticons.size(); i++) {
				logger.info(decepticons.get(i).getTransformerName());
				survivors.add(decepticons.get(i).getTransformerName());
			}

		}
		if (!gameDestroyed && autobotSurvivor) {
			logger.info("The Survivors are::");
			for (int i = decepticons.size(); i < autobots.size(); i++) {
				logger.info(autobots.get(i).getTransformerName());
				survivors.add(autobots.get(i).getTransformerName());
			}

		}
		
		BattleResult battleResult = new BattleResult();
		battleResult.setNumberOfBattles(numberOfBattles);
		battleResult.setWinningTeam(finalWinningTeam);
		battleResult.setWinners(winners);
		battleResult.setSurvivors(survivors);

		logger.info("Total No. of Battles::" + numberOfBattles);
		logger.info("Final Winning Team is::" + finalWinningTeam);

		return battleResult;

	}

	public List<Transformer> getTransformerRecords() {
		return transformerRecords;
	}

}
