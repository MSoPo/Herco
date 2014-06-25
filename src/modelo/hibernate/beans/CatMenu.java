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
@Table(name="catmenu")
public class CatMenu
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidmenu;
  @Column
  private String fcnombremenu;
  @Column
  private String fcurl;
  @Column
  private String fcdescmenu;
  @Column
  private long fimenupadre;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  @Column
  private long fiposicion;
  
  public CatMenu(String fcnombremenu, String fcurl, String fcdescmenu, long fimenupadre, String fcactivo, String fcusuariomodifica)
  {
    this.fcnombremenu = fcnombremenu;
    this.fcurl = fcurl;
    this.fcdescmenu = fcdescmenu;
    this.fimenupadre = fimenupadre;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public CatMenu() {}
  
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
  
  public long getFiidmenu()
  {
    return this.fiidmenu;
  }
  
  public void setFiidmenu(long fiidmenu)
  {
    this.fiidmenu = fiidmenu;
  }
  
  public String getFcnombremenu()
  {
    return this.fcnombremenu;
  }
  
  public void setFcnombremenu(String fcnombremenu)
  {
    this.fcnombremenu = fcnombremenu;
  }
  
  public String getFcurl()
  {
    return this.fcurl;
  }
  
  public void setFcurl(String fcurl)
  {
    this.fcurl = fcurl;
  }
  
  public String getFcdescmenu()
  {
    return this.fcdescmenu;
  }
  
  public void setFcdescmenu(String fcdescmenu)
  {
    this.fcdescmenu = fcdescmenu;
  }
  
  public long getFimenupadre()
  {
    return this.fimenupadre;
  }
  
  public void setFimenupadre(long fimenupadre)
  {
    this.fimenupadre = fimenupadre;
  }
  
  public long getFiposicion()
  {
    return this.fiposicion;
  }
  
  public void setFiposicion(long fiposicion)
  {
    this.fiposicion = fiposicion;
  }
}