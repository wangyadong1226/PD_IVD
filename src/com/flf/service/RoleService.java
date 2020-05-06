package com.flf.service;

import java.util.List;

import com.flf.entity.Role;

public interface RoleService {
	List<Role> listAllRoles(Role role);
	Role getRoleById(int roleId);
	boolean insertRole(Role role);
	boolean updateRoleBaseInfo(Role role);
	void deleteRoleById(int roleId);
	void updateRoleRights(Role role);
}
