package com.tek.sm.util;

public enum CommandPermissions {
	RELOAD("admin.reload"),
	LIST("user.list"),
	PLAY("user.play"),
	LOOP("user.loop"),
	STOP("user.stop"),
	SKIP("user.skip"),
	TUNE("user.tune"),
	SHUFFLE("user.shuffle"),
	GUI("user.gui"),
	IMPORT("user.import"),
	VOLUME("user.volume"),
	PLAYLIST("user.playlist");
	
	private final String text;
	CommandPermissions(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return Reference.PERMISSION_ROOT + text;
    }
}
