

public class Agenda extends Contacto {

    // Constructor
    public Agenda() {
    }

    public Agenda(String nombre, String apellido, String telefono) {
        super(nombre, apellido, telefono);
    }

    // Metodos
    public static void listarContactos() {
        // Listar contactos alfabeticamente
        public void listarContactos() {
            for (int i = 0; i < contactos.size() - 1; i++) {
                for (int j = 0; j < contactos.size() - i - 1; j++) {
                    Contacto contacto1 = contactos.get(j);
                    Contacto contacto2 = contactos.get(j + 1);
                    // Verificar si estan ordedanos
                    if (contacto1.getNombre().compareToIgnoreCase(contacto2.getNombre()) > 0) {
                        // Intercambiar posiciones
                        contactos.set(j, contacto2);
                        contacots.set(j + 1, contacto1);
                    }
                }
            }
            // imprimir la lista ya ordenada
            for (Contacto c: contactos) {
                System.out.println(c);
            }
        }

        // Metodo para buscar contacto
        public Contacto buscarContacto(String nombre) {
            for (Contacto c: contactos) {
                if (c.getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println(c);
                    return c;
                }
            }
            return null;
        }

        // Eliminar contacto
        public void eliminarContacto(Contacto c) {
            contactos.remove(c);

        }

        // Modificar telefono
        public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
            for (Contacto c: contactos) {
                if (c.getNombre().equalsIgnoreCase(nombre) && c.getApellido().equalsIgnoreCase(apellido)){
                    c.setTelefono(nuevoTelefono);
                }
            }
        }

    }
}
