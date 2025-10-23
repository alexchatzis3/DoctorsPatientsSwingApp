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

public class DoctorsMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public DoctorsMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorsMenuFrame.class.getResource("/resources/medical.png")));
		setTitle("Doctors' Menu");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton doctorsViewBtn = new JButton("View Doctors");
		doctorsViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getDoctorsUpdateDeleteFrame().setVisible(true);
				Main.getDoctorsMenuFrame().setEnabled(false);
			}
		});
		doctorsViewBtn.setForeground(new Color(0, 0, 255));
		doctorsViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		doctorsViewBtn.setBounds(137, 33, 147, 50);
		contentPane.add(doctorsViewBtn);
		
		JButton doctorsInsertBtn = new JButton("Insert Doctor");
		doctorsInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getDoctorsInsertFrame().setVisible(true);
				Main.getDoctorsMenuFrame().setEnabled(false);
			}
		});
		doctorsInsertBtn.setForeground(Color.BLUE);
		doctorsInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		doctorsInsertBtn.setBounds(137, 105, 147, 50);
		contentPane.add(doctorsInsertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMainMenuFrame().setEnabled(true);
				Main.getDoctorsMenuFrame().setVisible(false);
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
