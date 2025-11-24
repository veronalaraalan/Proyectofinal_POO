/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu03.controlador;

import hu03.repositorio.ModuloRegistros;
import hu03.modelo.Alumno;
import hu03.modelo.RegistroAcademico; 
import hu03.utilidades.GeneradorDatos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ControladorAlumnos: Gestiona las operaciones CRUD y Reportes del sistema.
 * Actúa como el intermediario entre la Vista (HU03) y el Modelo/Repositorio (ModuloRegistros).
 * Contiene la lógica de negocio para la gestión y el ranking de los alumnos.
 * * @author EnrollEngine
 * @version 3.10.18
 */
public class ControladorAlumnos {
    /**
     * Referencia al Módulo de Registros (Repositorio o Modelo) que contiene la lista de alumnos.
     */
    ModuloRegistros repositorio;

    /**
     * Constructor del controlador de alumnos.
     * * @param repositorio Instancia del ModuloRegistros que proporciona acceso a los datos.
     */
    public ControladorAlumnos(ModuloRegistros repositorio) {
        this.repositorio = repositorio;
    }
    
    // ===============================================
    //            ASIGNACIÓN DE NÚMERO DE INSCRIPCIÓN
    // ===============================================

    /**
     * Calcula el ranking para todos los alumnos basado en dos criterios:
     * 1. Indicador Bruto (Descendente).
     * 2. Primer Apellido (Ascendente, como desempate).
     * El resultado (el puesto en el ranking) se asigna como el Num. Inscripción Final
     * a cada objeto Alumno. Se debe llamar después de cualquier modificación de datos.
     */
    public void asignarNumerosDeInscripcion() {
        if (repositorio.getAlumnos().isEmpty()) {
            return;
        }

        List<Alumno> alumnosOrdenables = new ArrayList<>(repositorio.getAlumnos());

        // Ordenar con la lógica de desempate (Indicador Bruto Descendente, Apellido Ascendente)
        Collections.sort(alumnosOrdenables, new Comparator<Alumno>() {
            @Override
            public int compare(Alumno a1, Alumno a2) {
                long indicador1 = a1.getIndicadorBruto();
                long indicador2 = a2.getIndicadorBruto();
                
                // Criterio 1: Ordenar por Indicador Bruto (Descendente)
                int comparacionIndicador = Long.compare(indicador2, indicador1); 

                if (comparacionIndicador != 0) {
                    return comparacionIndicador;
                } else {
                    // Criterio 2: Desempate por Primer Apellido (Ascendente / Alfabético)
                    return a1.getPrimerApellido().compareTo(a2.getPrimerApellido()); 
                }
            }
        });

        // Asignar el puesto (Número de Inscripción Final) a CADA alumno
        for (int i = 0; i < alumnosOrdenables.size(); i++) {
            Alumno alumno = alumnosOrdenables.get(i);
            int numInscripcionAsignado = i + 1; // Puesto 1, 2, 3...
            
            // Almacenar el puesto en el objeto Alumno
            alumno.setNumInscripcionFinal(numInscripcionAsignado);
        }
        
        // System.out.println("⚙️ Se han actualizado los Números de Inscripción para todos los alumnos.");
    }
    
    // ===============================================
    //              CREAR (C)
    // ===============================================

