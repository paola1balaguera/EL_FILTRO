package com.campusland.views;


import java.time.LocalDateTime;

import com.campusland.exceptiones.clienteexceptions.ProductoNullException;
import com.campusland.exceptiones.clienteexceptions.ClienteNullException;

//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;

import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.respository.models.ItemFactura;
import com.campusland.respository.models.Producto;
//import com.campusland.respository.models.ItemFactura;
//import com.campusland.respository.models.Producto;



//import lombok.Data;

public class ViewFactura extends ViewMain{


    public static void startMenu() {

        int op = 0;

        do {

            op = mostrarMenu();
            switch (op) {
                case 1:
                    CrearFactura();
                    break;
                case 2:
                    listarFactura();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 6);

    }


    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear factura.");
        System.out.println("2. Listar factura.");      
        System.out.println("3. Salir ");
        return leer.nextInt();
    }

    public static void listarFactura() {
        System.out.println("Lista de Facturas");
        for (Factura factura : serviceFactura.listar()) {
            factura.display();
            System.out.println();
        }
    }

    public static void CrearFactura(){
        System.out.println("----Crear factura-----");
        System.out.println("Ingrese el documento del cliente: ");
        String id = leer.next();
        // fecha - Date
        // cliente - Instancia del cliente
        // items - Array -> [1,"Pincel"]
         
        LocalDateTime dateNow = LocalDateTime.now(); 
        
        System.out.println(dateNow);
        try {
            Cliente cliente = serviceCliente.porDocumento(id);
        if(cliente!=null){
            System.out.println("FACTURAAAA");
            Factura factura = new Factura(dateNow, cliente);

            System.out.println("Cuantos items deseas ingresar?: ");
            int numItems = leer.nextInt();
            for(int i=0; i<numItems; i++){
                System.out.println("Ingresa el codigo del producto: ");
                int codigoProducto = leer.nextInt();
                System.out.println("Ingresa la cantidad del producto: ");
                int cantidadProducto = leer.nextInt();
                try {
                    Producto producto = serviceProducto.porCodigo(codigoProducto);
                    ItemFactura item = new ItemFactura(cantidadProducto, producto);
                    factura.agregarItem(item);

                    
                } catch (ProductoNullException e) {
                    System.out.println(e.getMessage());
                }

            }
            serviceFactura.crear(factura);
        }
    }
            
        catch (ClienteNullException e) {
            System.out.println(e.getMessage());
    }   
    }       
}
        
    

