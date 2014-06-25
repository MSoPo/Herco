package control.springmvc.form;

import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.Producto;

public class CompraForm
{
  private Compra compra = new Compra();
  private Producto producto = new Producto();
  private int accion = 0;
  
  public Compra getCompra()
  {
    return this.compra;
  }
  
  public void setCompra(Compra compra)
  {
    this.compra = compra;
  }
  
  public Producto getProducto()
  {
    return this.producto;
  }
  
  public void setProducto(Producto producto)
  {
    this.producto = producto;
  }
  
  public int getAccion()
  {
    return this.accion;
  }
  
  public void setAccion(int accion)
  {
    this.accion = accion;
  }
}