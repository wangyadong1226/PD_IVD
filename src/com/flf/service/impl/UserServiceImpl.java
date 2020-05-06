package com.flf.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.flf.entity.User;
import com.flf.mapper.UserMapper;
import com.flf.service.UserService;

public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.getUserById(userId);
	}

	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		int count = userMapper.getCountByName(user.getLoginname());
		if(count>0){
			return false;
		}else{
			userMapper.insertUser(user);
			return true;
		}
		
	}

	public List<User> listPageUser(User user){
		return userMapper.listPageUser(user);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userMapper.updateUser(user);
	}

	public void updateUserBaseInfo(User user){
		userMapper.updateUserBaseInfo(user);
	}
	
	public void updateUserRights(User user){
		userMapper.updateUserRights(user);
	}
	
	public User getUserByNameAndPwd(String loginname, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setLoginname(loginname);
		user.setPassword(password);
		return userMapper.getUserInfo(user);
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void deleteUser(int userId){
		userMapper.deleteUser(userId);
	}

	public User getUserAndRoleById(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.getUserAndRoleById(userId);
	}

	public void updateLastLogin(User user) {
		// TODO Auto-generated method stub
		userMapper.updateLastLogin(user);
	}

	public List<User> listAllUser() {
		// TODO Auto-generated method stub
		return userMapper.listAllUser();
	}

	public List<User> listAllUserRole(Integer roleId) {
		// TODO Auto-generated method stub
		return userMapper.listAllUserRole(roleId);
	}
	public List<User> listAllUserByroleId7() {
		List<User> users = userMapper.listAllUser();
		List<User> userList=new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getRole().getRoleId()==7){
				userList.add(users.get(i));
			}
		}
		return userList;
	}
}
