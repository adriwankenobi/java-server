package com.acerete.vo;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Ranking {

	private SortedSet<RankingElement> data;
	private Object lock;
	
	public Ranking() {
		this.data = new TreeSet<RankingElement>();
		this.lock = new Object();
	}
	
	public void addOrReplace(RankingElement element) {
		boolean needsToBeAdded = true;
		for (RankingElement existing : data) {
			if (existing.getUid().intValue() == element.getUid().intValue()) {
				needsToBeAdded = false;
				if (existing.getScore().intValue() < element.getScore().intValue()) {
					data.remove(existing);
					data.add(element);
				}
			}
		}
		if (needsToBeAdded) {
			data.add(element);
		}
	}
	
	public SortedSet<RankingElement> getHighest(int numberOfElements) {
		if (data.size() < numberOfElements) {
			return data;
		}
		else {
			SortedSet<RankingElement> highest = new TreeSet<RankingElement>();
			Iterator<RankingElement> iterator = data.iterator();
			for (int i=0; i<numberOfElements; i++) {
				highest.add(iterator.next());
			}
			return highest;
		}
	}
	
	public Object getLock() {
		return lock;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ranking [").append(data).append("]");
		return builder.toString();
	}
}
