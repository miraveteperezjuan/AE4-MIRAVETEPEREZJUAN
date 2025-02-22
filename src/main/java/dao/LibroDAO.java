package dao;

import database.HibernateUtil;
import model.Autor;
import model.Editorial;
import model.Libro;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class LibroDAO {

    Session session;

    public void crearLibro(Libro libro) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Autor autor = session.get(Autor.class, libro.getAutor().getId());  // Usamos get() para traer la entidad directamente
        Editorial editorial = session.get(Editorial.class, libro.getEditorial().getId());  // Lo mismo para Editorial
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        session.persist(libro);
        session.getTransaction().commit();
        session.close();
    }

    public List<Libro> obtenerTodosLibros() {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query<Libro> query = session.createQuery("from Libro", Libro.class);
        List<Libro> listaLibros = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return listaLibros;
    }

    public List<Libro> obtenerLibrosPorAutor(int autorId) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Consulta HQL para obtener los libros asociados al autor
        String querySTR = "FROM Libro j WHERE j.autor.id = :autorId";
        Query<Libro> query = session.createQuery(querySTR, Libro.class);
        query.setParameter("autorId", autorId);

        // Ejecutar la consulta y obtener la lista de libros
        List<Libro> listaLibros = query.getResultList();

        // Cometer la transacción y cerrar la sesión
        session.getTransaction().commit();
        session.close();

        return listaLibros;
    }

    public void actualizarLibro(Libro libro) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(libro);
        session.getTransaction().commit();
        session.close();
    }

    public Libro obtenerLibroPorId(int id) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Libro libro = session.get(Libro.class, id);
        session.getTransaction().commit();
        session.close();
        return libro;
    }

}
