package person;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")

/*
 * ���α׷� �̸� : Person.java 
 * ���α׷� ���� : Ŭ���� Person 
 * ���� ���α׷� �ۼ��� : 2020-10-04
 */

public class Person implements java.io.Serializable { //implements Serializable 

	private String name; // �̸� �ʵ�
	private String phoneNum; // ��ȭ��ȣ �ʵ�
	private String address; // ���ּ� �ʵ�
	private String email; // �̸��� �ʵ�

	public Person(String name, String phoneNum, String address, String email) {
		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;
		this.email = email;
	}

	public Person() {
	}

	public void setName(String name) { // �̸� ������
		this.name = name;
	}

	public void setPhoneNum(String phoneNum) { // ��ȭ��ȣ ������
		this.phoneNum = phoneNum;
	}

	public void setAddress(String address) { // ���ּ� ������
		this.address = address;
	}

	public void setEmail(String email) { // �̸��� ������
		this.email = email;
	}

	public String getEmail() { // �̸��� ������
		return email;
	}

	public String getName() { // �̸� ������
		return name;
	}

	public String getPhoneNum() { // ��ȭ��ȣ ������
		return phoneNum;
	}

	public String getAddress() { // ���ּ� ������
		return address;
	}
	

	// �ʵ� ������ ���Ͽ� ����(����) �޼ҵ� - �������� ������ ���ܵ�
	public void writeMyField(DataOutputStream dos) throws Exception {
		try {
			dos.writeUTF(name);
			dos.writeUTF(phoneNum);
			dos.writeUTF(address);
			dos.writeUTF(email);
		} catch (IOException ioe) {
			throw new IOException("PersonSaveIOE\"");
		} catch (Exception ex) {
			throw new Exception("PersonSaveEx");
		}
	}

	// ���Ϸκ��� ������ �а� �ʵ忡 �־��ִ� �޼ҵ� - �������� ������ ���ܵ�
	public void readMyField(DataInputStream dis) throws Exception {
		try {
			name = dis.readUTF();
			phoneNum = dis.readUTF();
			address = dis.readUTF();
			email = dis.readUTF();
		} catch (EOFException eofe) {
			throw new EOFException("PersonReadEOFE");
		} catch (IOException ioe) {
			throw new IOException("PersonReadIOE");
		} catch (Exception ex) {
			throw new Exception("PersonReadEx");
		}
	}
}
