/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author emman
 */
public class GenerateKml {
    // --- Funcion Para General el KML --- //
        public static boolean GenKml(String fte, boolean market, boolean line, boolean polygon, String rute) throws IOException {
            boolean resultado = false;
            ConexionSQLClass connection = new ConexionSQLClass();
            
            Element root = DocumentHelper.createElement("kml"); 
	Document document = DocumentHelper.createDocument(root);
                
                  root.addAttribute("xmlns", "http://www.opengis.net/kml/2.2")
                          .addAttribute("xmlns:gx", "http://www.google.com/kml/ext/2.2")
                          .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
                          .addAttribute("xsi:schemaLocation", 
                                  "http://www.opengis.net/kml/2.2 http://schemas.opengis.net/kml/2.2.0/ogckml22.xsd http://www.google.com/kml/ext/2.2 http://code.google.com/apis/kml/schema/kml22gx.xsd");
                
                  Element documentElement = root.addElement("Document");
                  
                  // --- Funcion para crear los marcadores --- //
                  if(market) {
                           try {
                                    Connection conn = connection.CONN();
                                    if(conn != null) {
                                             String query = "select geo.id, geo.geometria, geo.tipoGeometria, geo.color " +
                                                       "from tblGeoreferencia as geo " +
                                                      "left join tblMunicipios mun on geo.idMunicipio = mun.id " +
                                                      "where geo.tipoGeometria = 'point' and mun.nombre =  '"+fte+"'";
                                             System.out.println(query);
                                             Statement stmt = conn.createStatement();
                                             ResultSet res = stmt.executeQuery(query); 
                                             
                                             if(res != null) {
                                                      Element folderPoints = documentElement.addElement ("Folder"); // Agregar un directorio
                                                      folderPoints.addAttribute("id", "Points")
                                                               .addElement ("name"). addText ("Marcadores"); // Nombre
                                                      
                                                      while(res.next()) {
                                                               String first = res.getString("geometria").replace("[", "");
                                                               String coordinates = first.replace("]", "");
                                                               String[] parts = coordinates.split(",");
                                                               String latitude = parts[0];
                                                               String longitude = parts[1];
                                                               
                                                               Element pointOption = folderPoints.addElement("Placemark");
                                                               pointOption.addAttribute("id", res.getString("id"));
                                                               
                                                               Element point = pointOption.addElement("Point");
                                                               point.addElement("coordinates").addText(longitude + ", " + latitude + " , 0");
                                                      }
                                                      resultado = true;
                                             } else {
                                                      resultado = false;
                                             }
                                    } conn.close();
                           } catch(SQLException e) {
                                    e.getSQLState();
                                    resultado = false;
                           }
                  }
                  
                  // --- Funcion para crear las lineas --- //
                  if(line) {
                           try {
                                    Connection conn = connection.CONN();
                                    if(conn != null) {
                                             String query = "select geo.id, geo.geometria, geo.tipoGeometria, geo.color " +
                                                       "from tblGeoreferencia as geo " +
                                                      "left join tblMunicipios mun on geo.idMunicipio = mun.id " +
                                                      "where geo.tipoGeometria = 'linestring' and mun.nombre =  '"+fte+"'";
                                             Statement stmt = conn.createStatement();
                                             ResultSet res = stmt.executeQuery(query); 
                                             
                                             if(res != null) {
                                                      Element folderLine = documentElement.addElement ("Folder"); // Agregar un directorio
                                                      folderLine.addAttribute("id", "Lines")
                                                               .addElement ("name"). addText ("Lineas"); // Nombre
                                                      
                                                      while(res.next()) {
                                                               String rem = res.getString("geometria").replace("[[", "[");
                                                               String rem2 = rem.replace("]]", "]");
                                                               String ren3 = rem2.replace("],[", "]/[");
                                                               String[] partsCoord = ren3.split("/");
                                                               String lineCoor = "";
                                                               for (int i = 0; i < partsCoord.length; i++) {
                                                                        String re = partsCoord[i].replace("[", "");
                                                                        String re2 = re.replace("]", "");
                                                                        String[] parts = re2.split(",");
                                                                        String latitude = parts[0];
                                                                        String longitude = parts[1];
                                                                        lineCoor = lineCoor + " \n" + longitude + ", " + latitude;
                                                                        System.out.println("Coordenadas: " + lineCoor);
                                                               }
                                                               
                                                               Element lineOptions = folderLine.addElement("Placemark");
                                                               lineOptions.addAttribute("id", res.getString("id"));
                                                               
                                                               Element lineP = lineOptions.addElement("LineString");
                                                               lineP.addElement("coordinates").addText(lineCoor);
                                                      }
                                                      resultado = true;
                                             }
                                    }
                           } catch(SQLException e) {
                                    e.getSQLState();
                                    resultado = false;
                           }
                  }
                  
                  // --- Funcion para crear los Poligonos --- //
                  if(polygon) {
                           // Code is here
                  }
                  
                  // -- Se crea el KML en local -- //
                  OutputFormat format = OutputFormat.createPrettyPrint();  
	format.setEncoding("utf-8");
	XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(rute),format);  
                  xmlWriter.write(document); 
	xmlWriter.close(); 
                  
                  // -- Se comienza a escribir el KML -- //
                  String[] strs = new String[1];
	strs[0] = rute;
	//writeKml (strs, "C:/Users/emman/Documents/" + nameFile); // Zip
        
	return resultado;
        }
}
