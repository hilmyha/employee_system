/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package table;

import entity.Pegawai;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GajiTableModel extends AbstractTableModel {
    private List<Pegawai> pegawais;
    private final String[] COLUMNS = {
            "ID PEGAWAI",
            "DEPARTEMENT",
            "HONOR",
            "ALLOWANCE",
            "TRANSPORT"
    };

    public GajiTableModel(List<Pegawai> pegawais) {
        this.pegawais = pegawais;
    }

    @Override
    public int getRowCount() {
        return pegawais.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> pegawais.get(rowIndex).getId_pegawai();
            case 1 -> pegawais.get(rowIndex).getDepartement().getName();
            case 2 -> pegawais.get(rowIndex).getDepartement().getHonor();
            case 3 -> pegawais.get(rowIndex).getDepartement().getAllowance();
            case 4 -> pegawais.get(rowIndex).getDepartement().getTransport();
            default -> " ";
        };
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        }
        return Object.class;
    }
}
