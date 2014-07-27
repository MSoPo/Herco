package modelo.hibernate.dao;

import modelo.hibernate.beans.CatRol;
import modelo.hibernate.dao.interfaz.CatRolDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatRolImpDAO
  extends GenericImpDAO<CatRol, Long>
  implements CatRolDAO
{
  @Autowired
  public CatRolImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public CatRolImpDAO() {}
}