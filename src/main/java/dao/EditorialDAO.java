package dao;

import database.HibernateUtil;
import model.Editorial;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EditorialDAO {

    Session session;

    public void crearEditorial(Editorial editorial) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(editorial);
        session.getTransaction().commit();
        session.close();
    }

    public Editorial getEditorial(int id){
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Editorial editorial = session.get(Editorial.class,id);
        session.getTransaction().commit();
        session.close();
        return editorial;
    }

    public List<Editorial> getAllEditorial(){
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query<Editorial> query = session.createQuery("FROM Editorial", Editorial.class);
        List<Editorial> listaEditorial = query.list();
        session.getTransaction().commit();
        session.close();
        return listaEditorial;
    }
}
