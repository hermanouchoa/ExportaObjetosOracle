/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author F1852
 */
public class Script {
    
    private String queryScript;
    private String pathScript;
    
    public Script(String path){
        this.pathScript=path;
    }

    /**
     * @return the queryScript
     */
    public String getQueryScript() {
        return queryScript;
    }

    /**
     * @param queryScript the queryScript to set
     */
    public void setQueryScript(String queryScript) {
        this.queryScript = queryScript;
    }

    /**
     * @return the pathScript
     */
    public String getPathScript() {
        return pathScript;
    }

    /**
     * @param pathScript the pathScript to set
     */
    public void setPathScript(String pathScript) {
        this.pathScript = pathScript;
    }    
    
    public String toString(){
        return this.pathScript;
    }
}
