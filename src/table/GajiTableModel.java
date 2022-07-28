package table;

import entity.Pegawai;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GajiTableModel extends AbstractTableModel {
    private List<Pegawai> pegawais;
    private final String[] COLUMNS = {
            "ID",
            "FIRST NAME",
            "LAST NAME",
            "DEPARTEMENT",
            "HONOR",
            "ALLOWANCE",
            "ABSEN",
            "GAJI BERSIH"
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
            case 0 -> pegawais.get(rowIndex).getDepartement().getId();
            case 1 -> pegawais.get(rowIndex).getFirst_name();
            case 2 -> pegawais.get(rowIndex).getLast_name();
            case 3 -> pegawais.get(rowIndex).getDepartement().getName();
            case 4 -> pegawais.get(rowIndex).getDepartement().getHonor();
            case 5 -> pegawais.get(rowIndex).getDepartement().getAllowance();
            case 6 -> pegawais.get(rowIndex).getAbsen();
            case 7 -> pegawais.get(rowIndex).getGaji_bersih();
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
