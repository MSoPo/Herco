package negocio.delegate;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.DetalleCompra;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;

public class CompraDelegate
{
  public double calcularTotal(List<DetalleCompra> lstdetalleCompra)
  {
    double total = 0.0D;
    for (DetalleCompra compra : lstdetalleCompra) {
      total += compra.getFdsubtotal();
    }
    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
    df.applyPattern("0.00");
    
    return new Double(df.format(total)).doubleValue();
  }
  
  public double calcularSubtotal(Producto producto, double cantidad)
  {
    double subtotal = 0.0D;
    
    subtotal = cantidad * producto.getFdpreciocompra();
    
    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
    df.applyPattern("0.00");
    
    return new Double(df.format(subtotal)).doubleValue();
  }
  
  public Compra nuevaCompra(Compra compra, List<DetalleCompra> lstCompra, Usuario usuario, CatProveedor proveedor)
  {
    Date fechaActual = new Date();
    compra.setDetalleCompra(lstCompra);
    compra.setFdfechacompra(fechaActual);
    compra.setUsuario(usuario);
    compra.setFcobservaciones("Pendiente");
    compra.setCatproveedor(proveedor);
    compra.setFcactivo("1");
    compra.setFcusuariomodifica(usuario.getFcusuario());
    compra.setFdfechamodifica(fechaActual);
    return compra;
  }
}