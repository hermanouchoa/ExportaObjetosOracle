/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;
import conexao.ConexaoOracle;
import entidades.Funcao;
import entidades.Job;
import entidades.ObjetoOracle;
import entidades.Procedure;
import entidades.View;
import exportaobjetosoracle.UBarraProgresso;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author F1852
 */
public class Controladora {
    public Controladora(){
        
    }
   
    public boolean testeConexao(String usuario, String senha, String servico, String host, String porta) throws Exception{        
        boolean retorno;
        try {
            ConexaoOracle conn = new ConexaoOracle();
            conn.conectaBanco(usuario, senha, servico, host, porta);
            if(conn != null){                
                retorno=true;
                conn.fechaConexaoBanco();
            }else{
                retorno=false;
            }            
        } catch (Exception e) {            
            return false;
            //throw new Exception("Não foi possivel conectar no banco de dados.\n"+e.getMessage());                                    
        }
        return retorno;
    }
    
    public Connection conectarOracle(String usuario, String senha, String servico, String host, String porta) throws Exception{
          try {
            ConexaoOracle conn = new ConexaoOracle();
            Connection conexao = conn.conectaBanco(usuario, senha, servico, host, porta);
            return conexao;
        } catch (Exception e) {
            throw new Exception("Problemas no momento de conectar no banco de dados.\n"+e.getMessage());                        
        }
    }
    
    public void fecharConexaoOracle(Connection conexao) throws Exception{        
        try {
            ConexaoOracle conn = new ConexaoOracle();
            conn.fechaConexaoBanco(conexao);
        } catch (Exception e) {
            throw new Exception("Problemas no fechar conexão com o banco de dados.\n"+e.getMessage());             
        }
    }
    
    public ResultSet execScriptManual(Connection conexao, String script) throws Exception{
        ResultSet retorno;          
        try {           
            ConexaoOracle conn = new ConexaoOracle();
            retorno=conn.executaSql(conexao, script);            
        } catch (Exception e) {            
            throw new Exception("Problemas ao executar Script manual.\n"+e.getMessage());            
        }
        return retorno;
    }
    
    public boolean verificaSeExisteObjeto(Connection conexao, String nomeObjeto) throws Exception{
        try {
            //String usuario = 
            String sqlObjExistente = "SELECT A.OBJECT_TYPE, A.OBJECT_NAME FROM DBA_OBJECTS A WHERE OWNER LIKE '%TASY%' AND upper(A.OBJECT_NAME) LIKE upper('"+nomeObjeto+"')";
            ResultSet rs = this.execScriptManual(conexao, sqlObjExistente);
            String tipoObj = this.lerResultSet(rs, "OBJECT_TYPE", "");
            rs.getStatement().close();
            rs.close();
            if (tipoObj.isEmpty()){
                return false;
            }else{
                return  true;
            }
        } catch (Exception e) {
            throw new Exception("Problemas ao verificar de objeto.\""+nomeObjeto+"\" existe!\n"+e.getMessage());            
        }
    }
    
    public String lerResultSet(ResultSet resultSet, String atributo, String caracterSeparador) throws Exception{
        String retorno="";
        try {
            
                if (caracterSeparador.equals("")){                
                    while(resultSet.next()){
                        retorno += resultSet.getString(atributo);
                    }                          
                }else{                    
                    while(resultSet.next()){
                        retorno += resultSet.getString(atributo)+caracterSeparador;
                    }   
                    if (!retorno.equals("")){
                        retorno = retorno.substring(0, retorno.length()-1);
                    }
                }
        } catch (Exception e) {
            throw new Exception("Problemas ao ler Result Set.\n"+e.getMessage());
        }
        return retorno;
    }    
    
    public List<View> obterListaDeViews(String palavraPesquisa, Connection conexao) throws Exception{
        try {  
            
            if (palavraPesquisa.equals("")){
                throw new Exception("Palavra-chave para pesquisa não informada.");
            }
            
            List<View> retorno = new ArrayList<View>();
            
            View view = null;
            String sqlConsulta = "SELECT view_name, text_length, text FROM all_views "
                                +"WHERE upper(view_name) like upper('%"+palavraPesquisa+"%')";
            
            ResultSet rs = this.execScriptManual(conexao, sqlConsulta);
            
            while (rs.next()){
                view = new View(rs.getString("view_name"), rs.getString("text"), rs.getString("text_length"));
                retorno.add(view);
            }  
            rs.getStatement().close();
            rs.close();
            return retorno;
        } catch (Exception e) {
            this.fecharConexaoOracle(conexao);
            throw new Exception("Problemas ao obter lista de Views.\n"+e.getMessage());
        }
    }

