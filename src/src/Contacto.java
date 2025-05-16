public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto() {
    }

    public Contacto(String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Tienes que ingresar un nombre valido : 😕😕😕");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("Tienes que ingresar un apellido valido : 😕😕😕");
        }
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellido: " + apellido + " Teléfono: " + telefono;
    }

}

