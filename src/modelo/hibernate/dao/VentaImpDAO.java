package modelo.hibernate.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Venta;
import modelo.hibernate.dao.interfaz.VentaDAO;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VentaImpDAO extends GenericImpDAO<Venta, Long> implements VentaDAO
{
  @Autowired
  public VentaImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public VentaImpDAO() {}
  
  public List<Venta> obtenerVentaCliente(Date fechaInicio, Date fechaFin, int cantidad)
  {
    List ventas = null;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    String fechai = formato.format(fechaInicio);
    String fechaf = formato.format(fechaFin);
    
    String hql = "select v, sum(v.fdtotal) from Venta v where v.fdfechaventa between '" + fechai + " 00:00' and '" + fechaf + " 23:59'" + 
      " group by v.cliente.fiidcliente order by sum(v.fdtotal) desc";
    getHibernateTemplate().setMaxResults(cantidad);
    ventas = obtenerPorConsulta(hql);
    
    List<Venta> lstventa = new ArrayList();
    for (Object object : ventas)
    {
      Object[] o = (Object[])object;
      Venta venta = (Venta)o[0];
      venta.setFdtotal(((Double)o[1]).doubleValue());
      lstventa.add(venta);
    }
    getHibernateTemplate().setMaxResults(0);
    return lstventa;
  }
  
  public List<Venta> obtenerVentasFechas(Date fechaInicio, Date fechaFin)
  {
    List<Venta> ventas = null;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    String fechai = formato.format(fechaInicio);
    String fechaf = formato.format(fechaFin);
    
    String hql = "select v from Venta v where v.fdfechaventa between '" + fechai + " 00:00' and '" + fechaf + " 23:59'" + " order by v.fdfechaventa";
    ventas = obtenerPorConsulta(hql);
    
    return ventas;
  }
  
  public List<DetalleVenta> obtenerDetalleVenta(long venta)
  {
    List<DetalleVenta> ventas = null;
    
    String hql = "select v.detalleVenta from Venta v where v.fiidventa = " + venta;
    ventas = getHibernateTemplate().find(hql);
    
    return ventas;
  }

	public List<Venta> obtenerVentaMensual(int ano) {
		String sql = "select MONTH(v.fdfechaventa), count(v.fiidventa), sum(v.fdtotal) "
				+ "from Venta v where (YEAR(v.fdfechaventa) >= " + ano + ") and (year(v.fdfechaventa) < ("+ ano +" + 1)) "
				+ " and v.fcactivo = 1 "
				+ "group by MONTH(fdfechaventa)";
		
		Query query = getSession().createSQLQuery(sql);
		List listaVentas = query.list();
		
		return listaVentas;
	}
	
	public List<Venta> obtenerVentaDiaria(int mes, int ano) {
		String sql = "select DAY(v.fdfechaventa), v.fdfechaventa ,count(v.fiidventa), sum(v.fdtotal) "
				+ "from Venta v where (MONTH(v.fdfechaventa) >= "+ mes +") and (MONTH(v.fdfechaventa) < ("+ mes +" + 1)) and "
				+ "(YEAR(v.fdfechaventa) >= " + ano + ") and (year(v.fdfechaventa) < ("+ ano +" + 1))"
				+ " and v.fcactivo = 1 "
				+ "group by DAY(fdfechaventa)";
		
		Query query = getSession().createSQLQuery(sql);
		List listaVentas = query.list();
		
		return listaVentas;
	}
}