package modelo.hibernate.dao;

import modelo.hibernate.beans.CatMenu;
import modelo.hibernate.dao.interfaz.CatMenuDAO;
import org.hibernate.SessionFactory;

public class CatMenuImpDAO
  extends GenericImpDAO<CatMenu, Long>
  implements CatMenuDAO
{
  public CatMenuImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
}