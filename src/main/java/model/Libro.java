package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "libro")
public class Libro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String titulo;

    @Column
    private double precio;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    @ManyToMany(mappedBy = "libros", fetch = FetchType.EAGER)
    private List<Libreria> librerias;

    public Libro(String titulo, double precio, Autor autor, Editorial editorial) {
        this.titulo = titulo;
        this.precio = precio;
        this.autor = autor;
        this.editorial = editorial;
    }
}
