package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RegistroM;

public class RegistroD extends Conexion {

    public void AddReg(RegistroM reg) throws Exception {
        try {
            this.Conectar();
            String sql = "Exec AddUsuarios ?,?,?,?,?,?,?,?,?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, reg.getNomb_Reg());
            ps.setString(2, reg.getApe_Reg());
            ps.setString(3, reg.getDni_Reg());
            ps.setString(4, reg.getEmail_Reg());
            ps.setString(6, reg.getCel_Reg());
            ps.setString(7, reg.getTelf_Reg());
            ps.setString(8, reg.getCod_Ubi());
            ps.setString(9, reg.getEst_Reg());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> queryAutoCompleteUbi(String a) throws Exception {
        this.Conectar();
        ResultSet rs;
        List<String> lista;
        String sql;
        try {

            sql = "SELECT Cod_Ubi,CONCAT(CONCAT(CONCAT(CONCAT(Dpto_Ubi,','),Prov_Ubi),','),Dist_Ubi) AS NOMBRE FROM UBIGEO WHERE Dist_Ubi LIKE UPPER(?)";

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
        }
    }

    public String leerUbi(String a) throws Exception {
        this.Conectar();
        ResultSet rs;
        try {
            String sql = "SELECT Cod_Ubi FROM UBIGEO WHERE CONCAT(CONCAT(CONCAT(CONCAT(Dpto_Ubi,','),Prov_Ubi),','),Dist_Ubi) = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("COD_UBI");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }
}
