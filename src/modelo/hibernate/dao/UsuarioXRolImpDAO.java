package modelo.hibernate.dao;

import java.util.List;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.UsuarioXRolDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioXRolImpDAO
  extends GenericImpDAO<UsuarioXRol, Long>
  implements UsuarioXRolDAO
{
  @Autowired
  public UsuarioXRolImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public UsuarioXRolImpDAO() {}
  
  public UsuarioXRol buscarPorUsuarioRol(Usuario usuario, CatRol rol)
  {
    List<UsuarioXRol> lstusuporrol = obtenerPorConsulta("from UsuarioXRol us where us.usuario.fcusuario = '" + usuario.getFcusuario() + "' and " + "us.rol.fiidrol = " + rol.getFiidrol());
    if (lstusuporrol.size() > 0) {
      return (UsuarioXRol)lstusuporrol.get(0);
    }
    return null;
  }
  
  public List<UsuarioXRol> rolesActivos(Usuario usuario)
  {
    List<UsuarioXRol> lstUsxROl = obtenerPorConsulta("from UsuarioXRol us where us.usuario.fcusuario = '" + usuario.getFcusuario() + "' and fcactivo = 1");
    return lstUsxROl;
  }
}