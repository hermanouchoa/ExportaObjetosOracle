/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.*;

public class ConexaoOracle {
    //jdbc:oracle:thin:@192.168.1.57:1521:tasy_tst
    private String url = "";  
    private String usr = "";  
    private String pwd = "";      
    private Connection con = null;  
      
    public ConexaoOracle(){}  
      
    public Connection conectaBanco(String usuario, String senha, String servico, String host, String porta) throws SQLException, ClassNotFoundException{  
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        url = "jdbc:oracle:thin:@"+host+":"+porta+":"+servico;  
        usr = usuario;  
        pwd = senha;  
        con = null;        
        con = DriverManager.getConnection(url,usr,pwd);  
        return con;  
    }  
      
    public Connection fechaConexaoBanco() throws SQLException, ClassNotFoundException{  
        if(con != null){  
           con.close();  
        }  
        return con;  
    }
    
    public void fechaConexaoBanco(Connection conexaoAberta) throws SQLException, ClassNotFoundException{  
        if(conexaoAberta != null){  
           conexaoAberta.close();
        }          
    }
    
    public ResultSet executaSql(Connection conexao,String query) throws SQLException {
        Statement st = conexao.createStatement();        
        ResultSet rs = st.executeQuery(query);        
        return rs;
    }
    
    public boolean executeInsert(Connection conexao, String comandoInsert) throws SQLException{
        boolean retorno = false;
        try {
            PreparedStatement ps = conexao.prepareStatement(comandoInsert);
            ps.execute();            
            ps.close();         
            retorno=true;
        } catch (Exception e) {
            retorno=false;
        }        
        return retorno;
    }
    
     public boolean executeUpdate(Connection conexao, String comandoUpdate) throws SQLException{
        boolean retorno = false;
        try {
            PreparedStatement ps = conexao.prepareStatement(comandoUpdate);
            ps.execute();            
            ps.close();         
            retorno=true;
        } catch (Exception e) {
            retorno=false;
        }        
        return retorno;
    }
    
    public boolean executaProcedure(Connection conexao,String parametros, String usuario, String procedure) throws SQLException, ClassNotFoundException {
        boolean retorno=false;
        CallableStatement cs = conexao.prepareCall("{ call "+usuario+"."+procedure+"("+parametros+") }");
        cs.execute();                    
        retorno=true;
        return retorno;
    }
    
    public boolean criaObjetoBanco(Connection conexao,String script) throws SQLException{
        boolean retorno=false;
        Statement st = conexao.createStatement();
        st.addBatch(script);
        st.executeBatch();
        retorno=true;
        return retorno;
    }    
}