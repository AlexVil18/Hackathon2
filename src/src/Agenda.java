import java.util.ArrayList;

public class Agenda {
    private int tamanoMax;
    private ArrayList<Contacto> contactos;

    // Constructores
    public Agenda() {
        this.tamanoMax = 10;
        this.contactos = new ArrayList<>();
    }

    public Agenda(int tamanoMax) {
        this.tamanoMax = tamanoMax;
        this.contactos = new ArrayList<>();
    }

    // Añadir contacto
    public void anadirContacto(Contacto cont) {
        if (existeContacto(cont)) {
            System.out.println("El contacto ya existe.");
        } else if (agendaLlena()) {
            System.out.println("La agenda está llena.");
        } else {
            contactos.add(cont);
            System.out.println("Se ha registrado exitosamente el contacto.");
        }
    }

    // Listar contactos alfabeticamente
    public void listarContactos() {
        for (int i = 0; i < contactos.size() - 1; i++) {
            for (int j = 0; j < contactos.size() - i - 1; j++) {
                Contacto contacto1 = contactos.get(j);
                Contacto contacto2 = contactos.get(j + 1);
                if (contacto1.getNombre().compareToIgnoreCase(contacto2.getNombre()) > 0) {
                    contactos.set(j, contacto2);
                    contactos.set(j + 1, contacto1);
                }
            }
        }
        for (Contacto c : contactos) {
            System.out.println(c);
        }
    }

    // Buscar contacto
    public Contacto buscaContacto(String nombre) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(c);
                return c;
            }
        }
        System.out.println("No se encontró el contacto.");
        return null;
    }

    // Eliminar contacto
    public void eliminarContacto(Contacto c) {
        if (contactos.remove(c)) {
            System.out.println("Contacto " + c.getNombre() + " " + c.getApellido() + " removido");
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    // Modificar telefono
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        boolean encontrado = false;
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre) && c.getApellido().equalsIgnoreCase(apellido)) {
                c.setTelefono(nuevoTelefono);
                encontrado = true;
                System.out.println("Teléfono modificado exitosamente.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró el contacto.");
        }
    }

    // Verificar si existe contacto
    public boolean existeContacto(Contacto cont) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(cont.getNombre()) &&
                    c.getApellido().equalsIgnoreCase(cont.getApellido())) {
                return true;
            }
        }
        return false;
    }

    // Verificar agenda llena
    public boolean agendaLlena() {
        return contactos.size() >= tamanoMax;
    }

    // Verificar espacios libres
    public int espacioLibres() {
        return tamanoMax - contactos.size();
    }
}