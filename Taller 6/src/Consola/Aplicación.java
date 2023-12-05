package Consola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Restaurante.Restaurante;
import Restaurante.Combo;
import Restaurante.Ingrediente;
import Restaurante.IngredienteRepetidoException;
import Restaurante.Pedido;
import Restaurante.PedidoExcepcion;
import Restaurante.Producto;
import Restaurante.ProductoMenu;
import Restaurante.ProductoRepetidoException;
import Restaurante.ProductoAjustado;

/**
 * Toca quitar tantas opciones, pero 1ero debo saber cuales quedan
 * Con esto se edita "mostrarMenú", para que imprima todas
 * Sabiendo cuales hay, toca hacer métodos para c/u que las ejecute
*/


public class Aplicación 
{
	//Atributo
	private Restaurante rest;
	
	//Métodos
	
	public void ejecutarAplicacion() throws FileNotFoundException, IOException, PedidoExcepcion, IngredienteRepetidoException, ProductoRepetidoException
	{
		System.out.println("Bienvenido al corral\n");

		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
					ejecutarCargarMenu();
				else if (opcion_seleccionada == 2 && rest != null)
				{
						if(rest.gethaypedido() == false)
						{
							ejecutarIniciarPedido();
						}
						else
						{
							System.out.println("Ya hay un pedido en curso. Por favor cerrárlo para iniciar uno nuevo");
						}
				}
					
				else if (opcion_seleccionada == 3 && rest != null)
					ejecutarAgregarItemAPedido();
				else if (opcion_seleccionada == 4 && rest != null)
					ejecutarCerrarPedidoyGuardarFactura();
				else if (opcion_seleccionada == 5 && rest != null)
					ejecutarConsultarInfoPedidoConID();

				else if (opcion_seleccionada == 6)
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else if (rest == null)
				{
					System.out.println("Para poder ejecutar esta opción primero debe cargar los archivos del menú.");
				}
				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}
	
	private void ejecutarCargarMenu() throws FileNotFoundException, IOException, IngredienteRepetidoException, ProductoRepetidoException 
	{
		System.out.println("Cargando menú...\n");
		this.rest = new Restaurante();
		rest.cargarInformacionRestaurante("./Datos/ingredientes.txt", "./Datos/menu.txt", "./Datos/combos.txt");
	}
	
	private void ejecutarIniciarPedido() 
	{
		System.out.println("Iniciando pedido...\n");
		
		String nombre = input("¿A nombre de quién el pedido?");
		String direccion = input("¿Dirección del pedido?");
		
		rest.iniciarPedido(nombre, direccion);
	}
	
	private void ejecutarAgregarItemAPedido() throws PedidoExcepcion 
	{
		System.out.println("¿Qué desea agregar a su pedido?");
		System.out.println("1. Producto Base");
		System.out.println("2. Producto Ajustado (ingredientes adicionales tienen costo adicional)");
		System.out.println("3. Combo");
		
		Pedido actual = rest.getpedidoEnCurso();
		int opcion = Integer.parseInt(input("Por favor seleccione una opción: "));
		
		try
		{
			if (opcion == 1)
			{
				String prod = input("¿Qué elemento desea agregar?");
				for (Producto elemento : rest.getMenuBase())
				{
					
					if (elemento.getNombre().equals(prod))
					{
						actual.agregarProducto(elemento);
						System.out.println("Producto agregado");
					}
					
				}
			}
			
			else if (opcion == 2)
			{
				String base = input("¿Qué producto base desea ajustar?");
				String cambio = input("¿Qué ingrediente desea agregar o eliminar?");
				String poneroquitar = input("¿agregar o eliminar?");
				boolean centinela = true ;
				
				ArrayList<Ingrediente> provicional = new ArrayList<>();
				
				for (Ingrediente element : rest.getIngredientes())
				{
					if (element.getNombre().equals(cambio))
					{
						Ingrediente ingr = element;
						provicional.add(ingr);
					}
				}
				
				if (provicional.size() == 0)
				{
					System.out.println("El ingrediente no existe");
					centinela = false;
				}
				
				if (centinela == true)
				{
					for (Producto elemento : rest.getMenuBase())
					{
						if (elemento.getNombre().equals(base))
						{
							ProductoMenu babase = (ProductoMenu) elemento;
							ProductoAjustado prod = new ProductoAjustado(babase);
							if (poneroquitar.equals("agregar"))
							{
								System.out.println("si pasó");
								prod.setAgregados(provicional.get(0));
							}
							else if (poneroquitar.equals("eliminar"))
							{
								System.out.println("si pasó");
								prod.setEliminados(provicional.get(0));
							}
								
							actual.agregarProducto(prod);
							System.out.println("Producto agregado");
						}
					}
				}
				else
				{
					System.out.println("No se pudo agregar");
				}
			}
			
			else if (opcion == 3)
			{
				String comb = input("Nombre del combo deseado: ");
				for (Combo elemen : rest.getCombos())
				{
					if (elemen.getNombre().equals(comb))
					{
						actual.agregarProducto(elemen);
						System.out.println("Producto agregado");
					}
				}
			}
			
			else
			{
				System.out.println("Opción no válida");
			}
		}
		catch (NumberFormatException e)
		{
			System.out.println("Hubo un error en su selección. Por favor intente de nuevo");
		}
		
	}
	
	private void ejecutarCerrarPedidoyGuardarFactura() throws IOException 
	{
		rest.cerrarYGuardarPedido();
		System.out.println("Pedido cerrado y Factura guardada. Para continuar inicie un nuevo pedido o cierre la aplicación");
	}
	
	
	private void ejecutarConsultarInfoPedidoConID() 
	{
		int id_buscado = Integer.parseInt(input("Escriba id del pedido para consultar"));
		HashMap<Integer, Pedido> map = rest.getMapa(id_buscado);
		Pedido pedido = map.get(id_buscado);
		
		System.out.println("Los elementos de este pedido son:");
		
		for (Producto elemento : pedido.getitemsPedidoActual())
		{
			System.out.println(elemento.getNombre() + "\n");
		}
		System.out.println("El nombre y dirrección son reservados por privacidad");
		
		
	}


	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Cargar y mostrar Menú (Productos, Ingredientes y Combos)");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar artículo al pedido");
		System.out.println("4. Cerrar pedido y guardar factura");
		System.out.println("5. Consultar información de un pedidio dado su id");
		System.out.println("6. Cerrar aplicación");
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, PedidoExcepcion, IngredienteRepetidoException, ProductoRepetidoException
	{
		Aplicación consola = new Aplicación();
		consola.ejecutarAplicacion();
	}
}
