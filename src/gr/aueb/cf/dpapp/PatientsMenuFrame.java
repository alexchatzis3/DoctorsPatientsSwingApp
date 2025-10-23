package gr.aueb.cf.dpapp;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientsMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public PatientsMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorsMenuFrame.class.getResource("/resources/medical.png")));
		setTitle("Patients' Menu");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton patientsViewBtn = new JButton("View Patients");
		patientsViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getPatientsUpdateDeleteFrame().setVisible(true);
				Main.getPatientsMenuFrame().setEnabled(false);
			}
		});
		patientsViewBtn.setForeground(new Color(0, 0, 255));
		patientsViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		patientsViewBtn.setBounds(137, 33, 147, 50);
		contentPane.add(patientsViewBtn);
		
		JButton patientsInsertBtn = new JButton("Insert Patient");
		patientsInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getPatientsInsertFrame().setVisible(true);
				Main.getPatientsMenuFrame().setEnabled(false);
			}
		});
		patientsInsertBtn.setForeground(Color.BLUE);
		patientsInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		patientsInsertBtn.setBounds(137, 105, 147, 50);
		contentPane.add(patientsInsertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMainMenuFrame().setEnabled(true);
				Main.getPatientsMenuFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		closeBtn.setBounds(324, 217, 102, 36);
		contentPane.add(closeBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 185, 416, 1);
		contentPane.add(separator);

	}

}
