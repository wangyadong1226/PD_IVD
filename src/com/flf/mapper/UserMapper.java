package com.flf.mapper;

import java.util.List;

import com.flf.entity.User;

public interface UserMapper  {
	List<User> listAllUser();
	
	List<User> listAllUserRole(Integer roleId);
	
	User getUserById(Integer userId);
	
	void insertUser(User user);
	
	void updateUser(User user);
	
	User getUserInfo(User user);
	
	void updateUserBaseInfo(User user);
	
	void updateUserRights(User user);
	
	int getCountByName(String loginname);
	
	void deleteUser(int userId);
	
	int getCount(User user);
	
	List<User> listPageUser(User user);
	
	User getUserAndRoleById(Integer userId);
	
	void updateLastLogin(User user);
}
