package control.springmvc.controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.hibernate.beans.Pago;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Venta;
import modelo.hibernate.dao.interfaz.PagoDAO;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import modelo.hibernate.dao.interfaz.VentaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ventas.util.BussinessException;

@Controller
public class GraficosController
{
  @Autowired
  private VentaDAO ventadao;
  @Autowired
  private PagoDAO pagodao;
  @Autowired
  private ProductoDAO productodao;
  
  @RequestMapping({"/stock.htm"})
  public ModelAndView listarUsuario(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String cantidad = request.getParameter("cantidad");
    String operacion = request.getParameter("operacion");
    int cant = 10;
    boolean mayor = false;
    if ((cantidad != null) && (!cantidad.trim().equals("")))
    {
      cant = Integer.parseInt(cantidad);
      if (operacion.equals("2")) {
        mayor = true;
      }
      request.setAttribute("cantidad", cantidad);
      request.setAttribute("operacion", operacion);
    }
    List<Producto> lstProd = this.productodao.obtenerStockBajo(cant, mayor);
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstProd", lstProd);
    if (request.getParameter("excel") != null) {
      return new ModelAndView("excel/stockExcel", "model", myModel);
    }
    return new ModelAndView("stock", "model", myModel);
  }
  
  @RequestMapping({"/productosVentas.htm"})
  public ModelAndView productosVentas(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException, ParseException
  {
    String fechainicio = request.getParameter("fechai");
    String fechafin = request.getParameter("fechaf");
    String cantidad = request.getParameter("cantidad");
    String operacion = request.getParameter("operacion");
    boolean vendidos = false;
    Calendar cal = Calendar.getInstance();
    Date fechaf = cal.getTime();
    cal.add(2, -1);
    Date fechai = cal.getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int cant = 10;
    if ((fechainicio != null) && (!fechainicio.trim().equals("")))
    {
      fechai = format.parse(fechainicio);
      fechaf = format.parse(fechafin);
      if (!cantidad.equals("")) {
        cant = Integer.parseInt(cantidad);
      }
      if (operacion.equals("2")) {
        vendidos = true;
      }
      request.setAttribute("operacion", operacion);
    }
    request.setAttribute("fechaf", format.format(fechaf));
    request.setAttribute("fechai", format.format(fechai));
    request.setAttribute("cantidad", Integer.valueOf(cant));
    
    List<Producto> lstProd = this.productodao.obtenerVendidos(fechai, fechaf, cant, vendidos);
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstProd", lstProd);
    if (request.getParameter("excel") != null) {
      return new ModelAndView("excel/stockExcel", "model", myModel);
    }
    return new ModelAndView("listaVentas", "model", myModel);
  }
  
  @RequestMapping({"/clientesVentas.htm"})
  public ModelAndView clientesVentas(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException, ParseException
  {
    String fechainicio = request.getParameter("fechai");
    String fechafin = request.getParameter("fechaf");
    String cantidad = request.getParameter("cantidad");
    Calendar cal = Calendar.getInstance();
    Date fechaf = cal.getTime();
    cal.add(1, -1);
    Date fechai = cal.getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    int cant = 10;
    if ((fechainicio != null) && (!fechainicio.trim().equals("")))
    {
      fechai = format.parse(fechainicio);
      fechaf = format.parse(fechafin);
      if (!cantidad.equals("")) {
        cant = Integer.parseInt(cantidad);
      }
    }
    request.setAttribute("fechaf", format.format(fechaf));
    request.setAttribute("fechai", format.format(fechai));
    request.setAttribute("cantidad", Integer.valueOf(cant));
    
    List<Venta> lstVenta = this.ventadao.obtenerVentaCliente(fechai, fechaf, cant);
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstVenta", lstVenta);
    
    return new ModelAndView("listarCliente", "model", myModel);
  }
  
  @RequestMapping({"/pagosPendientes.htm"})
  public ModelAndView pagosPendientes(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String cantidad = request.getParameter("cantidad");
    
    int cant = 10;
    if ((cantidad != null) && 
      (!cantidad.equals(""))) {
      cant = Integer.parseInt(cantidad);
    }
    request.setAttribute("cantidad", Integer.valueOf(cant));
    List<Pago> lstPago = this.pagodao.obtenerListaFaltantesGrafica(cant);
    Map<String, Object> myModel = new HashMap();
    myModel.put("lstPago", lstPago);
    
    return new ModelAndView("pagosPendientes", "model", myModel);
  }
				
  @RequestMapping({"/ventasMensuales.htm"})
  public ModelAndView ventasMensuales(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String mes = request.getParameter("mes");
    String ano = request.getParameter("ano");
   
	request.setAttribute("mes", mes);
	request.setAttribute("ano", ano);
	List list = ventadao.obtenerVentaDiaria(Integer.parseInt(mes), Integer.parseInt(ano));
    Map<String, Object> myModel = new HashMap();
    String lst = new Gson().toJson(list);
    myModel.put("lstVentas", lst);
    
    return new ModelAndView("listarVentasMensuales", "model", myModel);
  }
  
  @RequestMapping({"/ventasAnuales.htm"})
  public ModelAndView ventasAnuales(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, BussinessException
  {
    String ano = request.getParameter("ano");
   
	request.setAttribute("ano", ano);
	List list = ventadao.obtenerVentaMensual(Integer.parseInt(ano));
    Map<String, Object> myModel = new HashMap();
    String lst = new Gson().toJson(list);
    myModel.put("lstVentas", lst);
    
    return new ModelAndView("listarVentasAnuales", "model", myModel);
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