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

	/**
	 * Case strategy used to format the expressions created by the dictionary
	 */
	private Case currentCase;

	// TODO: explain what is it used for
	private final int prime;

	// ¤maybe: not final anymore: to enable dictionary to be build.

	public Dictionary(List<String> nouns, List<String> adjectives) {
		this.nouns = nouns;
		this.adjectives = adjectives;
		this.prime = computePrime();
		this.currentCase = Case.SNAKE;
	}

	public Dictionary(List<String> nouns, List<String> adjectives,
			Case caseStrategy) {
		this(nouns, adjectives);
		this.currentCase = caseStrategy;
	}

	// MAYBE for dynamic dictoanry
	// public Dictionary() {
	// this.nouns = new ArrayList<String>();
	// this.adjectives = new ArrayList<String>();
	// this.prime = 23; // will be recomputed.
	// }

	// TODO Make it a factory method!
	/**
	 * Factory method to create the standard Wordnet dictionary
	 * 
	 * @return Wordnet dictionary
	 */
	// TODO: extract file one method
	public static Dictionary standardWordnetDictionary() {
		List<String> nouns = new ArrayList<String>();
		List<String> adjectives = new ArrayList<String>();

		try {
			load("a.txt", adjectives);
			load("n.txt", nouns);
			return new Dictionary(nouns, adjectives);

		} catch (IOException e) {
			throw new Error(
					"Error occur when trying to build the wordnet dictionnary",
					e);
		}
	}

	/**
	 * Compute prime value to use for the dictionary
	 */
	private int computePrime() {
		int combo = this.size();

		int primeCombo = 2;
		while (primeCombo <= combo) {
			int nextPrime = primeCombo + 1;
			primeCombo *= nextPrime;
		}
		return primeCombo + 1;
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

	public Case getCurrentCase() {
		return currentCase;
	}

	public void setCurrentCase(Case currentCase) {
		this.currentCase = currentCase;
	}

	/**
	 * Indexed based accès to the expressions (adjective + word) of the
	 * dictionnary
	 * 
	 * @param i
	 *            index of the expression
	 * @return the indexed expression
	 */
	// MAYBE: rename?
	public String word(int i) {
		int a = i % adjectives.size();
		int n = i / adjectives.size();
		// ¤MAYBE: cache value if list are final

		return currentCase.combine(adjectives.get(a), adjectives.get(n));
	}

	/**
	 * Load list of word into provided list
	 * 
	 * @param ressourceName
	 *            Name of the file containing the list of words
	 * @param list
	 *            The list to be filled
	 * @throws IOException
	 */
	// ¤maybe rename method And make signature send back the list
	private static void load(String ressourceName, List<String> list)
			throws IOException {
		BufferedReader r = new BufferedReader(
				new InputStreamReader(
						Dictionary.class.getResourceAsStream(ressourceName),
						"US-ASCII"));
		String line;
		while ((line = r.readLine()) != null)
			list.add(line);
	}

	static final Dictionary INSTANCE = standardWordnetDictionary();

	/**
	 * Util interface to represent the case strategy to apply to form
	 * the expression from adjective and nouns
	 * 
	 * @author Adriean
	 *
	 */
	interface CaseStrategy {
		public String format(String adjective, String noun);
	}

	/**
	 * Enum to capture the name of case pattern and associated case strategy to
	 * use to format the messages
	 * 
	 * @author Adriean
	 *
	 */
	public enum Case {
		// MAYBE replace with two flag; capitalized and separator
		SNAKE(new CaseStrategy() {
			public String format(String adjective, String noun) {
				return adjective + "_" + noun;
			}
		}), CAMEL(new CaseStrategy() {
			public String format(String adjective, String noun) {
				return adjective.substring(0, 1).toUpperCase()
						+ adjective.substring(1) + ""
						+ noun.substring(0, 1).toUpperCase()
						+ noun.substring(1);
			}
		}), SPACE_CAMEL(new CaseStrategy() {
			public String format(String adjective, String noun) {
				return adjective.substring(0, 1).toUpperCase()
						+ adjective.substring(1) + " "
						+ noun.substring(0, 1).toUpperCase()
						+ noun.substring(1);
			}
		});

		CaseStrategy strategy;

		private Case(CaseStrategy strategy) {
			this.strategy = strategy;
		}

		public String combine(String adjective, String noun) {
			return strategy.format(adjective, noun);
		}
	}

}
