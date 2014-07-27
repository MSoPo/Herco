package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatProveedorDAO;
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
@RequestMapping({"/agregarProveedor.htm"})
public class ProveedorController
{
  @Autowired
  private CatProveedorDAO proveedorDAO;
  
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
    catalogoForm.getProveedor().setFcactivo("1");
    catalogoForm.getProveedor().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getProveedor().setFdfechamodifica(new Date());
    if (catalogoForm.getOperacion() == 1) {
      catalogoForm.getProveedor().setFiidproveedor(((Long)this.proveedorDAO.guarda(catalogoForm.getProveedor())).longValue());
    } else if (catalogoForm.getOperacion() == 2) {
      this.proveedorDAO.actualiza(catalogoForm.getProveedor());
    }
    if (result.hasErrors()) {
      return "agregarProveedor";
    }
    if ((catalogoForm.getSalida() != null) && (!catalogoForm.getSalida().trim().equals(""))) {
      return "redirect:" + catalogoForm.getSalida() + "?fiidproveedor=" + catalogoForm.getProveedor().getFiidproveedor();
    }
    return "redirect:listarProveedor.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    catalogoForm.setOperacion(1);
    Long fiidProveedor = null;
    
    CatProveedor proveedor = null;
    if (request.getParameter("fiidproveedor") != null)
    {
      try
      {
        fiidProveedor = new Long(request.getParameter("fiidproveedor"));
      }
      catch (Exception localException) {}
      proveedor = (CatProveedor)this.proveedorDAO.obtenPorId(fiidProveedor);
      catalogoForm.setOperacion(2);
      if (proveedor != null) {
        catalogoForm.setProveedor(proveedor);
      }
    }
    if (request.getParameter("salida") != null) {
      catalogoForm.setSalida(request.getParameter("salida"));
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