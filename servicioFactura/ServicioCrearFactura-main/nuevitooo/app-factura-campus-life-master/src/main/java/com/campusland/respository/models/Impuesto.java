package com.campusland.respository.models;

public class Impuesto {
    private Cliente cliente;
    private Factura factura;
    private double valorTotal;
    private double impuestoPagado;

    public Impuesto(Cliente cliente, Factura factura, double valorTotal, double impuestoPagado) {
        this.cliente = cliente;
        this.factura = factura;
        this.valorTotal = importacionIvaFactura();
        this.impuestoPagado = importacionIvaPagado();
    }

    public double importacionIvaFactura(){

        double importacion = factura.getTotalFacturaIva();

        return importacion;
    }

    public double importacionIvaPagado(){
        double importacion1 = factura.getTotalFactura();
        return importacion1;
    }





}
