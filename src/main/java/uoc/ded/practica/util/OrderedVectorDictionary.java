package uoc.ded.practica.util;

import java.util.Comparator;
import uoc.ei.tads.ClauValor;
import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.DiccionariVectorImpl;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.Utilitats;

public class OrderedVectorDictionary<C, E> extends DiccionariVectorImpl<C, E> implements ContenidorAfitat<E> {

	private static final long serialVersionUID = Utilitats.getSerialVersionUID();;
	
	public static final int MAXIM_ELEMENTS_PER_DEFECTE = 256;

	private Comparator<C> comparator;
	
	public OrderedVectorDictionary(Comparator<C> comparator) {
		this(MAXIM_ELEMENTS_PER_DEFECTE, comparator);
	}
	
	public OrderedVectorDictionary(int max, Comparator<C> comparator) {
		super(max);
		this.comparator = comparator;
	}

	@Override
	public boolean estaPle() {
		return this.n == this.diccionari.length;
	}
	
	@Override
	public void afegir(C clau, E obj) {
		if (this.estaPle())
			throw new ExcepcioContenidorPle();
		
		// Add the element
		super.afegir(clau, obj);
		
		int index = this.n - 1;
		
		ClauValor<C, E> aux;
		ClauValor<C, E> last = this.diccionari[index];
		
		// Move it until the TAD is ordered
		while (index > 0) {
			aux = this.diccionari[index-1];
			
			if (this.comparator.compare((C)last.getClau(), (C)aux.getClau()) < 0)
			{
				this.diccionari[index] = aux;
				this.diccionari[index-1] = last;
			} else
				break;
			
			index--;
		}
	}
	
	@Override
	public E consultar(C clau) {
		int index = binarySearch(clau, 0, this.n - 1);
		
		if (index < 0 || index >= this.n)
			// Must return null rather than throw ExcepcioPosicioInvalida
			// because SafetyActivities4Covid19 try to throw ActivityNotFoundException
			return null;
		
		return this.diccionari[index].getValor();
	}
	
	// Binary search to be used in the get method, O(log n)
	private int binarySearch(C key, int left, int right) {
		int mid = (left + right) / 2;
		ClauValor<C, E> aux = this.diccionari[mid];
		
		if (left > right)
			return -1;
		else {
			if (this.comparator.compare(key, (C)aux.getClau()) < 0)
				return binarySearch(key, 0, mid - 1);
			else if (this.comparator.compare(key, (C)aux.getClau()) > 0)
				return binarySearch(key, mid + 1, right);
			else
				return mid;
		}
	}
	
}
