package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatMedidaDAO;
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
@RequestMapping({"/agregarMedida.htm"})
public class MedidaController
{
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
    catalogoForm.getMedida().setFcactivo("1");
    catalogoForm.getMedida().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getMedida().setFdfechamodifica(new Date());
    if (catalogoForm.isChk()) {
      catalogoForm.getMedida().setFcvaloresenteros("1");
    } else {
      catalogoForm.getMedida().setFcvaloresenteros("0");
    }
    if (catalogoForm.getOperacion() == 1) {
      this.medidaDAO.guarda(catalogoForm.getMedida());
    } else if (catalogoForm.getOperacion() == 2) {
      this.medidaDAO.actualiza(catalogoForm.getMedida());
    }
    if (result.hasErrors()) {
      return "agregarMedida";
    }
    return "redirect:listarMedida.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    catalogoForm.setOperacion(1);
    Long fiidMedida = null;
    
    CatMedida medida = null;
    if (request.getParameter("fiidmedida") != null)
    {
      try
      {
        fiidMedida = new Long(request.getParameter("fiidmedida"));
      }
      catch (Exception localException) {}
      medida = (CatMedida)this.medidaDAO.obtenPorId(fiidMedida);
      if (medida.getFcvaloresenteros().equals("1")) {
        catalogoForm.setChk(true);
      }
      catalogoForm.setOperacion(2);
      if (medida != null) {
        catalogoForm.setMedida(medida);
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