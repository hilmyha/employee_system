/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package service;

import java.sql.SQLException;
import java.util.List;

public interface DepartementService<D> {
    List<D> fetchAll() throws SQLException, ClassNotFoundException;

    boolean addDept(D d) throws SQLException, ClassNotFoundException;

}
