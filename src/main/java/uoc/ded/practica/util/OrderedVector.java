package uoc.ded.practica.util;

import java.util.Comparator;

import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.ExcepcioPosicioInvalida;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.IteradorVectorImpl;
import uoc.ei.tads.Utilitats;

public class OrderedVector<E> implements ContenidorAfitat<E> {

	private static final long serialVersionUID = Utilitats.getSerialVersionUID();;
	
	private Comparator<E> comparator;
	
	private E[] elements;
	private int n;
	
	@SuppressWarnings("unchecked")
	public OrderedVector(int max, Comparator<E> comparator) {
		this.comparator = comparator;
		this.elements = (E[]) new Object[max];
		this.n = 0;
	}
	
	@Override
	public Iterador<E> elements() {
		return (Iterador<E>) new IteradorVectorImpl<E>(this.elements, this.n, 0);
	}

	@Override
	public boolean estaBuit() {
		return this.n == 0;
	}

	@Override
	public int nombreElems() {
		return this.n;
	}

	@Override
	public boolean estaPle() {
		return this.n == this.elements.length;
	}
	
	public E get(int index) {
		
		if (index < 0 || index >= this.n)
			// Must return null rather than throw ExcepcioPosicioInvalida
			// because code shouldn't do defensive programming, just handle
			// the out of bounds index
			return null;
		
		return this.elements[index];
	}
	
	public void add(E elem) {
		
		if (this.estaPle())
			throw new ExcepcioContenidorPle();
		
		this.elements[this.n] = elem;
		this.n++;
		
		E aux;
		boolean ordered = false;
		int index = this.n - 1;
		
		while (index > 0 && !ordered) {
			aux = this.get(index - 1);
			
			if (this.comparator.compare(elem, aux) < 0) {
				this.elements[index] = aux;
				this.elements[index - 1] = elem;
			}
			else
				ordered = true;
			
			index--;
		}
	}
	
	public void update(E elem) {
		int index = this.getIndex(elem);
		
		if (index == -1)
			// In this case the code can throw an exception rather
			// stop the execution with a return statement because all
			// elements has to be added with the methods of this class
			throw new ExcepcioPosicioInvalida();
		
		this.delete(index);
		this.add(elem);
	}
	
	private int getIndex(E elem) {
		int index = 0;
		
		for (Iterador<E> it = this.elements(); it.hiHaSeguent();)
		{
			if (it.seguent().equals(elem))
				return index;
			
			index++;
		}
		
		return -1;
	}
	
	private void delete(int index) {
		
		for (int i = index; i < this.n - 1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		
		this.elements[this.n - 1] = null;
		this.n--;
	}
	
}
