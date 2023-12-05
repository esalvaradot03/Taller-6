package Restaurante;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoAjustadoTest {

    @Test
    public void testGetPrecioConAgregados() {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado ajustado = new ProductoAjustado(base);
        ajustado.setAgregados(new Ingrediente("Queso", 500));
        assertEquals(10500, ajustado.getPrecio());
    }

    @Test
    public void testGetPrecioConEliminados() {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado ajustado = new ProductoAjustado(base);
        ajustado.setEliminados(new Ingrediente("Cebolla", 0)); // No cambia el precio
        assertEquals(10000, ajustado.getPrecio());
    }
}
