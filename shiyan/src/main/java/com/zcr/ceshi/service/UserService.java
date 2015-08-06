package com.zcr.ceshi.service;
import java.util.List;
import com.zcr.ceshi.pojo.User;

public interface UserService {
	public List<User> queryAllUser();
	public void addUser(User u);
	public void delUser(User u);
	public void upUser(User u);
}
