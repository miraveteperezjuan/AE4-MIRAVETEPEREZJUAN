package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "libreria")
public class Libreria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;
    @Column
    private String nombreDuenio;
    @Column
    private String direccion;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "libreria_libro", joinColumns = @JoinColumn(name = "id_libreria"),
            inverseJoinColumns = @JoinColumn(name = "id_libro"))
    private List<Libro> libros;

    public Libreria(String nombre, String nombreDuenio, String direccion) {
        this.nombre = nombre;
        this.nombreDuenio = nombreDuenio;
        this.direccion = direccion;
    }
}
