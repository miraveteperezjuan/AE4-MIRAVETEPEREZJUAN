package dao;

import database.HibernateUtil;
import model.Autor;
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
        System.out.println("Editorial agregado exitosamente.");
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

    public List<Editorial> obtenerEditorial(String nombre) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String querySTR = "FROM Editorial j WHERE j.nombre = :nombre ";
        Query<Editorial> query = session.createQuery(querySTR,Editorial.class);
        query.setParameter("nombre",nombre);
        List<Editorial> listaEditorial = query.list();
        session.getTransaction().commit();
        session.close();
        return listaEditorial;
    }

}
