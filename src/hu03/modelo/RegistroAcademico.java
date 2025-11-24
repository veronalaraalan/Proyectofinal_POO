/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.modelo;

import hu03.utilidades.CalculadoraIndicador; // Importación no usada, pero mantenida
import java.util.*;

/**
 * RegistroAcademico: Gestiona el historial académico de un alumno.
 * Almacena las asignaturas cursadas y sus calificaciones.
 * Mantiene estadísticas como el total de créditos y permite calcular el promedio.
 * @author EnrollEngine
 * @version 3.7.16
 */
public class RegistroAcademico {
    /**
     * Referencia al alumno dueño de este registro.
     */
    private Alumno alumno;
    
    /**
     * Mapa que almacena las asignaturas (clave) y la calificación obtenida (valor).
     */
    private Map<Asignatura, Double> asignaturasCursadas;
    
    /**
     * Suma total de los créditos de todas las asignaturas cursadas.
     */
    private int totalCreditos;

    /**
     * Constructor por defecto (usado para inicializaciones nulas o testing).
     */
    public RegistroAcademico() {
    }

    
    /**
     * Constructor principal que inicializa el registro para un alumno específico.
     * @param alumno El objeto Alumno al que pertenece este registro.
     */
    public RegistroAcademico(Alumno alumno) {
        this.alumno = alumno;
        this.asignaturasCursadas = new HashMap<>();
        this.totalCreditos = 0;
    }

    // Getters
    /**
     * Obtiene el alumno asociado a este registro.
     * @return El objeto Alumno.
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Obtiene el mapa de asignaturas cursadas con sus calificaciones.
     * @return Mapa donde la clave es la Asignatura y el valor es la calificación (Double).
     */
    public Map<Asignatura, Double> getAsignaturasCursadas() {
        return asignaturasCursadas;
    }

    /**
     * Obtiene la suma total de créditos acumulados por el alumno.
     * @return El total de créditos.
     */
    public int getTotalCreditos() {
        return totalCreditos;
    }
    
    
    /**
     * Añade una asignatura y su calificación al registro.
     * También actualiza el total de créditos acumulados.
     * @param asignatura El objeto Asignatura cursada.
     * @param calificacion La calificación obtenida.
     */
    public void agregarAsignatura(Asignatura asignatura, double calificacion){
        asignaturasCursadas.put(asignatura, calificacion);
        totalCreditos += asignatura.getCreditos();
    }
    
    /**
     * Calcula el promedio de todas las calificaciones registradas.
     * @return El promedio de calificaciones (0.0 si no hay asignaturas).
     */
    public double calcularPromedio(){
        if(asignaturasCursadas.isEmpty()){
            return 0.0;
        }
        double suma = 0.0;
        for (double calificacion : asignaturasCursadas.values()) {
            suma += calificacion;
        }
        return suma/asignaturasCursadas.size();
    }
    
    
    /**
     * Devuelve el número total de asignaturas cursadas (inscritas) registradas.
     * @return Cantidad de asignaturas en el registro.
     */
    public int getAsignaturasInscritas() {
        return asignaturasCursadas.size(); 
    }

    /**
     * Devuelve el número de asignaturas con calificación aprobatoria (>= 6.0).
     * @return Cantidad de asignaturas aprobadas.
     */
    public int getAsignaturasAprobadas() {
        int aprobadas = 0;
        final double CALIFICACION_MINIMA_APROBATORIA = 6.0; 

        for (double calificacion : asignaturasCursadas.values()) {
            if (calificacion >= CALIFICACION_MINIMA_APROBATORIA) { 
                aprobadas++;
            }
        }
        return aprobadas;
    }
    
    
    /**
     * Imprime un reporte detallado del registro académico del alumno, 
     * incluyendo datos personales, indicadores de mérito y el detalle de asignaturas.
     */
    public void mostrarRegistro() {
        // --- 1. Datos Generales y de Identificación ---
        System.out.println("\n=============================================");
        System.out.println("         FICHA DE REGISTRO ACADÉMICO        ");
        System.out.println("=============================================");
        System.out.println("Alumno: " + alumno.getNombreCompleto());
        System.out.println("Numero de Cuenta: " + alumno.getNumCuenta());
        System.out.println("Edad: " + alumno.getEdad());
        System.out.println("Carrera: " + alumno.getCarrera());
        System.out.println("Semestre: " + alumno.getSemestre());
        System.out.println("Direccion: " + alumno.getDireccion());
        
        // --- 2. Indicadores de Mérito ---
        System.out.println("---------------------------------------------");
        
        // Imprimir el puesto (Num. Inscripción Final)
        if (alumno.getNumInscripcionFinal() > 0) {
             System.out.printf("Num. Inscripcion Asignado: %d%n", alumno.getNumInscripcionFinal());
        } else {
             System.out.println("Num. Inscripcion Asignado: PENDIENTE DE CALCULO GLOBAL");
        }
        
        // Imprimir el Indicador Bruto
        System.out.printf("Indicador Escolar: %-12d%n", alumno.getIndicadorBruto());
        System.out.println("---------------------------------------------");
        
        // --- 3. Asignaturas Cursadas ---
        System.out.println("      Asignaturas Cursadas      ");
        
        for (Map.Entry<Asignatura, Double> entry : asignaturasCursadas.entrySet()) {
            Asignatura asignatura = entry.getKey();
            double calificacion = entry.getValue();
            System.out.printf("- %s | Calificacion: %.1f%n", 
                asignatura.toString(), calificacion);
        }
        
        // --- 4. Resumen Final ---
        System.out.println("---------------------------------------------");
        System.out.printf("Total de creditos: %d%n", totalCreditos);
        System.out.printf("Promedio: %.2f%n", calcularPromedio());
        System.out.println("=============================================\n");
    }
}