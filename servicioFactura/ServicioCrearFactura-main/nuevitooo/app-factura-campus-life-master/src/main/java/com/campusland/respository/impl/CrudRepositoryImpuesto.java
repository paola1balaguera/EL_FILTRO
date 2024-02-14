package com.campusland.respository.impl;

import java.util.List;

import com.campusland.respository.models.Impuesto;

import com.campusland.utils.ConexionBDList;

public class CrudRepositoryImpuesto implements com.campusland.respository.CrudRepositoryImpuesto  {
    ConexionBDList conexion = ConexionBDList.getConexion();

    @Override
    public List<Impuesto> listar() {
        return conexion.getListImpuesto();
        
    }
}
