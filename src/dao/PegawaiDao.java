/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package dao;

import database.MySQL;
import entity.Departement;
import entity.Pegawai;
import service.PegawaiService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDao implements PegawaiService<Pegawai> {
    @Override
    public List<Pegawai> fetchAll() throws SQLException, ClassNotFoundException {
        List<Pegawai> pegawais = new ArrayList<>();
        String query = "SELECT p.id_pegawai, first_name, last_name, address, absen, departement_id, dept_name, honor, allowance, transport FROM pegawai p JOIN departement d on d.id_dept = p.departement_id";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Departement departement = new Departement();
                        departement.setId(rs.getInt("departement_id"));
                        departement.setName(rs.getString("dept_name"));
                        departement.setHonor(rs.getInt("honor"));
                        departement.setAllowance(rs.getInt("allowance"));
                        departement.setTransport(rs.getInt("transport"));

                        Pegawai pegawai = new Pegawai();
                        pegawai.setId_pegawai(rs.getString("id_pegawai"));
                        pegawai.setFirst_name(rs.getString("first_name"));
                        pegawai.setLast_name(rs.getString("last_name"));
                        pegawai.setAddress(rs.getString("address"));
                        pegawai.setAbsen(rs.getInt("absen"));
                        pegawai.setDepartement(departement);

                        pegawais.add(pegawai);
                    }
                }
            }
        }
        return pegawais;
    }

    @Override
    public boolean addPegawai(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "INSERT INTO pegawai(id_pegawai, first_name, last_name, address, departement_id, absen) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, pegawai.getId_pegawai());
                ps.setString(2, pegawai.getFirst_name());
                ps.setString(3, pegawai.getLast_name());
                ps.setString(4, pegawai.getAddress());
                ps.setInt(5, pegawai.getDepartement().getId());
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

    @Override
    public boolean updatePegawai(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "UPDATE pegawai SET first_name = ?, last_name = ?, address = ?, departement_id = ? WHERE id_pegawai = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, pegawai.getFirst_name());
                ps.setString(2, pegawai.getLast_name());
                ps.setString(3, pegawai.getAddress());
                ps.setInt(4, pegawai.getDepartement().getId());
                ps.setString(5, pegawai.getId_pegawai());
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

    @Override
    public boolean deletePegawai(Pegawai pegawai) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "DELETE FROM pegawai WHERE id_pegawai = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
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
