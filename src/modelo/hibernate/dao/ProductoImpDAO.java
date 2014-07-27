 package modelo.hibernate.dao;
 
 import java.math.BigDecimal;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import modelo.hibernate.beans.HistorialProducto;
 import modelo.hibernate.beans.Producto;
 import modelo.hibernate.dao.interfaz.HistorialProductoDAO;
 import modelo.hibernate.dao.interfaz.ProductoDAO;
 import org.hibernate.Query;
 import org.hibernate.SessionFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;
 import org.springframework.transaction.annotation.Transactional;
 import ventas.util.BussinessException;
 
 @Repository
 public class ProductoImpDAO
   extends GenericImpDAO<Producto, String>
   implements ProductoDAO
 {
   @Autowired
   private HistorialProductoDAO historialdao;
   
   public void actualiza(Producto vo)
     throws BussinessException
   {
     Producto tmpProd = (Producto)obtenPorId(vo.getFiidproducto());
     HistorialProducto hisProd = new HistorialProducto();
     hisProd.setFcusuariomodifica(vo.getFcusuariomodifica());
     hisProd.setFdfechamodifica(new Date());
     hisProd.setFiidproducto(vo.getFiidproducto());
     if (tmpProd.getFdpreciocompra() != vo.getFdpreciocompra())
     {
       hisProd.setFccampo("fdpreciocompra");
       hisProd.setFcvaloranterior(tmpProd.getFdpreciocompra());
       hisProd.setFcvalornuevo(vo.getFdpreciocompra());
       this.historialdao.guarda(hisProd);
     }
     if (tmpProd.getFdpreciomayoreo() != vo.getFdpreciomayoreo())
     {
       hisProd.setFccampo("fdpreciounitario");
       hisProd.setFcvaloranterior(tmpProd.getFdpreciomayoreo());
       hisProd.setFcvalornuevo(vo.getFdpreciomayoreo());
       this.historialdao.guarda(hisProd);
     }
     if (tmpProd.getFdpreciounitario() != vo.getFdpreciounitario())
     {
       hisProd.setFccampo("fdpreciomayoreo");
       hisProd.setFcvaloranterior(tmpProd.getFdpreciounitario());
       hisProd.setFcvalornuevo(vo.getFdpreciounitario());
       this.historialdao.guarda(hisProd);
     }
     if (tmpProd.getFicantidad() != vo.getFicantidad())
     {
       hisProd.setFccampo("ficantidad");
       hisProd.setFcvaloranterior(tmpProd.getFicantidad());
       hisProd.setFcvalornuevo(vo.getFicantidad());
       this.historialdao.guarda(hisProd);
     }
     super.actualiza(vo);
   }
   
   @Autowired
   public ProductoImpDAO(SessionFactory sesionFactory)
   {
     super(sesionFactory);
   }
   
   public ProductoImpDAO() {}
   
   public List<Producto> obtenListaAutocompletado(String nombre)
   {
     List<Producto> listaContactos = null;
     listaContactos = getHibernateTemplate().find("SELECT e FROM Producto e where e.fcnomproducto like '%" + nombre + "%'");
     return listaContactos;
   }
   
   public List<Producto> obtenListaAutocompletado(String nombre, String fcactivo)
   {
     List<Producto> listaContactos = null;
     listaContactos = getHibernateTemplate().find("SELECT e FROM Producto e where e.fcnomproducto like '%" + nombre + "%' and e.fcactivo = " + fcactivo);
     return listaContactos;
   }
   
   public List<Producto> obtenListaPorIdLike(String codigo)
   {
     List<Producto> listaContactos = null;
     listaContactos = getHibernateTemplate().find("SELECT e FROM Producto e where e.fiidproducto like '%" + codigo + "%'");
     return listaContactos;
   }
   
   public List<Producto> obtenerStockBajo(int cantidad, boolean mayor)
   {
     List<Producto> listaContactos = null;
     String operacion = "<=";
     if (mayor) {
       operacion = ">=";
     }
     listaContactos = getHibernateTemplate().find("SELECT e FROM Producto e where e.fcactivo = '1' and e.ficantidad  " + operacion + " " + cantidad + " order by ficantidad");
     return listaContactos;
   }
   
   @Transactional
   public List<Producto> obtenerVendidos(Date fechaInicio, Date fechaFin, int cantidad, boolean vendido)
   {
     SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
     String fechai = formato.format(fechaInicio);
     String fechaf = formato.format(fechaFin);
     List listaContactos = null;
     if (!vendido)
     {
       String hql = "select dv.producto.fiidproducto, dv.producto.fcnomproducto ,  sum(dv.ficantidad), dv.producto.fdpreciocompra, dv.producto.fdpreciounitario, dv.producto.fdpreciomayoreo  from DetalleVenta dv where dv.fiidventa.fdfechaventa between '" + 
         fechai + " 00:00' and '" + fechaf + 
         " 23:59' group by dv.producto.fiidproducto order by sum(dv.ficantidad) desc";
       
       getHibernateTemplate().setMaxResults(cantidad);
       listaContactos = getHibernateTemplate().find(hql);
     }
     else
     {
       String hql = "select p.fiidproducto, p.fcnomproducto, sum(dv.ficantidad) as suma,  p.fdpreciocompra, p.fdpreciounitario, p.fdpreciomayoreo  from detalleventa dv inner join (select * from venta where fdfechaventa between '" + 
         fechai + " 00:00' and '" + fechaf + " 23:59') v" + " on dv.fiidventa=v.fiidventa " + 
         " right outer join producto p on dv.fiidproducto=p.fiidproducto" + " where p.fcactivo='1' group by p.fiidproducto " + " order by suma asc limit " + cantidad;
       Query query = getSession().createSQLQuery(hql);
       listaContactos = query.list();
     }
     List<Producto> lstProd = new ArrayList();
     for (Object o : listaContactos)
     {
       Object[] obj = (Object[])o;
       Producto p = new Producto();
       p.setFiidproducto(obj[0].toString());
       p.setFcnomproducto(obj[1].toString());
       if (!vendido)
       {
         p.setFicantidad(obj[2] == null ? 0.0D : ((Double)obj[2]).doubleValue());
         p.setFdpreciocompra(((Double)obj[3]).doubleValue());
         p.setFdpreciounitario(((Double)obj[4]).doubleValue());
         p.setFdpreciomayoreo(((Double)obj[5]).doubleValue());
       }
       else
       {
         p.setFicantidad(obj[2] == null ? 0.0D : ((BigDecimal)obj[2]).doubleValue());
         p.setFdpreciocompra(((BigDecimal)obj[3]).doubleValue());
         p.setFdpreciounitario(((BigDecimal)obj[4]).doubleValue());
         p.setFdpreciomayoreo(((BigDecimal)obj[5]).doubleValue());
       }
       lstProd.add(p);
     }
     getHibernateTemplate().setMaxResults(0);
     
     return lstProd;
   }
 }