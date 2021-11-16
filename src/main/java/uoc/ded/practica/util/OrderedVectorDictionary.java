package uoc.ded.practica.util;

import java.util.Comparator;
import uoc.ei.tads.ClauValor;
import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.DiccionariVectorImpl;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.Utilitats;

public class OrderedVectorDictionary<K, E> extends DiccionariVectorImpl<K, E> implements ContenidorAfitat<E> {

	private static final long serialVersionUID = Utilitats.getSerialVersionUID();;
	
	public static final int MAXIM_ELEMENTS_PER_DEFECTE = 256;

	private Comparator<K> comparator;
	
	public OrderedVectorDictionary(Comparator<K> comparator) {
		this(MAXIM_ELEMENTS_PER_DEFECTE, comparator);
	}
	
	public OrderedVectorDictionary(int max, Comparator<K> comparator) {
		super(max);
		this.comparator = comparator;
	}

	@Override
	public boolean estaPle() {
		return this.n == this.diccionari.length;
	}
	
	public void insert(K key, E elem) {
		if (this.estaPle())
			throw new ExcepcioContenidorPle();
		
		super.afegir(key, elem);
		
		boolean ordered = false;
		int index = this.n - 1;
		
		ClauValor<K, E> aux;
		ClauValor<K, E> last = this.diccionari[index];
		
		while (index > 0 && !ordered) {
			aux = this.diccionari[index-1];
			
			if (this.comparator.compare((K)last.getClau(), (K)aux.getClau()) < 0)
			{
				this.diccionari[index] = aux;
				this.diccionari[index-1] = last;
			} else
				ordered = true;
			
			index--;
		}
	}
	
	public E get(K key) {
		int index = binarySearch(key, 0, this.n - 1);
		
		if (index < 0 || index >= this.n)
			// Must return null rather than throw ExcepcioPosicioInvalida
			// because SafetyActivities4Covid19 try to throw ActivityNotFoundException
			return null;
		
		return this.diccionari[index].getValor();
	}
	
	private int binarySearch(K key, int left, int right) {
		int mid = (left + right) / 2;
		ClauValor<K, E> aux = this.diccionari[mid];
		
		if (left > right)
			return -1;
		else {
			if (this.comparator.compare(key, (K)aux.getClau()) < 0)
				return binarySearch(key, 0, mid - 1);
			else if (this.comparator.compare(key, (K)aux.getClau()) > 0)
				return binarySearch(key, mid + 1, right);
			else
				return mid;
		}
	}
	
}
