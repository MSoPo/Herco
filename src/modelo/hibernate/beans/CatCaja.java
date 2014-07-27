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
@Table(name="catcaja")
public class CatCaja
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidcaja;
  @Column
  private String fcdesccaja;
  @Column
  private String fcubicacioncaja;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public CatCaja(String fcdesccaja, String fcubicacioncaja, String fcactivo, String fcusuariomodifica)
  {
    this.fcdesccaja = fcdesccaja;
    this.fcubicacioncaja = fcubicacioncaja;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public CatCaja() {}
  
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
  
  public String getFcubicacioncaja()
  {
    return this.fcubicacioncaja;
  }
  
  public void setFcubicacioncaja(String fcubicacioncaja)
  {
    this.fcubicacioncaja = fcubicacioncaja;
  }
  
  public String getFcdesccaja()
  {
    return this.fcdesccaja;
  }
  
  public void setFcdesccaja(String fcdesccaja)
  {
    this.fcdesccaja = fcdesccaja;
  }
  
  public long getFiidcaja()
  {
    return this.fiidcaja;
  }
  
  public void setFiidcaja(long fiidcaja)
  {
    this.fiidcaja = fiidcaja;
  }
}