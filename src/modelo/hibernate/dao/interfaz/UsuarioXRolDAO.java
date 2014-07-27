package modelo.hibernate.dao.interfaz;

import java.util.List;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;

public abstract interface UsuarioXRolDAO
  extends GenericDAO<UsuarioXRol, Long>
{
  public abstract UsuarioXRol buscarPorUsuarioRol(Usuario paramUsuario, CatRol paramCatRol);
  
  public abstract List<UsuarioXRol> rolesActivos(Usuario paramUsuario);
}