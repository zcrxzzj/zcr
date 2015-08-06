package com.zcr.ceshi.dao;

import java.util.List;

import com.zcr.ceshi.pojo.User;

public interface UserDao {
	
	public List<User> queryAll();
	public void add(User u);
	public void del(User u);
	public void up(User u);
}