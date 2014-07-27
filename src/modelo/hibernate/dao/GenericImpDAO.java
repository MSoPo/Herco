package modelo.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import modelo.hibernate.dao.interfaz.GenericDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import ventas.util.BussinessException;

public class GenericImpDAO<T, ID extends Serializable>
  extends HibernateDaoSupport
  implements GenericDAO<T, ID>
{
  public GenericImpDAO(SessionFactory sesionFactory)
  {
    super.setSessionFactory(sesionFactory);
  }
  
  public GenericImpDAO() {}
  
  @Transactional
  public ID guarda(T vo)
  {
    ID id = null;
    id = (ID) getHibernateTemplate().save(vo);
    return id;
  }
  
  @Transactional
  public void actualiza(T vo)
    throws BussinessException
  {
    getHibernateTemplate().update(vo);
  }
  
  @Transactional
  public void elimina(T vo)
    throws BussinessException
  {
    getHibernateTemplate().delete(vo);
  }
  
  @Transactional
  public T obtenPorId(ID id)
    throws HibernateException
  {
    T vo = null;
    vo = getHibernateTemplate().get(getEntityClass(), id);
    return vo;
  }
  
  @Transactional
  public List<T> obtenerPorCompoString(String campo, String valor)
    throws HibernateException
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM " + getEntityClass().getName() + " e where e." + campo + " = '" + valor + "'");
    return listaContactos;
  }
  
  @Transactional
  public List<T> obtenerPorCompoString(String campo, String valor, String fcactivo)
    throws HibernateException
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM " + getEntityClass().getName() + " e where e." + campo + " = '" + valor + "' and " + "e.fcactivo = " + fcactivo);
    return listaContactos;
  }
  
  @Transactional
  public List<T> obtenerPorCompoString(String campo, String valor, String fcactivo, String camporOrden, boolean ascendente)
    throws HibernateException
  {
    List<T> listaContactos = null;
    String hql = "SELECT e FROM " + getEntityClass().getName() + " e where e." + campo + " = '" + valor + "' and " + "e.fcactivo = " + fcactivo + " order by e." + camporOrden;
    hql = hql + (ascendente ? " asc" : " desc");
    listaContactos = getHibernateTemplate().find(hql);
    return listaContactos;
  }
  
  @Transactional
  public List<T> obtenerPorCompoLong(String campo, long valor)
    throws HibernateException
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM " + getEntityClass().getName() + " e where e." + campo + " = " + valor);
    return listaContactos;
  }
  
  @Transactional
  public List<T> obtenerPorConsulta(String hql)
    throws HibernateException
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find(hql);
    return listaContactos;
  }
  
  @Transactional
  public double obtenerSuma(String hql)
    throws HibernateException
  {
    double suma = 0.0D;
    suma = new Double(getHibernateTemplate().find(hql).get(0).toString()).doubleValue();
    return suma;
  }
  
  @Transactional
  public List<T> obtenLista()
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM " + getEntityClass().getName() + " e");
    return listaContactos;
  }
  
  @Transactional
  public List<T> obtenLista(String campoOrdern, boolean ascendente)
  {
    List<T> listaContactos = null;
    String hql = "SELECT e FROM " + getEntityClass().getName() + " e order by e." + campoOrdern;
    hql = hql + (ascendente ? " asc" : " desc");
    listaContactos = getHibernateTemplate().find(hql);
    return listaContactos;
  }
  
  private Class<T> getEntityClass()
  {
    return (Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
  
  @Transactional
  public List<T> obtenListaLike(String campo, String valor)
  {
    List<T> listaContactos = null;
    listaContactos = getHibernateTemplate().find("SELECT e FROM " + getEntityClass().getName() + " e where e." + campo + " like '%" + valor + "%'");
    return listaContactos;
  }
}