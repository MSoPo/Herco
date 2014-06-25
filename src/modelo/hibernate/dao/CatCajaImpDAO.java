package modelo.hibernate.dao;

import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.dao.interfaz.CatCajaDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatCajaImpDAO
  extends GenericImpDAO<CatCaja, Long>
  implements CatCajaDAO
{
  @Autowired
  public CatCajaImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public CatCajaImpDAO() {}
}