package control.springmvc.form;

import modelo.hibernate.beans.Pago;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Venta;

public class VentaForm
{
	
	private Venta		venta					= new Venta();
	private Producto	producto				= new Producto();
	private Pago		pago					= new Pago();
	private double		recibido				= 0.0D;
	private double		cambio					= 0.0D;
	private double		diferenciaCambioVenta	= 0.0D;
	private int			accion					= 0;
	private String		nombre					= "";
	private String		domicilio				= "";
	private int 		impresion				= 0;
	
	public Venta getVenta()
	{
		return this.venta;
	}
	
	public void setVenta(Venta venta)
	{
		this.venta = venta;
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
	
	public Pago getPago()
	{
		return this.pago;
	}
	
	public void setPago(Pago pago)
	{
		this.pago = pago;
	}
	
	public void setRecibido(double recibido)
	{
		this.recibido = recibido;
	}
	
	public double getRecibido()
	{
		return this.recibido;
	}
	
	public void setCambio(double cambio)
	{
		this.cambio = cambio;
	}
	
	public double getCambio()
	{
		return this.cambio;
	}
	
	public void setDiferenciaCambioVenta(double diferenciaCambioVenta)
	{
		this.diferenciaCambioVenta = diferenciaCambioVenta;
	}
	
	public double getDiferenciaCambioVenta()
	{
		return this.diferenciaCambioVenta;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public String getDomicilio()
	{
		return domicilio;
	}
	
	public void setDomicilio(String domicilio)
	{
		this.domicilio = domicilio;
	}

	public int getImpresion()
	{
		return impresion;
	}

	public void setImpresion(int impresion)
	{
		this.impresion = impresion;
	}
}