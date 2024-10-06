package co.zonaFit;

import co.zonaFit.modelo.Cliente;
import co.zonaFit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

/*
 * La interfaz CommandLineRunner en Spring Boot es utilizada para ejecutar código específico
 * una vez que la aplicación Spring Boot ha iniciado completamente.
 * Básicamente, te permite ejecutar lógica de negocio al inicio de la aplicación.
 */

//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

    @Autowired
    private IClienteServicio clienteServicio;

    //Es utilizada para definir un logger (registro de eventos o mensajes) en una clase dentro de una aplicación Java.
    private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

    String nl = System.lineSeparator();

    public static void main(String[] args) {

        //Logger reemplaza a println en aplicaciones que utilizan Spring
        logger.info("Iniciando Aplicacion");

        //Inicializa Spring
        SpringApplication.run(ZonaFitApplication.class, args);

        logger.info("Aplicación Finalizada");
    }

    /*
     * Este método es invocado automáticamente al iniciar la aplicación, justo después de que
     * el contexto de Spring haya terminado de inicializarse.
     */
    @Override
    public void run(String... args) throws Exception {

        //Ejecutando la aplicación
        zonaFitApp();
    }

    private void zonaFitApp() {

        boolean salirApp = false;
        Scanner consola = new Scanner(System.in);

        while (!salirApp) {
            try {
                int opcion = mostrarMenu(consola);
                salirApp = ejecutarOpciones(consola, opcion);
            } catch (Exception e) {
                logger.info("Error al intentar ejecutar la opción: {}", e.getClass().getName());
                logger.info("Motivo: {}", e.getMessage());
                logger.info("--------------------------------");
                throw new RuntimeException(e);
            }
            logger.info(nl);
        }
    }

    private int mostrarMenu(Scanner consola) {
        logger.info("{}*** Aplicación Zona Fit (GYM) ***", nl);
        logger.info("""
                1. Listar Clientes\s
                2. Buscar Clientes\s
                3. Agregar Clientes\s
                4. Actualizar Clientes\s
                5. Eliminar Clientes\s
                6. Salir App\s
                Elige una opcion: \s""");

        return Integer.parseInt(consola.nextLine());
    }


    private boolean ejecutarOpciones(Scanner consola, int opcion) {
        boolean salirApp = false;

        switch (opcion) {
            case 1 -> {
                //Listar Clientes
                logger.info("{}--- Listado de Clientes ---", nl);
                List<Cliente> listaClientes = clienteServicio.listarCliente();
                listaClientes.forEach(cliente -> logger.info(cliente.toString()));
            }
            case 2 -> {
                //Buscar Cliente por ID
                logger.info("Ingrese el ID del Cliente a buscar: ");

                int idCliente = Integer.parseInt(consola.nextLine());
                Cliente newCliente = clienteServicio.buscarClienteId(idCliente);

                if (newCliente != null) {
                    logger.info("Cliente encontrado: {}", newCliente.toString());
                }else {
                    logger.info("Cliente encontrado");
                }
            }
            case 3 -> {
                //Agregar Cliente
                System.out.println("--- Agregar Cliente ---");

                //Solicitando los datos del clienteDAO
                System.out.print("Ingrese el nombre de Cliente: ");
                String userNameCliente = consola.nextLine();

                System.out.print("Ingrese el apellido de Cliente: ");
                String lastNameCliente = consola.nextLine();

                System.out.print("Ingrese la membresía del Cliente: ");
                int membershipCliente = Integer.parseInt(consola.nextLine());

                //Objeto Cliente
                Cliente insertCliente = new Cliente();
                insertCliente.setUserName(userNameCliente);
                insertCliente.setLastName(lastNameCliente);
                insertCliente.setMembership(membershipCliente);

                //Agregando el Cliente
                clienteServicio.guardarCliente(insertCliente);
                logger.info("Cliente agregado {}", insertCliente.toString());
            }
            case 4 -> {
                //Actualizar Clientes
                logger.info("--- Actualizar Clientes ---");

                //Solicitando los datos del clienteDAO
                logger.info("Ingrese el ID del cliente a actualizar: ");
                int userIdCliente = Integer.parseInt(consola.nextLine());

                Cliente cliente = clienteServicio.buscarClienteId(userIdCliente);

                if(cliente != null) {
                    //Solicitando los datos cliente
                    System.out.print("Ingrese el nombre de Cliente: ");
                    String userNameCliente = consola.nextLine();

                    System.out.print("Ingrese el apellido de Cliente: ");
                    String lastNameCliente = consola.nextLine();

                    System.out.print("Ingrese la membresía del Cliente: ");
                    int membershipCliente = Integer.parseInt(consola.nextLine());

                    cliente.setUserName(userNameCliente);
                    cliente.setLastName(lastNameCliente);
                    cliente.setMembership(membershipCliente);

                    clienteServicio.guardarCliente(cliente);

                    logger.info("Cliente Actualizado: {}", cliente.toString());
                }
            }
            case 5 -> {
                //Eliminar Clientes
                logger.info("--- Eliminar Clientes ---");

                //Solicitando los datos del clienteDAO
                logger.info("Ingrese el ID del cliente a eliminar: ");
                int userIdCliente = Integer.parseInt(consola.nextLine());

                Cliente clienteDelete = new Cliente();
                clienteDelete.setId_cliente(userIdCliente);

                clienteServicio.eliminarCliente(clienteDelete);
                logger.info("Cliente Eliminado");
            }
            case 6 -> {
                logger.info("Regresa Pronto!!!");
                salirApp = true;
            }
            default -> {
                logger.info("La opcion ingresada no es correcta: {}", opcion);
            }
        }
        return salirApp;
    }
}