    /**
     * Crea una nueva instancia de Alumno, genera su registro académico inicial
     * y lo añade al repositorio. Finalmente, actualiza el ranking global.
     * * @param primerNombre Primer nombre del alumno.
     * @param segundoNombre Segundo nombre del alumno.
     * @param primerApellido Primer apellido del alumno.
     * @param segundoApellido Segundo apellido del alumno.
     * @param edad Edad del alumno.
     * @param semestre Semestre que cursa el alumno.
     * @param direccion Dirección de residencia del alumno.
     * @param genero Género del alumno.
     * @return El objeto Alumno recién creado.
     */
    public Alumno crearAlumno(String primerNombre, String segundoNombre, String primerApellido, 
                             String segundoApellido, int edad, int semestre, String direccion, String genero) {
        
        long numCuenta = GeneradorDatos.generarNumCuenta();
        String carrera = "Ingenieria en Computacion"; 
        
        Alumno nuevoAlumno = new Alumno(
            numCuenta, primerNombre, segundoNombre, primerApellido, segundoApellido, 
            edad, carrera, semestre, direccion, genero
        );
        
        // Generar las materias y notas para el nuevo alumno
        repositorio.generarRegistroAcademico(nuevoAlumno);
        // Añadir al repositorio
        repositorio.agregarAlumno(nuevoAlumno);
        
        // Actualizar ranking para incluir al nuevo alumno
        asignarNumerosDeInscripcion(); 
        
        System.out.println("Alumno creado exitosamente. Cuenta: " + numCuenta + 
                             " | Indicador: " + nuevoAlumno.getIndicadorBruto()); 
        return nuevoAlumno;
    }
    
    
    // ===============================================
    //              LEER (R)
    // ===============================================

    /**
     * Busca un alumno dentro del repositorio utilizando su número de cuenta.
     * * @param numCuenta El número de cuenta (identificador único) del alumno a buscar.
     * @return El objeto Alumno si es encontrado, o null si no existe.
     */
    public Alumno buscarAlumnoPorNumCuenta(long numCuenta) {
        for (Alumno alumno : repositorio.getAlumnos()) {
            if (alumno.getNumCuenta() == numCuenta) {
                return alumno;
            }
        }
        return null; 
    }

    // ===============================================
    //              EDITAR (U)
    // ===============================================

    /**
     * Edita los datos personales de un alumno existente.
     * Si se cambia el semestre, se recalcula el registro académico y el Indicador Bruto.
     * * @param numCuenta Número de cuenta del alumno a editar.
     * @param nuevoPrimerNombre Nuevo primer nombre.
     * @param nuevoSegundoNombre Nuevo segundo nombre.
     * @param nuevoPrimerApellido Nuevo primer apellido.
     * @param nuevoSegundoApellido Nuevo segundo apellido.
     * @param nuevaEdad Nueva edad.
     * @param nuevoSemestre Nuevo semestre (si es diferente, se regenera el registro académico).
     * @param nuevaDireccion Nueva dirección.
     * @param nuevoGenero Nuevo género.
     * @return true si el alumno fue editado con éxito, false si no fue encontrado.
     */
    public boolean editarAlumno(long numCuenta, 
                                 String nuevoPrimerNombre, String nuevoSegundoNombre, 
                                 String nuevoPrimerApellido, String nuevoSegundoApellido, 
                                 int nuevaEdad, int nuevoSemestre, String nuevaDireccion, String nuevoGenero) {
        
        Alumno alumno = buscarAlumnoPorNumCuenta(numCuenta);
        if (alumno == null) {
            System.out.println("Error: No se encontro un alumno con la cuenta: " + numCuenta);
            return false;
        }
        
        // Actualización de los campos personales
        alumno.setPrimerNombre(nuevoPrimerNombre);
        alumno.setSegundoNombre(nuevoSegundoNombre);
        alumno.setPrimerApellido(nuevoPrimerApellido);
        alumno.setSegundoApellido(nuevoSegundoApellido);
        alumno.setEdad(nuevaEdad);
        alumno.setDireccion(nuevaDireccion);
        alumno.setGenero(nuevoGenero);
        
        // Si se edita el semestre, hay que recalcular el indicador y el registro
        if (nuevoSemestre > 0 && alumno.getSemestre() != nuevoSemestre) {
            alumno.setSemestre(nuevoSemestre);
            // Se asume que generarRegistroAcademico también recalcula el IndicadorBruto
            repositorio.generarRegistroAcademico(alumno); 
        }
        
        // Recalcular el ranking global debido a posibles cambios en el Indicador Bruto (por cambio de semestre)
        asignarNumerosDeInscripcion(); 

        System.out.println("Registro del alumno " + numCuenta + " modificado con exito.");
        return true;
    }
    
