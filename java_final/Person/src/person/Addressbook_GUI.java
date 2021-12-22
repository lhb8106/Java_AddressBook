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

//ADDRESSBOOK_GUI Ŭ����
public class Addressbook_GUI extends JFrame {

	private JPanel contentPane; // ContentPane
	private JTable table; // Table
	static int MAX = 100; // ���� �ִ밪 ����
	Person p;
	static AddressBook ad;
	String name, phoneNum, address, email;

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String url = "jdbc:mariadb://localhost:3306/addressbookdb"; // url�ּ� ����
				String id = "root"; // ���̵� �� ����
				String pwd = "1234"; // �н����� �� ����

				try {
					//�帮�ƹ� ����
					Class.forName("org.mariadb.jdbc.Driver");
					//������ ���̽� ����
					Connection con = DriverManager.getConnection(url, id, pwd);
					//AddressBook ������ con �־��ֱ�
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

	// window builder�� ���� GUI
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

		// textField4_ �˻� ��� �� ���Ǵ� textField
		TextField textField_4 = new TextField();
		textField_4.setBounds(169, 86, 177, 23);
		contentPane.add(textField_4);

		// addressbook title Label
		Label label_4 = new Label("ADDRESSBOOK");
		label_4.setFont(new Font("Dialog", Font.BOLD, 30));
		label_4.setBounds(33, 10, 388, 57);
		contentPane.add(label_4);

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// row�� ������ �ش� ���� textfield�� �ű�� ���� �ʿ��� MouseListener �߰�
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedIndex;
				JTable jt = (JTable) e.getSource();
				selectedIndex = jt.getSelectedRow(); // table���� ���õ� ������ ���� �ε��� ��ȯ

				int i = selectedIndex; // i�� ���õ� �ε���

				// ���õ� �ε����� ���� ���� textfield�� ��������
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

		// add��ư ����
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] rows = new String[4];

				// �Էµ� ������ �޾ƿ���
				rows[0] = textField_name.getText();
				rows[1] = textField_phonenum.getText();
				rows[2] = textField_address.getText();
				rows[3] = textField_email.getText();

				// getText�� �Էµ� �� ��������
				String name = textField_name.getText();
				String phoneNum = textField_phonenum.getText();
				String address = textField_address.getText();
				String email = textField_email.getText();

