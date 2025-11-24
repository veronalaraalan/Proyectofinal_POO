/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hu03.app;

import hu03.controlador.ControladorAlumnos;
import hu03.repositorio.ModuloRegistros;
import hu03.modelo.Alumno;
import hu03.utilidades.GeneradorDatos;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal (Vista) de la aplicación de gestión de registros académicos.
 * Esta clase contiene el método main y toda la lógica de interacción
 * con el usuario a través de la consola, actuando como la interfaz del usuario.
 * Es responsable de inicializar el Modelo (ModuloRegistros) y el Controlador.
 * * @author EnrollEngine
 * @version 5.12.41
 */
public class HU03 {

    /**
     * Objeto Scanner estático para la lectura de datos desde la consola.
     */
    private static final Scanner SCANNER = new Scanner(System.in);
    
    /**
     * Ruta relativa del archivo de texto que contiene las direcciones para la generación de datos.
     */
    private static final String RUTA_DIRECCIONES = "direcciones.txt"; 

    /**
     * Punto de entrada principal de la aplicación.
     * Inicializa el sistema, genera la data inicial (1000 alumnos) y
     * ejecuta el bucle principal del menú de opciones.
     * * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        
        // Inicializar el sistema de carga de datos (debe ir primero)
        GeneradorDatos.cargarDirecciones(RUTA_DIRECCIONES);

        // Inicialización de la lógica central del negocio
        ModuloRegistros modulo = new ModuloRegistros();
        ControladorAlumnos controlador = new ControladorAlumnos(modulo);

        // Generación de los 1000 alumnos
        GeneradorDatos.generarAlumnos(modulo);
        
        // Asignación inicial del ranking (número de inscripción) para los 1000 alumnos
        controlador.asignarNumerosDeInscripcion();
        
        
        int opcion = -1;
        
        do {
            mostrarMenu();
            try {
                System.out.print("Seleccione una opcion: ");
                opcion = SCANNER.nextInt();
                SCANNER.nextLine(); // Consumir el salto de línea
                
                switch (opcion) {
                    case 1:
                        crearAlumnoMenu(controlador);
                        break;
                    case 2:
                        buscarPorCuentaMenu(controlador);
                        break;
                    case 3:
                        editarAlumnoMenu(controlador);
                        break;
                    case 4:
                        eliminarAlumnoMenu(controlador);
                        break;
                    case 5:
                        imprimirAlumnosAlAzarMenu(controlador);
                        break;
                    case 6:
                        modulo.mostrarAsignaturas();
                        break;
                    case 7:
                        controlador.imprimirTop10Indicador();
                        break;
                    case 8: // Exportar a CSV
                        controlador.exportarAlumnosACsv("alumnos_ranking.csv");
                        break;
                    case 9:
                        buscarPorApellidoMenu(modulo);
                        break;
                    case 0:
                        System.out.println("Saliendo del programa. Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada no valida. Por favor, ingrese un numero.");
                SCANNER.nextLine(); // Limpiar el buffer
                opcion = -1; // Mantener el ciclo
            } catch (Exception e) {
                System.err.println("Ocurrio un error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);
        
        SCANNER.close();
    }
    
    // ===============================================
    //             MENÚ Y UTILIDADES
    // ===============================================

    /**
     * Muestra el menú principal de opciones en la consola.
     */
    private static void mostrarMenu() {
        System.out.println("\n=====================================");
        System.out.println(" SISTEMA DE REGISTRO ACADEMICO ");
        System.out.println("=====================================");
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. CRUD: Crear Alumno");
        System.out.println("2. CRUD: Buscar Alumno por Num. Cuenta");
        System.out.println("3. CRUD: Editar Alumno");
        System.out.println("4. CRUD: Eliminar Alumno");
        System.out.println("5. Imprimir Alumnos al Azar");
        System.out.println("6. Mostrar Asignaturas (Catalogo)");
        System.out.println("7. Reporte TOP 10 (Ranking)");
        System.out.println("8. Exportar a CSV (alumnos_ranking.csv)");
        System.out.println("9. Busqueda por Apellido");
        System.out.println("0. Salir");
        System.out.println("-------------------------------------");
    }

