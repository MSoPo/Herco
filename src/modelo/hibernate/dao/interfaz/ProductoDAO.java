package modelo.hibernate.dao.interfaz;

import java.util.Date;
import java.util.List;
import modelo.hibernate.beans.Producto;
import ventas.util.BussinessException;

public abstract interface ProductoDAO
  extends GenericDAO<Producto, String>
{
  public abstract List<Producto> obtenListaAutocompletado(String paramString);
  
  public abstract List<Producto> obtenListaAutocompletado(String paramString1, String paramString2);
  
  public abstract List<Producto> obtenListaPorIdLike(String paramString);
  
  public abstract List<Producto> obtenerStockBajo(int paramInt, boolean paramBoolean);
  
  public abstract List<Producto> obtenerVendidos(Date paramDate1, Date paramDate2, int paramInt, boolean paramBoolean);
  
  public abstract void actualiza(Producto paramProducto)
    throws BussinessException;
}