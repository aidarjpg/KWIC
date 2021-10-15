/**
 * 
 */
package kz.edu.nu.cs.se.hw;

/**
 * @author aidar.amangeldi
 *
 */
public class MyIndexable implements Indexable {
	String entry;
	int lineNum;
	
	public MyIndexable(String en, int linen) {
		entry = en;
		lineNum = linen;
	}
	@Override
	public String getEntry() {
		return entry;
	}

	@Override
	public int getLineNumber() {
		return lineNum;
	}
	
}
