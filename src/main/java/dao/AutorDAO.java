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
        System.out.println("Autor agregado exitosamente.");
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

        // Consulta HQL para obtener todos los libros
        Query<Autor> query = session.createQuery("from Autor", Autor.class);

        // Ejecutar la consulta y obtener la lista de libros
        List<Autor> listaAutores = query.getResultList();

        // Cometer la transacción y cerrar la sesión
        session.getTransaction().commit();
        session.close();
        return listaAutores;
    }


}
