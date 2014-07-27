package modelo.hibernate.dao;

import modelo.hibernate.beans.Usuario;
import modelo.hibernate.dao.interfaz.UsuarioDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioImpDAO
  extends GenericImpDAO<Usuario, String>
  implements UsuarioDAO
{
  @Autowired
  public UsuarioImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public UsuarioImpDAO() {}
}