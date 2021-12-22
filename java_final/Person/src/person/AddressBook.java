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

	//AddressBook �����ڿ��� �Ķ���ͷ� con �ѱ��
	public AddressBook(Connection conn) throws Exception {
		con = conn;

		// ���̺� ����(�������� ���� �ÿ���)
		st = con.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS addressbook(" + "name varchar(10) not null, "
				+ "phoneNum varchar(20) not null, " + "address varchar(30), " + "email varchar(30)" + ")";

		// sql�� �ؼ� �� ����
		rs = st.executeQuery(sql);
		dbClose();
	}

	// DB�ݱ� ��� �޼ҵ� �ۼ�
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

	public int getCount() throws Exception { // ��ϵ� ��� �� ������

		int result = 0;

		String sql = "SELECT COUNT (*) AS COUNT FROM addressbook";
		st = con.createStatement();
		rs = st.executeQuery(sql); // sql�� �ؼ��Ͽ� ���� ����

		while (rs.next()) // �ݺ��ϸ� ��ϵ� ��� ���� ���� result�� ��ȯ
			result = rs.getInt("COUNT");

		return result;
	}

	// �������� Ȯ�� �޼ҵ�
	public boolean checkName(String name) throws Exception {
		String sql = "SELECT * FROM addressbook WHERE name =?";
		ps = con.prepareStatement(sql); // sql�� �ؼ�
		ps.setString(1, name.trim());

		rs = ps.executeQuery(); // ����
		if (rs.next()) // �����Ѵٸ� true ��ȯ
			return true;

		return false;
	}

	// ��ϵ� ��ȭ��ȣ�� �ִ��� Ȯ�� �޼ҵ�
	public boolean checkPhoneNum(String phoneNum) throws Exception {
		String sql = "SELECT * FROM addressbook WHERE phoneNum =?";
		ps = con.prepareStatement(sql); // sql�� �ؼ�
		ps.setString(1, phoneNum.trim());

		rs = ps.executeQuery(); // ����
		if (rs.next()) // �����Ѵٸ� true ��ȯ
			return true;

		return false;
	}

	// �ּҷ� ��� �޼ҵ�
	public void add(Person p) throws Exception {	

			String sql = "INSERT INTO addressbook values (?,?,?,?)";
			ps = con.prepareStatement(sql); // sql�� �ؼ�

			// �ش� �� �޾ƿͼ� ������ ��ġ�� ����
			ps.setString(1, p.getName());
			ps.setString(2, p.getPhoneNum());
			ps.setString(3, p.getAddress());
			ps.setString(4, p.getEmail());

			ps.executeUpdate(); // ����
	}

	// �̸����� �ּҷ� ��ȣ �˻� �޼ҵ�, ��ϵ� �̸� ���� ��� �ͼ���
	public int searchName(String name) throws Exception {
		int result = 0;

		String sql = "SELECT * FROM addressbook WHERE name = ?";
		ps = con.prepareStatement(sql); // sql�� �ؼ�
		ps.setString(1, name);
		rs = ps.executeQuery(); // ����

		while (rs.next()) // �ݺ��ؼ� ã�� ��, ������ INT �������� ��ȯ
			result = rs.getInt("name");

		return result - 1;
	}

	// ��ȭ��ȣ�� �ּҷ� ��ȣ �˻� �޼ҵ�, ��ϵ� ��ȭ��ȣ ���� ��� �ͼ���
	public int searchPhoneNum(String phoneNum) throws Exception {
		int result = 0;

		String sql = "SELECT * FROM addressbook WHERE phoneNum = ?";
		ps = con.prepareStatement(sql); // sql�� �ؼ�
		ps.setString(1, phoneNum);
		rs = ps.executeQuery(); // ����

		while (rs.next()) // �ݺ��ؼ� ã�� ��, ������ INT �������� ��ȯ
			result = rs.getInt("phoneNum");

		return result - 1;
	}

	// �ּҷ� ���� �޼ҵ�
	public void modify(int index, Person P) throws Exception {
		rs.absolute(index + 1);

		String sql = "UPDATE addressbook GET name=?, phoneNum =?, address=?,email=?";
		ps = con.prepareStatement(sql); // sql�� �ؼ�

		// ������ ���� �޾ƿͼ� ������ ��ġ�� �־��ֱ�
		ps.setString(1, P.getName());
		ps.setString(2, P.getPhoneNum());
		ps.setString(3, P.getAddress());
		ps.setString(4, P.getEmail());

		// �����ϱ�
		ps.executeUpdate();
	}

	public void delete(String name) throws Exception {
		String deleteName = rs.getString("name");
		String sql = "delete from addressbook where name = '" + deleteName + "';";
		st = con.createStatement();

		st.executeUpdate(sql); // sql�� �ؼ� �� ����
	}

	// Person ��ü �Ѱ��ִ� �޼ҵ�
	public Person getPerson(int index) throws Exception {
		String sql = "SELECT * FROM addressbook";
		st = con.createStatement();
		rs = st.executeQuery(sql); // sql�� �ؼ� �� ����

		rs.absolute(index + 1);

		// �� ���� �޾ƿͼ� ���ο� Person ��ü ���� �Ѱ��ֱ�
		String gName = rs.getString("name");
		String gPhone = rs.getString("phoneNum");
		String gAddress = rs.getString("address");
		String gEmail = rs.getString("email");

		//���ο� ��ü p ����� return
		Person p = new Person(gName, gPhone, gAddress, gEmail);
		return p;
	}

}
