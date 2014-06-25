package control.springmvc.controlador;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.print.PrintException;
import javax.servlet.http.HttpServletRequest;
import modelo.hibernate.beans.CatCaja;
import modelo.hibernate.beans.CatMedida;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.Cliente;
import modelo.hibernate.beans.DetalleCompra;
import modelo.hibernate.beans.DetalleVenta;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ventas.util.BussinessException;
import ventas.util.Impresion;
import ventas.util.JsonResponse;
import ventas.util.Ticket;

@Controller
public class AjaxController
{
	
	@Autowired
	private ProductoDAO		productodao;
	
	@Autowired
	private UsuarioDAO		usauriodao;
	
	@Autowired
	private ClienteDAO		clientedao;
	
	@Autowired
	private CatProveedorDAO	proveedordao;
	
	@Autowired
	private CatMedidaDAO	medidadao;
	
	@Autowired
	private CatCajaDAO		cajadao;
	@Autowired
	private PagoDAO			pagodao;
	
	@Autowired
	private VentaDAO		ventadao;
	
	@Autowired
	private CompraDAO		compradao;
	
	@RequestMapping(value =
	{ "/buscarcodigo" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public JsonResponse buscarPorCodigo(@ModelAttribute("codigo") String id, BindingResult result)
	{
		List<Producto> lstProd = productodao.obtenerPorCompoString("fiidproducto", id, "1");
		Producto p = lstProd.size() > 0 ? (Producto) lstProd.get(0) : new Producto();
		
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		res.setResult(p);
		
		return res;
	}
	
	@RequestMapping(value =
	{ "/buscarPagos" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public JsonResponse buscarPagos(@ModelAttribute("codigo") String id, BindingResult result)
	{
		List<Pago> lstProd = this.pagodao.obtenerPorCompoString("venta.fiidventa", id);
		
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		try
		{
			Pago[] lstpago = new Pago[lstProd.size()];
			for (int i = 0; i < lstpago.length; i++)
			{
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				lstpago[i] = new Pago();
				lstpago[i].setFdcantidad(((Pago) lstProd.get(i)).getFdcantidad());
				lstpago[i].setFcobservacion(((Pago) lstProd.get(i)).getFcobservacion());
				lstpago[i].setFcactivo(formato.format(((Pago) lstProd.get(i)).getFdfechamodifica()));
				lstpago[i].setFcusuariomodifica(((Pago) lstProd.get(i)).getUsuario().getFcusuario());
			}
			res.setResult(lstpago);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value =
	{ "/buscarVentas" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public JsonResponse buscarVentas(@ModelAttribute("codigo") String id, BindingResult result)
	{
		List<DetalleVenta> lstProd = this.ventadao.obtenerDetalleVenta(new Long(id).longValue());
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		try
		{
			DetalleVenta[] lstpago = new DetalleVenta[lstProd.size()];
			for (int i = 0; i < lstpago.length; i++)
			{
				lstpago[i] = new DetalleVenta();
				lstpago[i].setFicantidad(((DetalleVenta) lstProd.get(i)).getFicantidad());
				lstpago[i].setFdsubtotal(((DetalleVenta) lstProd.get(i)).getFdsubtotal());
				lstpago[i].setFdprecioventa(((DetalleVenta) lstProd.get(i)).getFdprecioventa());
				lstpago[i].setProducto(new Producto());
				lstpago[i].getProducto().setFcnomproducto(((DetalleVenta) lstProd.get(i)).getProducto().getFcnomproducto());
			}
			res.setResult(lstpago);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value =
	{ "/imprimirticket" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public JsonResponse imprimirticket(@ModelAttribute("codigo") String id, BindingResult result) throws NumberFormatException, BussinessException,
			PrintException, FileNotFoundException
	{
		Venta venta = (Venta) this.ventadao.obtenPorId(new Long(id));
		List<DetalleVenta> lstProd = this.ventadao.obtenerDetalleVenta(new Long(id).longValue());
		venta.setDetalleVenta(lstProd);
		
		Impresion.impresionTicket(venta);
		
		/*Ticket ticket = new Ticket(venta);
		ticket.print();*/
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		return res;
	}
	
	@RequestMapping(value =
	{ "/buscarCompras" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public JsonResponse buscarCompras(@ModelAttribute("codigo") String id, BindingResult result)
	{
		List<DetalleCompra> lstProd = this.compradao.obtenerDetalleCompra(new Long(id).longValue());
		
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		try
		{
			DetalleCompra[] lstpago = new DetalleCompra[lstProd.size()];
			for (int i = 0; i < lstpago.length; i++)
			{
				lstpago[i] = new DetalleCompra();
				lstpago[i].setFicantidad(((DetalleCompra) lstProd.get(i)).getFicantidad());
				lstpago[i].setFdsubtotal(((DetalleCompra) lstProd.get(i)).getFdsubtotal());
				lstpago[i].setFdpreciocompra(((DetalleCompra) lstProd.get(i)).getFdpreciocompra());
				lstpago[i].setProducto(new Producto());
				lstpago[i].getProducto().setFcnomproducto(((DetalleCompra) lstProd.get(i)).getProducto().getFcnomproducto());
			}
			res.setResult(lstpago);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value =
	{ "/buscarNombre" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public JsonResponse buscarPorNomber(@ModelAttribute("codigo") String codigo, BindingResult result)
	{
		Producto p = null;
		List<Producto> lstProd = this.productodao.obtenerPorCompoString("fcnomproducto", codigo, "1");
		p = lstProd.size() > 0 ? (Producto) lstProd.get(0) : new Producto();
		
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		res.setResult(p);
		return res;
	}
	
	@RequestMapping(
	{ "/autocompletarProdActivos" })
	@ResponseBody
	public List<String> autocompeltarNombreActivos(@ModelAttribute("term") String codigo, BindingResult result)
	{
		System.out.println(codigo);
		List<Producto> ps = this.productodao.obtenListaAutocompletado(codigo, "1");
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (Producto p : ps)
			{
				ls.add(p.getFcnomproducto());
			}
		}
		return ls;
	}
	
	@RequestMapping(
	{ "/autocompletarProd" })
	@ResponseBody
	public List<String> autocompeltarNombre(@ModelAttribute("term") String codigo, BindingResult result)
	{
		System.out.println(codigo);
		List<Producto> ps = this.productodao.obtenListaAutocompletado(codigo);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (Producto p : ps)
			{
				ls.add(p.getFcnomproducto());
			}
		}
		return ls;
	}
	
	@RequestMapping(
	{ "/autocompletarUsuario" })
	@ResponseBody
	public List<String> autocompeltarUsuario(@ModelAttribute("term") String codigo, BindingResult result, HttpServletRequest request)
	{
		String campo = request.getParameter("campo");
		List<Usuario> ps = this.usauriodao.obtenListaLike(campo, codigo);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (Usuario p : ps)
			{
				if (campo.equals("fcusuario"))
				{
					ls.add(p.getFcusuario());
				} else if (campo.equals("fcnombre"))
				{
					ls.add(p.getFcnombre());
				}
			}
		}
		return ls;
	}
	
	@RequestMapping(value =
	{ "/autocompletarCliente" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> autocompletarCliente(@ModelAttribute("term") String valor, BindingResult result, HttpServletRequest request)
	{
		String campo = request.getParameter("campo");
		List<Cliente> ps = this.clientedao.obtenListaLike(campo, valor);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (Cliente p : ps)
			{
				if (campo.equals("fcnombre"))
				{
					ls.add(p.getFcnombre());
				} else if (campo.equals("fcapepat"))
				{
					ls.add(p.getFcapepat());
				}
			}
		}
		return ls;
	}
	
	@RequestMapping(value =
	{ "/autocompletarProveedor" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> autocompletarProveedor(@ModelAttribute("term") String valor, BindingResult result, HttpServletRequest request)
	{
		String campo = request.getParameter("campo");
		List<CatProveedor> ps = this.proveedordao.obtenListaLike(campo, valor);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (CatProveedor p : ps)
			{
				if (campo.equals("fcnomproveedor"))
				{
					ls.add(p.getFcnomproveedor());
				}
			}
		}
		return ls;
	}
	
	@RequestMapping(value =
	{ "/autocompletarMedida" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> autocompletarMedida(@ModelAttribute("term") String valor, BindingResult result, HttpServletRequest request)
	{
		String campo = request.getParameter("campo");
		List<CatMedida> ps = this.medidadao.obtenListaLike(campo, valor);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (CatMedida p : ps)
			{
				if (campo.equals("fcdescmedida"))
				{
					ls.add(p.getFcdescmedida());
				}
			}
		}
		return ls;
	}
	
	@RequestMapping(value =
	{ "/autocompletarCaja" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> autocompletarCaja(@ModelAttribute("term") String valor, BindingResult result, HttpServletRequest request)
	{
		String campo = request.getParameter("campo");
		List<CatCaja> ps = this.cajadao.obtenListaLike(campo, valor);
		List<String> ls = new ArrayList();
		if (ps.size() > 0)
		{
			for (CatCaja p : ps)
			{
				if (campo.equals("fcdesccaja"))
				{
					ls.add(p.getFcdesccaja());
				}
			}
		}
		return ls;
	}
	
	@RequestMapping(value =
	{ "/buscarUsuario" }, method =
	{ org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public JsonResponse buscarUsuarior(@ModelAttribute("fcusuario") String fcusuario, BindingResult result) throws BussinessException
	{
		JsonResponse res = new JsonResponse();
		if (!result.hasErrors())
		{
			res.setStatus("SUCCESS");
		} else
		{
			res.setStatus("FAIL");
		}
		if (this.usauriodao.obtenPorId(fcusuario) != null)
		{
			res.setResult(new Boolean(true));
		} else
		{
			res.setResult(new Boolean(false));
		}
		return res;
	}
}