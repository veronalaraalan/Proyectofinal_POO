/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.modelo;
import java.util.Objects;

/**
 * Asignatura: Clase de entidad que representa una materia en el plan de estudios.
 * Define las propiedades básicas como identificador, nombre y créditos.
 * La igualdad entre asignaturas se basa únicamente en el ID.
 * @author EnrollEngine
 * @version 1.5.6
 */
public class Asignatura {
    /** Identificador único de la asignatura (clave primaria). */
    private int id;
    /** Nombre descriptivo de la asignatura. */
    private String nombre;
    /** Cantidad de créditos que otorga la asignatura al aprobarse. */
    private int creditos;

    /**
     * Constructor por defecto.
     */
    public Asignatura() {
    }

    /**
     * Constructor que inicializa todos los atributos de la asignatura.
     * @param id Identificador único de la asignatura.
     * @param nombre Nombre descriptivo de la asignatura.
     * @param creditos Cantidad de créditos que otorga.
     */
    public Asignatura(int id, String nombre, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    // --- Getters ---

    /**
     * Obtiene el ID único de la asignatura.
     * @return El identificador de la asignatura.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la asignatura.
     * @return El nombre de la asignatura.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la cantidad de créditos de la asignatura.
     * @return El número de créditos.
     */
    public int getCreditos() {
        return creditos;
    }

    // --- Setters ---

    /**
     * Establece el ID único de la asignatura.
     * @param id Nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre de la asignatura.
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la cantidad de créditos de la asignatura.
     * @param creditos Nuevo número de créditos.
     */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    // --- Métodos de Utilidad ---

    /**
     * Proporciona una representación en cadena de la asignatura, mostrando
     * el nombre y la cantidad de créditos.
     * @return Una cadena formateada: "Nombre (X creditos)".
     */
    @Override
    public String toString() {
        return String.format("%s (%d creditos)", nombre, creditos );
    }
    
    /**
     * Compara dos objetos Asignatura para determinar si son iguales.
     * La comparación se realiza únicamente en base al ID único.
     * @param obj El objeto a comparar.
     * @return true si los IDs coinciden, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asignatura that = (Asignatura) obj;
        return id == that.id;
    }
    
    /**
     * Genera un código hash basado en el ID de la asignatura.
     * Es crucial para el correcto funcionamiento en estructuras de datos como HashMap o HashSet.
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
