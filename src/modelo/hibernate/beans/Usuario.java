package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario
  implements Serializable
{
  @Id
  private String fcusuario;
  @Column
  private String fcpassword;
  @Column
  private String fcrespuestasec;
  @Column
  private String fcpreguntasec;
  @Column
  private String fcnombre;
  @Column
  private String fcapepat;
  @Column
  private String fcapemat;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public Usuario(String fcusuario, String fcnombre, String fcapepat, String fcapemat, String fcpassword, String fcrespuestasec, String fcpreguntasec, String fcactivo, String fcusuariomodifica)
  {
    this.fcnombre = fcnombre;
    this.fcapemat = fcapemat;
    this.fcapepat = fcapepat;
    this.fcusuario = fcusuario;
    this.fcpassword = fcpassword;
    this.fcpreguntasec = fcpreguntasec;
    this.fcrespuestasec = fcrespuestasec;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public Usuario() {}
  
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
  
  public String getFcnombre()
  {
    return this.fcnombre;
  }
  
  public void setFcnombre(String fcnombre)
  {
    this.fcnombre = fcnombre;
  }
  
  public String getFcapepat()
  {
    return this.fcapepat;
  }
  
  public void setFcapepat(String fcapepat)
  {
    this.fcapepat = fcapepat;
  }
  
  public String getFcapemat()
  {
    return this.fcapemat;
  }
  
  public void setFcapemat(String fcapemat)
  {
    this.fcapemat = fcapemat;
  }
  
  public String getFcusuario()
  {
    return this.fcusuario;
  }
  
  public void setFcusuario(String fcusuario)
  {
    this.fcusuario = fcusuario;
  }
  
  public String getFcpassword()
  {
    return this.fcpassword;
  }
  
  public void setFcpassword(String fcpassword)
  {
    this.fcpassword = fcpassword;
  }
  
  public String getFcrespuestasec()
  {
    return this.fcrespuestasec;
  }
  
  public void setFcrespuestasec(String fcrespuestasec)
  {
    this.fcrespuestasec = fcrespuestasec;
  }
  
  public String getFcpreguntasec()
  {
    return this.fcpreguntasec;
  }
  
  public void setFcpreguntasec(String fcpreguntasec)
  {
    this.fcpreguntasec = fcpreguntasec;
  }
}