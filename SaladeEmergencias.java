import java.io.IOException;
import java.util.Scanner;

class Paciente {
    String nombre;
    int prioridad;
    Paciente siguiente;

    public Paciente(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.siguiente = null;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombre + " | Prioridad: " + prioridad;
    }
}

class ColaEmergencia {
    private Paciente cabeza;

    public void agregarPaciente(String nombre, int prioridad) {
        Paciente nuevo = new Paciente(nombre, prioridad);
        if (cabeza == null || prioridad < cabeza.prioridad) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Paciente temp = cabeza;
            while (temp.siguiente != null && temp.siguiente.prioridad <= prioridad) {
                temp = temp.siguiente;
            }
            nuevo.siguiente = temp.siguiente;
            temp.siguiente = nuevo;
        }
        System.out.println("Paciente agregado con éxito.");
    }

    public void atenderPaciente() {
        if (cabeza == null) {
            System.out.println("No hay pacientes en espera.");
        } else {
            System.out.println("Atendiendo a: " + cabeza);
            cabeza = cabeza.siguiente;
        }
    }

    public void mostrarPacientes() {
        if (cabeza == null) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        Paciente temp = cabeza;
        System.out.println("Pacientes en espera:");
        while (temp != null) {
            System.out.println(temp);
            temp = temp.siguiente;
        }
    }
}

public class SaladeEmergencias {
    public static void main(String[] args) {
        limpiarConsola();
        Scanner scanner = new Scanner(System.in);
        ColaEmergencia cola = new ColaEmergencia();
        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA DE EMERGENCIAS ---");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Atender paciente");
            System.out.println("3. Mostrar pacientes en espera");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del paciente: ");
                    String nombre = scanner.nextLine().trim();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                        break;
                    }
                    int prioridad;
                    while (true) {
                        System.out.print("Nivel de prioridad (1=Alta, 2=Media, 3=Baja): ");
                        try {
                            prioridad = Integer.parseInt(scanner.nextLine());
                            if (prioridad >= 1 && prioridad <= 3) break;
                            else System.out.println("Debe ser 1, 2 o 3.");
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Intente de nuevo.");
                        }
                    }
                    cola.agregarPaciente(nombre, prioridad);
                    break;
                case 2:
                    cola.atenderPaciente();
                    break;
                case 3:
                    cola.mostrarPacientes();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
        scanner.close();
    }

    static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }
}