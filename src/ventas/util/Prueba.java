package ventas.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Prueba
{
  public void printMe()
  {
    try
    {
      Properties propiedades = new Properties();
      

      propiedades
        .load(new FileInputStream(
        "C:\\sistemaherco\\dump.properties"));
      

      String ubicacionDump = propiedades.getProperty("ubicacionDump");
      String rutaRespaldo = propiedades.getProperty("rutaRespaldo");
      
      System.out.println("Creando Respaldo");
      String usuarioBD = "root";
      String passBD = "admin";
      String NombreBD = "administracionventas";
      SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
      String command = ubicacionDump + " --opt -c -u" + usuarioBD + " -p" + passBD + " " + NombreBD + " -r " + rutaRespaldo + formato.format(new Date()) + "dump.sql";
      

      Process p = Runtime.getRuntime().exec(command);
      InputStreamReader irs = new InputStreamReader(p.getInputStream());
      BufferedReader br = new BufferedReader(irs);
      String line;
      while ((line = br.readLine()) != null)
      {
        System.out.println(line + "\n");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}