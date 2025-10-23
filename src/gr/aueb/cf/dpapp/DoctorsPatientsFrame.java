package gr.aueb.cf.dpapp;

import gr.aueb.cf.dpapp.util.DBUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Frame for managing the relationship between doctors and patients.
 * <p>
 * Features:
 * - Display doctors in a combo box
 * - Display available patients in a combo box
 * - Assign a patient to a doctor
 * - Remove a patient from a doctor
 * - Display assigned patients in a JTable
 * </p>
 */
public class DoctorsPatientsFrame extends JFrame {

  
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable doctorsPatientsTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> doctorsCombo;
    private JComboBox<String> patientsCombo;
    private JButton assignBtn, removeBtn, closeBtn;

    // Maps for keeping track of IDs
    private Map<String, Integer> doctorMap = new HashMap<>();
    private Map<String, Integer> patientMap = new HashMap<>();

    /**
     * Constructor initializes the GUI components and loads data.
     */
    public DoctorsPatientsFrame() {
        setTitle("Doctors - Patients Links");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                DoctorsPatientsFrame.class.getResource("/resources/medical.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 720, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Labels
        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setForeground(Color.BLUE);
        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        doctorLabel.setBounds(25, 15, 60, 20);
        contentPane.add(doctorLabel);

        JLabel patientLabel = new JLabel("Patient:");
        patientLabel.setForeground(Color.BLUE);
        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        patientLabel.setBounds(330, 15, 60, 20);
        contentPane.add(patientLabel);

        // Combo boxes
        doctorsCombo = new JComboBox<>();
        doctorsCombo.setBounds(90, 15, 220, 22);
        contentPane.add(doctorsCombo);

        patientsCombo = new JComboBox<>();
        patientsCombo.setBounds(395, 15, 220, 22);
        contentPane.add(patientsCombo);

        // Buttons
        assignBtn = new JButton("Assign");
        assignBtn.setBounds(25, 55, 120, 30);
        assignBtn.setForeground(Color.BLUE);
        assignBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(assignBtn);

        removeBtn = new JButton("Remove");
        removeBtn.setBounds(160, 55, 120, 30);
        removeBtn.setForeground(Color.BLUE);
        removeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(removeBtn);

        closeBtn = new JButton("Close");
        closeBtn.setBounds(595, 340, 90, 30);
        closeBtn.setForeground(Color.BLUE);
        closeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(closeBtn);

        // Table to display patients of selected doctor
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 100, 660, 220);
        contentPane.add(scrollPane);

        doctorsPatientsTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Patient ID", "Firstname", "Lastname"}, 0);
        doctorsPatientsTable.setModel(tableModel);
        scrollPane.setViewportView(doctorsPatientsTable);

        // Load initial data
        loadDoctors();
        reloadPatientsCombo();

        // Event handlers
        doctorsCombo.addActionListener(e -> buildTable()); // Refresh table when doctor changes
        assignBtn.addActionListener(e -> assignDoctorToPatient());
        removeBtn.addActionListener(e -> removeSelectedLink());
        closeBtn.addActionListener(e -> {
            Main.getMainMenuFrame().setEnabled(true);
            Main.getMainMenuFrame().setVisible(true);
            Main.getDoctorsPatientsFrame().setVisible(false);
        });
    }

    /**
     * Loads all doctors from the database into the combo box and map.
     */
    private void loadDoctors() {
        doctorMap.clear();
        doctorsCombo.removeAllItems();

        String sql = "SELECT id, firstname, lastname FROM doctors";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullname = rs.getString("firstname") + " " + rs.getString("lastname");
                doctorsCombo.addItem(fullname);
                doctorMap.put(fullname, id);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading doctors: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reloads the patients combo box with all available patients from the database.
     * Clears previous items to avoid duplicates.
     */
    private void reloadPatientsCombo() {
        patientsCombo.removeAllItems();
        patientMap.clear();

        String sql = "SELECT id, firstname, lastname FROM patients ORDER BY firstname, lastname";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullname = rs.getString("firstname") + " " + rs.getString("lastname");
                patientsCombo.addItem(fullname);
                patientMap.put(fullname, id);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Builds the table showing patients assigned to the selected doctor.
     */
    private void buildTable() {
        tableModel.setRowCount(0); // clear previous rows
        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        if (selectedDoctor == null) return;

        Integer doctorId = doctorMap.get(selectedDoctor);
        if (doctorId == null) return;

        String sql = """
                SELECT p.id, p.firstname, p.lastname
                FROM patients p
                JOIN doctorspatients dp ON p.id = dp.p_id
                WHERE dp.d_id = ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("firstname"));
                row.add(rs.getString("lastname"));
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Assigns the selected patient from combo box to the selected doctor.
     * Refreshes the table and combo box after assignment.
     */
    private void assignDoctorToPatient() {
        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        String selectedPatient = (String) patientsCombo.getSelectedItem();

        if (selectedDoctor == null || selectedPatient == null) {
            JOptionPane.showMessageDialog(this, "Select both doctor and patient.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doctorId = doctorMap.get(selectedDoctor);
        int patientId = patientMap.get(selectedPatient);

        String sql = "INSERT INTO doctorspatients (d_id, p_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setInt(2, patientId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Doctor assigned successfully!");
            buildTable();
            reloadPatientsCombo(); // refresh combo box in case new patients were added

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error assigning: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes the selected patient-doctor relationship from the database.
     * Updates both table and combo box after removal.
     */
    private void removeSelectedLink() {
        int row = doctorsPatientsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to remove.");
            return;
        }

        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        if (selectedDoctor == null) return;

        int doctorId = doctorMap.get(selectedDoctor);
        int patientId = (int) tableModel.getValueAt(row, 0);

        String sql = "DELETE FROM doctorspatients WHERE d_id = ? AND p_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setInt(2, patientId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Relation removed successfully!");
            buildTable();
            reloadPatientsCombo();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing relation: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
