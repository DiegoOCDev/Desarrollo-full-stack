package com.API.API.model;

public class Globals {
    private Usuario usuarioActivo = new Usuario();
    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }
    public void setUsuarioActivo(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }
}