    /**
     * Interfaz de usuario para la opción de imprimir N alumnos seleccionados al azar.
     * Solicita al usuario la cantidad de alumnos a imprimir.
     * * @param controlador El ControladorAlumnos que maneja la lógica de negocio.
     */
    private static void imprimirAlumnosAlAzarMenu(ControladorAlumnos controlador) {
        System.out.println("\n--- IMPRIMIR ALUMNOS AL AZAR ---");
        System.out.print("Cuantos alumnos desea imprimir? (Máximo 1000): ");
        try {
            int cantidad = SCANNER.nextInt();
            SCANNER.nextLine();
            controlador.imprimirAlumnosAlAzar(cantidad);
        } catch (InputMismatchException e) {
            System.err.println("Entrada no valida. Debe ingresar un numero entero.");
            SCANNER.nextLine(); // Limpiar buffer
        }
    }
    
    /**
     * Interfaz de usuario para la búsqueda de alumnos por apellido.
     * Muestra una lista de todos los alumnos que coinciden con el apellido ingresado.
     * * @param modulo El ModuloRegistros que contiene el repositorio de datos.
     */
    private static void buscarPorApellidoMenu(ModuloRegistros modulo) {
        System.out.println("\n--- BUSCAR POR APELLIDO ---");
        System.out.print("Ingrese el apellido a buscar: ");
        String apellido = SCANNER.nextLine();
        
        List<Alumno> resultados = modulo.buscarAlumnosPorApellido(apellido);
        
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron alumnos con el apellido: " + apellido);
            return;
        }
        
        System.out.println("\n=== Resultados de la Busqueda (" + resultados.size() + " encontrados) ===");
        
