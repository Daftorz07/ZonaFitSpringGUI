package co.zonaFit.GUI;

import co.zonaFit.modelo.Cliente;
import co.zonaFit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class ZonaFitGUI extends JFrame {

    private JPanel panelPrincipal;
    private JTable tablaClientes;
    private JTextField nombreCliente;
    private JTextField apellidoCliente;
    private JTextField membershipCliente;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    //Se utiliza la clase para evitar obtener el valor primitivo (0) si no el de Null correspondiente a la clase
    private Integer idCliente;

    //Interfaz de servicio
    IClienteServicio clienteServicio;

    //Propiedades para la tabla
    private DefaultTableModel tablaModeloClientes;

    //Inyección de dependencia a través del constructor
    @Autowired
    public ZonaFitGUI(IClienteServicio clienteServicio) {

        //Inyección de dependencia
        this.clienteServicio = clienteServicio;
        //Iniciando la ventana principal
        iniciarVentanaPrincipal();

        // ---------------------------------------------------------------------------
        // Eventos -------------------------------------------------------------------
        // ---------------------------------------------------------------------------
        //Guardar cliente
        btnGuardar.addActionListener(e -> guardarCliente());

        //Escuchar clic sobre algún registro de la tabla de clientes
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });

        //Limpiar Formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        //Eliminar Registro de cliente
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    // ---------------------------------------------------------------------------
    // Configuración de la ventana principal -------------------------------------
    // ---------------------------------------------------------------------------
    private void iniciarVentanaPrincipal() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    /*
     * Se ejecuta que el constructor, el metodo se genera desde la interfaz en "custom créate"
     * Se ejecuta para todos los elementos marcados con "custom créate"
     * es decir son los elementos que se crearan manualmente y no de forma automática
     */
    private void createUIComponents() {

        //Configuración de la tabla
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresía"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);

        //Creacion de la tabla
        this.tablaClientes = new JTable(tablaModeloClientes);

        //Cargando los clientes a la tabla
        listarClientes();
    }

    // ---------------------------------------------------------------------------
    // Metodo Utilitarios --------------------------------------------------------
    // ---------------------------------------------------------------------------
    private void limpiarFormulario() {
        nombreCliente.setText("");
        apellidoCliente.setText("");
        membershipCliente.setText("");

        //Limpiamos ID del cliente seleccionado
        idCliente = null;
        //Deseleccionamos el registro seleccionado de la tabla
        this.tablaClientes.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String campo) {
        String mensaje = "El campo " + campo + " es obligatorio";
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // ---------------------------------------------------------------------------
    // listar Clientes -----------------------------------------------------------
    // ---------------------------------------------------------------------------
    private void listarClientes() {
        //Tamaño inicial de las filas de la tabla
        this.tablaModeloClientes.setRowCount(0);

        //Se obtiene el listado de clientes
        List<Cliente> listaClientes = this.clienteServicio.listarCliente();

        listaClientes.forEach(cliente -> {
            //Creando el registro del cliente
            Object[] rowCliente = {
                    cliente.getId_cliente(),
                    cliente.getUserName(),
                    cliente.getLastName(),
                    cliente.getMembership()
            };
            //Agregando el registro a la tabla
            this.tablaModeloClientes.addRow(rowCliente);
        });
    }

    // ---------------------------------------------------------------------------
    // Guardar Clientes ----------------------------------------------------------
    // ---------------------------------------------------------------------------
    private void guardarCliente() {

        if (nombreCliente.getText().isEmpty()) {
            mostrarMensaje("Nombre");
            nombreCliente.requestFocusInWindow(); // recupera el foco
            return;
        }
        if (apellidoCliente.getText().isEmpty()) {
            mostrarMensaje("Apellido");
            apellidoCliente.requestFocusInWindow(); // recupera el foco
            return;
        }
        if (membershipCliente.getText().isEmpty()) {
            mostrarMensaje("Membresía");
            membershipCliente.requestFocusInWindow(); // recupera el foco
        }

        //Se recuperan los datos
        String username = nombreCliente.getText().trim();
        String lastname = apellidoCliente.getText().trim();
        int membership = Integer.parseInt(membershipCliente.getText().trim());

        //Objeto cliente
        Cliente newCliente = new Cliente(this.idCliente, username, lastname, membership);

        /*
         * Si el ID_Cliente es NULL, Spring hace la actualización
         * Si trae algún valor significa que el registro será actualizado
         */

        //Se guarda el cliente
        clienteServicio.guardarCliente(newCliente);

        //Verificación de operación
        if (this.idCliente == null) {
            JOptionPane.showMessageDialog(this, "Nuevo cliente guardado");
        } else {
            JOptionPane.showMessageDialog(this, "Cliente actualizado");
        }

        //Se ejecutan los metodos
        limpiarFormulario();
        listarClientes();
    }

    // ---------------------------------------------------------------------------
    // Actualizar Clientes -------------------------------------------------------
    // ---------------------------------------------------------------------------
    private void cargarClienteSeleccionado() {

        int registroSeleccionado = tablaClientes.getSelectedRow();

        // -1 si no se selecciona ningún registro
        if (registroSeleccionado != -1) {

            //Recuperando el ID del cliente que se ha seleccionado
            String idClienteSeleccionado = tablaClientes.getModel().getValueAt(registroSeleccionado, 0).toString();
            this.idCliente = Integer.parseInt(idClienteSeleccionado);

            //Seteando los valores del registro seleccionado en el formulario
            String nombreSeleccionado = tablaClientes.getModel().getValueAt(registroSeleccionado, 1).toString();
            nombreCliente.setText(nombreSeleccionado);

            String apellidoSeleccionado = tablaClientes.getModel().getValueAt(registroSeleccionado, 2).toString();
            apellidoCliente.setText(apellidoSeleccionado);

            String membershipSeleccionado = tablaClientes.getModel().getValueAt(registroSeleccionado, 3).toString();
            membershipCliente.setText(membershipSeleccionado);

            //Id Cliente Seleccionado
            System.out.println(idCliente);
        }
    }

    // ---------------------------------------------------------------------------
    // Eliminar Clientes ---------------------------------------------------------
    // ---------------------------------------------------------------------------
}
