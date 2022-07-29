/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package service;

import java.sql.SQLException;
import java.util.List;

public interface GajiService<G> {

    int gajiBersih(G g) throws SQLException, ClassNotFoundException;

    boolean absensi(G g) throws SQLException, ClassNotFoundException;
}