        for (Alumno alumno : resultados) {
            System.out.printf("Cuenta: %d | Nombre: %s | Semestre: %d | NumInscripcion: %d\n",
                alumno.getNumCuenta(),
                alumno.getNombreCompleto(),
                alumno.getSemestre(),
                alumno.getNumInscripcionFinal());
        }
    }

    /**
     * Interfaz de usuario para la búsqueda de un alumno por su número de cuenta.
     * Muestra el registro académico completo del alumno si es encontrado.
     * * @param controlador El ControladorAlumnos que maneja la lógica de negocio.
     */
    private static void buscarPorCuentaMenu(ControladorAlumnos controlador) {
        System.out.println("\n--- BUSCAR POR NUMERO DE CUENTA ---");
        System.out.print("Ingrese el numero de cuenta: ");
        try {
            long numCuenta = SCANNER.nextLong();
            SCANNER.nextLine();
            Alumno alumno = controlador.buscarAlumnoPorNumCuenta(numCuenta);
            
            if (alumno != null) {
                alumno.getRegistroAcademico().mostrarRegistro();
            } else {
                System.out.println("Alumno no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada no valida. Debe ingresar un numero de cuenta valido.");
            SCANNER.nextLine(); 
        }
    }
    
    // ===============================================
    //              CRUD MENÚS
    // ===============================================

    /**
     * Menú para la creación de un nuevo alumno.
     * Solicita todos los datos personales necesarios al usuario.
     * * @param controlador El ControladorAlumnos que maneja la lógica de negocio.
     */
    private static void crearAlumnoMenu(ControladorAlumnos controlador) {
        System.out.println("\n--- CREAR NUEVO ALUMNO ---");
        
        System.out.print("Primer Nombre: ");
        String pNombre = SCANNER.nextLine();
        System.out.print("Segundo Nombre (dejar vacío si no aplica): ");
        String sNombre = SCANNER.nextLine();
        System.out.print("Primer Apellido: ");
        String pApellido = SCANNER.nextLine();
        System.out.print("Segundo Apellido: ");
        String sApellido = SCANNER.nextLine();
        System.out.print("Direccion: ");
        String direccion = SCANNER.nextLine();
        System.out.print("Genero (M/F): ");
        String genero = SCANNER.nextLine().toUpperCase();
        
        int edad = 0;
        while (edad < 18 || edad > 25) {
            System.out.print("Edad (18-25): ");
            try {
                edad = SCANNER.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Edad no valida. Ingrese un numero entero.");
                SCANNER.nextLine();
                edad = 0;
            }
        }
        
        int semestre = 0;
        while (semestre < 1 || semestre > 10) {
            System.out.print("Semestre (1-10): ");
            try {
                semestre = SCANNER.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Semestre no valido. Ingrese un numero entero.");
                SCANNER.nextLine();
                semestre = 0;
            }
        }
        SCANNER.nextLine(); // Consumir el resto de la línea
        
        controlador.crearAlumno(pNombre, sNombre, pApellido, sApellido, edad, semestre, direccion, genero);
    }
    
    /**
     * Menú para la edición de los datos personales de un alumno existente.
     * Permite al usuario actualizar los campos del alumno.
     * * @param controlador El ControladorAlumnos que maneja la lógica de negocio.
     */
    private static void editarAlumnoMenu(ControladorAlumnos controlador) {
        System.out.println("\n--- EDITAR ALUMNO ---");
        System.out.print("Ingrese el numero de cuenta del alumno a editar: ");
        long numCuenta = 0;
        try {
            numCuenta = SCANNER.nextLong();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Numero de cuenta no valido.");
            SCANNER.nextLine();
            return;
        }

        Alumno alumnoExistente = controlador.buscarAlumnoPorNumCuenta(numCuenta);
        if (alumnoExistente == null) {
            System.out.println("Error: Alumno no encontrado.");
            return;
        }

        System.out.println("\nEditando a: " + alumnoExistente.getNombreCompleto());

        // Pedir nuevos datos (se usa el valor actual como sugerencia)
        System.out.print("Primer Nombre [" + alumnoExistente.getPrimerNombre() + "]: ");
        String pNombre = SCANNER.nextLine();
        pNombre = pNombre.isEmpty() ? alumnoExistente.getPrimerNombre() : pNombre;

        System.out.print("Segundo Nombre [" + alumnoExistente.getSegundoNombre() + "]: ");
        String sNombre = SCANNER.nextLine();
        sNombre = sNombre.isEmpty() ? alumnoExistente.getSegundoNombre() : sNombre;
        
        System.out.print("Primer Apellido [" + alumnoExistente.getPrimerApellido() + "]: ");
        String pApellido = SCANNER.nextLine();
        pApellido = pApellido.isEmpty() ? alumnoExistente.getPrimerApellido() : pApellido;
        
        System.out.print("Segundo Apellido [" + alumnoExistente.getSegundoApellido() + "]: ");
        String sApellido = SCANNER.nextLine();
        sApellido = sApellido.isEmpty() ? alumnoExistente.getSegundoApellido() : sApellido;
        
        System.out.print("Direccion [" + alumnoExistente.getDireccion() + "]: ");
        String direccion = SCANNER.nextLine();
        direccion = direccion.isEmpty() ? alumnoExistente.getDireccion() : direccion;

        System.out.print("Genero (M/F) [" + alumnoExistente.getGenero() + "]: ");
        String genero = SCANNER.nextLine().toUpperCase();
        genero = genero.isEmpty() ? alumnoExistente.getGenero() : genero;

        int edad = alumnoExistente.getEdad();
        System.out.print("Edad [" + edad + "]: ");
        String sEdad = SCANNER.nextLine();
        if (!sEdad.isEmpty()) {
            try {
                edad = Integer.parseInt(sEdad);
            } catch (NumberFormatException e) {
                System.err.println("Valor de edad no valido. Se mantendra la edad anterior.");
            }
        }

        int semestre = alumnoExistente.getSemestre();
        System.out.print("Semestre [" + semestre + "]: ");
        String sSemestre = SCANNER.nextLine();
        if (!sSemestre.isEmpty()) {
            try {
                semestre = Integer.parseInt(sSemestre);
            } catch (NumberFormatException e) {
                System.err.println("Valor de semestre no valido. Se mantendrá el semestre anterior.");
            }
        }
        
        // Llamada a la función de edición
        controlador.editarAlumno(numCuenta, pNombre, sNombre, pApellido, sApellido, edad, semestre, direccion, genero);
    }

    /**
     * Menú para la eliminación de un alumno del registro.
     * Solicita el número de cuenta para confirmar la eliminación.
     * * @param controlador El ControladorAlumnos que maneja la lógica de negocio.
     */
    private static void eliminarAlumnoMenu(ControladorAlumnos controlador) {
        System.out.println("\n--- ELIMINAR ALUMNO ---");
        System.out.print("Ingrese el número de cuenta del alumno a eliminar: ");
        try {
            long numCuenta = SCANNER.nextLong();
            SCANNER.nextLine();
            controlador.eliminarAlumno(numCuenta);
        } catch (InputMismatchException e) {
            System.err.println("Numero de cuenta no valido.");
            SCANNER.nextLine();
        }
    }
}