package TicTacToe;

import java.awt.BorderLayout;
import java.sql.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.login.FailedLoginException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;

public class onePlayer extends JFrame implements MouseListener {

	private JPanel mainP, p1, p2, p3;
	private JButton[][] arrBT;
	private JButton btnReplay;
	private JButton btnBack;
	private JButton btnUndo;
//	private JButton btnRedo;
	private JLabel lb1, lb2, lb3;

	private int[][] arr;
	private int c, h, d;
	private boolean turn;
	private boolean[][] arrEnd;
	private boolean endGame;
	private List<String> data = new ArrayList<>();
	private String dataOld;
	private int iOld;
	private int jOld;
	private int iNew;
	private int jNew;
	private String dataNew;
	public JLabel lblName;
	private JComboBox<?> cbLevel;
	private boolean checkGame = false;

	public onePlayer() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(onePlayer.class.getResource("/images/icon.png")));

		arrBT = new JButton[3][3];
		arr = new int[3][3];
		arrEnd = new boolean[3][3];

		taoGUI();
		setTitle("Tic Tac Toe");
		setSize(340, 430);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		lblName.setText(toanCuc.getTen() + ":");
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "SELECT * FROM DataGame WHERE ID = " + toanCuc.getId();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("LoaiCo").equals("X")) {
					int i = rs.getInt("row");
					int j = rs.getInt("col");
					arrBT[i][j].setText("X");
					arr[i][j] = 1;
					arrBT[i][j].setForeground(Color.red);
					arrEnd[i][j] = true;
					turn = true;

				} else if (rs.getString("LoaiCo").equals("O")) {
					int i = rs.getInt("row");
					int j = rs.getInt("col");
					arrBT[i][j].setForeground(new Color(0, 0, 255));
					arrBT[i][j].setText("O");
					arr[i][j] = 2;
					arrEnd[i][j] = true;
					turn = false;
				}
			}
//			System.out.println(checkGame);
			con.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
		loadPoint();
	}

	public void addP1() {
		mainP.add(p1 = new JPanel(), BorderLayout.CENTER);
		p1.setBackground(new Color(135, 206, 235));
		p1.setPreferredSize(new Dimension(315, 315));
		p1.setLayout(new GridLayout(3, 3, 3, 3));
		for (int i = 0; i < arrBT.length; i++) {
			for (int j = 0; j < arrBT[i].length; j++) {
				arrBT[i][j] = new JButton();
				arrBT[i][j].addMouseListener(this);
				arrBT[i][j].setPreferredSize(new Dimension(80, 80));
				arrBT[i][j].setForeground(Color.red);
				arrBT[i][j].setBackground(Color.white);
				arrBT[i][j].setFocusPainted(false);
				arrBT[i][j].setFont(new Font("Forte", Font.BOLD, 40));
				p1.add(arrBT[i][j]);
			}

		}
	}

	public void undo() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "DELETE FROM DataGame WHERE IB = (SELECT MAX(IB) FROM DataGame WHERE ID = ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, toanCuc.getId());
			st.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "DELETE FROM DataGame WHERE IB = (SELECT MAX(IB) FROM DataGame WHERE ID = ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, toanCuc.getId());
			st.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		reset();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "SELECT * FROM DataGame WHERE ID = " + toanCuc.getId();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("LoaiCo").equals("X")) {
					int i = rs.getInt("row");
					int j = rs.getInt("col");
					arrBT[i][j].setText("X");
					arr[i][j] = 1;
					arrBT[i][j].setForeground(Color.red);
					arrEnd[i][j] = true;
					turn = true;

				} else if (rs.getString("LoaiCo").equals("O")) {
					int i = rs.getInt("row");
					int j = rs.getInt("col");
					arrBT[i][j].setForeground(new Color(0, 0, 255));
					arrBT[i][j].setText("O");
					arr[i][j] = 2;
					arrEnd[i][j] = true;
					turn = false;
				}
			}
