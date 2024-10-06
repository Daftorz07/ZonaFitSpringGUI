package co.zonaFit.servicio;

import co.zonaFit.modelo.Cliente;
import co.zonaFit.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * La anotación @Service en Spring Framework se utiliza para marcar una clase como un componente de servicio.
 * En términos generales, una clase marcada con @Service contiene la lógica de negocio de la aplicación
 * y representa un servicio que puede ser inyectado y utilizado en otras partes de la aplicación
 * (como en controladores o repositorios).
 */
@Service
public class ClienteServicio implements IClienteServicio {

    /*
     * La anotación @Autowired en Spring Framework se utiliza para inyectar dependencias de manera automática
     * en una clase gestionada por el contenedor de Spring
     */
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public Cliente buscarClienteId(Integer idCliente) {
        //En caso de no encontrar el cliente orElse retorna NULL
        return clienteRepositorio.findById(idCliente).orElse(null);
    }

    @Override
    public List<Cliente> listarCliente() {
        return clienteRepositorio.findAll();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        //Si el cliente no existe hace el Insert sino lo Actualiza
        clienteRepositorio.save(cliente);

    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}
