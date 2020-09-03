package entidades;

public class Procedure extends ObjetoOracle{
        
    public Procedure(String nomeDaProcedure, String codigoObjeto){
        super("PROCEDURE", nomeDaProcedure, codigoObjeto);        
    }
        
    public String toString(){        
       return super.toString();
    }
}
