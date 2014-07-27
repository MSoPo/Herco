package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="historialproductos")
public class HistorialProducto
  implements Serializable
{
  @Id
  private long fiidhistorial;
  @Column
  private String fiidproducto;
  @Column
  private String fccampo;
  @Column
  private double fcvaloranterior;
  @Column
  private double fcvalornuevo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public void setFiidhistorial(long fiidhistorial)
  {
    this.fiidhistorial = fiidhistorial;
  }
  
  public long getFiidhistorial()
  {
    return this.fiidhistorial;
  }
  
  public void setFiidproducto(String fiidproducto)
  {
    this.fiidproducto = fiidproducto;
  }
  
  public String getFiidproducto()
  {
    return this.fiidproducto;
  }
  
  public void setFccampo(String fccampo)
  {
    this.fccampo = fccampo;
  }
  
  public String getFccampo()
  {
    return this.fccampo;
  }
  
  public void setFcvaloranterior(double fcvaloranterior)
  {
    this.fcvaloranterior = fcvaloranterior;
  }
  
  public double getFcvaloranterior()
  {
    return this.fcvaloranterior;
  }
  
  public void setFcvalornuevo(double fcvalornuevo)
  {
    this.fcvalornuevo = fcvalornuevo;
  }
  
  public double getFcvalornuevo()
  {
    return this.fcvalornuevo;
  }
  
  public void setFcusuariomodifica(String fcusuariomodifica)
  {
    this.fcusuariomodifica = fcusuariomodifica;
  }
  
  public String getFcusuariomodifica()
  {
    return this.fcusuariomodifica;
  }
  
  public void setFdfechamodifica(Date fdfechamodifica)
  {
    this.fdfechamodifica = fdfechamodifica;
  }
  
  public Date getFdfechamodifica()
  {
    return this.fdfechamodifica;
  }
}