/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author My_Love_Is_My
 */
public class FXMLUtils {
    public static <T extends Parent> void loadFXML(T component) {
    FXMLLoader loader = new FXMLLoader();
    loader.setRoot(component);
    loader.setControllerFactory(theClass -> component);

    String fileName = component.getClass().getSimpleName() + ".fxml";
    try {
        loader.load(component.getClass().getResourceAsStream(fileName));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
}
