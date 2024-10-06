package co.zonaFit.servicio;

import co.zonaFit.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {

    public List<Cliente> listarCliente();
    public Cliente buscarClienteId(Integer idCliente);
    public void guardarCliente(Cliente cliente);
    public void eliminarCliente(Cliente cliente);

}
