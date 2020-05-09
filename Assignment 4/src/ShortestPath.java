/**
 *  @author Ryan Xu
 *	CS1027
 *  This class attempts to find a path from the start to the destination given a map file
 */

public class ShortestPath {

	/** Instance Variables */
	CityMap cityMap;
	
	/** Constructor */
	public ShortestPath (CityMap theMap) {
		cityMap = theMap;
	}
	
	/** Looks for a path with the minimum number of map cells from the startin cell to the destination cell */
	public void findShortestPath() {
		OrderedCircularArray<MapCell> list = new OrderedCircularArray<MapCell>();
		MapCell startingCell = cityMap.getStart();
		list.insert(startingCell, 0);
		startingCell.markInList();
		boolean destinationFound = false;
		int numberOfCells = 0;

		
		while (!list.isEmpty() && !destinationFound) {
			MapCell smallest = list.getSmallest();
			smallest.markOutList();
			
			if (smallest.isDestination()) {
				destinationFound = true;
				break;
			}
			else {
			
					for (int i = 0; i <= 3; i++) {
						if (nextCell(smallest, i) != null && !nextCell(smallest, i).isMarked() 
						&& !nextCell(smallest, i).isBlock()) {
							int distance = 1 + smallest.getDistanceToStart();
							MapCell current = nextCell(smallest, i);
						
							if (current.getDistanceToStart() > distance) {
								current.setDistanceToStart(distance);
								current.setPredecessor(smallest);
							}
							int nextCellDistance = current.getDistanceToStart();
							
							if (current.isMarkedInList() && nextCellDistance < list.getValue(current)) {
								list.changeValue(current, nextCellDistance);
							}
							else if (!current.isMarkedInList()) {
								list.insert(current, nextCellDistance);
								current.markInList();
								if (current.isDestination()) {
									numberOfCells = nextCellDistance + 1;
								}
									
							}
						}	
					}			
				}
			}
		if (destinationFound) {
			System.out.println("There were " + numberOfCells + " cells in the path.");
		}
		else if (list.isEmpty()) {
			System.out.println("No path found");
		}
		
	}
	
	/** Returns the first unmarked neighboring map cell that can be used to continue the path form the current one
	 * @param cell The current cell
	 * @param index Determines the direction from the current cell to be returned
	 * @return The cell at the given index, if the given cell meets conditions */
	private MapCell nextCell(MapCell cell, int index) {
		
		try {
			MapCell answerCell = null;
			if (index == 0) {
				if (cell.getNeighbour(index).isNorthRoad() || cell.getNeighbour(index).isIntersection()) {
					answerCell = cell.getNeighbour(index);
				}
				else if (cell.isNorthRoad() && cell.getNeighbour(index).isDestination()) {
					answerCell = cell.getNeighbour(index);
				}
			}
			else if (index == 1) {
				if (cell.getNeighbour(index).isEastRoad() || cell.getNeighbour(index).isIntersection()) {
					answerCell = cell.getNeighbour(index);
				}
				else if (cell.isEastRoad() && cell.getNeighbour(index).isDestination()) {
					answerCell = cell.getNeighbour(index);
				}
			}
			else if (index == 2) {
				if (cell.getNeighbour(index).isSouthRoad() || cell.getNeighbour(index).isIntersection()) {
					answerCell = cell.getNeighbour(index);
				}
				else if (cell.isSouthRoad() && cell.getNeighbour(index).isDestination()) {
					answerCell = cell.getNeighbour(index);
				}
			}
			else if (index == 3) {
				if (cell.getNeighbour(index).isWestRoad() || cell.getNeighbour(index).isIntersection()) {
					answerCell = cell.getNeighbour(index);
				}
				else if (cell.isWestRoad() && cell.getNeighbour(index).isDestination()) {
					answerCell = cell.getNeighbour(index);
				}
			}
			return answerCell;
		}
		catch (NullPointerException e) {
			return null;
		}
	}
}
