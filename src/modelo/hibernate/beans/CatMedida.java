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
@Table(name="catmedida")
public class CatMedida
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidmedida;
  @Column
  private String fcdescmedida;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  @Column
  private String fcvaloresenteros;
  
  public CatMedida(String fcdescmedida, String fcactivo, String fcusuariomodifica)
  {
    this.fcdescmedida = fcdescmedida;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public CatMedida() {}
  
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
  
  public long getFiidmedida()
  {
    return this.fiidmedida;
  }
  
  public void setFiidmedida(long fiidmedida)
  {
    this.fiidmedida = fiidmedida;
  }
  
  public String getFcdescmedida()
  {
    return this.fcdescmedida;
  }
  
  public void setFcdescmedida(String fcdescmedida)
  {
    this.fcdescmedida = fcdescmedida;
  }
  
  public String getFcvaloresenteros()
  {
    return this.fcvaloresenteros;
  }
  
  public void setFcvaloresenteros(String fcvaloresenteros)
  {
    this.fcvaloresenteros = fcvaloresenteros;
  }
}