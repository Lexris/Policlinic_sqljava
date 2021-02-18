package MainUI;

import com.mysql.cj.conf.ConnectionUrlParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;


public class MainModel {
    public Connection connection;
    public int tableCount=0;

    /**
     * Connecting connection to the main UI
     * @param connection conexiunea la care ne conectam
     */
    public MainModel(Connection connection) {
        this.connection = connection;
    }


    public void executeProcedure(JTable selectTable, String procedure, String params) {
        Statement statement = null;
        ResultSetMetaData rsmd = null;
        ResultSet resultSet = null;
        String instruction = "CALL " + procedure + "(" + params + ")";
        System.out.println(instruction);

         //Initializare tabel
        DefaultTableModel model = new DefaultTableModel();
        selectTable.setModel(model);

        try {
            statement = connection.createStatement();

            //Daca avem resultSet il printam
            if(statement.execute(instruction)) {
                resultSet = statement.getResultSet();
                rsmd = resultSet.getMetaData();

                //Adaugam titlurile coloanelor in prima linia a tabelului
                String[] tableHeader = new String[rsmd.getColumnCount()];
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    tableHeader[i-1] = rsmd.getColumnName(i);
                }
                model.setColumnIdentifiers(tableHeader);
                model.setColumnCount(rsmd.getColumnCount());

                //La urmatoarele linii adaugam seturile de valori
                int rowCount = 0;
                model.setRowCount(1);
                while (resultSet.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        selectTable.setValueAt(resultSet.getString(rsmd.getColumnName(i)), rowCount, i-1);
                    }
                    rowCount++;
                    model.setRowCount(rowCount+1);
                }

            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) {
                }
                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
                statement = null;
            }
        }

    }

    /**
     * Utilizata pt cazul in care facem SELECT
     * @param selectTable tabelul de output
     * @param tableName numele tabelei la care cautam coloanele
     * @param columns de forma @nume, age, etc@ fara @-uri
     */
    public void selectOperation(JTable selectTable, String tableName, String columns) {
        Statement statement = null;
        ResultSetMetaData rsmd = null;
        ResultSet resultSet = null;

        //Initializare intructiune de select
        if(columns.equals(""))
            columns = "*";
        String instruction = "SELECT " + columns + " FROM " + tableName;
        System.out.println(instruction);

        //Initializare tabel
        DefaultTableModel model = new DefaultTableModel();
        selectTable.setModel(model);

        //Operatii cu db-ul
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(instruction);
            resultSet = statement.getResultSet();
            rsmd = resultSet.getMetaData();

            //Adaugam titlurile coloanelor in prima linia a tabelului
            String[] tableHeader = new String[rsmd.getColumnCount()];
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                tableHeader[i-1] = rsmd.getColumnName(i);
            }
            model.setColumnIdentifiers(tableHeader);
            model.setColumnCount(rsmd.getColumnCount());

            //La urmatoarele linii adaugam seturile de valori
            int rowCount = 0;
            model.setRowCount(1);
            while (resultSet.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    selectTable.setValueAt(resultSet.getString(rsmd.getColumnName(i)), rowCount, i-1);
                }
                rowCount++;
                model.setRowCount(rowCount+1);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) {
                }
                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
                statement = null;
            }
        }
    }

    /**
     * Utilizata pt cazul in care facem INSERT
     * @param tableName numele tabelei in care facem insert
     * @param columns coloanele in care facem insert
     * @param values valorile
     */
    public void insertOperation(String tableName, String columns, String values) {
        Statement statement = null;
        //System.out.println(tableName + " " + columns + " " + values);
        String instruction = "INSERT INTO " + tableName + " (" + columns + ") VALUES " + "(" + values + ")";
        //System.out.println(instruction);

        try {
            statement = connection.createStatement();
            statement.execute(instruction);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
                statement = null;
            }
        }
    }

    /**
     * Only the user available tables and their available operations will be shown in the UI
     * @return tablesOperations
     */
    public ConnectionUrlParser.Pair<Vector<String>, HashMap<String, Vector<String>>> getAccessibleTables() {

        //Declaram variabilele de conexiune intre interfata si server
        Statement statement = null;
        ResultSetMetaData rsmd = null;
        ResultSet resultSet = null;
        ResultSet resultSetAux = null;

        //Declaram variabilele de iesire(cele doua parti alea obiectului de iesire Pair)
        Vector<String> accessibleTables = new Vector<String>();
        HashMap<String, Vector<String>> accessibleOperationsOnTables = new HashMap<String, Vector<String>>();

        //Initializam variabilele de conexiune intre interfata si server si user-ul curent si toate tabelele disponibile acestuia
        tableCount=0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW TABLES");
            resultSet = statement.getResultSet();
            rsmd = resultSet.getMetaData();

            //Adaugam fiecare nume de tablela in vectorul care retine tabelele disponibile @accessibleTables
            while (resultSet.next()) {
                accessibleTables.add(resultSet.getString(rsmd.getColumnName(1))); //table name "Tables_in_polyclinic_chain"
                tableCount++;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Gasit toate procedurile available user-ului
        try {
            //Pe coloana 2 in query vom avea numele tuturor procedurilor available user-ului curent
            resultSet = statement.executeQuery("SHOW PROCEDURE STATUS WHERE Db = 'polyclinic_chain'");
            resultSet = statement.getResultSet();

            //Adaugam la fiecare procedura dreptul "USAGE" care evident exista daca e returnata procedura de catre mySQL
            while (resultSet.next()) {
                accessibleTables.add(resultSet.getString("Name"));
                Vector<String> usageAccessForProcedure = new Vector<String>(Collections.singleton("USAGE"));    //no idea what a singleton is but well :/
                accessibleOperationsOnTables.put(resultSet.getString("Name"),  usageAccessForProcedure);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Determinam toate operatiile disponibile user-ului pe tabelele disponibile
        try {
            resultSetAux = statement.executeQuery("SELECT user()");
            resultSetAux = statement.getResultSet();
            resultSetAux.next();

            resultSet = statement.executeQuery("SHOW GRANTS FOR " + resultSetAux.getString("user()"));
            resultSet = statement.getResultSet();
            rsmd = resultSet.getMetaData();

            try {
                //resultSet.next();
                while (resultSet.next()) {
                    String resultString = resultSet.getString(rsmd.getColumnName(1));
                    //System.out.println(resultString);

                    //Verificam ce tabela are legatura cu row-ul query-ului(doar tabelele nu si procedurile)
                    for (int i = 0; i < tableCount; i++) {

                        //Cand avem chestii cu * la priveleges
                        if (resultString.contains("ON *.*")) {

                            //Vectorul unde punem operatiile available pt fiecare tabel
                            Vector<String> vecAux = new Vector<String>();

                            //Parcurgem query-ul si luam fiecare operatie disponibila pt fiecare tabela
                            for (String word : resultString.split(",")) {
                                //System.out.println(word);

                                //Se poate sa apara grant si mai pe la sfarsit si strica treaba
                                if ((word.contains("GRANT ") && !word.contains(" GRANT ")) && word.contains(" ON ")) {
                                    String[] strAux = word.split(" ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[1].replaceAll(" ", ""));
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.get(i)));
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    continue;
                                }

                                //Ultima operatie disponibila adaugata
                                else if (word.contains(" ON ")) {
                                    String[] strAux = word.split(" ON ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[0].replaceAll(" ", ""));
                                        //System.out.println(vecAux.lastElement());
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    break;
                                }

                                //Prima operatie disponibila adaugata
                                else if (word.contains("GRANT ")) {
                                    String[] strAux = word.split(" ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[1].replaceAll(" ", ""));
                                        //System.out.println(vecAux.lastElement());
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    continue;
                                }

                                //Exceptie in caz ca e null vecAux care retine toate operatiile available pt fiecare tabela
                                try {
                                    //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                    vecAux.add(word.replaceAll(" ", ""));
                                    //System.out.println(vecAux.lastElement());
                                } catch (NullPointerException e) {
                                    System.out.println("GOT SOME NULL AFTER replaceAll");
                                }
                            }

                            //Daca exista deja creat vectorul adaugam la el vectorul nou, altfel punem direct vectorul nou creat
                            if (accessibleOperationsOnTables.containsKey(accessibleTables.get(i)))
                                accessibleOperationsOnTables.get(accessibleTables.get(i)).addAll(vecAux);
                            else accessibleOperationsOnTables.put(accessibleTables.get(i), vecAux);
                        }

                        //Cand avem chestii fara * la privileges
                        else if (resultString.contains(accessibleTables.get(i))) {

                            //Vectorul unde punem operatiile available pt fiecare tabel
                            Vector<String> vecAux = new Vector<String>();

                            //Parcurgem query-ul si luam fiecare operatie disponibila pt fiecare tabela
                            for (String word : resultString.split(",")) {

                                //Se poate sa apara grant si mai pe la sfarsit si strica treaba
                                if ((word.contains("GRANT ") && !word.contains(" GRANT ")) && word.contains(" ON ")) {
                                    String[] strAux = word.split(" ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[1].replaceAll(" ", ""));
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.get(i)));
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    continue;
                                }

                                //Ultima operatie disponibila adaugata
                                if (word.contains(" ON ")) {
                                    String[] strAux = word.split(" ON ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[0].replaceAll(" ", ""));
                                        //System.out.println(vecAux.lastElement());
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    break;
                                }

                                //Prima operatie disponibila adaugata
                                if (word.contains("GRANT ")) {
                                    String[] strAux = word.split(" ");
                                    try {
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                        vecAux.add(strAux[1].replaceAll(" ", ""));
                                        //System.out.println(accessibleOperationsOnTables.get(accessibleTables.get(i)));
                                    } catch (NullPointerException e) {
                                        System.out.println("GOT SOME NULL AFTER replaceAll");
                                    }
                                    continue;
                                }

                                //Exceptie in caz ca e null vecAux care retine toate operatiile available pt fiecare tabela
                                try {
                                    //System.out.println(accessibleOperationsOnTables.get(accessibleTables.elementAt(i)));
                                    vecAux.add(word.replaceAll(" ", ""));
                                    //System.out.println(accessibleOperationsOnTables.get(accessibleTables.get(i)));
                                } catch (NullPointerException e) {
                                    System.out.println("GOT SOME NULL AFTER replaceAll");
                                }
                            }

                            //Daca exista deja creat vectorul adaugam la el vectorul nou, altfel punem direct vectorul nou creat
                            if (accessibleOperationsOnTables.containsKey(accessibleTables.get(i)))
                                accessibleOperationsOnTables.get(accessibleTables.get(i)).addAll(vecAux);
                            else accessibleOperationsOnTables.put(accessibleTables.get(i), vecAux);
                            break;
                        }
                    }
                }
            } catch (NullPointerException ex) {
                System.out.println("Got some null getting table operations");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {

            //Inchidem resultSet(aka chestia care ia rezultatele de la query-uri)
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) {
                }
                resultSet = null;
            }

            //Inchidem statement(aka chestie care face query-uri)
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
                statement = null;
            }

            //Inchidem resultSetAux(cu care am vazut ce user face conexiunea)
            if (resultSetAux != null) {
                try {
                    resultSetAux.close();
                } catch (SQLException sqlEx) {
                }
                resultSetAux = null;
            }
        }

        //Asignam variabila de iesire care retine toate tabelele disponibile user-ului(Pair.left) si toate operatiile dispobibile pt fiecare dintre acestea(Pair.right)
        ConnectionUrlParser.Pair<Vector<String>, HashMap<String, Vector<String>>> tablesOperations = new ConnectionUrlParser.Pair(accessibleTables, accessibleOperationsOnTables);
        return tablesOperations;
    }

    /**
     * Only the user available columns will be shown in the UI when doing column dependant operations
     * @return tablesOperations
     */
    public ConnectionUrlParser.Pair<HashMap<String, Vector<String>>, HashMap<String, Vector<String>>> getAccessibleColumns(Vector<String> accessibleTables) {
        HashMap<String, Vector<String>> tableColumns = new HashMap<String, Vector<String>>();
        HashMap<String, Vector<String>> tableColumnsType = new HashMap<String, Vector<String>>();

        //Declaram variabilele de conexiune intre interfata si server
        Statement statement = null;
        ResultSetMetaData rsmd = null;
        ResultSet resultSet = null;
        ResultSet resultSetAux = null;

        for (int i=0; i<tableCount; i++) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SHOW COLUMNS IN " + accessibleTables.get(i));//SHOW COLUMNS IN ;
                resultSet = statement.getResultSet();
                rsmd = resultSet.getMetaData();

                 //Adaugam fiecare nume de 'Field' de coloana in vectorul care retine coloanele disponibile @tableColumns
                 //si fiecare tip de data de coloana in @tableColumnsType
                while (resultSet.next()) {
                    if(tableColumns.containsKey(accessibleTables.get(i))) {
                        tableColumns.get(accessibleTables.get(i)).add(resultSet.getString(rsmd.getColumnName(1)));
                        tableColumnsType.get(accessibleTables.get(i)).add(resultSet.getString(rsmd.getColumnName(2)));
                    }
                    else {
                        Vector<String> colVect = new Vector<String>();
                        colVect.add(resultSet.getString(rsmd.getColumnName(1)));
                        tableColumns.put(accessibleTables.get(i), colVect);

                        Vector<String> colTypeVect = new Vector<String>();
                        colTypeVect.add(resultSet.getString(rsmd.getColumnName(2)));
                        tableColumnsType.put(accessibleTables.get(i), colTypeVect);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }

        ConnectionUrlParser.Pair<HashMap<String, Vector<String>>, HashMap<String, Vector<String>>> accessibleColumnsAndTypes = new ConnectionUrlParser.Pair<>(tableColumns, tableColumnsType);
        return accessibleColumnsAndTypes;
    }

    /**
     * End connection if there is one
     */
    public void endConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
            connection = null;
        }
    }
}
