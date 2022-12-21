package TicTacToe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class enterID extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private int check = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enterID frame = new enterID();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public enterID() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(enterID.class.getResource("/images/icon.png")));
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 191);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtID.getText().equals("")) {
					JOptionPane.showMessageDialog(btnOk, "Please Enter Your ID!!!");
					txtID.requestFocus();
					return;
				} else {
				}
				try {
					toanCuc.setId(Integer.parseInt(txtID.getText().trim()));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "SELECT * FROM InfoPlayer WHERE ID = " + toanCuc.getId();
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
					while (rs.next()) {
						if (!rs.getString("ID").equals("")) {
							check = 11;
							toanCuc.setTen(rs.getString("Name"));
						}
					}
//					System.out.println(checkGame);
					con.close();
				} catch (Exception ee) {
					System.out.println(ee);
				}

				if (check == 11) {
					dispose();
					onePlayer oP = new onePlayer();
					oP.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(btnOk, "ID not found!!!");
					txtID.requestFocus();
				}
			}
		});
		btnOk.setForeground(Color.RED);
		btnOk.setFont(new Font("Forte", Font.PLAIN, 11));
		btnOk.setBounds(138, 116, 89, 23);
		contentPane.add(btnOk);

		JLabel lblid = new JLabel("Your ID");
		lblid.setForeground(Color.BLUE);
		lblid.setFont(new Font("Forte", Font.PLAIN, 12));
		lblid.setBounds(66, 72, 53, 20);
		contentPane.add(lblid);

		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOk.doClick();
				}
			}
		});
		txtID.setColumns(10);
		txtID.setBounds(129, 71, 132, 20);
		contentPane.add(txtID);
		txtID.requestFocus();

		JTextArea txtrNote = new JTextArea();
		txtrNote.setWrapStyleWord(true);
		txtrNote.setText("Note: This is the ID that you were given \r\nwhen you started playing in 1Player mode.");
		txtrNote.setTabSize(3);
		txtrNote.setLineWrap(true);
		txtrNote.setForeground(new Color(0, 0, 128));
		txtrNote.setFont(new Font("Gadugi", Font.PLAIN, 14));
		txtrNote.setEditable(false);
		txtrNote.setBackground(new Color(135, 206, 250));
		txtrNote.setBounds(30, 11, 265, 46);
		contentPane.add(txtrNote);

		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inGame in = new inGame();
				in.setVisible(true);
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(enterID.class.getResource("/images/icons8-back-arrow-23.png")));
		btnBack.setPreferredSize(new Dimension(28, 23));
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setForeground(new Color(0, 0, 128));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setFocusPainted(false);
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(135, 206, 250));
		btnBack.setBounds(93, 116, 28, 23);
		contentPane.add(btnBack);

	}
}
