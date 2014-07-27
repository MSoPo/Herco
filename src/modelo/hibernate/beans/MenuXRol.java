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
@Table(name="menuxrol")
public class MenuXRol
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long fiidmenuxrol;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidmenu", nullable=false)
  private CatMenu menu;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidrol", nullable=false)
  private CatRol rol;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public MenuXRol(CatMenu menu, CatRol rol, String fcactivo, String fcusuariomodifica)
  {
    this.menu = menu;
    this.rol = rol;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public MenuXRol() {}
  
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
  
  public Long getFiidmenuxrol()
  {
    return this.fiidmenuxrol;
  }
  
  public void setFiidmenuxrol(Long fiidmenu)
  {
    this.fiidmenuxrol = fiidmenu;
  }
  
  public CatMenu getMenu()
  {
    return this.menu;
  }
  
  public void setMenu(CatMenu menu)
  {
    this.menu = menu;
  }
  
  public CatRol getRol()
  {
    return this.rol;
  }
  
  public void setRol(CatRol rol)
  {
    this.rol = rol;
  }
}