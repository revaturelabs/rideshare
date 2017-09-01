package com.revature.rideshare.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.rideshare.json.Option;

public class EquivalenceUtilities {

	/**
	 * This method has been deprecated. Use {@link #SafeCompare(Object, Object)
	 * SafeCompare(Object A, Object B) } instead. <br>
	 * <br>
	 * Safely compares two strings that may or may not be null.
	 * 
	 * @param A
	 * @param B
	 * @return True if both strings are null, false if one string is null,
	 *         otherwise returns A.equals(B)
	 */
	@Deprecated
	public static boolean SafeCompareStrings(String A, String B) {
		return SafeCompare(A, B);
	}

	/**
	 * This method has been deprecated. Use {@link #SafeCompare(Object, Object)
	 * SafeCompare(Object A, Object B) } instead<br>
	 * <br>
	 * Safely compares two options that may or may not be null.
	 * 
	 * @param A
	 * @param B
	 * @return True if both options are null, false if one option is null,
	 *         otherwise returns A.equals(B)
	 */
	@Deprecated
	public static boolean SafeCompareOptions(Option A, Option B) {
		return SafeCompare(A, B);
	}

	/**
	 * 
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
	 * Safely compares two lists that may or may not be null.<br>
	 * <br>
	 * Checks if both lists are the same size, then creates a hash code from
	 * each using {@link #ListHash(List) ListHash<T> function}. If both hash
	 * codes are the same, the lists are considered to be equal.<br>
	 * <br>
	 * Will return true even if the objects in both lists are in a different
	 * order from one another.<br>
	 * <br>
	 * Basically free if both lists are of a different size, but must process
	 * every item in each list if both lists are of equal size.<br>
	 * <br>
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
	 * Compounds the hashCode of every object in the list. This will return the
	 * same hashcode for two lists containing the same objects(As measured by
	 * their {@link java.lang.Object#hashCode() Hash Code} ) even if they are in different
	 * orders.
	 * 
	 * @param list
	 * @return compound hashCode for the list.
	 */

	public static <T> int ListHash(List<T> list) {

		Long numToHash = 0l;

		if (list == null) {
			return numToHash.toString().hashCode();
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null)
			{
				continue;
			}
			numToHash += list.get(i).hashCode();
		}

		return numToHash.toString().hashCode();

	}

}
