package com.acerete.vo;

public class RankingElement implements Comparable<RankingElement> {

	private Integer uid;
	private Integer score;
	
	public RankingElement(Integer uid, Integer score) {
		this.uid = uid;
		this.score = score;
	}
	
	public Integer getUid() {
		return uid;
	}
	
	public Integer getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RankingElement [user=").append(uid)
				.append(", score=").append(score).append("]");
		return builder.toString();
	}
	
	@Override
	public int compareTo(RankingElement o) {
		if (score.intValue() == o.getScore().intValue()) {
			return uid.compareTo(o.getUid());
		}
		else {
			if (score.intValue() > o.getScore().intValue()) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
}
