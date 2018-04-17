package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import model.PersonaModel;
import org.json.JSONObject;

public class PersonaDao extends DAO {

    public PersonaModel consultarDni(PersonaModel dl) throws org.json.JSONException, IOException {

        //Crea es json de Solicitud
        JSONObject obj = new JSONObject();
        obj.put("dni", dl.getDni());

        //Realiza la consulta
        String json = obj.toString();
        URL url = new URL("https://tecactus.com/api/reniec/dni");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("X-RateLimit-Limit", "100");
        con.setRequestProperty("X-RateLimit-Remaining", "58");
        con.setRequestProperty("Authorization", "Bearer " + dl.getTokendni());
        con.setDoOutput(true);
        con.getOutputStream().write(json.getBytes("UTF-8"));
        Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        //Trae la cadene de respuesta
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;) {
            sb.append((char) c);
        }
        String response = sb.toString(); //String Respuesta
        System.out.println(response);
        JSONObject json2 = new JSONObject(response); //JSON con la respuesta

        //Contenido del json rellenando en cada modelo
        dl.setNombre((String) json2.get("nombres"));
        dl.setApepaterno((String) json2.get("apellido_paterno"));
        dl.setApematerno((String) json2.get("apellido_materno"));
        return dl;
    }

    public void guardar(PersonaModel model, String codigo) throws Exception {
        try {
            this.Conectar();
            String sql = "INSERT INTO registro (dni_reg,nomb_reg,ape_regm,ape_regp,cel_reg,correo_reg,cod_ubi,est_reg)  VALUES (?,UPPER(?),UPPER(?),UPPER(?),?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, model.getDni());
            ps.setString(2, model.getNombre());
            ps.setString(3, model.getApematerno());
            ps.setString(4, model.getApepaterno());
            ps.setString(5, model.getCelular());
            ps.setString(6, model.getCorreo());
            ps.setString(7, codigo);
            ps.setString(8, model.getMedios());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
