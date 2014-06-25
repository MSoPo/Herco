package control.springmvc.controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.Pago;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.Venta;
import modelo.hibernate.dao.interfaz.CatCajaDAO;
import modelo.hibernate.dao.interfaz.CatMedidaDAO;
import modelo.hibernate.dao.interfaz.CatProveedorDAO;
import modelo.hibernate.dao.interfaz.ClienteDAO;
import modelo.hibernate.dao.interfaz.CompraDAO;
import modelo.hibernate.dao.interfaz.PagoDAO;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import modelo.hibernate.dao.interfaz.UsuarioDAO;
import modelo.hibernate.dao.interfaz.VentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;

@Controller
public class ListarCatalogosController
{
  @Autowired
  private UsuarioDAO usuariodao;
  @Autowired
  private CatProveedorDAO proveedrodao;
  @Autowired
  private ClienteDAO clientedao;
  @Autowired
  private CatCajaDAO cajadao;
  @Autowired
  private CatMedidaDAO medidadao;
  @Autowired
  private PagoDAO pagodao;
  @Autowired
  private ProductoDAO productodao;
  @Autowired
  private VentaDAO ventadao;
  @Autowired
  private CompraDAO compradao;
  
  @RequestMapping({"/listarUsuario.htm"})
  public ModelAndView listarUsuario(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String id = request.getParameter("id");
    if ((id != null) && 
      (!id.equals("Administrador")))
    {
      Usuario usuario = (Usuario)this.usuariodao.obtenPorId(id);
      if (usuario.getFcactivo().equals("1")) {
        usuario.setFcactivo("0");
      } else {
        usuario.setFcactivo("1");
      }
      this.usuariodao.actualiza(usuario);
    }
    String usuario = request.getParameter("usuarioCat");
    String nombre = request.getParameter("nombre");
    List<Usuario> lstUsuario;
    if (usuario != null)
    {
      lstUsuario = this.usuariodao.obtenListaLike("fcusuario", usuario);
      request.setAttribute("usuarioCat", usuario);
    }
    else if (nombre != null)
    {
      lstUsuario = this.usuariodao.obtenListaLike("fcnombre", nombre);
      request.setAttribute("nombre", nombre);
    }
    else
    {
      lstUsuario = this.usuariodao.obtenLista("fcusuario", true);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("usuario", lstUsuario);
    
    return new ModelAndView("listarUsuario", "model", myModel);
  }
  
  @RequestMapping({"/listarClientes.htm"})
  public ModelAndView listarClientes(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String id = request.getParameter("id");
    if (id != null)
    {
      long idL = new Long(id).longValue();
      if (idL != 1L)
      {
        Cliente cliente = (Cliente)this.clientedao.obtenPorId(Long.valueOf(idL));
        if (cliente.getFcactivo().equals("1")) {
          cliente.setFcactivo("0");
        } else {
          cliente.setFcactivo("1");
        }
        this.clientedao.actualiza(cliente);
      }
    }
    String nombre = request.getParameter("nombre");
    String apepat = request.getParameter("apepat");
    List<Cliente> lstCliente;
    if (nombre != null)
    {
      lstCliente = this.clientedao.obtenListaLike("fcnombre", nombre);
      request.setAttribute("nombre", nombre);
    }
    else if (apepat != null)
    {
      lstCliente = this.clientedao.obtenListaLike("fcapepat", apepat);
      request.setAttribute("apepat", apepat);
    }
    else
    {
      lstCliente = this.clientedao.obtenLista("fcnombre", true);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("cliente", lstCliente);
    
    return new ModelAndView("listarClientes", "model", myModel);
  }
  
  @RequestMapping({"/listarProveedor.htm"})
  public ModelAndView listarProveedor(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String id = request.getParameter("id");
    if (id != null)
    {
      long idL = new Long(id).longValue();
      if (idL != 1L)
      {
        CatProveedor proveedor = (CatProveedor)this.proveedrodao.obtenPorId(Long.valueOf(idL));
        if (proveedor.getFcactivo().equals("1")) {
          proveedor.setFcactivo("0");
        } else {
          proveedor.setFcactivo("1");
        }
        this.proveedrodao.actualiza(proveedor);
      }
    }
    String nombre = request.getParameter("nombre");
    List<CatProveedor> lstProveedor;
    if (nombre != null)
    {
      lstProveedor = this.proveedrodao.obtenListaLike("fcnomproveedor", nombre);
      request.setAttribute("nombre", nombre);
    }
    else
    {
      lstProveedor = this.proveedrodao.obtenLista("fcnomproveedor", true);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("proveedor", lstProveedor);
    
    return new ModelAndView("listarProveedor", "model", myModel);
  }
  
  @RequestMapping({"/listarCaja.htm"})
  public ModelAndView listarCaja(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String id = request.getParameter("id");
    if (id != null)
    {
      CatCaja caja = (CatCaja)this.cajadao.obtenPorId(new Long(id));
      if (caja.getFcactivo().equals("1")) {
        caja.setFcactivo("0");
      } else {
        caja.setFcactivo("1");
      }
      this.cajadao.actualiza(caja);
    }
    String nombre = request.getParameter("nombre");
    List<CatCaja> lstCaja;
    if (nombre != null)
    {
      lstCaja = this.cajadao.obtenListaLike("fcdesccaja", nombre);
      request.setAttribute("nombre", nombre);
    }
    else
    {
      lstCaja = this.cajadao.obtenLista("fcdesccaja", true);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("caja", lstCaja);
    
    return new ModelAndView("listarCaja", "model", myModel);
  }
  
  @RequestMapping({"/listarMedida.htm"})
  public ModelAndView listarMedida(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, NumberFormatException, BussinessException
  {
    String id = request.getParameter("id");
    if (id != null)
    {
      CatMedida medida = (CatMedida)this.medidadao.obtenPorId(new Long(id));
      if (medida.getFcactivo().equals("1")) {
        medida.setFcactivo("0");
      } else {
        medida.setFcactivo("1");
      }
      this.medidadao.actualiza(medida);
    }
    String nombre = request.getParameter("nombre");
    List<CatMedida> lstMedida;
    if (nombre != null)
    {
      lstMedida = this.medidadao.obtenListaLike("fcdescmedida", nombre);
      request.setAttribute("nombre", nombre);
    }
    else
    {
      lstMedida = this.medidadao.obtenLista("fcdescmedida", true);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("medida", lstMedida);
    
    return new ModelAndView("listarMedida", "model", myModel);
  }
  
  @RequestMapping({"/listarPagos.htm"})
  public ModelAndView listarPagos(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String nombre = request.getParameter("nombre");
    String apepat = request.getParameter("apepat");
    List<Pago> lstPago;
    if (nombre != null)
    {
      lstPago = this.pagodao.obtenerListaFaltantesLike("cliente.fcnombre", nombre);
      request.setAttribute("nombre", nombre);
    }
    else if (apepat != null)
    {
      lstPago = this.pagodao.obtenerListaFaltantesLike("cliente.fcapepat", apepat);
      request.setAttribute("apepat", apepat);
    }
    else
    {
      lstPago = this.pagodao.obtenerListaFaltantes();
    }
    for (Pago p : lstPago) {
      p.setFdcantidad(this.pagodao.obtenerTotalPagos(Long.valueOf(p.getVenta().getFiidventa())));
    }
    Map<String, Object> myModel = new HashMap();
    
    myModel.put("pagos", lstPago);
    
    return new ModelAndView("listarPagos", "model", myModel);
  }
  
  @RequestMapping({"/listarProductos.htm"})
  public ModelAndView listarProductos(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String id = request.getParameter("id");
    if (id != null)
    {
      Producto producto = (Producto)this.productodao.obtenPorId(id);
      if (producto.getFcactivo().equals("1")) {
        producto.setFcactivo("0");
      } else {
        producto.setFcactivo("1");
      }
      this.productodao.actualiza(producto);
    }
    Map<String, Object> myModel = new HashMap();
    
    String codigo = request.getParameter("codigo");
    String nombre = request.getParameter("nombre");
    List<Producto> lstProdcuto;
    if (codigo != null)
    {
      lstProdcuto = this.productodao.obtenListaPorIdLike(codigo);
      request.setAttribute("codigo", codigo);
    }
    else if (nombre != null)
    {
      lstProdcuto = this.productodao.obtenListaAutocompletado(nombre);
      request.setAttribute("nombre", nombre);
    }
    else
    {
      //lstProdcuto = this.productodao.obtenLista("fcnomproducto", true);
					lstProdcuto = new ArrayList<Producto>(); 
    }
    myModel.put("producto", lstProdcuto);
    
    return new ModelAndView("listarProductos", "model", myModel);
  }
  
  @RequestMapping({"/listarVentas.htm"})
  public ModelAndView listarVentas(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException, ParseException
  {
    String fechainicio = request.getParameter("fechai");
    String fechafin = request.getParameter("fechaf");
    String noventa = request.getParameter("noventa");
    Calendar cal = Calendar.getInstance();
    Date fechaf = cal.getTime();
    cal.add(5, -1);
    Date fechai = cal.getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    long nventa = 0L;
    if (fechainicio != null)
    {
      fechai = format.parse(fechainicio);
      fechaf = format.parse(fechafin);
      try
      {
        nventa = new Long(noventa).longValue();
      }
      catch (Exception localException) {}
    }
    request.setAttribute("fechaf", format.format(fechaf));
    request.setAttribute("fechai", format.format(fechai));
    request.setAttribute("noventa", Long.valueOf(nventa));
    
    List<Venta> lstVenta = null;
    if (nventa > 0L)
    {
      lstVenta = new ArrayList();
      Venta venta = (Venta)this.ventadao.obtenPorId(Long.valueOf(nventa));
      if (venta != null) {
        lstVenta.add(venta);
      }
    }
    else
    {
      lstVenta = this.ventadao.obtenerVentasFechas(fechai, fechaf);
    }
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstVenta", lstVenta);
    if (request.getParameter("excel") != null) {
      return new ModelAndView("excel/ventasExcel", "model", myModel);
    }
    return new ModelAndView("listarVentas", "model", myModel);
  }
  
  @RequestMapping({"/listarCompras.htm"})
  public ModelAndView listarCompras(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException, ParseException
  {
    String fechainicio = request.getParameter("fechai");
    String fechafin = request.getParameter("fechaf");
    Calendar cal = Calendar.getInstance();
    Date fechaf = cal.getTime();
    cal.add(2, -1);
    Date fechai = cal.getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    if (fechainicio != null)
    {
      fechai = format.parse(fechainicio);
      fechaf = format.parse(fechafin);
    }
    request.setAttribute("fechaf", format.format(fechaf));
    request.setAttribute("fechai", format.format(fechai));
    List<Compra> lstVenta = this.compradao.obtenerComprasFechas(fechai, fechaf);
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstCompra", lstVenta);
    
    return new ModelAndView("listarCompras", "model", myModel);
  }
  
  @RequestMapping({"/cambiarVenta.htm"})
  public ModelAndView cambiarVenta(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException, ParseException
  {
    String idventa = request.getParameter("idventa");
    long idv = 1L;
    
    Calendar cal = Calendar.getInstance();
    cal.add(5, -10);
    Date fechacaduca = cal.getTime();
    Map<String, Object> myModel = new HashMap();
    if (idventa != null)
    {
      idv = new Long(idventa).longValue();
      myModel.put("idventa", Long.valueOf(idv));
      Venta venta = (Venta)this.ventadao.obtenPorId(Long.valueOf(idv));
      if (venta != null)
      {
        if (venta.getFcactivo().equals("0"))
        {
          myModel.put("message", "Esta venta ya esta cancelada");
          return new ModelAndView("cambiarVenta", "model", myModel);
        }
        if (venta.getFdfechaventa().after(fechacaduca)) {
          return new ModelAndView("redirect:/venta.htm?idventa=" + idventa);
        }
        myModel.put("message", "Fecha vencida para cambios");
        return new ModelAndView("cambiarVenta", "model", myModel);
      }
      myModel.put("message", "No existe la venta");
      return new ModelAndView("cambiarVenta", "model", myModel);
    }
    myModel.put("idventa", Long.valueOf(idv));
    return new ModelAndView("cambiarVenta", "model", myModel);
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