import java.io.IOException;
import java.util.Scanner;

class Paciente {
    static int contadorID = 1;
    int id;
    String nombre;
    String horaLlegada;
    int prioridad;

    public Paciente(String nombre, String horaLlegada, int prioridad) {
        this.id = contadorID++;
        this.nombre = nombre;
        this.horaLlegada = horaLlegada;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Hora de llegada: " + horaLlegada + " | Prioridad: " + prioridad;
    }
}

class NodoPaciente {
    Paciente paciente;
    NodoPaciente siguiente;

    public NodoPaciente(Paciente paciente) {
        this.paciente = paciente;
        this.siguiente = null;
    }
}

class ColaPacientes {
    private NodoPaciente cabeza;

    public void agregarPaciente(Paciente paciente) {
        NodoPaciente nuevo = new NodoPaciente(paciente);
        if (cabeza == null || paciente.prioridad > cabeza.paciente.prioridad) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            NodoPaciente actual = cabeza;
            while (actual.siguiente != null && actual.siguiente.paciente.prioridad >= paciente.prioridad) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }

    public void mostrarPacientes() {
        if (cabeza == null) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        NodoPaciente temp = cabeza;
        while (temp != null) {
            System.out.println(temp.paciente);
            temp = temp.siguiente;
        }
    }

    public void atenderPaciente() {
        if (cabeza == null) {
            System.out.println("No hay pacientes para atender.");
            return;
        }
        System.out.println("Atendiendo paciente: " + cabeza.paciente);
        cabeza = cabeza.siguiente;
    }

    public void buscarPacientePorID(int id) {
    if (cabeza == null) {
        System.out.println("La sala de emergencias está vacía.");
        return;
    }

    NodoPaciente actual = cabeza;
    while (actual != null) {
        if (actual.paciente.id == id)
 {          System.out.println("Paciente encontrado:");
            System.out.println(actual.paciente);
            return;
        }
        actual = actual.siguiente;
    }
    System.out.println("No se encontró ningún paciente con la ID proporcionada.");
    }

    public void eliminarPacientePorID(int id) {
    if (cabeza == null) {
        System.out.println("No hay pacientes en la sala.");
        return;
    }

    if (cabeza.paciente.id == id) {
        System.out.println("Paciente eliminado: " + cabeza.paciente);
        cabeza = cabeza.siguiente;
        return;
    }

    NodoPaciente actual = cabeza;
    while (actual.siguiente != null) {
        if (actual.siguiente.paciente.id == id) {
            System.out.println("Paciente eliminado: " + actual.siguiente.paciente);
            actual.siguiente = actual.siguiente.siguiente;
            return;
        }
        actual = actual.siguiente;
    }

    System.out.println("No se encontró ningún paciente con la ID proporcionada.");
}


}

class Utilidades {
    public static void presioneEnterParaContinuar(Scanner scanner) {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}

public class SaladeEmergencias {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColaPacientes cola = new ColaPacientes();
        int opcion = 0;

        do {
            limpiarConsola();
            System.out.println("\n--- GESTIÓN DE PACIENTES EN EMERGENCIAS ---");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Mostrar pacientes en espera");
            System.out.println("3. Atender siguiente paciente");
            System.out.println("4. Buscar paciente por ID");
            System.out.println("5. Eliminar paciente por ID");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                Utilidades.presioneEnterParaContinuar(scanner);
                continue;
            }

            switch (opcion) {
                case 1:
                    limpiarConsola();
                    System.out.print("Nombre del paciente: ");
                    String nombre = scanner.nextLine().trim();

                    String horaLlegada;
                    while (true) {
                        System.out.print("Hora de llegada (HH:MM): ");
                        horaLlegada = scanner.nextLine().trim();
                        if (horaLlegada.matches("\\d{2}:\\d{2}")) break;
                        System.out.println("Formato inválido. Intente de nuevo.");
                    }

                    int prioridad;
                    while (true) {
                        System.out.print("Nivel de urgencia (1-5): ");
                        try {
                            prioridad = Integer.parseInt(scanner.nextLine());
                            if (prioridad >= 1 && prioridad <= 5) break;
                            else System.out.println("La prioridad debe estar entre 1 y 5.");
                        } catch (NumberFormatException e) {
                            System.out.println("Ingrese un número válido entre 1 y 5.");
                        }
                    }

                    cola.agregarPaciente(new Paciente(nombre, horaLlegada, prioridad));
                    System.out.println("Paciente agregado exitosamente.");
                    Utilidades.presioneEnterParaContinuar(scanner);
                    break;

                case 2:
                    limpiarConsola();
                    cola.mostrarPacientes();
                    Utilidades.presioneEnterParaContinuar(scanner);
                    break;

                case 3:
                    limpiarConsola();
                    cola.atenderPaciente();
                    Utilidades.presioneEnterParaContinuar(scanner);
                    break;

                case 4:
                    limpiarConsola();
                    System.out.print("Ingrese la ID del paciente a buscar: ");
                    try {
                        int idBuscar = Integer.parseInt(scanner.nextLine().trim());
                        cola.buscarPacientePorID(idBuscar);
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválida. Debe ser un número.");
                    }
                    Utilidades.presioneEnterParaContinuar(scanner);
                    break;

                case 5:
                    limpiarConsola();
                    System.out.print("Ingrese la ID del paciente a eliminar: ");
                    try {
                        int idEliminar = Integer.parseInt(scanner.nextLine().trim());
                        cola.eliminarPacientePorID(idEliminar);
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválida. Debe ser un número.");
                    }
                    Utilidades.presioneEnterParaContinuar(scanner);
                    break;

                case 6:
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    Utilidades.presioneEnterParaContinuar(scanner);
            }
        } while (opcion != 6);

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
