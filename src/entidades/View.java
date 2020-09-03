/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author F1852
 */
public class View extends ObjetoOracle{

    private String tamanhoTextoView;
        
    /**
     *
     * @param nomeDaVisao
     * @param textoDaVisao
     * @param tamanhoTextoVisao
     */
    public View(String nomeDaVisao, String textoDaVisao, String tamanhoTextoVisao){        
        super("VIEW", nomeDaVisao, textoDaVisao);        
        this.tamanhoTextoView = tamanhoTextoVisao;
    }

    /**
     * @return the tamanhoTextoView
     */
    public String getTamanhoTextoView() {
        return tamanhoTextoView;
    }  
    
    public String toString(){
       return super.toString()+" | Tamanho Texto: "+this.tamanhoTextoView;
    }
}
