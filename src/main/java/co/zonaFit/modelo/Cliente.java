package co.zonaFit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity //Le indica a Java que es una clase de entidad
@Data //Generación de los Getters y Setters de forma automática
@NoArgsConstructor //Genera constructor sin argumentos
@AllArgsConstructor //Genera constructor con todos los argumentos
@EqualsAndHashCode //Genera los metodos equals y hashcode
public class Cliente {

    //Configuración de propiedades/atributos de la clase
    @Id //llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que el ID será generado por la DB
    private  Integer id_cliente;
    private String userName;
    private String lastName;
    private Integer membership;
}

