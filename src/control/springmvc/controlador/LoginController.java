package control.springmvc.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.MenuXRol;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatCajaDAO;
import modelo.hibernate.dao.interfaz.MenuXRolDAO;
import modelo.hibernate.dao.interfaz.UsuarioDAO;
import modelo.hibernate.dao.interfaz.UsuarioXRolDAO;
import negocio.delegate.LoginDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;
import ventas.util.MenuDianmico;

@Controller
@RequestMapping({"/login.htm"})
public class LoginController
{
  final Logger logger = Logger.getLogger(LoginController.class);
  @Autowired
  private UsuarioDAO dao;
  @Autowired
  private CatCajaDAO cajadao;
  @Autowired
  private UsuarioXRolDAO usuarioxroldao;
  @Autowired
  private MenuXRolDAO menuxroldao;
  @Autowired
  private MessageSource messageSource;
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String onSubmit(@ModelAttribute UsuarioXRol usuarioxrol, BindingResult result, HttpServletRequest request)
  {
    Usuario ustmp = null;
    HttpSession session = request.getSession(true);
    LoginDelegate delegate = new LoginDelegate();
    try
    {
      ustmp = (Usuario)this.dao.obtenPorId(usuarioxrol.getUsuario().getFcusuario());
      if ((ustmp != null) && (usuarioxrol.getRol() != null) && (usuarioxrol.getRol().getFiidrol() > 0L))
      {
        session.setAttribute("usuarioxrol", usuarioxrol);
        List<CatCaja> cajaSelect = this.cajadao.obtenerPorCompoString("fcubicacioncaja", request.getRemoteAddr());
        this.logger.info(request.getRemoteAddr());
        this.logger.info(request.getRemoteHost());
        this.logger.info((cajaSelect.size() > 0 ? (CatCaja)cajaSelect.get(0) : (CatCaja)this.cajadao.obtenLista().get(0)).getFcdesccaja());
        session.setAttribute("caja", cajaSelect.size() > 0 ? (CatCaja)cajaSelect.get(0) : (CatCaja)this.cajadao.obtenLista().get(0));
        List<MenuDianmico> md = menudinamico(usuarioxrol.getRol().getFiidrol(), 0L);
        session.setAttribute("menu", md);
        return delegate.determinarSalida(usuarioxrol.getRol());
      }
      if (ustmp == null)
      {
        result.addError(new FieldError("error", "usuario.fcusuario", this.messageSource.getMessage("error.fcusuario.incorrecto", null, null)));
        usuarioxrol.getUsuario().setFcpassword("");
      }
      else if (ustmp.getFcactivo().equals("0"))
      {
        result.addError(new FieldError("error", "usuario.fcusuario", "Usuario Bloqueado"));
        usuarioxrol.getUsuario().setFcpassword("");
      }
      else if (!ustmp.getFcpassword().equals(usuarioxrol.getUsuario().getFcpassword()))
      {
        result.addError(new FieldError("error", "usuario.fcpassword", this.messageSource.getMessage("error.fcpassword.incorrecto", null, null)));
      }
    }
    catch (BussinessException be)
    {
      be.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (!result.hasErrors())
    {
      List<UsuarioXRol> lstUsuxRol = this.usuarioxroldao.rolesActivos(ustmp);
      if (lstUsuxRol.size() > 1)
      {
        ArrayList<CatRol> lstRol = new ArrayList();
        for (UsuarioXRol usuxrol : lstUsuxRol) {
          lstRol.add(usuxrol.getRol());
        }
        request.setAttribute("roles", lstRol);
        return "login";
      }
      if (lstUsuxRol.size() == 1)
      {
        usuarioxrol = (UsuarioXRol)lstUsuxRol.get(0);
        session.setAttribute("usuarioxrol", usuarioxrol);
        List<MenuDianmico> md = menudinamico(usuarioxrol.getRol().getFiidrol(), 0L);
        session.setAttribute("menu", md);
        List<CatCaja> cajaSelect = this.cajadao.obtenerPorCompoString("fcubicacioncaja", request.getRemoteAddr());
        session.setAttribute("caja", cajaSelect.size() > 0 ? (CatCaja)cajaSelect.get(0) : (CatCaja)this.cajadao.obtenLista().get(0));
        return delegate.determinarSalida(((UsuarioXRol)lstUsuxRol.get(0)).getRol());
      }
      result.addError(new FieldError("error", "usuario.fcpassword", this.messageSource.getMessage("error.fiidrol.noexiste", null, null)));
      return "login";
    }
    return "login";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected UsuarioXRol formBackingObject(HttpServletRequest request)
    throws ServletException
  {
    return new UsuarioXRol();
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
  
  public List<MenuDianmico> menudinamico(long rol, long idpadre)
  {
    List<MenuXRol> lstmxr = this.menuxroldao.obtenerMenusPorPadres(rol, idpadre);
    List<MenuDianmico> lstMenu = new ArrayList();
    for (MenuXRol mxr : lstmxr)
    {
      MenuDianmico md = new MenuDianmico();
      md.setMenu(mxr.getMenu());
      md.setLstMenu(menudinamico(rol, md.getMenu().getFiidmenu()));
      lstMenu.add(md);
    }
    return lstMenu;
  }
}