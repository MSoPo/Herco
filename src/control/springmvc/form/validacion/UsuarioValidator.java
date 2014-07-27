package control.springmvc.form.validacion;

import control.springmvc.form.CatalogosForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UsuarioValidator
  implements Validator
{
  public boolean supports(Class<?> clazz)
  {
    return CatalogosForm.class.equals(clazz);
  }
  
  public void validate(Object obj, Errors e) {}
}