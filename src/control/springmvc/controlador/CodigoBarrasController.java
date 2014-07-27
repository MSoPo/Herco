package control.springmvc.controlador;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.GenerarCodigoDeBarras;

@Controller
public class CodigoBarrasController
{
  @RequestMapping({"/codigoDeBarra.htm"})
  public ModelAndView codigoDeBarras(HttpServletResponse response, HttpServletRequest request)
  {
    return new ModelAndView("CodigodeBarras");
  }
  
  @RequestMapping({"/generarCodigoDeBarra.htm"})
  public void generarCodigoDeBarra(HttpServletResponse response, HttpServletRequest request)
  {
    response.setContentType("image/jpeg");
    String codigo = request.getParameter("codigo");
    if (codigo == null) {
      codigo = generarCodigo(10);
    }
    OutputStream output = null;
    try
    {
      output = response.getOutputStream();
      byte[] buffer = GenerarCodigoDeBarras.crearImagen(codigo);
      response.setContentLength(buffer.length);
      output.write(buffer);
    }
    catch (Exception e)
    {
      System.out.print("Error");
      try
      {
        output.close();
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
      }
    }
    finally
    {
      try
      {
        output.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public String generarCodigo(int longitud)
  {
    String cadenaAleatoria = "";
    long milis = new GregorianCalendar().getTimeInMillis();
    Random r = new Random(milis);
    int i = 0;
    while (i < longitud)
    {
      char c = (char)r.nextInt(255);
      if ((c >= '0') && (c <= '9'))
      {
        cadenaAleatoria = cadenaAleatoria + c;
        i++;
      }
    }
    return cadenaAleatoria;
  }
}