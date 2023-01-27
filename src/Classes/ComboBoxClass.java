/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import javax.swing.JComboBox;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author emman
 */
public class ComboBoxClass {
        // --- Se llena el Combo desde una tabla de SQL --- //
        public void ComboFuente(JComboBox comboFuente) {
                comboFuente.removeAllItems();
                ConexionSQLClass connection = new ConexionSQLClass();
                Connection conn = connection.CONN();
                
                try {
                    if(conn != null) {
                          String query = "select tblMunicipios.nombre " +
                                    "from tblGeoreferencia " +
                                    "left join tblMunicipios on tblGeoreferencia.idMunicipio = tblMunicipios.id " +
                                    "group by tblMunicipios.id, tblMunicipios.nombre " +
                                    "order by tblMunicipios.id asc";
                          Statement stmt = conn.createStatement();
                          ResultSet res = stmt.executeQuery(query);

                          if (res != null) {
                                   while (res.next()) {
                                            comboFuente.addItem(res.getString("nombre"));
                                   }
                          }
                          conn.close();
                    }
                } catch (SQLException e) {
                          e.getSQLState();
                }
        }
}
