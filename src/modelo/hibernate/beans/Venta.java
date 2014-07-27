package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="venta")
public class Venta
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidventa;
  @Column
  private Date fdfechaventa;
  @ManyToOne(optional=false)
  @JoinColumn(name="fcusuarioventa", nullable=false)
  private Usuario usuario;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidcliente")
  private Cliente cliente;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidcaja", nullable=false)
  private CatCaja caja;
  @Column
  private String fcobservaciones;
  @Column
  private double fdtotal;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  @Column
  private long fiidventaanterior;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="fiidventa")
  private List<DetalleVenta> detalleVenta = new ArrayList();
  
  public long getFiidventa()
  {
    return this.fiidventa;
  }
  
  public void setFiidventa(long fiidventa)
  {
    this.fiidventa = fiidventa;
  }
  
  public Date getFdfechaventa()
  {
    return this.fdfechaventa;
  }
  
  public void setFdfechaventa(Date fdfechaventa)
  {
    this.fdfechaventa = fdfechaventa;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Cliente getCliente()
  {
    return this.cliente;
  }
  
  public void setCliente(Cliente cliente)
  {
    this.cliente = cliente;
  }
  
  public CatCaja getCaja()
  {
    return this.caja;
  }
  
  public void setCaja(CatCaja caja)
  {
    this.caja = caja;
  }
  
  public String getFcobservaciones()
  {
    return this.fcobservaciones;
  }
  
  public void setFcobservaciones(String fcobservaciones)
  {
    this.fcobservaciones = fcobservaciones;
  }
  
  public double getFdtotal()
  {
    return this.fdtotal;
  }
  
  public void setFdtotal(double fdtotal)
  {
    this.fdtotal = fdtotal;
  }
  
  public String getFcactivo()
  {
    return this.fcactivo;
  }
  
  public void setFcactivo(String fcactivo)
  {
    this.fcactivo = fcactivo;
  }
  
  public String getFcusuariomodifica()
  {
    return this.fcusuariomodifica;
  }
  
  public void setFcusuariomodifica(String fcusuariomodifica)
  {
    this.fcusuariomodifica = fcusuariomodifica;
  }
  
  public Date getFdfechamodifica()
  {
    return this.fdfechamodifica;
  }
  
  public void setFdfechamodifica(Date fdfechamodifica)
  {
    this.fdfechamodifica = fdfechamodifica;
  }
  
  public List<DetalleVenta> getDetalleVenta()
  {
    return this.detalleVenta;
  }
  
  public void setDetalleVenta(List<DetalleVenta> detalleVenta)
  {
    for (DetalleVenta dv : detalleVenta) {
      dv.setFiidventa(this);
    }
    this.detalleVenta = detalleVenta;
  }
  
  public void agregarDetalle(DetalleVenta detalleVenta)
  {
    this.detalleVenta.add(detalleVenta);
    detalleVenta.setFiidventa(this);
  }
  
  public void setFiidventaanterior(long fiidventaanterior)
  {
    this.fiidventaanterior = fiidventaanterior;
  }
  
  public long getFiidventaanterior()
  {
    return this.fiidventaanterior;
  }
}