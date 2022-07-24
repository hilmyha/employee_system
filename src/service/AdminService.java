package service;

import java.sql.SQLException;

public interface AdminService<A> {

    boolean loginGet(A a) throws SQLException, ClassNotFoundException;
}
