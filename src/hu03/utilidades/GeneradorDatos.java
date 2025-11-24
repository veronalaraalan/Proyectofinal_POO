/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.utilidades;

import hu03.modelo.Alumno;
import hu03.repositorio.ModuloRegistros;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * GeneradorDatos: Clase de utilidad estática encargada de crear datos simulados para la aplicación.
 * Esto incluye la generación de nombres, apellidos, números de cuenta, edades, semestres,
 * y la carga de direcciones desde un archivo externo.
 * Es crucial para inicializar el sistema con una gran cantidad de objetos Alumno.
 * @author EnrollEngine
 * @version 3.9.14
 */
public class GeneradorDatos {
    
    /** Número fijo de alumnos a generar. */
    private static final int NUM_ALUMNOS_REQUERIDOS = 1000;
    /** Contador estático para asegurar que cada número de cuenta es único. */
    private static long contadorNumCuenta = 1000000; 
    /** Objeto Random estático para todas las operaciones de aleatoriedad. */
    private static final Random RANDOM = new Random();

    // Estructuras de nombres separadas por género
    private static final List<String> NOMBRES_MASCULINOS = Arrays.asList(
        "Alejandro", "Javier", "Miguel", "Ricardo", "Andres", "Fernando", "Santiago", "Carlos", "Pablo", "Luis",
        "Jorge", "Manuel", "Angel", "Diego", "Hector", "Marco", "Juan", "Rafael", "Antonio", "Gustavo",
        "Roberto", "David", "Francisco", "Mario", "Jose", "Arturo", "Guillermo", "Alonso", "Benito", "Cesar",
        "Hector", "Julian", "Daniel", "Emilio", "Israel", "Jesus", "Pedro", "Alfredo", "Ernesto", "Felipe",
        "Ramon", "Victor", "Sergio", "Oscar", "Enrique", "Mauricio", "Gerardo", "Adolfo", "Ruben", "Ivan"
    );

    private static final List<String> NOMBRES_FEMENINOS = Arrays.asList(
        "Sofia", "Valeria", "Isabella", "Camila", "Mariana", "Luciana", "Daniela", "Emma", "Regina", "Natalia",
        "Nicole", "Victoria", "Ximena", "Adriana", "Fernanda", "Andrea", "Gabriela", "Paulina", "Emilia", "Catalina",
        "Maria", "Claudia", "Elena", "Sara", "Veronica", "Diana", "Laura", "Julia", "Gloria", "Esther",
        "Ana", "Beatriz", "Carla", "Dulce", "Erika", "Irene", "Jimena", "Leticia", "Monica", "Nadia",
        "Olga", "Patricia", "Quetzali", "Rosa", "Tania", "Ursula", "Vanesa", "Yolanda", "Zoe", "Alma"
    );

    // Estructura con 50 apellidos
    private static final List<String> APELLIDOS = Arrays.asList(
        "Garcia", "Rodriguez", "Gonzalez", "Fernandez", "Lopez", "Martinez", "Sanchez", "Perez", "Gomez", "Diaz",
        "Vazquez", "Moreno", "Jimenez", "Ruiz", "Hernandez", "Torres", "Rivera", "Flores", "Ramirez", "Reyes",
        "Morales", "Ortiz", "Gutierrez", "Castillo", "Mendoza", "Contreras", "Vargas", "Silva", "Rojas", "Herrera",
        "Castro", "Blanco", "Navarro", "Soto", "Alonso", "Nunez", "Molina", "Aguilar", "Delgado", "Vega",
        "Cabrera", "Maldonado", "Cruz", "Estrada", "Guerrero", "Ramos", "Salazar", "Montes", "Miranda", "Padilla"
    );
    
    /** Lista que contendrá las direcciones cargadas desde un archivo externo. */
    private static final List<String> DIRECCIONES = new ArrayList<>();

    
    /**
     * Carga las direcciones desde un archivo de texto en la ruta especificada.
     * Cada línea del archivo se considera una dirección única. Muestra mensajes de
     * estado y errores en la consola.
     * @param rutaArchivo La ruta del archivo de texto que contiene las direcciones.
     */
    public static void cargarDirecciones(String rutaArchivo) {
        System.out.println("\n--- INICIO DE CARGA DE DATOS ---");
        System.out.println("Cargando direcciones desde el archivo: " + rutaArchivo);
        try {
            File archivo = new File(rutaArchivo); 
            Scanner scanner = new Scanner(archivo);
            
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty()) { 
                    DIRECCIONES.add(linea);
                }
            }
            scanner.close();
            
