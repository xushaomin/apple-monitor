/**
 * Copyright 2004-2006 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.core.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * date:  Apr 27, 2006
 * @author	Vinit Srivastava
 */
public class JManageHSQLDB implements JManageDB {
    public  boolean available = false;
    private Connection conn;
    private Statement  stmt;
    
    JManageHSQLDB() {
      
    }
    public boolean isAvailable()
    {
      return available;
    }

    private void start() {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:hsqldb:../data/db","sa","");
            stmt = conn.createStatement();
            
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "%", null);
            
            boolean found = false;

            while (rs.next()) {
                    String s = rs.getString(3);
                    if (s.equalsIgnoreCase("T_ATTRIBUTES")){
                       found = true;
                    }
            }
            if (!found) {
              stmt.executeUpdate("CREATE CACHED TABLE T_ATTRIBUTES (ATTRIBUTE_ID IDENTITY NOT NULL PRIMARY KEY,  APPLICATION_NAME VARCHAR(100), MBEAN_NAME VARCHAR(100), ATTRIBUTE_NAME VARCHAR(100),  POLL_INTERVAL DECIMAL)");
              stmt.executeUpdate("CREATE CACHED TABLE T_ATTRIBUTE_VALUES (ATTRIBUTE_ID INTEGER, ATTRIBUTE_VALUE NUMERIC, TIME TIMESTAMP default 'now')");                                  
            }
            available = true;
        } catch (IllegalAccessException e) {
             e.printStackTrace();
        } catch (InstantiationException e) {
             e.printStackTrace();
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }

    public void stop() {
      if (available){
            try {
                conn.close();
                stmt.execute("shutdown");                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
  /**
   * @param rs
   * @param wantedColumnNames
   * @return
   * @throws SQLException
   */
  public static final List <Map> toList(ResultSet rs, List <String> wantedColumnNames)
    throws SQLException
  {
    List <Map> rows = new ArrayList < Map > ();

    int numWantedColumns = wantedColumnNames.size();
    while (rs.next())
    {
      Map row = new HashMap ();

      for (int i = 0; i < numWantedColumns; ++i)
      {
	String columnName = wantedColumnNames.get(i);
        Object value = rs.getObject(columnName);
        row.put(columnName, value);
      }

      rows.add(row);
    }

    return rows;
  }
  public List <Map> getAttributeList()
  {
    List attList = new ArrayList<Map>();
    List <String> attNames = new ArrayList <String> ();
    
    attNames.add("ATTRIBUTE_ID");
    attNames.add("ATTRIBUTE_NAME");
    attNames.add("APPLICATION_NAME");
    attNames.add("MBEAN_NAME");
    attNames.add("POLL_INTERVAL");
    
    if (!available)
    {
      start();
    }
    
    String query = "SELECT ATTRIBUTE_ID, ATTRIBUTE_NAME, APPLICATION_NAME, MBEAN_NAME, POLL_INTERVAL FROM T_ATTRIBUTES";
    ResultSet rs;
    try
    {
      rs = stmt.executeQuery(query);
      attList = JManageHSQLDB.toList(rs, attNames);
      rs.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return attList;
  }
  public Connection getConnection()
  {
    if  ( !available ) {
      start();
    }
    return conn;
  }
}

