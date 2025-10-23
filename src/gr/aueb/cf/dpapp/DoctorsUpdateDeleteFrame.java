package gr.aueb.cf.dpapp;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import gr.aueb.cf.dpapp.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DoctorsUpdateDeleteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable doctorsTable;
	private DefaultTableModel model = new DefaultTableModel();
	private JLabel lastnameSearchLabel;
	private JTextField lastnameSearchText;
	private JButton btnSearch;
	private JLabel idLabel;
	private JTextField idText;
	private JLabel firstnameLabel;
	private JTextField firstnameText;
	private JLabel lastnameLabel;
	private JTextField lastnameText;
	private JPanel panel;
	private JLabel errorFirstname;
	private JLabel errorLastname;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton closeBtn;
	
	/**
	 * Create the frame.
	 */
	public DoctorsUpdateDeleteFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				buildTable(); // initial rendering
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
			@Override
			public void windowActivated(WindowEvent e) {
				buildTable(); //refresh after update / delete
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});
		setTitle("Update / Delete Doctor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorsUpdateDeleteFrame.class.getResource("/resources/medical.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 682, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		doctorsTable = new JTable();
		doctorsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 0));
				firstnameText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 1));
				lastnameText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 2));
			}
		});
		doctorsTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"ID", "Firstname", "Lastname"}
		));
		
		model = (DefaultTableModel) doctorsTable.getModel();
		
		doctorsTable.setBounds(34, 56, 189, 273);
		contentPane.add(doctorsTable);
		
		JScrollPane scrollPane = new JScrollPane(doctorsTable);
		scrollPane.setBounds(25, 45, 348, 284);
		contentPane.add(scrollPane);
		
		lastnameSearchLabel = new JLabel("Lastname");
		lastnameSearchLabel.setForeground(new Color(128, 0, 0));
		lastnameSearchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastnameSearchLabel.setBounds(25, 10, 69, 12);
		contentPane.add(lastnameSearchLabel);
		
		lastnameSearchText = new JTextField();
		lastnameSearchText.setBounds(104, 7, 154, 18);
		contentPane.add(lastnameSearchText);
		lastnameSearchText.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTable();
			}
		});
		btnSearch.setForeground(new Color(0, 0, 255));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSearch.setBounds(289, 6, 84, 20);
		contentPane.add(btnSearch);
		
		idLabel = new JLabel("Id");
		idLabel.setForeground(new Color(0, 0, 255));
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		idLabel.setBounds(409, 65, 44, 12);
		contentPane.add(idLabel);
		
		idText = new JTextField();
		idText.setEditable(false);
		idText.setBounds(483, 62, 51, 18);
		contentPane.add(idText);
		idText.setColumns(10);
		
		firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setForeground(new Color(0, 0, 255));
		firstnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstnameLabel.setBounds(409, 99, 64, 12);
		contentPane.add(firstnameLabel);
		
		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname;
				
				inputFirstname = firstnameText.getText().trim();
				
				validateFirstname(inputFirstname);
				
			}
		});
		firstnameText.setBounds(483, 96, 154, 18);
		contentPane.add(firstnameText);
		firstnameText.setColumns(10);
		
		lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setForeground(new Color(0, 0, 255));
		lastnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastnameLabel.setBounds(409, 141, 64, 12);
		contentPane.add(lastnameLabel);
		
		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname;
				inputLastname = lastnameText.getText().trim();
				
				validateLastname(inputLastname);
				
			}
		});
		lastnameText.setBounds(483, 138, 154, 18);
		contentPane.add(lastnameText);
		lastnameText.setColumns(10);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(383, 45, 275, 159);
		contentPane.add(panel);
		panel.setLayout(null);
		
		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorFirstname.setBounds(100, 72, 154, 18);
		panel.add(errorFirstname);
		
		errorLastname = new JLabel("");
		errorLastname.setForeground(new Color(255, 0, 0));
		errorLastname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorLastname.setBounds(100, 113, 154, 18);
		panel.add(errorLastname);
		
		updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Data Binding
				String idStr = idText.getText().trim();
		        String inputFirstname = firstnameText.getText().trim();
		        String inputLastname = lastnameText.getText().trim();

		        // Checks if doctor is chosen
		        if (idStr.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please select a doctor from the table first.", "No Doctor Selected", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        int inputId;
		        try {
		            inputId = Integer.parseInt(idStr);
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
			
				
				
				// Validation
				if (inputFirstname.isEmpty()) {
					errorFirstname.setText("Firstname is required");
				}
				
				if (!inputFirstname.isEmpty()) {
					errorFirstname.setText("");
				}
				
				if (inputLastname.isEmpty()) {
					errorLastname.setText("Lastname is required");
				}
				
				if (!inputLastname.isEmpty()) {
					errorLastname.setText("");
				}
				
				if (inputFirstname.isEmpty() || inputLastname.isEmpty()) {
					return;
				}
				
			
				
				String sql = "UPDATE doctors SET firstname = ?, lastname = ? WHERE id = ?";
				try (Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql);) {
					
					ps.setString(1, inputFirstname);
					ps.setString(2, inputLastname);
					ps.setInt(3, inputId);
					
					int answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Update", JOptionPane.YES_NO_OPTION);
					
					if (answer == JOptionPane.YES_OPTION) {
						int rowsAffected = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, rowsAffected +  " row(s) updated", "Update", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
					
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Update Error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		updateBtn.setForeground(new Color(0, 0, 255));
		updateBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		updateBtn.setBounds(383, 229, 98, 38);
		contentPane.add(updateBtn);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idStr = idText.getText().trim();
				
				if (idStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a doctor from the table first.", "No Doctor Selected", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				
				int inputId;
				try {
						inputId = Integer.parseInt(idStr);
			        } catch (NumberFormatException ex) {
			            JOptionPane.showMessageDialog(null, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
				
				String sql = "DELETE FROM doctors WHERE id = ?";
				try(Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql);) {
					
					ps.setInt(1, inputId);
					int answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						int rowsAffected = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, rowsAffected +  " row(s) deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Delete Error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteBtn.setBounds(560, 229, 98, 38);
		contentPane.add(deleteBtn);
		
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getDoctorsMenuFrame().setEnabled(true);
				Main.getDoctorsUpdateDeleteFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		closeBtn.setBounds(560, 291, 98, 38);
		contentPane.add(closeBtn);

	}
	
	private void buildTable() {
		
		Vector<String> vector;
		String sql = "SELECT id, firstname, lastname FROM doctors WHERE lastname LIKE ?";

		try(Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, lastnameSearchText.getText().trim() + "%");
			ResultSet rs = ps.executeQuery();
			
			// Clear model -> clear table - MVVM
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			
			while (rs.next()) {
				vector = new Vector<>(3);
				vector.add(rs.getString("id"));
				vector.add(rs.getString("firstname"));
				vector.add(rs.getString("lastname"));
				
				model.addRow(vector);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Selection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void validateFirstname(String inputFirstname) {
		if (inputFirstname.equals("")) {
			errorFirstname.setText("Firstname is required");
		}
		
		if (!inputFirstname.equals("")) {
			errorFirstname.setText("");
		}
	}
	
	private void validateLastname(String inputLastname) {
		if (inputLastname.equals("")) {
			errorLastname.setText("Lastname is required");
		}
		
		if (!inputLastname.equals("")) {
			errorLastname.setText("");
		}
	}
}
