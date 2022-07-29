/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package dao;

import database.MySQL;
import entity.Departement;
import entity.Pegawai;
import service.GajiService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GajiDao implements GajiService<Pegawai> {
    @Override
    public int gajiBersih(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        int gaji_bersih = 0;
        String query = "SELECT p.id_pegawai, first_name, last_name, absen, dept_name, honor, allowance, transport, (honor - allowance + (transport * absen)) AS gaji_bersih FROM pegawai p JOIN departement d on d.id_dept = p.departement_id WHERE id_pegawai = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, pegawai.getId_pegawai());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() != false) {
                        Departement departement = new Departement();
                        departement.setName(rs.getString("dept_name"));
                        departement.setHonor(rs.getInt("honor"));
                        departement.setAllowance(rs.getInt("allowance"));
                        departement.setTransport(rs.getInt("transport"));

                        pegawai.setGaji_bersih(gaji_bersih = rs.getInt("gaji_bersih"));
                        pegawai.setFirst_name(rs.getString("first_name"));
                        pegawai.setLast_name(rs.getString("last_name"));
                        pegawai.setId_pegawai(rs.getString("id_pegawai"));
                        pegawai.setAbsen(rs.getInt("absen"));

                        pegawai.setDepartement(departement);
                        connection.commit();
                        return gaji_bersih;
                    } else {
                        connection.rollback();
                    }
                }
            }
        }
        return gaji_bersih;
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
