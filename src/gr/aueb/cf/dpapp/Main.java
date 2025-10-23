package gr.aueb.cf.dpapp;

import java.awt.EventQueue;

public class Main {
	
	private final static MainMenuFrame mainMenuFrame = new MainMenuFrame();
	private final static DoctorsMenuFrame doctorsMenuFrame = new DoctorsMenuFrame();
	private final static DoctorsInsertFrame doctorsInsertFrame = new DoctorsInsertFrame();
	private final static DoctorsUpdateDeleteFrame doctorsUpdateDeleteFrame = new DoctorsUpdateDeleteFrame();
	private final static PatientsMenuFrame patientsMenuFrame = new PatientsMenuFrame();
	private final static PatientsInsertFrame patientsInsertFrame = new PatientsInsertFrame();
	private final static PatientsUpdateDeleteFrame patientsUpdateDeleteFrame = new PatientsUpdateDeleteFrame();
	private final static DoctorsPatientsFrame doctorsPatientsFrame = new DoctorsPatientsFrame();






	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainMenuFrame.setLocationRelativeTo(null);
					mainMenuFrame.setVisible(true);
					
					doctorsMenuFrame.setLocationRelativeTo(null);
					doctorsMenuFrame.setVisible(false);
					
					doctorsInsertFrame.setLocationRelativeTo(null);
					doctorsInsertFrame.setVisible(false);
					
					doctorsUpdateDeleteFrame.setLocationRelativeTo(null);
					doctorsUpdateDeleteFrame.setVisible(false);
					
					patientsMenuFrame.setLocationRelativeTo(null);
					patientsMenuFrame.setVisible(false);
					
					patientsInsertFrame.setLocationRelativeTo(null);
					patientsInsertFrame.setVisible(false);
					
					patientsUpdateDeleteFrame.setLocationRelativeTo(null);
					patientsUpdateDeleteFrame.setVisible(false);
					
					doctorsPatientsFrame.setLocationRelativeTo(null);
					doctorsPatientsFrame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static MainMenuFrame getMainMenuFrame() {
		return mainMenuFrame;
	}

	public static DoctorsMenuFrame getDoctorsMenuFrame() {
		return doctorsMenuFrame;
	}

	public static DoctorsInsertFrame getDoctorsInsertFrame() {
		return doctorsInsertFrame;
	}

	public static DoctorsUpdateDeleteFrame getDoctorsUpdateDeleteFrame() {
		return doctorsUpdateDeleteFrame;
	}

	public static PatientsMenuFrame getPatientsMenuFrame() {
		return patientsMenuFrame;
	}

	public static PatientsInsertFrame getPatientsInsertFrame() {
		return patientsInsertFrame;
	}

	public static PatientsUpdateDeleteFrame getPatientsUpdateDeleteFrame() {
		return patientsUpdateDeleteFrame;
	}
	
	public static DoctorsPatientsFrame getDoctorsPatientsFrame() {
		return doctorsPatientsFrame;
	}
	
}