    public List<Job> obterListaDeJobs(String palavraPesquisa, Connection conexao) throws Exception{
        try {  
            
            if (palavraPesquisa.equals("")){
                throw new Exception("Palavra-chave para pesquisa não informada.");
            }
            
            List<Job> retorno = new ArrayList<Job>();
            
            Job job = null;
//            String sqlConsulta = "SELECT job, COMANDO, decode(status,'Y', 'Sim','Não') status FROM job_v "
//                                +"WHERE upper(COMANDO) like upper('%"+palavraPesquisa+"%')";
            
            String sqlConsulta = "select job\n" +
                                "      ,substr(priv_user,1,10) banco\n" +
                                "      ,substr(last_date||' '||last_sec,1,18)  \"ULT_EXEC\"\n" +
                                "      ,substr(next_date||' '||next_sec,1,18)  \"PROX_EXEC\"\n" +
                                "      ,TOTAL_TIME/1000      \"DUR_SEG\"\n" +
                                "      ,BROKEN      \"STATUS\"\n" +
                                "      ,FAILURES      \"FALHAS\"\n" +
                                "      ,substr(schema_user,1,10)               \"OWNER\"\n" +
                                "      ,substr(what,1,128)               \"COMANDO\"\n" +
                                "      , broken \"PARADA\"\n" +
                                "from user_jobs \n" +
                                "WHERE upper(substr(what,1,128)) like upper('%"+palavraPesquisa+"%') " +
                                "order by 4 desc";
            
            //System.out.println(sqlConsulta);
            
            ResultSet rs = this.execScriptManual(conexao, sqlConsulta);
            
            while (rs.next()){
                job = new Job(rs.getString("job"), rs.getString("COMANDO"), rs.getString("status"));
                retorno.add(job);
            }  
            rs.getStatement().close();
            return retorno;
        } catch (Exception e) {
            this.fecharConexaoOracle(conexao);
            throw new Exception("Problemas ao obter lista de Jobs.\n"+e.getMessage());
        }
    }    
    
    public void criarArquivoTxtObjetoOracle(ObjetoOracle objOracle, String localSalvar) throws Exception{
        try {
            String conteudo = objOracle.getCodigoObjeto();
            String nome     = objOracle.getNomeObjeto()+".txt";            
            FileWriter arquivoTxt = new FileWriter(localSalvar+"/"+nome);
            arquivoTxt.write(conteudo);
            arquivoTxt.close();            
        } catch (Exception e) {
            throw new Exception("Problemas ao gerar txt dos objetos selecionados.\n"+e.getMessage());
        }
    }
    
    
    public void criarListaTxtObjetoOracle(List<ObjetoOracle> ldtObjOracle, String localSalvar) throws Exception{
        try {
            String conteudo = "";
            for (ObjetoOracle objetoOracle : ldtObjOracle) {
                conteudo += objetoOracle.toString()+"\r\n";
            }
            
            
            String nome     = "lista_jobs_unseen.txt";            
            FileWriter arquivoTxt = new FileWriter(localSalvar+"/"+nome);
            arquivoTxt.write(conteudo);
            arquivoTxt.close();            
        } catch (Exception e) {
            throw new Exception("Problemas ao gerar txt dos objetos selecionados.\n"+e.getMessage());
        }
    }    
    
    public List<Procedure> obterListaDeProcedure(String palavraPesquisa, Connection conexao) throws Exception{
        try {
            if (palavraPesquisa.equals("")){
                throw new Exception("Palavra-chave para pesquisa não informada.");
            }
            List<Procedure> retorno = new ArrayList<Procedure>();
            
            Procedure procedure = null;
            String sqlProcedure = "select OBJECT_NAME name from USER_OBJECTS a where upper(a.OBJECT_NAME) like upper('%"+palavraPesquisa+"%') and OBJECT_TYPE = 'PROCEDURE'";
            ResultSet rs = this.execScriptManual(conexao, sqlProcedure);
            
            UBarraProgresso frame = new UBarraProgresso();
            String sqlQtd = "select count(*) qtd from USER_OBJECTS a where upper(a.OBJECT_NAME) like upper('%"+palavraPesquisa+"%') and OBJECT_TYPE = 'PROCEDURE'";
            ResultSet rsQtd = this.execScriptManual(conexao, sqlQtd);            
            rsQtd.next();
            frame.redefiniProgressBar(0, rsQtd.getInt("qtd"));            
            frame.setVisible(true);
            int contador=0;
            while (rs.next()){
                procedure = new Procedure(rs.getString("name"),this.obterTextoObjeto(rs.getString("name"), conexao));
                retorno.add(procedure);
                contador++;
                frame.atualizaProgressBar("Consultando Procedures", contador); 
                
            }               
            rs.getStatement().close();
            rs.close();
            frame.dispose();
            return retorno;  
        } catch (Exception e) {
            this.fecharConexaoOracle(conexao);
            throw new Exception("Problemas ao obter lista de procedure.\n"+e.getMessage());
        }        
    }    
    
