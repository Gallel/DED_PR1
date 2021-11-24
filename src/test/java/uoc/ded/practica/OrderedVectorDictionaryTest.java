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
		elems.afegir("F", 6);
		elems.afegir("B", 2);
		elems.afegir("H", 8);
		elems.afegir("A", 1);
		elems.afegir("J", 10);
		elems.afegir("D", 4);
		elems.afegir("I", 9);
		elems.afegir("E", 5);
		elems.afegir("C", 3);
		elems.afegir("G", 7);
		
		// Try to add another element when it is full return an exception
		try {
			elems.afegir("K", 11);
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
		elems.afegir("F", 6);
		elems.afegir("B", 2);
		elems.afegir("H", 8);
		elems.afegir("A", 1);
		elems.afegir("J", 10);
		
		// Now, elems is not empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
		
		// Insert more elements in a random order until elems will be full
		elems.afegir("D", 4);
		elems.afegir("I", 9);
		elems.afegir("E", 5);
		elems.afegir("C", 3);
		elems.afegir("G", 7);
		
		// However elems is full, it cannot be empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
	}
	
	@Test
	public void testOrderedVectorDictionaryIsFull() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Initially, elems must be empty, so cannot be full
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert elements in a random order
		elems.afegir("F", 6);
		elems.afegir("B", 2);
		elems.afegir("H", 8);
		elems.afegir("A", 1);
		elems.afegir("J", 10);
		
		// Now, elems is not empty, but not full yet
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert more elements in a random order until elems will be full
		elems.afegir("D", 4);
		elems.afegir("I", 9);
		elems.afegir("E", 5);
		elems.afegir("C", 3);
		elems.afegir("G", 7);
		
		// Now has to be full because it only has 10 positions
		Assert.assertEquals(true, elems.estaPle());
	}
	
	@Test
	public void testOrderedVectorDictionaryGet() {
		OrderedVectorDictionary<String, Integer> elems = new OrderedVectorDictionary<String, Integer>(10, Activity.COMPARATOR);
		
		// Cannot find anything because elems is just initialized
		Assert.assertEquals(elems.consultar("F"), null);
		
		// Insert elements in a random order
		elems.afegir("F", 6);
		elems.afegir("B", 2);
		elems.afegir("H", 8);
		elems.afegir("A", 1);
		elems.afegir("J", 10);
		elems.afegir("D", 4);
		elems.afegir("I", 9);
		elems.afegir("E", 5);
		elems.afegir("C", 3);
		elems.afegir("G", 7);
		
		// All elements has to be found by its key and in its correct position according to the comparator
		Assert.assertEquals((Integer)1, elems.consultar("A"));
		Assert.assertEquals((Integer)2, elems.consultar("B"));
		Assert.assertEquals((Integer)3, elems.consultar("C"));
		Assert.assertEquals((Integer)4, elems.consultar("D"));
		Assert.assertEquals((Integer)5, elems.consultar("E"));
		Assert.assertEquals((Integer)6, elems.consultar("F"));
		Assert.assertEquals((Integer)7, elems.consultar("G"));
		Assert.assertEquals((Integer)8, elems.consultar("H"));
		Assert.assertEquals((Integer)9, elems.consultar("I"));
		Assert.assertEquals((Integer)10, elems.consultar("J"));
		
		// A non existing key has to fail
		Assert.assertEquals(elems.consultar("Z"), null);
	}
}
