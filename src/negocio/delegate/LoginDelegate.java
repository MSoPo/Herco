package negocio.delegate;

import java.util.ArrayList;
import java.util.List;
import modelo.hibernate.beans.CatMenu;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.MenuXRol;

public class LoginDelegate
{
  public List<CatMenu> crearMenu(List<MenuXRol> lstMenuxrol)
  {
    List<CatMenu> lstMenu = new ArrayList();
    for (MenuXRol mxr : lstMenuxrol) {
      lstMenu.add(mxr.getMenu());
    }
    return lstMenu;
  }
  
  public String determinarSalida(CatRol rol)
  {
    if (rol.getFiidrol() == 1L) {
      return "redirect:/venta.htm";
    }
    if (rol.getFiidrol() == 2L) {
      return "redirect:/venta.htm";
    }
    if (rol.getFiidrol() == 3L) {
      return "redirect:/compra.htm";
    }
    if (rol.getFiidrol() == 4L) {
      return "redirect:/stock.htm";
    }
    return "redirect:/venta.htm";
  }
}