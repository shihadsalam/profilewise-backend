package com.profilewise.model;

public enum PermissionEnum {
	
	ALL("All"),
	NONE("None");

	private String permission;
	
	private PermissionEnum(String permissionVal) {
		permission = permissionVal;
	}

	public String getPermission() {
		return permission;
	}
	
}
