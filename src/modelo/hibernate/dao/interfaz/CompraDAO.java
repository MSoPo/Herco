package modelo.hibernate.dao.interfaz;

import java.util.Date;
import java.util.List;
import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.DetalleCompra;

public abstract interface CompraDAO
  extends GenericDAO<Compra, Long>
{
  public abstract List<DetalleCompra> obtenerDetalleCompra(long paramLong);
  
  public abstract List<Compra> obtenerComprasFechas(Date paramDate1, Date paramDate2);
}