    // ===============================================
    //              ELIMINAR (D)
    // ===============================================

    /**
     * Elimina un alumno del repositorio utilizando un Iterador.
     * El ranking global se actualiza tras la eliminación.
     * * @param numCuenta Número de cuenta del alumno a eliminar.
     * @return true si el alumno fue eliminado con éxito, false si no fue encontrado.
     */
    public boolean eliminarAlumno(long numCuenta) {
        Iterator<Alumno> it = repositorio.getAlumnos().iterator();
        while (it.hasNext()) {
            Alumno alumno = it.next();
            if (alumno.getNumCuenta() == numCuenta) {
                it.remove(); 
                
                // Actualizar ranking
                asignarNumerosDeInscripcion(); 
                
                System.out.println("🗑️ Alumno con cuenta " + numCuenta + " eliminado del registro.");
                return true;
            }
        }
        System.out.println("Error: No se encontro un alumno con la cuenta: " + numCuenta + " para eliminar.");
        return false; 
    }

    // ===============================================
    //            5. IMPRESIÓN AL AZAR
    // ===============================================

    /**
     * Imprime una cantidad especificada de alumnos seleccionados aleatoriamente.
     * La lista completa se mezcla (shuffle) antes de seleccionar la cantidad deseada.
     * * @param cantidad Número de alumnos a imprimir.
     */
    public void imprimirAlumnosAlAzar(int cantidad) {
        List<Alumno> alumnos = repositorio.getAlumnos();
        
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados para seleccionar al azar.");
            return;
        }
        
        // 1. Asegurar que el ranking esté actualizado para mostrar el puesto
        asignarNumerosDeInscripcion(); 
        
        // 2. Determinar la cantidad real a imprimir
        int numAImprimir = Math.min(cantidad, alumnos.size());
        
        if (numAImprimir <= 0) {
              System.out.println("La cantidad debe ser mayor a 0 o se excede el maximo (" + alumnos.size() + ").");
              return;
        }
        
        // 3. Crear una copia de la lista y mezclarla (Shuffle)
        List<Alumno> copiaAlumnos = new ArrayList<>(alumnos);
        Collections.shuffle(copiaAlumnos);
        
        // 4. Imprimir la lista
        System.out.println("\n==================================================");
        System.out.println("      " + numAImprimir + " ALUMNOS SELECCIONADOS AL AZAR      ");
        System.out.println("==================================================");

