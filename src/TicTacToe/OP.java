package TicTacToe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class OP extends JFrame implements MouseListener {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OP frame = new OP("");
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
	public OP(String s) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(onePlayer.class.getResource("/images/icon.png")));
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 430);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		setResizable(false);
		setLocationRelativeTo(null);

		JButton btn3X3 = new JButton("3 x 3");
		btn3X3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn3X3.setBorder(null);
		btn3X3.setBackground(new Color(135, 206, 235));
		btn3X3.setIcon(new ImageIcon(OP.class.getResource("/images/3x3.png")));
		btn3X3.setHorizontalTextPosition(SwingConstants.RIGHT);
		btn3X3.setFocusPainted(false);
		btn3X3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (s.equals("1 Player")) {
					new onePlayer();
				} else {
					new twoPlayer();
				}
			}
		});
		btn3X3.setForeground(new Color(0, 0, 128));
		btn3X3.setFont(new Font("Forte", Font.PLAIN, 18));
		btn3X3.setBounds(82, 97, 158, 94);
		contentPane.add(btn3X3);

		JButton btn6X6 = new JButton("6 x 6");
		btn6X6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn6X6.setBorder(null);
		btn6X6.setBackground(new Color(135, 206, 235));
		btn6X6.setIcon(new ImageIcon(OP.class.getResource("/images/6x6.png")));
		btn6X6.setHorizontalTextPosition(SwingConstants.RIGHT);
		btn6X6.setFocusPainted(false);
		btn6X6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (s.equals("1 Player")) {
					onePlayer6x6 k = new onePlayer6x6("",0,0);
					if (Math.random() >= 0.5) {
						k.timer.start();
						k.icon = "O";
					} else {
						k.icon = "X";
					}
				} else {
					new twoPlayer6x6();
				}
				dispose();
			}
		});
		btn6X6.setForeground(new Color(0, 0, 128));
		btn6X6.setFont(new Font("Forte", Font.PLAIN, 18));
		btn6X6.setBounds(82, 210, 158, 100);
		contentPane.add(btn6X6);

		JButton btnBack = new JButton("Back");
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.setBackground(new Color(135, 206, 235));
		btnBack.setBorder(null);
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inGame frame = new inGame();
				frame.setVisible(true);
				dispose();
			}
		});
		btnBack.setForeground(new Color(255, 0, 0));
		btnBack.setFont(new Font("Forte", Font.PLAIN, 18));
		btnBack.setBounds(104, 333, 124, 39);
		contentPane.add(btnBack);

		JLabel lblNewLabel = new JLabel("Game Mode");
		lblNewLabel.setAutoscrolls(true);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Forte", Font.ITALIC, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 316, 75);
		contentPane.add(lblNewLabel);
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
