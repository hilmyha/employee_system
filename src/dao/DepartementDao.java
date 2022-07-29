/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package dao;

import database.MySQL;
import entity.Departement;
import service.DepartementService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartementDao implements DepartementService<Departement> {
    @Override
    public List<Departement> fetchAll() throws SQLException, ClassNotFoundException {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM departement";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Departement departement = new Departement();
                        departement.setId(rs.getInt("id_dept"));
                        departement.setName(rs.getString("dept_name"));
                        departement.setHonor(rs.getInt("honor"));
                        departement.setAllowance(rs.getInt("allowance"));
                        departement.setTransport(rs.getInt("transport"));
                        departements.add(departement);
                    }
                }
            }
        }
        return departements;
    }

    @Override
    public boolean addDept(Departement departement) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "INSERT INTO departement(dept_name, honor, allowance, transport) VALUES (?, ?, ?, ?)";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, departement.getName());
                ps.setInt(2, departement.getHonor());
                ps.setInt(3, departement.getAllowance());
                ps.setInt(4, departement.getTransport());
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
