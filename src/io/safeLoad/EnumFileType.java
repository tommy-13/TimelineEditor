package io.safeLoad;

public enum EnumFileType {

	DATA_BASE;
	
	
	public static String getFileExtension(EnumFileType type) {
		switch(type) {
		case DATA_BASE: default: return "tlf"; // time line file
		}
	}
}
