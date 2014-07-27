package modelo.hibernate.dao;

import modelo.hibernate.beans.HistorialProducto;
import modelo.hibernate.dao.interfaz.HistorialProductoDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistorialProductoImpDAO
  extends GenericImpDAO<HistorialProducto, Long>
  implements HistorialProductoDAO
{
  @Autowired
  public HistorialProductoImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public HistorialProductoImpDAO() {}
}