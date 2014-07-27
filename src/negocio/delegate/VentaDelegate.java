package negocio.delegate;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.Venta;

public class VentaDelegate
{
  public double calcularTotal(List<DetalleVenta> lstdetalleVenta, Cliente cliente)
  {
    double total = 0.0D;
    for (DetalleVenta venta : lstdetalleVenta) {
      total += venta.getFdsubtotal();
    }
    total -= total * cliente.getFddescuento() / 100.0D;
    
    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
    df.applyPattern("0.00");
    
    return new Double(df.format(total)).doubleValue();
  }
  
  public double calcularSubtotal(Producto producto, Cliente cliente, double cantidad)
  {
    double subtotal = 0.0D;
    if(cliente.getFcmayoreo().equals("2")){
    	if(producto.getFdprecioproveedor() > 0)
    		subtotal = cantidad * producto.getFdprecioproveedor();
    	else if(producto.getFdpreciomayoreo() > 0)
    		subtotal = cantidad * producto.getFdpreciomayoreo();
    	else
    		subtotal = cantidad * producto.getFdpreciounitario();
    	
    }else if(cliente.getFcmayoreo().equals("1")){
    	
    	if(producto.getFdpreciomayoreo() > 0)
    		subtotal = cantidad * producto.getFdpreciomayoreo();
    	else
    		subtotal = cantidad * producto.getFdpreciounitario();
    	
    }else{
    	if ((producto.getFicantidadmayore() > 0) && (producto.getFicantidadmayore() <= cantidad)) {
    	      subtotal = cantidad * producto.getFdpreciomayoreo();
    	    } else {
    	      subtotal = cantidad * producto.getFdpreciounitario();
    	    }
    }
    
    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
    df.applyPattern("0.00");
    
    return new Double(df.format(subtotal)).doubleValue();
  }
  
  public Venta nuevaVenta(Venta venta, List<DetalleVenta> lstVenta, Usuario usuario, Cliente cliente, CatCaja caja, String Observacion)
  {
    Date fechaActual = new Date();
    for (DetalleVenta dv : lstVenta) {
      dv.setFiiddetalleventa(0L);
    }
    venta.setDetalleVenta(lstVenta);
    venta.setFdfechaventa(fechaActual);
    venta.setUsuario(usuario);
    venta.setFcobservaciones(Observacion);
    venta.setCaja(caja);
    venta.setCliente(cliente);
    venta.setFcactivo("1");
    venta.setFcusuariomodifica(usuario.getFcusuario());
    venta.setFdfechamodifica(fechaActual);
    return venta;
  }
  
  public String agregarEspacion(String valor, int cantidad, boolean inicio)
  {
    while (valor.length() < cantidad) {
      if (inicio) {
        valor = " " + valor;
      } else {
        valor = valor + " ";
      }
    }
    if (valor.length() > cantidad) {
      valor.substring(0, cantidad);
    }
    return valor;
  }
}