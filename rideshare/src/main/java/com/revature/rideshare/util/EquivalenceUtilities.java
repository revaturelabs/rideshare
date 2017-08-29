package com.revature.rideshare.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.rideshare.json.Option;

public class EquivalenceUtilities {

	/**
	 * Safely compares two strings that may or may not be null.
	 * 
	 * @param A
	 * @param B
	 * @return True if both strings are null, false if one string is null,
	 *         otherwise returns A.equals(B)
	 */
	public static boolean SafeCompareStrings(String A, String B) {
		return SafeCompare(A, B);
	}

	/**
	 * Safely compares two options that may or may not be null.
	 * 
	 * @param A
	 * @param B
	 * @return True if both options are null, false if one option is null,
	 *         otherwise returns A.equals(B)
	 */
	public static boolean SafeCompareOptions(Option A, Option B) {
		return SafeCompare(A, B);
	}

	/**
	 * Safely compares two objects that may or may not be null.
	 * 
	 * @param A
	 * @param B
	 * @return True if both objects are null, false if one object is null,
	 *         otherwise returns A.equals(B)
	 */
	public static <T> boolean SafeCompare(T A, T B) {
		if (A == null && B == null) {
			return true;
		} else if (A == null || B == null) {
			return false;
		}
		return A.equals(B);
	}

	/**
	 * 
	 * Safely compares two lists that may or may not be null.
	 * 
	 * Checks if both lists are the same size, then creates a hash code from
	 * each using ListHash<T>. If both hash codes are the same, the lists are
	 * considered to be equal.
	 * 
	 * Basically free if both lists are of a different size, but must process every item in each list if both lists are of equal size.
	 * 
	 * @param A
	 * @param B
	 * @return True if both lists contain all the same elements as decided by
	 *         Object.hashCode OR if both lists are null. Else false;
	 */
	public static <T> boolean SafeCompare(List<T> A, List<T> B) {

		if (A == null && B == null) {
			return true;
		} else if (A == null || B == null) {
			return false;
		} else if (A.size() != B.size()) {
			return false;
		}

		if (ListHash(A) == ListHash(B)) {
			return true;
		}
		return false;

	}

	/**
	 * Compounds the hashCode of every object in the list.
	 * 
	 * @param list
	 * @return compound hashCode for the list.
	 */

	public static <T> int ListHash(List<T> list) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			stringBuilder.append(list.get(i).hashCode());
		}

		return stringBuilder.toString().hashCode();

	}

}
