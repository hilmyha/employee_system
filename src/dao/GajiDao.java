package dao;

import database.MySQL;
import entity.Pegawai;
import service.GajiService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GajiDao implements GajiService<Pegawai> {
    String  total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String  total) {
        this.total = total;
    }

    @Override
    public boolean hitungGaji(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        boolean result = false;
        total = "";
        String query = "SELECT p.id_pegawai, first_name, last_name, honor, allowance, transport, (honor - allowance + (transport * absen)) AS gaji_bersih FROM pegawai p JOIN departement d on d.id_dept = p.departement_id WHERE id_pegawai = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, pegawai.getId_pegawai());
                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next() != false) {
//                        pegawai.setGaji_bersih(rs.getInt("gaji_bersih"));
//                        pegawai.setId_pegawai(rs.getString("id_pegawai"));
//                        connection.commit();
//                        result = true;
//                    } else {
//                        connection.rollback();
//                    }
                    if (rs.next()) {
                        total = String.valueOf(Integer.parseInt(rs.getString("gaji_bersih")));
                        pegawai.setId_pegawai(rs.getString("id_pegawai"));
                        System.out.println("Rp" + total);

                    }
                }

            }
        }
        return result;
    }

    @Override
    public boolean absensi(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "UPDATE pegawai SET absen = absen + 1 WHERE id_pegawai = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
//                ps.setInt(1, pegawai.getAbsen());
                ps.setString(1, pegawai.getId_pegawai());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = true;
                } else {
                    connection.rollback();
                }
            }
        }
        return result;
    }
}
