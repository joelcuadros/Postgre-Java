package controller;

import dao.RegistroD;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.RegistroM;

@Named(value = "registro")
@SessionScoped
public class Registro implements Serializable {

    RegistroD dao = new RegistroD();
    RegistroM model = new RegistroM();

    public void ingresar() throws Exception{
        try {
            dao.AddReg(model);
        } catch (Exception e) {
            throw e;
        }
    }

    public RegistroD getDao() {
        return dao;
    }

    public void setDao(RegistroD dao) {
        this.dao = dao;
    }

    public RegistroM getModel() {
        return model;
    }

    public void setModel(RegistroM model) {
        this.model = model;
    }
    
}

