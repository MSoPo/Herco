package control.springmvc.form;

import java.util.ArrayList;
import java.util.List;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.CatRol;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.Pago;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;

public class CatalogosForm
{
  private Usuario usuario = new Usuario();
  private List<CatRol> roles = new ArrayList();
  private Cliente cliente = new Cliente();
  private CatProveedor proveedor = new CatProveedor();
  private CatCaja caja = new CatCaja();
  private CatMedida medida = new CatMedida();
  private Pago pago = new Pago();
  private Producto producto = new Producto();
  private int operacion;
  private boolean chk;
  private String opcion;
  
private String salida;
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public List<CatRol> getRoles()
  {
    return this.roles;
  }
  
  public void setRoles(List<CatRol> roles)
  {
    this.roles = roles;
  }
  
  public Cliente getCliente()
  {
    return this.cliente;
  }
  
  public void setCliente(Cliente cliente)
  {
    this.cliente = cliente;
  }
  
  public CatProveedor getProveedor()
  {
    return this.proveedor;
  }
  
  public void setProveedor(CatProveedor proveedor)
  {
    this.proveedor = proveedor;
  }
  
  public CatCaja getCaja()
  {
    return this.caja;
  }
  
  public void setCaja(CatCaja caja)
  {
    this.caja = caja;
  }
  
  public CatMedida getMedida()
  {
    return this.medida;
  }
  
  public void setMedida(CatMedida medida)
  {
    this.medida = medida;
  }
  
  public Pago getPago()
  {
    return this.pago;
  }
  
  public void setPago(Pago pago)
  {
    this.pago = pago;
  }
  
  public Producto getProducto()
  {
    return this.producto;
  }
  
  public void setProducto(Producto producto)
  {
    this.producto = producto;
  }
  
  public int getOperacion()
  {
    return this.operacion;
  }
  
  public void setOperacion(int operacion)
  {
    this.operacion = operacion;
  }
  
  public boolean isChk()
  {
    return this.chk;
  }
  
  public void setChk(boolean chk)
  {
    this.chk = chk;
  }
  
  public String getSalida()
  {
    return this.salida;
  }
  
  public void setSalida(String salida)
  {
    this.salida = salida;
  }

public String getOpcion()
{
	return opcion;
}

public void setOpcion(String opcion)
{
	this.opcion = opcion;
}

}