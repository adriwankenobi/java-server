package com.acerete.data.ranking;

import com.acerete.vo.Ranking;

public interface RankingsData {

	/**
	 * Inserts or updates the ranking for that level
	 * @param level
	 * @param ranking
	 */
	public void insertOrUpdateRanking(Integer level, Ranking ranking);
	
	/**
	 * Gets the ranking for that level. Returns null if it doesn't exist
	 * @param level
	 * @return
	 */
	public Ranking getRankingByLevel(Integer level);
}
