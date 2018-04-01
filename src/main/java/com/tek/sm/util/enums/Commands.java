package com.tek.sm.util.enums;

public enum Commands {
	HELP("help"),
	RELOAD("reload"),
	LIST("list"),
	PLAY("play"),
	LOOP("loop"),
	TUNE("tune"),
	STOP("stop"),
	SHUFFLE("shuffle"),
	SKIP("skip"),
	GUI("gui"),
	IMPORT("import"),
	VOLUME("volume");
	
	private final String text;
	Commands(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
