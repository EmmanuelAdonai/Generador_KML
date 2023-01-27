/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import javax.swing.JOptionPane;

/**
 *
 * @author emman
 */
public class MessageDialogClass {
          // --- MessageDialog de Error --- //
         public void ErrorMessage(String msj) {
                JOptionPane.showMessageDialog(null, msj, "Error!", JOptionPane.ERROR_MESSAGE);
         }
    
         // --- MessageDialog de Susscess --- //
         public void SussecessMessage(String msj) {
                  JOptionPane.showMessageDialog(null, msj, "Exito!", JOptionPane.INFORMATION_MESSAGE);
         }
}
