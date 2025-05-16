import java.util.ArrayList;

public class Registro {

    private int tamanoMax;
    public ArrayList<Contacto> listaContactos;


    //Contructor
    public Registro(int tamanoMax) {
        this.tamanoMax = tamanoMax;
        listaContactos = new ArrayList<>();
    }

    //Lista de contactos

    public ArrayList<Contacto> registrarContactos(Contacto cont){
        listaContactos.add(cont);
        return listaContactos;
    }

    // Añadir contacto

    public void añadirContacto (Contacto cont){
        if(existeContacto(cont)){
            System.out.println("El contacto ya existe.");
        } else{
            listaContactos.add(cont);
            System.out.println("Se ha registrado exitosamente el contacto.");
        }
    }


    // Existe Contacto

    public boolean existeContacto (Contacto cont){
        for (Contacto contacto : listaContactos){
            if(contacto.equals((cont))){
                return true;
            }
        }
        return false;
    }


    //Verificar Agenda llena

    public boolean agendaLlena(){
        return listaContactos.size() >= tamanoMax;
    }

    //Verificar espacio libres

    public int espaciosLibres(){
        return tamanoMax - listaContactos.size();
    }

}
