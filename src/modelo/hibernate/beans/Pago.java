package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pago")
public class Pago
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidpago;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidventa", nullable=false)
  private Venta venta;
  @Column
  private String fcobservacion;
  @Column
  private double fdcantidad;
  @Column
  private String fcactivo;
  @ManyToOne(optional=false)
  @JoinColumn(name="fcusuario", nullable=false)
  private Usuario usuario;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public Pago(Venta venta, String fcobservacion, double fdcantidad, String fcactivo, String fcusuariomodifica, Usuario usuario)
  {
    this.venta = venta;
    this.fcobservacion = fcobservacion;
    this.fcactivo = fcactivo;
    this.fdcantidad = fdcantidad;
    this.usuario = usuario;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public Pago() {}
  
  public Date getFdfechamodifica()
  {
    return this.fdfechamodifica;
  }
  
  public void setFdfechamodifica(Date fdfechamodifica)
  {
    this.fdfechamodifica = fdfechamodifica;
  }
  
  public String getFcusuariomodifica()
  {
    return this.fcusuariomodifica;
  }
  
  public void setFcusuariomodifica(String fcusuariomodifica)
  {
    this.fcusuariomodifica = fcusuariomodifica;
  }
  
  public String getFcactivo()
  {
    return this.fcactivo;
  }
  
  public void setFcactivo(String fcactivo)
  {
    this.fcactivo = fcactivo;
  }
  
  public long getFiidpago()
  {
    return this.fiidpago;
  }
  
  public void setFiidpago(long fiidpago)
  {
    this.fiidpago = fiidpago;
  }
  
  public Venta getVenta()
  {
    return this.venta;
  }
  
  public void setVenta(Venta venta)
  {
    this.venta = venta;
  }
  
  public String getFcobservacion()
  {
    return this.fcobservacion;
  }
  
  public void setFcobservacion(String fcobservacion)
  {
    this.fcobservacion = fcobservacion;
  }
  
  public double getFdcantidad()
  {
    return this.fdcantidad;
  }
  
  public void setFdcantidad(double fdcantidad)
  {
    this.fdcantidad = fdcantidad;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
}