package control.springmvc.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionController
{
  @RequestMapping({"/cerrarSesion.htm"})
  public ModelAndView listarPagos(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Map<String, Object> myModel = new HashMap();
    request.getSession().invalidate();
    
    return new ModelAndView("redirect:login.htm", "model", myModel);
  }
}