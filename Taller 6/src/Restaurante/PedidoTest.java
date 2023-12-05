package Restaurante;

import org.junit.Test;
import static org.junit.Assert.*;

public class PedidoTest {

    @Test(expected = PedidoExcepcion.class)
    public void testAgregarProductoExcepcion() throws PedidoExcepcion {
        Pedido pedido = new Pedido("Cliente", "Dirección");
        ProductoMenu producto = new ProductoMenu("Producto Caro", 200000);
        pedido.agregarProducto(producto); // Esto debería lanzar PedidoExcepcion
    }

    @Test
    public void testGenerarTextoFactura() {
        Pedido pedido = new Pedido("Cliente", "Dirección");
        ProductoMenu producto = new ProductoMenu("Hamburguesa", 10000);
        pedido.agregarProducto(producto);
        String expected = "     FACTURA    \nCliente: Cliente\nDirección Cliente: Dirección\nID Pedido: 0\nPrecioBase Hamburguesa: 10000\n\nPrecio Neto: 10000\nIVA : 1900\nPrecio Neto: 11900\n\nGracias por su compra :) \n--------------------------------------\n\n";
        assertEquals(expected, pedido.guardarFactura());
    }
}
