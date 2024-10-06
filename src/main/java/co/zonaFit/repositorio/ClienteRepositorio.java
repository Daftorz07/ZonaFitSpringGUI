package co.zonaFit.repositorio;

import co.zonaFit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

//El repositorio extiende de JpaRepository y le pasamos la clase con la que se trabajará y el tipo de la PrimaryKey
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

}
