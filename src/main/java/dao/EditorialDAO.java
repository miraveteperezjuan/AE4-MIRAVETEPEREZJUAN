package dao;

import database.HibernateUtil;
import model.Autor;
import model.Editorial;
import org.hibernate.Session;

public class EditorialDAO {

    Session session;

    public void crearEditorial(Editorial editorial) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(editorial);
        session.getTransaction().commit();
        session.close();
    }

}
