package org.kohsuke.randname;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Dictionary of adjectives and nouns.
 *
 * @author Kohsuke Kawaguchi
 */
public class Dictionary {
	private List<String> nouns;
	private List<String> adjectives;

	private final int prime;

	public Dictionary(List<String> nouns, List<String> adjectives, int prime) {
		this.nouns = nouns;
		this.adjectives = adjectives;
		this.prime = prime;
	}

	public Dictionary(int prime) {
		this.nouns = new ArrayList<String>();
		this.adjectives = new ArrayList<String>();
		this.prime = 23; // will be recomputed.
	}

	// public Dictionary() {
	// this();
	// }

	// TODO Make it a factory method!
	public Dictionary() {
		try {
			load("a.txt", adjectives);
			load("n.txt", nouns);
		} catch (IOException e) {
			throw new Error(e);
		}

		// TODO: extract combo compute. (when updating a dictionary)
		// for instance `recomputeCombo`
		// TODO get understanding of what it's really
		int combo = size();

		int primeCombo = 2;
		while (primeCombo <= combo) {
			int nextPrime = primeCombo + 1;
			primeCombo *= nextPrime;
		}
		prime = primeCombo + 1;
	}

	/**
	 * Total size of the combined words.
	 */
	public int size() {
		return nouns.size() * adjectives.size();
	}

	// TODO: introduced getter/adder for adjectives and nous
	// TODO: maybe ad a sorting method.

	/**
	 * Sufficiently big prime that's bigger than {@link #size()}
	 */
	public int getPrime() {
		return prime;
	}

	/**
	 * Indexed based accès to the expressions (adjective + word) of the
	 * dictionnary
	 * 
	 * @param i
	 *            index of the expression
	 * @return the indexed expression
	 */
	public String word(int i) {
		int a = i % adjectives.size();
		int n = i / adjectives.size();
		// MAYBE: cache value

		// TODO see how to extract case logic
		return adjectives.get(a) + "_" + nouns.get(n);
	}

	private void load(String name, List<String> col) throws IOException {
		// TODO: make it static?
		BufferedReader r = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream(name), "US-ASCII"));
		String line;
		while ((line = r.readLine()) != null)
			col.add(line);
	}

	static final Dictionary INSTANCE = new Dictionary();
}
