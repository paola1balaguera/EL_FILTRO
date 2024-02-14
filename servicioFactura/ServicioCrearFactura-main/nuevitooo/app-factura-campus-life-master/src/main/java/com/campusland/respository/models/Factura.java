package com.campusland.respository.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.campusland.utils.ConexionBDList;
import com.campusland.utils.Formato;

import lombok.Data;

@Data
public class Factura {

    private int numeroFactura;
    private LocalDateTime fecha;
    private Cliente cliente;
    private List<ItemFactura> items;
    private Impuesto impuesto;
    private static int nextNumeroFactura;

    public Factura(LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = ++nextNumeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.impuesto = new Impuesto(cliente, null, numeroFactura, nextNumeroFactura);
    }

    public double getTotalFactura() {
        double totalFactura = 0;
        for (ItemFactura item : items) {
            totalFactura += item.getImporte();
        }
        
        return totalFactura;
    }

    public double getTotalFacturaIva(){
        double totalFactura = this.getTotalFactura();
        double PorcentajeIva = 0.19;
        double totalIva, totalFacturaIva = 0;
        
        totalIva = totalFactura * PorcentajeIva;

        totalFacturaIva = totalIva + totalFactura;
        
        return totalFacturaIva;
    }

    public double getTotalDescuento(){
        double totalFacturaDescuento =  this.getTotalFactura();
        double sumDescuentos = 0;
            //SE realizan validaciones para ver si es mayor a 0, de ser 
            //asi se debe imprimir al final de la facturo que se aplico tal descuento---
            // al final de la funcion sumo el valor de todos los descuento y se lo resto
            //al monto factura y lo imprimo
        if(sumDescuentos > totalFacturaDescuento){
            sumDescuentos = totalFacturaDescuento;
        }
        return sumDescuentos;
    }

    public void getDescuentoMontoMinimo(){
        double totalFacturaDescuento =  this.getTotalFactura();
        final double porcentaje = 0.10; 
        double precioFinal = 0;

        if(totalFacturaDescuento > 1000){
            precioFinal = (totalFacturaDescuento * porcentaje) + totalFacturaDescuento;
        }
    }

    public void getDescuentoCompra5Unidades(){
        double totalFacturaDescuento =  this.getTotalFactura();
        final double monto = 5; 
        double precioFinal = 0;

        for(ItemFactura item: this.items){
            if(item.getProducto().getCodigoNombre()){
                if(item.getCantidad()> 5){
                    precioFinal = (totalFacturaDescuento * monto) + totalFacturaDescuento;
                }
            }
        }
    }

    public void getDescuentoHisotorialCompras(){
        double totalFacturaDescuento =  this.getTotalFactura();
        final double monto = 3; 
        double precioFinal = 0;

        ConexionBDList conexion = ConexionBDList.getConexion();
        PreparedStatement statement = null;

        String query = "SELECT COUNT(*) as numCompras, CONCAT(cliente.nombre, " ", cliente.apellido) AS "Cliente", cliente.documento FROM factura)";
       
        // statement = conexion.prepareStatement(query);
        // statement.setInt(1, programa.getId());
        // statement.setString(2, programa.getNombre());
        // statement.setBoolean(3, programa.isNivel());


    }

    public void getDescuentoViernes(){
        double totalFacturaDescuento =  this.getTotalFactura();
        final double monto = 3; 
        double precioFinal = 0;

        String dia = getDayOfWeek(new Date(), Locale.US);

        if(dia.equals("Friday")){
            precioFinal = (totalFacturaDescuento * monto) + totalFacturaDescuento;
        }
    }




    public void agregarItem(ItemFactura item){
        this.items.add(item);
    }


    public void display() {
        System.out.println();
        System.out.println("Factura: " + this.numeroFactura);
        System.out.println("Cliente: " + this.cliente.getFullName());
        System.out.println("Fecha: " + Formato.formatoFechaHora(this.getFecha()));
        System.out.println("-----------Detalle Factura----------------------");
        for (ItemFactura item : this.items) {
            System.out.println(item.getProducto().getCodigoNombre() + " " + item.getCantidad() + " "
                    + Formato.formatoMonedaPesos(item.getImporte()));

        }
        System.out.println();
        System.out.println("Total Nto                       " + Formato.formatoMonedaPesos(this.getTotalFactura()));
        System.out.println("Total con IVA                       " + Formato.formatoMonedaPesos(this.getTotalFacturaIva()));
        System.out.println("Total a pagar                             " + Formato.formatoMonedaPesos(this.getTotalDescuento()));
        System.out.println();
    }

}
