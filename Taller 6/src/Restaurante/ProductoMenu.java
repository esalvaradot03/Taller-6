package Restaurante;

public class ProductoMenu implements Producto
{
	//Atributos
	
	private String nombre;
	
	private int precioBase;
	
	//Constructor
	
	public ProductoMenu(String nombre, int precioBase)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
	//Métodos (heredados interface)
	
	@Override
	public int getPrecio() 
	{
		return precioBase;
	}

	@Override
	public String getNombre() 
	{
		return nombre;
	}

	@Override
	public String generarTextoFactura() 
	{
		String formato = "PrecioBase " + nombre + ": " + precioBase;
		return formato;
	}
	
}
