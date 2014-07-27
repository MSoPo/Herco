package modelo.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="producto")
public class Producto
  implements Serializable
{
  @Id
  private String fiidproducto;
  @Column
  private String fcnomproducto;
  @Column
  private String fcdescproducto;
  @Column
  private double fdpreciounitario;
  @Column
  private double fdpreciomayoreo;
  @Column
  private double fdpreciocompra;
  @Column
  private double fdprecioproveedor;
@ManyToOne(optional=false)
  @JoinColumn(name="fiidmedida", nullable=false)
  private CatMedida medida;
  @Column
  private double ficantidad;
  @Column
  private String fcactivo;
  @Column
  private String fcusuariomodifica;
  @Column
  private Date fdfechamodifica;
  @Column
  private int ficantidadmayore;
  
  public Producto(String fiidproducto, String fcnomproducto, String fcdescproducto, double fdpreciounitario, double fdpreciomayore, double fdpreciocompra, CatMedida medida, double ficantidad, String fcactivo, String fcusuariomodifica, double fdprecioproveedor)
  {
    this.fiidproducto = fiidproducto;
    this.fcnomproducto = fcnomproducto;
    this.fcdescproducto = fcdescproducto;
    this.fdpreciounitario = fdpreciounitario;
    this.fdpreciomayoreo = fdpreciomayore;
    this.fdpreciocompra = fdpreciocompra;
    this.medida = medida;
    setFicantidad(ficantidad);
    this.fcactivo = fcactivo;
    this.fcusuariomodifica = fcusuariomodifica;
    this.fdfechamodifica = new Date();
    this.fdprecioproveedor = fdprecioproveedor;
  }
  
  public Producto() {}
  
  	public double getFdprecioproveedor() {
		return fdprecioproveedor;
	}

	public void setFdprecioproveedor(double fdprecioproveedor) {
		this.fdprecioproveedor = fdprecioproveedor;
	}
  
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
  
  public String getFiidproducto()
  {
    return this.fiidproducto;
  }
  
  public void setFiidproducto(String fiidproducto)
  {
    this.fiidproducto = fiidproducto;
  }
  
  public String getFcnomproducto()
  {
    return this.fcnomproducto;
  }
  
  public void setFcnomproducto(String fcnomproducto)
  {
    this.fcnomproducto = fcnomproducto;
  }
  
  public String getFcdescproducto()
  {
    return this.fcdescproducto;
  }
  
  public void setFcdescproducto(String fcdescproducto)
  {
    this.fcdescproducto = fcdescproducto;
  }
  
  public double getFdpreciounitario()
  {
    return this.fdpreciounitario;
  }
  
  public void setFdpreciounitario(double fdpreciounitario)
  {
    this.fdpreciounitario = fdpreciounitario;
  }
  
  public double getFdpreciomayoreo()
  {
    return this.fdpreciomayoreo;
  }
  
  public void setFdpreciomayoreo(double fdpreciomayore)
  {
    this.fdpreciomayoreo = fdpreciomayore;
  }
  
  public double getFdpreciocompra()
  {
    return this.fdpreciocompra;
  }
  
  public void setFdpreciocompra(double fdpreciocompra)
  {
    this.fdpreciocompra = fdpreciocompra;
  }
  
  public CatMedida getMedida()
  {
    return this.medida;
  }
  
  public void setMedida(CatMedida fiidmedida)
  {
    this.medida = fiidmedida;
  }
  
  public int getFicantidadmayore()
  {
    return this.ficantidadmayore;
  }
  
  public void setFicantidadmayore(int ficantidadmayore)
  {
    this.ficantidadmayore = ficantidadmayore;
  }
  
  public double getFicantidad()
  {
    return this.ficantidad;
  }
  
  public void setFicantidad(double ficantidad)
  {
    this.ficantidad = ficantidad;
  }
}