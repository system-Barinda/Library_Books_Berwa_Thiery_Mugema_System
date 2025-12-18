package ui;

import dao.FanDAO;
import model.Fan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FanPanel extends JPanel {

    private JTextField txtName, txtTeam, txtP1, txtP2, txtP3;
    private DefaultTableModel tableModel;
    private FanDAO dao = new FanDAO();

    public FanPanel() {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ===== TITLE =====
        JLabel title = new JLabel("Premier League", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtName = createTextField();
        txtTeam = createTextField();
        txtP1 = createTextField();
        txtP2 = createTextField();
        txtP3 = createTextField();

        addFormRow(formPanel, gbc, 0, "Fan Name:", txtName);
        addFormRow(formPanel, gbc, 1, "Team Name:", txtTeam);
        addFormRow(formPanel, gbc, 2, "Player One Goals:", txtP1);
        addFormRow(formPanel, gbc, 3, "Player Two Goals:", txtP2);
        addFormRow(formPanel, gbc, 4, "Player Three Goals:", txtP3);

        add(formPanel, BorderLayout.CENTER);

        // ===== BUTTONS PANEL (LIKE IMAGE) =====
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        JButton btnAdd = new JButton("Add");
        JButton btnExit = new JButton("Exit");

        styleButton(btnAdd, new Color(0, 200, 0));
        styleButton(btnExit, Color.RED);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnExit);

        // ===== TABLE =====
        String[] columns = {"Fan Name", "Team", "Average Goals"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 220));

        // ===== SOUTH CONTAINER (Buttons + Table) =====
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(scrollPane, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addFan());
        btnExit.addActionListener(e -> System.exit(0));

        loadFans();
    }

    // ===== UI HELPERS =====
    private JTextField createTextField() {
        JTextField tf = new JTextField(22);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return tf;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc,
                            int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
    }

    // ===== LOGIC =====
    private void addFan() {

        // 1️⃣ Check empty fields
        if (txtName.getText().trim().isEmpty() ||
            txtTeam.getText().trim().isEmpty() ||
            txtP1.getText().trim().isEmpty() ||
            txtP2.getText().trim().isEmpty() ||
            txtP3.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required.",
                    "Message",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int p1, p2, p3;

        // 2️⃣ Check numeric values
        try {
            p1 = Integer.parseInt(txtP1.getText());
            p2 = Integer.parseInt(txtP2.getText());
            p3 = Integer.parseInt(txtP3.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for goals.",
                    "Message",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 3️⃣ Check positive numbers
        if (p1 < 0 || p2 < 0 || p3 < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Goals should be a positive number.",
                    "Message",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 4️⃣ Save to database
        try {
            Fan fan = new Fan(
                    txtName.getText(),
                    txtTeam.getText(),
                    p1, p2, p3
            );

            dao.addFan(fan);
            clearFields();
            loadFans();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadFans() {
        try {
            tableModel.setRowCount(0);
            List<Fan> fans = dao.getAllFans();
            for (Fan f : fans) {
                tableModel.addRow(new Object[]{
                        f.getName(),
                        f.getTeam(),
                        f.getAverage()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtTeam.setText("");
        txtP1.setText("");
        txtP2.setText("");
        txtP3.setText("");
    }
}
