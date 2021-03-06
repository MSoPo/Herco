package ventas.util;

public class Constantes
{
  public static final String ACTIVO = "1";
  public static final String INACTIVO = "0";
  public static final String CORRECTO = "SUCCESS";
  public static final String FALLO = "FAIL";
  public static final int INSERTAR = 1;
  public static final int ACTUALIZAR = 2;
  public static final String MAYORIGUAL = ">=";
  public static final String MENORIGUAL = "<=";
  public static final String USUARIOLOGEADO = "usuarioxrol";
  public static final String CAJALOGEADA = "caja";
  public static final String CAMPOACTIVO = "fcactivo";
  public static final String CAMPONOMBRE = "fcnombre";
  public static final String MAYOREOAPLICADO = "1";
  public static final String MAYOREONOAPLICADO = "0";
  public static final String REDIRECCION = "redirect:";
  public static final String CAMPO = "campo";
  public static final String VACIO = "";
  public static final String CANTIDAD = "cantidad";
  public static final String OPERACION = "operacion";
  public static final String FECHAFIN = "fechaf";
  public static final String FECHAINICIO = "fechai";
  public static final String PRODPRECIOCOMPRA = "fdpreciocompra";
  public static final String PRODPRECIOUNITARIO = "fdpreciomayoreo";
  public static final String PRODPRECIOMAYOREO = "fdpreciounitario";
  public static final String PRODCANTIDAD = "ficantidad";
  public static final String PRODCODIGO = "fiidproducto";
  public static final String PRODNOMBRE = "fcnomproducto";
  public static final String PROVNOMBRE = "fcnomproveedor";
  public static final String PROVID = "fiidproveedor";
  public static final String CAMPOUSUARIO = "fcusuario";
  public static final String CAMPOAPEPAT = "fcapepat";
  public static final String MEDIDADESCRIPCION = "fcdescmedida";
  public static final String CAJADESCRIPTCION = "fcdesccaja";
  public static final String CAJAID = "fiidcaja";
  public static final String PAGOIDVENTA = "venta.fiidventa";
  public static final String CAMPOPRODUCTOID = "producto.fiidproducto";
  public static final String FECHAFRONT = "dd/MM/yyyy hh:mm";
  public static final String FECHADATAPICLKER = "dd/MM/yyyy";
  public static final String CLIENTEID = "fiidcliente";
  public static final String DETALLEDECOMPRA = "detalleCompra";
  public static final String VENDIDOS = "2";
  public static final double IVA = 1.16D;
  public static final double IVA2 = 0.16D;
  public static final int ACCIONAGREGAR = 1;
  public static final int ACCIONCAMBIARCANTIDAD = 2;
  public static final int ACCIONELIMINAR = 3;
  public static final int ACCIONELIMINARTODO = 4;
  public static final int ACCIONGUARDAR = 5;
  public static final int ACCIONPAGOS = 7;
  public static final int ACCIONTCKET = 6;
  public static final String ERROR = "error";
  public static final Long IDDEDAULT = Long.valueOf(1L);
  
//Ticket
  	final static public int Ticket56 = 19;
	//final static public int Ticket56 = 16; //56mm
	
	//final static public int Ticket80 = 19; //80mm
  	
  //Desarrollo
  	//final static public String archivoTicket = "/Users/miguesopo/Desktop/ticket.txt";
  	//final static public String archivoTicketIva = "/Users/miguesopo/Desktop/ticketIva.txt";
  	
  //Produccion
  	final static public String archivoTicket = "C:/sistemaherco/ticket.txt";
  	final static public String archivoTicketIva = "C:/sistemaherco/ticketIva.txt";
  	
}