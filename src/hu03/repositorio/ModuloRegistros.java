/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.repositorio;

import hu03.modelo.Alumno;
import hu03.modelo.RegistroAcademico;
import hu03.modelo.Asignatura;
import hu03.utilidades.CalculadoraIndicador;
import java.util.*;

/**
 * ModuloRegistros: Clase central del repositorio que gestiona la información de la carrera.
 * Es responsable de mantener el plan de estudios (asignaturas por semestre) y la lista de alumnos.
 * Contiene la lógica para la búsqueda de alumnos y la generación aleatoria de los Registros Académicos
 * y los Indicadores Brutos.
 * @author EnrollEngine
 * @version 4.12.21
 */
public class ModuloRegistros {
    /**
     * Lista principal que agrupa las listas de asignaturas por semestre (índice 0 = Semestre 1).
     */
    private List<List<Asignatura>> asignaturas;
    
    // Listas individuales para cada semestre
    private List<Asignatura> semestre1;
    private List<Asignatura> semestre2;
    private List<Asignatura> semestre3;
    private List<Asignatura> semestre4;
    private List<Asignatura> semestre5;
    private List<Asignatura> semestre6;
    private List<Asignatura> semestre7;
    private List<Asignatura> semestre8;
    private List<Asignatura> semestre9;
    private List<Asignatura> semestre10;
    
    /**
     * Lista que almacena todos los objetos Alumno gestionados por el módulo.
     */
    private List<Alumno> alumnos;

    /**
     * Objeto Random estático para la generación de datos aleatorios (calificaciones, selección de materias).
     */
    private static final Random RANDOM = new Random();

    /**
     * Constructor que inicializa todas las listas de asignaturas, la lista de alumnos
     * y carga el plan de estudios llamando a {@link #inicializarAsignaturas()}.
     */
    public ModuloRegistros() {
        // Inicialización de las listas de semestres individuales
        this.semestre1 = new ArrayList<>();
        this.semestre2 = new ArrayList<>();
        this.semestre3 = new ArrayList<>();
        this.semestre4 = new ArrayList<>();
        this.semestre5 = new ArrayList<>();
        this.semestre6 = new ArrayList<>();
        this.semestre7 = new ArrayList<>();
        this.semestre8 = new ArrayList<>();
        this.semestre9 = new ArrayList<>();
        this.semestre10 = new ArrayList<>();
        this.alumnos = new ArrayList<>();

        // Agrupación de las listas en la lista principal 'asignaturas'
        this.asignaturas = new ArrayList<>(
            Arrays.asList(semestre1, semestre2, semestre3, semestre4, semestre5,
                          semestre6, semestre7, semestre8, semestre9, semestre10)
        );

        inicializarAsignaturas();
    }

    /**
     * Obtiene la lista anidada de asignaturas, agrupadas por semestre.
     * @return Una lista de listas de Asignatura.
     */
    public List<List<Asignatura>> getAsignaturas() {
        return asignaturas;
    }

    /**
     * Obtiene la lista de todos los alumnos registrados.
     * @return La lista de objetos Alumno.
     */
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    /**
     * Busca y devuelve todos los alumnos que tienen un apellido coincidente (primer o segundo apellido)
     * con el término de búsqueda, ignorando mayúsculas y minúsculas.
     * @param apellidoBusqueda El apellido a buscar.
     * @return Una lista de objetos Alumno que coinciden con el apellido.
     */
    public List<Alumno> buscarAlumnosPorApellido(String apellidoBusqueda) {
        List<Alumno> resultados = new ArrayList<>();
        String terminoNormalizado = apellidoBusqueda.toLowerCase();

        for (Alumno alumno : this.alumnos) {
            if (alumno.getPrimerApellido().toLowerCase().equals(terminoNormalizado) ||
                alumno.getSegundoApellido().toLowerCase().equals(terminoNormalizado)) {

                resultados.add(alumno);
            }
        }
        return resultados;
    }


