package Restaurante;

import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado implements Producto
{
	//Atributos

	private ProductoMenu base;
	private List<Ingrediente> agregados;
	private List<Ingrediente> eliminados;
	
	//Constructor
	
	public ProductoAjustado (ProductoMenu base)
	{
		this.base = base;
		this.agregados = new ArrayList<>();
		this.eliminados = new ArrayList<>();
		
	}
	
	//Métodos
	
	@Override
	public int getPrecio() 
	{	
		int adicional = 0;
		
		if (agregados.size() == 0)
		{
			return base.getPrecio();
		}
		
		else
		{ 
			for (Ingrediente elemento : agregados)
			{
				adicional += elemento.getCostoAdicional();
			}
			
			return adicional += base.getPrecio();
		}
	}

	@Override
	public String getNombre() 
	{
		return base.getNombre();
	}

	@Override
	public String generarTextoFactura() 
	{
		String retorno = "Costo " + base.getNombre();
		
		for (Ingrediente elemento : agregados)
		{ 
			String name = elemento.getNombre();
			retorno += " con " + name;	
		}
		
		for (Ingrediente element : eliminados)
		{ 
			String namee = element.getNombre();
			retorno += " sin " + namee;	
		}		
		
		return retorno;
	}

	//Métodos agregados
	
	public List<Ingrediente> getAgregados()
	{
		return agregados;
	}
	
	public List<Ingrediente> getEliminados()
	{
		return agregados;
	}
	
	public void setAgregados(Ingrediente ingr)
	{
		agregados.add(ingr);
		System.out.println(agregados);
	}
	
	public void setEliminados(Ingrediente ingr)
	{
		eliminados.add(ingr);
		System.out.println(eliminados);
	}
	
}





