package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.revature.rideshare.util.EquivalenceUtilities;

public class EquivalencyTests {

	@Test
	public void testListComparison() {

		List<String> stringListA = new ArrayList<String>();

		List<String> stringListB = new LinkedList<String>();

		String A = "Alpha";
		String B = "Beta";
		String C = "Gamma";

		stringListA.add(A);
		stringListA.add(B);
		stringListA.add(C);

		stringListB.add(C);
		stringListB.add(B);
		stringListB.add(A);

		assert (EquivalenceUtilities.SafeCompare(stringListA, stringListB));

		stringListB.add(A);

		assert (!EquivalenceUtilities.SafeCompare(stringListA, stringListB));

		stringListB.remove(B);

		assert (!EquivalenceUtilities.SafeCompare(stringListA, stringListB));

	}

}
