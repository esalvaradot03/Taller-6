package Restaurante;


import org.junit.Test;
import static org.junit.Assert.*;

public class ComboTest {

    @Test
    public void testGetPrecio() {
        ProductoMenu item1 = new ProductoMenu("Hamburguesa", 10000);
        ProductoMenu item2 = new ProductoMenu("Refresco", 3000);
        Combo combo = new Combo("Combo Hamburguesa", 10); // 10% de descuento
        combo.agregarleItemACombo(item1);
        combo.agregarleItemACombo(item2);
        int expectedPrice = (int) ((10000 + 3000) * 0.9); // Precio con descuento
        assertEquals(expectedPrice, combo.getPrecio());
    }
}

