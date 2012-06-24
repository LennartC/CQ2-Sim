package be.lacerta.cq2.utils;

public class Tag {
	String original;
	String originalEnd;
	String replacer;
	String replacerEnd;
	int count;
	
	public Tag() {
		this("test","test2");
	}
	
	public Tag(String original, String replacer) {
		setOriginal(original);
		setReplacer(replacer);
		count = 0;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getReplacer() {
		return replacer;
	}

	public void setReplacer(String replacer) {
		this.replacer = replacer;
	}

	public void add() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
}
