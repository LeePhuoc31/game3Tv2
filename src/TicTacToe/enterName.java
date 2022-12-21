package TicTacToe;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class enterName extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtID;
	private int id = 1;
	private JButton btnOk;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enterName frame = new enterName();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadID() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "SELECT ID FROM InfoPlayer WHERE ID =(SELECT MAX(ID) FROM InfoPlayer)";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt(1) + 1;

			}
			con.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		txtID.setText(String.valueOf(id));
		toanCuc.setId(id);
	}

	/**
	 * Create the frame.
	 */
	public enterName() {
		setTitle("Tic Tac Toe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(enterName.class.getResource("/images/icon.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 330, 191);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOk.doClick();
				}
			}
		});
		txtName.setBounds(143, 90, 132, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Enter Your Name");
		lblName.setForeground(new Color(0, 0, 255));
		lblName.setFont(new Font("Forte", Font.PLAIN, 12));
		lblName.setBounds(44, 90, 89, 20);
		contentPane.add(lblName);
		btnOk = new JButton("OK");
		btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toanCuc.setTen(txtName.getText());
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "INSERT INTO InfoPlayer VALUES(?)";
					PreparedStatement st = con.prepareStatement(sql);
					st.setString(1, txtName.getText());
					st.executeUpdate();
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
				if (txtName.getText().equals("")) {
					JOptionPane.showMessageDialog(btnOk, "Please Enter Your Name!!!");
					txtName.requestFocus();
				} else {
					dispose();
					OP frame = new OP("1 Player");
					frame.setVisible(true);
				}
			}
		});
		btnOk.setForeground(new Color(255, 0, 0));
		btnOk.setFont(new Font("Forte", Font.PLAIN, 11));
		btnOk.setBounds(136, 121, 89, 23);
		contentPane.add(btnOk);

		JTextArea txtrNote = new JTextArea();
		txtrNote.setForeground(new Color(0, 0, 128));
		txtrNote.setTabSize(3);
		txtrNote.setWrapStyleWord(true);
		txtrNote.setLineWrap(true);
		txtrNote.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtrNote.setBackground(new Color(135, 206, 250));
		txtrNote.setEditable(false);
		txtrNote.setFont(new Font("Gadugi", Font.PLAIN, 14));
		txtrNote.setText("Note: This ID will be used so that you \r\ncan continue playing the game next time.");
		txtrNote.setBounds(30, 11, 265, 46);
		contentPane.add(txtrNote);

		JLabel lblid = new JLabel("Your ID");
		lblid.setForeground(Color.BLUE);
		lblid.setFont(new Font("Forte", Font.PLAIN, 12));
		lblid.setBounds(44, 59, 89, 20);
		contentPane.add(lblid);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(143, 59, 132, 20);
		contentPane.add(txtID);
		
		btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inGame in = new inGame();
				in.setVisible(true);
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(enterName.class.getResource("/images/icons8-back-arrow-23.png")));
		btnBack.setPreferredSize(new Dimension(28, 23));
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setForeground(new Color(0, 0, 128));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setFocusPainted(false);
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(135, 206, 250));
		btnBack.setBounds(86, 121, 28, 23);
		contentPane.add(btnBack);
		txtName.requestFocus();
		loadID();

	}
}
