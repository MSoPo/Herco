package ventas.util;

import org.hibernate.HibernateException;

@SuppressWarnings("serial")
public class BussinessException
  extends Exception
{
  public String msg;
  public String causa;
  
  public BussinessException(String msg, HibernateException he)
  {
    this.msg = msg;
    this.causa = he.getMessage();
    setStackTrace(he.getStackTrace());
  }
}