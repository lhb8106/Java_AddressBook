package person;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//ADDRESSBOOK_GUI 클래스
public class Addressbook_GUI extends JFrame {

	private JPanel contentPane; // ContentPane
	private JTable table; // Table
	static int MAX = 100; // 저장 최대값 설정
	Person p;
	static AddressBook ad;
	String name, phoneNum, address, email;

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String url = "jdbc:mariadb://localhost:3306/addressbookdb"; // url주소 선언
				String id = "root"; // 아이디 값 선언
				String pwd = "1234"; // 패스워드 값 선언

				try {
					//드리아버 연결
					Class.forName("org.mariadb.jdbc.Driver");
					//데이터 베이스 연결
					Connection con = DriverManager.getConnection(url, id, pwd);
					//AddressBook 생성시 con 넣어주기
					ad = new AddressBook(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Addressbook_GUI frame = new Addressbook_GUI();
				frame.setVisible(true);
			}
		});
	}

	// window builder로 만든 GUI
	public Addressbook_GUI() {
		setTitle("addressbook");
		setBackground(UIManager.getColor("Button.background"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 494);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Add Button
		JButton btnAdd = new JButton("ADD");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.GRAY);
		btnAdd.setBounds(55, 402, 95, 23);
		contentPane.add(btnAdd);

		// Modify Button
		JButton btnModify = new JButton("MODIFY");
		btnModify.setForeground(Color.WHITE);
		btnModify.setBackground(Color.GRAY);
		btnModify.setBounds(196, 402, 95, 23);
		contentPane.add(btnModify);

		// Delete Button
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.GRAY);
		btnDelete.setBounds(328, 402, 93, 23);
		contentPane.add(btnDelete);

		// Finish Button
		JButton btnFinish = new JButton("FINISH");
		btnFinish.setForeground(Color.WHITE);
		btnFinish.setBackground(Color.GRAY);
		btnFinish.setBounds(597, 402, 95, 23);
		contentPane.add(btnFinish);

		// Search Button
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(Color.GRAY);
		btnSearch.setBounds(390, 86, 95, 23);
		contentPane.add(btnSearch);

		// Readall Button
		JButton btnReadall = new JButton("READALL");
		btnReadall.setForeground(Color.WHITE);
		btnReadall.setBackground(Color.GRAY);
		btnReadall.setBounds(497, 86, 95, 23);
		contentPane.add(btnReadall);

		// textField_name
		TextField textField_name = new TextField();
		textField_name.setBounds(528, 158, 177, 23);
		contentPane.add(textField_name);

		// textField_phonenum
		TextField textField_phonenum = new TextField();
		textField_phonenum.setBounds(528, 216, 177, 23);
		contentPane.add(textField_phonenum);

		// textField_address
		TextField textField_address = new TextField();
		textField_address.setBounds(528, 274, 177, 23);
		contentPane.add(textField_address);

		// textField_email
		TextField textField_email = new TextField();
		textField_email.setBounds(528, 332, 177, 23);
		contentPane.add(textField_email);

		// label_NAME
		Label label_NAME = new Label("NAME");
		label_NAME.setFont(new Font("Dialog", Font.BOLD, 10));
		label_NAME.setBounds(528, 129, 84, 23);
		contentPane.add(label_NAME);

		// label_PHONENUM
		Label label_PHONENUM = new Label("ADDRESS");
		label_PHONENUM.setFont(new Font("Dialog", Font.BOLD, 10));
		label_PHONENUM.setBounds(528, 245, 84, 23);
		contentPane.add(label_PHONENUM);

		// label_ADDRESS
		Label label_ADDRESS = new Label("E-MAIL");
		label_ADDRESS.setFont(new Font("Dialog", Font.BOLD, 10));
		label_ADDRESS.setBounds(528, 303, 84, 23);
		contentPane.add(label_ADDRESS);

		// label_EMAIL
		Label label_EMAIL = new Label("PHONENUM");
		label_EMAIL.setFont(new Font("Dialog", Font.BOLD, 10));
		label_EMAIL.setBounds(528, 187, 103, 23);
		contentPane.add(label_EMAIL);

		// scrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 129, 454, 238);
		contentPane.add(scrollPane);

		// table
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setForeground(Color.DARK_GRAY);
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "NAME", "PHONENUM", "ADDRESS", "E-MAIL" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(54);
		table.getColumnModel().getColumn(1).setPreferredWidth(58);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(74);
		scrollPane.setViewportView(table);

		// comboBox
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "NAME", "PHONENUM" }));
		comboBox.setBounds(31, 86, 119, 23);
		contentPane.add(comboBox);

		// textField4_ 검색 기능 시 사용되는 textField
		TextField textField_4 = new TextField();
		textField_4.setBounds(169, 86, 177, 23);
		contentPane.add(textField_4);

		// addressbook title Label
		Label label_4 = new Label("ADDRESSBOOK");
		label_4.setFont(new Font("Dialog", Font.BOLD, 30));
		label_4.setBounds(33, 10, 388, 57);
		contentPane.add(label_4);

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// row를 누르면 해당 정보 textfield에 옮기는 데에 필요한 MouseListener 추가
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedIndex;
				JTable jt = (JTable) e.getSource();
				selectedIndex = jt.getSelectedRow(); // table에서 선택된 정보에 대한 인덱스 반환

				int i = selectedIndex; // i는 선택된 인덱스

				// 선택된 인덱스에 대한 정보 textfield에 가져오기
				try {
					textField_name.setText(ad.getPerson(i).getName());
					textField_phonenum.setText(ad.getPerson(i).getPhoneNum());
					textField_address.setText(ad.getPerson(i).getAddress());
					textField_email.setText(ad.getPerson(i).getEmail());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		// add버튼 구현
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] rows = new String[4];

				// 입력된 값들을 받아오기
				rows[0] = textField_name.getText();
				rows[1] = textField_phonenum.getText();
				rows[2] = textField_address.getText();
				rows[3] = textField_email.getText();

				// getText로 입력된 값 가져오기
				String name = textField_name.getText();
				String phoneNum = textField_phonenum.getText();
				String address = textField_address.getText();
				String email = textField_email.getText();

				// 입력된 정보가 없거나 이미 등록된 정보가 있다면 경고창
				if (name.equals("") || phoneNum.equals("")) {
					JOptionPane.showMessageDialog(null, "필수 정보를 입력해주세요.(이름, 전화번호)", "Message",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else
					try {
						if (ad.checkName(name) == true) {
							JOptionPane.showMessageDialog(null, "이미 등록된 이름입니다.", "Message",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else if (ad.checkPhoneNum(phoneNum) == true) {
							JOptionPane.showMessageDialog(null, "이미 등록된 전화번호입니다.", "Message",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else {
							model.addRow(rows); // 한 줄 단위로 대입

							// 입력후 텍스트 필드 값 제거
							textField_name.setText("");
							textField_phonenum.setText("");
							textField_address.setText("");
							textField_email.setText("");

							try {
								ad.add(new Person(name, phoneNum, address, email)); // 정보 추가
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});

		// delete 버튼 구현
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 선택한 줄(row)의 번호 알아내기
				int rowIndex = table.getSelectedRow();

				// 테이블에서 삭제할 정보를 선택하지 않고, 삭제를 누르면, 경고창
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 정보가 선택되지 않았습니다.", "Message", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					// 삭제하기 전 다시 물어보기.
					int result = JOptionPane.showConfirmDialog(null, "정보를 삭제하시겠습니까?", "Confirm",
							JOptionPane.YES_NO_OPTION);

					// 만약 그냥 닫음을 누르면 삭제하지 않음
					if (result == JOptionPane.CLOSED_OPTION)
						return;

					// Yes를 누르면 정보 삭제
					else if (result == JOptionPane.YES_OPTION) {
						model.removeRow(rowIndex); // 해당 row 삭제
						try {
							ad.delete(name);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // 데이터 지우기

						// 입력후 텍스트 필드 값 제거
						textField_name.setText("");
						textField_phonenum.setText("");
						textField_address.setText("");
						textField_email.setText("");
					}

					// No버튼을 누르면 삭제하지 않음
					else
						return;
				}
			}
		});

		// search 버튼 구현
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 콤보박스 카테고리 가져오기
				String category = (String) comboBox.getSelectedItem();

				if (category.equals("NAME")) { // category에서 NAME으로 검색할 시
					try {
						name = textField_4.getText(); // 이름 입력 받음
						textField_4.setText(""); // 입력 후 텍스트 필드 값 제거

						// 만약 이름을 작성하지 않고 검색버튼 누른다면, 경고창
						if (name.equals("")) {
							JOptionPane.showMessageDialog(null, "텍스트필드가 비었습니다. 검색하실 이름을 작성해주세요.", "Message",
									JOptionPane.WARNING_MESSAGE);

						} else { // 등록된 정보를 찾으면 changeSelection으로 해당 row 선택
							int index = ad.searchName(name);
							table.changeSelection(index, 0, false, false);

							// 찾은 인덱스의 정보 textfield에 가져오기
							int i = index;
							textField_name.setText(ad.getPerson(i).getName());
							textField_phonenum.setText(ad.getPerson(i).getPhoneNum());
							textField_address.setText(ad.getPerson(i).getAddress());
							textField_email.setText(ad.getPerson(i).getEmail());
						}
					} catch (Exception e1) { // 만약 등록된 정보가 없다면 경고창
						JOptionPane.showMessageDialog(null, "등록된 이름이 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
					}
				} else { // phoneNum으로 검색시
					try {
						phoneNum = textField_4.getText(); // 전화번호 입력 받음
						textField_4.setText(""); // 입력 후 텍스트 필드 값 제거

						// 만약 전화번호를 작성하지 않고 검색버튼 누른다면, 경고창
						if (phoneNum.equals("")) {
							JOptionPane.showMessageDialog(null, "텍스트필드가 비었습니다. 검색하실 전화번호를 작성해주세요.", "Message",
									JOptionPane.WARNING_MESSAGE);

						} else { // 등록된 정보를 찾으면 changeSelection으로 해당 row 선택
							int index = ad.searchPhoneNum(phoneNum);
							table.changeSelection(index, 0, false, false);

							// 찾은 인덱스의 정보 textfield에 가져오기
							int i = index;
							textField_name.setText(ad.getPerson(i).getName());
							textField_phonenum.setText(ad.getPerson(i).getPhoneNum());
							textField_address.setText(ad.getPerson(i).getAddress());
							textField_email.setText(ad.getPerson(i).getEmail());
						}
					} catch (Exception e1) { // 만약 등록된 정보가 없다면 경고창
						JOptionPane.showMessageDialog(null, "등록된 전화번호가 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});

		// Modify 버튼 구현
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] rows = new String[4];

				// getText로 수정된 데이터 값 가져오기
				String editname = textField_name.getText();
				String editphoneNum = textField_phonenum.getText();
				String editaddress = textField_address.getText();
				String editemail = textField_email.getText();

				// 만약 선택된 row가 없는데 수정을 하려고하면, 경고창
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "수정할 정보가 선택되지 않았습니다.", "Message", JOptionPane.WARNING_MESSAGE);
					return;

				} else { // 수정할 정보가 선택 되었다면
					int editIndex = table.getSelectedRow(); // 수정할 row 찾아서 editIndex에 해당 row의 index번호 저장하기

					// 만약 수정할 이름이나 전화번호가 비어있다면 경고창
					if (editname.equals("") || editphoneNum.equals("")) {
						JOptionPane.showMessageDialog(null, "필수 정보를 입력해주세요.(이름, 전화번호)", "Message",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					do { // (do-while)이름 겹치지 않게 수정
						try {
							if (editname.equals(ad.getPerson(editIndex).getName())) {// 이름 수정 안하는경우
								break;
							} else {
								if (ad.checkName(editname) == true) {// 이름 수정할 때, 등록된 이름일 경우
									JOptionPane.showMessageDialog(null, "이미 저장된 이름입니다.", "Message",
											JOptionPane.WARNING_MESSAGE);
									return;
								} else
									break;
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} while (true);

					do { // (do-while)전화번호 겹치지 않게 수정
						try {
							if (editphoneNum.equals(ad.getPerson(editIndex).getPhoneNum())) {// 전화번호 수정 안하는경우
								break;
							} else {
								if (ad.checkPhoneNum(editphoneNum) == true) { // 전화번호 수정할 때, 등록된 전화번호일 경우
									JOptionPane.showMessageDialog(null, "이미 저장된 전화번호입니다.", "Message",
											JOptionPane.WARNING_MESSAGE);
									return;
								} else
									break;
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} while (true);

					// 정보를 수정하기 전에 다시 물어보기
					int result = JOptionPane.showConfirmDialog(null, "정보를 수정하시겠습니까?", "Confirm",
							JOptionPane.YES_NO_OPTION);

					// 만약 그냥 닫음을 누르면 삭제하지 않음
					if (result == JOptionPane.CLOSED_OPTION)
						return;

					// 만약 Yes를 누르면 수정
					else if (result == JOptionPane.YES_OPTION) {

						Person editPerson = null;
						try {
							editPerson = ad.getPerson(editIndex);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // 수정할 index의 Person객체 가져오기
						try {
							ad.modify(editIndex, editPerson);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // 가져온 객체 정보 수정

						int rowIndex = table.getSelectedRow(); // 수정할 객체의 원래 테이블 삭제
						model.removeRow(editIndex);
						try {
							ad.delete(name);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // 데이터 지우기

						// 입력된 값들을 배열로 만들기
						rows[0] = textField_name.getText();
						rows[1] = textField_phonenum.getText();
						rows[2] = textField_address.getText();
						rows[3] = textField_email.getText();

						// 입력후 텍스트 필드 값 제거
						textField_name.setText("");
						textField_phonenum.setText("");
						textField_address.setText("");
						textField_email.setText("");

						model.addRow(rows); // 새로 고친 정보 대입

						// 수정된 뒤 안내문
						JOptionPane.showMessageDialog(null, "수정되었습니다.", "Message", JOptionPane.PLAIN_MESSAGE);

						try {
							// 새로 입력받은 정보들로 객체 생성
							ad.add(new Person(editname, editphoneNum, editaddress, editemail));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else // 그 외의 다른것 누르면 수정 안 함
						return;
				}
			}
		});

		// Readall버튼 구현
		btnReadall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				model.setNumRows(0); // 테이블 초기화

				int c = 0;
				try {
					c = ad.getCount();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} // 전체 저장된 정보 숫자 세기

				if (c == 0) { // 등록된 주소록이 없다면 없다는 경고창
					JOptionPane.showMessageDialog(null, "등록된 주소록이 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				} else { // 등록된 정보가 있다면 c에 해당하는 크기만큼 반복문 돌며 출력
					for (int i = 0; i < c; i++) { // 주소록 크기만큼 반복
						String[] rows = new String[4]; // 입력된 값들 데이터 배열로 생성
						try {
							rows[0] = ad.getPerson(i).getName();
							rows[1] = ad.getPerson(i).getPhoneNum();
							rows[2] = ad.getPerson(i).getAddress();
							rows[3] = ad.getPerson(i).getEmail();
							model.addRow(rows); // 테이블에 데이터 추가
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});

		// Finish 버튼 구현
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 종료하기 전 다시 물어보기.
				int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);

				// 만약 그냥 닫음을 누르면 창이 꺼지지 않음
				if (result == JOptionPane.CLOSED_OPTION)
					return;

				// Yes를 누르면 창이 종료
				else if (result == JOptionPane.YES_OPTION)
					System.exit(0);
					

				// 그 외의 No버튼을 누르면 창이 꺼지지 않음
				else
					return;
			}
		});
	};
}
