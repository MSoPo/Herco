package control.springmvc.controlador;

import control.springmvc.form.CargaMasivaForm;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ventas.util.ErrorSistema;

@Controller
@RequestMapping({"/cargaMasiva.htm"})
public class CargaMasivaController
{
  @Autowired
  private ProductoDAO productodao;
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ModelAttribute("cargaMasivaForm")
  public CargaMasivaForm getInitialMessage()
  {
    CargaMasivaForm cargamasiva = new CargaMasivaForm();
    return cargamasiva;
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ModelAttribute("message")
  public String guardaFichero(@ModelAttribute CargaMasivaForm fileFormBean, HttpServletRequest request)
  {
    Usuario usuario = ((UsuarioXRol)request.getSession().getAttribute("usuarioxrol")).getUsuario();
    try
    {
      ArrayList<ErrorSistema> errores = leerArchivoExcel(fileFormBean.getFichero().getInputStream(), usuario);
      if (errores == null) {
        return "Seleccione un archivo excel 97-2003";
      }
      request.setAttribute("lstErrores", errores);
      return "Archivo Cargado";
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "Ocurrio un error con el archivo";
  }
  
  private ArrayList<ErrorSistema> leerArchivoExcel(InputStream datos, Usuario usuario)
  {
    ArrayList<ErrorSistema> lstErrores = new ArrayList();
    try
    {
      Workbook archivoExcel = Workbook.getWorkbook(datos);
      System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets());
      for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++)
      {
        Sheet hoja = archivoExcel.getSheet(sheetNo);
        int numFilas = hoja.getRows();
        
        System.out.println("Nombre de la Hoja\t" + archivoExcel.getSheet(sheetNo).getName());
        for (int fila = 0; fila < numFilas;) {
          if (fila != 0)
          {
            Producto p = new Producto();
            try
            {
              String data = hoja.getCell(0, fila).getContents();
              p.setFiidproducto(data.trim());
              data = hoja.getCell(1, fila).getContents();
              p.setFcnomproducto(data.trim());
              data = hoja.getCell(2, fila).getContents();
              p.setFcdescproducto(data.trim());
              data = hoja.getCell(3, fila).getContents();
              p.setFdpreciounitario(Double.parseDouble(data.trim()));
              data = hoja.getCell(4, fila).getContents();
              p.setFdpreciomayoreo(Double.parseDouble(data.trim()));
              data = hoja.getCell(5, fila).getContents();
              p.setFdpreciocompra(Double.parseDouble(data.trim()));
              data = hoja.getCell(6, fila).getContents();
              CatMedida medida = new CatMedida();
              medida.setFiidmedida(Long.parseLong(data));
              p.setMedida(medida);
              data = hoja.getCell(7, fila).getContents();
              p.setFicantidad(Double.parseDouble(data));
              data = hoja.getCell(8, fila).getContents();
              p.setFicantidadmayore(Integer.parseInt(data));
              p.setFcusuariomodifica(usuario.getFcusuario());
              p.setFcactivo("1");
              p.setFdfechamodifica(new Date());
              try
              {
                this.productodao.guarda(p);
              }
              catch (Exception e)
              {
                ErrorSistema er = new ErrorSistema();
                er.setCausa(p.getFiidproducto() + "       Fila: " + (fila + 1));
                er.setError("Este codigo o nombre de producto ya existe, o la medidad es incorrecta");
                lstErrores.add(er);
              }
              ErrorSistema er;
              fila++;
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
              ErrorSistema er = new ErrorSistema();
              er.setCausa(p.getFiidproducto() + "       Fila: " + (fila + 1));
              er.setError("Valor incorreconte en algun campo");
              lstErrores.add(er);
            }
          }
        }
      }
    }
    catch (Exception ioe)
    {
      return null;
    }
    return lstErrores;
  }
}