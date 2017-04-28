import java.util.LinkedList;

public class Buffer {
	private final int DEFAULT_LENGTH = 1;
	private LinkedList<Cell[][]> buffer;
	private int length;
	
	public Buffer(int length){
		buffer = new LinkedList<Cell[][]>();
		this.length = length;
	}
	
	public Buffer(){
		buffer = new LinkedList<Cell[][]>();
		this.length = DEFAULT_LENGTH;
	}
	
	public Cell[][] remove(){
		return buffer.removeFirst();
	}
	
	public boolean add(Cell[][] c){
		if(buffer != null && buffer.size() == length)
			return false;
		buffer.add(c);
		return true;
	}
	
	public boolean isEmpty(){
		return buffer.isEmpty();
	}
	
	public int getLength(){
		return length;
	}
	
}
