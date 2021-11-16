package uoc.ded.practica;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ded.practica.model.Activity;
import uoc.ded.practica.util.OrderedVectorDictionary;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.Iterador;

public class OrderedVectorDictionaryTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
	@Test
	public void testOrderedVectorDictionaryInsert() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Insert elements in a random order
		elems.insert("F", 6);
		elems.insert("B", 2);
		elems.insert("H", 8);
		elems.insert("A", 1);
		elems.insert("J", 10);
		elems.insert("D", 4);
		elems.insert("I", 9);
		elems.insert("E", 5);
		elems.insert("C", 3);
		elems.insert("G", 7);
		
		// Try to add another element when it is full return an exception
		try {
			elems.insert("K", 11);
			fail("fail ExcepcioContenidorPle");
		} catch(ExcepcioContenidorPle e) {}

		
		// Print them
		for (Iterador<Integer> it = elems.elements(); it.hiHaSeguent();) {
			System.out.print(it.seguent() + " ");
		}
		
		// The outContent must be ordered according to the comparator without the number 11
		Assert.assertEquals("1 2 3 4 5 6 7 8 9 10 ", outContent.toString());
	}
	
	@Test
	public void testOrderedVectorDictionaryIsEmpty() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Initially, elems must be empty
		Assert.assertEquals(true, elems.estaBuit());
		
		// Insert elements in a random order
		elems.insert("F", 6);
		elems.insert("B", 2);
		elems.insert("H", 8);
		elems.insert("A", 1);
		elems.insert("J", 10);
		
		// Now, elems is not empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
		
		// Insert more elements in a random order until elems will be full
		elems.insert("D", 4);
		elems.insert("I", 9);
		elems.insert("E", 5);
		elems.insert("C", 3);
		elems.insert("G", 7);
		
		// However elems is full, it cannot be empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
	}
	
	@Test
	public void testOrderedVectorDictionaryIsFull() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Initially, elems must be empty, so cannot be full
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert elements in a random order
		elems.insert("F", 6);
		elems.insert("B", 2);
		elems.insert("H", 8);
		elems.insert("A", 1);
		elems.insert("J", 10);
		
		// Now, elems is not empty, but not full yet
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert more elements in a random order until elems will be full
		elems.insert("D", 4);
		elems.insert("I", 9);
		elems.insert("E", 5);
		elems.insert("C", 3);
		elems.insert("G", 7);
		
		// Now has to be full because it only has 10 positions
		Assert.assertEquals(true, elems.estaPle());
	}
	
	@Test
	public void testOrderedVectorDictionaryGet() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Cannot find anything because elems is just initialized
		Assert.assertEquals(elems.get("F"), null);
		
		// Insert elements in a random order
		elems.insert("F", 6);
		elems.insert("B", 2);
		elems.insert("H", 8);
		elems.insert("A", 1);
		elems.insert("J", 10);
		elems.insert("D", 4);
		elems.insert("I", 9);
		elems.insert("E", 5);
		elems.insert("C", 3);
		elems.insert("G", 7);
		
		// All elements has to be found by its key and in its correct position according to the comparator
		Assert.assertEquals((Integer)1, elems.get("A"));
		Assert.assertEquals((Integer)2, elems.get("B"));
		Assert.assertEquals((Integer)3, elems.get("C"));
		Assert.assertEquals((Integer)4, elems.get("D"));
		Assert.assertEquals((Integer)5, elems.get("E"));
		Assert.assertEquals((Integer)6, elems.get("F"));
		Assert.assertEquals((Integer)7, elems.get("G"));
		Assert.assertEquals((Integer)8, elems.get("H"));
		Assert.assertEquals((Integer)9, elems.get("I"));
		Assert.assertEquals((Integer)10, elems.get("J"));
		
		// A non existing key has to fail
		Assert.assertEquals(elems.get("Z"), null);
	}
}
