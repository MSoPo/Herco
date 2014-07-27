package ventas.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Venta;

public class ObjetoImprimir implements Printable {
	private Venta venta;
	private String cliente;
	private String domicilio;
	
	
	public ObjetoImprimir(Venta venta, String cliente, String domicilio)
	{
		this.venta = venta;
		this.cliente = cliente;
		this.domicilio = domicilio;
	}

	@Override
	public int print(Graphics arg0, PageFormat arg1, int arg2)
			throws PrinterException {
		
		DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
		df.applyPattern("0.00");
		arg0.setFont(new Font("TimesRoman", Font.BOLD, 8));
		Calendar cal = Calendar.getInstance();
		cal.setTime(venta.getFdfechaventa());
		if(arg2 == 0){
			arg0.drawString(venta.getFiidventa() + "", 450, 140);
			arg0.drawString(cal.get(cal.DAY_OF_MONTH) + "", 210, 160);
			arg0.drawString(Numero_a_Letra.Mes(cal.get(cal.MONTH) + 1) + "", 280, 160);
			arg0.drawString(cal.get(cal.YEAR) + "", 530, 160);
			arg0.drawString(cliente, 115, 173);
			arg0.drawString(domicilio, 120, 195);
			arg0.drawString(venta.getFcobservaciones(), 550, 200);
			int x = 240;
			for(int i = 0; i < venta.getDetalleVenta().size(); i++){
				DetalleVenta dv = venta.getDetalleVenta().get(i);
				arg0.drawString(dv.getFicantidad() + "", 55, x);
				arg0.drawString(dv.getProducto().getMedida().getFcdescmedida(), 103, x);
				arg0.drawString(dv.getProducto().getFcnomproducto() + i, 155, x);
				arg0.drawString("$" + df.format(dv.getFdsubtotal() / dv.getFicantidad()), 511, x);
				arg0.drawString("$" + df.format(dv.getFdsubtotal()), 598, x);
				x+=9;
			}
			arg0.drawString("$" + df.format(venta.getFdtotal()), 590, 400);
			arg0.drawString(Numero_a_Letra.Convertir(df.format(venta.getFdtotal()), false), 55, 410);
			
			return PAGE_EXISTS;
		}
		return NO_SUCH_PAGE;

	}
	
	public static void main(String[] args) {
		System.out.println(new Numero_a_Letra().Convertir("99999.99", false));
		
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		Paper paper = new Paper();
		double margin = 10;
		paper.setImageableArea(margin, margin, paper.getWidth() - margin, paper.getHeight() - margin);
		pf.setPaper(paper);
		pf.setOrientation(PageFormat.LANDSCAPE);
		//job.setPrintable(new ObjetoImprimir(), pf);
		job.setJobName("nombre_de_impresion");
		try{
			if(job.printDialog()){
		job.print();
			}
		}catch(PrinterException e){
		System.out.println(e);
		}
		}
}
