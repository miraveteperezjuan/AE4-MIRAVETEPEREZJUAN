package dao;

import database.HibernateUtil;

import model.Libreria;
import model.Libro;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class LibreriaDAO {

    Session session;

    public void crearLibreria(Libreria libreria) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(libreria);
        session.getTransaction().commit();
        session.close();
    }

    public void actualizarLibreria(Libreria libreria) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(libreria);
        session.getTransaction().commit();
        session.close();
    }

    public List<Libreria> obtenerTodasLibrerias() {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Consulta HQL para obtener todas las librerías
        Query<Libreria> query = session.createQuery("FROM Libreria", Libreria.class);

        // Ejecutar la consulta y obtener el resultado
        List<Libreria> listaLibrerias = query.getResultList();

        // Finalizar la transacción
        session.getTransaction().commit();

        // Cerrar la sesión
        session.close();

        return listaLibrerias;
    }

    public List<Libreria> obtenerLibreriasPorLibro(Libro libro) {
        session = new HibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Consulta HQL para obtener las librerías asociadas a un libro
        String querySTR = "FROM Libreria l WHERE :libroId IN (SELECT libro.id FROM l.libros libro WHERE libro.id = :libroId)";
        Query<Libreria> query = session.createQuery(querySTR, Libreria.class);
        query.setParameter("libroId", libro.getId());

        List<Libreria> librerias = query.getResultList();

        session.getTransaction().commit();
        session.close();

        return librerias;
    }



}
