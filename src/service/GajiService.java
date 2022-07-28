package service;

import java.sql.SQLException;
import java.util.List;

public interface GajiService<G> {
    boolean hitungGaji(G g) throws SQLException, ClassNotFoundException;

    boolean absensi(G g) throws SQLException, ClassNotFoundException;

//    List<G> fetchAll() throws SQLException, ClassNotFoundException;
}
