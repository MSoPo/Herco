package control.springmvc.controlador;

import control.springmvc.form.CatalogosForm;
import control.springmvc.form.validacion.UsuarioValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.ClienteDAO;
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
@RequestMapping({"/agregarCliente.htm"})
public class ClienteController
{
  @Autowired
  private ClienteDAO clientedao;
  
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
    catalogoForm.getCliente().setFcactivo("1");
    catalogoForm.getCliente().setFcusuariomodifica(usuarioLog.getFcusuario());
    catalogoForm.getCliente().setFdfechamodifica(new Date());
   
    if (catalogoForm.getOperacion() == 1) {
      catalogoForm.getCliente().setFiidcliente(((Long)this.clientedao.guarda(catalogoForm.getCliente())).longValue());
    } else if (catalogoForm.getOperacion() == 2) {
      this.clientedao.actualiza(catalogoForm.getCliente());
    }
    if (result.hasErrors()) {
      return "agregarCliente";
    }
    if ((catalogoForm.getSalida() != null) && (!catalogoForm.getSalida().trim().equals(""))) {
      return "redirect:" + catalogoForm.getSalida() + "?" + "fiidcliente" + "=" + catalogoForm.getCliente().getFiidcliente();
    }
    return "redirect:listarClientes.htm";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CatalogosForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    CatalogosForm catalogoForm = new CatalogosForm();
    catalogoForm.setOperacion(1);
    catalogoForm.getCliente().setFcmayoreo("0");
    
    Long fiidCliente = null;
    
    Cliente cliente = null;
    if (request.getParameter("fiidcliente") != null)
    {
      try
      {
        fiidCliente = new Long(request.getParameter("fiidcliente"));
      }
      catch (Exception localException) {}
      cliente = (Cliente)this.clientedao.obtenPorId(fiidCliente);
      catalogoForm.setOperacion(2);
      if (cliente != null) {
        catalogoForm.setCliente(cliente);
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