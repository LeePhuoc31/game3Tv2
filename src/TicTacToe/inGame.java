package TicTacToe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Cursor;

public class inGame extends JFrame implements MouseListener {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inGame frame = new inGame();
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
	public inGame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(inGame.class.getResource("/images/icon.png")));
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 430);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btn1P = new JButton("1 Player");
		btn1P.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1P.setFocusPainted(false);
		btn1P.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				enterName eName = new enterName();
				eName.setVisible(true);
//				OP frame = new OP("1 Player");
//				frame.setVisible(true);
			}
		});
		btn1P.setForeground(Color.BLUE);
		btn1P.setFont(new Font("Forte", Font.PLAIN, 18));
		btn1P.setBounds(103, 212, 124, 39);
		contentPane.add(btn1P);

		JButton btn2P = new JButton("2 Player");
		btn2P.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2P.setFocusPainted(false);
		btn2P.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
//				new twoPlayer();
				OP frame = new OP("2 Player");
				frame.setVisible(true);
			}
		});
		btn2P.setForeground(Color.BLUE);
		btn2P.setFont(new Font("Forte", Font.PLAIN, 18));
		btn2P.setBounds(103, 262, 124, 39);
		contentPane.add(btn2P);

		JButton btnRank = new JButton("LeaderBoard");
		btnRank.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRank.setFocusPainted(false);
		btnRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rank frame = new rank();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnRank.setForeground(Color.RED);
		btnRank.setFont(new Font("Forte", Font.PLAIN, 15));
		btnRank.setBounds(103, 312, 124, 39);
		contentPane.add(btnRank);

		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon(inGame.class.getResource("/images/title.png")));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.RED);
		lblTitle.setFont(new Font("Forte", Font.BOLD | Font.ITALIC, 50));
		lblTitle.setBounds(10, 62, 306, 79);
		contentPane.add(lblTitle);

		JLabel lblO = new JLabel("");
		lblO.setHorizontalAlignment(SwingConstants.CENTER);
		lblO.setIcon(new ImageIcon(inGame.class.getResource("/images/o.png")));
		lblO.setBounds(0, 290, 93, 101);
		contentPane.add(lblO);

		JLabel lblx = new JLabel("");
		lblx.setHorizontalAlignment(SwingConstants.CENTER);
		lblx.setIcon(new ImageIcon(inGame.class.getResource("/images/x.png")));
		lblx.setBounds(15, 0, 78, 79);
		contentPane.add(lblx);

		JLabel lblX = new JLabel("");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setIcon(new ImageIcon(inGame.class.getResource("/images/x2.png")));
		lblX.setBounds(243, 318, 93, 101);
		contentPane.add(lblX);

		JLabel lblo = new JLabel("");
		lblo.setHorizontalAlignment(SwingConstants.CENTER);
		lblo.setIcon(new ImageIcon(inGame.class.getResource("/images/o2.png")));
		lblo.setBounds(238, 0, 78, 68);
		contentPane.add(lblo);

//		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
//			Connection con = DriverManager.getConnection(url, "sa", "123456");
//			String sql = "SELECT TOP 1 * FROM InfoPlayer ORDER BY Id desc";
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery(sql);
//			while (rs.next()) {
//				toanCuc.setTen(rs.getString(2));
//			}
//			con.close();
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
		JButton btnConti = new JButton("Continue");
		btnConti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterID etID = new enterID();
				etID.setVisible(true);
				dispose();
			}
		});
		btnConti.setForeground(Color.ORANGE);
		btnConti.setFont(new Font("Forte", Font.PLAIN, 18));
		btnConti.setFocusPainted(false);
		btnConti.setBounds(103, 162, 124, 39);
		contentPane.add(btnConti);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
