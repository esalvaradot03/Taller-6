package Restaurante;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto
{
	//Atributos
	
	private double descuento;
	
	private String nombreCombo;
	
	private ArrayList<ProductoMenu> itemsCombo; //Podría ser "Producto" si hay ajustados
	
	//Constructor

		//seguramente deba guardar los nombres de los productos del combo
	public Combo(String nombre, double descuento)
	{
		this.descuento = descuento;
		this.nombreCombo = nombre;
		this.itemsCombo = new ArrayList<>();
	}
	
	//Métodos
	
	public void agregarleItemACombo(Producto itemCombo)
	{
		itemsCombo.add((ProductoMenu) itemCombo);
	}

	@Override
	public int getPrecio() 
	{	
		double total = 0;
		
		for (ProductoMenu productoBase : itemsCombo)
		{
			int precio = productoBase.getPrecio();
			double ajustado = Math.round(precio - ((precio/100)*descuento));
			total += ajustado;
		}
		
		return (int) total;
	}

	@Override
	public String getNombre() 
	{
		return nombreCombo;
	}

	@Override
	public String generarTextoFactura() 
	{
		int precio = getPrecio();
		String formato = "Precio " + nombreCombo + ": " + precio;
		return formato;
	}

}




