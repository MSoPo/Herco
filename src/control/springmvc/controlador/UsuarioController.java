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
import javax.servlet.http.HttpSession;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatRolDAO;
import modelo.hibernate.dao.interfaz.UsuarioDAO;
import modelo.hibernate.dao.interfaz.UsuarioXRolDAO;
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
@RequestMapping({"/agregarUsuario.htm"})
public class UsuarioController
{
  @Autowired
  private UsuarioDAO usuariodao;
  @Autowired
  private UsuarioXRolDAO usuarioporroldao;
  @Autowired
  private CatRolDAO roldao;
  
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
    UsuarioXRol usuarioporrol = null;
    catalogoForm.getUsuario().setFcactivo("1");
    catalogoForm.getUsuario().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getUsuario().setFdfechamodifica(new Date());
    if (catalogoForm.getOperacion() == 1) {
      this.usuariodao.guarda(catalogoForm.getUsuario());
    } else if (catalogoForm.getOperacion() == 2) {
      this.usuariodao.actualiza(catalogoForm.getUsuario());
    }
    for (CatRol rol : catalogoForm.getRoles())
    {
      UsuarioXRol usxrol = new UsuarioXRol();
      usxrol.setUsuario(catalogoForm.getUsuario());
      usxrol.setRol(rol);
      usxrol.setFcusuariomodifica(usuarioLog.getFcusuario());
      usxrol.setFdfechamodifica(new Date());
      usuarioporrol = this.usuarioporroldao.buscarPorUsuarioRol(catalogoForm.getUsuario(), rol);
      if ((rol.getFcactivo() != null) && (rol.getFcactivo().equals("2")))
      {
        usxrol.setFcactivo("1");
        if (usuarioporrol == null)
        {
          this.usuarioporroldao.guarda(usxrol);
        }
        else
        {
          usxrol.setFiidusuarioxrol(usuarioporrol.getFiidusuarioxrol());
          this.usuarioporroldao.actualiza(usxrol);
        }
      }
      else if (((rol.getFcactivo() == null) || (rol.getFcactivo().equals("1"))) && 
        (usuarioporrol != null))
      {
        usxrol.setFcactivo("0");
        usxrol.setFiidusuarioxrol(usuarioporrol.getFiidusuarioxrol());
        this.usuarioporroldao.actualiza(usxrol);
      }
    }
    if (result.hasErrors()) {
      return "agregarUsuario";
    }
    return "redirect:listarUsuario.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    catalogoForm.setOperacion(1);
    List<CatRol> roles = this.roldao.obtenLista();
    String fcusuario = request.getParameter("fcusuario");
    Usuario usuario = null;
    if (fcusuario != null)
    {
      usuario = (Usuario)this.usuariodao.obtenPorId(fcusuario);
      catalogoForm.setOperacion(2);
      if (usuario != null)
      {
        catalogoForm.setUsuario(usuario);
        List<UsuarioXRol> lstusuporrol = this.usuarioporroldao.obtenerPorCompoString("usuario.fcusuario", usuario.getFcusuario());
        for (UsuarioXRol uxr : lstusuporrol) {
          for (CatRol rol : roles) {
            if ((uxr.getRol().getFiidrol() == rol.getFiidrol()) && (uxr.getFcactivo().equals("1")))
            {
              rol.setFcactivo("2");
              break;
            }
          }
        }
      }
    }
    catalogoForm.setRoles(roles);
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