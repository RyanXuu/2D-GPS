/**
 *  @author Ryan Xu
 *	CS1027
 *  This class represents data items that will be stored in a circular array
 */

public class CellData<T> {

	/** Instance Variables */
	private T id; 
	private int value;
	
	/** Constructor */
	public CellData(T theId, int theValue) {
		id = theId;
		value = theValue;
	}
	
	/** Getter Methods */
	public int getValue() {
		return value;
	}
	public T getId() {
		return id;
	}
	
	/** Setter Methods */
	public void setValue(int newValue) {
		value = newValue;
	}
	
	public void setId(T newId) {
		id = newId;
	}
}
