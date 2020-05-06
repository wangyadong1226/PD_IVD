package com.flf.entity;

public class Role {
	private Integer roleId;
	private String roleName;
	private String rights;
	private Integer isSuper;
	private Page page;
	public Integer getIsSuper() {
		return isSuper;
	}
	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	
}
