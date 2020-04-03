
import java.util.Iterator;

public class TwoDArray implements Iterable<Integer> {

	int[][] myArray;

	public TwoDArray(int[][] myArray) {
		this.myArray = myArray;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new TwoDArrayIterator();
	}
  
  private class TwoDArrayIterator implements Iterator<Integer> {

		int rowIndex;
		int colIndex;

		@Override
		public boolean hasNext() {
			if (rowIndex < myArray.length) {
				return true;
			}
			return false;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new ArrayIndexOutOfBoundsException();
			}
			int returnValue = myArray[rowIndex][colIndex];
			if (rowIndex < myArray.length && colIndex < myArray[rowIndex].length - 1) {
				colIndex++;
			} else if (rowIndex < myArray.length) {
				colIndex = 0;
				rowIndex++;
				while(rowIndex < myArray.length && myArray[rowIndex].length == 0) {
					rowIndex++;
				}
			}
			return returnValue;
		}

	}
}
