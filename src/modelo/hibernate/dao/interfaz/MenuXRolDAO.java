package modelo.hibernate.dao.interfaz;

import java.util.List;
import modelo.hibernate.beans.MenuXRol;
import org.hibernate.HibernateException;

public abstract interface MenuXRolDAO
  extends GenericDAO<MenuXRol, Long>
{
  public abstract List<MenuXRol> obtenerMenusPorPadres(long paramLong1, long paramLong2)
    throws HibernateException;
}
