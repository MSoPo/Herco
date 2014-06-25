package ventas.util;

public class ErrorSistema
{
  private String causa;
  private String error;
  
  public void setCausa(String causa)
  {
    this.causa = causa;
  }
  
  public String getCausa()
  {
    return this.causa;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
  
  public String getError()
  {
    return this.error;
  }
}