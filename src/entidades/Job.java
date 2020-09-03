package entidades;

public class Job extends ObjetoOracle{

    private String parada;
    
    public Job(String nome, String codificacao, String estaParada) {
        super("JOB", nome, codificacao);
        this.parada = estaParada;
    }
    
    public String toString(){        
       return super.toString()+" Comando: "+this.getCodigoObjeto()+" Parada? "+this.parada;
    }
}
