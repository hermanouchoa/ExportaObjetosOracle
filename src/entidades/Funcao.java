/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author F1852
 */
public class Funcao extends ObjetoOracle{
    public Funcao(String nomeDaFuncao, String codigoObjeto){
        super("FUNCTION", nomeDaFuncao, codigoObjeto);
    }
    
    public String toString(){
        return super.toString();
    }
}
