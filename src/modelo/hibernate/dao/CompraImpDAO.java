package modelo.hibernate.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.DetalleCompra;
import modelo.hibernate.dao.interfaz.CompraDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompraImpDAO
  extends GenericImpDAO<Compra, Long>
  implements CompraDAO
{
  @Autowired
  public CompraImpDAO(SessionFactory sesionFactory)
  {
    super(sesionFactory);
  }
  
  public CompraImpDAO() {}
  
  public List<DetalleCompra> obtenerDetalleCompra(long compra)
  {
    List<DetalleCompra> ventas = null;
    
    String hql = "select v.detalleCompra from Compra v where v.fiidcompra = " + compra;
    ventas = getHibernateTemplate().find(hql);
    
    return ventas;
  }
  
  public List<Compra> obtenerComprasFechas(Date fechaInicio, Date fechaFin)
  {
    List<Compra> ventas = null;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    String fechai = formato.format(fechaInicio);
    String fechaf = formato.format(fechaFin);
    
    String hql = "select v from Compra v where v.fdfechacompra between '" + fechai + " 00:00' and '" + fechaf + " 23:59'" + " order by v.fdfechacompra";
    ventas = obtenerPorConsulta(hql);
    
    return ventas;
  }
}