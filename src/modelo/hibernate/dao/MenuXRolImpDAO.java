package modelo.hibernate.dao;

import java.util.List;
import modelo.hibernate.beans.MenuXRol;
import modelo.hibernate.dao.interfaz.MenuXRolDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MenuXRolImpDAO
  extends GenericImpDAO<MenuXRol, Long>
  implements MenuXRolDAO
{
  @Autowired
  public MenuXRolImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public MenuXRolImpDAO() {}
  
  public List<MenuXRol> obtenerMenusPorPadres(long rol, long idpadre)
    throws HibernateException
  {
    List<MenuXRol> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM MenuXRol e where e.rol.fiidrol = " + rol + " and e.menu.fimenupadre = " + idpadre + " order by e.menu.fiposicion");
    return listaContactos;
  }
}