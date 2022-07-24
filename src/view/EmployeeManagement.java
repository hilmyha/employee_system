package view;

import dao.DepartementDao;
import dao.PegawaiDao;
import entity.Departement;
import entity.Pegawai;
import table.PegawaiTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JTextField tfId;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextArea taAddress;
    private JButton saveButton;
    private JButton clearButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JComboBox cbDept;
    private JButton addDepartementButton;
    private JTable tabelPegawai;
    private JTextField tfSearchId;
    private JTextField tfHonor;
    private JTextField tfAllowance;
    private JTextField tfLembur;
    private JButton hitungButton;
    private JButton searchButton;
    private JTable table1;

    private DepartementDao departementDao;
    private List<Departement> departements;
    private DefaultComboBoxModel<Departement> departementCbModel;

    private PegawaiDao pegawaiDao;
    private List<Pegawai> pegawais;

    private PegawaiTableModel pegawaiTableModel;
    private Pegawai selectedPegawai;



    public static void main(String[] args) {
        JFrame eMS = new EmployeeManagement();
        eMS.setVisible(true);
    }

    public EmployeeManagement() {
        super("Employee Management System");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 650);
        this.setLocationRelativeTo(null);


        departementDao = new DepartementDao();
        departements = new ArrayList<>();
        pegawaiDao = new PegawaiDao();
        pegawais = new ArrayList<>();

        try {
            departements.addAll(departementDao.fetchAll());
            pegawais.addAll(pegawaiDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        departementCbModel = new DefaultComboBoxModel<>(departements.toArray(new Departement[0]));
        cbDept.setModel(departementCbModel);

        pegawaiTableModel = new PegawaiTableModel(pegawais);
        tabelPegawai.setModel(pegawaiTableModel);
        tabelPegawai.setAutoCreateRowSorter(true);


        addDepartementButton.addActionListener(e -> {
            String newDept = JOptionPane.showInputDialog(mainPanel, "New Departement");
            String newHonor = JOptionPane.showInputDialog(mainPanel, "New Honor");
            String newAllowance = JOptionPane.showInputDialog(mainPanel, "New Allowance");
            if (
                    newDept != null && !newDept.trim().isEmpty() || newHonor != null && !newHonor.trim().isEmpty() || newAllowance != null && !newAllowance.trim().isEmpty()
            ) {
                Departement departement = new Departement();
                departement.setName(newDept);
                departement.setHonor(Integer.parseInt(newHonor));
                departement.setAllowance(Integer.parseInt(newAllowance));
                try {
                    if (departementDao.addDept(departement) == true) {
                        departements.clear();
                        departements.addAll(departementDao.fetchAll());
                        departementCbModel.removeAllElements();
                        departementCbModel.addAll(departements);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(e -> {
            if (
                    tfId.getText().trim().isEmpty() || tfFirstName.getText().trim().isEmpty() || tfLastName.getText().trim().isEmpty() || taAddress.getText().trim().isEmpty() || cbDept.getSelectedItem() == null
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Isikan Form", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Pegawai pegawai = new Pegawai();
                pegawai.setId_pegawai(tfId.getText());
                pegawai.setFirst_name(tfFirstName.getText());
                pegawai.setLast_name(tfLastName.getText().trim().isEmpty() ? null : tfLastName.getText());
                pegawai.setAddress(taAddress.getText());
                pegawai.setDepartement((Departement) cbDept.getSelectedItem());
                try {
                    if (pegawaiDao.addPegawai(pegawai) == true) {
                        pegawais.clear();
                        pegawais.addAll(pegawaiDao.fetchAll());
                        pegawaiTableModel.fireTableDataChanged();
                        clearAndReset();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(e -> {
            if (
                    tfFirstName.getText().trim().isEmpty() || taAddress.getText().trim().isEmpty() || cbDept.getSelectedItem() == null
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Isikan Form", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                selectedPegawai.setFirst_name(tfFirstName.getText());
                selectedPegawai.setLast_name(tfLastName.getText().trim().isEmpty() ? null : tfLastName.getText());
                selectedPegawai.setDepartement((Departement) cbDept.getSelectedItem());
                try {
                    if (pegawaiDao.updatePegawai(selectedPegawai) == true) {
                        pegawais.clear();
                        pegawais.addAll(pegawaiDao.fetchAll());
                        pegawaiTableModel.fireTableDataChanged();
                        clearAndReset();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(e -> {
            if (
                    tfId.getText().trim().isEmpty() || tfFirstName.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Pilih Nama", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                selectedPegawai.setId_pegawai(tfId.getText());
                try {
                    if (pegawaiDao.deletePegawai(selectedPegawai) == true) {
                        pegawais.clear();
                        pegawais.addAll(pegawaiDao.fetchAll());
                        pegawaiTableModel.fireTableDataChanged();
                        clearAndReset();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        clearButton.addActionListener(e -> {
            clearAndReset();
        });
        tabelPegawai.getSelectionModel().addListSelectionListener(e -> {
           if (!tabelPegawai.getSelectionModel().isSelectionEmpty()) {
               int selectedIndex = tabelPegawai.convertRowIndexToModel(tabelPegawai.getSelectedRow());
               selectedPegawai = pegawais.get(selectedIndex);
               if (selectedPegawai != null) {
                   tfId.setText(selectedPegawai.getId_pegawai());
                   tfFirstName.setText(selectedPegawai.getFirst_name());
                   tfLastName.setText(selectedPegawai.getLast_name() != null ? selectedPegawai.getLast_name() : " ");
                   taAddress.setText(selectedPegawai.getAddress());
                   cbDept.setSelectedItem(selectedPegawai.getDepartement());
                   tfId.setEnabled(false);
                   saveButton.setEnabled(false);
                   updateButton.setEnabled(true);
               }
           }
        });
    }

    private void clearAndReset() {
        tfId.setText("");
        tfFirstName.setText("");
        tfLastName.setText("");
        taAddress.setText("");
        tfId.setEnabled(true);
        saveButton.setEnabled(true);
        updateButton.setEnabled(false);
        tabelPegawai.clearSelection();
        selectedPegawai = null;
    }
}
