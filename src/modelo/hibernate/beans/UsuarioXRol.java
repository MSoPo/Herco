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
@Table(name="usuarioxrol")
public class UsuarioXRol
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidusuarioxrol;
  @ManyToOne(optional=false)
  @JoinColumn(name="fcusuario", nullable=false)
  private Usuario usuario;
  @ManyToOne(optional=false)
  @JoinColumn(name="fiidrol", nullable=false)
  private CatRol rol;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public UsuarioXRol(CatRol rol, Usuario usuario, String fcactivo, String fcusuariomodifica)
  {
    this.rol = rol;
    this.usuario = usuario;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public UsuarioXRol() {}
  
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
  
  public long getFiidusuarioxrol()
  {
    return this.fiidusuarioxrol;
  }
  
  public void setFiidusuarioxrol(long fiidusuarioxrol)
  {
    this.fiidusuarioxrol = fiidusuarioxrol;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
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