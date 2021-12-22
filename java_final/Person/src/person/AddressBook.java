package person;

import java.io.*;
import java.lang.Thread.State;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@SuppressWarnings("unused")

public class AddressBook {
	static Connection con = null;
	static Statement st;
	static PreparedStatement ps;
	static ResultSet rs;
	DataSource ds;

	//AddressBook 생성자에서 파라메터로 con 넘기기
	public AddressBook(Connection conn) throws Exception {
		con = conn;

		// 테이블 생성(존재하지 않을 시에만)
		st = con.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS addressbook(" + "name varchar(10) not null, "
				+ "phoneNum varchar(20) not null, " + "address varchar(30), " + "email varchar(30)" + ")";

		// sql문 해석 및 실행
		rs = st.executeQuery(sql);
		dbClose();
	}

	// DB닫기 기능 메소드 작성
	public void dbClose() throws Exception{
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
	}

	public AddressBook(int num) throws Exception {

	}

	public int getCount() throws Exception { // 등록된 사람 수 접근자

		int result = 0;

		String sql = "SELECT COUNT (*) AS COUNT FROM addressbook";
		st = con.createStatement();
		rs = st.executeQuery(sql); // sql문 해석하여 값을 받음

		while (rs.next()) // 반복하며 등록된 사람 수를 세고 result로 반환
			result = rs.getInt("COUNT");

		return result;
	}

	// 동명이인 확인 메소드
	public boolean checkName(String name) throws Exception {
		String sql = "SELECT * FROM addressbook WHERE name =?";
		ps = con.prepareStatement(sql); // sql문 해석
		ps.setString(1, name.trim());

		rs = ps.executeQuery(); // 실행
		if (rs.next()) // 존재한다면 true 반환
			return true;

		return false;
	}

	// 등록된 전화번호가 있는지 확인 메소드
	public boolean checkPhoneNum(String phoneNum) throws Exception {
		String sql = "SELECT * FROM addressbook WHERE phoneNum =?";
		ps = con.prepareStatement(sql); // sql문 해석
		ps.setString(1, phoneNum.trim());

		rs = ps.executeQuery(); // 실행
		if (rs.next()) // 존재한다면 true 반환
			return true;

		return false;
	}

	// 주소록 등록 메소드
	public void add(Person p) throws Exception {	

			String sql = "INSERT INTO addressbook values (?,?,?,?)";
			ps = con.prepareStatement(sql); // sql문 해석

			// 해당 값 받아와서 지정된 위치에 저장
			ps.setString(1, p.getName());
			ps.setString(2, p.getPhoneNum());
			ps.setString(3, p.getAddress());
			ps.setString(4, p.getEmail());

			ps.executeUpdate(); // 실행
	}

	// 이름으로 주소록 번호 검색 메소드, 등록된 이름 없을 경우 익셉션
	public int searchName(String name) throws Exception {
		int result = 0;

		String sql = "SELECT * FROM addressbook WHERE name = ?";
		ps = con.prepareStatement(sql); // sql문 해석
		ps.setString(1, name);
		rs = ps.executeQuery(); // 실행

		while (rs.next()) // 반복해서 찾은 뒤, 있으면 INT 형식으로 반환
			result = rs.getInt("name");

		return result - 1;
	}

	// 전화번호로 주소록 번호 검색 메소드, 등록된 전화번호 없을 경우 익셉션
	public int searchPhoneNum(String phoneNum) throws Exception {
		int result = 0;

		String sql = "SELECT * FROM addressbook WHERE phoneNum = ?";
		ps = con.prepareStatement(sql); // sql문 해석
		ps.setString(1, phoneNum);
		rs = ps.executeQuery(); // 실행

		while (rs.next()) // 반복해서 찾은 뒤, 있으면 INT 형식으로 반환
			result = rs.getInt("phoneNum");

		return result - 1;
	}

	// 주소록 수정 메소드
	public void modify(int index, Person P) throws Exception {
		rs.absolute(index + 1);

		String sql = "UPDATE addressbook GET name=?, phoneNum =?, address=?,email=?";
		ps = con.prepareStatement(sql); // sql문 해석

		// 수정할 정보 받아와서 지정된 위치에 넣어주기
		ps.setString(1, P.getName());
		ps.setString(2, P.getPhoneNum());
		ps.setString(3, P.getAddress());
		ps.setString(4, P.getEmail());

		// 실행하기
		ps.executeUpdate();
	}

	public void delete(String name) throws Exception {
		String deleteName = rs.getString("name");
		String sql = "delete from addressbook where name = '" + deleteName + "';";
		st = con.createStatement();

		st.executeUpdate(sql); // sql문 해석 및 실행
	}

	// Person 객체 넘겨주는 메소드
	public Person getPerson(int index) throws Exception {
		String sql = "SELECT * FROM addressbook";
		st = con.createStatement();
		rs = st.executeQuery(sql); // sql문 해석 및 실행

		rs.absolute(index + 1);

		// 각 정보 받아와서 새로운 Person 객체 만들어서 넘겨주기
		String gName = rs.getString("name");
		String gPhone = rs.getString("phoneNum");
		String gAddress = rs.getString("address");
		String gEmail = rs.getString("email");

		//새로운 객체 p 만들어 return
		Person p = new Person(gName, gPhone, gAddress, gEmail);
		return p;
	}

}
