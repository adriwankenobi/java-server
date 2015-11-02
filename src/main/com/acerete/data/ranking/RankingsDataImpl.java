package com.acerete.data.ranking;

import java.util.concurrent.ConcurrentHashMap;

import com.acerete.Configuration;
import com.acerete.exceptions.CannotBeExecutedException;
import com.acerete.vo.Ranking;

public class RankingsDataImpl implements RankingsData {

	// ERROR strings
	private final static String ERROR_NO_UNIT_TEST = "This method cannot be executed outside unit tests";
		
	// Thread safe lazy initialization singleton
	private static class LazyHolder {
		private static final RankingsDataImpl INSTANCE = new RankingsDataImpl();
	}
		
	public static RankingsDataImpl getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private ConcurrentHashMap<Integer, Ranking> data;
		
	private RankingsDataImpl() {
		this.data = new ConcurrentHashMap<Integer, Ranking>();
	}
	
	@Override
	public void insertOrUpdateRanking(Integer level, Ranking ranking) {
		data.put(level, ranking);
	}
	
	@Override
	public Ranking getRankingByLevel(Integer level) {
		return data.get(level);
	}
	
	public void clearAllRankings() {
		if (Configuration.getInstance().isUnitTest()) {
			for (Integer level : data.keySet()) {
				data.remove(level);
			}
		}
		else {
			throw new CannotBeExecutedException(ERROR_NO_UNIT_TEST);
		}
	}

}
