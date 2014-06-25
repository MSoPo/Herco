package modelo.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.hibernate.beans.Pago;
import modelo.hibernate.dao.interfaz.PagoDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PagoImpDAO
  extends GenericImpDAO<Pago, Long>
  implements PagoDAO
{
  @Autowired
  public PagoImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public PagoImpDAO() {}
  
  public List<Pago> obtenerListaFaltantes()
  {
    String hql = "SELECT p FROM Pago p inner join p.venta v where (select sum(fdcantidad) from Pago p where p.venta = v) < v.fdtotal   group by p.venta.fiidventa order by p.venta.cliente.fcnombre";
    

    List<Pago> lstPago = obtenerPorConsulta(hql);
    
    return lstPago;
  }
  
  public List<Pago> obtenerListaFaltantesGrafica(int cantidad)
  {
    String hql = "SELECT p, v.fdtotal - sum(p.fdcantidad) FROM Pago p inner join p.venta v where (select sum(fdcantidad) from Pago p where p.venta = v) < v.fdtotal   group by p.venta.fiidventa order by sum(p.fdcantidad) desc";
    

    getHibernateTemplate().setMaxResults(cantidad);
    List lst = obtenerPorConsulta(hql);
    List<Pago> lstPago = new ArrayList();
    for (Object o : lst)
    {
      Object[] obj = (Object[])o;
      Pago p = new Pago();
      p.setVenta(((Pago)obj[0]).getVenta());
      p.setFdcantidad(((Double)obj[1]).doubleValue());
      lstPago.add(p);
    }
    getHibernateTemplate().setMaxResults(0);
    return lstPago;
  }
  
  public List<Pago> obtenerListaFaltantesLike(String campo, String valor)
  {
    String hql = "SELECT p FROM Pago p inner join p.venta v where (select sum(fdcantidad) from Pago p where p.venta = v) < v.fdtotal  and v." + campo + " like '%" + valor + 
      "%' group by p.venta.fiidventa";
    
    List<Pago> lstPago = obtenerPorConsulta(hql);
    
    return lstPago;
  }
  
  public double obtenerTotalPagos(Long idVenta)
  {
    double total = 0.0D;
    
    total = obtenerSuma("SELECT sum(fdcantidad) FROM Pago p where p.venta.fiidventa = " + idVenta);
    
    return total;
  }
}