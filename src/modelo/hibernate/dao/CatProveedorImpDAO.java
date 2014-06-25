package modelo.hibernate.dao;

import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.dao.interfaz.CatProveedorDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatProveedorImpDAO
  extends GenericImpDAO<CatProveedor, Long>
  implements CatProveedorDAO
{
  @Autowired
  public CatProveedorImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public CatProveedorImpDAO() {}
}