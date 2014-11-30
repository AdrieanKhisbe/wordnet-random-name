package org.kohsuke.randname;

/**
 * Generates pseudo random unique names that combines one adjective and one
 * noun, like "friendly tiger" or "good apple".
 *
 * There's about 1.5 million unique combinations, and if you keep requesting a
 * new word it will start to loop (but this code will generate all unique
 * combinations before it starts to loop.)
 *
 * @author Kohsuke Kawaguchi
 */
public class RandomNameGenerator {

	/**
	 * Dictionnary used by the RNG
	 */
	private final Dictionary dico;
	
	/**
	 * Current used position of the RNG
	 */
	private int pos;

	public RandomNameGenerator(Dictionary dico, int pos) {
		this.dico = dico;
		this.pos = pos;
	}

	public RandomNameGenerator(Dictionary dico) {
		this(dico, (int) System.currentTimeMillis());
	}

	public RandomNameGenerator(int seed) {
		this(Dictionary.INSTANCE, seed);
	}

	public RandomNameGenerator() {
		this((int) System.currentTimeMillis());
	}

	/**
	 * Get the next named generated
	 * 
	 * @return next name
	 */
	public synchronized String next() {
		pos = Math.abs(pos + dico.getPrime()) % dico.size();
		// TODO: see what the prime is for?
		// ¤Check Math.abs sems to be to prevent negative overflow
		return dico.word(pos);
	}

	/**
	 * Send back current name generated
	 * 
	 * @return current name
	 */
	public String current() {
		return dico.word(pos);
	}

	
	
	// TODO: SEE which accessors to give access.
	// ¤ASK
	public Dictionary getDico() {
		return dico;
	}
	
	
}
