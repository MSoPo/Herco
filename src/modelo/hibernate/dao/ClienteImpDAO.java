package modelo.hibernate.dao;

import modelo.hibernate.beans.Cliente;
import modelo.hibernate.dao.interfaz.ClienteDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteImpDAO
  extends GenericImpDAO<Cliente, Long>
  implements ClienteDAO
{
  @Autowired
  public ClienteImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public ClienteImpDAO() {}
}