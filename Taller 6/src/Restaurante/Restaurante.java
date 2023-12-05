package Restaurante;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Restaurante.ProductoMenu;
import Restaurante.Combo;
import Restaurante.Pedido;

public class Restaurante 
{
/** Atributos
 * Agregamos una lista para guardar los pedidos
 * 
 */
	// error: clase no creada aún
	
	private ArrayList<Pedido> pedidos;
	private ArrayList<Ingrediente> lista_ingredientes;
	private ArrayList<Producto> menuBase;
	private ArrayList<Combo> combos;
	private boolean haypedido;
	private Pedido pedidoEnCurso;
	private HashMap<Integer, Pedido> mapa;
	
	//Constructor
	
	public Restaurante()
	{
		this.pedidos = new ArrayList<>();
		this.lista_ingredientes = new ArrayList<>();
		this.menuBase = new ArrayList<>();
		this.combos = new ArrayList<>();
		this.haypedido = false;
		this.mapa = new HashMap<Integer, Pedido>();
		
		int ultimo = pedidos.size();
		if ((ultimo) != 0)
		{
			int ult = ultimo - 1;
			this.pedidoEnCurso = pedidos.get(ult);
		}
		else 
		{
			this.pedidoEnCurso = null;
		}
		
	}
	
	//Métodos
	
	public void iniciarPedido (String nombreCliente, String direccionCliente)
	{
		haypedido = true;
		Pedido ped = new Pedido(nombreCliente, direccionCliente);
		this.pedidoEnCurso = ped;
		pedidos.add(ped);
	}

	public void cerrarYGuardarPedido() throws IOException
	{
		int ultimo = pedidos.size()-1;
		Pedido.aumentaridpedido();
		pedidos.get(ultimo).guardarFactura("./Datos/Pedidos.txt");
		mapa.put(pedidoEnCurso.getIdPedido(), pedidoEnCurso);
		haypedido = false;
	}
	
	public Pedido getpedidoEnCurso()
	{
		
		int ultimo = pedidos.size()-1;
		return pedidos.get(ultimo);
	}
		
	public boolean gethaypedido()
	{
		return haypedido;
	}
	
	//Recordar que ProductoMenu tmbn es un Producto; polimorfismo
	public ArrayList<Producto> getMenuBase()
	{		
		return menuBase;
	}
	
	public ArrayList<Ingrediente> getIngredientes()
	{
		return lista_ingredientes;
	}

	public ArrayList<Combo> getCombos()
	{		
		return combos;
	}
	
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos) throws FileNotFoundException, IOException, IngredienteRepetidoException, ProductoRepetidoException
	{
		
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		
		System.out.println("MENÚ\n");
		for (Producto elemento : menuBase)
		{
			System.out.println(elemento.getNombre());
		}
		System.out.println("\n");
		System.out.println("COMBOS:\n");
		for (Combo element : combos)
		{
			System.out.println(element.getNombre());
		}
		System.out.println("\n");
		System.out.println("INGREDIENTES\n");
		for (Ingrediente elemen : lista_ingredientes)
		{
			System.out.println(elemen.getNombre());
		}
		
	}
	
		//Métodos que llaman a clases para instanciar productos
			// cargan el archivo, y llaman al constructor de la clase
		//Se llaman por MenuBase para hacer el menú
	
	// File de momento será str para que no de error
	
	private void cargarIngredientes(String archivoIngredientes) throws IOException, IngredienteRepetidoException {
		BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
		String linea = br.readLine(); 
		
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
	
			// Verifica si el ingrediente ya existe
			for (Ingrediente ingrediente : lista_ingredientes) {
				if (ingrediente.getNombre().equals(nombre)) {
					throw new IngredienteRepetidoException("Ingrediente repetido: " + nombre);
				}
			}
	
			int costoAdicional = Integer.parseInt(partes[1]);
			Ingrediente ing = new Ingrediente(nombre, costoAdicional);
			lista_ingredientes.add(ing);
	
			linea = br.readLine();
		}
		br.close();
	}
	
	
	private void cargarMenu(String archivoMenu) throws IOException, ProductoRepetidoException {
		BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
		String linea = br.readLine(); 
		
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
	
			// Verifica si el producto ya existe
			for (Producto producto : menuBase) {
				if (producto.getNombre().equals(nombre)) {
					throw new ProductoRepetidoException("Producto repetido: " + nombre);
				}
			}
	
			int precio = Integer.parseInt(partes[1]);
			ProductoMenu prod = new ProductoMenu(nombre, precio);
			menuBase.add(prod);
	
			linea = br.readLine(); 
		}
		br.close();
	}
	
	
	private void cargarCombos(String archivoCombos) throws IOException, FileNotFoundException
	{
		
		BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
		String linea = br.readLine(); 
		
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String dct = partes[1];
			double descuento = Integer.parseInt(dct.replaceAll("%", ""));
			Combo cc = new Combo(nombre, descuento); 
			
			for (Producto elemento : menuBase)
			{
				if (elemento.getNombre().equals(partes[2]))
				{
					cc.agregarleItemACombo(elemento);
				}
				else if (elemento.getNombre().equals(partes[3]))
				{
					cc.agregarleItemACombo(elemento);
				}
				else if (elemento.getNombre().equals(partes[4]))
				{
					cc.agregarleItemACombo(elemento);
				}
			}
			
			combos.add(cc);			
			
			linea = br.readLine(); 
		}
		br.close();
		
	}
	
	
	// Nuevo Método
	
	public HashMap<Integer, Pedido> getMapa(int id)
	{
		return mapa;
	}
	
	
}
