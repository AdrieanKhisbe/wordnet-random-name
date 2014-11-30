package org.kohsuke.randname;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kohsuke Kawaguchi
 */
public class RandomNameGeneratorTest extends Assert {
  
	
	RandomNameGenerator rng; 
	
	@Before 
	public void setup(){
		List<String> a = new ArrayList<String>();
		List<String> n = new ArrayList<String>();

		for (int i = 0; i < 8; i++) {
			a.add("A" + i);
			n.add("N" + i);
		}
		Dictionary d = new Dictionary(a, n);
		
		rng = new RandomNameGenerator(d, 42);
	}
	
    /**
     * We can generate up to {@link Dictionary#size()} unique combinations.
     */
    @Test
    public void uniqueness() {
        int sz = rng.getDico().size();
        Set<String> s = new HashSet<String>(sz);

        for (int i=0; i<sz; i++)
            assertTrue(s.add(rng.next()));

        assertFalse(s.add(rng.next()));
    }

    @Test
    public void firstTen() {
        for (int i=0; i<10; i++)
            System.out.println(rng.next());
    }
    
    // TODO: Adapt test with new changes
    
}
