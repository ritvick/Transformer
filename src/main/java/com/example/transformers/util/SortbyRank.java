package com.example.transformers.util;

import java.util.Comparator;

import com.example.model.Transformer;

public class SortbyRank implements Comparator<Transformer> {

	// Used for sorting in ascending order of
	// rank number
	public int compare(Transformer a, Transformer b) {
		return a.getRank() - b.getRank();
	}

}
