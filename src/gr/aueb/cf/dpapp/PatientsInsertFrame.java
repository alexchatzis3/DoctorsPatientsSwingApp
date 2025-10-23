package gr.aueb.cf.dpapp;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.dpapp.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PatientsInsertFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JLabel errorFirstname;
	private JLabel errorLastname;


	
	/**
	 * Create the frame.
	 */
	public PatientsInsertFrame() {
		setTitle("Insert New Patient");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(PatientsInsertFrame.class.getResource("/resources/medical.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 10, 416, 141);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setBounds(21, 34, 51, 21);
		panel.add(firstnameLabel);
		firstnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstnameLabel.setForeground(new Color(0, 0, 255));
		firstnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname;
				
				inputFirstname = firstnameText.getText().trim();
				
				if (inputFirstname.equals("")) {
					errorFirstname.setText("Firstname is required");
				}
				
				if (!inputFirstname.equals("")) {
					errorFirstname.setText("");
				}
			}
		});
		firstnameText.setBounds(82, 35, 212, 19);
		panel.add(firstnameText);
		firstnameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstnameText.setColumns(10);
		
		JLabel lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setBounds(21, 81, 51, 21);
		panel.add(lastnameLabel);
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLabel.setForeground(Color.BLUE);
		lastnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				String inputLastname;
				inputLastname = lastnameText.getText().trim();
				
				if (inputLastname.equals("")) {
					errorLastname.setText("Lastname is required");
				}
				
				if (!inputLastname.equals("")) {
					errorLastname.setText("");
				}
			}
		});
		lastnameText.setBounds(82, 83, 212, 19);
		panel.add(lastnameText);
		lastnameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastnameText.setColumns(10);
		
		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorFirstname.setBounds(82, 52, 212, 19);
		panel.add(errorFirstname);
		
		errorLastname = new JLabel("");
		errorLastname.setForeground(Color.RED);
		errorLastname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorLastname.setBounds(82, 100, 212, 19);
		panel.add(errorLastname);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Data binding
				String inputFirstname = firstnameText.getText().trim();
				String inputLastname = lastnameText.getText().trim();
				
				String sql = "INSERT INTO patients (firstname, lastname) VALUES (?, ?)";

				
				if (inputFirstname.equals("")) {
					errorFirstname.setText("Firstname is required");
				}
				
				if (!inputFirstname.equals("")) {
					errorFirstname.setText("");
				}
				
				if (inputLastname.equals("")) {
					errorLastname.setText("Lastname is required");
				}
				
				if (!inputLastname.equals("")) {
					errorLastname.setText("");
				}
				
				if (inputFirstname.equals("") || inputLastname.equals("")) {
					return;
				}
				
				
				try(Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql);) {
					ps.setString(1, inputFirstname);
					ps.setString(2, inputLastname);
		
					int n = ps.executeUpdate();
					JOptionPane.showMessageDialog(null, n + " record(s) inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
					
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Insertion Error", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		insertBtn.setForeground(new Color(0, 0, 255));
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		insertBtn.setBounds(192, 203, 104, 33);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getPatientsMenuFrame().setEnabled(true);
				Main.getPatientsInsertFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		closeBtn.setBounds(322, 203, 104, 33);
		contentPane.add(closeBtn);

	}

}
