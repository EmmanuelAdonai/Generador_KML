/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author emman
 */
public class ConexionSQLClass {
         public Connection CONN() {
                  String Url;
                  Connection conn = null;
                
                  try {   
                           Class.forName(ConstansConnectClass.driver);
                           Url = "jdbc:jtds:sqlserver://" + ConstansConnectClass.server + ";"
                                    + "databaseName=" + ConstansConnectClass.db + ";"
                                    + "user=" + ConstansConnectClass.user + ";"
                                    + "password=" + ConstansConnectClass.pass + ";";
                            conn = DriverManager.getConnection(Url);
                  } catch (SQLException ex) {
                           ex.getSQLState();
                           System.out.print("Error: "+ex.getMessage());
                  } catch (ClassNotFoundException fe) {
                           System.out.print("Error2: "+fe.getMessage());
                  } catch (Exception e) {
                          System.out.print("Error3: "+e.getMessage());
                  }
                  
                  return conn;
        }
}
