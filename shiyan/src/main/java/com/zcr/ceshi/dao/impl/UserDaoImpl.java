package com.zcr.ceshi.dao.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zcr.ceshi.dao.UserDao;
import com.zcr.ceshi.pojo.User;

import util.JsonUtil;
public class UserDaoImpl implements UserDao {
	public static Statement getStmt(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//step.2创建连接对象
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "518615");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//step.2创建Statement 对象
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stmt;
	}
	Statement stmt=null;
	public List<User> queryAll() {
	
		ResultSet rs = null;
		List<User> l = new ArrayList<User>();
		try {
			
			rs = stmt.executeQuery("select * from users");
			while(rs.next()){
				//数据的封装
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				l.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	public void add(User u) {
		//step.4创建Result对象，进行处理数据
		String firstname = u.getFirstname();
		String lastname =u.getLastname();
		String phone = u.getPhone();
		String email = u.getEmail();
		//System.out.println(firstname+"|"+lastname+"|"+phone+"|"+email);
		try {
			System.out.println("insert into users(firstname,lastname,phone,email) values('"+firstname+"','"+lastname+"','"+phone+"','"+email+"')");
			stmt.executeUpdate("insert into users(firstname,lastname,phone,email) values('"+firstname+"','"+lastname+"','"+phone+"','"+email+"')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void del(User u) {
		Integer id = u.getId();
		try {
			System.out.println("DELETE FROM users where id=" +id);
			stmt.executeUpdate("DELETE FROM users where id=" +id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void up(User u) {
		//step.4创建Result对象，进行处理数据
		Integer id = u.getId();
		String firstname = u.getFirstname();
		String lastname =u.getLastname();
		String phone = u.getPhone();
		String email = u.getEmail();
		
		try {
			System.out.println("update users set firstname = '"+firstname+"',lastname ='"+lastname+"',phone ='"+phone+"', email = '"+email+"' where id=" +id);
			stmt.executeUpdate("update users set firstname = '"+firstname+"',lastname ='"+lastname+"',phone ='"+phone+"', email = '"+email+"' where id=" +id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	
	




}