//			System.out.println(checkGame);
			con.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public void loadPoint() {
		int a = 10;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "SELECT * FROM Points WHERE ID = " + toanCuc.getId();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				h = rs.getInt("hPoints");
				c = rs.getInt("cPoints");
				d = rs.getInt("dPoints");
				count();
				a = 11;
			}
//			System.out.println(checkGame);
			con.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
		if (a == 10) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
				Connection con = DriverManager.getConnection(url, "sa", "123456");
				String sql = "INSERT INTO Points (hPoints, cPoints, dPoints, ID) " + "VALUES (?,?,?,?);";
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, h);
				st.setInt(2, c);
				st.setInt(3, d);
				st.setInt(4, toanCuc.getId());
				st.executeUpdate();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public void redo() {
		if (dataOld == null) {

		} else {
			if (dataOld.contains("X")) {
				arrBT[iOld][jOld].setText("X");
				turn = true;
				arrEnd[iOld][jOld] = true;
				arr[iOld][jOld] = 1;
				if (dataNew.contains("O")) {
					arrBT[iNew][jNew].setText("O");
					arrEnd[iNew][iNew] = true;
					arr[iNew][iNew] = 2;
					turn = false;
				}

			} else if (dataOld.contains("O")) {
				arrBT[iOld][jOld].setText("O");
				arrEnd[iOld][jOld] = true;
				arr[iOld][jOld] = 2;
				turn = false;
			}
			data.add(dataOld);
			data.add(dataNew);
			dataOld = null;
//			writeData();
		}
	}

	public void reset() {
		for (int i = 0; i < arrBT.length; i++) {
			for (int j = 0; j < arrBT[i].length; j++) {
				arrBT[i][j].setText("");
				arr[i][j] = 0;
				arrEnd[i][j] = false;
				turn = false;
				endGame = false;
			}
		}
//		data.clear();
//		writeData();
	}

	public void taoGUI() {
		getContentPane().add(mainP = new JPanel());
		FlowLayout flowLayout = (FlowLayout) mainP.getLayout();
		mainP.setBackground(new Color(135, 206, 235));
		addP1();
		mainP.add(p2 = new JPanel(), BorderLayout.SOUTH);
		FlowLayout flowLayout_1 = (FlowLayout) p2.getLayout();
		p2.setBackground(new Color(135, 206, 235));
		p2.setPreferredSize(new Dimension(300, 20));

		lblName = new JLabel("");
		lblName.setText(toanCuc.getTen() + ":");
//		p2.add(new JLabel("Player "));
		p2.add(lblName);
		p2.add(lb1 = new JLabel("0"));
		lb1.setForeground(Color.RED);
		p2.add(new JLabel("Computer: "));
		p2.add(lb2 = new JLabel("0"));
		lb2.setForeground(Color.BLUE);
		p2.add(new JLabel("Draw: "));
		p2.add(lb3 = new JLabel("0"));

		mainP.add(p3 = new JPanel(), BorderLayout.WEST);
		FlowLayout flowLayout_2 = (FlowLayout) p3.getLayout();
		p3.setPreferredSize(new Dimension(300, 35));
		p3.add(btnBack = new JButton(""));
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(135, 206, 235));
		btnBack.setPreferredSize(new Dimension(28, 23));
		btnBack.setIcon(new ImageIcon(onePlayer.class.getResource("/images/icons8-back-arrow-23.png")));
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setFocusPainted(false);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbLevel = new JComboBox();
		cbLevel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbLevel.setModel(new DefaultComboBoxModel(new String[] { "Normal", "Easy" }));
		p3.add(cbLevel);
		p3.add(btnUndo = new JButton("Undo"));
		btnUndo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUndo.setFocusPainted(false);
		btnUndo.setMinimumSize(new Dimension(65, 23));
		btnUndo.setMaximumSize(new Dimension(65, 23));
		btnUndo.setForeground(Color.BLUE);
		btnUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		p3.add(btnRedo = new JButton("REDO"));
		p3.add(btnReplay = new JButton("Replay"));
		btnReplay.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReplay.setFocusPainted(false);
		btnReplay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3.setBackground(new Color(135, 206, 235));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setForeground(new Color(0, 0, 128));
		btnReplay.setEnabled(false);
		btnReplay.setForeground(Color.red);

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OP optinOp = new OP("1 Player");
				optinOp.setVisible(true);
				dispose();
			}
		});
		btnReplay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				btnReplay.setEnabled(false);
				btnUndo.setEnabled(true);
			}
		});
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				undo();
//				writeData();
			}
		});
