package modelo.hibernate.dao;

import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.dao.interfaz.CatMedidaDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatMedidaImpDAO
  extends GenericImpDAO<CatMedida, Long>
  implements CatMedidaDAO
{
  @Autowired
  public CatMedidaImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public CatMedidaImpDAO() {}
}