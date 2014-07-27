package ventas.util;

public class JsonResponse
{
  private String status;
  private Object result;
  private Object[] lsresult;
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Object getResult()
  {
    return this.result;
  }
  
  public void setResult(Object result)
  {
    this.result = result;
  }
  
  public void setLsresult(Object[] lsresult)
  {
    this.lsresult = lsresult;
  }
  
  public Object[] getLsresult()
  {
    return this.lsresult;
  }
}