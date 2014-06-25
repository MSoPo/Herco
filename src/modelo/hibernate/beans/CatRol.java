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
@Table(name="catrol")
public class CatRol
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidrol;
  @Column
  private String fcdescrol;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public CatRol(String fcdescrol, String fcactivo, String fcusuariomodifica)
  {
    this.fcdescrol = fcdescrol;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public CatRol() {}
  
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
  
  public long getFiidrol()
  {
    return this.fiidrol;
  }
  
  public void setFiidrol(long fiidrol)
  {
    this.fiidrol = fiidrol;
  }
  
  public String getFcdescrol()
  {
    return this.fcdescrol;
  }
  
  public void setFcdescrol(String fcdescrol)
  {
    this.fcdescrol = fcdescrol;
  }
}