    public List<Funcao> obterListaDeFuncoes(String palavraPesquisa, Connection conexao) throws Exception{
        try {
            if (palavraPesquisa.equals("")){
                throw new Exception("Palavra-chave para pesquisa não informada.");
            }
            List<Funcao> retorno = new ArrayList<Funcao>();
            
            Funcao funcao = null;
            String sqlProcedure = "select OBJECT_NAME name from USER_OBJECTS a where upper(a.OBJECT_NAME) like upper('%"+palavraPesquisa+"%') and OBJECT_TYPE = 'FUNCTION'";
            ResultSet rs = this.execScriptManual(conexao, sqlProcedure);
            
            
            UBarraProgresso frame = new UBarraProgresso();
            String sqlQtd = "select count(*) qtd from USER_OBJECTS a where upper(a.OBJECT_NAME) like upper('%"+palavraPesquisa+"%') and OBJECT_TYPE = 'FUNCTION'";
            ResultSet rsQtd = this.execScriptManual(conexao, sqlQtd);            
            rsQtd.next();
            frame.redefiniProgressBar(0, rsQtd.getInt("qtd"));            
            frame.setVisible(true);            
            int contador=0;
            
            while (rs.next()){
                funcao = new Funcao(rs.getString("name"),this.obterTextoObjeto(rs.getString("name"), conexao));
                retorno.add(funcao);
                contador++;
                frame.atualizaProgressBar("Consultando Funções", contador);
            }  
            rs.getStatement().close();
            rs.close();
            frame.dispose();
            return retorno;  
        } catch (Exception e) {
            this.fecharConexaoOracle(conexao);
            throw new Exception("Problemas ao obter lista de funções.\n"+e.getMessage());
        }        
    }    
    
    public String obterTextoObjeto(String objetoBanco, Connection conexao) throws Exception{
        try {            
            String sqlConsulta = "select line, text from user_source a where upper(a.name) = upper('"+objetoBanco+"') order by name, line";
            ResultSet rs = this.execScriptManual(conexao, sqlConsulta);
            String retorno = "";
            while(rs.next()){
                retorno += rs.getString("text");
                retorno += " \r\n ";
            }
            rs.getStatement().close();
            return retorno;
        } catch (Exception e) {
            this.fecharConexaoOracle(conexao);
            throw new Exception("Problemas ao obter texto de objeto ("+objetoBanco+").\n"+e.getMessage());
        }        
    }

    public String lerArquivo(File arquivo){
        try {
            String retorno = "";
            String linhaAtual = "";
            FileReader fr = new FileReader(arquivo);
            BufferedReader lerArq = new BufferedReader(fr);
            linhaAtual = lerArq.readLine();
            
            while (linhaAtual != null) {                
                retorno += linhaAtual+"\n";
                linhaAtual = lerArq.readLine();
            }
            fr.close();
            return retorno;
        } catch (Exception e) {
            return "NÃO FOI POSSIVEL LER O ARQUIVO.\n"+e.getMessage();
        }        
    }
    
    public String obterNomeObjetoNoScript(File arquivoScript) throws Exception{
        try {
            String retorno = "";
            String linhaAtual = "";
            FileReader fr = new FileReader(arquivoScript);
            BufferedReader lerArq = new BufferedReader(fr);
            linhaAtual = lerArq.readLine();
            
            while (linhaAtual != null) {                
                retorno += linhaAtual+"\n";
                linhaAtual = lerArq.readLine();//To do: verificar se na linha tem o comando create or replace... para obter no nome o objeto que será compilado no script
            }
            fr.close();
            return retorno;            
        } catch (Exception e) {
            throw new Exception("Erro ao obter nome do objeto no script.\n"+e.getMessage());
        }
    }
    
    public boolean verificaSeExisteObjetoInvalido(Connection conexao) throws Exception{
        try {
            //String usuario = 
            String sqlObjExistente = "SELECT 'ALTER '|| OBJECT_TYPE||' '||OBJECT_NAME||' COMPILE;' ds_comando,"
                                        +"substr(object_type,1,15) ds_tipo,"
                                        +"substr(object_name,1,40) nm_objeto,"
                                        +"obter_ordem_Objeto(object_type) nr_ordem"
                                      +"FROM USER_OBJECTS"
                                      +"WHERE OBJECT_TYPE IN('PROCEDURE', 'VIEW', 'TRIGGER', 'PACKAGE', 'FUNCTION')"
                                      +"AND STATUS = 'INVALID'"
                                      +"UNION ALL"
                                      +"SELECT 'ALTER PACKAGE '||OBJECT_NAME||' COMPILE BODY;' ds_comando,"
                                        +"substr(object_type,1,15) ds_tipo,"
                                        +"substr(object_name,1,40) nm_objeto"
                                      +"FROM USER_OBJECTS"
                                      +"WHERE OBJECT_TYPE in ('PACKAGE BODY')"
                                      +"AND STATUS = 'INVALID'";
            ResultSet rs = this.execScriptManual(conexao, sqlObjExistente);
            String tipoObj = this.lerResultSet(rs, "OBJECT_TYPE", "");
            rs.getStatement().close();
            rs.close();
            if (tipoObj.isEmpty()){
                return false;
            }else{
                return  true;
            }
        } catch (Exception e) {
            throw new Exception("Problemas ao verificar se há objetos invalidos!\n"+e.getMessage());            
        }
    }    
    
}
