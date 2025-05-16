import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AgendaGUI extends JFrame {
    private Agenda agenda;
    private JTable tablaContactos;
    private DefaultTableModel modeloTabla;
    private JTextField campoNombre, campoApellido, campoTelefono, campoBuscar;
    private JLabel etiquetaEstado;
    private JLabel etiquetaInfo;
    private JButton botonAgregar, botonModificar, botonLimpiar;
    
    // Colores mejorados con menos brillo
    private Color colorPrincipal = new Color(60, 110, 160);
    private Color colorSecundario = new Color(235, 240, 245);
    private Color colorTexto = new Color(10, 10, 60);
    private Color colorBoton = new Color(230, 235, 240);
    private Color colorBotonHover = new Color(210, 220, 235);
    
    public AgendaGUI() {
        // Inicializar la agenda con tamaño personalizado o por defecto
        int tamanoAgenda = mostrarDialogoTamanoAgenda();
        agenda = new Agenda(tamanoAgenda);
        
        // Configurar la ventana principal
        setTitle("Mi Agenda de Contactos");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(colorSecundario);
        
        // Configurar el layout principal con un poco de espacio entre componentes
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Crear y añadir los paneles
        JPanel panelSuperior = crearPanelSuperior();
        JPanel panelCentral = crearPanelCentral();
        JPanel panelInferior = crearPanelInferior();
        
        // Dar más espacio a la tabla central
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Cargar algunos contactos de ejemplo
        cargarContactosEjemplo();
        
        // Actualizar la tabla de contactos al inicio
        actualizarTablaContactos();
    }
    
    private int mostrarDialogoTamanoAgenda() {
        Object[] opciones = {"Usar tamaño por defecto (10)", "Personalizar tamaño"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo desea configurar su agenda?",
                "Configuración de Agenda",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        
        // Si selecciona Por defecto o cierra el diálogo
        if (seleccion == 0 || seleccion == JOptionPane.CLOSED_OPTION) {
            return 10;
        }
        
        // Si selecciona Personalizar, mostrar diálogo para ingresar tamaño
        String input = JOptionPane.showInputDialog(
                null,
                "Introduzca el tamaño máximo de la agenda (número entero):",
                "Tamaño Personalizado",
                JOptionPane.QUESTION_MESSAGE);
        
        // Si el usuario cancela, usar valor predeterminado
        if (input == null || input.trim().isEmpty()) {
            return 10;
        }
        
        try {
            int tamano = Integer.parseInt(input.trim());
            if (tamano <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "Valor inválido. Se usará el tamaño predeterminado: 10", 
                    "Error de entrada", 
                    JOptionPane.WARNING_MESSAGE);
                return 10;
            }
            return tamano;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Valor inválido. Se usará el tamaño predeterminado: 10", 
                "Error de entrada", 
                JOptionPane.WARNING_MESSAGE);
            return 10;
        }
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        // Título con efecto de fuente mejorado
        JLabel titulo = new JLabel("Agenda de Contactos", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(colorPrincipal);
        titulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        // Panel de búsqueda con diseño mejorado
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBusqueda.setOpaque(false);
        
        // Agregar etiqueta informativa sobre espacios disponibles
        etiquetaInfo = new JLabel();
        etiquetaInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        etiquetaInfo.setForeground(colorTexto);
        actualizarEtiquetaInfo(etiquetaInfo);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInfo.setOpaque(false);
        panelInfo.add(etiquetaInfo);
        
        campoBuscar = new JTextField(15);
        campoBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoBuscar.setForeground(colorTexto);
        campoBuscar.setBorder(BorderFactory.createCompoundBorder(
                campoBuscar.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // Agregar acción al presionar Enter en el campo de búsqueda
        campoBuscar.addActionListener(e -> buscarContacto());
        
        JButton botonBuscar = createStyledButton("Buscar");
        botonBuscar.setPreferredSize(new Dimension(90, 30));
        botonBuscar.addActionListener(e -> buscarContacto());
        
        JButton botonMostrarTodos = createStyledButton("Mostrar Todos");
        botonMostrarTodos.setPreferredSize(new Dimension(120, 30));
        botonMostrarTodos.addActionListener(e -> mostrarTodosContactos());
        
        JLabel etiquetaBuscar = new JLabel("Buscar: ");
        etiquetaBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        etiquetaBuscar.setForeground(colorTexto);
        
        panelBusqueda.add(etiquetaBuscar);
        panelBusqueda.add(campoBuscar);
        panelBusqueda.add(botonBuscar);
        panelBusqueda.add(botonMostrarTodos);
        panelBusqueda.add(etiquetaInfo);
        
        // Añadir componentes al panel superior
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void actualizarEtiquetaInfo(JLabel etiqueta) {
        int espaciosLibres = agenda.espacioLibres();
        int tamanoMax = agenda.getTamanoMax();
        etiqueta.setText("Contactos: " + (tamanoMax - espaciosLibres) + "/" + tamanoMax);
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(245, 245, 250));
        
        // Borde con título mejorado
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorPrincipal, 2),
                "Lista de Contactos");
        borde.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        borde.setTitleColor(colorTexto);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde,
                new EmptyBorder(10, 10, 10, 10)));
        
        // Crear modelo de tabla y tabla con mejor apariencia
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Teléfono");
        
        tablaContactos = new JTable(modeloTabla);
        tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaContactos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaContactos.setRowHeight(30);
        tablaContactos.setGridColor(new Color(210, 210, 230));
        tablaContactos.setForeground(colorTexto);
        tablaContactos.setShowHorizontalLines(true);
        tablaContactos.setShowVerticalLines(true);
        
        // Mejorar el aspecto del encabezado de la tabla
        JTableHeader header = tablaContactos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(colorPrincipal);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        tablaContactos.setSelectionBackground(new Color(176, 196, 222));
        
        // Eventos de doble clic en la tabla
        tablaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarContactoSeleccionado();
                }
            }
        });
        
        // Agregar tabla a un scroll pane con mejor apariencia
        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        scrollPane.setBorder(BorderFactory.createLineBorder(colorPrincipal, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Asegurar que la tabla tenga suficiente espacio
        // La tabla ocupa el máximo espacio disponible en el panel central
        panel.add(scrollPane, BorderLayout.CENTER);
        

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setOpaque(false);
        
        JButton botonActualizar = createStyledButton("Actualizar");
        JButton botonEliminar = createStyledButton("Eliminar");
        JButton botonEditar = createStyledButton("Editar");
        

        Dimension tamanoBoton = new Dimension(120, 30);
        botonActualizar.setPreferredSize(tamanoBoton);
        botonEliminar.setPreferredSize(tamanoBoton);
        botonEditar.setPreferredSize(tamanoBoton);
        
        botonActualizar.addActionListener(e -> actualizarTablaContactos());
        botonEliminar.addActionListener(e -> eliminarContactoSeleccionado());
        botonEditar.addActionListener(e -> editarContactoSeleccionado());
        
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(245, 245, 250));
        
        // Borde
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorPrincipal, 2),
                "Datos del Contacto");
        borde.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        borde.setTitleColor(colorTexto);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde,
                new EmptyBorder(10, 10, 10, 10)));
        
        // Panel para campos de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setOpaque(false);
        
        // Crear etiquetas y campos
        JLabel etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        etiquetaNombre.setForeground(colorTexto);
        
        JLabel etiquetaApellido = new JLabel("Apellido:");
        etiquetaApellido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        etiquetaApellido.setForeground(colorTexto);
        
        JLabel etiquetaTelefono = new JLabel("Teléfono:");
        etiquetaTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
        etiquetaTelefono.setForeground(colorTexto);
        
        campoNombre = new JTextField();
        campoNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoNombre.setForeground(colorTexto);
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                campoNombre.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        campoApellido = new JTextField();
        campoApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoApellido.setForeground(colorTexto);
        campoApellido.setBorder(BorderFactory.createCompoundBorder(
                campoApellido.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        campoTelefono = new JTextField();
        campoTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoTelefono.setForeground(colorTexto);
        campoTelefono.setBorder(BorderFactory.createCompoundBorder(
                campoTelefono.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        panelFormulario.add(etiquetaNombre);
        panelFormulario.add(campoNombre);
        panelFormulario.add(etiquetaApellido);
        panelFormulario.add(campoApellido);
        panelFormulario.add(etiquetaTelefono);
        panelFormulario.add(campoTelefono);
        
        // Panel para botones del formulario
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setOpaque(false);
        
        botonAgregar = createStyledButton("Agregar");
        botonModificar = createStyledButton("Modificar");
        botonLimpiar = createStyledButton("Limpiar");
        
        // Botones
        Dimension tamanoBoton = new Dimension(120, 30);
        botonAgregar.setPreferredSize(tamanoBoton);
        botonModificar.setPreferredSize(tamanoBoton);
        botonLimpiar.setPreferredSize(tamanoBoton);
        
        botonAgregar.addActionListener(e -> agregarContacto());
        botonModificar.addActionListener(e -> modificarTelefono());
        botonLimpiar.addActionListener(e -> limpiarCampos());
        
        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonLimpiar);
        
        // Etiqueta de estado para mensajes
        etiquetaEstado = new JLabel(" ");
        etiquetaEstado.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        etiquetaEstado.setHorizontalAlignment(JLabel.CENTER);
        etiquetaEstado.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        panel.add(panelFormulario, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        panel.add(etiquetaEstado, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(colorBoton);
        button.setForeground(colorTexto);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorPrincipal, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        // Efecto hover para los botones
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorBotonHover);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorBoton);
            }
        });
        
        return button;
    }
    
    private void buscarContacto() {
        String textoBuscar = campoBuscar.getText().trim();
        if (textoBuscar.isEmpty()) {
            mostrarMensaje("Ingrese un texto para buscar", false);
            return;
        }
        
        // Limpiar tabla y mostrar solo los contactos que coinciden con la búsqueda
        modeloTabla.setRowCount(0);
        boolean encontrado = false;
        
        // Obtener todos los contactos
        ArrayList<Contacto> contactos = agenda.getContactos();
        if (contactos == null || contactos.isEmpty()) {
            mostrarMensaje("No hay contactos en la agenda", false);
            return;
        }
        
        String busquedaLower = textoBuscar.toLowerCase();
        
        for (Contacto contacto : contactos) {
            String nombre = contacto.getNombre().toLowerCase();
            String apellido = contacto.getApellido().toLowerCase();
            String telefono = contacto.getTelefono().toLowerCase();
            String nombreCompleto = nombre + " " + apellido;
            
            // Buscar coincidencias con el texto ingresado
            if (nombre.contains(busquedaLower) || 
                apellido.contains(busquedaLower) || 
                telefono.contains(busquedaLower) || 
                nombreCompleto.contains(busquedaLower)) {
                
                // Agregar el contacto coincidente a la tabla
                modeloTabla.addRow(new Object[]{
                    contacto.getNombre(), 
                    contacto.getApellido(), 
                    contacto.getTelefono()
                });
                encontrado = true;
            } else {
                // Si el texto de búsqueda tiene espacios, comprobar cada palabra individualmente
                String[] palabras = busquedaLower.split("\\s+");
                if (palabras.length > 1) {
                    // Verificar si todas las palabras están presentes
                    boolean todasLasPalabrasCoinciden = true;
                    for (String palabra : palabras) {
                        if (!nombre.contains(palabra) && !apellido.contains(palabra) && 
                            !telefono.contains(palabra) && !nombreCompleto.contains(palabra)) {
                            todasLasPalabrasCoinciden = false;
                            break;
                        }
                    }
                    
                    if (todasLasPalabrasCoinciden) {
                        // Agregar contacto que coincide con todas las palabras
                        modeloTabla.addRow(new Object[]{
                            contacto.getNombre(), 
                            contacto.getApellido(), 
                            contacto.getTelefono()
                        });
                        encontrado = true;
                    }
                }
            }
        }
        
        if (encontrado) {
            mostrarMensaje("Se encontraron " + modeloTabla.getRowCount() + 
                          " contactos que coinciden con \"" + textoBuscar + "\"", true);
            
            // Si solo hay un resultado, seleccionarlo automáticamente
            if (modeloTabla.getRowCount() == 1) {
                tablaContactos.setRowSelectionInterval(0, 0);
                // Mostrar los datos en el formulario
                campoNombre.setText(modeloTabla.getValueAt(0, 0).toString());
                campoApellido.setText(modeloTabla.getValueAt(0, 1).toString());
                campoTelefono.setText(modeloTabla.getValueAt(0, 2).toString());
            }
        } else {
            mostrarMensaje("No se encontraron contactos que coincidan con \"" + textoBuscar + "\"", false);
            // Mostrar todos los contactos en caso de no encontrar coincidencias
            actualizarTablaContactos();
        }
        
        // Actualizar información de capacidad
        actualizarEtiquetaInfo(etiquetaInfo);
    }
    
    // Botón adicional para mostrar todos los contactos
    private void mostrarTodosContactos() {
        actualizarTablaContactos();
        limpiarCampos();
        mostrarMensaje("Mostrando todos los contactos", true);
    }
    
    private void actualizarTablaContactos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Obtener los contactos de la agenda
        ArrayList<Contacto> contactos = agenda.getContactos();
        

        if (contactos != null && !contactos.isEmpty()) {
            // Añadir cada contacto a la tabla
            for (Contacto c : contactos) {
                modeloTabla.addRow(new Object[]{c.getNombre(), c.getApellido(), c.getTelefono()});
            }
            mostrarMensaje("Lista de contactos actualizada", true);
        } else {
            mostrarMensaje("No hay contactos en la agenda", false);
        }
        

        actualizarEtiquetaInfo(etiquetaInfo);
    }
    
    private void eliminarContactoSeleccionado() {
        int filaSeleccionada = tablaContactos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) tablaContactos.getValueAt(filaSeleccionada, 0);
            String apellido = (String) tablaContactos.getValueAt(filaSeleccionada, 1);
            
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea eliminar a " + nombre + " " + apellido + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                Contacto contacto = new Contacto(nombre, apellido, "");
                boolean resultado = agenda.eliminarContacto(contacto);
                
                if (resultado) {
                    // Actualizar la tabla
                    actualizarTablaContactos();
                    limpiarCampos();
                    mostrarMensaje("Contacto eliminado correctamente", true);
                } else {
                    mostrarMensaje("No se pudo eliminar el contacto", false);
                }
            }
        } else {
            mostrarMensaje("Seleccione un contacto de la lista para eliminar", false);
        }
    }
    
    private void editarContactoSeleccionado() {
        int filaSeleccionada = tablaContactos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) tablaContactos.getValueAt(filaSeleccionada, 0);
            String apellido = (String) tablaContactos.getValueAt(filaSeleccionada, 1);
            String telefono = (String) tablaContactos.getValueAt(filaSeleccionada, 2);
            
            // Llenar el formulario con los datos del contacto seleccionado
            campoNombre.setText(nombre);
            campoApellido.setText(apellido);
            campoTelefono.setText(telefono);
            
            mostrarMensaje("Editando contacto: " + nombre + " " + apellido, true);
        } else {
            mostrarMensaje("Seleccione un contacto de la lista para editar", false);
        }
    }
    
    private void agregarContacto() {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String telefono = campoTelefono.getText().trim();
        
        if (validarCampos(nombre, apellido, telefono)) {
            // Verificar si el teléfono ya existe en la agenda
            if (telefonoExistente(telefono)) {
                mostrarMensaje("Error: El número de teléfono ya existe en la agenda", false);
                return;
            }
            
            Contacto contacto = new Contacto(nombre, apellido, telefono);
            boolean resultado = agenda.anadirContacto(contacto);
            
            if (resultado) {
                // Actualizar la tabla y limpiar campos
                actualizarTablaContactos();
                limpiarCampos();
                mostrarMensaje("Contacto agregado correctamente", true);
            } else {
                mostrarMensaje("No se pudo agregar el contacto. La agenda está llena o el contacto ya existe", false);
            }
        }
    }
    
    private void modificarTelefono() {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String telefono = campoTelefono.getText().trim();
        
        if (validarCampos(nombre, apellido, telefono)) {
            // Obtener el teléfono actual para este contacto
            int filaSeleccionada = tablaContactos.getSelectedRow();
            String telefonoActual = "";
            
            if (filaSeleccionada >= 0) {
                telefonoActual = tablaContactos.getValueAt(filaSeleccionada, 2).toString();
            }
            
            // Si el teléfono es diferente al actual, verificar si ya existe
            if (!telefono.equals(telefonoActual) && telefonoExistente(telefono)) {
                mostrarMensaje("Error: El número de teléfono ya existe en la agenda", false);
                return;
            }
            
            boolean resultado = agenda.modificarTelefono(nombre, apellido, telefono);
            
            if (resultado) {
                // Actualizar la tabla y limpiar campos
                actualizarTablaContactos();
                limpiarCampos();
                mostrarMensaje("Teléfono actualizado correctamente", true);
            } else {
                mostrarMensaje("No se pudo actualizar el teléfono. Contacto no encontrado", false);
            }
        }
    }
    
    // verificar si un teléfono ya existe en la agenda
    private boolean telefonoExistente(String telefono) {
        ArrayList<Contacto> contactos = agenda.getContactos();
        if (contactos != null) {
            for (Contacto c : contactos) {
                if (c.getTelefono().equals(telefono)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean validarCampos(String nombre, String apellido, String telefono) {
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios", false);
            return false;
        }
        
        // Validación básica para el teléfono (solo números)
        if (!telefono.matches("\\d+")) {
            mostrarMensaje("El teléfono debe contener solo números", false);
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        campoNombre.setText("");
        campoApellido.setText("");
        campoTelefono.setText("");
        etiquetaEstado.setText(" ");
    }
    
    private void mostrarMensaje(String mensaje, boolean exito) {
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.setForeground(exito ? new Color(0, 128, 0) : Color.RED);
        
        // Temporizador para limpiar el mensaje después de 3 segundos
        Timer timer = new Timer(3000, e -> etiquetaEstado.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void cargarContactosEjemplo() {
        // Contactos de Ejemplo
        agenda.anadirContacto(new Contacto("Juan", "Pérez", "1234567890"));
        agenda.anadirContacto(new Contacto("María", "López", "9876543210"));
        agenda.anadirContacto(new Contacto("Carlos", "González", "5555555555"));
        agenda.anadirContacto(new Contacto("Ana", "Martínez", "1111111111"));
        agenda.anadirContacto(new Contacto("Pedro", "Rodríguez", "2222222222"));
        agenda.anadirContacto(new Contacto("Laura", "Sánchez", "3333333333"));
        agenda.anadirContacto(new Contacto("Roberto", "Fernández", "4444444444"));
        agenda.anadirContacto(new Contacto("Carmen", "Díaz", "6666666666"));
        agenda.anadirContacto(new Contacto("Miguel", "Torres", "7777777777"));
        agenda.anadirContacto(new Contacto("Sofía", "Ramírez", "8888888888"));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            AgendaGUI ventana = new AgendaGUI();
            ventana.setVisible(true);
        });
    }
}