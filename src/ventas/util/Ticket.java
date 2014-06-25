package ventas.util;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Venta;

public class Ticket
{
	
	private String	contentTicket	= "Ferreteria San Juan\nEXPEDIDO EN: Cuautempan, Pue.\nDOMICILIO CONOCIDO CUAUTEMPAN.\nTEL. 797 59 6 33 15\n=============================\nRFC: COCH8205082J5\n{{caja}} - Ticket # {{ticket}}\nLE ATENDIO: {{cajero}}\n{{dateTime}}\n=============================\n{{items}}\n=============================\nTOTAL: {{total}}\n\n{{ventaanterior}}RECIBIDO: {{recibo}}\n{{lnlcambio}}: {{change}}\n\n{{Cliente}}=============================\nGRACIAS POR SU COMPRA...\nESPERAMOS SU VISITA NUEVAMENTE\n\n\n\n\n\n\n\n";
	
	public Ticket(Venta venta)
	{
		this.contentTicket = "Ferreteria San Juan\nEXPEDIDO EN: Cuautempan, Pue.\nDOMICILIO CONOCIDO CUAUTEMPAN.\nTEL. 797 59 6 33 15\n=============================\nRFC: COCH8205082J5\n{{caja}} - Ticket # {{ticket}}\nLE ATENDIO: {{cajero}}\n{{dateTime}}\n=============================\n{{items}}\n=============================\nTOTAL: {{total}}\n\n{{Cliente}}=============================\nGRACIAS POR SU COMPRA...\nESPERAMOS SU VISITA NUEVAMENTE\n\n\n\n\n\n\n\n";
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		this.contentTicket = this.contentTicket.replace("{{ticket}}", venta.getFiidventa() + "");
		this.contentTicket = this.contentTicket.replace("{{cajero}}", venta.getUsuario().getFcnombre() + " " + venta.getUsuario().getFcapepat() + " "
				+ venta.getUsuario().getFcapemat());
		this.contentTicket = this.contentTicket.replace("{{dateTime}}", formato.format(venta.getFdfechaventa()));
		
		String itmes = "";
		for (DetalleVenta detVe : venta.getDetalleVenta())
		{
			String nombre = detVe.getProducto().getFcnomproducto();
			String nombreF = "";
			for (int i = 0; i < nombre.length(); i += Constantes.Ticket80)
			{
				if (i + Constantes.Ticket80 < nombre.length())
				{
					nombreF = nombreF + nombre.substring(i, i + Constantes.Ticket80) + "\n";
				} else
				{
					nombreF = nombreF + agregarEspacion(nombre.substring(i), Constantes.Ticket80, false);
				}
			}
			itmes = itmes
					+ (detVe.getFicantidad() % 1.0D == 0.0D ? (int) detVe.getFicantidad() : new StringBuilder(String.valueOf(detVe.getFicantidad()))
							.toString()) + " " + nombreF + " "
					+ agregarEspacion(new StringBuilder(String.valueOf(detVe.getFdprecioventa())).toString(), 5, true) + " "
					+ agregarEspacion(new StringBuilder(String.valueOf(detVe.getFdsubtotal())).toString(), 5, true) + "\n";
		}
		this.contentTicket = this.contentTicket.replace("{{items}}", itmes);
		this.contentTicket = this.contentTicket.replace("{{total}}", venta.getFdtotal() + "");
		this.contentTicket = this.contentTicket.replace("{{caja}}", venta.getCaja().getFcdesccaja());
		if (venta.getCliente().getFiidcliente() > Constantes.IDDEDAULT.longValue())
		{
			this.contentTicket = this.contentTicket.replace("{{Cliente}}", venta.getCliente().getFcnombre() + " " + venta.getCliente().getFcapepat()
					+ " " + venta.getCliente().getFcapemat() + "\n");
		} else
		{
			this.contentTicket = this.contentTicket.replace("{{Cliente}}", "");
		}
		System.out.println(this.contentTicket);
	}
	
	public Ticket(String ticket, String dateTime, String items, String total, String caissier, String recibido, String cambio, String caja,
			String lblcambio, String cliente, long idventaante, double diferencia)
	{
		this.contentTicket = this.contentTicket.replace("{{ticket}}", ticket);
		this.contentTicket = this.contentTicket.replace("{{cajero}}", caissier);
		this.contentTicket = this.contentTicket.replace("{{dateTime}}", dateTime);
		this.contentTicket = this.contentTicket.replace("{{items}}", items);
		this.contentTicket = this.contentTicket.replace("{{total}}", total);
		this.contentTicket = this.contentTicket.replace("{{change}}", cambio);
		this.contentTicket = this.contentTicket.replace("{{recibo}}", recibido);
		this.contentTicket = this.contentTicket.replace("{{caja}}", caja);
		this.contentTicket = this.contentTicket.replace("{{Cliente}}", cliente);
		this.contentTicket = this.contentTicket.replace("{{lnlcambio}}", lblcambio);
		String ventaAnterior = "";
		if (idventaante > 0L)
		{
			ventaAnterior = "Venta Anterior: " + idventaante + "\n";
			ventaAnterior = ventaAnterior + "Diferencia entra ventas: " + diferencia + "\n";
		}
		this.contentTicket = this.contentTicket.replace("{{ventaanterior}}", ventaAnterior);
	}
	
	public void print() throws PrintException, FileNotFoundException
	{
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService service = null;
		for (PrintService s : services)
		{
			String impresora = s.toString();
			if (impresora.indexOf("RP58") != -1)
			{
				System.out.println(s);
				service = s;
			}
		}
		byte[] bytes = this.contentTicket.getBytes();
		
		if (service != null)
		{
			DocPrintJob job = service.createPrintJob();
			Object doc2 = new SimpleDoc(bytes, flavor, null);
			job.print((Doc) doc2, null);
		}
	}
	
	private String agregarEspacion(String valor, int cantidad, boolean inicio)
	{
		while (valor.length() < cantidad)
		{
			if (inicio)
			{
				valor = " " + valor;
			} else
			{
				valor = valor + " ";
			}
		}
		if (valor.length() > cantidad)
		{
			valor.substring(0, cantidad);
		}
		return valor;
	}
}