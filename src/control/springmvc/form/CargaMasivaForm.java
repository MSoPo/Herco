package control.springmvc.form;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CargaMasivaForm
{
  private CommonsMultipartFile fichero;
  
  public CommonsMultipartFile getFichero()
  {
    return this.fichero;
  }
  
  public void setFichero(CommonsMultipartFile fichero)
  {
    this.fichero = fichero;
  }
}