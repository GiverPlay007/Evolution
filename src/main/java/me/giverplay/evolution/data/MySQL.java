package me.giverplay.evolution.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.giverplay.evolution.Evolution;

public class MySQL
{
  private final Evolution plugin;
  private final String host;
  private final String database;
  private final String user;
  private final String pass;
  private final String url;
  
  private final int port;
  
  private Connection connection;
  
  public MySQL(String host, int port, String database, String user, String pass)
  {
    this.host = host;
    this.port = port;
    this.database = database;
    this.user = user;
    this.pass = pass;
    this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
    
    this.plugin = Evolution.getInstance();
  }
  
  public void connect()
  {
    try
    {
      connection = DriverManager.getConnection(url, user, pass);
    }
    catch(SQLException exception)
    {
      plugin.getLogger().warning("Falha ao conectar com o MySQL: " + url);
      exception.printStackTrace();
    }
  }
  
  public void disconnect()
  {
    if(connection != null)
    {
      try
      {
        connection.close();
      }
      catch(SQLException e)
      {
        plugin.getLogger().warning("Falha ao fechar a conexão MySQL: " + url);
        e.printStackTrace();
      }
    }
  }
  
  public boolean isConnected()
  {
    if(connection == null)
    {
      return false;
    }
    
    try(
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT 1")
    )
    {
      return true;
    }
    catch(Exception ignored) { }
    
    return false;
  }
  
  public Connection getConnection()
  {
    if (connection == null || !isConnected())
    {
      plugin.getLogger().warning("MySQL desconectado, reconectando...");
      connection = null;
      connect();
    }
    
    return connection;
  }
  
  public String getHost()
  {
    return host;
  }
  
  public int getPort()
  {
    return port;
  }
  
  public String getDatabase()
  {
    return database;
  }
  
  public String getPassword()
  {
    return pass;
  }
  
  public String getUsername()
  {
    return user;
  }
}
