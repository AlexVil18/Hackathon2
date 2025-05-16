import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = null;
        int opcion;
        boolean salir = false;
        
        // Pregunta inicial para definir el tamaño de la agenda
        System.out.println("¿Desea especificar el tamaño máximo de la agenda? (S/N)");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.println("Introduzca el tamaño máximo de la agenda:");
            try {
                int tamano = Integer.parseInt(scanner.nextLine());
                agenda = new Agenda(tamano);
                System.out.println("Agenda creada con tamaño máximo: " + tamano);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Se creará agenda con tamaño por defecto: 10");
                agenda = new Agenda();
            }
        } else {
            agenda = new Agenda();
            System.out.println("Agenda creada con tamaño por defecto: 10");
        }
        
        // Menú principal
        while (!salir) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1: // Añadir contacto
                        anadirContacto(scanner, agenda);
                        break;
                    case 2: // Listar contactos
                        listarContactos(agenda);
                        break;
                    case 3: // Buscar contacto
                        buscarContacto(scanner, agenda);
                        break;
                    case 4: // Eliminar contacto
                        eliminarContacto(scanner, agenda);
                        break;
                    case 5: // Modificar teléfono
                        modificarTelefono(scanner, agenda);
                        break;
                    case 6: // Consultar si agenda está llena
                        consultarAgendaLlena(agenda);
                        break;
                    case 7: // Ver espacios libres
                        verEspaciosLibres(agenda);
                        break;
                    case 0: // Salir
                        salir = true;
                        System.out.println("¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe introducir un número. Intente nuevamente.");
            }
            
            if (!salir) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n===== AGENDA TELEFÓNICA =====");
        System.out.println("1. Añadir contacto");
        System.out.println("2. Listar contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Modificar teléfono");
        System.out.println("6. Consultar si la agenda está llena");
        System.out.println("7. Ver espacios libres");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void anadirContacto(Scanner scanner, Agenda agenda) {
        System.out.println("\n--- AÑADIR CONTACTO ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            System.out.println("Error: El nombre y apellido no pueden estar vacíos.");
            return;
        }
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        Contacto contacto = new Contacto(nombre, apellido, telefono);
        
        // Añadimos el contacto a la agenda
        agenda.anadirContacto(contacto);
    }
    
    private static void listarContactos(Agenda agenda) {
        System.out.println("\n--- LISTA DE CONTACTOS ---");
        agenda.listarContactos();
    }
    
    private static void buscarContacto(Scanner scanner, Agenda agenda) {
        System.out.println("\n--- BUSCAR CONTACTO ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            System.out.println("Error: El nombre y apellido no pueden estar vacíos.");
            return;
        }
        
        // Buscamos el contacto por nombre y apellido
        agenda.buscaContacto(nombre);
    }
    
    private static void eliminarContacto(Scanner scanner, Agenda agenda) {
        System.out.println("\n--- ELIMINAR CONTACTO ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            System.out.println("Error: El nombre y apellido no pueden estar vacíos.");
            return;
        }
        
        Contacto contacto = new Contacto(nombre, apellido, "");
        agenda.eliminarContacto(contacto);
    }
    
    private static void modificarTelefono(Scanner scanner, Agenda agenda) {
        System.out.println("\n--- MODIFICAR TELÉFONO ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            System.out.println("Error: El nombre y apellido no pueden estar vacíos.");
            return;
        }
        
        System.out.print("Nuevo teléfono: ");
        String nuevoTelefono = scanner.nextLine();
        
        agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
    }
    
    private static void consultarAgendaLlena(Agenda agenda) {
        System.out.println("\n--- ESTADO DE LA AGENDA ---");
        
        if (agenda.agendaLlena()) {
            System.out.println("La agenda está llena. No hay espacio disponible para nuevos contactos.");
        } else {
            System.out.println("La agenda no está llena. Aún hay espacio disponible.");
        }
    }
    
    private static void verEspaciosLibres(Agenda agenda) {
        System.out.println("\n--- ESPACIOS LIBRES ---");
        int espacios = agenda.espacioLibres();
        System.out.println("Número de contactos que se pueden agregar: " + espacios);
    }
}