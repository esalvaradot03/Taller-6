package Restaurante;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoMenuTest {
    
    @Test
    public void testGenerarTextoFactura() {
        ProductoMenu producto = new ProductoMenu("Hamburguesa", 10000);
        String expected = "PrecioBase Hamburguesa: 10000";
        assertEquals(expected, producto.generarTextoFactura());
    }
}
