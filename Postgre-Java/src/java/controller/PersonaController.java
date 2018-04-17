package controller;

import dao.PersonaDao;
import dao.UbigeoDao;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import model.PersonaModel;
import org.primefaces.json.JSONException;

@Named(value = "personaController")
@SessionScoped
public class PersonaController implements Serializable {

    PersonaModel selected = new PersonaModel();
    private String NombreUbigeo;

    //METODO PARA AUTOCOMPLETAR
    public List<String> autocompleteDistUbigeo(String query) throws Exception {
        UbigeoDao dao = new UbigeoDao();
        return dao.queryAutoCompleteUbi(query);
    }

    public String buscarCodigoUbigeo() throws Exception {
        UbigeoDao dao;
        try {
            dao = new UbigeoDao();
            return dao.leerUbigeo(NombreUbigeo);
        } catch (Exception e) {
            throw e;
        }
    }

    public void consultarDni() throws Exception {
        PersonaDao ds;
        try {
            ds = new PersonaDao();
            selected = ds.consultarDni(selected);
            guardar();
            FacesContext.getCurrentInstance().getExternalContext().redirect("correcto.xhtml");
        } catch (IOException | JSONException e) {
              
            throw e;
        }
    }

    public void guardar() throws IOException, Exception {
        PersonaDao dao;
        try {
            dao = new PersonaDao();
            dao.guardar(selected,buscarCodigoUbigeo());
        } catch (Exception e) {
             FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            throw e;
        }
    }

    public String getNombreUbigeo() {
        return NombreUbigeo;
    }

    public void setNombreUbigeo(String NombreUbigeo) {
        this.NombreUbigeo = NombreUbigeo;
    }

    public PersonaModel getSelected() {
        return selected;
    }

    public void setSelected(PersonaModel selected) {
        this.selected = selected;
    }

}
