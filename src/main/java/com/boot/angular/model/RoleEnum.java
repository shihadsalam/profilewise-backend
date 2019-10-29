package com.boot.angular.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum RoleEnum {
	
	Admin("Admin", PermissionEnum.ALL),
	Student("Student", PermissionEnum.NONE),
	HOD("HOD", PermissionEnum.ALL),
	Employee("Employee", PermissionEnum.ALL),
	Manager("Manager", PermissionEnum.ALL);

	private String roleName;
	private PermissionEnum permission;
	
	private RoleEnum(String role, PermissionEnum permissionVal) {
		roleName = role;
		permission = permissionVal;
	}

	public String getRoleName() {
		return roleName;
	}

	public PermissionEnum getPermission() {
		return permission;
	}
	
	public static RoleEnum getRole(String roleName) {
		Optional<RoleEnum> opt = Stream.of(values()).filter(val -> roleName.equals(val.getRoleName())).findFirst();
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
}
