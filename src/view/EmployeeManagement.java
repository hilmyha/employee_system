/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package view;

import dao.DepartementDao;
import dao.GajiDao;
import dao.PegawaiDao;
import entity.Departement;
import entity.Pegawai;
import table.AbsensiTableModel;
import table.GajiTableModel;
import table.PegawaiTableModel;

import javax.swing.*;
import java.awt.*;
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
    private JTextField searchFirstName;
    private JTextField searchLastName;
    private JTextField searchDept;
    private JTable tableGaji;
    private JTextField searchId;
    private JTextField tfIdAbsen;
    private JButton absenButton;
    private JButton hitungButton;
    private JTextField hitungGajiFname;
    private JTextField hitungGajiLname;
    private JTextField hitungGajiDept;
    private JTextField hitungGajiBersih;
    private JTable absensiTable;
    private JScrollPane pegawaiJscroll;
    private JScrollPane absenJscroll;
    private JScrollPane hitungJscroll;
    private JButton clearButton1;
    private JButton clearButton2;
    private JPanel header;

    private DepartementDao departementDao;
    private List<Departement> departements;
    private DefaultComboBoxModel<Departement> departementCbModel;

    private PegawaiDao pegawaiDao;
    private List<Pegawai> pegawais;

    private PegawaiTableModel pegawaiTableModel;
    private Pegawai selectedPegawai;
    private Pegawai hitungGajiPegawai;

    private GajiDao gajiDao;
    private GajiTableModel gajiTableModel;
    private AbsensiTableModel absensiTableModel;

//    public static void main(String[] args) {
//        JFrame eMS = new EmployeeManagement();
//        eMS.setVisible(true);
//    }

    public EmployeeManagement() {
        super("Employee Management System");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);

        departementDao = new DepartementDao();
        departements = new ArrayList<>();
        pegawaiDao = new PegawaiDao();
        pegawais = new ArrayList<>();
        gajiDao = new GajiDao();

        pegawaiJscroll.setPreferredSize(new Dimension(500, 50));
        absenJscroll.setPreferredSize(new Dimension(500, 20));
        hitungJscroll.setPreferredSize(new Dimension(500, 100));
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

        gajiTableModel = new GajiTableModel(pegawais);
        tableGaji.setModel(gajiTableModel);
        tableGaji.setAutoCreateRowSorter(true);

        absensiTableModel = new AbsensiTableModel(pegawais);
        absensiTable.setModel(absensiTableModel);
        absensiTable.setAutoCreateRowSorter(true);

        addDepartementButton.addActionListener(e -> {
            String newDept = JOptionPane.showInputDialog(mainPanel, "New Departement");
            String newHonor = JOptionPane.showInputDialog(mainPanel, "Honor");
            String newAllowance = JOptionPane.showInputDialog(mainPanel, "Allowance");
            String newTransport = JOptionPane.showInputDialog(mainPanel, "Transport");
            if (
                    newDept != null && !newDept.trim().isEmpty() || newHonor != null && !newHonor.trim().isEmpty() || newAllowance != null && !newAllowance.trim().isEmpty() || newTransport != null && !newTransport.trim().isEmpty()
            ) {
                Departement departement = new Departement();
                departement.setName(newDept);
                departement.setHonor(Integer.parseInt(newHonor));
                departement.setAllowance(Integer.parseInt(newAllowance));
                departement.setTransport(Integer.parseInt(newTransport));
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

        absenButton.addActionListener(e -> {
            if (
                    tfIdAbsen.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Masukkan ID", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Pegawai pegawai = new Pegawai();
                pegawai.setId_pegawai(tfIdAbsen.getText());
                try {
                    if (gajiDao.absensi(pegawai) == true) {
                        pegawais.clear();
                        pegawais.addAll(pegawaiDao.fetchAll());
                        absensiTableModel.fireTableDataChanged();
                        clearAbsen();
                        JOptionPane.showMessageDialog(mainPanel, "Sukses Absensi", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        hitungButton.addActionListener(e -> {
            if (
                    searchId.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Cari ID", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Pegawai pegawai = new Pegawai();
                pegawai.setId_pegawai(searchId.getText());
                try {
                    if (gajiDao.gajiBersih(pegawai) != 0) {
                        hitungGajiFname.setText(String.valueOf(pegawai.getFirst_name()));
                        hitungGajiLname.setText(String.valueOf(pegawai.getLast_name()));
                        hitungGajiDept.setText(pegawai.getDepartement().getName());
                        hitungGajiBersih.setText(String.valueOf(pegawai.getGaji_bersih()));
                        System.out.println("SQL OK");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        clearButton2.addActionListener(e -> {
            clearAbsen();
        });
        clearButton1.addActionListener(e -> {
            clearHitungGaji();
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
    private void clearAbsen() {
        tfIdAbsen.setText("");
        absensiTable.clearSelection();
    }
    private void clearHitungGaji() {
        searchId.setText("");
        hitungGajiFname.setText("");
        hitungGajiLname.setText("");
        hitungGajiDept.setText("");
        hitungGajiBersih.setText(" ");
    }
}
