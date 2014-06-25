package modelo.hibernate.dao.interfaz;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import ventas.util.BussinessException;

public abstract interface GenericDAO<T, ID extends Serializable>
{
  public abstract ID guarda(T paramT);
  
  public abstract void actualiza(T paramT)
    throws BussinessException;
  
  public abstract void elimina(T paramT)
    throws BussinessException;
  
  public abstract T obtenPorId(ID paramID)
    throws BussinessException;
  
  public abstract List<T> obtenLista();
  
  public abstract List<T> obtenLista(String paramString, boolean paramBoolean);
  
  public abstract List<T> obtenerPorCompoString(String paramString1, String paramString2)
    throws HibernateException;
  
  public abstract List<T> obtenerPorCompoString(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws HibernateException;
  
  public abstract List<T> obtenerPorCompoLong(String paramString, long paramLong)
    throws HibernateException;
  
  public abstract List<T> obtenListaLike(String paramString1, String paramString2);
  
  public abstract List<T> obtenerPorCompoString(String paramString1, String paramString2, String paramString3)
    throws HibernateException;
}
