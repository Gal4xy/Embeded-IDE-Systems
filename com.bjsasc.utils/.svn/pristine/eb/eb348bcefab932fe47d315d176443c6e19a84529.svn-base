package com.bjsasc.utils;


import java.sql.*;
public class Connection_access {
    private static String database ="person.mdb";
    private String table = "testcase";
   
    public Connection conn = null;
    public ResultSet result = null;
    Statement stmt = null;
    public Connection_access() {
    	this(database);
    }
    public Connection_access(String dbPath) {
        try{
        	 String strurl="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+dbPath;
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn=DriverManager.getConnection(strurl) ;
            stmt = conn.createStatement();
            }catch(Exception ex){
                System.out.println(ex);
                System.exit(0);
            }

    }
    public void setTable(String tableName){
    	this.table = tableName;
    }
    public ResultSet show()
    {
      
         try  {
          result = stmt.executeQuery("SELECT * FROM "+table);
       }catch(SQLException e){e.printStackTrace();}
		return result;
       
    }
    public ResultSet show(String item)
    {
      
         try  {
          result = stmt.executeQuery("SELECT "+item+" FROM "+table );//+" ORDER by casename"
       }catch(SQLException e){e.printStackTrace();}
		return result;
       
    }

     public void insert(String key,String value)
	  {
	      try{
	           stmt.executeUpdate("INSERT INTO "+table+"("+key+") VALUES ('"+value+"')");
	         }catch(SQLException e){ e.printStackTrace();}
	  }

       public void delete(String key,String value)
	  {
	      try{
	          stmt.executeUpdate("DELETE FROM "+table+" WHERE "+key+" = '"+value+"'");
	         }catch(SQLException e){e.printStackTrace(); }
	  }
       /*
       public void copy(String newkey,String curkey)
 	  {
 	      try{
 	          stmt.executeUpdate("INSERT INTO "+table+"(casename,ioro,iotype,mode,var_name,startT,intervalT,tiggerAddr,monitorAddr,monitorLen,iotime,iovalue,iotxtfile,iobinfile) select '"+newkey+"',ioro,iotype,mode,var_name,startT,intervalT,tiggerAddr,monitorAddr,monitorLen,iotime,iovalue,iotxtfile,iobinfile from testcase where casename='"+curkey+"'");
 	         }catch(SQLException e){e.printStackTrace(); }
 	  }
       
       public void update(String[] s)
       {
	      try{
	    	  String str ="UPDATE "+table+" SET ioro = '"+s[2]+"', iotype='"+s[3]+"', mode='"+s[4]+"', var_name='"+s[5]+"', startT='"+s[6]+"', intervalT='"+s[7]+"', tiggerAddr='"+s[8]+"', monitorAddr='"+s[9]+"', monitorLen='"+s[10]+"', iotime='"+s[11]+"', iovalue='"+s[12]+"', iotxtfile='"+s[13]+"', iobinfile='"+s[14]+"'  WHERE casename = '"+s[1]+"'";
	    	  System.out.println(str);
	          stmt.executeUpdate(str);
	          }catch(SQLException e){e.printStackTrace(); }
	  }*/

      public ResultSet search(String key,String value)
	  {
	      try
	      {
	          if(!key.equals(""))
	          {
	             result = stmt.executeQuery("SELECT * FROM "+table+" WHERE "+key+" = '"+value+"'");
	          }
	          else result = null;
	      }catch(SQLException e){e.printStackTrace(); }
	      return result;
	  }
      public boolean find(String key,String value)
	  {
	      try
	      {
	          if(!key.equals(""))
	          {
	             result = stmt.executeQuery("SELECT * FROM "+table+" WHERE "+key+" = '"+value+"'");
	          }
	          else result = null;
	      }catch(SQLException e){e.printStackTrace(); }
	      try {
			if(result==null||result.next()==false)
				  return false;
			else
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return false;
	  }
      public boolean find(String condision)
	  {
	      try
	      {
	          if(!condision.equals(""))
	          {
	             result = stmt.executeQuery("SELECT * FROM "+table+" WHERE "+condision);
	          }
	          else result = null;
	      }catch(SQLException e){e.printStackTrace(); }
	      try {
			if(result==null||result.next()==false)
				  return false;
			else 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return false;
	  }
      public int getIntItem (String item,String condision)
	  {
	      try
	      {
	          if(!condision.equals(""))
	          {
	             result = stmt.executeQuery("SELECT "+item+" FROM "+table+" WHERE "+condision);
	          }
	          else result = null;
	      }catch(SQLException e){e.printStackTrace(); }
	      int value = -1;
	      try {
	  			while(result.next())
	  			{
					value = result.getInt(1);
	  			}
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      return value;
	  }
}