            System.out.println("Carga finalizada. Total de direcciones cargadas: " + DIRECCIONES.size());
            if (DIRECCIONES.size() < NUM_ALUMNOS_REQUERIDOS) {
                 System.err.println("ADVERTENCIA: Se esperaban " + NUM_ALUMNOS_REQUERIDOS + " direcciones. Solo se cargaron " + DIRECCIONES.size() + ".");
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("ERROR CRITICO: No se pudo encontrar el archivo de direcciones en la ruta: " + rutaArchivo);
        }
        System.out.println("--- FIN DE CARGA DE DATOS ---\n");
    }

    /**
     * Genera un número de cuenta único e incremental para cada nuevo alumno.
     * @return El siguiente número de cuenta disponible (long).
     */
    public static long generarNumCuenta() {
        return contadorNumCuenta++;
    }

    /**
     * Genera un número predefinido de objetos {@link Alumno} con datos aleatorios
     * (nombre, edad, semestre, etc.), los agrega al {@link ModuloRegistros} y
     * automáticamente genera su {@link hu03.modelo.RegistroAcademico}.
     * @param modulo La instancia de ModuloRegistros donde se agregarán los alumnos.
     */
    public static void generarAlumnos(ModuloRegistros modulo) {
        if (DIRECCIONES.isEmpty()) {
            System.err.println("ERROR: No se pueden generar alumnos sin direcciones cargadas.");
            return;
        }
        System.out.println("Iniciando la generacion de " + NUM_ALUMNOS_REQUERIDOS + " alumnos...");

        for (int i = 0; i < NUM_ALUMNOS_REQUERIDOS; i++) {
            
            String[] nombreYGenero = generarNombreConGeneroBase();
            String primerNombre = nombreYGenero[0];
            String genero = nombreYGenero[1];
            
            // Genera un segundo nombre aleatorio o una cadena vacía (50% de probabilidad de tener 2 nombres)
            String segundoNombre = RANDOM.nextBoolean() ? generarSegundoNombre(genero) : ""; 
            
            String primerApellido = generarApellido();
            String segundoApellido = generarApellido();
            
            // Genera una edad entre 18 y 24
            int edad = 18 + RANDOM.nextInt(7); 
            
            // Lógica para limitar el semestre máximo según la edad, simulando un avance natural
            int semestreMaximo;
            if (edad == 18) {
                semestreMaximo = 2;
            } else if (edad == 19) {
                semestreMaximo = 4;
            } else if (edad == 20) {
                semestreMaximo = 6;
            } else if (edad == 21) {
                semestreMaximo = 8;
            } else { 
                semestreMaximo = 10;
            }
            int semestre = 1 + RANDOM.nextInt(semestreMaximo);
            long numCuenta = generarNumCuenta(); 
            String carrera = "Ingenieria en Computacion"; 
            String direccion = generarDireccion();
            
            // Creación del objeto Alumno
            Alumno nuevoAlumno = new Alumno(
                    numCuenta, primerNombre, segundoNombre, primerApellido, segundoApellido, 
                    edad, carrera, semestre, direccion, genero
            );
            
            modulo.agregarAlumno(nuevoAlumno);
            // Genera su historial académico y el Indicador Bruto
            modulo.generarRegistroAcademico(nuevoAlumno);
        }

        System.out.println("Generacion de alumnos completada. Total de alumnos en el modulo: " + modulo.getAlumnos().size());
    }
    
    /**
     * Obtiene una dirección seleccionada al azar de la lista cargada.
     * @return Una cadena que representa una dirección.
     */
    public static String generarDireccion() {
        if (DIRECCIONES.isEmpty()) {
            return "Direccion no asignada (ERROR de carga)";
        }
        int indice = RANDOM.nextInt(DIRECCIONES.size());
        return DIRECCIONES.get(indice);
    }
    
    /**
     * Decide aleatoriamente el género del alumno y selecciona un nombre base
     * de la lista correspondiente.
     * @return Un array de String donde [0] es el primer nombre y [1] es el género ("M" o "F").
     */
    public static String[] generarNombreConGeneroBase() {
        boolean esMasculino = RANDOM.nextBoolean();

        if (esMasculino) {
            int indice = RANDOM.nextInt(NOMBRES_MASCULINOS.size());
            return new String[] {NOMBRES_MASCULINOS.get(indice), "M"};
        } else {
            int indice = RANDOM.nextInt(NOMBRES_FEMENINOS.size());
            return new String[] {NOMBRES_FEMENINOS.get(indice), "F"};
        }
    }

    /**
     * Obtiene un segundo nombre aleatorio que coincide con el género proporcionado.
     * @param genero El género ("M" o "F") para determinar la lista de nombres.
     * @return Un segundo nombre.
     */
    public static String generarSegundoNombre(String genero) {
        if ("M".equals(genero)) {
            int indice = RANDOM.nextInt(NOMBRES_MASCULINOS.size());
            return NOMBRES_MASCULINOS.get(indice);
        } else {
            int indice = RANDOM.nextInt(NOMBRES_FEMENINOS.size());
            return NOMBRES_FEMENINOS.get(indice);
        }
    }

    /**
     * Obtiene un apellido seleccionado al azar de la lista predefinida.
     * @return Un apellido.
     */
    public static String generarApellido() {
        int indice = RANDOM.nextInt(APELLIDOS.size());
        return APELLIDOS.get(indice);
    }
}