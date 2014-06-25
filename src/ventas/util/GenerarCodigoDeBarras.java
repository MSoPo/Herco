package ventas.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.jbarcodebean.BarcodeException;
import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Interleaved25;

public class GenerarCodigoDeBarras
{
  public static byte[] crearImagen(String codigo)
    throws IOException, BarcodeException
  {
    JBarcodeBean barcode = new JBarcodeBean();
    

    barcode.setCodeType(new Interleaved25());
    


    barcode.setCode(codigo);
    barcode.setCheckDigit(true);
    
    BufferedImage bufferedImage = barcode.draw(new BufferedImage(codigo.length() * 15, 100, 1));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "jpeg", baos);
    baos.flush();
    byte[] imageInByte = baos.toByteArray();
    baos.close();
    
    return imageInByte;
  }
}