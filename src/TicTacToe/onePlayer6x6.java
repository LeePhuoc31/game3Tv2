package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class onePlayer6x6 extends JFrame implements ActionListener {
	boolean win = false;
	String icon;
	int point1 = 0, point2 = 0;
	int start = 0;
	String str = "";
	int count;
	int countH[] = { 0, 0 };
	String text[] = { "X", "O" };
	private Color background_cl = Color.white;
	private Color number_cl[] = { Color.red, Color.blue };
	private JButton bt[] = new JButton[36];
	private int a[] = new int[36];
	private JPanel mainP, p1, p2, p3;
	private JLabel lb1, lb2, lb3;
	private int c, h, d;
	private JButton btnBack, btnReplay, btnUndo;
	private List<String> data = new ArrayList<>();
	private String dataOld;
	private int iOld;
	private int iNew;
	private String dataNew;
	private JLabel lblName;
	Timer timer;

	public onePlayer6x6(String s, int Point1, int Point2) {
		super(s);
		taoGUI();
		setTitle("Tic Tac Toe");
		setSize(340, 430);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		point1 = Point1;
		point2 = Point2;
		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					addPoint(autoPoint());
					timer.stop();
				}
			}
		});
		File f = new File("src\\TicTacToe\\data16x6.txt");

		if(f.exists() && !f.isDirectory()) {
			readData();
		}
		else {
			
		}
	}

	public void addP1() {
		mainP.add(p1 = new JPanel(), BorderLayout.CENTER);
		p1.setPreferredSize(new Dimension(315, 315));
		p1.setBackground(new Color(135, 206, 235));
		p1.setLayout(new GridLayout(6, 6, 2, 2));
		for (int i = 0; i < 36; i++) {
			bt[i] = new JButton(" ");
			p1.add(bt[i]);
			bt[i].setActionCommand(String.valueOf(i));
			bt[i].setBackground(background_cl);
			bt[i].addActionListener(this);
			bt[i].setPreferredSize(new Dimension(80, 80));
			bt[i].setForeground(Color.red);
			bt[i].setBackground(Color.white);
			bt[i].setFocusPainted(false);
			bt[i].setFont(new Font("Forte", Font.BOLD, 20));
			a[i] = 1;
		}
	}

	public void reset() {
		for (int i = 0; i < 36; i++) {
			bt[i].setText("");
		}
		data.clear();
		writeData();
	}

	public void taoGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(onePlayer6x6.class.getResource("/images/icon.png")));

		getContentPane().add(mainP = new JPanel());
		mainP.setBackground(new Color(135, 206, 235));
		addP1();

		mainP.add(p2 = new JPanel(), BorderLayout.SOUTH);
		p2.setBackground(new Color(135, 206, 235));
		p2.setPreferredSize(new Dimension(300, 20));

		lblName = new JLabel("");
		lblName.setText(toanCuc.getTen() + ":");
		p2.add(lblName);
		p2.add(lb1 = new JLabel("0"));
		lb1.setForeground(Color.RED);
		p2.add(new JLabel("Computer: "));
		p2.add(lb2 = new JLabel("0"));
		lb2.setForeground(Color.BLUE);
		p2.add(new JLabel("Draw: "));
		p2.add(lb3 = new JLabel("0"));

		mainP.add(p3 = new JPanel(), BorderLayout.WEST);
		p3.setPreferredSize(new Dimension(300, 30));
		p3.setPreferredSize(new Dimension(300, 35));
		p3.add(btnBack = new JButton(""));
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(135, 206, 235));
		btnBack.setPreferredSize(new Dimension(28, 23));
		btnBack.setIcon(new ImageIcon(onePlayer.class.getResource("/images/icons8-back-arrow-23.png")));
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setFocusPainted(false);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3.add(btnUndo = new JButton("Undo"));
		btnUndo.setFocusPainted(false);
		btnUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3.add(btnReplay = new JButton("Replay"));
		btnReplay.setFocusPainted(false);
		btnReplay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3.setBackground(new Color(135, 206, 235));

		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setForeground(new Color(0, 0, 128));
//		btnReplay.setEnabled(true);
		btnReplay.setForeground(Color.red);

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OP frame = new OP("1 Player");
				frame.setVisible(true);
				setVisible(false);
			}
		});

		btnReplay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
//				btnReplay.setEnabled(false);
				checkPoint1();
				checkPoint2();
			}
		});
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				undo();
				undo();
			}
		});
	}

	public void xoaFile() {
		File myObj = new File("src\\TicTacToe\\data16x6.txt");
		myObj.delete();
	}

	public void undo() {
		if (data.isEmpty()) {
		} else {
			iNew = iOld;
			dataNew = dataOld;
			int i = Integer.parseInt(data.get(data.size() - 1).substring(1, data.get(data.size() - 1).length()));
			iOld = i;
			String XO = data.get(data.size() - 1);
			dataOld = XO;
			if (XO.contains("X")) {
				bt[i].setText("");
				checkPoint1();
				checkPoint2();
			} else if (XO.contains("O")) {
				bt[i].setText("");
				checkPoint1();
				checkPoint2();
			}
			data.remove(data.size() - 1);
			writeData();
//			btnRedo.setEnabled(true);
		}
	}

	public void writeData() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("src\\TicTacToe\\data16x6.txt"));
			oos.writeObject(data);
			oos.flush();
