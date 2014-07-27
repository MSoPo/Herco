package modelo.hibernate.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalleventa")
public class DetalleVenta
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiiddetalleventa;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidventa", nullable=false)
  private Venta fiidventa;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidproducto", nullable=false)
  private Producto producto;
  @Column
  private double ficantidad;
  @Column
  private double fdprecioventa;
  @Column
  private double fdsubtotal;
  
  public DetalleVenta(Venta fiidventa, Producto fiidproducto, double ficantidad, double fdprecioventa, double fdsubtotal)
  {
    setFiidventa(fiidventa);
    setProducto(fiidproducto);
    setFicantidad(ficantidad);
    setFdprecioventa(fdprecioventa);
    setFdsubtotal(fdsubtotal);
  }
  
  public DetalleVenta() {}
  
  public long getFiiddetalleventa()
  {
    return this.fiiddetalleventa;
  }
  
  public void setFiiddetalleventa(long fiiddetalleventa)
  {
    this.fiiddetalleventa = fiiddetalleventa;
  }
  
  public Venta getFiidventa()
  {
    return this.fiidventa;
  }
  
  public void setFiidventa(Venta fiidventa)
  {
    this.fiidventa = fiidventa;
  }
  
  public Producto getProducto()
  {
    return this.producto;
  }
  
  public void setProducto(Producto producto)
  {
    this.producto = producto;
  }
  
  public double getFdprecioventa()
  {
    return this.fdprecioventa;
  }
  
  public void setFdprecioventa(double fdprecioventa)
  {
    this.fdprecioventa = fdprecioventa;
  }
  
  public double getFdsubtotal()
  {
    return this.fdsubtotal;
  }
  
  public void setFdsubtotal(double fdsubtotal)
  {
    this.fdsubtotal = fdsubtotal;
  }
  
  public double getFicantidad()
  {
    return this.ficantidad;
  }
  
  public void setFicantidad(double ficantidad)
  {
    this.ficantidad = ficantidad;
  }
}