        for (int i = 0; i < numAImprimir; i++) {
            Alumno alumno = copiaAlumnos.get(i);
            
            System.out.printf("%d. Cuenta: %-10d | Nombre: %s | Num Inscripcion: %d | Indicador: %d\n",
                              (i + 1),
                              alumno.getNumCuenta(),
                              alumno.getNombreCompleto(),
                              alumno.getNumInscripcionFinal(),
                              alumno.getIndicadorBruto());
        }
        System.out.println("==================================================");
    }
    
    // ===============================================
    //            7. REPORTE TOP 10 INDICADOR
    // ===============================================

    /**
     * Genera y muestra por consola el reporte del Top 10 de alumnos,
     * ordenados por su Número de Inscripción Final (puesto en el ranking).
     */
    public void imprimirTop10Indicador() {
        if (repositorio.getAlumnos().isEmpty()) {
            System.out.println("No hay alumnos registrados para generar el ranking.");
            return;
        }
        
        // Asegurar que el ranking esté actualizado antes de generar el reporte
        asignarNumerosDeInscripcion(); 

        // 1. Crear una copia de la lista para ordenar
        List<Alumno> alumnosOrdenables = new ArrayList<>(repositorio.getAlumnos());

        // 2. Ordenar usando el Num. Inscripción Final ya asignado (puesto)
        Collections.sort(alumnosOrdenables, new Comparator<Alumno>() {
            @Override
            public int compare(Alumno a1, Alumno a2) {
                // Ordenar por Num. Inscripción Final (el puesto): Ascendente (1 es mejor)
                return Integer.compare(a1.getNumInscripcionFinal(), a2.getNumInscripcionFinal());
            }
        });

        // 3. Imprimir el Top 10
        int numAImprimir = Math.min(10, alumnosOrdenables.size());

        System.out.println("\n==================================================");
        System.out.println("      TOP " + numAImprimir + " ALUMNOS - NUM. INSCRIPCION ASIGNADO      ");
        System.out.println("==================================================");

        for (int i = 0; i < numAImprimir; i++) {
            Alumno alumno = alumnosOrdenables.get(i);
            
            int numInscripcionAsignado = alumno.getNumInscripcionFinal(); 
            long indicadorBruto = alumno.getIndicadorBruto();

            String ranking = String.format("%-4s", numInscripcionAsignado + ".");
            
            System.out.printf("%s Num. Inscripcion: %-4d | Indicador: %-12d | Cuenta: %-10d | Nombre: %s\n",
                              ranking,
                              numInscripcionAsignado,
                              indicadorBruto,
                              alumno.getNumCuenta(),
                              alumno.getNombreCompleto());
        }
        System.out.println("==================================================");
    }
    
    // ===============================================
    //            8. EXPORTAR A CSV (NUEVO)
    // ===============================================

    /**
     * Exporta todos los registros de alumnos al formato CSV.
     * Los alumnos se ordenan por su Número de Inscripción Final (Ranking).
     * * @param nombreArchivo El nombre del archivo CSV a crear/sobrescribir.
     */
    public void exportarAlumnosACsv(String nombreArchivo) {
        if (repositorio.getAlumnos().isEmpty()) {
            System.out.println("No hay alumnos registrados para exportar.");
            return;
        }

        // 1. Asegurar que el ranking esté actualizado antes de exportar
        asignarNumerosDeInscripcion();

        // Obtener la lista de alumnos y ordenar por Num. Inscripción Final (Ranking)
        List<Alumno> alumnosOrdenados = new ArrayList<>(repositorio.getAlumnos());
        // Uso de lambda para ordenar por NumInscripcionFinal (Ascendente)
        Collections.sort(alumnosOrdenados, (a1, a2) -> 
            Integer.compare(a1.getNumInscripcionFinal(), a2.getNumInscripcionFinal()));
        
        // Usamos try-with-resources para asegurar que el FileWriter se cierre automáticamente
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            
            // =========================================================
            // 2. Escribir la cabecera (Header)
            // =========================================================
            writer.append("NumInscripcion,NumCuenta,NombreCompleto,PrimerApellido,SegundoApellido,Semestre,Edad,IndicadorBruto,Promedio,AsignaturasAprobadas,TotalCreditos\n");
            
            // =========================================================
            // 3. Escribir los registros de cada alumno
            // =========================================================
            for (Alumno alumno : alumnosOrdenados) {
                RegistroAcademico registro = alumno.getRegistroAcademico();
                
                // Usamos String.format para garantizar la consistencia en el orden y formato
                String linea = String.format(
                    "%d,%d,\"%s\",\"%s\",\"%s\",%d,%d,%d,%.2f,%d,%d\n",
                    alumno.getNumInscripcionFinal(),        // NumInscripcion
                    alumno.getNumCuenta(),                  // NumCuenta
                    alumno.getNombreCompleto().replace(",", "."), // NombreCompleto (reemplazar comas dentro del nombre)
                    alumno.getPrimerApellido(),             // PrimerApellido
                    alumno.getSegundoApellido(),            // SegundoApellido
                    alumno.getSemestre(),                   // Semestre
                    alumno.getEdad(),                       // Edad
                    alumno.getIndicadorBruto(),             // IndicadorBruto
                    registro.calcularPromedio(),            // Promedio (2 decimales)
                    registro.getAsignaturasAprobadas(),     // AsignaturasAprobadas
                    registro.getTotalCreditos()             // TotalCreditos
                );
                writer.append(linea);
            }

            System.out.println("Exito: Los datos de los " + alumnosOrdenados.size() + " alumnos han sido exportados a: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("ERROR al escribir el archivo CSV: " + e.getMessage());
        }
    }
}