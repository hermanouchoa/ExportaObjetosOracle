/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author F1852
 */
public class ObjetoOracle {
    
    private String tipoObjeto;
    private String nomeObjeto;
    private String codigoObjeto;
    
    public ObjetoOracle(String tipo, String nome, String codificacao){
        this.tipoObjeto = tipo;
        this.nomeObjeto = nome;
        this.codigoObjeto = codificacao;
    }

    /**
     * @return the tipoObjeto
     */
    public String getTipoObjeto() {
        return tipoObjeto;
    }

    /**
     * @return the nomeObjeto
     */
    public String getNomeObjeto() {
        return nomeObjeto;
    }

    /**
     * @return the codigoObjeto
     */
    public String getCodigoObjeto() {
        return codigoObjeto;
    }
    
    public String toString(){
        return "Tipo: "+this.tipoObjeto+" | Nome: "+this.nomeObjeto;
    }
}
