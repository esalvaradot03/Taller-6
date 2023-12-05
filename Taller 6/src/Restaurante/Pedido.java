package Restaurante;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Pedido 
{
	
	//Atributos
	
	private static int numeroPedidos;
	
	private int idPedido;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private ArrayList<Producto> itemsPedido;
	
	//Constructor
	// El nombre y la dir. llegan, pero cómo contamos el # y id del pedido?
	
	public Pedido(String nombreCliente, String direccionCliente)
	{
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.idPedido = numeroPedidos;
		// no se si deba sumarle 1
		this.itemsPedido = new ArrayList<>();
	}
	
	// Métodos
	
	public int getIdPedido()
	{
		return idPedido;
	}
	
	public void agregarProducto(Producto nuevoItem) throws PedidoExcepcion
	{
		if (getPrecioTotalPedido() + nuevoItem.getPrecio() > 150000) {
			throw new PedidoExcepcion("El valor total del pedido no puede superar 150.000 pesos");
		}
		itemsPedido.add(nuevoItem);
	}
	
	private int getPrecioNetoPedido()
	{ 
		int PrecioNeto = 0;
		
		for (Producto elemento : itemsPedido)
		{
			int valor = elemento.getPrecio();
			PrecioNeto += valor;
		}
		
		return PrecioNeto;
	}
	
	private int getPrecioIVAPedido()
	{ 
		int IVA = (getPrecioNetoPedido()/100)*19;
		return IVA;
	}
	
	private int getPrecioTotalPedido()
	{ 
		int total = getPrecioNetoPedido() + getPrecioIVAPedido();
		return total;
	}
	
	private String generarTextoFactura()
	{ 
		String Factura = "     FACTURA    \n" + "Cliente: " + nombreCliente + "\n";
		Factura += "Dirección Cliente: " + direccionCliente + "\n";
		Factura += "ID Pedido: " + idPedido + "\n";
		
		for (Producto elemento : itemsPedido)
		{
			String enunciado = elemento.generarTextoFactura();
			Factura += enunciado + "\n";
		}
		Factura += "\n";
		Factura += "Precio Neto: " + getPrecioNetoPedido() + "\n";
		Factura += "IVA : " + getPrecioIVAPedido() + "\n";
		Factura += "Precio Neto: " + getPrecioTotalPedido() + "\n";
		Factura += "\n";
		Factura += "Gracias por su compra :) \n";
		Factura += "--------------------------------------\n";
		Factura += "\n";
		
		return Factura;
	}
	
	public void guardarFactura(String archivo) throws IOException
	{
		String factura = generarTextoFactura();
		
		FileWriter fileWriter = new FileWriter(archivo);
		
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		printWriter.println(factura);

        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
		
	}
	
	// Nuevo Método
	
	public ArrayList<Producto> getitemsPedidoActual()
	{ 
		return itemsPedido;
	}
	
	public static void aumentaridpedido ()
	{
		numeroPedidos += 1;
	}
	
}
