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
@Table(name="compra")
public class Compra
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidcompra;
  @Column
  private Date fdfechacompra;
  @ManyToOne(optional=false)
  @JoinColumn(name="fcusuariocompra", nullable=false)
  private Usuario usuario;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidproveedor", nullable=false)
  private CatProveedor catproveedor;
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
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="fiidcompra")
  private List<DetalleCompra> detalleCompra = new ArrayList();
  
  public long getFiidcompra()
  {
    return this.fiidcompra;
  }
  
  public void setFiidcompra(long fiidcompra)
  {
    this.fiidcompra = fiidcompra;
  }
  
  public Date getFdfechacompra()
  {
    return this.fdfechacompra;
  }
  
  public void setFdfechacompra(Date fdfechacompra)
  {
    this.fdfechacompra = fdfechacompra;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public CatProveedor getCatproveedor()
  {
    return this.catproveedor;
  }
  
  public void setCatproveedor(CatProveedor catproveedor)
  {
    this.catproveedor = catproveedor;
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
  
  public List<DetalleCompra> getDetalleCompra()
  {
    return this.detalleCompra;
  }
  
  public void setDetalleCompra(List<DetalleCompra> detalleCompra)
  {
    for (DetalleCompra lst : detalleCompra) {
      lst.setFiidcompra(this);
    }
    this.detalleCompra = detalleCompra;
  }
  
  public void agregarDetalle(DetalleCompra detalleCompra)
  {
    this.detalleCompra.add(detalleCompra);
    detalleCompra.setFiidcompra(this);
  }
}