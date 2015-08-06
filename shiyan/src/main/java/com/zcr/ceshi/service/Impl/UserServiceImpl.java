package com.zcr.ceshi.service.Impl;

import java.util.List;

import com.zcr.ceshi.dao.UserDao;
import com.zcr.ceshi.dao.impl.UserDaoImpl;
import com.zcr.ceshi.pojo.User;
import com.zcr.ceshi.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	
	public List<User> queryAllUser() {
		List<User> l = userDao.queryAll();
		return l;
	}

	public void addUser(User u) {
		userDao.add(u);
		
	}

	public void delUser(User u) {
		userDao.del(u);
		
	}

	public void upUser(User u) {
		userDao.up(u);
		
	}
}
