package com.flf.mapper;

import java.util.List;

import com.flf.entity.Role;

public interface RoleMapper {
	List<Role> listAllRoles(Role role);
	Role getRoleById(int roleId);
	void insertRole(Role role);
	void updateRoleBaseInfo(Role role);
	void deleteRoleById(int roleId);
	int getCountByName(Role role);
	void updateRoleRights(Role role);
}
