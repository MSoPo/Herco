package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.beans.Venta;
import modelo.hibernate.dao.interfaz.PagoDAO;
import modelo.hibernate.dao.interfaz.VentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;

@Controller
@RequestMapping({"/agregarPago.htm"})
public class PagoController
{
  @Autowired
  private PagoDAO pagoDAO;
  @Autowired
  private VentaDAO ventaDAO;
  
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
    catalogoForm.getPago().setFcactivo("1");
    catalogoForm.getPago().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getPago().setFdfechamodifica(new Date());
    catalogoForm.getPago().setUsuario(usuarioLog);
    
    this.pagoDAO.guarda(catalogoForm.getPago());
    if (result.hasErrors()) {
      return "agregarPago";
    }
    return "redirect:/listarPagos.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    
    Long fiidVenta = null;
    if (request.getParameter("fiidventa") != null)
    {
      try
      {
        fiidVenta = new Long(request.getParameter("fiidventa"));
      }
      catch (Exception localException) {}
      catalogoForm.getPago().setVenta((Venta)this.ventaDAO.obtenPorId(fiidVenta));
      catalogoForm.getPago().setFdcantidad(catalogoForm.getPago().getVenta().getFdtotal() - this.pagoDAO.obtenerTotalPagos(fiidVenta));
    }
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