    /**
     * Inicializa el plan de estudios de 10 semestres, creando y agregando las 50 asignaturas
     * con sus respectivos nombres, créditos e IDs.
     */
    private void inicializarAsignaturas(){
        // Semestre 1
        agregarAsignatura("Calculo Diferencial", 5, 1);
        agregarAsignatura("Algebra Lineal", 5, 2);
        agregarAsignatura("Fundamentos de Programacion", 6, 3);
        agregarAsignatura("Introduccion a la Ingenieria en Computacion", 4, 4);
        agregarAsignatura("Fundamentos de Electronica", 6, 5);

        // Semestre 2
        agregarAsignatura("Calculo Integral", 5, 6);
        agregarAsignatura("Programacion Orientada a Objetos", 6, 7);
        agregarAsignatura("Circuitos Electricos", 6, 8);
        agregarAsignatura("Matematicas Discretas", 4, 9);
        agregarAsignatura("Arquitectura de Computadoras I", 5, 10);

        // Semestre 3
        agregarAsignatura("Calculo Multivariable", 5, 11);
        agregarAsignatura("Estructuras de Datos", 6, 12);
        agregarAsignatura("Probabilidad y Estadistica", 4, 13);
        agregarAsignatura("Arquitectura de Computadoras II", 5, 14);
        agregarAsignatura("Electronica Digital", 6, 15);

        // Semestre 4
        agregarAsignatura("Sistemas Operativos", 6, 16);
        agregarAsignatura("Metodos Numericos", 5, 17);
        agregarAsignatura("Teoria de la Computacion", 4, 18);
        agregarAsignatura("Seniales y Sistemas", 5, 19);
        agregarAsignatura("Programacion de Sistemas", 6, 20);

        // Semestre 5
        agregarAsignatura("Bases de Datos I", 6, 21);
        agregarAsignatura("Redes de Computadoras I", 5, 22);
        agregarAsignatura("Microcontroladores", 6, 23);
        agregarAsignatura("Programacion Web", 5, 24);
        agregarAsignatura("Ingenieria de Software I", 4, 25);

        // Semestre 6
        agregarAsignatura("Bases de Datos II", 5, 26);
        agregarAsignatura("Redes de Computadoras II", 5, 27);
        agregarAsignatura("Compiladores", 6, 28);
        agregarAsignatura("Sistemas Embebidos", 6, 29);
        agregarAsignatura("Ingenieria de Software II", 4, 30);

        // Semestre 7
        agregarAsignatura("Sistemas Distribuidos", 6, 31);
        agregarAsignatura("Seguridad Informatica", 5, 32);
        agregarAsignatura("Modelado y Simulacion", 5, 33);
        agregarAsignatura("Interfaces Hombre-Maquina", 5, 34);
        agregarAsignatura("Inteligencia Artificial", 5, 35);

        // Semestre 8
        agregarAsignatura("Internet de las Cosas", 6, 36);
        agregarAsignatura("Vision por Computadora", 6, 37);
        agregarAsignatura("Computacion en la Nube", 5, 38);
        agregarAsignatura("Redes Inalambricas Avanzadas", 5, 39);
        agregarAsignatura("Gerencia de Proyectos Tecnologicos", 4, 40);

        // Semestre 9
        agregarAsignatura("Aprendizaje Automatico", 6, 41);
        agregarAsignatura("Ciberseguridad Avanzada", 5, 42);
        agregarAsignatura("Robotica", 6, 43);
        agregarAsignatura("Alta Disponibilidad y Tolerancia a Fallos", 5, 44);
        agregarAsignatura("Gestion de Infraestructura TI", 4, 45);

        // Semestre 10
        agregarAsignatura("Realidad Virtual y Aumentada", 6, 46);
        agregarAsignatura("Introduccion a la Computación Cuántica", 4, 47);
        agregarAsignatura("Proyecto Terminal I", 10, 48);
        agregarAsignatura("Ética Profesional y Responsabilidad Tecnologica", 3, 49);
        agregarAsignatura("Emprendimiento Tecnologico", 3, 50);
    }

