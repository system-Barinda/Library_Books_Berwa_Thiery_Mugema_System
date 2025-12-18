package ui;

import dao.FanDAO;
import model.Fan;
import service.FanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FanPanel extends JPanel {

    private JTextField txtName, txtTeam, txtP1, txtP2, txtP3;
    private JTable table;
    private DefaultTableModel model;
    private FanDAO dao = new FanDAO();
    private FanService service = new FanService();

    public FanPanel() {
        setLayout(new BorderLayout());

        // ===== FORM PANEL =====
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        txtName = new JTextField();
        txtTeam = new JTextField();
        txtP1 = new JTextField();
        txtP2 = new JTextField();
        txtP3 = new JTextField();

        form.add(new JLabel("Fan Name:"));
        form.add(txtName);

        form.add(new JLabel("Team:"));
        form.add(txtTeam);

        form.add(new JLabel("Player One Goals:"));
        form.add(txtP1);

        form.add(new JLabel("Player Two Goals:"));
        form.add(txtP2);

        form.add(new JLabel("Player Three Goals:"));
        form.add(txtP3);

        JButton btnAdd = new JButton("Add Fan");
        JButton btnRefresh = new JButton("Refresh Table");

        form.add(btnAdd);
        form.add(btnRefresh);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {
            "ID", "Name", "Team", "P1", "P2", "P3", "Average"
        };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        btnAdd.addActionListener(e -> addFan());
        btnRefresh.addActionListener(e -> loadFans());

        loadFans();
    }

    private void addFan() {
        try {
            if (txtName.getText().isEmpty() || txtTeam.getText().isEmpty()) {
                throw new Exception("Name and Team are required");
            }

            int p1 = Integer.parseInt(txtP1.getText());
            int p2 = Integer.parseInt(txtP2.getText());
            int p3 = Integer.parseInt(txtP3.getText());

            if (p1 < 0 || p2 < 0 || p3 < 0) {
                throw new Exception("Goals must be positive");
            }

            Fan fan = new Fan(txtName.getText(), txtTeam.getText(), p1, p2, p3);
            dao.addFan(fan);

            JOptionPane.showMessageDialog(this, "Fan added successfully!");
            clearFields();
            loadFans();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Goals must be numbers only");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadFans() {
        try {
            model.setRowCount(0);
            List<Fan> fans = dao.getAllFans();

            for (Fan f : fans) {
                model.addRow(new Object[]{
                    f.getId(),
                    f.getName(),
                    f.getTeam(),
                    f.getP1(),
                    f.getP2(),
                    f.getP3(),
                    f.getAverage()
                });
            }

            Fan top = service.highestAverage(fans);
            if (top != null) {
                setBorder(BorderFactory.createTitledBorder(
                    "Top Fan: " + top.getName() + " | Avg Goals: " + top.getAverage()
                ));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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