//            System.out.println("Lưu thành công");
		} catch (Exception e) {
//            System.out.println("Lỗi ghi file");
		} finally {
			try {
				oos.close();
			} catch (IOException ex) {
//                System.out.println("Lỗi đóng file");
			}
		}
	}

	public void readData() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("src\\TicTacToe\\data16x6.txt"));
			data = (List<String>) ois.readObject();
			if (data.isEmpty()) {
			} else {
				for (String d : data) {
					if (d.contains("X")) {
						int index = Integer.parseInt(d.substring(1, d.length()));
						a[index] = 0;
						bt[index].setText(text[0]);
						bt[index].setForeground(number_cl[0]);

					} else if (d.contains("O")) {
						int index = Integer.parseInt(d.substring(1, d.length()));
						a[index] = 1;
						bt[index].setText(text[1]);
						bt[index].setForeground(number_cl[1]);

					}
				}
			}
		} catch (Exception e) {
//            System.out.println("Lỗi đọc file");
		} finally {
			try {
				ois.close();
			} catch (IOException ex) {
//                System.out.println("Lỗi đóng file");
			}
		}
	}

	public int autoPoint() {
		int k = checkPoint1();
		if (k != -1)
			return k;
		else {
			k = checkPoint2();
			if (k != -1)
				return k;
			else
				return creatPointRandom();
		}
	}

	public void updateMatrix() {
		for (int i = 0; i < 36; i++)
			if (bt[i].getText() == "X")
				a[i] = 0;
			else if (bt[i].getText() == "O")
				a[i] = 1;
			else
				a[i] = 2;
	}

	public void dem() {
		lb1.setText(h + "");
		lb2.setText(c + "");
		lb3.setText(d + "");
	}

	public int checkWin() {
		updateMatrix();
		if (a[0] != 2 && a[0] == a[1] && a[1] == a[2] && a[2] == a[3] && a[3] == a[4])
			return a[0];
		if (a[6] != 2 && a[6] == a[7] && a[7] == a[8] && a[8] == a[9] && a[9] == a[10])
			return a[6];
		if (a[12] != 2 && a[12] == a[13] && a[13] == a[14] && a[14] == a[15] && a[15] == a[16])
			return a[12];
		if (a[18] != 2 && a[18] == a[19] && a[19] == a[20] && a[20] == a[21] && a[21] == a[22])
			return a[18];
		if (a[24] != 2 && a[24] == a[25] && a[25] == a[26] && a[26] == a[27] && a[27] == a[28])
			return a[24];
		if (a[30] != 2 && a[30] == a[31] && a[31] == a[32] && a[32] == a[33] && a[33] == a[34])
			return a[30];

		if (a[5] != 2 && a[5] == a[4] && a[4] == a[3] && a[3] == a[2] && a[2] == a[1])
			return a[5];
		if (a[11] != 2 && a[11] == a[10] && a[10] == a[9] && a[9] == a[8] && a[8] == a[7])
			return a[11];
		if (a[17] != 2 && a[17] == a[16] && a[16] == a[15] && a[15] == a[14] && a[14] == a[13])
			return a[17];
		if (a[23] != 2 && a[23] == a[22] && a[22] == a[21] && a[21] == a[20] && a[20] == a[19])
			return a[23];
		if (a[29] != 2 && a[29] == a[28] && a[28] == a[27] && a[27] == a[26] && a[26] == a[25])
			return a[29];
		if (a[35] != 2 && a[35] == a[34] && a[34] == a[33] && a[33] == a[32] && a[32] == a[31])
			return a[35];

		if (a[0] != 2 && a[0] == a[6] && a[6] == a[12] && a[12] == a[18] && a[18] == a[24])
			return a[0];
		if (a[1] != 2 && a[1] == a[7] && a[7] == a[13] && a[13] == a[19] && a[19] == a[25])
			return a[1];
		if (a[2] != 2 && a[2] == a[8] && a[8] == a[14] && a[14] == a[20] && a[20] == a[26])
			return a[2];
		if (a[3] != 2 && a[3] == a[9] && a[9] == a[15] && a[15] == a[21] && a[21] == a[27])
			return a[3];
		if (a[4] != 2 && a[4] == a[10] && a[10] == a[16] && a[16] == a[20] && a[22] == a[28])
			return a[4];
		if (a[5] != 2 && a[5] == a[11] && a[11] == a[17] && a[17] == a[20] && a[23] == a[28])
			return a[5];

		if (a[30] != 2 && a[30] == a[24] && a[24] == a[18] && a[18] == a[12] && a[12] == a[6])
			return a[30];
		if (a[31] != 2 && a[31] == a[25] && a[25] == a[19] && a[19] == a[13] && a[13] == a[7])
			return a[31];
		if (a[32] != 2 && a[32] == a[26] && a[26] == a[20] && a[20] == a[14] && a[14] == a[8])
			return a[32];
		if (a[33] != 2 && a[33] == a[27] && a[27] == a[21] && a[21] == a[15] && a[15] == a[9])
			return a[33];
		if (a[34] != 2 && a[34] == a[28] && a[28] == a[22] && a[22] == a[16] && a[16] == a[10])
			return a[34];
		if (a[35] != 2 && a[35] == a[29] && a[29] == a[23] && a[23] == a[17] && a[17] == a[11])
			return a[35];

		if (a[0] != 2 && a[0] == a[7] && a[7] == a[14] && a[14] == a[21] && a[21] == a[28])
			return a[0];
		if (a[5] != 2 && a[5] == a[10] && a[10] == a[15] && a[15] == a[20] && a[20] == a[25])
			return a[5];

		if (a[35] != 2 && a[35] == a[28] && a[28] == a[21] && a[21] == a[14] && a[14] == a[7])
			return a[35];
		if (a[30] != 2 && a[30] == a[25] && a[25] == a[20] && a[20] == a[15] && a[15] == a[10])
			return a[30];

		for (int i = 0; i < 36; i++)
			if (a[i] == 2)
				return -1;
		return 2;

	}

	public int checkPoint1() {
		for (int i = 0; i < 36; i++)
			if (a[i] == 2) {
				a[i] = count;
				bt[i].setText(text[count]);
				if (checkWin() != -1) {
					a[i] = 2;
					bt[i].setText(" ");
					return i;
				}
				a[i] = 2;
				bt[i].setText(" ");
			}
		return -1;
	}

	public int checkPoint2() {
		for (int i = 0; i < 36; i++)
			if (a[i] == 2) {
				a[i] = 1 - count;
				bt[i].setText(text[1 - count]);
				if (checkWin() != -1) {
					a[i] = 2;
					bt[i].setText(" ");
					return i;
				}
				a[i] = 2;
				bt[i].setText(" ");
			}
		return -1;
	}

	public void addPoint(int k) {
		if (!win) {
			if (a[k] == 2) {
				a[k] = count;
				bt[k].setForeground(number_cl[count]);
				bt[k].setText(text[count]);
				data.add(text[count] + k);
				countH[count]++;
				count = 1 - count;
				writeData();
			}
			if (checkWin() != -1) {
				String mess = "";
				int cw = checkWin();
				if (cw == 2) {
					d++;
					dem();
					mess = "Draw";
					data.clear();
//					btnReplay.setEnabled(true);
					writeData();
					xoaFile();
				} else {
					if (icon != text[count]) {
						c++;
						dem();
						btnUndo.setEnabled(false);
						data.clear();
//						btnReplay.setEnabled(true);
						writeData();
						mess = "Computer Wins";
						xoaFile();
					} else {
						h++;
						dem();
						btnUndo.setEnabled(false);
						data.clear();
//						btnReplay.setEnabled(true);
						writeData();
						mess = "Player wins";
						xoaFile();
					}
				}
				win = true;
				JOptionPane.showMessageDialog(null, mess);
			}
		}
	}

	public int creatPointRandom() {
		if (a[14] == 2)
			return 14;
//		if (a[15] == 2)
//			return 15;
		if (a[20] == 2)
			return 20;
		if (a[21] == 2)
			return 21;

		int k = 0;
		k += (a[0] == 2) ? 1 : 0;
		k += (a[5] == 2) ? 1 : 0;
		k += (a[30] == 2) ? 1 : 0;
		k += (a[35] == 2) ? 1 : 0;
//		if (k > 0) {
//			int h = (int) ((k - 1) * Math.random() + 1);
//			k = 0;
//			k += (a[0] == 2) ? 1 : 0;
//			if (k == h)
//				return 0;
//			k += (a[5] == 2) ? 1 : 0;
//			if (k == h)
//				return 5;
//			k += (a[30] == 2) ? 1 : 0;
//			if (k == h)
//				return 30;
//			k += (a[35] == 2) ? 1 : 0;
//			if (k == h)
//				return 35;
//		}
		for (int i = 0; i < 36; i++)
			if (a[i] == 2)
				k++;
		int h = (int) ((k - 1) * Math.random() + 1);
		k = 0;
		for (int i = 0; i < 36; i++)
			if (a[i] == 2) {
				k++;
				if (k == h)
					return i;
			}
		return 0;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == btnReplay.getActionCommand()) {
			onePlayer6x6 k = new onePlayer6x6("", point1, point2);
			if (Math.random() >= 0.5) {
				k.timer.start();
				k.icon = "O";
			} else
				k.icon = "X";
			this.dispose();
		}
		if (!win) {
			int k = Integer.parseInt(e.getActionCommand());
			if (a[k] == 2) {
				addPoint(k);
				timer.start();
			}
		}
	}

	public static void main(String[] args) {
		onePlayer6x6 k = new onePlayer6x6("", 0, 0);
		if (Math.random() >= 0.5) {
			k.timer.start();
			k.icon = "O";
		} else
			k.icon = "X";
	}
}