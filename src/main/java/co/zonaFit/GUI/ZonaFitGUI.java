package co.zonaFit.GUI;

import co.zonaFit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ZonaFitGUI {

    private JPanel panelPrincipal;
    IClienteServicio clienteServicio;

    //Inyección de dependencia a través del constructor
    @Autowired
    public ZonaFitGUI(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }
}
