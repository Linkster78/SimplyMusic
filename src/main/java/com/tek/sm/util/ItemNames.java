package com.tek.sm.util;

public enum ItemNames {
	BACKMAIN("Back to the music menu"),
	BACK("Previous Page"),
	NEXT("Next Page"),
	STOP("Stop Playback"),
	SKIP("Skip Song"),
	PLAY("Play"),
	SHUFFLE("Shuffle"),
	TUNE("Listen along with other players"),
	REFRESH("Refresh List"),
	VOLUME("Volume"),
	FILTER("Filter Songs");
	
	private final String text;
	ItemNames(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
