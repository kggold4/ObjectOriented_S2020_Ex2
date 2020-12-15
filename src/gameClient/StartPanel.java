package gameClient;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class StartPanel {
    public static String getId () {
        ImageIcon icon = new ImageIcon("./images/vg_logo3.png");
        String id = (String)JOptionPane.showInputDialog(
                null,
                "Please enter ID number: ",
                "Enter ID",
                JOptionPane.QUESTION_MESSAGE,
                icon,
                null,
                0
        );
        return id;
    }
    public static String getSen () {
        ImageIcon icon = new ImageIcon("./images/empty.png");
        String[] options ={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        String sen= (String) JOptionPane.showInputDialog(
                null,
                "Please select level: ",
                "Select Level",
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options,
                options[0]
        );
        return sen;
    }
}