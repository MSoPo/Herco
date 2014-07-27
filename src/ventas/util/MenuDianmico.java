package ventas.util;

import java.io.Serializable;
import java.util.List;
import modelo.hibernate.beans.CatMenu;

@SuppressWarnings("serial")
public class MenuDianmico
  implements Serializable
{
  private CatMenu menu;
  private List<MenuDianmico> lstMenu;
  
  public CatMenu getMenu()
  {
    return this.menu;
  }
  
  public void setMenu(CatMenu menu)
  {
    this.menu = menu;
  }
  
  public List<MenuDianmico> getLstMenu()
  {
    return this.lstMenu;
  }
  
  public void setLstMenu(List<MenuDianmico> lstMenu)
  {
    this.lstMenu = lstMenu;
  }
}
