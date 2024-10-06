package co.zonaFit;

import com.formdev.flatlaf.FlatDarculaLaf;
import co.zonaFit.GUI.ZonaFitGUI;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {

    public static void main(String[] args) {

        //Dark mode
        FlatDarculaLaf.setup();

        //Instanciando la fábrica de Spring
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(ZonaFitSwing.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        //Creando el objeto de Swing una vez se termina de inicializar la fábrica de Spring
        SwingUtilities.invokeLater(()-> {
            //Este codigo es necesario por el uso de la inyección de dependencia desde el constructor
            ZonaFitGUI zonaFitGUI = contextSpring.getBean(ZonaFitGUI.class);
            zonaFitGUI.setVisible(true);
        });
    }
}
