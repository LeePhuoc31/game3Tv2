package TicTacToe;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class rank extends JFrame {

	private JPanel contentPane;
	private JTable tblRank;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					rank frame = new rank();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void load() {
		int i = 0;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;database=TicTacToe;trustServerCertificate=true;";
			Connection con = DriverManager.getConnection(url, "sa", "123456");
//			System.out.println("Kết nối CSDL thành công!!!");
			String sql = "SELECT Name, Points FROM Ranks INNER JOIN InfoPlayer ON Ranks.ID = InfoPlayer.ID\r\n"
					+ "ORDER BY Points desc";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Object[] cols = { "Ranks", "Player", "Points" };
			DefaultTableModel model = (DefaultTableModel) tblRank.getModel();
			model.setColumnIdentifiers(cols);
			while (rs.next()) {
				i++;
				String name = rs.getString("Name");
				int points = rs.getInt("Points");

				model.addRow(new Object[] { i, name, points * 10 });
			}
			st.close();
			con.close();
		} catch (Exception ex) {
//			System.out.println("Kết nối CSDL thất bại!!!");
			System.out.println(ex);
		}
	}

	/**
	 * Create the frame.
	 */
	public rank() {
		setTitle("Tic Tac Toe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(rank.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 430);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(135, 206, 250));
		scrollPane.setBounds(36, 62, 249, 255);
		contentPane.add(scrollPane);

		tblRank = new JTable();
		tblRank.setEnabled(false);
		tblRank.setGridColor(new Color(135, 206, 250));
		tblRank.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		scrollPane.setViewportView(tblRank);
		load();

		JLabel lblNewLabel = new JLabel("Leader Board");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Forte", Font.ITALIC, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 306, 59);
		contentPane.add(lblNewLabel);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inGame frame = new inGame();
				frame.setVisible(true);
				dispose();
			}
		});
		btnBack.setForeground(Color.RED);
		btnBack.setFont(new Font("Forte", Font.PLAIN, 18));
		btnBack.setFocusPainted(false);
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(135, 206, 250));
		btnBack.setBounds(95, 331, 124, 39);
		contentPane.add(btnBack);

	}
}
