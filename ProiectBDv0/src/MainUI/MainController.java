package MainUI;

import ConnectionEstablishing.ConnectionEstablisher;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Vector;

public class MainController {

    /**
     * Establishing connection between model and view
     * @param model model
     * @param view view
     */
    public MainController(MainModel model, MainView view) {


        view.tableComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                //Facem ca atunci cand se schimba elementul din combobox-ul de tabele si proceduri sa se schimbe si combobox-ul de optiuni
                if(e.getStateChange() == ItemEvent.SELECTED) {

                    //Verificam intai daca exista drepturi pentru tabela respectiva[cred ca ar trebui intotdeauna sa existe(USAGE at least) ca altfel nu ar fi acolo]
                    if (view.tablesAndOperations.right.containsKey(view.tableComboBox.getSelectedItem())) {

                        //Atribuim noua lista de elemente la al doilea box in functie de selectia noua din primul box
                        view.operationsComboBox.setModel(new DefaultComboBoxModel(view.tablesAndOperations.right.get(view.tableComboBox.getSelectedItem())));
                    } else {

                        //Daca nu avem access la nimic la tabela selectata avem default-ul "ACCESS DENIED"
                        view.operationsComboBox.setModel(new DefaultComboBoxModel(new String[]{"ACCESS DENIED"}));
                    }

                    //Cand schimbam tabela nu mai vrem sa vedem insert decat daca se nimereste selectat
                    if (!view.operationsComboBox.getSelectedItem().toString().contains("INSERT"))//se poate aici sa nu se actualizeze daca e tot insert
                        view.insertTableScroll.setVisible(false);

                    //La fiecare tabela se actualizeaza coloanele din JList
                    //Daca in tabel nu e procedura(primul if), verificam daca avem selectat select sau insert pt a da display la lista de coloane
                    if (view.accessibleColumnsAndTypes.left.containsKey(view.tableComboBox.getSelectedItem().toString())) {
                        if (view.tablesAndOperations.right.get(view.tableComboBox.getSelectedItem().toString()).get(0).contains("SELECT") ||
                                view.tablesAndOperations.right.get(view.tableComboBox.getSelectedItem().toString()).get(0).contains("INSERT")) {
                            view.columnList.setListData(view.accessibleColumnsAndTypes.left.get(view.tableComboBox.getSelectedItem()));
                            view.columnListScroll.setVisible(true);
                        } else {
                            view.columnList.setListData(view.accessibleColumnsAndTypes.left.get(view.tableComboBox.getSelectedItem()));
                            view.columnListScroll.setVisible(false);
                        }
                    }
                    //Daca avem procedura selectata verificam cu switch ce procedura e si afisam parametrii de introdus in insertTable
                    else {
                        view.columnListScroll.setVisible(false);

                        DefaultTableModel insertTableModel = new DefaultTableModel();
                        switch (view.tableComboBox.getSelectedItem().toString()) {
                            case "SHOW_MY_DATA": {
                                insertTableModel.setColumnIdentifiers(new String[]{"TableName"});
                                break;
                            }
                            case "SHOW_TIME_TABLE":
                            case "SHOW_HOLIDAY":
                            case "SHOW_PATIENT_APPOINTMENTS":
                            case "SHOW_MEDIC_SCHEDULE":
                            case "SHOW_MEDIC_PROFIT": {
                                insertTableModel.setColumnIdentifiers(new String[]{"FirstName", "LastName"});
                                break;
                            }
                            case "SET_TIME_TABLE": {
                                insertTableModel.setColumnIdentifiers(new String[]{"FirstName", "LastName", "Date", "BeginsAt", "EndsAt"});
                                break;
                            }
                            case "SET_HOLIDAY": {
                                insertTableModel.setColumnIdentifiers(new String[]{"FirstName", "LastName", "StartingDate", "EndingDate"});
                                break;
                            }
                            case "SHOW_SUITABLE_UNITS": //mergeuit cu showqalifmedics
                            case "SHOW_QUALIFIED_MEDICS": {
                                insertTableModel.setColumnIdentifiers(new String[]{"ServiceName"});
                                break;
                            }
                            case "ADD_APPOINTMENT": {
                                insertTableModel.setColumnIdentifiers(new String[]{"PatientFirstName", "PatientLastName", "ServiceName", "PolyclinicName",
                                        "MedicFirstName", "MedicLastName", "Date", "BeginsAt"});
                                break;
                            }
                            case "ADD_MEDICAL_REPORT": {
                                insertTableModel.setColumnIdentifiers(new String[]{"PatientFirstName", "PatientLastName", "AppointmentID", "MedicalReport"});
                                break;
                            }
                            case "SHOW_PATIENT_HISTORY": {
                                insertTableModel.setColumnIdentifiers(new String[]{"PatientFirstName", "PatientLastName"});
                                break;
                            }
                            case "SHOW_MY_MEDIC_SCHEDULE":
                            case "SHOW_USERS":
                            case "SHOW_EMPLOYEES":
                            case "SHOW_PATIENTS":
                            case "SHOW_TOTAL_PROFIT": {
                                insertTableModel.setColumnIdentifiers(new String[]{});
                                break;
                            }
                            case "ADD_PATIENT": {
                                insertTableModel.setColumnIdentifiers(new String[]{"CNP", "FirstName", "LastName", "PhoneNumber", "Email"});
                                break;
                            }
                            case "ADD_NEW_ADMIN": {
                                insertTableModel.setColumnIdentifiers(new String[]{"Username", "Password", "CNP", "FirstName", "LastName",
                                        "PhoneNumber", "Email", "IBAN", "ContractNumber", "HiringDate"});
                                break;
                            }
                            case "ADD_NEW_ACCOUNTANT":
                            case "ADD_NEW_RECEPTIONIST":
                            case "ADD_NEW_INSPECTOR": {
                                insertTableModel.setColumnIdentifiers(new String[]{"Username", "Password", "CNP", "FirstName", "LastName",
                                        "PhoneNumber", "Email", "IBAN", "ContractNumber", "HiringDate", "NegociatedIncome", "WorkHours"});
                                break;
                            }
                            case "ADD_NEW_NURSE": {
                                insertTableModel.setColumnIdentifiers(new String[]{"Username", "Password", "CNP", "FirstName", "LastName",
                                        "PhoneNumber", "Email", "IBAN", "ContractNumber", "HiringDate", "NegociatedIncome", "WorkHours", "Type", "Grade"});
                                break;
                            }
                            case "ADD_NEW_MEDIC": {
                                insertTableModel.setColumnIdentifiers(new String[]{"Username", "Password", "CNP", "FirstName", "LastName",
                                        "PhoneNumber", "Email", "IBAN", "ContractNumber", "HiringDate", "NegociatedIncome", "WorkHours", "StampCode",
                                        "MedicalTitle", "AcademicPosition", "Comission", "Specialization", "Competence"});
                                break;
                            }
                            case "DELETE_ACCOUNT": {
                                insertTableModel.setColumnIdentifiers(new String[]{"Username"});
                                break;
                            }
                            default: {
                                System.out.println("Got to default in maincontroller line 105");
                                break;
                            }
                        }
                        insertTableModel.setRowCount(1);
                        view.insertTable.setModel(insertTableModel);
                        view.insertTableScroll.setVisible(true);
                        view.mainFrame.setVisible(true);

                    }
                }//acum se inchide aici if-ul cu getstatechange

                //Selecturile din trecut vrem mereu sa dispara
                view.tableScroll.setVisible(false);

                //Doar pt actualizarea frame-ului
                view.mainFrame.setVisible(true);
            }
        });


        //Cand avem in operation comboBox Insert sau Select afisam lista de coloane
        view.operationsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {

                    //Vrem coloanele doar daca facem SELECT sau INSERT si daca da, afisam lista de coloane
                    if(view.operationsComboBox.getSelectedItem().toString().contains("SELECT") || view.operationsComboBox.getSelectedItem().toString().contains("INSERT")) {
                        view.columnList.setListData(view.accessibleColumnsAndTypes.left.get(view.tableComboBox.getSelectedItem()));
                        view.columnListScroll.setVisible(true);
                    }

                    //Daca nu, ascundem lista de coloane(o si actualizam se pare, nu mai stiu dc)
                    else {
                        view.columnList.setListData(view.accessibleColumnsAndTypes.left.get(view.tableComboBox.getSelectedItem()));
                        view.columnListScroll.setVisible(false);
                    }

                    //Daca nu avem insert ascundem tabelul de input pt insert
                    if(!view.operationsComboBox.getSelectedItem().toString().contains("INSERT"))
                        view.insertTableScroll.setVisible(false);

                    //Doar pt actualizarea frame-ului
                    view.mainFrame.setVisible(true);
                }
            }
        });

        //La fiecare coloana noua selectata din lista, adaugam coloana respectiva in tabelul pt executarea insertului
        view.columnList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()){
                    if (view.operationsComboBox.getSelectedItem().toString().contains("INSERT")) {
                        DefaultTableModel insertTableModel = new DefaultTableModel();

                    /*for(String s : view.columnList.getSelectedValuesList().toString().replaceAll("[\\[\\] ]", "").split("[,]"))
                        System.out.print(s + " ");
                    System.out.println();*/

                        String[] selectedColumnNames = view.columnList.getSelectedValuesList().toString().replaceAll("[\\[\\] ]", "").split("[,]");
                        insertTableModel.setColumnIdentifiers(selectedColumnNames);
                        insertTableModel.setRowCount(1);
                        view.insertTable.setModel(insertTableModel);
                        view.insertTableScroll.setVisible(true);
                        view.mainFrame.setVisible(true);
                    }
                }
            }
        });

        //Adaugare tabel nou in functie de elementele selectate, FIXED ->(un bug unde doar primele doua linii din tabel pot fi doar selectate in interfata iar la pop-out pop-in sar datele in toate partile :/)
        view.executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Verificam sa vedem ce tip de instructiune vrem sa executam
                switch(view.operationsComboBox.getSelectedItem().toString()) {

                    //case usage for procedures
                    case "USAGE": {

                        //Legam fiecare functie din sql server de interfata
                        switch(view.tableComboBox.getSelectedItem().toString()) {
                            case "SHOW_MY_DATA":
                            case "SHOW_QUALIFIED_MEDICS":
                            case "SHOW_SUITABLE_UNITS": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "'");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "SHOW_TIME_TABLE":
                            case "SHOW_PATIENT_APPOINTMENTS":
                            case "SHOW_MEDIC_SCHEDULE":
                            case "SHOW_PATIENT_HISTORY":
                            case "SHOW_MEDIC_PROFIT":
                            case "SHOW_HOLIDAY": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "'");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "SET_TIME_TABLE": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "'");
                                model.selectOperation(view.selectTable, "time_table", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "SET_HOLIDAY": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "'");
                                model.selectOperation(view.selectTable, "holiday", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_APPOINTMENT": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 5).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 6).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 7).toString() + "'");
                                //model.selectOperation(view.selectTable, "", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_MEDICAL_REPORT": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + view.insertTable.getValueAt(0, 2).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "'");
                                model.selectOperation(view.selectTable, "patient_history", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "SHOW_MY_MEDIC_SCHEDULE":
                            case "SHOW_USERS":
                            case "SHOW_TOTAL_PROFIT":
                            case "SHOW_EMPLOYEES":
                            case "SHOW_PATIENTS": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_PATIENT": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "'");
                                model.executeProcedure(view.selectTable,"SHOW_PATIENTS", "");
                                //model.selectOperation(view.selectTable, "patients", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_NEW_ADMIN": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 5).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 6).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 7).toString() + "', "
                                        + view.insertTable.getValueAt(0, 8).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 9).toString() + "'");
                                model.selectOperation(view.selectTable, "users", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_NEW_ACCOUNTANT":
                            case "ADD_NEW_RECEPTIONIST":
                            case "ADD_NEW_INSPECTOR": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 5).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 6).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 7).toString() + "', "
                                        + view.insertTable.getValueAt(0, 8).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 9).toString() + "', "
                                        + view.insertTable.getValueAt(0, 10).toString() + ", "
                                        + view.insertTable.getValueAt(0, 11).toString());
                                model.selectOperation(view.selectTable, "users", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_NEW_NURSE": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 5).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 6).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 7).toString() + "', "
                                        + view.insertTable.getValueAt(0, 8).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 9).toString() + "', "
                                        + view.insertTable.getValueAt(0, 10).toString() + ", "
                                        + view.insertTable.getValueAt(0, 11).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 12).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 13).toString() + "'");
                                model.selectOperation(view.selectTable, "users", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "ADD_NEW_MEDIC": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 1).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 2).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 3).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 4).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 5).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 6).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 7).toString() + "', "
                                        + view.insertTable.getValueAt(0, 8).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 9).toString() + "', "
                                        + view.insertTable.getValueAt(0, 10).toString() + ", "
                                        + view.insertTable.getValueAt(0, 11).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 12).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 13).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 14).toString() + "', "
                                        + view.insertTable.getValueAt(0, 15).toString() + ", "
                                        + "'" + view.insertTable.getValueAt(0, 16).toString() + "', "
                                        + "'" + view.insertTable.getValueAt(0, 17).toString() + "'");
                                model.selectOperation(view.selectTable, "users", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            case "DELETE_ACCOUNT": {
                                model.executeProcedure(view.selectTable, view.tableComboBox.getSelectedItem().toString(), "'" + view.insertTable.getValueAt(0, 0).toString() + "'");
                                model.selectOperation(view.selectTable, "users", "");
                                view.tableScroll.setVisible(true);
                                view.mainFrame.setVisible(true);    //pt refresh
                                break;
                            }
                            default: {
                                System.out.println("Got error or something got in default somehow line 261ish maincrontroller");
                            }
                        }
                        break;
                    }

                    //Executam select-ul si facem refresh la tabel
                    case "SELECT": {
                        //System.out.println(view.columnList.getSelectedValuesList().toString().replaceAll("[\\[\\]]", ""));
                        model.selectOperation(view.selectTable, view.tableComboBox.getSelectedItem().toString(), view.columnList.getSelectedValuesList().toString().replaceAll("[\\[\\]]", ""));//@@@@@@aici de modificat [t cand adaug coloanele
                        view.tableScroll.setVisible(true);
                        view.mainFrame.setVisible(true);    //pt refresh
                        break;
                    }

                    case "INSERT": {

                        //Aici rezolv cu numele la fiecare coloana
                        String insertColumns = "";
                        if (view.insertTable.getColumnCount()!=0) {
                            insertColumns = insertColumns + view.insertTable.getColumnName(0);

                            for (int i = 1; i < view.insertTable.getColumnCount(); i++) {
                                insertColumns = insertColumns + ", " + view.insertTable.getColumnName(i);
                            }
                            //System.out.println(insertColumns);
                        }

                        //Aici rezolv pt valorile la fiecare coloana
                        String insertValues = "";
                        if (view.insertTable.getColumnCount()!=0) {

                            //If-ul verifica if column-ul la care facem insert este de un anumit tip, char in cazul asta(most probably o sa uit cum fct)
                            //                   hashul cu datatype][vectorul la tabela selectata                       ][la indexul la care e si numele coloanei pt a lua datatype coloana                                                                     ][tipul cautat, va trebui sa adaug si la date and time stuff like this :/
                            if(view.accessibleColumnsAndTypes.right.get(view.tableComboBox.getSelectedItem().toString()).get(view.accessibleColumnsAndTypes.left.get(
                                    view.tableComboBox.getSelectedItem().toString()).indexOf(view.insertTable.getColumnName(0))).contains("char"))
                                insertValues = insertValues + "'" + view.insertTable.getValueAt(0, 0) + "'";
                            else insertValues = insertValues + view.insertTable.getValueAt(0, 0);

                            for (int i = 1; i < view.insertTable.getColumnCount(); i++) {

                                //If-ul verifica if column-ul la care facem insert este de un anumit tip, char in cazul asta
                                if(view.accessibleColumnsAndTypes.right.get(view.tableComboBox.getSelectedItem().toString()).get(view.accessibleColumnsAndTypes.left.get(
                                        view.tableComboBox.getSelectedItem().toString()).indexOf(view.insertTable.getColumnName(i))).contains("char"))

                                    insertValues = insertValues + ", '" + view.insertTable.getValueAt(0, i) + "'";
                                else insertValues = insertValues + ", " + view.insertTable.getValueAt(0, i);
                            }
                            //System.out.println(insertValues);
                        }

                        model.insertOperation(view.tableComboBox.getSelectedItem().toString(), insertColumns, insertValues);
                        model.selectOperation(view.selectTable, view.tableComboBox.getSelectedItem().toString(), insertColumns);//pot sa fac select la toate aici nu doar unde am facut insert
                        view.tableScroll.setVisible(true);
                        view.mainFrame.setVisible(true);    //pt refresh
                        break;
                    }

                    //Aici nu ar trebui sa se ajunga cred
                    default: {
                        System.out.println("Operation not implemented yet la execute button");
                        break;
                    }
                }

            }
        });

         //Reinitializare aplicatie cand apasam logOut
        view.logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.mainFrame.setVisible(false);
                view.mainFrame = null;
                model.endConnection();

                //Se inchide conexiunea veche si incepe una noua
                ConnectionEstablisher.establishConnection();
            }
        });
    }
}
