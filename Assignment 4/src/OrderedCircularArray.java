/**
 *  @author Ryan Xu
 *	CS1027
 *  This class implements an ordered list using a circular array
 */

public class OrderedCircularArray<T> implements SortedListADT<T> {

	/** Instance Variables */
	private CellData[] list;
	private int front;
	private int rear;
	private int count;
	
	/** Constructor */
	public OrderedCircularArray() {
		list = new CellData[5];
		front = 1;
		rear = 0;
		count = 0;
	}
	
	/** Adds a new CellData object to the ordered list
	 * Invokes expandQueue if ordered list is full
	 * Sorts the list from smallest to largest int values
	 * @param id The id of the new CellData object
	 * @param value The value of the new CellData object
	 */
	public void insert(T id, int value) {
		CellData newObject = new CellData(id, value);
		if (size() == list.length) {
			expandQueue();
		} 
		if (size() > 0) {
			rear = (rear + 1) % list.length;
			boolean shiftOne = false;
			int index = 0;
			for (int i = front; i < front + size(); i++) {
				if (value < list[i % list.length].getValue()) {
					index = i;
					shiftOne = true;
					break;
				}
			}
			
			if (shiftOne) {
				for (int i = front + size() - 1; i >= index; i--) {
					list[(i + 1) % list.length] = list[i % list.length];
				}
				list[index % list.length] = newObject;
			}
			else {
				list[rear] = newObject;
			}
			
		}
		else if (size() == 0) {
			list[front] = newObject;
			rear = front;
		}
		
		count++;
		
	}
	
	/** Returns the value of a CellData object given an id
	 * @param id The id of the CellData object to be returned
	 * @throws InvalidDataItemException If the given id is not found in the ordered list
	 */
	public int getValue(T id) throws InvalidDataItemException {
		if (!idInList(id)) {
			throw new InvalidDataItemException("Object with id not found");
		}
		
		int value = 0;
		for (int i = front; i < front + size(); i++) {
			if (id.equals(list[i % list.length].getId())) {
				value = list[i % list.length].getValue();
				break;
			}
		}
		return value;
 	}
	
	/** Removes the value of a CellData object given an id
	 * @param id The id of the CellData object to be removed
	 * @throws InvalidDataItemException If the given id is not found in the ordered list
	 */
	public void remove(T id) throws InvalidDataItemException {
		if (!idInList(id)) {
			throw new InvalidDataItemException("Object with id not found");
		}
		
		if (size() == 1) {
			CellData[] newList = new CellData[list.length];
			list = newList;
			front = 1;
			rear = 0;
			count = 0;
		}
		else {
			int index = 0;
			for (int i = front; i < front + size(); i++) {
				if (id.equals(list[(i % list.length)].getId())) {
					index = i;
				}
			}
			for (int i = index; i < front + size(); i++) {
				list[i % list.length] = list[(i + 1) % list.length];
			}
			rear = negativeModulo(rear - 1, list.length);
			count--;
		}
	}
	
	/** Changes the value of a CellData object given an id and a new value
	 * @param id The id of the CellData object to change value
	 * @param newValue new value of the CellData object
	 * @throws InvalidDataItemException If the gien id is not found in the ordered list
	 */
	public void changeValue(T id, int newValue) throws InvalidDataItemException {
		if (!idInList(id)) {
			throw new InvalidDataItemException("Object with id not found");
		}
		
		if (size() == 1) {
			list[front].setValue(newValue);
		}
		else {
			remove(id);
			insert(id, newValue);
		}
	}
	
	/** Removes and returns the id or the CellData object in the ordered list with the smallest associated value
	 * @throws EmptyListException If there are no objects in the list
	 */
	public T getSmallest() throws EmptyListException {
		if (isEmpty()) {
			throw new EmptyListException("The list is empty");
		}
		CellData smallest = list[front];
		
		front = (front + 1) % list.length;
		count--;
		return (T) smallest.getId();
	}
	
	/** Boolean Methods */
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/** Getter Methods */
	public int size() {
		return count;
	}
	
	public int getFront() {
		return front;
	}
	
	public int getRear() {
		return rear;
	}
	
	/** Expands the ordered list if it is full */
	private void expandQueue() {
		CellData[] larger = new CellData[list.length * 2];
		
		for (int i = front; i < front + size(); i++) {
			larger[i] = list[i % size()];
		}
		rear = front + size() - 1;
		list = larger;
		
	}
	
	/** Determines whether a CellData object with the given id is in the list */
	private boolean idInList(T id) {
		boolean found = false;
		
		if (size() == 1) {
			if (id.equals(list[front].getId())) {
				found = true;
			}
		}
		else {
			for (int i = front; i < front + size(); i++) {
				if (id.equals(list[i % list.length].getId())) {
					found = true;
					break;
				}
			}
		}
		return found;		
	} 
	
	/** Used to perform the modulo operation on negative numbers */
	private int negativeModulo(int number, int mod) {
		while (number < 0) {
			number = number + mod;
		}
		return number;
	}
}