				// �Էµ� ������ ���ų� �̹� ��ϵ� ������ �ִٸ� ���â
				if (name.equals("") || phoneNum.equals("")) {
					JOptionPane.showMessageDialog(null, "�ʼ� ������ �Է����ּ���.(�̸�, ��ȭ��ȣ)", "Message",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else
					try {
						if (ad.checkName(name) == true) {
							JOptionPane.showMessageDialog(null, "�̹� ��ϵ� �̸��Դϴ�.", "Message",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else if (ad.checkPhoneNum(phoneNum) == true) {
							JOptionPane.showMessageDialog(null, "�̹� ��ϵ� ��ȭ��ȣ�Դϴ�.", "Message",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else {
							model.addRow(rows); // �� �� ������ ����

							// �Է��� �ؽ�Ʈ �ʵ� �� ����
							textField_name.setText("");
							textField_phonenum.setText("");
							textField_address.setText("");
							textField_email.setText("");

							try {
								ad.add(new Person(name, phoneNum, address, email)); // ���� �߰�
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

		// delete ��ư ����
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// ������ ��(row)�� ��ȣ �˾Ƴ���
				int rowIndex = table.getSelectedRow();

				// ���̺��� ������ ������ �������� �ʰ�, ������ ������, ���â
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "������ ������ ���õ��� �ʾҽ��ϴ�.", "Message", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					// �����ϱ� �� �ٽ� �����.
					int result = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Confirm",
							JOptionPane.YES_NO_OPTION);

					// ���� �׳� ������ ������ �������� ����
					if (result == JOptionPane.CLOSED_OPTION)
						return;

					// Yes�� ������ ���� ����
					else if (result == JOptionPane.YES_OPTION) {
						model.removeRow(rowIndex); // �ش� row ����
						try {
							ad.delete(name);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // ������ �����

						// �Է��� �ؽ�Ʈ �ʵ� �� ����
						textField_name.setText("");
						textField_phonenum.setText("");
						textField_address.setText("");
						textField_email.setText("");
					}

					// No��ư�� ������ �������� ����
					else
						return;
				}
			}
		});

		// search ��ư ����
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// �޺��ڽ� ī�װ� ��������
				String category = (String) comboBox.getSelectedItem();

				if (category.equals("NAME")) { // category���� NAME���� �˻��� ��
					try {
						name = textField_4.getText(); // �̸� �Է� ����
						textField_4.setText(""); // �Է� �� �ؽ�Ʈ �ʵ� �� ����

						// ���� �̸��� �ۼ����� �ʰ� �˻���ư �����ٸ�, ���â
						if (name.equals("")) {
							JOptionPane.showMessageDialog(null, "�ؽ�Ʈ�ʵ尡 ������ϴ�. �˻��Ͻ� �̸��� �ۼ����ּ���.", "Message",
									JOptionPane.WARNING_MESSAGE);

						} else { // ��ϵ� ������ ã���� changeSelection���� �ش� row ����
							int index = ad.searchName(name);
							table.changeSelection(index, 0, false, false);

							// ã�� �ε����� ���� textfield�� ��������
							int i = index;
							textField_name.setText(ad.getPerson(i).getName());
							textField_phonenum.setText(ad.getPerson(i).getPhoneNum());
							textField_address.setText(ad.getPerson(i).getAddress());
							textField_email.setText(ad.getPerson(i).getEmail());
						}
					} catch (Exception e1) { // ���� ��ϵ� ������ ���ٸ� ���â
						JOptionPane.showMessageDialog(null, "��ϵ� �̸��� �����ϴ�.", "Message", JOptionPane.WARNING_MESSAGE);
					}
				} else { // phoneNum���� �˻���
					try {
						phoneNum = textField_4.getText(); // ��ȭ��ȣ �Է� ����
						textField_4.setText(""); // �Է� �� �ؽ�Ʈ �ʵ� �� ����

						// ���� ��ȭ��ȣ�� �ۼ����� �ʰ� �˻���ư �����ٸ�, ���â
						if (phoneNum.equals("")) {
							JOptionPane.showMessageDialog(null, "�ؽ�Ʈ�ʵ尡 ������ϴ�. �˻��Ͻ� ��ȭ��ȣ�� �ۼ����ּ���.", "Message",
									JOptionPane.WARNING_MESSAGE);

						} else { // ��ϵ� ������ ã���� changeSelection���� �ش� row ����
							int index = ad.searchPhoneNum(phoneNum);
							table.changeSelection(index, 0, false, false);

							// ã�� �ε����� ���� textfield�� ��������
							int i = index;
							textField_name.setText(ad.getPerson(i).getName());
							textField_phonenum.setText(ad.getPerson(i).getPhoneNum());
							textField_address.setText(ad.getPerson(i).getAddress());
							textField_email.setText(ad.getPerson(i).getEmail());
						}
					} catch (Exception e1) { // ���� ��ϵ� ������ ���ٸ� ���â
						JOptionPane.showMessageDialog(null, "��ϵ� ��ȭ��ȣ�� �����ϴ�.", "Message", JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});

		// Modify ��ư ����
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] rows = new String[4];

				// getText�� ������ ������ �� ��������
				String editname = textField_name.getText();
				String editphoneNum = textField_phonenum.getText();
				String editaddress = textField_address.getText();
				String editemail = textField_email.getText();

				// ���� ���õ� row�� ���µ� ������ �Ϸ����ϸ�, ���â
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "������ ������ ���õ��� �ʾҽ��ϴ�.", "Message", JOptionPane.WARNING_MESSAGE);
					return;

				} else { // ������ ������ ���� �Ǿ��ٸ�
					int editIndex = table.getSelectedRow(); // ������ row ã�Ƽ� editIndex�� �ش� row�� index��ȣ �����ϱ�

					// ���� ������ �̸��̳� ��ȭ��ȣ�� ����ִٸ� ���â
					if (editname.equals("") || editphoneNum.equals("")) {
						JOptionPane.showMessageDialog(null, "�ʼ� ������ �Է����ּ���.(�̸�, ��ȭ��ȣ)", "Message",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					do { // (do-while)�̸� ��ġ�� �ʰ� ����
						try {
							if (editname.equals(ad.getPerson(editIndex).getName())) {// �̸� ���� ���ϴ°��
								break;
							} else {
								if (ad.checkName(editname) == true) {// �̸� ������ ��, ��ϵ� �̸��� ���
									JOptionPane.showMessageDialog(null, "�̹� ����� �̸��Դϴ�.", "Message",
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

					do { // (do-while)��ȭ��ȣ ��ġ�� �ʰ� ����
						try {
							if (editphoneNum.equals(ad.getPerson(editIndex).getPhoneNum())) {// ��ȭ��ȣ ���� ���ϴ°��
								break;
							} else {
								if (ad.checkPhoneNum(editphoneNum) == true) { // ��ȭ��ȣ ������ ��, ��ϵ� ��ȭ��ȣ�� ���
									JOptionPane.showMessageDialog(null, "�̹� ����� ��ȭ��ȣ�Դϴ�.", "Message",
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

					// ������ �����ϱ� ���� �ٽ� �����
					int result = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Confirm",
							JOptionPane.YES_NO_OPTION);

					// ���� �׳� ������ ������ �������� ����
					if (result == JOptionPane.CLOSED_OPTION)
						return;

					// ���� Yes�� ������ ����
					else if (result == JOptionPane.YES_OPTION) {

						Person editPerson = null;
						try {
							editPerson = ad.getPerson(editIndex);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // ������ index�� Person��ü ��������
						try {
							ad.modify(editIndex, editPerson);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // ������ ��ü ���� ����

						int rowIndex = table.getSelectedRow(); // ������ ��ü�� ���� ���̺� ����
						model.removeRow(editIndex);
						try {
							ad.delete(name);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // ������ �����

						// �Էµ� ������ �迭�� �����
						rows[0] = textField_name.getText();
						rows[1] = textField_phonenum.getText();
						rows[2] = textField_address.getText();
						rows[3] = textField_email.getText();

						// �Է��� �ؽ�Ʈ �ʵ� �� ����
						textField_name.setText("");
						textField_phonenum.setText("");
						textField_address.setText("");
						textField_email.setText("");

						model.addRow(rows); // ���� ��ģ ���� ����

						// ������ �� �ȳ���
						JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.", "Message", JOptionPane.PLAIN_MESSAGE);

						try {
							// ���� �Է¹��� ������� ��ü ����
							ad.add(new Person(editname, editphoneNum, editaddress, editemail));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else // �� ���� �ٸ��� ������ ���� �� ��
						return;
				}
			}
		});

		// Readall��ư ����
		btnReadall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				model.setNumRows(0); // ���̺� �ʱ�ȭ

				int c = 0;
				try {
					c = ad.getCount();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} // ��ü ����� ���� ���� ����

				if (c == 0) { // ��ϵ� �ּҷ��� ���ٸ� ���ٴ� ���â
					JOptionPane.showMessageDialog(null, "��ϵ� �ּҷ��� �����ϴ�.", "Message", JOptionPane.WARNING_MESSAGE);
				} else { // ��ϵ� ������ �ִٸ� c�� �ش��ϴ� ũ�⸸ŭ �ݺ��� ���� ���
					for (int i = 0; i < c; i++) { // �ּҷ� ũ�⸸ŭ �ݺ�
						String[] rows = new String[4]; // �Էµ� ���� ������ �迭�� ����
						try {
							rows[0] = ad.getPerson(i).getName();
							rows[1] = ad.getPerson(i).getPhoneNum();
							rows[2] = ad.getPerson(i).getAddress();
							rows[3] = ad.getPerson(i).getEmail();
							model.addRow(rows); // ���̺� ������ �߰�
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});

		// Finish ��ư ����
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �����ϱ� �� �ٽ� �����.
				int result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);

				// ���� �׳� ������ ������ â�� ������ ����
				if (result == JOptionPane.CLOSED_OPTION)
					return;

				// Yes�� ������ â�� ����
				else if (result == JOptionPane.YES_OPTION)
					System.exit(0);
					

				// �� ���� No��ư�� ������ â�� ������ ����
				else
					return;
			}
		});
	};
}
