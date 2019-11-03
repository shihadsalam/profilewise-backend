package com.profilewise.model;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum RoleEnum {
	
	Admin("Admin", Arrays.asList("Admin", "Manager", "Employee", "HOD", "Student"), PermissionEnum.ALL),
	Manager("Manager", Arrays.asList("Manager", "Employee"), PermissionEnum.ALL),
	Employee("Employee", Arrays.asList("Employee"), PermissionEnum.NONE),
	HOD("HOD", Arrays.asList("HOD", "Student"), PermissionEnum.ALL),
	Student("Student",  Arrays.asList("Student"), PermissionEnum.NONE);

	private String roleName;
	private List<String> authorizedRoleNames;
	private PermissionEnum permission;
	
	private RoleEnum(String role, List<String> authorizedRoleNames, PermissionEnum permissionVal) {
		this.roleName = role;
		this.authorizedRoleNames = authorizedRoleNames;
		this.permission = permissionVal;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<String> getAuthorizedRoleNames() {
		return authorizedRoleNames;
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
