package modelo.hibernate.dao.interfaz;

import java.util.List;
import modelo.hibernate.beans.Pago;

public abstract interface PagoDAO
  extends GenericDAO<Pago, Long>
{
  public abstract List<Pago> obtenerListaFaltantes();
  
  public abstract double obtenerTotalPagos(Long paramLong);
  
  public abstract List<Pago> obtenerListaFaltantesGrafica(int paramInt);
  
  public abstract List<Pago> obtenerListaFaltantesLike(String paramString1, String paramString2);
}
