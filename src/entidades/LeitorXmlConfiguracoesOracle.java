/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author F1852
 */
public class LeitorXmlConfiguracoesOracle {
    public LeitorXmlConfiguracoesOracle(){
        
    }
    
    private File[] obterListaArquivos(){
        File dir = new File("ponto");
        File[] files = dir.listFiles();
        return  files;
    }
    
    
    public ConfiguracaoConexaoOracle obterConfiguracoes() throws Exception{
        return this.lerXmlConfigConexaoOra(System.getProperty("user.dir")+"/configsisora/config.xml");
    }
    
    private ConfiguracaoConexaoOracle lerXmlConfigConexaoOra(String pathXml) throws Exception{
        ConfiguracaoConexaoOracle configConnOra = new ConfiguracaoConexaoOracle();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(pathXml);
            
            Element raiz = doc.getDocumentElement();
            
            NodeList listaConfigBancoProd = raiz.getElementsByTagName("connBancoProducao");            
            for (int i=0; i < listaConfigBancoProd.getLength(); i++){
                Element confiBancoProd = (Element)listaConfigBancoProd.item(i);  
                
                NodeList usuarioProd = confiBancoProd.getElementsByTagName("usrProducao");
                Node usuario = usuarioProd.item(0).getFirstChild();
                configConnOra.setUsuarioConexao(usuario.getNodeValue());
                
                NodeList senhaProd = confiBancoProd.getElementsByTagName("snhProducao");
                Node senha = senhaProd.item(0).getFirstChild();                
                configConnOra.setSenhaConexao(senha.getNodeValue());
                
                NodeList servProd = confiBancoProd.getElementsByTagName("servProducao");
                Node serv = servProd.item(0).getFirstChild();                
                configConnOra.setServicoOracle(serv.getNodeValue());  
                
                NodeList hostProducao = confiBancoProd.getElementsByTagName("hostProducao");
                Node host = hostProducao.item(0).getFirstChild();                
                configConnOra.setHostConexao(host.getNodeValue());                  
                
                NodeList portProducao = confiBancoProd.getElementsByTagName("portProducao");
                Node porta = portProducao.item(0).getFirstChild();                
                configConnOra.setPortaOracleConexao(porta.getNodeValue());                 
            }
            
            NodeList listaConfigBancoTeste = raiz.getElementsByTagName("connBancoTeste");            
            for (int i=0; i < listaConfigBancoTeste.getLength(); i++){
                Element confiBancoTeste = (Element)listaConfigBancoTeste.item(i);  
                
                NodeList usuarioTeste = confiBancoTeste.getElementsByTagName("usrTeste");
                Node usuario = usuarioTeste.item(0).getFirstChild();
                configConnOra.setUsuarioConexaoTeste(usuario.getNodeValue());
                
                NodeList senhaTeste = confiBancoTeste.getElementsByTagName("snhTeste");
                Node senha = senhaTeste.item(0).getFirstChild();                
                configConnOra.setSenhaConexaoTeste(senha.getNodeValue());
                
                NodeList servTeste = confiBancoTeste.getElementsByTagName("servTeste");
                Node serv = servTeste.item(0).getFirstChild();                
                configConnOra.setServicoOracleTeste(serv.getNodeValue());  
                
                NodeList hostTeste = confiBancoTeste.getElementsByTagName("hostTeste");
                Node host = hostTeste.item(0).getFirstChild();                
                configConnOra.setHostConexaoTeste(host.getNodeValue());                  
                
                NodeList portTeste = confiBancoTeste.getElementsByTagName("portTeste");
                Node porta = portTeste.item(0).getFirstChild();                
                configConnOra.setPortaOracleConexaoTeste(porta.getNodeValue());                 
            }            
        } catch (Exception e) {
            throw new Exception("Erro ao ler XML de configurações so sistema. \n"+e.getMessage());
        }
        return configConnOra;
    }
}
