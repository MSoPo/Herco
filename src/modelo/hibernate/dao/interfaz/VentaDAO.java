package modelo.hibernate.dao.interfaz;

import java.util.Date;
import java.util.List;
import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Venta;

public abstract interface VentaDAO
  extends GenericDAO<Venta, Long>
{
  public abstract List<Venta> obtenerVentaCliente(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<Venta> obtenerVentasFechas(Date paramDate1, Date paramDate2);
  
  public abstract List<DetalleVenta> obtenerDetalleVenta(long paramLong);
  
  public abstract List<Venta>	obtenerVentaDiaria(int mes, int ano);
  
  public abstract List<Venta>	obtenerVentaMensual(int ano);
}