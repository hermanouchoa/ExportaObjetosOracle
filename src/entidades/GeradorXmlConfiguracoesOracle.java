/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.File;
import java.io.FileWriter;
import java.text.Normalizer;
import java.util.Calendar;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
/**
 *
 * @author F1852
 */
public class GeradorXmlConfiguracoesOracle {
    
    private ConfiguracaoConexaoOracle configOra;
    
    public GeradorXmlConfiguracoesOracle(ConfiguracaoConexaoOracle cco) throws Exception{
        this.configOra = cco;
        this.criarDiretorioConfigSis();
        this.gerarXmlConfigSis();
    }
      
    private void criarDiretorioConfigSis() throws Exception{
        try {
            if (!new File("configsisora").exists()){
                (new File("configsisora")).mkdir();
            }
            
        } catch (Exception e) {            
            throw new Exception("Erro ao criar diretório 'configsis' em \""+System.getProperty("user.dir")+"\", você pode fazer manualmente.");
        }
    }
    
    private String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str.toUpperCase();
    } 
    
    public void gerarXmlConfigSis() throws Exception{
        try {
            Calendar data = Calendar.getInstance();//Apenas para compor o nome do arquivo XML.    
            
            Element configuracao = new Element("configuracao");
            Document documento = new Document(configuracao);
            
            Element dadosConfigConnBanco = new Element("configConnBanco");
            dadosConfigConnBanco.setAttribute("id", Integer.toString(data.get(Calendar.DATE))
                                               +Integer.toString(data.get(Calendar.MONTH)+1)
                                               +Integer.toString(data.get(Calendar.YEAR))
                                               +Integer.toString(data.get(Calendar.HOUR_OF_DAY)) 
                                               +Integer.toString(data.get(Calendar.MINUTE)) 
                                               +Integer.toString(data.get(Calendar.SECOND)));            
            
            Element configuracaoConnBancoProducao = new Element("connBancoProducao");            
                Element usuarioBanco = new Element("usrProducao");
                usuarioBanco.setText(this.configOra.getUsuarioConexao());

                Element senhaBanco = new Element("snhProducao");
                senhaBanco.setText(this.configOra.getSenhaConexao());

                Element servicoBanco = new Element("servProducao");
                servicoBanco.setText(this.configOra.getServicoOracle());

                Element hostBanco = new Element("hostProducao");
                hostBanco.setText(this.configOra.getHostConexao());

                Element portaBanco = new Element("portProducao");
                portaBanco.setText(this.configOra.getPortaOracleConexao());

            Element configuracaoConnBancoTeste = new Element("connBancoTeste");
                Element usuarioBancoTeste = new Element("usrTeste");
                usuarioBancoTeste.setText(this.configOra.getUsuarioConexaoTeste());

                Element senhaBancoTeste = new Element("snhTeste");
                senhaBancoTeste.setText(this.configOra.getSenhaConexaoTeste());

                Element servicoBancoTeste = new Element("servTeste");
                servicoBancoTeste.setText(this.configOra.getServicoOracleTeste());

                Element hostBancoTeste = new Element("hostTeste");
                hostBancoTeste.setText(this.configOra.getHostConexaoTeste());

                Element portaBancoTeste = new Element("portTeste");
                portaBancoTeste.setText(this.configOra.getPortaOracleConexaoTeste());                
                
                
            configuracaoConnBancoProducao.addContent(usuarioBanco);
            configuracaoConnBancoProducao.addContent(senhaBanco);
            configuracaoConnBancoProducao.addContent(servicoBanco);
            configuracaoConnBancoProducao.addContent(hostBanco);
            configuracaoConnBancoProducao.addContent(portaBanco);            

            configuracaoConnBancoTeste.addContent(usuarioBancoTeste);
            configuracaoConnBancoTeste.addContent(senhaBancoTeste);
            configuracaoConnBancoTeste.addContent(servicoBancoTeste);
            configuracaoConnBancoTeste.addContent(hostBancoTeste);
            configuracaoConnBancoTeste.addContent(portaBancoTeste);            
            
            dadosConfigConnBanco.addContent(configuracaoConnBancoProducao);
            dadosConfigConnBanco.addContent(configuracaoConnBancoTeste);
            
            configuracao.addContent(dadosConfigConnBanco);
            
            XMLOutputter xout = new XMLOutputter();
            
            FileWriter arquivo = new FileWriter(new File(System.getProperty("user.dir")+"/configsisora/config.xml"));
            xout.output(documento ,arquivo);             
        
        } catch (Exception e) {
            throw new Exception("Erro ao gerar XML de configurações.\n"+e.getMessage());
        }
    }
}
