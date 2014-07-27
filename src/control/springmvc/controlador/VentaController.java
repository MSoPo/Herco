package control.springmvc.controlador;

import control.springmvc.form.VentaForm;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.print.PrintException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.DetalleVenta;
import modelo.hibernate.beans.Pago;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.beans.Venta;
import modelo.hibernate.dao.interfaz.ClienteDAO;
import modelo.hibernate.dao.interfaz.PagoDAO;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import modelo.hibernate.dao.interfaz.VentaDAO;
import negocio.delegate.VentaDelegate;
import net.sourceforge.jbarcodebean.BarcodeException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;
import ventas.util.Constantes;
import ventas.util.Impresion;

@Controller
@RequestMapping({"/venta.htm"})
public class VentaController
{
  final Logger logger = Logger.getLogger(VentaController.class);
  @Autowired
  private VentaDAO dao;
  @Autowired
  private ProductoDAO productodao;
  @Autowired
  private ClienteDAO clientedao;
  @Autowired
  private PagoDAO pagodao;
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String onSubmit(@ModelAttribute VentaForm ventaform, BindingResult result, HttpServletRequest request)
    throws BussinessException, IOException, BarcodeException, PrintException
  {
    HttpSession session = request.getSession();
    VentaDelegate delegate = new VentaDelegate();
    List<DetalleVenta> listaDetalle = null;
    Usuario usuario = ((UsuarioXRol)session.getAttribute("usuarioxrol")).getUsuario();
    CatCaja caja = (CatCaja)session.getAttribute("caja");

    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
    df.applyPattern("0.00");

    if (ventaform.getVenta().getCliente() == null)
    {
      List<Cliente> lstClientes = (ArrayList)session.getAttribute("lstCliente");
      Cliente cliente = new Cliente();
      if (lstClientes.size() == 1) {
        cliente.setFiidcliente(((Cliente)lstClientes.get(0)).getFiidcliente());
      } else {
        cliente.setFiidcliente(Constantes.IDDEDAULT.longValue());
      }
      ventaform.getVenta().setCliente(cliente);
    }
    Cliente cliente = (Cliente)this.clientedao.obtenPorId(Long.valueOf(ventaform.getVenta().getCliente().getFiidcliente()));
    
    ventaform.getVenta().setCliente(cliente);
    if (session.getAttribute("detalleVenta") == null) {
      listaDetalle = new ArrayList();
    } else {
      listaDetalle = (List)session.getAttribute("detalleVenta");
    }
    Producto p;
    String lblCambio;
    if (ventaform.getAccion() == 1)
    {
      boolean existe = false;
      for (DetalleVenta dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(ventaform.getProducto().getFiidproducto()))
        {
          if (dv.getProducto().getFicantidad() >= dv.getFicantidad() + 1.0D) {
            dv.setFicantidad(dv.getFicantidad() + 1.0D);
          }
          existe = true;
          break;
        }
      }
      if (!existe)
      {
        p = (Producto)this.productodao.obtenPorId(ventaform.getProducto().getFiidproducto());
        if (p == null)
        {
          result.addError(new FieldError("error", "producto.fiidproducto", "No existe el producto"));
          return "venta";
        }
        if (p.getFicantidad() <= 0.0D)
        {
          result.addError(new FieldError("error", "producto.fiidproducto", "Producto agotado"));
          return "venta";
        }
        if (p.getFcactivo().equals("0"))
        {
          result.addError(new FieldError("error", "producto.fiidproducto", "Producto bloqueado"));
          return "venta";
        }
        DetalleVenta dv = new DetalleVenta(ventaform.getVenta(), p, 1.0D, p.getFdpreciounitario(), p.getFdpreciounitario());
        listaDetalle.add(0, dv);
      }
    }
    else if (ventaform.getAccion() == 2)
    {
      for (DetalleVenta dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(ventaform.getProducto().getFiidproducto())) {
          if (dv.getProducto().getFicantidad() >= ventaform.getProducto().getFicantidad())
          {
            if (ventaform.getProducto().getFicantidad() <= 0.0D) {
              ventaform.getProducto().setFicantidad(1.0D);
            }
            dv.setFicantidad(ventaform.getProducto().getFicantidad());
          }
          else
          {
            dv.setFicantidad(dv.getProducto().getFicantidad());
          }
        }
      }
    }
    else if (ventaform.getAccion() == 3)
    {
      for (DetalleVenta dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(ventaform.getProducto().getFiidproducto()))
        {
          listaDetalle.remove(dv);
          break;
        }
      }
    }
    else if (ventaform.getAccion() == 4)
    {
      if (ventaform.getVenta().getFiidventaanterior() > 0L) {
        return "redirect:/venta.htm";
      }
      listaDetalle.clear();
    }
    else if ((ventaform.getAccion() == 5) || (ventaform.getAccion() == 7) || (ventaform.getAccion() == 6))
    {
      String observacion = "CONTADO";
      if (ventaform.getVenta().getFiidventaanterior() > 0L) {
        observacion = "Cambio de la Venta " + ventaform.getVenta().getFiidventaanterior();
      }
      lblCambio = "Cambio";
      /*String tckCliente = "";
      if (cliente.getFiidcliente() != Constantes.IDDEDAULT.longValue())
      {
        tckCliente = "Cliente: " + cliente.getFcnombre() + " " + cliente.getFcapepat() + " " + cliente.getFcapemat() + "\n";
        if (cliente.getFddescuento() > 0.0D) {
          tckCliente = tckCliente + "Descuento: " + cliente.getFddescuento() + "\n";
        }
      }*/
      if (ventaform.getAccion() == 7)
      {
        lblCambio = "Pendiente";
        ventaform.setRecibido(ventaform.getPago().getFdcantidad());
        ventaform.setCambio(ventaform.getVenta().getFdtotal() - ventaform.getPago().getFdcantidad());
        observacion = "Venta a Pagos";
      }
      ventaform.getVenta().setFiidventa(((Long)this.dao.guarda(delegate.nuevaVenta(ventaform.getVenta(), listaDetalle, usuario, cliente, caja, observacion))).longValue());
      request.getSession().setAttribute("lstCliente", this.clientedao.obtenerPorCompoString("fcactivo", "1", "1", "fcnombre", true));
      ventaform.getVenta().setFiidventaanterior(0L);
      for (DetalleVenta lst : listaDetalle)
      {
        lst.getProducto().setFicantidad(lst.getProducto().getFicantidad() - lst.getFicantidad());
        this.productodao.actualiza(lst.getProducto());
      }
      if ((ventaform.getAccion() == 6) || (ventaform.getAccion() == 7))
      {
    	  /*SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String itmes = "";
        for (DetalleVenta detVe : listaDetalle)
        {
          String nombre = detVe.getProducto().getFcnomproducto();
          String nombreF = "";
          for (int i = 0; i < nombre.length(); i += 16) {
            if (i + 16 < nombre.length()) {
              nombreF = nombreF + nombre.substring(i, i + 16) + "\n";
            } else {
              nombreF = nombreF + delegate.agregarEspacion(nombre.substring(i), 16, false);
            }
          }
          itmes = itmes + (detVe.getFicantidad() % 1.0D == 0.0D ? (int)detVe.getFicantidad() : new StringBuilder(String.valueOf(detVe.getFicantidad())).toString()) + " " + nombreF + " " + delegate.agregarEspacion(new StringBuilder(String.valueOf(detVe.getFdprecioventa())).toString(), 5, true) + " " + delegate.agregarEspacion(new StringBuilder(String.valueOf(detVe.getFdsubtotal())).toString(), 5, true) + "\n";
        }*/
    	
    	  if(ventaform.getImpresion() == 0)
    		  Impresion.impresionTicket(ventaform.getVenta(), ventaform.getCambio()+"", ventaform.getRecibido()+"", lblCambio, ventaform.getDiferenciaCambioVenta());
    	  else
    		  Impresion.ImpresionNota(ventaform.getVenta(), ventaform.getNombre(), ventaform.getDomicilio());
        /*Ticket ticket = new Ticket(ventaform.getVenta().getFiidventa() + "", formato.format(new Date()), itmes, ventaform.getVenta().getFdtotal() + "", usuario.getFcnombre() + " " + 
          usuario.getFcapepat(), ventaform.getRecibido()+"", ventaform.getCambio()+"", caja.getFcdesccaja(), lblCambio, tckCliente, ventaform.getVenta().getFiidventaanterior(), 
          ventaform.getDiferenciaCambioVenta());
        ticket.print();*/
        if (ventaform.getAccion() == 7)
        {
          Pago pago = ventaform.getPago();
          pago.setFcactivo("1");
          pago.setFcusuariomodifica(usuario.getFcusuario());
          if ((pago.getFcobservacion() == null) || (pago.getFcobservacion().trim().equals(""))) {
            pago.setFcobservacion("Primer pago de la venta");
          }
          pago.setFdfechamodifica(new Date());
          pago.setUsuario(usuario);
          pago.setVenta(ventaform.getVenta());
          this.pagodao.guarda(pago);
        }
      }
      listaDetalle.clear();
    }
    for (DetalleVenta lst : listaDetalle)
    {
      double subtotal = delegate.calcularSubtotal(lst.getProducto(), cliente, lst.getFicantidad());
      lst.setFdprecioventa(Double.parseDouble(df.format(subtotal / lst.getFicantidad())));
      lst.setFdsubtotal(subtotal);
    }
    ventaform.getVenta().setFdtotal(delegate.calcularTotal(listaDetalle, cliente));
    if (ventaform.getVenta().getFiidventaanterior() > 0L) {
      ventaform.setDiferenciaCambioVenta(ventaform.getVenta().getFdtotal() - ventaform.getRecibido());
    }
    ventaform.setProducto(new Producto());
    session.setAttribute("detalleVenta", listaDetalle);
    
    return "venta";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected VentaForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
	DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(new Locale("es", "MX"));
	df.applyPattern("0.00");
    request.getSession().setAttribute("lstCliente", this.clientedao.obtenerPorCompoString("fcactivo", "1", "1", "fcnombre", true));
    VentaForm ventaform = new VentaForm();
    HttpSession session = request.getSession();
    VentaDelegate delegate;
    if (session.getAttribute("detalleVenta") != null)
    {
      Long fiidcliente = Long.valueOf(1L);
      if ((request.getParameter("fiidcliente") != null) && (!request.getParameter("fiidcliente").trim().equals(""))) {
        fiidcliente = new Long(request.getParameter("fiidcliente"));
      }
      Cliente cliente = (Cliente)this.clientedao.obtenPorId(fiidcliente);
      ventaform.getVenta().setCliente(cliente);
      if (session.getAttribute("detalleVenta") != null)
      {
        List<DetalleVenta> listaDetalle = (List)session.getAttribute("detalleVenta");
        delegate = new VentaDelegate();
        for (DetalleVenta lst : listaDetalle)
        {
          double subtotal = delegate.calcularSubtotal(lst.getProducto(), cliente, lst.getFicantidad());
          lst.setFdprecioventa(Double.parseDouble(df.format(subtotal / lst.getFicantidad())));
          lst.setFdsubtotal(subtotal);
        }
        ventaform.getVenta().setFdtotal(delegate.calcularTotal(listaDetalle, cliente));
      }
    }
    if (request.getParameter("idventa") != null)
    {
      Venta venta = (Venta)this.dao.obtenPorId(new Long(request.getParameter("idventa")));
      if (venta.getFcactivo().equals("1"))
      {
        venta.setFcactivo("0");
        venta.setFcusuariomodifica(((UsuarioXRol)session.getAttribute("usuarioxrol")).getUsuario().getFcusuario());
        venta.setFcobservaciones("Venta Cancelada");
        venta.setFdfechamodifica(new Date());
        this.dao.actualiza(venta);
        ventaform.setVenta(venta);
        List<DetalleVenta> detalle = this.dao.obtenerDetalleVenta(venta.getFiidventa());
        session.setAttribute("detalleVenta", detalle);
        ventaform.getVenta().setFiidventaanterior(venta.getFiidventa());
        ventaform.setRecibido(venta.getFdtotal());
        ventaform.getVenta().setFiidventa(0L);
        for (DetalleVenta dv : detalle)
        {
          Producto producto = dv.getProducto();
          producto.setFicantidad(producto.getFicantidad() + dv.getFicantidad());
          producto.setFcusuariomodifica(((UsuarioXRol)session.getAttribute("usuarioxrol")).getUsuario().getFcusuario());
          producto.setFdfechamodifica(new Date());
          this.productodao.actualiza(producto);
        }
        List<Cliente> lstCliente = new ArrayList();
        lstCliente.add(venta.getCliente());
        request.getSession().setAttribute("lstCliente", lstCliente);
      }
    }
    else
    {
      session.setAttribute("detalleVenta", null);
    }
    ventaform.getVenta().setCliente(new Cliente());
    ventaform.getVenta().getCliente().setFiidcliente(2L);
    ventaform.getVenta().getCliente().setFcnombre("Boro");
    return ventaform;
  }
  
  @ExceptionHandler({Exception.class})
  public ModelAndView Exception(Exception e, HttpServletRequest request)
  {
    Map<String, Object> myModel = new HashMap();
    myModel.put("error", e.getMessage());
    myModel.put("causa", e.getCause());
    myModel.put("clase", getClass());
    e.printStackTrace();
    return new ModelAndView("error", "model", myModel);
  }
}