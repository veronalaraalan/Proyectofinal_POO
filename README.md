# Proyectofinal_POO

# Diseño e Implementación Académica de un Sistema Automatizado de Inscripción Universitaria Enfocado al Cálculo del Número de Inscripción

![Lenguaje: Java](https://img.shields.io/badge/language-Java-blue)
![Estado](https://img.shields.io/badge/status-ongoing-yellow)

Descripción
-----------
Proyecto en Java para automatizar el proceso de inscripción universitaria con énfasis en el cálculo del número de inscripción asignado a cada estudiante. Proporciona operaciones CRUD de alumnos, catálogo de asignaturas, impresión aleatoria de alumnos, reporte TOP 10 por indicador y exportación a CSV.

Características
---------------
- CRUD de alumnos (crear, buscar por número de cuenta, editar, eliminar).
- Impresión aleatoria de alumnos.
- Catálogo de asignaturas.
- Cálculo y asignación automática del "Número de Inscripción" y un indicador asociado.
- Reporte TOP 10 (ranking) y exportación a CSV (`alumnos_ranking.csv`).
- Interfaz de consola (CLI) con menú.
- Uso del archivo `direcciones.txt` exportado para ciertas funciones del programa.

Requisitos
----------
- JDK 8 o superior (se recomienda JDK 11+).
- IDE Java (NetBeans, IntelliJ IDEA, Eclipse) o herramientas de línea de comandos (javac, jar, java).

Instalación
-----------
1. Clona el repositorio:
   ```
   git clone https://github.com/veronalaraalan/Proyectofinal_POO.git
   cd Proyectofinal_POO
   ```

Uso del archivo direcciones.txt
------------------------------
- El programa utiliza el archivo `direcciones.txt` para funciones específicas (se exporta desde el proyecto y se lee en tiempo de ejecución).
- Colocar `direcciones.txt` en la carpeta desde la cual se ejecuta la aplicación (misma carpeta donde se ejecuta `java -jar`) o incluirlo en la carpeta `dist/` antes de distribuir.
- Al distribuir, incluya `direcciones.txt` dentro del ZIP junto con `dist/` y `dist/lib/` para que las funciones que lo consumen puedan accederlo por la ruta relativa `direcciones.txt`.

Build con NetBeans
------------------
- Abrir el proyecto en NetBeans.
- Ejecutar "Clean and Build" / "Build".
- El IDE generará la carpeta `dist/` con el JAR ejecutable y copiará las dependencias JAR a `dist/lib/`.
- El manifiesto (`MANIFEST.MF`) del JAR incluirá `Main-Class` y `Class-Path`.

Compilar y empaquetar por línea de comandos (ejemplo)
----------------------------------------------------
1. Compilar:
   ```
   javac -d bin $(find src -name "*.java")
   ```
2. Crear manifiesto (`manifest.txt`):
   ```
   Main-Class: tu.paquete.ClasePrincipal
   ```
   (terminar la última línea con un salto de línea)
3. Empaquetar:
   ```
   jar cfm dist/NombreAplicacion.jar manifest.txt -C bin .
   ```
4. Incluir `direcciones.txt` en la carpeta desde la que se ejecute el JAR o dentro de `dist/` al distribuir.

Ejecutar la aplicación
----------------------
Desde la carpeta `dist/`:
```
java -jar "HU03.jar"
```
Sustituya "HU03.jar" por el nombre real del JAR generado.

Exportación a CSV
-----------------
- Archivo de salida: `alumnos_ranking.csv`
- Ejemplo de salida al exportar:
```
Seleccione una opcion: 8
Exito: Los datos de los 1000 alumnos han sido exportados a: alumnos_ranking.csv
```

Ejemplos de salida (consola)
-----------------------------

Menu principal:
```
==========================================
SISTEMA DE REGISTRO ACADEMICO
==========================================
---  MENU PRINCIPAL  ---
1. CRUD: Crear Alumno
2. CRUD: Buscar Alumno por Num. Cuenta
3. CRUD: Editar Alumno
4. CRUD: Eliminar Alumno
5. Imprimir Alumnos al Azar
6. Mostrar Asignaturas (Catalogo)
7. Reporte TOP 10 (Ranking)
8. Exportar a CSV (alumnos_ranking.csv)
9. Busqueda por Apellido
0. Salir
------------------------------------------
Seleccione una opcion:
```

Exportación CSV (ejemplo):
```
Seleccione una opcion: 8
Exito: Los datos de los 1000 alumnos han sido exportados a: alumnos_ranking.csv
```

Reporte TOP 10 (ejemplo):
```
==========================================
TOP 10 ALUMNOS - NUM. INSCRIPCION ASIGNADO
==========================================
1.  Num. Inscripcion: 1   | Indicador: 8380  | Cuenta: 1000462
   Nombre: Quetzali Diana Rivera Padilla
2.  Num. Inscripcion: 2   | Indicador: 8106  | Cuenta: 1000542
   Nombre: Santiago Miguel Castillo Lopez
3.  Num. Inscripcion: 3   | Indicador: 8046  | Cuenta: 1000168
   Nombre: Veronica Jimena Vega Morales
4.  Num. Inscripcion: 4   | Indicador: 7861  | Cuenta: 1000480
   Nombre: Tania Contreras Miranda
5.  Num. Inscripcion: 5   | Indicador: 7794  | Cuenta: 1000724
   Nombre: Ramon Carlos Perez Rojas
6.  Num. Inscripcion: 6   | Indicador: 7787  | Cuenta: 1000930
   Nombre: Emilia Moreno Contreras
7.  Num. Inscripcion: 7   | Indicador: 7772  | Cuenta: 1000700
   Nombre: Sara Cabrera Montes
8.  Num. Inscripcion: 8   | Indicador: 7769  | Cuenta: 1000157
   Nombre: Ricardo Cruz Cabrera
9.  Num. Inscripcion: 9   | Indicador: 7719  | Cuenta: 1000929
   Nombre: Gustavo Estrada Contreras
10. Num. Inscripcion: 10  | Indicador: 7693  | Cuenta: 1000704
   Nombre: Esther Emilia Mendoza Montes
==========================================
```
