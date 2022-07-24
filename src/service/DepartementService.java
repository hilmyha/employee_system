package service;

import java.sql.SQLException;
import java.util.List;

public interface DepartementService<D> {
    List<D> fetchAll() throws SQLException, ClassNotFoundException;

    boolean addDept(D d) throws SQLException, ClassNotFoundException;
}
