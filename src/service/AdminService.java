/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package service;

import java.sql.SQLException;

public interface AdminService<A> {

    boolean loginGet(A a) throws SQLException, ClassNotFoundException;
}
