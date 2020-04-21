/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
@author Mohammed Hadra (cph-mh879@cphbusiness.dk)
@author Andreas Bergmann (cph-ab435@cphbusiness.dk)
@author Jacob Lange Nielsen (cph-jn352@cphbusiness.dk)
 */
import Controller.MainController;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        MainController mainController = new MainController();
        mainController.runApplication();

    }
}
