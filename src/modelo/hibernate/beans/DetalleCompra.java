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
@Table(name="detallecompra")
public class DetalleCompra
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiiddetallecompra;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidcompra", nullable=false)
  private Compra fiidcompra;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidproducto", nullable=false)
  private Producto producto;
  @Column
  private double ficantidad;
  @Column
  private double fdpreciocompra;
  @Column
  private double fdsubtotal;
  
  public DetalleCompra(Compra compra, Producto producto, double cantidad, double precio, double subtotal)
  {
    this.fiidcompra = compra;
    this.producto = producto;
    setFicantidad(cantidad);
    this.fdpreciocompra = precio;
    this.fdsubtotal = subtotal;
  }
  
  public DetalleCompra() {}
  
  public long getFiiddetallecompra()
  {
    return this.fiiddetallecompra;
  }
  
  public void setFiiddetallecompra(long fiiddetallecompra)
  {
    this.fiiddetallecompra = fiiddetallecompra;
  }
  
  public Producto getProducto()
  {
    return this.producto;
  }
  
  public void setProducto(Producto producto)
  {
    this.producto = producto;
  }
  
  public double getFdpreciocompra()
  {
    return this.fdpreciocompra;
  }
  
  public void setFdpreciocompra(double fdpreciocompra)
  {
    this.fdpreciocompra = fdpreciocompra;
  }
  
  public double getFdsubtotal()
  {
    return this.fdsubtotal;
  }
  
  public void setFdsubtotal(double fdsubtotal)
  {
    this.fdsubtotal = fdsubtotal;
  }
  
  public Compra getFiidcompra()
  {
    return this.fiidcompra;
  }
  
  public void setFiidcompra(Compra fiidcompra)
  {
    this.fiidcompra = fiidcompra;
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