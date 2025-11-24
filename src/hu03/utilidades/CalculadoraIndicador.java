/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.utilidades;

/**
 * CalculadoraIndicador: Contiene la lógica estática para calcular los componentes
 * del Indicador Escolar de un alumno, incluyendo la escolaridad, la velocidad
 * y el Indicador Bruto final.
 * @author EnrollEngine
 * @version 3.7.18
 */
public class CalculadoraIndicador {

    /** Créditos teóricos que un alumno debe haber cursado en un semestre. */
    private static final int CREDITOS_POR_SEMESTRE = 26; 
    
    /**
     * Calcula la cantidad total de créditos que el alumno "debería" haber acumulado
     * hasta el semestre actual, basado en el plan de estudios teórico.
     * @param semestreActual El semestre en el que se encuentra el alumno.
     * @return El total de créditos esperados.
     */
    public static int calcularCreditosDeberiaLlevar(int semestreActual) {
        return semestreActual * CREDITOS_POR_SEMESTRE;
    }
    
    /**
     * Calcula el factor de Escolaridad (proporción de materias aprobadas).
     * Fórmula: Escolaridad = Asignaturas Aprobadas / Asignaturas Inscritas
     * @param aprobadas Número de asignaturas aprobadas.
     * @param inscritas Número de asignaturas inscritas.
     * @return El valor de Escolaridad (entre 0.0 y 1.0).
     */
    public static double calcularEscolaridad(int aprobadas, int inscritas) {
        if (inscritas <= 0) return 0.0;
        return ((double) aprobadas / inscritas); 
    }

    /**
     * Calcula el factor de Velocidad (proporción de avance de créditos).
     * Fórmula: Velocidad = Créditos Acumulados / Créditos Teóricos Esperados
     * @param creditosAlumno Total de créditos acumulados por el alumno.
     * @param creditosDeberiaLlevar Total de créditos teóricos esperados hasta su semestre.
     * @return El valor de Velocidad (un valor > 0.0, usualmente <= 1.0).
     */
    public static double calcularVelocidad(int creditosAlumno, int creditosDeberiaLlevar) {
        if (creditosDeberiaLlevar <= 0) return 0.0;
        return ((double) creditosAlumno / creditosDeberiaLlevar); 
    }

    /**
     * Calcula el Indicador Escolar Bruto, que es el valor final utilizado para
     * el ranking académico del alumno.
     * Fórmula: Indicador Bruto = Promedio * Escolaridad * Velocidad * 1000
     * El resultado se escala por 1000 y se convierte a long para mantener precisión
     * en las comparaciones y ordenamiento.
     *
     * @param promedio Promedio general de calificaciones del alumno.
     * @param asigAprobOrd Asignaturas aprobadas (usado para Escolaridad).
     * @param asigInscOrd Asignaturas inscritas (usado para Escolaridad).
     * @param credAlumno Créditos totales acumulados por el alumno (usado para Velocidad).
     * @param semestreActual El semestre actual del alumno (usado para Créditos Esperados).
     * @return El Indicador Bruto (valor long usado para ordenar).
     */
    public static long calcularIndicadorBruto(double promedio, int asigAprobOrd, int asigInscOrd, 
                                             int credAlumno, int semestreActual) {
        
        int credDeberiaLlevar = calcularCreditosDeberiaLlevar(semestreActual);
        
        double escolaridadDecimal = calcularEscolaridad(asigAprobOrd, asigInscOrd);
        double velocidadDecimal = calcularVelocidad(credAlumno, credDeberiaLlevar);
        
        double indicadorDecimal = promedio * escolaridadDecimal * velocidadDecimal;
        
        // Factor de escala 1,000L para convertir a long y mantener precisión.
        return (long) (indicadorDecimal * 1_000L); 
    }
}