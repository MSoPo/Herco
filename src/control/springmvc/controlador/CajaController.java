package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatCajaDAO;
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
@RequestMapping({"/agregarCaja.htm"})
public class CajaController
{
  @Autowired
  private CatCajaDAO cajaDAO;
  
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
    catalogoForm.getCaja().setFcactivo("1");
    catalogoForm.getCaja().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getCaja().setFdfechamodifica(new Date());
    if (catalogoForm.getOperacion() == 1) {
      this.cajaDAO.guarda(catalogoForm.getCaja());
    } else if (catalogoForm.getOperacion() == 2) {
      this.cajaDAO.actualiza(catalogoForm.getCaja());
    }
    if (result.hasErrors()) {
      return "agregarCaja";
    }
    return "redirect:listarCaja.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    catalogoForm.setOperacion(1);
    Long fiidCaja = null;
    CatCaja caja = null;
    if (request.getParameter("fiidcaja") != null)
    {
      fiidCaja = new Long(request.getParameter("fiidcaja"));
      caja = (CatCaja)this.cajaDAO.obtenPorId(fiidCaja);
      catalogoForm.setOperacion(2);
      if (caja != null) {
        catalogoForm.setCaja(caja);
      }
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