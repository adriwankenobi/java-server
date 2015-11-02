package com.acerete.utils;

import java.util.Iterator;
import java.util.SortedSet;

import com.acerete.vo.RankingElement;

public final class SetUtils {

	/**
	 * Conversion method
	 * @param set
	 * @return string with format "key1=value1,ke2=value2"
	 */
	public static String getSetAsString(SortedSet<RankingElement> set) {
		StringBuilder sb = new StringBuilder();
		if (set != null && !set.isEmpty()) {
			Iterator<RankingElement> iterator = set.iterator();
			while (iterator.hasNext()) {
				RankingElement element = iterator.next();
				sb.append(element.getUid()).append("=").append(element.getScore()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
