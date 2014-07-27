package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="catproveedor")
public class CatProveedor
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidproveedor;
  @Column
  private String fcnomproveedor;
  @Column
  private String fcdescproveedor;
  @Column
  private String fcdireccion;
  @Column
  private String fctelefono;
  @Column
  private String fccelular;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public CatProveedor(String fcnomproveedor, String fcdescproveedor, String fcdireccion, String fctelefono, String fccelular, String fcactivo, String fcusuariomodifica)
  {
    this.fcnomproveedor = fcnomproveedor;
    this.fcdescproveedor = fcdescproveedor;
    this.fcdireccion = fcdireccion;
    this.fctelefono = fctelefono;
    this.fccelular = fccelular;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public CatProveedor() {}
  
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
  
  public long getFiidproveedor()
  {
    return this.fiidproveedor;
  }
  
  public void setFiidproveedor(long fiidproveedor)
  {
    this.fiidproveedor = fiidproveedor;
  }
  
  public String getFcnomproveedor()
  {
    return this.fcnomproveedor;
  }
  
  public void setFcnomproveedor(String fcnomproveedor)
  {
    this.fcnomproveedor = fcnomproveedor;
  }
  
  public String getFcdescproveedor()
  {
    return this.fcdescproveedor;
  }
  
  public void setFcdescproveedor(String fcdescproveedor)
  {
    this.fcdescproveedor = fcdescproveedor;
  }
  
  public String getFcdireccion()
  {
    return this.fcdireccion;
  }
  
  public void setFcdireccion(String fcdireccion)
  {
    this.fcdireccion = fcdireccion;
  }
  
  public String getFctelefono()
  {
    return this.fctelefono;
  }
  
  public void setFctelefono(String fctelefono)
  {
    this.fctelefono = fctelefono;
  }
  
  public String getFccelular()
  {
    return this.fccelular;
  }
  
  public void setFccelular(String fccelular)
  {
    this.fccelular = fccelular;
  }
}