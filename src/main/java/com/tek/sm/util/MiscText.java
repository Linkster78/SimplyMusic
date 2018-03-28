package com.tek.sm.util;

public enum MiscText {
	CLEARFILTER("Right click to clear filter");
	
	private final String text;
	MiscText(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
