package be.lacerta.cq2.utils;

public enum Condition {
	LIKE,
	NOT_LIKE,
	GREATER_THAN,
	LOWER_THAN;
	
	public static Condition fromString(String s) {
		if (("like").equals(s)) return LIKE;
		if (("notlike").equals(s)) return NOT_LIKE;
		if (("gt").equals(s)) return GREATER_THAN;
		if (("lt").equals(s)) return LOWER_THAN;
		return null;
	}
}
