package org.kohsuke.randname;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kohsuke Kawaguchi
 */
public class DictionaryTest {
	
	@Test
	public void size() {
		List<String> a = new ArrayList<String>();
		List<String> n = new ArrayList<String>();

		for (int i = 0; i < 3; i++){
			a.add("A" + i);
			n.add("N" + i);
		}
		Dictionary d = new Dictionary(a,n);
		
		assertEquals("Size of dictionary is wrong", d.size(), 9);
		assertTrue("Prime is too small wrong", d.getPrime() > d.size());
	}
}
