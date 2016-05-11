package com.footballschedule2016.logify;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TeamColors {

	private Context context;
	private List<String> teamcolors = new ArrayList<>();

	public TeamColors(Context context) {
		this.context = context;
		setUpColors();

	}

	private void setUpColors() {

		// add all colors from string XML
		teamcolors.add(context.getString(R.string.Cards));
		teamcolors.add(context.getString(R.string.Falcons));
		teamcolors.add(context.getString(R.string.Ravens));
		teamcolors.add(context.getString(R.string.Bills));
		teamcolors.add(context.getString(R.string.Panthers));
		teamcolors.add(context.getString(R.string.Bears));
		teamcolors.add(context.getString(R.string.Bengals));
		teamcolors.add(context.getString(R.string.Browns));
		teamcolors.add(context.getString(R.string.Cowboys));
		teamcolors.add(context.getString(R.string.Broncos));
		teamcolors.add(context.getString(R.string.Lions));
		teamcolors.add(context.getString(R.string.Packers));
		teamcolors.add(context.getString(R.string.Texans));
		teamcolors.add(context.getString(R.string.Colts));
		teamcolors.add(context.getString(R.string.Jags));
		teamcolors.add(context.getString(R.string.Chiefs));
		teamcolors.add(context.getString(R.string.Rams));
		teamcolors.add(context.getString(R.string.Dolphins));
		teamcolors.add(context.getString(R.string.Vikings));
		teamcolors.add(context.getString(R.string.Patriots));
		teamcolors.add(context.getString(R.string.Saints));
		teamcolors.add(context.getString(R.string.Giants));
		teamcolors.add(context.getString(R.string.Jets));
		teamcolors.add(context.getString(R.string.Raiders));
		teamcolors.add(context.getString(R.string.Eagles));
		teamcolors.add(context.getString(R.string.Steelers));
		teamcolors.add(context.getString(R.string.Chargers));
		teamcolors.add(context.getString(R.string.fourniners));
		teamcolors.add(context.getString(R.string.Seahawks));
		teamcolors.add(context.getString(R.string.Buccaneers));
		teamcolors.add(context.getString(R.string.Titans));
		teamcolors.add(context.getString(R.string.RedSkins));
	}

	/**
	 * @return the teamcolors
	 */
	public List<String> getTeamcolors() {
		return teamcolors;
	}

	/**
	 * @param teamcolors
	 *            the teamcolors to set
	 */
	public void setTeamcolors(List<String> teamcolors) {
		this.teamcolors = teamcolors;
	}

}
