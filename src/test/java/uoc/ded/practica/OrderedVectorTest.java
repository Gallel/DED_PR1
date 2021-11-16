package uoc.ded.practica;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ded.practica.model.Activity;
import uoc.ded.practica.util.OrderedVector;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.ExcepcioPosicioInvalida;
import uoc.ei.tads.Iterador;

public class OrderedVectorTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	private final static Comparator<StringBuffer> COMPARATOR_STRING = new Comparator<StringBuffer>() {
		@Override
		public int compare(StringBuffer string1, StringBuffer string2) {
			return string1.compareTo(string2);
		}
	};
	
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
	public void testOrderedVectorAdd() {
		
		OrderedVector<String> elems = new OrderedVector<String>(10, Activity.COMPARATOR);
		
		// Insert elements in a random order
		elems.add("F");
		elems.add("B");
		elems.add("H");
		elems.add("A");
		elems.add("J");
		elems.add("D");
		elems.add("I");
		elems.add("E");
		elems.add("C");
		elems.add("G");
		
		// Try to add another element out of bounds
		try {
			elems.add("K");
			fail("fail ExcepcioContenidorPle");
		} catch(ExcepcioContenidorPle e) {}
		
		// Print them
		for (Iterador<String> it = elems.elements(); it.hiHaSeguent();) {
			System.out.print(it.seguent() + " ");
		}
		
		// The outContent must be ordered according to the comparator without the K element
		Assert.assertEquals("A B C D E F G H I J ", outContent.toString());
	}
	
	@Test
	public void testOrderedVectorIsEmpty() {
		
		OrderedVector<String> elems = new OrderedVector<String>(10, Activity.COMPARATOR);
		
		// Initially, elems must be empty
		Assert.assertEquals(true, elems.estaBuit());
		
		// Insert elements in a random order
		elems.add("F");
		elems.add("B");
		elems.add("H");
		elems.add("A");
		elems.add("J");
		
		// Now, elems is not empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
		
		// Insert more elements in a random order until elems will be full
		elems.add("D");
		elems.add("I");
		elems.add("E");
		elems.add("C");
		elems.add("G");
		
		// However elems is full, it cannot be empty because it has elements inside
		Assert.assertEquals(false, elems.estaBuit());
	}
	
	@Test
	public void testOrderedVectorIsFull() {
		
		OrderedVector<String> elems = new OrderedVector<String>(10, Activity.COMPARATOR);
		
		// Initially, elems must be empty, so cannot be full
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert elements in a random order
		elems.add("F");
		elems.add("B");
		elems.add("H");
		elems.add("A");
		elems.add("J");
		
		// Now, elems is not empty, but not full yet
		Assert.assertEquals(false, elems.estaPle());
		
		// Insert more elements in a random order until elems will be full
		elems.add("D");
		elems.add("I");
		elems.add("E");
		elems.add("C");
		elems.add("G");
		
		// Now has to be full because it only has 10 positions
		Assert.assertEquals(true, elems.estaPle());
	}
	
	@Test
	public void testOrderedVectorNumElems() {
		
		OrderedVector<String> elems = new OrderedVector<String>(10, Activity.COMPARATOR);
		
		// Initially, the amount of elements of elems must be 0
		Assert.assertEquals(0, elems.nombreElems());
		
		// Insert elements in a random order
		elems.add("F");
		elems.add("B");
		elems.add("H");
		elems.add("A");
		elems.add("J");
		
		// 5 elements added, so the amount of elements has to be 5
		Assert.assertEquals(5, elems.nombreElems());
		
		// Insert more elements in a random order until elems will be full
		elems.add("D");
		elems.add("I");
		elems.add("E");
		elems.add("C");
		elems.add("G");
		
		// 10 elements added, so the amount of elements has to be 10
		Assert.assertEquals(10, elems.nombreElems());
	}
	
	@Test
	public void testOrderedVectorGet() {
		
		OrderedVector<String> elems = new OrderedVector<String>(10, Activity.COMPARATOR);
		
		// Initially, it has to be null because there are no elements yet
		Assert.assertEquals(elems.get(0), null);
		
		// Insert elements in a random order
		elems.add("F");
		elems.add("B");
		elems.add("H");
		elems.add("A");
		elems.add("J");
		elems.add("D");
		elems.add("I");
		elems.add("E");
		elems.add("C");
		elems.add("G");
		
		// Every element has to be found in its correct position according to the comparator
		Assert.assertEquals("A", elems.get(0));
		Assert.assertEquals("B", elems.get(1));
		Assert.assertEquals("C", elems.get(2));
		Assert.assertEquals("D", elems.get(3));
		Assert.assertEquals("E", elems.get(4));
		Assert.assertEquals("F", elems.get(5));
		Assert.assertEquals("G", elems.get(6));
		Assert.assertEquals("H", elems.get(7));
		Assert.assertEquals("I", elems.get(8));
		Assert.assertEquals("J", elems.get(9));
		
		// A non existing position has to be null
		Assert.assertEquals(elems.get(10), null);
	}
	
	@Test
	public void testOrderedVectorUpdate() {
		
		OrderedVector<StringBuffer> elems = new OrderedVector<StringBuffer>(10, OrderedVectorTest.COMPARATOR_STRING);
		
		StringBuffer a = new StringBuffer("AA");
		StringBuffer b = new StringBuffer("BB");
		StringBuffer c = new StringBuffer("CC");
		StringBuffer d = new StringBuffer("DD");
		StringBuffer e = new StringBuffer("EE");
		StringBuffer f = new StringBuffer("FF");
		StringBuffer g = new StringBuffer("GG");
		StringBuffer h = new StringBuffer("HH");
		StringBuffer i = new StringBuffer("II");
		StringBuffer j = new StringBuffer("JJ");
		
		// Insert elements in a random order
		elems.add(f);
		elems.add(b);
		elems.add(h);
		elems.add(a);
		elems.add(j);
		elems.add(d);
		elems.add(i);
		elems.add(e);
		elems.add(c);
		elems.add(g);
		
		f.insert(0, "Z");
		elems.update(f);
		
		for (Iterador<StringBuffer> it = elems.elements(); it.hiHaSeguent();) {
			System.out.print(it.seguent() + " ");
		}
		
		a.insert(0, "Y");
		elems.update(a);
		
		for (Iterador<StringBuffer> it = elems.elements(); it.hiHaSeguent();) {
			System.out.print(it.seguent() + " ");
		}
		
		f.insert(0, "X");
		elems.update(f);
		
		for (Iterador<StringBuffer> it = elems.elements(); it.hiHaSeguent();) {
			System.out.print(it.seguent() + " ");
		}
		
		// Try to update a non existing element in elems has to do nothing
		StringBuffer n = new StringBuffer("NN");
		n.insert(0, "A");
		
		try {
			elems.update(n);
			fail("fail ExcepcioPosicioInvalida");
		} catch (ExcepcioPosicioInvalida ex) {}
		
		// It has to be ordered according to the comparator
		Assert.assertEquals("AA BB CC DD EE GG HH II JJ ZFF BB CC DD EE GG HH II JJ YAA ZFF BB CC DD EE GG HH II JJ XZFF YAA ", outContent.toString());
	}
}
