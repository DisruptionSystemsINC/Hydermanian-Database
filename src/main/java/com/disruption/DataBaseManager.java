package com.disruption;

import com.disruptionsystems.logging.LogLevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseManager {

    private Connection conn;

    public DataBaseManager establishConnection(String databaseAddress) {
        try {
            conn = DriverManager.getConnection(databaseAddress);
            if (conn != null){
                return this;
            }
        } catch (SQLException e){
            HyDB.getLogger().printToLog(LogLevel.ERROR, "COULD NOT CONNECT TO DATABASE: " + e.getMessage());
        }
        return null;
    }

    public Connection getConnection() {return this.conn;}

    private ResultSet getResultSet(String sql, Connection conn) {
        ResultSet set;
        try {
            Statement statement = conn.createStatement();
            set = statement.executeQuery(sql);
            return set;
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED," + e.getMessage());
        }
        return null;
    }

    private void setStatement() {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS LANGDB (ENGLISH VARCHAR NOT NULL, HYDERMANIAN VARCHAR NOT NULL, id INT PRIMARY KEY AUTOINCREMENT)");
            statement.close();
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage() + "\n" + Arrays.stream(e.getStackTrace()).toList());
        }
    }

    public String getPosField(String pos) {
        ResultSet rs = getResultSet("SELECT * FROM LANGDB WHERE POS='" + pos + "'", conn);
        String result = "";
        try {
            result = rs.getString("pos");
            rs.close();
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage());
        }
        return result;
    }

    public void delById(String Id) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM LANGDB WHERE id='" + Id + "'");
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage() + "\n" + Arrays.stream(e.getStackTrace()).toList());
        }
    }

    public void delByHyd(String hydermanian) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM LANGDB WHERE HYDERMANIAN='" + hydermanian + "'");
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage() + "\n" + Arrays.stream(e.getStackTrace()).toList());
        }
    }

    public String getValue(String hydermanian) {
        ResultSet rs = getResultSet("SELECT * FROM LANGDB WHERE HYDERMANIAN='" + hydermanian + "'", conn);
        String result = "";
        try {
            result = rs.getString("value");
            rs.close();
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage());
        }
        return result;
    }

    public String[] getValues() {
        ResultSet rs = getResultSet("SELECT ENGLISH FROM LANGDB", conn);
        try {
            List<String> result = new ArrayList<>();
            while (rs.next()){
                result.add(rs.getString("ENGLISH"));
            }
            rs.close();
            return result.toArray(new String[0]);
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage());
        }
        return null;
    }

    public String[] getHydermanian() {
        ResultSet rs = getResultSet("SELECT HYDERMANIAN FROM LANGDB", conn);
        try {
            List<String> result = new ArrayList<>();
            while (rs.next()){
                result.add(rs.getString("pos"));
            }
            rs.close();
            return result.toArray(new String[0]);
        } catch (SQLException e) {
             HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage());
        }
        return null;
    }

    public String[] getIds() {
        ResultSet rs = getResultSet("SELECT id FROM LANGDB", conn);
        try {
            List<String> result = new ArrayList<>();
            while (rs.next()){
                result.add(rs.getString("id"));
            }
            rs.close();
            return result.toArray(new String[0]);
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage());
        }
        return null;
    }

    public void createBaseDatabaseStructure(){
        this.setStatement();
    }

    public void addEntry(String hydermanian, String english){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO LANGDB ('ENGLISH', HYDERMANIAN) VALUES (?, ?)");
            statement.setString(1, hydermanian);
            statement.setString(2, english);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            HyDB.getLogger().printToLog(LogLevel.ERROR, "ERROR: STATEMENT COULD NOT BE EXECUTED, " + e.getMessage() + "\n" + Arrays.stream(e.getStackTrace()).toList());
        }
    }
}
