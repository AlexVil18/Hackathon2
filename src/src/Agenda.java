import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Agenda {
    private Contacto[] contactos;
    private int tamanoMax;
    private int contador;
    
    // Constructor por defecto
    public Agenda() {
        this(10); // Por defecto, agenda con 10 espacios
    }
    
    // Constructor parametrizado
    public Agenda(int tamanoMax) {
        this.tamanoMax = tamanoMax;
        this.contactos = new Contacto[tamanoMax];
        this.contador = 0;
    }
    
    // Método para verificar si un número de teléfono ya existe
    public boolean existeTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getTelefono().equals(telefono)) {
                return true;
            }
        }
        
        return false;
    }
    
    // Modificar el método añadirContacto para validar teléfono repetido
    public boolean anadirContacto(Contacto c) {
        // Verificar si hay espacio
        if (contador == contactos.length) {
            return false;
        }
        
        // Verificar si el contacto ya existe (por nombre y apellido)
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(c.getNombre()) && 
                contactos[i].getApellido().equalsIgnoreCase(c.getApellido())) {
                return false;
            }
        }
        
        // Verificar si el teléfono ya existe
        if (existeTelefono(c.getTelefono())) {
            return false;
        }
        
        // Añadir el contacto
        contactos[contador] = c;
        contador++;
        return true;
    }
    
    // Método para verificar si un contacto ya existe
    public boolean existeContacto(Contacto c) {
        if (c == null) {
            return false;
        }
        
        for (int i = 0; i < contador; i++) {
            if (contactos[i].equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    // Método para buscar un contacto por cualquier campo
    public Contacto buscaContacto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return null;
        }
        
        String textoBuscar = texto.toLowerCase();
        
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().toLowerCase().contains(textoBuscar) || 
                contactos[i].getApellido().toLowerCase().contains(textoBuscar) || 
                contactos[i].getTelefono().toLowerCase().contains(textoBuscar)) {
                return contactos[i];
            }
        }
        
        // Si no se encuentra coincidencia parcial, intentar con método original
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(texto) || 
                contactos[i].getApellido().equalsIgnoreCase(texto)) {
                return contactos[i];
            }
        }
        
        return null;
    }
    
    // Método para eliminar un contacto
    public boolean eliminarContacto(Contacto c) {
        if (c == null) {
            return false;
        }
        
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(c.getNombre()) && 
                contactos[i].getApellido().equalsIgnoreCase(c.getApellido())) {
                
                // Desplazar los elementos siguientes una posición hacia atrás
                for (int j = i; j < contador - 1; j++) {
                    contactos[j] = contactos[j+1];
                }
                
                // Eliminar la referencia del último elemento y decrementar contador
                contactos[contador - 1] = null;
                contador--;
                
                System.out.println("Contacto eliminado con éxito");
                return true;
            }
        }
        
        System.out.println("No se encontró el contacto para eliminar");
        return false;
    }
    
    // Método para verificar si la agenda está llena
    public boolean agendaLlena() {
        return contador >= tamanoMax;
    }
    
    // Método para saber cuántos espacios libres hay
    public int espacioLibres() {
        return tamanoMax - contador;
    }
    
    // Método para listar los contactos
    public void listarContactos() {
        if (contador == 0) {
            System.out.println("No hay contactos en la agenda");
            return;
        }
        
        System.out.println("Lista de contactos:");
        for (int i = 0; i < contador; i++) {
            System.out.println((i+1) + ". " + contactos[i].toString());
        }
    }
    
    // Validar telefono repetido al modificar
    public boolean modificarTelefono(String nombre, String apellido, String telefono) {
        Contacto c = buscaContactoPorNombreApellido(nombre, apellido);
        
        if (c != null) {
            // Si el teléfono es diferente al actual, verificar si ya existe
            if (!c.getTelefono().equals(telefono) && existeTelefono(telefono)) {
                return false;
            }
            
            c.setTelefono(telefono);
            return true;
        }
        
        return false;
    }
    
    // Método auxiliar para buscar contacto por nombre y apellido exactos
    private Contacto buscaContactoPorNombreApellido(String nombre, String apellido) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre) && 
                contactos[i].getApellido().equalsIgnoreCase(apellido)) {
                return contactos[i];
            }
        }
        return null;
    }
    
    // Método para obtener todos los contactos como una lista ordenada
    public ArrayList<Contacto> getContactos() {
        ArrayList<Contacto> listaContactos = new ArrayList<>();
        
        // Añadir todos los contactos válidos a la lista
        for (int i = 0; i < contador; i++) {
            if (contactos[i] != null) {
                listaContactos.add(contactos[i]);
            }
        }
        
        // Ordenar la lista por nombre
        if (!listaContactos.isEmpty()) {
            Collections.sort(listaContactos, new Comparator<Contacto>() {
                @Override
                public int compare(Contacto c1, Contacto c2) {
                    // Primero por nombre
                    int comparaNombre = c1.getNombre().compareToIgnoreCase(c2.getNombre());
                    if (comparaNombre != 0) {
                        return comparaNombre;
                    }
                    // Si nombres iguales, por apellido
                    return c1.getApellido().compareToIgnoreCase(c2.getApellido());
                }
            });
        }
        
        return listaContactos;
    }
    
    // Getter para el tamaño máximo
    public int getTamanoMax() {
        return tamanoMax;
    }
}