//		btnRedo.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				redo();
//				writeData();
//			}
//		});
	}

	public void count() {
		lb1.setText(h + "");
		lb2.setText(c + "");
		lb3.setText(d + "");
	}

	private void updatePoints() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
			String sql = "UPDATE Points SET hPoints = " + h + ", cPoints = " + c + ", dPoints = " + d + " WHERE ID = "
					+ toanCuc.getId();
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			con.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

//hàm kiểm tra kết quả 
	public int checkResult(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			int a = arr[i][0];
			if (a == arr[i][1] && arr[i][1] == arr[i][2]) {
				if (a == 1) {
					return -10;
				} else if (a == 2) {
					return 10;
				}
			}
		}
		for (int i = 0; i < arr.length; i++) {
			int a = arr[0][i];
			if (a == arr[1][i] && arr[1][i] == arr[2][i]) {
				if (a == 1) {
					return -10;
				} else if (a == 2) {
					return 10;
				}
			}
		}

		if (arr[0][0] == arr[1][1] && arr[0][0] == arr[2][2]) {
			if (arr[0][0] == 1) {
				return -10;
			} else if (arr[0][0] == 2) {
				return 10;
			}
		}

		if (arr[2][0] == arr[1][1] && arr[2][0] == arr[0][2]) {
			if (arr[2][0] == 1) {
				return -10;
			} else if (arr[2][0] == 2) {
				return 10;
			}
		}
		return 0;
	}

	public boolean checkMove(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public int miniMax(int[][] arr, boolean isTurn) {
		int s = checkResult(arr);
		if (s == 10)
			return s;
		if (s == -10)
			return s;
		if (checkMove(arr) == false)
			return 0;
		if (isTurn == true) {
			int best = -100;
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 0) {
						arr[i][j] = 2;
						best = Math.max(best, miniMax(arr, !isTurn));
						arr[i][j] = 0;
					}
				}
			}
			return best;
		} else {
			int best = 100;
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 0) {
						arr[i][j] = 1;
						best = Math.min(best, miniMax(arr, !isTurn));
						arr[i][j] = 0;
					}
				}
			}
			return best;
		}
	}

	public int miniMaxEz(int[][] arr, boolean isTurn) {
		int s = checkResult(arr);
		if (s == 10)
			return s;
		if (s == -10)
			return s;
		if (checkMove(arr) == false)
			return 0;
		if (isTurn == true) {
			int best = -100;
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 0) {
						arr[i][j] = 2;
						best = Math.min(best, miniMaxEz(arr, !isTurn));
						arr[i][j] = 0;
					}
				}
			}
			return best;
		} else {
			int best = 100;
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 0) {
						arr[i][j] = 1;
						best = Math.max(best, miniMaxEz(arr, !isTurn));
						arr[i][j] = 0;
					}
				}
			}
			return best;
		}
	}

	public Move bestMove(int[][] arr, boolean turn) {
		int best = -100;
		Move result = new Move(-1, -1);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] == 0) {
					arr[i][j] = 2;
					int score = miniMax(arr, !turn);
					arr[i][j] = 0;
					if (score > best) {
						best = score;
						result.setX(i);
						result.setY(j);
					}
				}
			}
		}
		return result;
	}

	public Move badMove(int[][] arr, boolean turn) {
		int best = -100;
		Move result = new Move(-1, -1);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] == 0) {
					arr[i][j] = 2;
					int score = miniMaxEz(arr, !turn);
					arr[i][j] = 0;
					if (score > best) {
						best = score;
						result.setX(i);
						result.setY(j);
					}
				}
			}
		}
		return result;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < arrBT.length; i++) {
			for (int j = 0; j < arrBT[i].length; j++) {
				if (e.getButton() == 1 && e.getSource() == arrBT[i][j] && turn == false && arrEnd[i][j] == false) {
					arrBT[i][j].setText("X");
					arr[i][j] = 1;
					arrBT[i][j].setForeground(Color.red);
					arrEnd[i][j] = true;
					turn = true;
					try {
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
						Connection con = DriverManager.getConnection(url, "sa", "123456");
						String sql = "INSERT INTO DataGame (row, col, modeG, LoaiCo, ID) " + "VALUES (?,?,?,?,?);";
						PreparedStatement st = con.prepareStatement(sql);
						st.setInt(1, i);
						st.setInt(2, j);
						st.setString(3, cbLevel.getSelectedItem().toString());
						st.setString(4, "X");
						st.setInt(5, toanCuc.getId());
						st.executeUpdate();
						con.close();
					} catch (Exception ex) {
						System.out.println(ex);
					}

				}

//				else if(e.getButton()==1 && e.getSource()== arrBT[i][j] && turn == true && arrEnd[i][j] == false ) {
//					arrBT[i][j].setText("O");
//					arr[i][j] = 1;
//					arrEnd[i][j] = true;
//					turn = false;
//				}
			}
		}
		if (turn == true && checkMove(arr) == true) {
			if (cbLevel.getSelectedItem().equals("Easy")) {
				Move p = badMove(arr, turn);
				int i = p.getX();
				int j = p.getY();
				arrBT[i][j].setText("O");
				arrBT[i][j].setForeground(Color.blue);
				arrEnd[i][j] = true;
				arr[i][j] = 2;
				turn = false;
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "INSERT INTO DataGame (row, col, modeG, LoaiCo, ID) " + "VALUES (?,?,?,?,?);";
					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, i);
					st.setInt(2, j);
					st.setString(3, cbLevel.getSelectedItem().toString());
					st.setString(4, "O");
					st.setInt(5, toanCuc.getId());
					st.executeUpdate();
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
				// clip46:14-https://www.youtube.com/watch?v=1aXgp89WWIc&t=2187s
			} else {
				Move p = bestMove(arr, turn);
				int i = p.getX();
				int j = p.getY();
				arrBT[i][j].setText("O");
				arrBT[i][j].setForeground(Color.blue);
				arrEnd[i][j] = true;
				arr[i][j] = 2;
				turn = false;
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "INSERT INTO DataGame (row, col, modeG, LoaiCo, ID) " + "VALUES (?,?,?,?,?);";
					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, i);
					st.setInt(2, j);
					st.setString(3, cbLevel.getSelectedItem().toString());
					st.setString(4, "O");
					st.setInt(5, toanCuc.getId());
					st.executeUpdate();
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
				// clip46:14-https://www.youtube.com/watch?v=1aXgp89WWIc&t=2187s
			}
		}
		int check = checkResult(arr);
		if (check == 10 && endGame == false) {
			c++;
			count();
			JOptionPane.showMessageDialog(this, "Computer Wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			end();
			endGame = true;
			btnReplay.setEnabled(true);
			btnUndo.setEnabled(false);
			updatePoints();
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
				Connection con = DriverManager.getConnection(url, "sa", "123456");
				String sql = "DELETE FROM DataGame WHERE ID = ?";
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, toanCuc.getId());
				st.executeUpdate();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else if (check == -10 && endGame == false) {
			h++;
			count();
			JOptionPane.showMessageDialog(this, "Player wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			end();
			endGame = true;
			btnReplay.setEnabled(true);
			btnUndo.setEnabled(false);
			updatePoints();
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
				Connection con = DriverManager.getConnection(url, "sa", "123456");
				String sql = "DELETE FROM DataGame WHERE ID = ?";
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, toanCuc.getId());
				st.executeUpdate();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
				Connection con = DriverManager.getConnection(url, "sa", "123456");
				String sql = "SELECT ID FROM Ranks WHERE ID = " + toanCuc.getId();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					checkGame = true;
				}
//				System.out.println(checkGame);
				con.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
			if (checkGame == true) {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "UPDATE Ranks SET Points = " + h + "WHERE ID = " + toanCuc.getId();
					Statement st = con.createStatement();
					st.executeUpdate(sql);
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			} else if (checkGame == false) {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "INSERT INTO Ranks VALUES(?,?)";
					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, toanCuc.getId());
					st.setInt(2, h);
					st.executeUpdate();
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
//			writeData();
		}
		if (endGame == false) {
			if (checkMove(arr) == false) {
				d++;
				count();
				JOptionPane.showMessageDialog(this, "Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				endGame = true;
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				updatePoints();
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
					Connection con = DriverManager.getConnection(url, "sa", "123456");
					String sql = "DELETE FROM DataGame WHERE ID = ?";
					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, toanCuc.getId());
					st.executeUpdate();
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
//		for (int i = 0; i < lcX.size(); i += 2) {
//			String notifications = "";
//			notifications += lcX.get(i).toString();
//			notifications += lcX.get(i + 1).toString();
//			JOptionPane.showMessageDialog(this, notifications);
//		}
//		for (int i = 0; i < lcO.size(); i += 2) {
//			String notifications = "";
//			notifications += lcO.get(i).toString();
//			notifications += lcO.get(i + 1).toString();
//			JOptionPane.showMessageDialog(this, notifications);
//		}
//		writeData();

	}

//	public void writeData() {
//		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
//			Connection con = DriverManager.getConnection(url, "sa", "123456");
//			String sql = "INSERT INTO DataGame VALUES(?,?,?,?,?)";
//			PreparedStatement st = con.prepareStatement(sql);
//			st.setInt(1, i);
//			st.setInt(2, j);
//			st.setString(3, cbLevel.getSelectedItem().toString());
//			st.setInt(4, "O");
//			st.setInt(5, toanCuc.getId());
//			st.executeUpdate();
//			con.close();
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
//	}

//	public void readData() {
//		ObjectInputStream ois = null;
//		try {
//			ois = new ObjectInputStream(new FileInputStream("src\\TicTacToe\\data1P3.txt"));
//			data = (List<String>) ois.readObject();
//			if (data.isEmpty()) {
//			} else {
//				for (String d : data) {
//					if (d.contains("X")) {
//						int i = Integer.parseInt(d.substring(1, 2));
//						int j = Integer.parseInt(d.substring(2, d.length()));
//						arrBT[i][j].setText("X");
//						arr[i][j] = 1;
//						arrBT[i][j].setForeground(Color.red);
//						arrEnd[i][j] = true;
//						turn = true;
//
//					} else if (d.contains("O")) {
//						int i = Integer.parseInt(d.substring(1, 2));
//						int j = Integer.parseInt(d.substring(2, d.length()));
//						arrBT[i][j].setForeground(new Color(0, 0, 255));
//						arrBT[i][j].setText("O");
//						arr[i][j] = 2;
//						arrEnd[i][j] = true;
//						turn = false;
//					}
//				}
//			}
//		} catch (Exception e) {
////            System.out.println("Lỗi đọc file");
//		} finally {
//			try {
//				ois.close();
//			} catch (IOException ex) {
////                System.out.println("Lỗi đóng file");
//			}
//		}
//	}

	public void end() {
		for (int i = 0; i < arrBT.length; i++) {
			for (int j = 0; j < arrBT[i].length; j++) {
				arrEnd[i][j] = true;
			}
		}
	}

	public static void main(String[] args) {
		new onePlayer();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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