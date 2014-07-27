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
@Table(name="cliente")
public class Cliente
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long fiidcliente;
  @Column
  private String fcnombre;
  @Column
  private String fcapepat;
  @Column
  private String fcapemat;
  @Column
  private String fcdireccion;
  @Column
  private double fddescuento;
  @Column
  private String fctelefono;
  @Column
  private String fccelular;
  @Column
  private String fcactivo;
  @Column
  private String fcmayoreo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  
  public Cliente(String fcnombre, String fcapepat, String fcapemat, String fcdireccion, String fctelefono, String fccelular, double fddescuento, String fcactivo, String fcusuariomodifica)
  {
    this.fcnombre = fcnombre;
    this.fcapemat = fcapemat;
    this.fcapepat = fcapepat;
    this.fcdireccion = fcdireccion;
    this.fctelefono = fctelefono;
    this.fccelular = fccelular;
    this.fddescuento = fddescuento;
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
  }
  
  public Cliente() {}
  
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
  
  public long getFiidcliente()
  {
    return this.fiidcliente;
  }
  
  public void setFiidcliente(long fiidcliente)
  {
    this.fiidcliente = fiidcliente;
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
  
  public String getFcdireccion()
  {
    return this.fcdireccion;
  }
  
  public void setFcdireccion(String fcdireccion)
  {
    this.fcdireccion = fcdireccion;
  }
  
  public double getFddescuento()
  {
    return this.fddescuento;
  }
  
  public void setFddescuento(double fddesceunto)
  {
    this.fddescuento = fddesceunto;
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
  
  public String getFcmayoreo()
  {
    return this.fcmayoreo;
  }
  
  public void setFcmayoreo(String fcmayoreo)
  {
    this.fcmayoreo = fcmayoreo;
  }
}