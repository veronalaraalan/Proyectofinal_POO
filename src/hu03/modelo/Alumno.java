/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.modelo;

import java.util.Objects;

/**
 * Alumno: Clase de entidad que representa a un estudiante universitario.
 * Almacena los datos personales del alumno, su información académica (carrera, semestre)
 * y referencias a su historial académico. Incluye campos calculados
 * fundamentales para el proceso de ranking e inscripción.
 *
 * @author EnrollEngine
 * @version 2.16.11
 */
public class Alumno {
    private long numCuenta;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreCompleto;
    private int edad;
    private String carrera;
    private int semestre;
    private String direccion;
    private String genero;
    
    /** * Valor calculado para el ranking. Es el resultado de una fórmula (Promedio * Escolaridad * Velocidad) escalado, 
     * utilizado como clave de ordenamiento principal.
     */
    private long indicadorBruto; 
    /** * Puesto final asignado al alumno en el ranking (1, 2, 3...). 
     * Representa el orden final de inscripción del alumno.
     */
    private int numInscripcionFinal; 
    
    /** Objeto que almacena y gestiona el historial académico del alumno (calificaciones, estatus). */
    private RegistroAcademico registroAcademico;

    /**
     * Constructor principal para inicializar un objeto Alumno.
     * @param numCuenta Número único de cuenta del alumno. Es inmutable.
     * @param primerNombre Primer nombre del alumno.
     * @param segundoNombre Segundo nombre del alumno (puede ser {@code null} o vacío).
     * @param primerApellido Primer apellido del alumno.
     * @param segundoApellido Segundo apellido del alumno.
     * @param edad Edad del alumno.
     * @param carrera Carrera que cursa el alumno.
     * @param semestre Semestre actual del alumno.
     * @param direccion Dirección de residencia del alumno.
     * @param genero Género del alumno ("M", "F", u otro).
     */
    public Alumno(long numCuenta, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, int edad, String carrera, int semestre, String direccion, String genero) {
        this.numCuenta = numCuenta;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.direccion = direccion;
        this.genero = genero;
        actualizarNombreCompleto(); 
        this.indicadorBruto = 0; // Se calcula posteriormente en el módulo de ranking
        this.numInscripcionFinal = 0; // Se asigna al final del proceso de ranking
        this.registroAcademico = null; // Debe ser asignado con un setter
    }

    /**
     * Reconstruye el nombre completo a partir de los atributos de nombre y apellido,
     * manejando correctamente la posibilidad de que el segundo nombre esté ausente.
     */
    public void actualizarNombreCompleto() {
        this.nombreCompleto = this.primerNombre + 
                              (this.segundoNombre != null && !this.segundoNombre.isEmpty() ? " " + this.segundoNombre : "") + 
                              " " + this.primerApellido + " " + this.segundoApellido;
    }

    // --- Getters ---
    /** @return El nombre completo del alumno. */
    public String getNombreCompleto() { return nombreCompleto; }
    /** @return El número de cuenta único del alumno. */
    public long getNumCuenta() { return numCuenta; }
    /** @return El primer nombre del alumno. */
    public String getPrimerNombre() { return primerNombre; }
    /** @return El segundo nombre del alumno (puede ser {@code null}). */
    public String getSegundoNombre() { return segundoNombre; }
    /** @return El primer apellido del alumno. */
    public String getPrimerApellido() { return primerApellido; }
    /** @return El segundo apellido del alumno. */
    public String getSegundoApellido() { return segundoApellido; }
    /** @return La edad del alumno. */
    public int getEdad() { return edad; }
    /** @return La carrera que cursa el alumno. */
    public String getCarrera() { return carrera; }
    /** @return El semestre actual del alumno. */
    public int getSemestre() { return semestre; }
    /** @return La dirección del alumno. */
    public String getDireccion() { return direccion; }
    /** @return El género del alumno. */
    public String getGenero() { return genero; }
    /** @return El {@link RegistroAcademico} asociado al alumno. */
    public RegistroAcademico getRegistroAcademico() { return registroAcademico; }

    /**
     * Obtiene el Indicador Escolar Bruto (valor long calculado para el ranking).
     * @return El valor del Indicador Bruto.
     */
    public long getIndicadorBruto() { return indicadorBruto; } 
    
    /**
     * Obtiene el número de inscripción final (puesto en el ranking).
     * @return El puesto final asignado (1, 2, 3...).
     */
    public int getNumInscripcionFinal() { return numInscripcionFinal; } 

    // --- Setters ---
    /** * Establece el primer nombre y actualiza el nombre completo.
     * @param primerNombre El nuevo primer nombre. 
     */
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; actualizarNombreCompleto(); }
    /** * Establece el segundo nombre y actualiza el nombre completo.
     * @param segundoNombre El nuevo segundo nombre. 
     */
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; actualizarNombreCompleto(); }
    /** * Establece el primer apellido y actualiza el nombre completo.
     * @param primerApellido El nuevo primer apellido. 
     */
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; actualizarNombreCompleto(); }
    /** * Establece el segundo apellido y actualiza el nombre completo.
     * @param segundoApellido El nuevo segundo apellido. 
     */
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; actualizarNombreCompleto(); }
    /** @param edad La nueva edad. */
    public void setEdad(int edad) { this.edad = edad; }
    /** @param semestre El nuevo semestre. */
    public void setSemestre(int semestre) { this.semestre = semestre; }
    /** @param direccion La nueva dirección. */
    public void setDireccion(String direccion) { this.direccion = direccion; }
    /** @param genero El nuevo género. */
    public void setGenero(String genero) { this.genero = genero; }
    
    /**
     * Asigna el objeto {@link RegistroAcademico} asociado a este alumno.
     * @param registroAcademico El historial académico del alumno.
     */
    public void setRegistroAcademico(RegistroAcademico registroAcademico) { this.registroAcademico = registroAcademico; }
    
    /**
     * Establece el valor del Indicador Escolar Bruto.
     * @param indicadorBruto El valor calculado para el ranking.
     */
    public void setIndicadorBruto(long indicadorBruto) { this.indicadorBruto = indicadorBruto; } 
    
    /**
     * Establece el número de inscripción final (puesto en el ranking).
     * @param numInscripcionFinal El puesto final asignado.
     */
    public void setNumInscripcionFinal(int numInscripcionFinal) { this.numInscripcionFinal = numInscripcionFinal; } 

    /**
     * Implementación de {@code toString()} para una representación legible del objeto Alumno,
     * enfocada en los datos clave de identificación y ranking.
     * @return Cadena que representa el estado del Alumno.
     */
    @Override
    public String toString() {
        return "Alumno{" +
               "numCuenta=" + numCuenta +
               ", nombreCompleto='" + nombreCompleto + '\'' +
               ", carrera='" + carrera + '\'' +
               ", semestre=" + semestre +
               ", indicador=" + indicadorBruto +
               ", numInscripcion=" + numInscripcionFinal +
               '}';
    }

    /**
     * Implementación de {@code equals()} para comparar alumnos. Dos alumnos se consideran
     * iguales si tienen el mismo número de cuenta, que actúa como clave primaria.
     * @param o Objeto a comparar.
     * @return {@code true} si los objetos son iguales (mismo numCuenta), {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return numCuenta == alumno.numCuenta;
    }

    /**
     * Implementación de {@code hashCode()} basada en el {@code numCuenta}. Es consistente con {@link #equals(Object)}.
     * @return Código hash del alumno.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numCuenta);
    }
}