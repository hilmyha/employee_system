/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package dao;

import database.MySQL;
import entity.Admin;
import service.AdminService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao implements AdminService<Admin> {
    @Override
    public boolean loginGet(Admin admin) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String query = "SELECT * FROM user WHERE uname = ? AND pwd = ?";
        try (Connection connection = MySQL.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, admin.getName());
                ps.setString(2, admin.getPass());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() != false) {
                        admin.setName(rs.getString("uname"));
                        admin.setPass(rs.getString("pwd"));
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
