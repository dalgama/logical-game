import java.lang.NullPointerException ;
import java.lang.IllegalStateException ;


public class GenericLinkedStack<E> implements Stack<E>{


	private static class Elem<T> {
		private T info;
		private Elem<T> next;

		private Elem(T info, Elem<T> next) {
			this.info = info;
			this.next = next;
		}
	}

	private Elem<E> top;

	private int size ;

	public GenericLinkedStack(){
		top = null ;
		size = 0 ;
	}

	public void push(E elem){
		if( elem == null ) {
			throw new NullPointerException("Cannot push a null object into a stack") ;
		}
		
		top = new Elem<E>(elem,top);
		size++ ;
	}
	public boolean isEmpty() {
		return top == null ;
	}
	
	public E pop() {
		if(isEmpty()) {
			throw new EmptyStackException("Cannot pop an object from an empty stack") ;
		}
	//stack in not empty (solved)
		E tmp = top.info;
		top = top.next;
		size-- ;
		return tmp;
	}

	public E peek(){
		if(isEmpty()){
			throw new IllegalStateException("Cannot peek an object from an empty stack") ;
		}
	// stack is not empty solved
		return top.info;
	}

	public int getSize() {
		return size ;
	}

	public void clear() {
		top = null ;
		size = 0 ;
	}
}