package control.springmvc.controlador;

import control.springmvc.form.CompraForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.hibernate.beans.CatProveedor;
import modelo.hibernate.beans.Compra;
import modelo.hibernate.beans.DetalleCompra;
import modelo.hibernate.beans.Producto;
import modelo.hibernate.beans.Usuario;
import modelo.hibernate.beans.UsuarioXRol;
import modelo.hibernate.dao.interfaz.CatProveedorDAO;
import modelo.hibernate.dao.interfaz.CompraDAO;
import modelo.hibernate.dao.interfaz.ProductoDAO;
import negocio.delegate.CompraDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ventas.util.BussinessException;
import ventas.util.Constantes;

@Controller
@RequestMapping({"/compra.htm"})
public class CompraController
{
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private CompraDAO dao;
  @Autowired
  private ProductoDAO productodao;
  @Autowired
  private CatProveedorDAO proveedrodao;
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String onSubmit(@ModelAttribute CompraForm compraform, BindingResult result, HttpServletRequest request)
    throws BussinessException
  {
    HttpSession session = request.getSession();
    CompraDelegate delegate = new CompraDelegate();
    List<DetalleCompra> listaDetalle = null;
    Usuario usuario = ((UsuarioXRol)session.getAttribute("usuarioxrol")).getUsuario();
    if (compraform.getCompra().getCatproveedor() == null)
    {
      CatProveedor proveedor = new CatProveedor();
      proveedor.setFiidproveedor(Constantes.IDDEDAULT.longValue());
      compraform.getCompra().setCatproveedor(proveedor);
    }
    CatProveedor proveedor = (CatProveedor)this.proveedrodao.obtenPorId(Long.valueOf(compraform.getCompra().getCatproveedor().getFiidproveedor()));
    compraform.getCompra().setCatproveedor(proveedor);
    if (session.getAttribute("detalleCompra") == null) {
      listaDetalle = new ArrayList();
    } else {
      listaDetalle = (List)session.getAttribute("detalleCompra");
    }
    Producto p;
    if (compraform.getAccion() == 1)
    {
      boolean existe = false;
      for (DetalleCompra dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(compraform.getProducto().getFiidproducto()))
        {
          dv.setFicantidad(dv.getFicantidad() + 1.0D);
          existe = true;
          break;
        }
      }
      if (!existe)
      {
        p = (Producto)this.productodao.obtenPorId(compraform.getProducto().getFiidproducto());
        if (p == null)
        {
          result.addError(new FieldError("error", "producto.fiidproducto", this.messageSource.getMessage("error.fiidproducto.noexiste", null, null)));
          request.setAttribute("lstProveedor", this.proveedrodao.obtenerPorCompoString("fcactivo", "1", "1", "fcnomproveedor", true));
          return "compra";
        }
        if (p.getFcactivo().equals("0"))
        {
          result.addError(new FieldError("error", "producto.fiidproducto", this.messageSource.getMessage("error.fiidproducto.bloqueado", null, null)));
          request.setAttribute("lstProveedor", this.proveedrodao.obtenerPorCompoString("fcactivo", "1", "1", "fcnomproveedor", true));
          return "compra";
        }
        p = (Producto)this.productodao.obtenPorId(compraform.getProducto().getFiidproducto());
        DetalleCompra dv = new DetalleCompra(compraform.getCompra(), p, 1.0D, p.getFdpreciocompra(), p.getFdpreciocompra());
        listaDetalle.add(dv);
      }
    }
    else if (compraform.getAccion() == 2)
    {
      for (DetalleCompra dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(compraform.getProducto().getFiidproducto()))
        {
          if (compraform.getProducto().getFicantidad() <= 0.0D) {
            compraform.getProducto().setFicantidad(1.0D);
          }
          dv.setFicantidad(compraform.getProducto().getFicantidad());
        }
      }
    }
    else if (compraform.getAccion() == 3)
    {
      for (DetalleCompra dv : listaDetalle) {
        if (dv.getProducto().getFiidproducto().equals(compraform.getProducto().getFiidproducto()))
        {
          listaDetalle.remove(dv);
          break;
        }
      }
    }
    else if (compraform.getAccion() == 4)
    {
      listaDetalle.clear();
    }
    else if (compraform.getAccion() == 5)
    {
      this.dao.guarda(delegate.nuevaCompra(compraform.getCompra(), listaDetalle, usuario, proveedor));
      for (DetalleCompra lst : listaDetalle)
      {
        lst.getProducto().setFicantidad(lst.getProducto().getFicantidad() + lst.getFicantidad());
        this.productodao.actualiza(lst.getProducto());
      }
      listaDetalle.clear();
    }
    for (DetalleCompra lst : listaDetalle)
    {
      double subtotal = delegate.calcularSubtotal(lst.getProducto(), lst.getFicantidad());
      lst.setFdpreciocompra(subtotal / lst.getFicantidad());
      lst.setFdsubtotal(subtotal);
    }
    compraform.getCompra().setFdtotal(delegate.calcularTotal(listaDetalle));
    
    compraform.setProducto(new Producto());
    request.setAttribute("lstProveedor", this.proveedrodao.obtenerPorCompoString("fcactivo", "1", "1", "fcnomproveedor", true));
    session.setAttribute("detalleCompra", listaDetalle);
    
    return "compra";
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  protected CompraForm formBackingObject(HttpServletRequest request)
    throws ServletException, BussinessException
  {
    request.setAttribute("lstProveedor", this.proveedrodao.obtenerPorCompoString("fcactivo", "1", "1", "fcnomproveedor", true));
    HttpSession session = request.getSession();
    CompraForm compraform = new CompraForm();
    if (session.getAttribute("detalleCompra") != null)
    {
      Long idproveedro = Long.valueOf(1L);
      if ((request.getParameter("fiidproveedor") != null) && (!request.getParameter("fiidproveedor").trim().equals(""))) {
        idproveedro = new Long(request.getParameter("fiidproveedor"));
      }
      compraform.getCompra().setCatproveedor((CatProveedor)this.proveedrodao.obtenPorId(idproveedro));
      
      String idprod = request.getParameter("fiidproducto");
      CompraDelegate delegate = new CompraDelegate();
      List<DetalleCompra> detallecompra = (List)request.getSession().getAttribute("detalleCompra");
      for (DetalleCompra lst : detallecompra)
      {
        if ((idprod != null) && (!idprod.trim().equals("")) && 
          (lst.getProducto().getFiidproducto().equals(idprod)))
        {
          lst.setProducto((Producto)this.productodao.obtenPorId(idprod));
          lst.setFdpreciocompra(lst.getProducto().getFdpreciocompra());
        }
        double subtotal = delegate.calcularSubtotal(lst.getProducto(), lst.getFicantidad());
        lst.setFdpreciocompra(subtotal / lst.getFicantidad());
        lst.setFdsubtotal(subtotal);
      }
      compraform.getCompra().setFdtotal(delegate.calcularTotal(detallecompra));
    }
    return compraform;
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