    /**
     * Agrega una nueva asignatura al semestre correspondiente según su ID.
     * @param nombre Nombre de la asignatura.
     * @param creditos Créditos de la asignatura.
     * @param id ID único de la asignatura (utilizado para determinar el semestre).
     */
    private void agregarAsignatura(String nombre, int creditos, int id){
        // La lógica asume bloques de 5 asignaturas por semestre
        int indiceSemestre = (id - 1) / 5;
        
        if (indiceSemestre >= 0 && indiceSemestre < asignaturas.size()) {
            asignaturas.get(indiceSemestre).add(new Asignatura(id, nombre, creditos));
        }
    }

    /**
     * Genera un {@link RegistroAcademico} completo para el alumno, incluyendo la selección
     * aleatoria de asignaturas cursadas, sus calificaciones y el cálculo de su
     * Indicador Bruto.
     * * La selección de materias se basa en un "Bloque Móvil":
     * - Semestre 1: Solo materias del S1.
     * - Semestre 2+: Materias del historial completo anterior (S1 hasta S_Actual-2)
     * más las materias del bloque móvil (S_Actual-1, S_Actual, S_Actual+1).
     * * @param alumno El objeto Alumno para el cual se generará el registro.
     */
    public void generarRegistroAcademico(Alumno alumno){
        RegistroAcademico registro = new RegistroAcademico(alumno);

        List<Asignatura> asignaturasDisponiblesEnBloque = new ArrayList<>();
        int semestreActual = alumno.getSemestre(); 
        
        // =======================================================
        // LÓGICA DE DEFINICIÓN DEL POOL DE ASIGNATURAS
        // =======================================================
        
        if (semestreActual == 1) {
            // S1: Solo las materias del semestre 1. El índice del S1 es 0.
            if (asignaturas.size() > 0) {
                 asignaturasDisponiblesEnBloque.addAll(asignaturas.get(0));
            }
        } else {
            // Lógica del Bloque Móvil (Semestre 2 al 10)
            
            // Define el inicio del bloque móvil (S_A-1)
            int semestreInicioBloque = Math.max(1, semestreActual - 1); 
            // Define el fin del bloque móvil (S_A+1 o máximo S10)
            int semestreFinBloque = Math.min(10, semestreInicioBloque + 2);

            // 1. Obtener las asignaturas del Bloque Móvil (S_A-1, S_A, S_A+1)
            for (int s = semestreInicioBloque; s <= semestreFinBloque; s++) {
                // Convertir número de semestre a índice de lista (s-1)
                if (s - 1 < asignaturas.size()) {
                    asignaturasDisponiblesEnBloque.addAll(asignaturas.get(s - 1));
                }
            }

            // 2. Lógica de Seguimiento (Historial Completo: S1 hasta S_Inicio-1)
            List<Asignatura> asignaturasHistorialAnterior = new ArrayList<>();
            
            if (semestreInicioBloque > 1) {
                // Recorrer desde el S1 (índice 0) hasta el semestre antes de que inicie el bloque (S_A-2)
                for (int i = 0; i < semestreInicioBloque - 1; i++) {
                     if (i < asignaturas.size()) {
                         asignaturasHistorialAnterior.addAll(asignaturas.get(i));
                     }
                }
            }
            // 3. Agregar materias del historial al pool total
            asignaturasDisponiblesEnBloque.addAll(asignaturasHistorialAnterior);
        }
        
        // =======================================================
        // LÓGICA DE SELECCIÓN FINAL Y ASIGNACIÓN DE CALIFICACIONES
        // =======================================================

        // Usar un Set para eliminar duplicados si las materias del historial coinciden con el bloque móvil
        Set<Asignatura> asignaturasUnicas = new HashSet<>(asignaturasDisponiblesEnBloque); 
        List<Asignatura> poolList = new ArrayList<>(asignaturasUnicas);
        
        int totalAsignaturasDisponibles = poolList.size();
        
        if (totalAsignaturasDisponibles == 0) {
            alumno.setRegistroAcademico(registro); 
            return; 
        }
        
        // 1. Definir rango de materias a cursar
        int minAsignaturas;
        int maxAsignaturas;
        
        if (semestreActual == 1) {
            // S1: Fijo en 5 materias.
            minAsignaturas = 5; 
            maxAsignaturas = 5;
        } else {
            // S2+: Rango normal 5-10.
            minAsignaturas = 5;
            maxAsignaturas = 10;
        }

        // 2. Calcular la cantidad final a cursar
        maxAsignaturas = Math.min(maxAsignaturas, totalAsignaturasDisponibles); 
        
        int asignaturasACursar;
        
        if (semestreActual == 1) {
            asignaturasACursar = Math.min(5, totalAsignaturasDisponibles); // Siempre 5 o el total disponible
        } else {
            // Para S2+, elige un valor aleatorio entre [min, max], ajustado al total disponible
            int rango = maxAsignaturas - minAsignaturas + 1;
            // Garantiza que se seleccione al menos el mínimo o el total disponible si es menor.
            asignaturasACursar = Math.min(totalAsignaturasDisponibles, minAsignaturas + (rango > 0 ? RANDOM.nextInt(rango) : 0));
        }

        // 3. Seleccionar asignaturas aleatorias y asignar calificaciones
        Set<Integer> indicesSeleccionados = new HashSet<>();

        while(indicesSeleccionados.size() < asignaturasACursar) {
            int indiceAleatorio = RANDOM.nextInt(totalAsignaturasDisponibles);
            indicesSeleccionados.add(indiceAleatorio);
        }

        for (int indice : indicesSeleccionados) {
            Asignatura asignatura = poolList.get(indice);
            double calificacion = generarCalificacion(); // Calificación entre 5.0 y 9.0
            registro.agregarAsignatura(asignatura, calificacion);
        }

        alumno.setRegistroAcademico(registro);
        
        // =======================================================
        // CÁLCULO Y ASIGNACIÓN DEL INDICADOR BRUTO
        // =======================================================
        
        double promedio = registro.calcularPromedio();
        int asigInsc = registro.getAsignaturasInscritas(); 
        int asigAprob = registro.getAsignaturasAprobadas();
        int creditos = registro.getTotalCreditos();
        
        // 4. Calcular el Indicador Bruto
        long indicadorBruto = CalculadoraIndicador.calcularIndicadorBruto(
            promedio, 
            asigAprob, 
            asigInsc, 
            creditos, 
            semestreActual
        );
        
        // 5. Asignar el Indicador Bruto
        alumno.setIndicadorBruto(indicadorBruto);
    }


    /**
     * Genera una calificación aleatoria en el rango de 5.0 a 9.0, simulando 
     * un rendimiento académico variable.
     * @return Una calificación double entre 5.0 (inclusive) y 9.0 (exclusive).
     */
    private double generarCalificacion(){
        return 5.0 + (RANDOM.nextDouble()*4.0); // Calificaciones entre 5.0 y 9.0
    }

    /**
     * Agrega un objeto Alumno a la lista de alumnos gestionados por el módulo.
     * @param alumno El objeto Alumno a agregar.
     */
    public void agregarAlumno(Alumno alumno){
        alumnos.add(alumno);
    }

    /**
     * Imprime en la consola el listado completo de todas las asignaturas
     * disponibles en el plan de estudios, junto con el total.
     */
    public void mostrarAsignaturas(){
        System.out.println("Asignaturas Disponibles");
        for(List<Asignatura> listaSemestre : asignaturas){
            for(Asignatura asignatura : listaSemestre){
                System.out.printf("%s (%d creditos)%n",asignatura.getNombre(), asignatura.getCreditos());
            }
        }
        int totalAsignaturas = asignaturas.stream().mapToInt(List::size).sum();
        System.out.println("Total: "+ totalAsignaturas + " asignaturas");
    }

}