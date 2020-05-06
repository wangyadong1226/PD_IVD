package com.flf.service.impl;

import java.util.List;

import com.flf.entity.Role;
import com.flf.mapper.RoleMapper;
import com.flf.service.RoleService;

public class RoleServiceImpl implements RoleService{

	private RoleMapper roleMapper;
	
	public List<Role> listAllRoles(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.listAllRoles(role);
	}

	public void deleteRoleById(int roleId) {
		// TODO Auto-generated method stub
		roleMapper.deleteRoleById(roleId);
	}

	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(roleId);
	}

	public boolean insertRole(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.insertRole(role);
			return true;
		}
	}

	public boolean updateRoleBaseInfo(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}
	
	public void updateRoleRights(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateRoleRights(role);
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

}
