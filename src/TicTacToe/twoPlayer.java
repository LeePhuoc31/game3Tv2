package TicTacToe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Cursor;
import java.awt.Toolkit;

public class twoPlayer extends JFrame implements MouseListener {

	private JPanel mainP, p1, p2, p3;
	private JButton[] arrBT;
	private JButton btnReplay;
	private JButton btnBack;
	private JButton btnUndo;
//	private JButton btnRedo;
	private JLabel lb1, lb2, lb3;

	private int[] arr;
	private int c, h, d;
	private boolean turn;
	private boolean[] arrEnd;
	private List<String> data = new ArrayList<>();
	private String dataOld;
	private int indexOld;
	// private boolean checkUndo = true;

	public twoPlayer() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(twoPlayer.class.getResource("/images/icon.png")));

		arrBT = new JButton[9];
		arr = new int[9];
		arrEnd = new boolean[9];

		taoGUI();

		setTitle("Tic Tac Toe");
		setSize(340, 430);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		try {
			readData();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void undo() {
		if (data.isEmpty()) {
		} else {
			int index = Integer.parseInt(data.get(data.size() - 1).substring(1, data.get(data.size() - 1).length()));
			indexOld = index;
			String XO = data.get(data.size() - 1);
			dataOld = XO;
			if (XO.contains("X")) {
				arrBT[index].setText("");
				turn = false;
			} else if (XO.contains("O")) {
				arrBT[index].setText("");
				turn = true;
			}
			data.remove(data.size() - 1);
		}
	}

	public void redo() {
		if (dataOld == null) {

		} else {
			if (dataOld.contains("X")) {
				arrBT[indexOld].setText("X");
				turn = true;
			} else if (dataOld.contains("O")) {
				arrBT[indexOld].setText("O");
				turn = false;
			}
			data.add(dataOld);
			dataOld = null;
		}
	}

	public void addP1() {
		mainP.add(p1 = new JPanel(), BorderLayout.CENTER);
		p1.setPreferredSize(new Dimension(315, 315));
		p1.setBackground(new Color(135, 206, 235));
		p1.setLayout(new GridLayout(3, 3, 3, 3));
		for (int i = 0; i < arrBT.length; i++) {
			arrBT[i] = new JButton();
			arrBT[i].addMouseListener(this);
			arrBT[i].setPreferredSize(new Dimension(80, 80));
			arrBT[i].setForeground(Color.red);
			arrBT[i].setBackground(Color.white);
			arrBT[i].setFocusPainted(false);
			arrBT[i].setFont(new Font("Forte", Font.BOLD, 40));
			p1.add(arrBT[i]);
		}
	}

	public void reset() {
		for (int i = 0; i < arrBT.length; i++) {
			arrBT[i].setText("");
			arr[i] = 0;
			arrEnd[i] = false;
		}
		data.clear();
		writeData();
	}

	public void taoGUI() {
		getContentPane().add(mainP = new JPanel());
		mainP.setBackground(new Color(135, 206, 235));
		addP1();
		mainP.add(p2 = new JPanel(), BorderLayout.SOUTH);
		p2.setBackground(new Color(135, 206, 235));
		p2.setPreferredSize(new Dimension(300, 20));
		p2.add(new JLabel("Player X: "));
		p2.add(lb1 = new JLabel("0"));
		lb1.setForeground(Color.RED);
		p2.add(new JLabel("Player O: "));
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
//		p3.add(btnRedo = new JButton("Redo"));
		p3.add(btnReplay = new JButton("Replay"));
		btnReplay.setFocusPainted(false);
		btnReplay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p3.setBackground(new Color(135, 206, 235));

		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setForeground(new Color(0, 0, 128));
		btnReplay.setEnabled(false);
		btnReplay.setForeground(Color.red);
		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUndo.setForeground(new Color(0, 0, 128));
		btnUndo.setForeground(Color.blue);
//		btnRedo.setFont(new Font("Tahoma", Font.BOLD, 11));
//		btnRedo.setForeground(new Color(0, 0, 128));
//		btnRedo.setForeground(Color.blue);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,
						"Do you want to save this board data for further play?", "Message", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					OP optiOp = new OP("1 Player");
					optiOp.setVisible(true);
					dispose();
				} else if (result == JOptionPane.NO_OPTION) {
					try {
						File file = new File("src\\\\TicTacToe\\\\data2P3.txt");
						if (file.delete()) {
							System.out.println(file.getName() + " is deleted!");
						} else {
							System.out.println("Delete operation is failed.");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					OP optiOp = new OP("1 Player");
					optiOp.setVisible(true);
					dispose();
				}
//				OP frame = new OP("2 Player 3x3");
//				frame.setVisible(true);
//				setVisible(false);
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
				writeData();
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

	public void writeData() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("src\\TicTacToe\\data2P3.txt"));
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
			ois = new ObjectInputStream(new FileInputStream("src\\TicTacToe\\data2P3.txt"));
			data = (List<String>) ois.readObject();
			if (data.isEmpty()) {
			} else {
				for (String d : data) {
					if (d.contains("X")) {
						int index = Integer.parseInt(d.substring(1, data.get(data.size() - 1).length()));
						arrBT[index].setText("X");
						turn = true;

					} else if (d.contains("O")) {
						int index = Integer.parseInt(d.substring(1, data.get(data.size() - 1).length()));
						arrBT[index].setForeground(new Color(0, 0, 255));
						arrBT[index].setText("O");
						turn = false;
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

	public void checkWin() {
		try {
			String b1 = arrBT[0].getText();
			String b2 = arrBT[1].getText();
			String b3 = arrBT[2].getText();
			String b4 = arrBT[3].getText();
			String b5 = arrBT[4].getText();
			String b6 = arrBT[5].getText();
			String b7 = arrBT[6].getText();
			String b8 = arrBT[7].getText();
			String b9 = arrBT[8].getText();

			// PLAYER X CODING

			if (b1 == ("X") && b2 == ("X") && b3 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			else if (b4 == ("X") && b5 == ("X") && b6 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			}

			else if (b7 == ("X") && b8 == ("X") && b9 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			} else if (b1 == ("X") && b4 == ("X") && b7 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			} else if (b2 == ("X") && b5 == ("X") && b8 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			} else if (b3 == ("X") && b6 == ("X") && b9 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			} else if (b1 == ("X") && b5 == ("X") && b9 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			else if (b3 == ("X") && b5 == ("X") && b7 == ("X")) {
				JOptionPane.showMessageDialog(this, "Player X wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				h++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			// PLAYER O CODING

			else if (b1 == ("O") && b2 == ("O") && b3 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			}

			else if (b4 == ("O") && b5 == ("O") && b6 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			else if (b7 == ("O") && b8 == ("O") && b9 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			else if (b1 == ("O") && b4 == ("O") && b7 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();
			}

			else if (b2 == ("O") && b5 == ("O") && b8 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			} else if (b3 == ("O") && b6 == ("O") && b9 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			}

			else if (b1 == ("O") && b5 == ("O") && b9 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			}

			else if (b3 == ("O") && b5 == ("O") && b7 == ("O")) {
				JOptionPane.showMessageDialog(this, "Player O wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				c++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			} else if (!b1.equals("") && !b2.equals("") && !b3.equals("") && !b4.equals("") && !b5.equals("")
					&& !b6.equals("") && !b7.equals("") && !b8.equals("") && !b9.equals("")) {
				JOptionPane.showMessageDialog(this, "Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				d++;
				count();
				end();
				btnReplay.setEnabled(true);
				btnUndo.setEnabled(false);
				turn = false;
				data.clear();
				writeData();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < arrBT.length; i++) {
			if (e.getSource() == arrBT[i]) {
				if (turn == false) {
					if (arrBT[i].getText() == "") {
						arrBT[i].setForeground(new Color(255, 0, 0));
						arrBT[i].setText("X");
						turn = true;
						checkWin();
						data.add("X" + i);
					}
				} else {
					if (arrBT[i].getText() == "") {
						arrBT[i].setForeground(new Color(0, 0, 255));
						arrBT[i].setText("O");
						turn = false;
						checkWin();
						data.add("O" + i);
					}
				}

			}
		}
		writeData();
	}

	public void end() {
		for (int i = 0; i < arrBT.length; i++) {
			for (int j = 0; j < arrBT.length; j++) {
				arrEnd[i] = true;
			}
		}
	}

	public static void main(String[] args) {
		new twoPlayer();
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
