/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package service;

import java.sql.SQLException;
import java.util.List;

public interface PegawaiService<P> {
    List<P> fetchAll() throws SQLException, ClassNotFoundException;

    boolean addPegawai(P p) throws SQLException, ClassNotFoundException;

    boolean updatePegawai(P p) throws SQLException, ClassNotFoundException;

    boolean deletePegawai(P p) throws SQLException, ClassNotFoundException;

}
