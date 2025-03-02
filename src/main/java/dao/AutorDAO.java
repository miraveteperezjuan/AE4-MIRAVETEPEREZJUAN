package dao;

import database.HibernateUtil;
import model.Autor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AutorDAO {

    Session session;

    public void crearAutor(Autor autor) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(autor);
        session.getTransaction().commit();
        session.close();
    }

    public Autor getAutor(int id){
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Autor autor = session.get(Autor.class,id);
        session.getTransaction().commit();
        session.close();
        return autor;
    }

    public List<Autor> getAllAutores(){
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query<Autor> query = session.createQuery("FROM Autor", Autor.class);
        List<Autor> listaAutor = query.list();
        session.getTransaction().commit();
        session.close();
        return listaAutor;
    }

    public List<Autor> obtenerTodosAutor() {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query<Autor> query = session.createQuery("FROM Autor", Autor.class);
        List<Autor> listaAutores = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return listaAutores;
    }
}
