package com.revature.rideshare.util;

public class EquivalenceUtilities {

	public static boolean SafeCompareStrings(String A, String B)
	{
		if (A == null && B == null)
		{
			return true;
		}
		else if (A == null || B == null)
		{
			return false;
		}
		return A.equals(B);
	}
	
}
