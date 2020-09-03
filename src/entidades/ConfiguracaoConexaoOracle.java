/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author F1852
 */
public class ConfiguracaoConexaoOracle {
    private String usuarioConexao;
    private String senhaConexao;
    private String servicoOracle;
    private String hostConexao;
    private String portaOracleConexao;
    
    private String usuarioConexaoTeste;
    private String senhaConexaoTeste;
    private String servicoOracleTeste;
    private String hostConexaoTeste;
    private String portaOracleConexaoTeste;    
    
    public ConfiguracaoConexaoOracle(){

    }

    /**
     * @return the usuarioConexao
     */
    public String getUsuarioConexao() {
        return usuarioConexao;
    }

    /**
     * @param usuarioConexao the usuarioConexao to set
     */
    public void setUsuarioConexao(String usuarioConexao) {
        this.usuarioConexao = usuarioConexao;
    }

    /**
     * @return the senhaConexao
     */
    public String getSenhaConexao() {
        return senhaConexao;
    }

    /**
     * @param senhaConexao the senhaConexao to set
     */
    public void setSenhaConexao(String senhaConexao) {
        this.senhaConexao = senhaConexao;
    }

    /**
     * @return the servicoOracle
     */
    public String getServicoOracle() {
        return servicoOracle;
    }

    /**
     * @param servicoOracle the servicoOracle to set
     */
    public void setServicoOracle(String servicoOracle) {
        this.servicoOracle = servicoOracle;
    }

    /**
     * @return the hostConexao
     */
    public String getHostConexao() {
        return hostConexao;
    }

    /**
     * @param hostConexao the hostConexao to set
     */
    public void setHostConexao(String hostConexao) {
        this.hostConexao = hostConexao;
    }

    /**
     * @return the portaOracleConexao
     */
    public String getPortaOracleConexao() {
        return portaOracleConexao;
    }

    /**
     * @param portaOracleConexao the portaOracleConexao to set
     */
    public void setPortaOracleConexao(String portaOracleConexao) {
        this.portaOracleConexao = portaOracleConexao;
    }

    /**
     * @return the usuarioConexaoTeste
     */
    public String getUsuarioConexaoTeste() {
        return usuarioConexaoTeste;
    }

    /**
     * @param usuarioConexaoTeste the usuarioConexaoTeste to set
     */
    public void setUsuarioConexaoTeste(String usuarioConexaoTeste) {
        this.usuarioConexaoTeste = usuarioConexaoTeste;
    }

    /**
     * @return the senhaConexaoTeste
     */
    public String getSenhaConexaoTeste() {
        return senhaConexaoTeste;
    }

    /**
     * @param senhaConexaoTeste the senhaConexaoTeste to set
     */
    public void setSenhaConexaoTeste(String senhaConexaoTeste) {
        this.senhaConexaoTeste = senhaConexaoTeste;
    }

    /**
     * @return the servicoOracleTeste
     */
    public String getServicoOracleTeste() {
        return servicoOracleTeste;
    }

    /**
     * @param servicoOracleTeste the servicoOracleTeste to set
     */
    public void setServicoOracleTeste(String servicoOracleTeste) {
        this.servicoOracleTeste = servicoOracleTeste;
    }

    /**
     * @return the hostConexaoTeste
     */
    public String getHostConexaoTeste() {
        return hostConexaoTeste;
    }

    /**
     * @param hostConexaoTeste the hostConexaoTeste to set
     */
    public void setHostConexaoTeste(String hostConexaoTeste) {
        this.hostConexaoTeste = hostConexaoTeste;
    }

    /**
     * @return the portaOracleConexaoTeste
     */
    public String getPortaOracleConexaoTeste() {
        return portaOracleConexaoTeste;
    }

    /**
     * @param portaOracleConexaoTeste the portaOracleConexaoTeste to set
     */
    public void setPortaOracleConexaoTeste(String portaOracleConexaoTeste) {
        this.portaOracleConexaoTeste = portaOracleConexaoTeste;
    }

    public void gerarXml() throws Exception{
        GeradorXmlConfiguracoesOracle gerador = new GeradorXmlConfiguracoesOracle(this);
    }
}
