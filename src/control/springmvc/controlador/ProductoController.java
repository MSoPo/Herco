package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatMedidaDAO;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;

@Controller
@RequestMapping({"/agregarProducto.htm"})
public class ProductoController
{
  @Autowired
  private ProductoDAO productoDAO;
  @Autowired
  private CatMedidaDAO medidaDAO;
  
  @InitBinder
  protected void initBinder(WebDataBinder binder)
  {
    binder.setValidator(new UsuarioValidator());
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String onSubmit(@Validated CatalogosForm catalogoForm, BindingResult result, HttpServletResponse response, HttpServletRequest request)
    throws BussinessException
  {
    Usuario usuarioLog = ((UsuarioXRol)request.getSession().getAttribute("usuarioxrol")).getUsuario();
    catalogoForm.getProducto().setFcactivo("1");
    catalogoForm.getProducto().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getProducto().setFdfechamodifica(new Date());
    if (catalogoForm.getOperacion() == 1)
    {
      if (this.productoDAO.obtenPorId(catalogoForm.getProducto().getFiidproducto()) == null)
      {
        if (this.productoDAO.obtenerPorCompoString("fcnomproducto", catalogoForm.getProducto().getFcnomproducto()).size() == 0)
        {
          CatMedida medida = (CatMedida)this.medidaDAO.obtenPorId(Long.valueOf(catalogoForm.getProducto().getMedida().getFiidmedida()));
          if (medida.getFcvaloresenteros().equals("1"))
          {
            if (catalogoForm.getProducto().getFicantidad() % 1.0D != 0.0D) {
              result.addError(new FieldError("error", "producto.medida.fiidmedida", "Esta medida solo acepta enteros"));
            } else {
              this.productoDAO.guarda(catalogoForm.getProducto());
            }
          }
          else {
            this.productoDAO.guarda(catalogoForm.getProducto());
          }
        }
        else
        {
          result.addError(new FieldError("error", "producto.fcnomproducto", "Ya existe un producto con este nombre"));
        }
      }
      else {
        result.addError(new FieldError("error", "producto.fiidproducto", "Ya existe un producto con este codigo"));
      }
    }
    else if (catalogoForm.getOperacion() == 2)
    {
      List<Producto> lstp = this.productoDAO.obtenerPorCompoString("fcnomproducto", catalogoForm.getProducto().getFcnomproducto());
      if ((lstp.size() == 0) || (((Producto)lstp.get(0)).getFiidproducto().equals(catalogoForm.getProducto().getFiidproducto())))
      {
        CatMedida medida = (CatMedida)this.medidaDAO.obtenPorId(Long.valueOf(catalogoForm.getProducto().getMedida().getFiidmedida()));
        catalogoForm.getProducto().setMedida(medida);
        if (medida.getFcvaloresenteros().equals("1"))
        {
          if (catalogoForm.getProducto().getFicantidad() % 1.0D != 0.0D) {
            result.addError(new FieldError("error", "producto.medida.fiidmedida", "Esta medida solo acepta enteros"));
          } else {
            this.productoDAO.actualiza(catalogoForm.getProducto());
          }
        }
        else {
          this.productoDAO.actualiza(catalogoForm.getProducto());
        }
      }
      else
      {
        result.addError(new FieldError("error", "producto.fcnomproducto", "Ya existe un producto con este nombre"));
      }
    }
    request.setAttribute("lstMedida", this.medidaDAO.obtenerPorCompoString("fcactivo", "1"));
    if (result.hasErrors()) {
      return "agregarProducto";
    }
    if ((catalogoForm.getSalida() != null) && (!catalogoForm.getSalida().trim().equals(""))) {
      return "redirect:" + catalogoForm.getSalida() + "?fiidproducto=" + catalogoForm.getProducto().getFiidproducto();
    }
    return "redirect:/listarProductos.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    
    String fiidProducto = null;
    
    Producto producto = null;
    catalogoForm.setOperacion(1);
    if (request.getParameter("fiidproducto") != null)
    {
      try
      {
        fiidProducto = request.getParameter("fiidproducto");
      }
      catch (Exception localException) {}
      producto = (Producto)this.productoDAO.obtenPorId(fiidProducto);
      if (producto != null)
      {
        catalogoForm.setProducto(producto);
        catalogoForm.setOperacion(2);
      }
    }
    if (request.getParameter("salida") != null) {
      catalogoForm.setSalida(request.getParameter("salida"));
    }
    request.setAttribute("lstMedida", this.medidaDAO.obtenerPorCompoString("fcactivo", "1"));
    
    return catalogoForm;
  }
  
  @ExceptionHandler({Exception.class})
  public ModelAndView Exception(Exception e, HttpServletRequest request)
  {
    Map<String, Object> myModel = new HashMap();
    myModel.put("error", e.getMessage());
    myModel.put("causa", e.getCause());
    myModel.put("clase", getClass());
    e.printStackTrace();
    return new ModelAndView("error", "model", myModel);
  }
}