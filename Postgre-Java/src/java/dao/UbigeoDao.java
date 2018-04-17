package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UbigeoDao extends DAO {

    public String leerUbigeo(String a) throws Exception {
        this.Conectar();
        ResultSet rs;
        try {
            String sql = "SELECT CODUBI FROM ubigeo WHERE CONCAT(CONCAT(CONCAT(CONCAT(Dpto_Ubi,','),Prov_Ubi),','),Dist_Ubi) = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Cod_Ubi");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

//Lista el autocomplete de ubigeo mientras escriben
    public List<String> queryAutoCompleteUbi(String a) throws Exception {
        this.Conectar();
        ResultSet rs;
        List<String> lista;
        String sql;
        try {
            if (a == null || "".equals(a)) {
                sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(Dpto_Ubi,','),Prov_Ubi),','),Dist_Ubi) AS NOMBRE FROM ubigeo WHERE Prov_Ubi LIKE UPPER(?)";
                a = "CAÑETE";
            } else {
                sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(Dpto_Ubi,','),Prov_Ubi),','),Dist_Ubi) AS NOMBRE FROM ubigeo WHERE Dist_Ubi like UPPER(?)";
            }
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NOMBRE"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
