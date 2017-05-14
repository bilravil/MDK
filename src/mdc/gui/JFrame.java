package mdc.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jssc.SerialPortException;
import mdc.client.MdcClient;
import mdc.client.GetResultsForMdcServer;
import mdc.client.parse.DoctorListParser;
import mdc.client.parse.FapListParser;
import mdc.client.parse.PatientListParser;
import mdc.client.parse.ServiceParamsParser;
import mdc.client.update.PatientServerStatusChange;
import mdc.db.connect.StartDB;
import mdc.device.ecg.WriteXMLFileForECG;
import mdc.device.print.PrintButtonEditor;
import mdc.device.print.PrintButtonRenderer;
import mdc.device.print.PrintImpl;
import mdc.device.urinoanalisy.BC01Reader;
import mdc.device.urinoanalisy.DocUReader;
import mdc.engine.AddNewPatient;
import mdc.engine.Autorization;
import mdc.engine.CheckServiceState;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import mdc.engine.SessionData;
import mdc.engine.filltable.FillAcusherTable;
import mdc.engine.filltable.FillBarcodeTable;
import mdc.engine.filltable.FillFapTable;
import mdc.engine.filltable.FillPatientTable;
import mdc.engine.filltable.FillReadyTable;
import mdc.engine.filltable.FillRezultTable;
import mdc.engine.filltable.SearchPatient;
import mdc.gui.jicontextfield.JIconTextField;
import mdc.gui.tablemodel.BarcodeTableModel;
import mdc.gui.tablemodel.JTextFieldLimit;
import mdc.gui.tablemodel.PatientTableModel;
import mdc.gui.tablemodel.RezultTableModel;
import mdc.gui.tablemodel.SearchTableModel;
import mdc.gui.tools.GhostJTextField;
import mdc.gui.tools.JDateChooserCellEditor;
import mdc.gui.tools.ServiceListTableRenderer;
import mdc.gui.tools.TableCellListener;
import mdc.update.Update;
import mdc.ws.TisClient;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.simple.parser.ParseException;



public class JFrame extends javax.swing.JFrame implements Runnable{

    
    public JFrame() {
        initComponents();
        perInfoChangeBut.setVisible(false);
        config.parse();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ico.png")));
        con = new StartDB();
        con.Start();
        update.update(con.getConnection());
        loginTxt.requestFocus();
        addResBut.setVisible(false);
        launchECBBut.setVisible(false);
        Font font = new Font("Dialog", 0, 16);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        newPoliceTxt.setDocument(new JTextFieldLimit(16));
        CheckAddPatientBut();
        SERVER_ADDRESS = config.getRabbitHost(); 
        TCP_SERVER_PORT = config.getRabbitPort(); 
        
    }
        
    
    private final String SERVER_ADDRESS;
    private final String TCP_SERVER_PORT;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(JFrame.class);
    private boolean conFlag = false;
    private Update update = new Update();
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        addPatDialog = new javax.swing.JDialog();
        addPatPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        mRad = new javax.swing.JRadioButton();
        jRad = new javax.swing.JRadioButton();
        patSurTxt = new javax.swing.JTextField();
        patNameTxt = new javax.swing.JTextField();
        patMiddTxt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cancelAddPat = new javax.swing.JButton();
        addPatBut = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        birthChooser = new com.toedter.calendar.JDateChooser("dd.MM.yyyy", "##.##.####",'_');
        jLabel18 = new javax.swing.JLabel();
        patAdressTxt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        patLPUTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        newPoliceTxt = new javax.swing.JTextField();
        SnilsTxt = new javax.swing.JFormattedTextField();
        PassTxt = new javax.swing.JFormattedTextField();
        oldPoliceTxt = new javax.swing.JFormattedTextField();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        birthdateTxt = new javax.swing.JFormattedTextField(format);
        jLabel13 = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        patSexGroup = new javax.swing.ButtonGroup();
        personalInfoDialog = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        okPersInfoBut = new javax.swing.JButton();
        helpEcgDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        helpUrinoDialog = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        prg = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPrg = new javax.swing.JProgressBar();
        loginPanel = new javax.swing.JPanel();
        subLogin = new javax.swing.JPanel();
        loginBut = new javax.swing.JButton();
        loginTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        exitBut = new javax.swing.JButton();
        passTxt = new javax.swing.JPasswordField();
        FAP = new javax.swing.JPanel();
        subFAP = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        FapTable = new javax.swing.JTable();
        nextBut = new javax.swing.JButton();
        FapTxt = new JIconTextField();
        searchFap = new javax.swing.JButton();
        synchronBut2 = new javax.swing.JButton();
        Paramedic = new javax.swing.JPanel();
        subPar = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ParTable = new javax.swing.JTable();
        nextBut1 = new javax.swing.JButton();
        ParTxt = new javax.swing.JTextField();
        backBut1 = new javax.swing.JButton();
        ECG = new javax.swing.JPanel();
        subECG = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ECGTable = new javax.swing.JTable();
        nextBut2 = new javax.swing.JButton();
        EcgTxt = new javax.swing.JTextField();
        backBut2 = new javax.swing.JButton();
        Acusher = new javax.swing.JPanel();
        subAc = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        AcTable = new javax.swing.JTable();
        nextBut3 = new javax.swing.JButton();
        AcTxt = new JIconTextField();
        searchAcusher = new javax.swing.JButton();
        backBut3 = new javax.swing.JButton();
        synchronBut1 = new javax.swing.JButton();
        SearchPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        searchTxt = new JIconTextField();
        jLabel3 = new javax.swing.JLabel();
        backBut4 = new javax.swing.JButton();
        searchBut = new javax.swing.JButton();
        openBut = new javax.swing.JButton();
        synchronBut = new javax.swing.JButton();
        addPatient = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        SearchTable = new javax.swing.JTable();
        Person = new javax.swing.JPanel();
        TabbledPane = new javax.swing.JTabbedPane();
        p1 = new javax.swing.JPanel();
        backBut5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        surnameTxt = new javax.swing.JTextField();
        nameTxt = new javax.swing.JTextField();
        patrTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        birthTxt = new javax.swing.JTextField();
        ageTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        sexTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        PatServTable = new javax.swing.JTable();
        phNumTxt = new javax.swing.JTextField();
        perInfoChangeBut = new javax.swing.JButton();
        nextBut4 = new javax.swing.JButton();
        p2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        printBut = new javax.swing.JButton();
        printCheck = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        BarcodeTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        DirectTable = new javax.swing.JTable();
        backBut6 = new javax.swing.JButton();
        nextBut6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        PaperTable = new javax.swing.JTable();
        p3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        ServiceListTable = new javax.swing.JTable();
        backBut7 = new javax.swing.JButton();
        launchECBBut = new javax.swing.JButton();
        addResBut = new javax.swing.JButton();
        cancelBut = new javax.swing.JButton();
        confirmtBut = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        RezultTable = new javax.swing.JTable();
        helpBut = new javax.swing.JLabel();

        addPatDialog.setTitle("Добавить пациента");
        addPatDialog.setBackground(new java.awt.Color(239, 239, 243));
        addPatDialog.setName("addPatDialog"); // NOI18N
        addPatDialog.setSize(720, 590);
        addPatDialog.setResizable(false);
        addPatDialog.setLocationRelativeTo(addPatDialog);
        addPatDialog.getContentPane().setLayout(new java.awt.CardLayout());

        addPatPanel.setBackground(new java.awt.Color(239, 239, 243));
        addPatPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Добавление пациента", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N
        addPatPanel.setName("addPatPanel"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel11.setText("Фамилия");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel14.setText("Пол");
        jLabel14.setName("jLabel14"); // NOI18N

        patSexGroup.add(mRad);
        mRad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        mRad.setText("Мужской");
        mRad.setName("mRad"); // NOI18N

        patSexGroup.add(jRad);
        jRad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jRad.setText("Женский");
        jRad.setName("jRad"); // NOI18N

        patSurTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        patSurTxt.setName("patSurTxt"); // NOI18N

        patNameTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        patNameTxt.setName("patNameTxt"); // NOI18N

        patMiddTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        patMiddTxt.setName("patMiddTxt"); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel15.setText("Имя");
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel16.setText("Отчество");
        jLabel16.setName("jLabel16"); // NOI18N

        cancelAddPat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cancelAddPat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/cancel.png"))); // NOI18N
        cancelAddPat.setText("Отмена");
        cancelAddPat.setName("cancelAddPat"); // NOI18N
        cancelAddPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddPatActionPerformed(evt);
            }
        });

        addPatBut.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        addPatBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/ok.png"))); // NOI18N
        addPatBut.setText("Добавить пациента");
        addPatBut.setName("addPatBut"); // NOI18N
        addPatBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatButActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel17.setText("Дата рождения");
        jLabel17.setName("jLabel17"); // NOI18N

        birthChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        birthChooser.setName("birthChooser"); // NOI18N
        birthChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                birthChooserPropertyChange(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel18.setText("Адрес");
        jLabel18.setName("jLabel18"); // NOI18N

        patAdressTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        patAdressTxt.setName("patAdressTxt"); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel19.setText("Участок");
        jLabel19.setName("jLabel19"); // NOI18N

        patLPUTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        patLPUTxt.setName("patLPUTxt"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel12.setText("Серия / Номер  ");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel20.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel20.setText("СНИЛС");
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel21.setText("Паспорт");
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel22.setText("№ старого полиса");
        jLabel22.setName("jLabel22"); // NOI18N

        jLabel23.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel23.setText("№ нового полиса");
        jLabel23.setName("jLabel23"); // NOI18N

        newPoliceTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        newPoliceTxt.setName("newPoliceTxt"); // NOI18N

        try {
            SnilsTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-### ##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        SnilsTxt.setToolTipText("");
        SnilsTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        SnilsTxt.setName("SnilsTxt"); // NOI18N
        SnilsTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SnilsTxtKeyReleased(evt);
            }
        });

        try {
            PassTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#### ######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        PassTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        PassTxt.setName("PassTxt"); // NOI18N

        try {
            oldPoliceTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##?? #######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        oldPoliceTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        oldPoliceTxt.setName("oldPoliceTxt"); // NOI18N

        try {
            birthdateTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.##.####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        birthdateTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        birthdateTxt.setName("birthdateTxt"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel13.setText("Номер тел.");
        jLabel13.setName("jLabel13"); // NOI18N

        phoneTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        phoneTxt.setName("phoneTxt"); // NOI18N

        javax.swing.GroupLayout addPatPanelLayout = new javax.swing.GroupLayout(addPatPanel);
        addPatPanel.setLayout(addPatPanelLayout);
        addPatPanelLayout.setHorizontalGroup(
            addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPatPanelLayout.createSequentialGroup()
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel15)
                            .addComponent(jLabel11)
                            .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(addPatPanelLayout.createSequentialGroup()
                                    .addComponent(cancelAddPat, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addPatBut))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addPatPanelLayout.createSequentialGroup()
                                    .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel16))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(patLPUTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(addPatPanelLayout.createSequentialGroup()
                                            .addComponent(patSurTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel14)
                                            .addGap(26, 26, 26)
                                            .addComponent(mRad)
                                            .addGap(18, 18, 18)
                                            .addComponent(jRad))
                                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(phoneTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(PassTxt)
                                            .addComponent(SnilsTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                                        .addComponent(patAdressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(addPatPanelLayout.createSequentialGroup()
                                            .addComponent(patNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel22)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(oldPoliceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(addPatPanelLayout.createSequentialGroup()
                                            .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(addPatPanelLayout.createSequentialGroup()
                                                    .addComponent(birthdateTxt)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(birthChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(patMiddTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel23)
                                            .addGap(18, 18, 18)
                                            .addComponent(newPoliceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        addPatPanelLayout.setVerticalGroup(
            addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPatPanelLayout.createSequentialGroup()
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel11)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addPatPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patSurTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(mRad)
                            .addComponent(jRad))))
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15))
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(oldPoliceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)))
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patMiddTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(newPoliceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addPatPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)))
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(patLPUTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPatPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel17))
                    .addComponent(birthChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(SnilsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(PassTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(patAdressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addPatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPatBut, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelAddPat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        addPatDialog.getContentPane().add(addPatPanel, "card2");

        personalInfoDialog.setTitle("Обновление базы.");
        personalInfoDialog.setName("personalInfoDialog"); // NOI18N
        personalInfoDialog.setSize(400, 330);
        personalInfoDialog.setResizable(false);
        personalInfoDialog.setLocationRelativeTo(addPatDialog);

        jLabel4.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel4.setText("Отметьте галочкой пункты,которые изменились.");
        jLabel4.setName("jLabel4"); // NOI18N

        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCheckBox1.setText("Паспортные данные");
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        jCheckBox3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCheckBox3.setText("Адрес ");
        jCheckBox3.setName("jCheckBox3"); // NOI18N

        jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCheckBox2.setText("Полис ОМС");
        jCheckBox2.setName("jCheckBox2"); // NOI18N

        jCheckBox4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCheckBox4.setText("Номер телефона");
        jCheckBox4.setName("jCheckBox4"); // NOI18N

        okPersInfoBut.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        okPersInfoBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/ok.png"))); // NOI18N
        okPersInfoBut.setText("Принять");
        okPersInfoBut.setName("okPersInfoBut"); // NOI18N
        okPersInfoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okPersInfoButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personalInfoDialogLayout = new javax.swing.GroupLayout(personalInfoDialog.getContentPane());
        personalInfoDialog.getContentPane().setLayout(personalInfoDialogLayout);
        personalInfoDialogLayout.setHorizontalGroup(
            personalInfoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInfoDialogLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(personalInfoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalInfoDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okPersInfoBut)
                .addContainerGap())
        );
        personalInfoDialogLayout.setVerticalGroup(
            personalInfoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInfoDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox2)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okPersInfoBut)
                .addContainerGap())
        );

        helpEcgDialog.setTitle("Помощь. ЭКГ");
        helpEcgDialog.setName("helpEcgDialog"); // NOI18N
        helpEcgDialog.setSize(515,316);
        helpEcgDialog.setResizable(false);
        helpEcgDialog.setLocationRelativeTo(addPatDialog);

        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("\tПомощь при снятии электрокардиографии:\n\n1.Нажмите на кнопку \"►Запуск\" .Дождитесь открытия программы \nдля снятия экг.\n\n2.После снятия экг закройте (сверните) программу \"EcgReciever\".\n\n3.Нажмите на кнопку \"+ Результаты\".\n\n4.Убедитесь,что напротив строки \"Экг\" в таблице с результатами \nпоявилось \"Файл прикреплен.\"");
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane5.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout helpEcgDialogLayout = new javax.swing.GroupLayout(helpEcgDialog.getContentPane());
        helpEcgDialog.getContentPane().setLayout(helpEcgDialogLayout);
        helpEcgDialogLayout.setHorizontalGroup(
            helpEcgDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        helpEcgDialogLayout.setVerticalGroup(
            helpEcgDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        helpUrinoDialog.setTitle("Помощь. Уриноанализатор");
        helpUrinoDialog.setName("helpUrinoDialog"); // NOI18N
        helpUrinoDialog.setSize(630, 345);
        helpUrinoDialog.setResizable(false);
        helpUrinoDialog.setLocationRelativeTo(addPatDialog);

        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane13.setName("jScrollPane13"); // NOI18N

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setText("1. Погрузите тест-полоску  в пробу мочи примерно на одну секунду.\n   Убедитесь,чтобы моча касалась всех реагентных зон тест-полоски.\n\n2. Поместите тест-полоску в держатель тест-полосок на приборе.  \n\n3. Сдвиньте полоску до упора в канале держателя.Не дотрагивайтесь \n    до реагентных  зон на тест-полоске.\n\n4.Прибор автоматически распознает тест-полоску. Начнѐтся процесс измерения.\n\n5.После окончания измерения , нажмите \"+ Результат\" в окне программы.\n\n6.На уриноанализаторе нажмите кнопку \"1001\" для передачи данных.");
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane13.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout helpUrinoDialogLayout = new javax.swing.GroupLayout(helpUrinoDialog.getContentPane());
        helpUrinoDialog.getContentPane().setLayout(helpUrinoDialogLayout);
        helpUrinoDialogLayout.setHorizontalGroup(
            helpUrinoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        helpUrinoDialogLayout.setVerticalGroup(
            helpUrinoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        prg.setTitle("Обмен данными");
        prg.setSize(515, 198);
        prg.setDefaultCloseOperation(0);
        prg.setName("prg"); // NOI18N
        prg.setLocationRelativeTo(prg);
        prg.setResizable(false);

        jPanel6.setName("jPanel6"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Происходит обмен данными. Подождите...");
        jLabel9.setName("jLabel9"); // NOI18N

        jPrg.setName("jPrg"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jPrg, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jPrg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout prgLayout = new javax.swing.GroupLayout(prg.getContentPane());
        prg.getContentPane().setLayout(prgLayout);
        prgLayout.setHorizontalGroup(
            prgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        prgLayout.setVerticalGroup(
            prgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Мобильный диагностический комплекс");
        setPreferredSize(new java.awt.Dimension(990, 726));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        loginPanel.setName("loginPanel"); // NOI18N
        loginPanel.setPreferredSize(new java.awt.Dimension(990, 720));
        loginPanel.setLayout(new java.awt.GridBagLayout());

        subLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true), "Авторизация"));
        subLogin.setName("subLogin"); // NOI18N

        loginBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/ok.png"))); // NOI18N
        loginBut.setText("Войти");
        loginBut.setToolTipText("");
        loginBut.setFocusPainted(false);
        loginBut.setIconTextGap(10);
        loginBut.setName("loginBut"); // NOI18N
        loginBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButActionPerformed(evt);
            }
        });

        loginTxt.setName("loginTxt"); // NOI18N
        loginTxt.setPreferredSize(new java.awt.Dimension(60, 20));
        loginTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginTxtKeyPressed(evt);
            }
        });

        jLabel1.setText("Имя пользователя:");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Пароль:");
        jLabel2.setName("jLabel2"); // NOI18N

        exitBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/cancel.png"))); // NOI18N
        exitBut.setText("Выход");
        exitBut.setFocusPainted(false);
        exitBut.setIconTextGap(10);
        exitBut.setName("exitBut"); // NOI18N
        exitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButActionPerformed(evt);
            }
        });

        passTxt.setToolTipText("");
        passTxt.setName("passTxt"); // NOI18N
        passTxt.setPreferredSize(new java.awt.Dimension(60, 20));
        passTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passTxtKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout subLoginLayout = new javax.swing.GroupLayout(subLogin);
        subLogin.setLayout(subLoginLayout);
        subLoginLayout.setHorizontalGroup(
            subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subLoginLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(subLoginLayout.createSequentialGroup()
                        .addComponent(loginBut, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(exitBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(subLoginLayout.createSequentialGroup()
                        .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(39, 39, 39)
                        .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(36, 36, 36))
        );
        subLoginLayout.setVerticalGroup(
            subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subLoginLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subLoginLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1))
                    .addComponent(loginTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(subLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitBut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(170, 260, 250, 275);
        loginPanel.add(subLogin, gridBagConstraints);

        getContentPane().add(loginPanel, "card2");

        FAP.setBackground(new java.awt.Color(239, 239, 243));
        FAP.setName("FAP"); // NOI18N
        FAP.setPreferredSize(new java.awt.Dimension(990, 720));
        FAP.setLayout(new java.awt.CardLayout());

        subFAP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Выберите ФАП", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18))); // NOI18N
        subFAP.setName("subFAP"); // NOI18N
        subFAP.setPreferredSize(new java.awt.Dimension(990, 720));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        FapTable.setFont(new java.awt.Font("Arial", 0, 16));
        FapTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Наименование ФАПа"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        FapTable.setName("FapTable"); // NOI18N
        FapTable.setRowHeight(25);
        FapTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        FapTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FapTableMouseClicked(evt);
            }
        });
        FapTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FapTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(FapTable);
        if (FapTable.getColumnModel().getColumnCount() > 0) {
            FapTable.getColumnModel().getColumn(0).setResizable(false);
        }
        FapTable.getTableHeader().setFont(new java.awt.Font("Arial", 0, 16));

        nextBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut.setMnemonic('E');
        nextBut.setText("Далее");
        nextBut.setFocusPainted(false);
        nextBut.setName("nextBut"); // NOI18N
        nextBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButActionPerformed(evt);
            }
        });

        FapTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        FapTxt.setName("FapTxt"); // NOI18N
        FapTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FapTxtKeyReleased(evt);
            }
        });

        searchFap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/search1.png"))); // NOI18N
        searchFap.setText("Поиск");
        searchFap.setIconTextGap(10);
        searchFap.setName("searchFap"); // NOI18N
        searchFap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFapActionPerformed(evt);
            }
        });

        synchronBut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/Synchronize32.png"))); // NOI18N
        synchronBut2.setText("Обмен данными");
        synchronBut2.setName("synchronBut2"); // NOI18N
        synchronBut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronBut2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subFAPLayout = new javax.swing.GroupLayout(subFAP);
        subFAP.setLayout(subFAPLayout);
        subFAPLayout.setHorizontalGroup(
            subFAPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subFAPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subFAPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subFAPLayout.createSequentialGroup()
                        .addComponent(FapTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchFap, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(synchronBut2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subFAPLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nextBut, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        subFAPLayout.setVerticalGroup(
            subFAPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subFAPLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(subFAPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(synchronBut2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FapTxt)
                    .addComponent(searchFap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextBut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        FAP.add(subFAP, "card2");

        getContentPane().add(FAP, "card3");

        Paramedic.setBackground(new java.awt.Color(239, 239, 243));
        Paramedic.setName("Paramedic"); // NOI18N
        Paramedic.setLayout(new java.awt.GridBagLayout());

        subPar.setBackground(new java.awt.Color(239, 239, 243));
        subPar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Выберите фельдшера", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        subPar.setName("subPar"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        ParTable.setFont(new java.awt.Font("Times New Roman", 0, 16));
        ParTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ФИО фельдшера"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ParTable.setName("ParTable"); // NOI18N
        ParTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(ParTable);
        if (ParTable.getColumnModel().getColumnCount() > 0) {
            ParTable.getColumnModel().getColumn(0).setResizable(false);
        }
        ParTable.getTableHeader().setFont(new java.awt.Font("Times New Roman", 0, 16));

        nextBut1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        nextBut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut1.setMnemonic('E');
        nextBut1.setText("Далее");
        nextBut1.setBorderPainted(false);
        nextBut1.setFocusPainted(false);
        nextBut1.setName("nextBut1"); // NOI18N
        nextBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBut1ActionPerformed(evt);
            }
        });

        ParTxt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        ParTxt.setName("ParTxt"); // NOI18N

        backBut1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        backBut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut1.setMnemonic('E');
        backBut1.setText("Назад");
        backBut1.setBorderPainted(false);
        backBut1.setFocusPainted(false);
        backBut1.setName("backBut1"); // NOI18N
        backBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subParLayout = new javax.swing.GroupLayout(subPar);
        subPar.setLayout(subParLayout);
        subParLayout.setHorizontalGroup(
            subParLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subParLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backBut1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subParLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ParTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(subParLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextBut1)))
                .addGap(31, 31, 31))
        );
        subParLayout.setVerticalGroup(
            subParLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subParLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subParLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(subParLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backBut1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subParLayout.createSequentialGroup()
                        .addComponent(ParTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(subParLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(subParLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(nextBut1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))))
                .addGap(28, 28, 28))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 344;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 46, 11, 46);
        Paramedic.add(subPar, gridBagConstraints);

        getContentPane().add(Paramedic, "card3");

        ECG.setBackground(new java.awt.Color(239, 239, 243));
        ECG.setName("ECG"); // NOI18N
        ECG.setLayout(new java.awt.GridBagLayout());

        subECG.setBackground(new java.awt.Color(239, 239, 243));
        subECG.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Выберите специалиста ЭКГ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        subECG.setName("subECG"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        ECGTable.setFont(new java.awt.Font("Times New Roman", 0, 16));
        ECGTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ФИО специалиста ЭКГ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ECGTable.setName("ECGTable"); // NOI18N
        ECGTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ECGTableKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(ECGTable);
        if (ECGTable.getColumnModel().getColumnCount() > 0) {
            ECGTable.getColumnModel().getColumn(0).setResizable(false);
        }
        ECGTable.getTableHeader().setFont(new  java.awt.Font("Times New Roman",0,16));

        nextBut2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        nextBut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut2.setMnemonic('E');
        nextBut2.setText("Далее");
        nextBut2.setBorderPainted(false);
        nextBut2.setFocusPainted(false);
        nextBut2.setName("nextBut2"); // NOI18N
        nextBut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBut2ActionPerformed(evt);
            }
        });

        EcgTxt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        EcgTxt.setName("EcgTxt"); // NOI18N

        backBut2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        backBut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut2.setMnemonic('E');
        backBut2.setText("Назад");
        backBut2.setBorderPainted(false);
        backBut2.setFocusPainted(false);
        backBut2.setName("backBut2"); // NOI18N
        backBut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subECGLayout = new javax.swing.GroupLayout(subECG);
        subECG.setLayout(subECGLayout);
        subECGLayout.setHorizontalGroup(
            subECGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subECGLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backBut2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subECGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EcgTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(subECGLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextBut2)))
                .addGap(31, 31, 31))
        );
        subECGLayout.setVerticalGroup(
            subECGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subECGLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subECGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(subECGLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backBut2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subECGLayout.createSequentialGroup()
                        .addComponent(EcgTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(subECGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(subECGLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(nextBut2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))))
                .addGap(28, 28, 28))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 344;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 46, 11, 46);
        ECG.add(subECG, gridBagConstraints);

        getContentPane().add(ECG, "card3");

        Acusher.setBackground(new java.awt.Color(239, 239, 243));
        Acusher.setName("Acusher"); // NOI18N
        Acusher.setPreferredSize(new java.awt.Dimension(990, 720));
        Acusher.setLayout(new java.awt.CardLayout());

        subAc.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Выберите Акушера", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18))); // NOI18N
        subAc.setName("subAc"); // NOI18N
        subAc.setPreferredSize(new java.awt.Dimension(990, 720));

        jScrollPane12.setName("jScrollPane12"); // NOI18N

        AcTable.setFont(new java.awt.Font("Arial", 0, 16));
        AcTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "ФИО акушера"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        AcTable.setName("AcTable"); // NOI18N
        AcTable.setRowHeight(25);
        AcTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        AcTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AcTableMouseClicked(evt);
            }
        });
        AcTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AcTableKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(AcTable);
        if (AcTable.getColumnModel().getColumnCount() > 0) {
            AcTable.getColumnModel().getColumn(0).setResizable(false);
        }
        AcTable.getTableHeader().setFont(new java.awt.Font("Arial", 0, 16));

        nextBut3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut3.setMnemonic('E');
        nextBut3.setText("Далее");
        nextBut3.setFocusPainted(false);
        nextBut3.setName("nextBut3"); // NOI18N
        nextBut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBut3ActionPerformed(evt);
            }
        });

        AcTxt.setName("AcTxt"); // NOI18N
        AcTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                AcTxtKeyReleased(evt);
            }
        });

        searchAcusher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/search1.png"))); // NOI18N
        searchAcusher.setText("Поиск");
        searchAcusher.setIconTextGap(10);
        searchAcusher.setName("searchAcusher"); // NOI18N

        backBut3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut3.setMnemonic('E');
        backBut3.setText("Назад");
        backBut3.setFocusPainted(false);
        backBut3.setName("backBut3"); // NOI18N
        backBut3.setPreferredSize(new java.awt.Dimension(105, 70));
        backBut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut3ActionPerformed(evt);
            }
        });

        synchronBut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/Synchronize32.png"))); // NOI18N
        synchronBut1.setText("Обмен данными");
        synchronBut1.setName("synchronBut1"); // NOI18N
        synchronBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronBut1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subAcLayout = new javax.swing.GroupLayout(subAc);
        subAc.setLayout(subAcLayout);
        subAcLayout.setHorizontalGroup(
            subAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subAcLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subAcLayout.createSequentialGroup()
                        .addComponent(AcTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchAcusher, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(synchronBut1))
                    .addGroup(subAcLayout.createSequentialGroup()
                        .addComponent(backBut3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextBut3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE))
                .addContainerGap())
        );
        subAcLayout.setVerticalGroup(
            subAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subAcLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(subAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchAcusher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AcTxt)
                    .addComponent(synchronBut1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(subAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextBut3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBut3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        Acusher.add(subAc, "card2");

        getContentPane().add(Acusher, "card3");

        SearchPanel.setBackground(new java.awt.Color(239, 239, 243));
        SearchPanel.setName("SearchPanel"); // NOI18N
        SearchPanel.setPreferredSize(new java.awt.Dimension(880, 740));
        SearchPanel.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Поиск пациента", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(990, 720));

        searchTxt.setName("searchTxt"); // NOI18N
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTxtKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel3.setText("ФИО, год рождения или ID: ");
        jLabel3.setName("jLabel3"); // NOI18N

        backBut4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut4.setMnemonic('E');
        backBut4.setText("Назад");
        backBut4.setFocusPainted(false);
        backBut4.setName("backBut4"); // NOI18N
        backBut4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut4ActionPerformed(evt);
            }
        });

        searchBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/search1.png"))); // NOI18N
        searchBut.setMnemonic('E');
        searchBut.setText("Поиск");
        searchBut.setFocusPainted(false);
        searchBut.setName("searchBut"); // NOI18N
        searchBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButActionPerformed(evt);
            }
        });

        openBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/ok.png"))); // NOI18N
        openBut.setMnemonic('E');
        openBut.setText("Открыть");
        openBut.setFocusPainted(false);
        openBut.setName("openBut"); // NOI18N
        openBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButActionPerformed(evt);
            }
        });

        synchronBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/Synchronize32.png"))); // NOI18N
        synchronBut.setText("Обмен данными");
        synchronBut.setName("synchronBut"); // NOI18N
        synchronBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronButActionPerformed(evt);
            }
        });

        addPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/addPat32.png"))); // NOI18N
        addPatient.setMnemonic('E');
        addPatient.setText("Добавить пациента");
        addPatient.setFocusPainted(false);
        addPatient.setName("addPatient"); // NOI18N
        addPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientActionPerformed(evt);
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        SearchTable.setAutoCreateRowSorter(true);
        SearchTable.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        SearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "ФИО ", "Дата рождения", "Пол", "Адрес"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SearchTable.setName("SearchTable"); // NOI18N
        SearchTable.setRowHeight(20);
        SearchTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //SearchTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        //SearchTable.getTableHeader().setReorderingAllowed(false);
        SearchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchTableMouseClicked(evt);
            }
        });
        SearchTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SearchTableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(SearchTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(backBut4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addPatient)
                        .addGap(37, 37, 37)
                        .addComponent(openBut, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addGap(34, 34, 34)
                        .addComponent(searchTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(searchBut, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(synchronBut))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(synchronBut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBut4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openBut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        SearchPanel.add(jPanel1, "card2");

        getContentPane().add(SearchPanel, "card7");

        Person.setBackground(new java.awt.Color(239, 239, 243));
        Person.setName("Person"); // NOI18N
        Person.setPreferredSize(new java.awt.Dimension(990, 720));
        Person.setLayout(new java.awt.CardLayout());

        TabbledPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true), "Случай медицинского осмотра", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 16))); // NOI18N
        TabbledPane.setName("PatientPane"); // NOI18N
        TabbledPane.setFont(new java.awt.Font("Dialog",0,16));
        TabbledPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabbledPaneMouseClicked(evt);
            }
        });

        p1.setBackground(new java.awt.Color(239, 239, 243));
        p1.setName("p1"); // NOI18N

        backBut5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut5.setMnemonic('E');
        backBut5.setText("Назад");
        backBut5.setFocusPainted(false);
        backBut5.setName("backBut5"); // NOI18N
        backBut5.setPreferredSize(new java.awt.Dimension(105, 70));
        backBut5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut5ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Персональные данные"));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel5.setText("Ф.И.О.");
        jLabel5.setName("jLabel5"); // NOI18N

        surnameTxt.setEditable(false);
        surnameTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        surnameTxt.setName("surnameTxt"); // NOI18N

        nameTxt.setEditable(false);
        nameTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        nameTxt.setName("nameTxt"); // NOI18N

        patrTxt.setEditable(false);
        patrTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        patrTxt.setName("patrTxt"); // NOI18N

        jLabel6.setText("Дата рождения");
        jLabel6.setName("jLabel6"); // NOI18N

        birthTxt.setEditable(false);
        birthTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        birthTxt.setName("birthTxt"); // NOI18N

        ageTxt.setEditable(false);
        ageTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        ageTxt.setName("ageTxt"); // NOI18N

        jLabel7.setText("Пол");
        jLabel7.setName("jLabel7"); // NOI18N

        sexTxt.setEditable(false);
        sexTxt.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        sexTxt.setName("sexTxt"); // NOI18N

        jLabel8.setText("Возраст");
        jLabel8.setName("jLabel8"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        PatServTable.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        PatServTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "№", "Наименование", "Состояние", "Дата"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PatServTable.setName("PatServTable"); // NOI18N
        PatServTable.setRowHeight(22);
        PatServTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PatServTable.getTableHeader().setReorderingAllowed(false);
        PatServTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PatServTableMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(PatServTable);
        if (PatServTable.getColumnModel().getColumnCount() > 0) {
            PatServTable.getColumnModel().getColumn(0).setResizable(false);
            PatServTable.getColumnModel().getColumn(1).setResizable(false);
            PatServTable.getColumnModel().getColumn(2).setResizable(false);
            PatServTable.getColumnModel().getColumn(3).setResizable(false);
        }
        PatServTable.getTableHeader().setFont(new java.awt.Font("Arial", 0, 16));

        GhostJTextField gh = new GhostJTextField(phNumTxt,"Введите номер телефона...");
        phNumTxt.setFont(new java.awt.Font("Dialog", 2, 15)); // NOI18N
        phNumTxt.setToolTipText("");
        phNumTxt.setName("phNumTxt"); // NOI18N

        perInfoChangeBut.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        perInfoChangeBut.setText("Личные данные");
        perInfoChangeBut.setName("perInfoChangeBut"); // NOI18N
        perInfoChangeBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perInfoChangeButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(birthTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(surnameTxt))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(sexTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phNumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(perInfoChangeBut))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(surnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phNumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(birthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(sexTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(perInfoChangeBut)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );

        nextBut4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut4.setMnemonic('E');
        nextBut4.setText("Далее");
        nextBut4.setFocusPainted(false);
        nextBut4.setName("nextBut4"); // NOI18N
        nextBut4.setPreferredSize(new java.awt.Dimension(105, 70));
        nextBut4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBut4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p1Layout.createSequentialGroup()
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backBut5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextBut4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        p1Layout.setVerticalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextBut4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBut5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        TabbledPane.addTab("              Пациент             ", p1);

        p2.setBackground(new java.awt.Color(239, 239, 243));
        p2.setName("p2"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setName("jPanel4"); // NOI18N

        printBut.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        printBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/printer_1.png"))); // NOI18N
        printBut.setText("Печатать все");
        printBut.setName("printBut"); // NOI18N
        printBut.setPreferredSize(new java.awt.Dimension(130, 35));
        printBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButActionPerformed(evt);
            }
        });

        printCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        printCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/printer_1.png"))); // NOI18N
        printCheck.setText("Печать отмеченных");
        printCheck.setFocusPainted(false);
        printCheck.setName("printCheck"); // NOI18N
        printCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(printBut, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(printCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(printCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(printBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Штрих-коды пробирок"));
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        BarcodeTable.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        BarcodeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Наименование"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BarcodeTable.setName("BarcodeTable"); // NOI18N
        BarcodeTable.setRowHeight(18);
        BarcodeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        BarcodeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(BarcodeTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Направления"));
        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        DirectTable.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        DirectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Наименование"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DirectTable.setName("DirectTable"); // NOI18N
        DirectTable.setRowHeight(18);
        DirectTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        DirectTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(DirectTable);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        backBut6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut6.setMnemonic('E');
        backBut6.setText("Назад");
        backBut6.setFocusPainted(false);
        backBut6.setName("backBut6"); // NOI18N
        backBut6.setPreferredSize(new java.awt.Dimension(105, 70));
        backBut6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut6ActionPerformed(evt);
            }
        });

        nextBut6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/next.png"))); // NOI18N
        nextBut6.setMnemonic('E');
        nextBut6.setText("Далее");
        nextBut6.setFocusPainted(false);
        nextBut6.setName("nextBut6"); // NOI18N
        nextBut6.setPreferredSize(new java.awt.Dimension(105, 70));
        nextBut6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBut6ActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Бумажные носители"));
        jPanel10.setName("jPanel10"); // NOI18N

        jScrollPane11.setName("jScrollPane11"); // NOI18N

        PaperTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        PaperTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null,  new Boolean(false), null},
                {null,  new Boolean(false), null},
                {null,  new Boolean(false), null},
                {null,  new Boolean(false), null}
            },
            new String [] {
                "Наименование", "", "Печать"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                switch(columnIndex){
                    case 0 : return false;
                    default : return true;
                }
            }
        });
        PaperTable.setName("PaperTable"); // NOI18N
        PaperTable.setRowHeight(18);
        PaperTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(PaperTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane11)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addGroup(p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backBut6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextBut6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextBut6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBut6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        TabbledPane.addTab("       Распечатать       ", p2);

        p3.setName("p3"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        ServiceListTable.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ServiceListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "№", "Наименование"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ServiceListTable.setName("ServiceListTable"); // NOI18N
        ServiceListTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        ServiceListTable.setRowHeight(33);
        ServiceListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ServiceListTable.getTableHeader().setReorderingAllowed(false);
        ServiceListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ServiceListTableMouseClicked(evt);
            }
        });
        ServiceListTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ServiceListTableKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(ServiceListTable);
        if (ServiceListTable.getColumnModel().getColumnCount() > 0) {
            ServiceListTable.getColumnModel().getColumn(0).setMinWidth(30);
            ServiceListTable.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        backBut7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/back.png"))); // NOI18N
        backBut7.setMnemonic('E');
        backBut7.setText("Назад");
        backBut7.setFocusPainted(false);
        backBut7.setName("backBut7"); // NOI18N
        backBut7.setPreferredSize(new java.awt.Dimension(105, 70));
        backBut7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBut7ActionPerformed(evt);
            }
        });

        launchECBBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/1460382386_Play.png"))); // NOI18N
        launchECBBut.setText("Запуск");
        launchECBBut.setFocusPainted(false);
        launchECBBut.setName("launchECBBut"); // NOI18N
        launchECBBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchECBButActionPerformed(evt);
            }
        });

        addResBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/add.png"))); // NOI18N
        addResBut.setText("Результаты");
        addResBut.setFocusPainted(false);
        addResBut.setName("addResBut"); // NOI18N
        addResBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addResButActionPerformed(evt);
            }
        });

        cancelBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/cancel.png"))); // NOI18N
        cancelBut.setMnemonic('E');
        cancelBut.setText("Отмена");
        cancelBut.setFocusPainted(false);
        cancelBut.setName("cancelBut"); // NOI18N
        cancelBut.setPreferredSize(new java.awt.Dimension(105, 70));
        cancelBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButActionPerformed(evt);
            }
        });

        confirmtBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/ok.png"))); // NOI18N
        confirmtBut.setMnemonic('E');
        confirmtBut.setText("Готово");
        confirmtBut.setFocusPainted(false);
        confirmtBut.setName("confirmtBut"); // NOI18N
        confirmtBut.setPreferredSize(new java.awt.Dimension(105, 70));
        confirmtBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmtButActionPerformed(evt);
            }
        });

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        RezultTable.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        RezultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Параметр", "Значение"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        RezultTable.setName("RezultTable"); // NOI18N
        RezultTable.setRowHeight(35);
        RezultTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        RezultTable.getTableHeader().setReorderingAllowed(false);
        RezultTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RezultTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                RezultTableMousePressed(evt);
            }
        });
        RezultTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RezultTableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                RezultTableKeyTyped(evt);
            }
        });
        jScrollPane8.setViewportView(RezultTable);
        if (RezultTable.getColumnModel().getColumnCount() > 0) {
            RezultTable.getColumnModel().getColumn(0).setMinWidth(350);
            RezultTable.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        helpBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/help.png"))); // NOI18N
        helpBut.setName("helpBut"); // NOI18N
        helpBut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                helpButMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                helpButMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                helpButMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout p3Layout = new javax.swing.GroupLayout(p3);
        p3.setLayout(p3Layout);
        p3Layout.setHorizontalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backBut7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(p3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane8))
                    .addGroup(p3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(helpBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(launchECBBut, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addResBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelBut, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(confirmtBut, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        p3Layout.setVerticalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addGap(10, 10, 10)
                .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(helpBut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(backBut7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(launchECBBut, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addResBut, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cancelBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(confirmtBut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );

        TabbledPane.addTab("       Услуги       ", p3);

        Person.add(TabbledPane, "card2");

        getContentPane().add(Person, "card7");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CheckAddPatientBut(){
        //<editor-fold defaultstate="collapsed" desc="Активация добавления пациента">       
            String flag = config.getAddPatSwitcher();
            if(flag.equals("false")){
              addPatient.setEnabled(false);
            }
        }
//</editor-fold>

    private void login(){
        //<editor-fold defaultstate="collapsed" desc="Вход в программу">
        Autorization login = new Autorization();
        char [] p = passTxt.getPassword();
        String pass = String.valueOf(p);      
        String username = loginTxt.getText();
        if(login.login(username, pass,con.getConnection()) == true){
        loginPanel.setVisible(false);
        FAP.setVisible(true);
        FillFapTable fap = new FillFapTable();
        PatientTableModel dtm = new PatientTableModel(true,con.getConnection(),pat_table,PatServTable);
        String crb = config.getCrbID();
        crb = crb.split("\\.")[0];
            try {
                dtm.setDataSource(fap.FillFapTable(con.getConnection(),crb));
            } catch (Exception ex) {
                log.info(ex,ex);
            }        
        FapTable.setModel(dtm);
        FapTable.getColumnModel().getColumn(0).setHeaderValue("Наименование");
        FapTable.requestFocus();
        }
        else JOptionPane.showMessageDialog(null, "Ошибка при вводе логина или пароля");
        loginTxt.requestFocus();
    }
//</editor-fold>
        
    private void loginButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButActionPerformed
        login();
    }//GEN-LAST:event_loginButActionPerformed

    private void passTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passTxtKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            login();
        }     
    }//GEN-LAST:event_passTxtKeyPressed

    private void exitButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButActionPerformed
        // выход из прогр через окно авторизации    
        try {
           con.Stop(); 
        } catch (NullPointerException e){}
        System.exit(0);       
    }//GEN-LAST:event_exitButActionPerformed

    private void nextButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButActionPerformed
       // переход к окну выбора Фелдьшера после выбоора ФАПа
        int row = FapTable.getSelectedRow();
        if(row != -1){
            FillFapTable fap = new FillFapTable();
            String fap1 = FapTable.getValueAt(row,0).toString();
            fap1 = fap.getFapCode(con.getConnection(), fap1);
            session.setFap(fap1);
        }else{
            JOptionPane.showMessageDialog(null, "Необходимо выбрать ФАП.");
            return;
        }
        FAP.setVisible(false);
        Acusher.setVisible(true);
        ac_table_filling();       
        AcTable.requestFocus();
    }//GEN-LAST:event_nextButActionPerformed
    
    private void ac_table_filling(){
        //<editor-fold defaultstate="collapsed" desc="Обновление таблицы с фапами после запуска программы">
        FillAcusherTable ac = new FillAcusherTable();
        PatientTableModel dtm = new PatientTableModel(true,con.getConnection(),pat_table, PatServTable);
        String crb = config.getCrbID();
        crb = crb.split("\\.")[0];
            try {
                dtm.setDataSource(ac.FillAcusher(con.getConnection(),crb));
            } catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }        
        AcTable.setModel(dtm);
        AcTable.getColumnModel().getColumn(0).setHeaderValue("ID");
        AcTable.getColumnModel().getColumn(1).setHeaderValue("ФИО акушера");
        AcTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        AcTable.getColumnModel().getColumn(0).setWidth(50);
        AcTable.getColumnModel().getColumn(0).setMaxWidth(50);          
    }
//</editor-fold>
          
    private void FapTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FapTableKeyPressed
        // переход к окну выбора Фелдьшера после выбоора ФАПа через enter
       // открывается окно с выбором фельдшера
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){       
        int row = FapTable.getSelectedRow();
        if(row != -1){
            FillFapTable fap = new FillFapTable();
            String fap1 = FapTable.getValueAt(row,0).toString();
            fap1 = fap.getFapCode(con.getConnection(), fap1);
            session.setFap(fap1);
        }else{
            JOptionPane.showMessageDialog(null, "Необходимо выбрать ФАП.");
            return;
        }
        FAP.setVisible(false);
        Acusher.setVisible(true);
        ac_table_filling();       
        AcTable.requestFocus();
       }
    }//GEN-LAST:event_FapTableKeyPressed

    private void ParTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParTableKeyPressed
         // переход к окну выбора Фелдьшера после выбоора ФАПа через enter
         // открывается окно с выбором фельдшера
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){       
            Paramedic.setVisible(false);
            ECG.setVisible(true);
            ECGTable.requestFocus();
        }
    }//GEN-LAST:event_ParTableKeyPressed

    private void nextBut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBut1ActionPerformed
        Paramedic.setVisible(false);
        ECG.setVisible(true);
        ECGTable.requestFocus();
    }//GEN-LAST:event_nextBut1ActionPerformed

    private void backBut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut1ActionPerformed
        Paramedic.setVisible(false);
        FAP.setVisible(true); 
        FapTable.requestFocus();
    }//GEN-LAST:event_backBut1ActionPerformed

    private void ECGTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ECGTableKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ECG.setVisible(false);
            Acusher.setVisible(true);
            AcTable.requestFocus();
        }
    }//GEN-LAST:event_ECGTableKeyPressed

    private void nextBut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBut2ActionPerformed
        ECG.setVisible(false);
        Acusher.setVisible(true);
        AcTable.requestFocus();
    }//GEN-LAST:event_nextBut2ActionPerformed

    private void backBut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut2ActionPerformed
        ECG.setVisible(false);
        FAP.setVisible(true); 
        FapTable.requestFocus();
    }//GEN-LAST:event_backBut2ActionPerformed

    private void backBut4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut4ActionPerformed
        SearchPanel.setVisible(false);
        Acusher.setVisible(true);        
        AcTable.requestFocus();
    }//GEN-LAST:event_backBut4ActionPerformed
       
    private void searchButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButActionPerformed
        try {
            String  crbID = config.getCrbID();
            System.out.println(crbID);
            searchPatient();
            SearchTableModify();
        } catch (Exception ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_searchButActionPerformed
    
    private void searchPatient() throws Exception{
        //<editor-fold defaultstate="collapsed" desc="Поиск пациента в общей таблице">
        String txt = searchTxt.getText();
        txt = txt.replaceAll(" ", "");        
        SearchPatient srch = new SearchPatient();         
        SearchTableModel dtm = new SearchTableModel(false);
        dtm.setDataSource(srch.searchPatient(txt, con.getConnection()));
        SearchTable.setModel(dtm);
        SearchTableModify();
    }
//</editor-fold>
        
  
    private void SearchTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchTableKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                if(SearchTable.getSelectedRow() == -1){JOptionPane.showMessageDialog(null, "Необходимо выбрать пациента.");return;}
                if(SearchTable.getModel().getValueAt(0, 0) == null){JOptionPane.showMessageDialog(null, "Необходимо выбрать пациента.");return;}     
                openPatient();
                SearchTableModify();
            } catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SearchTableKeyPressed

    private void backBut5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut5ActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Диалоговое окно о сохранении рез-ов диспы">
        Object[] options = {"Да",
                    "Нет", "Отмена"};
        int n = JOptionPane.showOptionDialog(this,
        "Данные на форме не сохранены и будут потеряны при выходе.\n                Сохранить изменения?",
        "Сохранение изменений.",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
            Person.setVisible(false);
            SearchPanel.setVisible(true);
        }
        if( n == 1){
            pat_table.removeAllPatientResult(con.getConnection());
            Person.setVisible(false);
            SearchPanel.setVisible(true);
        }
//</editor-fold>             
    }//GEN-LAST:event_backBut5ActionPerformed
    
    private void setPatientParams(String id){
        //<editor-fold defaultstate="collapsed" desc="Получение ФИО  пациента с общей таблицы"> 
       String surname = id.split(" ")[0];
       String name = id.split(" ")[1];
       String middlename = id.split(" ")[2];
       surnameTxt.setText(surname);
       nameTxt.setText(name);
       patrTxt.setText(middlename);
       patient.setName(id);
       patient.setSurname(surname);
       patient.setNname(name);
       patient.setMiddleName(middlename);
    }
//</editor-fold>
         
    private void openPatient() throws Exception{
        //<editor-fold defaultstate="collapsed" desc="Открыть пациента для прох диспансеризации с общей таблицы">
        // открыть найденного пациента 
         
        int row = SearchTable.getSelectedRow(); // узнали id 
        String id = SearchTable.getValueAt(row, 0)+" ";
         
        String birth = (SearchTable.getValueAt(row, 2)+" ");// узнали дату рожд
        birth = birth.replaceAll(" ", "");
        int year = getCurrentYear();        
        int full_age = year - Integer.parseInt(getCurrentAge(birth)); // вычислили кол-во полных лет пациента
        
        String sex = SearchTable.getValueAt(row, 3)+" ";
        sex = sex.replaceAll(" ", "");
        
        UUID uuid = UUID.randomUUID();
        String event = uuid.toString().toUpperCase();
        
        // проверка таблицы с возрастом и полом 
        String age_tab = getIntSex(sex);       
        //if(CheckTableIsNull(age_tab) == 1) return;
        
        String full_name = SearchTable.getValueAt(row, 1).toString();    
        
        getPatientPhoneNum(id);
        
        // добавление в PatientData параметров
        patient.setId(id);
        patient.setUuid(event);
        setPatientParams(full_name);
        birthTxt.setText(birth); 
        patient.setBirth_date(birth);
        patient.setSex(sex);
        result_tab = new FillRezultTable(patient);
        if(pat_table.getPatientServiceFromAgeTab(String.valueOf(full_age),age_tab, con.getConnection()) == -1) {
            JOptionPane.showMessageDialog(null, "Данный пациент не подлежит диспансеризации.");
            return;
        }
        
        // добавление чел-а в первую таблицу вкладки
        
        PatientTableModel dtm = new PatientTableModel(true,con.getConnection(),pat_table,PatServTable);
        dtm.setDataSource(pat_table.showPatientTable(con.getConnection()));
         
        PatServTable.setModel(dtm);
        for (int i = 0; i < PatServTable.getRowCount(); i++) {
            int ind = IndexRow();
            if(ind !=-1){
                dtm.removeRow(IndexRow());  
            }
        }
        result_tab.insertIntoMeasures(con.getConnection(), patient.getId());        
        BarcodeTableUpdating();
        ServiceListTableUpdating();
        ServiceStateObserv();
        sexTxt.setText(sex);
        ageTxt.setText(String.valueOf(full_age));
        SearchPanel.setVisible(false);
        Person.setVisible(true);
        PatientTable();
        PaperTableModify();
        PatientServiceTableModify();
        TabbledPane.setSelectedIndex(0);
    }
    

    private  int  IndexRow(){
        int l = PatServTable.getRowCount();
        for (int i = 0; i < l; i++) {
            if(PatServTable.getModel().getValueAt(i,1).toString().equals("07.Мазок на онкоцитологию (С)")||
                    PatServTable.getModel().getValueAt(i,1).toString().equals("07.Мазок на онкоцитологию (Ж)")||
                    PatServTable.getModel().getValueAt(i,1).toString().equals("09.Маммография расшифровка")){
            return i; 
            }
        }return -1;
    }
//</editor-fold>
    
    private void getPatientPhoneNum(String id){
        //<editor-fold defaultstate="collapsed" desc="Получение номера телефона пациента.">
        SearchPatient srch = new SearchPatient(); 
        String phone = srch.getPhoneNum(id, con.getConnection());
        if(phone != null) phNumTxt.setText(phone);
//</editor-fold>       
    }
    
    private void BarcodeTableUpdating() throws Exception{
        //<editor-fold defaultstate="collapsed" desc="Обновление таблицы с штрихкодами и направлениями">
        BarcodeTableModel md1 = new BarcodeTableModel(true);
        BarcodeTableModel md2 = new BarcodeTableModel(true);
        barcode_tab = new FillBarcodeTable(patient);
        md1.setDataSource(barcode_tab.FillBar( con.getConnection()));
        BarcodeTable.setModel(md1); 
        
        for (int i = 0; i < BarcodeTable.getRowCount(); i++) {
            int ind = CheckBloodInBarcode();
            if(ind !=-1){ 
                BarcodeTable.getModel().setValueAt("04.Холестерин,глюкоза в крови (К)", ind, 0); 
                md1.removeRow(ind + 1);              
                }
        }
        md2.setDataSource(barcode_tab.FillDirect( con.getConnection()));
        DirectTable.setModel(md2);
        
        BarcodeTable.getColumnModel().getColumn(0).setHeaderValue("Наименование");
        BarcodeTable.getColumnModel().getColumn(1).setHeaderValue("");
        BarcodeTable.getColumnModel().getColumn(2).setHeaderValue("Печать");
        BarcodeTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        DirectTable.getColumnModel().getColumn(0).setHeaderValue("Наименование");
        DirectTable.getColumnModel().getColumn(1).setHeaderValue("");
        DirectTable.getColumnModel().getColumn(2).setHeaderValue("Печать");
        DirectTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        BarcodeTableButton(BarcodeTable);
        BarcodeTableButton(DirectTable);
    }
//</editor-fold>

    private int  CheckBloodInBarcode(){
        //<editor-fold defaultstate="collapsed" desc="Проверка выводить ли обобщенное название для печати штрихкода">
        int l = BarcodeTable.getRowCount();
        for (int i = 0; i < l; i++) {
            if(BarcodeTable.getModel().getValueAt(i,0).toString().equals("04.Холестерин в крови (К)")&&
               BarcodeTable.getModel().getValueAt(i+1,0).toString().equals("05.Глюкоза в крови (К)")){
               return i;
            }
        } 
        return -1;
    }
//</editor-fold>
        
    
    private void ServiceListTableUpdating() throws Exception{
        //<editor-fold defaultstate="collapsed" desc="Обновление таблицы с наименованиями услуг во вкладке Услуги">
        RezultTableModel dm = new RezultTableModel(false,con.getConnection(),RezultTable,ServiceListTable,patient);      
        dm.setDataSource(result_tab.fillServiceListTable(patient.getId(), con.getConnection()));
        ServiceListTable.setModel(dm);        
    }
//</editor-fold>
        
    
    private void PaperTableModify(){
        //<editor-fold defaultstate="collapsed" desc="Создание таблицы согласия для печати">
         PaperTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
         PaperTable.getModel().setValueAt("Амбулаторная карта Д", 0, 0);
         PaperTable.getModel().setValueAt("Персональные данные", 1, 0);
         PaperTable.getModel().setValueAt("Анкета", 2, 0);
         PaperTable.getModel().setValueAt("Согласие", 3, 0);
         PaperTable.getColumn("Печать").setCellRenderer(new PrintButtonRenderer());
         PaperTable.getColumn("Печать").setCellEditor(
         new PrintButtonEditor(new JCheckBox(),patient,PaperTable,result_tab,con.getConnection(),session));
         PaperTable.getColumnModel().getColumn(1).setWidth(50);
         PaperTable.getColumnModel().getColumn(1).setPreferredWidth(50);
         PaperTable.getColumnModel().getColumn(1).setMaxWidth(50); 
         PaperTable.getColumnModel().getColumn(2).setWidth(50);
         PaperTable.getColumnModel().getColumn(2).setPreferredWidth(50);
         PaperTable.getColumnModel().getColumn(2).setMaxWidth(50); 
    }
//</editor-fold>
         
    
    private void BarcodeTableButton(JTable table){  
        //<editor-fold defaultstate="collapsed" desc="Создание кнопки печати в таблице с штрихкодами и направлениями ">
        table.getColumnModel().getColumn(1).setCellEditor(BarcodeTable.getDefaultEditor(Boolean.class)); 
        table.getColumnModel().getColumn(1).setCellRenderer(BarcodeTable.getDefaultRenderer(Boolean.class));
        int l = table.getRowCount();
        for (int i = 0; i < l; i++) {
            table.getModel().setValueAt(true, i, 1);
        }
        table.getColumn("Печать").setCellRenderer(new PrintButtonRenderer());
        table.getColumn("Печать").setCellEditor(
        new PrintButtonEditor(new JCheckBox(),patient,table,result_tab,con.getConnection(),session));
        table.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        table.getColumnModel().getColumn(1).setWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setMaxWidth(30);
        table.getColumnModel().getColumn(2).setWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
    }
//</editor-fold>
         

    private void PatientServiceTableModify(){
        //<editor-fold defaultstate="collapsed" desc="Коррекция таблицы с услугами в первой вкладке">
        PatServTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        PatServTable.setFont(new java.awt.Font("Dialog", 0, 16));
        PatServTable.getColumnModel().getColumn(0).setMaxWidth(50);
        PatServTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        PatServTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        PatServTable.getColumnModel().getColumn(0).setHeaderValue("№");
        PatServTable.getColumnModel().getColumn(1).setHeaderValue("Наименование");
        PatServTable.getColumnModel().getColumn(2).setHeaderValue("Состояние");
        PatServTable.getColumnModel().getColumn(3).setHeaderValue("Дата");
        PatServTable.addPropertyChangeListener(new TableCellListener(PatServTable,action));

    }
//</editor-fold>

    
    private void ServiceStateObserv() throws Exception{    
        //<editor-fold defaultstate="collapsed" desc="Cледит за столбцом Состояние  и обновляет цвет в таблице с услугами в 3 вкл ">
        RezultTableModel dm = new RezultTableModel(false,con.getConnection(),RezultTable,ServiceListTable,patient);
        dm.setDataSource(result_tab.fillServiceListTable(patient.getId(), con.getConnection()));
        ServiceListTable.setModel(dm);
        CheckServiceState state = new CheckServiceState(patient);
        state.getChangeState(con.getConnection());
        ServiceListTable.setDefaultRenderer(Object.class, new ServiceListTableRenderer(patient));
        BarcodeTableUpdating();
        ServiceListTableUpdating();
    }
            Action action = new AbstractAction() // следит за столбцом Состояние  и обновляет таблицу с услугами
            {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();              
                    try {                     
                        ServiceStateObserv();                       
                    } catch (Exception ex) {
                        Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }           
            }
        };
//</editor-fold>
            
 
    public void SearchTableModify(){
        //<editor-fold defaultstate="collapsed" desc="Коррекция общей таблицы с пациентами">
        SearchTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 16));
        SearchTable.getColumnModel().getColumn(0).setMaxWidth(95);
        SearchTable.getColumnModel().getColumn(0).setPreferredWidth(95);
        SearchTable.getColumnModel().getColumn(1).setMaxWidth(380);
        SearchTable.getColumnModel().getColumn(1).setPreferredWidth(380);
        SearchTable.getColumnModel().getColumn(2).setWidth(140);
        SearchTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        SearchTable.getColumnModel().getColumn(3).setMaxWidth(40);
        SearchTable.getColumnModel().getColumn(4).setWidth(700);
        SearchTable.getColumnModel().getColumn(4).setPreferredWidth(700);
    }
//</editor-fold>
        
    
    private String getIntSex(String sex){
        //<editor-fold defaultstate="collapsed" desc="Определение пола у чел-а по общей таблице">
        String s;
        if (sex.equals("М")){
            s = "1";
            return s;
        }else{
            s = "2";
            return s;
        }
    }
//</editor-fold>
        
    
    private String getCurrentAge(String str){
        //<editor-fold defaultstate="collapsed" desc="Определение текущего возраста у чел-а">
        int l = str.length();
        str = str.substring(l-4, l);
        return str;        
    }
//</editor-fold>
          
    
    private static int getCurrentYear(){
        //<editor-fold defaultstate="collapsed" desc="Определение тек года для опр возраста чел-а">
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }
//</editor-fold>
        
    
    private void backBut6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut6ActionPerformed
        p2.setVisible(false);
        p1.setVisible(false);
        TabbledPane.setSelectedIndex(0);
    }//GEN-LAST:event_backBut6ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //<editor-fold defaultstate="collapsed" desc="Диалоговое окно при выходе из программы">
        Object[] options = {"Да",
                    "Нет!"};
        int n = JOptionPane.showOptionDialog( this,
        "Завершить работу программы?",
        "Выход",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
            System.exit(0);
        }
        if (n == 1 ){
            return;
        }
        try {
           con.Stop(); 
        } catch (NullPointerException e) {
        }  
//</editor-fold>         
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
           con.Stop(); 
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_formWindowClosed

    private void loginTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginTxtKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            passTxt.requestFocus();
        }
    }//GEN-LAST:event_loginTxtKeyPressed

    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try{              
                searchPatient();
            }catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            SearchTable.requestFocus();
        }
    }//GEN-LAST:event_searchTxtKeyPressed

    private void openButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButActionPerformed
       if(SearchTable.getSelectedRow()==-1){JOptionPane.showMessageDialog(null, "Необходимо выбрать пациента.");return;}
       if(SearchTable.getModel().getValueAt(0, 0) == null){JOptionPane.showMessageDialog(null, "Необходимо выбрать пациента.");return;}
        try {
            openPatient();
        } catch (Exception ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openButActionPerformed

    private void nextBut4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBut4ActionPerformed
        p1.setVisible(false);
        p2.setVisible(true);
        TabbledPane.setSelectedIndex(1);        
    }//GEN-LAST:event_nextBut4ActionPerformed

    private void nextBut6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBut6ActionPerformed
        p2.setVisible(false);
        p3.setVisible(true);
        ServiceListTable.changeSelection(0, 0, false, false);
        ServiceListTable.requestFocus();
        changeServiceList();
        TabbledPane.setSelectedIndex(2);
    }//GEN-LAST:event_nextBut6ActionPerformed

    private void backBut7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut7ActionPerformed
        p3.setVisible(false);
        p2.setVisible(true);
        TabbledPane.setSelectedIndex(1);
    }//GEN-LAST:event_backBut7ActionPerformed

    private void confirmtButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmtButActionPerformed
        Object[] options = {"Да",
                    "Нет!"};
        int n = JOptionPane.showOptionDialog(this,
        "Закончить осмотр пациента?",
        "Выход из осмотра",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
            if(phNumTxt.getText().trim().length() == 0 || phNumTxt.getText().equals("Введите номер телефона...")){
                JOptionPane.showMessageDialog(null, "Необходимо заполнить номер телефона.");
                p3.setVisible(false);
                p1.setVisible(true);
                TabbledPane.setSelectedIndex(0);               
            }else{
                for (int i = 0; i < PatServTable.getRowCount(); i++) {
                    if(PatServTable.getValueAt(i, 3).toString().equals("Выберите дату")){
                        JOptionPane.showMessageDialog(null, "Не заполнена дата выполнения одной из услуг.");
                        p3.setVisible(false);
                        p1.setVisible(true);
                        TabbledPane.setSelectedIndex(0);
                        return;
                    }
                }          
                String passportFlag = "0";
                String policyFlag = "0";
                String addressFlag = "0";
                String phoneFlag = "0";
                if(jCheckBox1.isSelected()){
                    passportFlag = "1";
                }
                if(jCheckBox2.isSelected()){
                    policyFlag = "1";
                }
                if(jCheckBox3.isSelected()){
                    addressFlag = "1";
                }
                if(jCheckBox4.isSelected()){
                    phoneFlag = "1";
                }
                String phoneNum = phNumTxt.getText();            
                FillReadyTable ready = new FillReadyTable(con.getConnection(),patient,session);
                ready.FillResult(session.getFap());
                ready.insertPersonalInfoChange(passportFlag, policyFlag, addressFlag, phoneFlag);
                ready.insertPhoneNum(phoneNum);
                Person.setVisible(false);
                SearchPanel.setVisible(true);
                phNumTxt.setText("");
                GhostJTextField gh = new GhostJTextField(phNumTxt, "Введите номер телефона...");
                jCheckBox1.setSelected(false);
                jCheckBox3.setSelected(false);
                jCheckBox2.setSelected(false);
                jCheckBox4.setSelected(false);
                
            }
        }
    }//GEN-LAST:event_confirmtButActionPerformed

    private void cancelButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButActionPerformed
        Object[] options = {"Да",
                    "Нет", "Отмена"};
        int n = JOptionPane.showOptionDialog(this,
         "Данные на форме не сохранены и будут потеряны при выходе.\n                Сохранить изменения?",
        "Сохранение изменений.",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
            Person.setVisible(false);
            SearchPanel.setVisible(true);
        }
        if( n == 1){
            pat_table.removeAllPatientResult(con.getConnection());
            Person.setVisible(false);
            SearchPanel.setVisible(true);
        }
        
    }//GEN-LAST:event_cancelButActionPerformed

    private void ServiceListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ServiceListTableMouseClicked
        //<editor-fold defaultstate="collapsed" desc="Коррекция таблицы с услугами в 3-й вкл и кнопки">
        changeServiceList();
//</editor-fold>        
    }//GEN-LAST:event_ServiceListTableMouseClicked
  
    public void updateResultTable(){
        //<editor-fold defaultstate="collapsed" desc="Обновление таблицы с заполняемыми данными в 3-й вкл">
        String value = ServiceListTable.getValueAt(ServiceListTable.getSelectedRow(), 0).toString();        
        String code = result_tab.getServiceCode(con.getConnection(), patient.getId(), value);
        ResultSet rs =  result_tab.fillParamTable(code, value, con.getConnection());
        RezultTableModel tm = new RezultTableModel(true, con.getConnection(),RezultTable,ServiceListTable,patient);
        try {
            tm.setDataSource(rs);              
        } catch (Exception ex) {
            log.info(ex,ex);
        }
        tm.Show();
        RezultTable.setModel(tm);
        RezultTable.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 14));
        RezultTable.getColumnModel().getColumn(0).setHeaderValue("Параметр");       
        RezultTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        RezultTable.getColumnModel().getColumn(1).setMaxWidth(300);
        RezultTable.getColumnModel().getColumn(1).setHeaderValue("Значение"); 
    //</editor-fold>    
    }
  
    private void SearchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchTableMouseClicked
        if(evt.getClickCount() == 2){
            try {
                openPatient();
            }catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SearchTableMouseClicked

    private void RezultTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RezultTableMouseClicked
        int row = RezultTable.getSelectedRow();
        String value = RezultTable.getValueAt(row, 0).toString();
        RezultTableModel tm = new RezultTableModel(true, con.getConnection(),RezultTable,ServiceListTable,patient);
        System.out.println("row = "+ row + " value is " + value);
        tm.check(value);
        
        
    }//GEN-LAST:event_RezultTableMouseClicked

    private void RezultTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RezultTableKeyTyped
        char ch = evt.getKeyChar();
        if(!(ch==KeyEvent.VK_BACK_SPACE ||  Character.isDigit(ch) || ch == KeyEvent.VK_ENTER)){            
            evt.consume();
        }
    }//GEN-LAST:event_RezultTableKeyTyped

    private void addResButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addResButActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Добавление экг из прибора и рез-ов с урино.">
       String value = ServiceListTable.getValueAt(ServiceListTable.getSelectedRow(), 0).toString(); 
       String id = patient.getId(); 
        
       switch (value) {
            case "06.Электрокардиография в покое":
                File folder = new File("C:\\ECGReceiver\\Out");
                File[] listOfFiles = folder.listFiles();
                int i = 0;
                if (i < listOfFiles.length){
                    File file = listOfFiles[i];
                    if ((file.isFile()) && (file.getName().endsWith(".pdf"))){
                        String path = file.getAbsolutePath();
                        for (int j = 0; j < listOfFiles.length; j++){
                            File file1 = listOfFiles[j];
                            if ((file1.isFile()) && (file1.getName().endsWith(".ecg"))) {
                                String ecgFile = file1.getAbsolutePath();
                                try {
                                    result_tab.insertPdfBytes(this.session, id, this.con.getConnection(), path, ecgFile);
                                } catch (IOException ex) { log.info(ex,ex); }
                          }
                        }
                        this.RezultTable.setValueAt("Файл прикреплен", 0, 1);
                        repaint();
                        return;
                  }else 
                  JOptionPane.showMessageDialog(null, "Файл с ЭКГ не найден.Повторите попытку снятия ЭКГ.");return;
                }break;
            case "13.Общий анализ мочи":         
                String typeOfDevice = config.getTypeOfUrino();
                switch(typeOfDevice){
                    case "0" : {
                        DocUReader read= new DocUReader(con.getConnection(),result_tab,patient,this);                
                        try {
                           read.run();
                           addResBut.setEnabled(false);
                        } catch (SerialPortException ex) {
                           log.info(ex,ex);
                        }
                        if(read.flag = true){
                           updateResultTable();
                        }             
                        break;
                    }
                    case "1" :{
                        BC01Reader read = new BC01Reader(patient, con.getConnection(), result_tab, this);
                        addResBut.setEnabled(false);
                        read.start();                       
                    }    
                } break;              
        } 
//</editor-fold>   
    }//GEN-LAST:event_addResButActionPerformed
    
    private void launchECBButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchECBButActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Запуск прогр считывания ЭКГ">
        String value = ServiceListTable.getValueAt(ServiceListTable.getSelectedRow(), 0).toString();         
        if (value.contains("06.Электрокардиография ")){
            File dir = new File("C:\\ECGReceiver\\Out");
            try {
               FileUtils.cleanDirectory(dir); 
            } catch (IOException e) {
                log.info(e,e);
            }
            
            WriteXMLFileForECG write = new WriteXMLFileForECG();
            write.writeXML(patient);  
            try{
                String  path = config.getEcgAppPath();
                Process process = new ProcessBuilder(path).start();
                addResBut.setEnabled(true);
            }catch(IOException ex){
                log.info(ex,ex);
            }
        }       
//</editor-fold>
    }//GEN-LAST:event_launchECBButActionPerformed

    private void printButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Печать всего,что есть">
       int b = BarcodeTable.getRowCount();
       int d = DirectTable.getRowCount();
       int p = PaperTable.getRowCount();
       for (int i = 0; i < b; i++) {
         PrintImpl print = new PrintImpl(con.getConnection(), BarcodeTable, result_tab, patient,session);
         print.print(i);
        }
       for (int i = 0; i < d; i++) {
         PrintImpl print = new PrintImpl(con.getConnection(), DirectTable, result_tab, patient,session);
         print.print(i);
        }
       for (int i = 0; i < p; i++) {
         PrintImpl print = new PrintImpl(con.getConnection(), PaperTable, result_tab, patient,session);
         print.print(i);
        } 
//</editor-fold>             
    }//GEN-LAST:event_printButActionPerformed

    private void printCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printCheckActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Печать выбранных ">
       int b = BarcodeTable.getRowCount();
       int d = DirectTable.getRowCount();
       int p = PaperTable.getRowCount();       
         for (int i = 0; i < b; i++) {            
            if(BarcodeTable.getModel().getValueAt(i, 1).toString().equals("true")){  
             PrintImpl print = new PrintImpl(con.getConnection(), BarcodeTable, result_tab, patient,session);
             print.print(i);
            }
        }         
         for (int i = 0; i < d; i++) {
            if(DirectTable.getModel().getValueAt(i, 1).toString().equals("true")){
                PrintImpl print = new PrintImpl(con.getConnection(), DirectTable, result_tab, patient,session);
                print.print(i);
            }
        }       
         for (int i = 0; i < p; i++) {
            if(PaperTable.getModel().getValueAt(i, 1).toString().equals("true")){
                PrintImpl print = new PrintImpl(con.getConnection(), PaperTable, result_tab, patient,session);
                print.print(i);
            }
        }
//</editor-fold>     
    }//GEN-LAST:event_printCheckActionPerformed

    private void ServiceListTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ServiceListTableKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
            RezultTable.setCellSelectionEnabled(true);
            RezultTable.changeSelection(0, 1, false, false);
            RezultTable.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP){
            changeServiceList();         
        }
    }//GEN-LAST:event_ServiceListTableKeyReleased
   
    private void changeServiceList(){
        String value = ServiceListTable.getValueAt(ServiceListTable.getSelectedRow(), 0).toString(); 
        addResBut.setEnabled(true);
        switch (value) {
            case "06.Электрокардиография в покое":
                addResBut.setEnabled(true);
                addResBut.setVisible(true);
                launchECBBut.setVisible(true);
                helpBut.setVisible(true);
                break;
            case "13.Общий анализ мочи":
                addResBut.setVisible(true);
                launchECBBut.setVisible(false);
                helpBut.setVisible(true);
                break;
            default:
                addResBut.setVisible(false);
                launchECBBut.setVisible(false);
                helpBut.setVisible(false);
                break;
        }
        updateResultTable();
    }
      
    private void RezultTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RezultTableKeyReleased
        int s = ServiceListTable.getSelectedRow()+1;
        if(evt.getKeyCode() == KeyEvent.VK_LEFT){ 
            ServiceListTable.changeSelection(s-1, 0, false, false);
            ServiceListTable.requestFocus();
        }
        int row = RezultTable.getSelectedRow();
        
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){  
               // переход на новую услугу при достижении конца параметров  
                int length = RezultTable.getRowCount();        
                if((row +length)== length){
                    ServiceListTable.requestFocus();
                    if(s == ServiceListTable.getRowCount()){
                        ServiceListTable.changeSelection(0,0,false, false);                   
                    }
                    else{
                        ServiceListTable.changeSelection(s,0,false, false);
                        }
                    changeServiceList();
                    RezultTable.setCellSelectionEnabled(true);
                    RezultTable.changeSelection(0, 1, false, false);
                    RezultTable.requestFocus();         
                }
        }
    }//GEN-LAST:event_RezultTableKeyReleased

    private void synchronButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronButActionPerformed
        Thread thread = new Thread(this);
        thread.start();
    }//GEN-LAST:event_synchronButActionPerformed
     
    @Override
    public void run() {
        //<editor-fold defaultstate="collapsed" desc="Запуск клиента для обмена с данными">
        String value = config.getServerType();
        switch(value){
            case "0": {
                runMDCServer();
            }break;
            case "1": {
            try {
                runTISServer();
            } catch (ParseException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            }break;                   
        }       
//</editor-fold>       
    }
    
    private void runMDCServer(){
        //<editor-fold defaultstate="collapsed" desc="Запуск общения с  МДК сервер">
        synchronBut.setEnabled(false);
        synchronBut1.setEnabled(false);
        synchronBut2.setEnabled(false);
        GetResultsForMdcServer patientResult = new GetResultsForMdcServer();          
        PatientListParser patientList = new PatientListParser(con.getConnection());
        ServiceParamsParser serv = new ServiceParamsParser(con.getConnection());
        FapListParser fapList = new FapListParser(con.getConnection());
        DoctorListParser docList = new DoctorListParser(con.getConnection());
        PatientServerStatusChange status = new PatientServerStatusChange(con.getConnection());
        MdcClient client;  
        String  crbID = config.getCrbID();
        String  tmp  = config.getDownAllPatient();
        boolean allFlag = false;
        if(tmp.equals("true")){
            allFlag = true;
            config.EditPAtientDownloadingFlag();      
        }   
        System.out.println(crbID);
        client = new MdcClient(patientResult,con.getConnection(),patientList,serv,crbID,fapList,docList,status,allFlag,this);
        try {          
              patientResult.writeXML(con.getConnection());
            } catch (SQLException ex) {
            log.error(ex,ex);
        }
        try {             
            client.run();
        } catch (Exception ex) {
            log.error(ex,ex);
        }
//</editor-fold>       
    }
    
    private void runTISServer() throws ParseException{
        //<editor-fold defaultstate="collapsed" desc="Запуск общения с  ТИС сервер">
        TisClient client = new TisClient(con.getConnection(),this);
        try {
            client.run();
        } catch (JSONException ex) {
            log.info(ex,ex);
        } catch (SQLException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
//</editor-fold>      
    }
    
    private  boolean checkInternetConnection() {
        //<editor-fold defaultstate="collapsed" desc="Проверка соединения с интерн для подкл к серверу">
        Boolean result = false;
        HttpURLConnection inet = null;
        try {            
            inet = (HttpURLConnection) new URL("http://google.com/").openConnection();
            inet.setRequestMethod("HEAD");
            result = (inet.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            log.info(e,e);
        } finally {
            if (inet != null) {
                try {
                    inet.disconnect();
                } catch (Exception e) {
                    log.info(e,e);
                }
            }
    }
    return result;
//</editor-fold>    
    }
    private  boolean CheckHostAvailability() { 
        //<editor-fold defaultstate="collapsed" desc="Проверка подкл к серверу при обмене данными">
        try (
            Socket s = new Socket(SERVER_ADDRESS, Integer.parseInt(TCP_SERVER_PORT))) {
            return true;
    } catch (IOException ex) {
        log.info(ex,ex);
    }
    return false;
//</editor-fold>
    }       
    private void AcTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AcTableKeyPressed
        //<editor-fold defaultstate="collapsed" desc="Переход на вкладку поиска Пациентов с выбора Акушера с клавиатуры">
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            int row = AcTable.getSelectedRow();
            if(row != -1){
            FillAcusherTable ac = new FillAcusherTable();
            String acusher = AcTable.getValueAt(row,0).toString();
            session.setAcusher(ac.getAcusherID(con.getConnection(), acusher));
        }
        else {
            session.setAcusher("");
        }
        Acusher.setVisible(false);
        SearchTableModify();
        SearchPanel.setVisible(true);
        searchTxt.requestFocus();
        }
//</editor-fold>     
    }//GEN-LAST:event_AcTableKeyPressed
    
    private void nextBut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBut3ActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Переход на вкладку поиска Пациентов с выбора Акушера с кнопки">
        int row = AcTable.getSelectedRow();
        if(row != -1){
            FillAcusherTable ac = new FillAcusherTable();
            String acusher = AcTable.getValueAt(row,0).toString();
            session.setAcusher(ac.getAcusherID(con.getConnection(), acusher));
        }
        else {
           JOptionPane.showMessageDialog(null,"Необходимо выбрать акушера.");
           return;
        }
        Acusher.setVisible(false);
        SearchTableModify();
        SearchPanel.setVisible(true);
        searchTxt.requestFocus();
//</editor-fold>       
    }//GEN-LAST:event_nextBut3ActionPerformed

    private void backBut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBut3ActionPerformed
        Acusher.setVisible(false);
        FAP.setVisible(true);
        FapTable.requestFocus();
    }//GEN-LAST:event_backBut3ActionPerformed

    private void FapTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FapTableMouseClicked
        if(evt.getClickCount() == 2){
            try {
                int row = FapTable.getSelectedRow();
                if(row != -1){
                    FillFapTable fap = new FillFapTable();
                    String fap1 = FapTable.getValueAt(row,0).toString();
                    fap1 = fap.getFapCode(con.getConnection(), fap1);
                    session.setFap(fap1);
                }else {
                    JOptionPane.showMessageDialog(null,"Необходимо выбрать акушера.");
                    return;
                 }
                FAP.setVisible(false);
                Acusher.setVisible(true);                
                AcTable.requestFocus();  
                ac_table_filling();
            } catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_FapTableMouseClicked

    private void FapTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FapTxtKeyReleased
       filterFap();
    }//GEN-LAST:event_FapTxtKeyReleased

    private void searchFapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFapActionPerformed
       filterFap();
    }//GEN-LAST:event_searchFapActionPerformed

    private void AcTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcTableMouseClicked
        if(evt.getClickCount() == 2){
            try {
                int row = AcTable.getSelectedRow();
                if(row != -1){
                     FillAcusherTable ac = new FillAcusherTable();
                     String acusher = AcTable.getValueAt(row,0).toString();
                     session.setAcusher(ac.getAcusherID(con.getConnection(), acusher));            
                }else {
                    JOptionPane.showMessageDialog(null,"Необходимо выбрать акушера.");
                    return;
                 }
                Acusher.setVisible(false);
                SearchPanel.setVisible(true);
                SearchTableModify();
                searchTxt.requestFocus();
            } catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_AcTableMouseClicked

    private void addPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientActionPerformed
        addPatDialog.setVisible(true);
    }//GEN-LAST:event_addPatientActionPerformed
    
    private boolean CheckSnils(String txt){
        //<editor-fold defaultstate="collapsed" desc="Проверка контр суммы СНИЛС">
        String[] str = txt.split("");
        int sum = 0;
        if(str.length != 11){
            return false;
        } else {
            for (int j = 0; j < 9; j++) {
                sum += Integer.parseInt(str[j]) * (8- j +1);
                sum = sum % 101;
            }       
                if(Integer.parseInt(txt.substring(9, 11)) == sum){
                    return true;
                }else{
                    return false;
                }           
        }
//</editor-fold>      
    }   
    
    private void addPatButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatButActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Добавление нового пациента с кнопки">
        try {
        addPatDialog.setVisible(true);
        Object[] options = {"Да",
                    "Нет!"};
        int n = JOptionPane.showOptionDialog( addPatDialog,
        "Добавить пациента?",
        "Подтверждение",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
         AddNewPatient addPat = new AddNewPatient(con.getConnection());
            String surname = patSurTxt.getText();
            String name = patNameTxt.getText();
            String middle = patMiddTxt.getText();
            String sex = "";
            if (mRad.isSelected()){
                sex = "М";
            }
            if (jRad.isSelected()){
                sex = "Ж";
            } 
            
            String crbID = config.getCrbID();
            crbID = crbID.split("//.")[0];
            String adress = patAdressTxt.getText();
            String  lpu_id = patLPUTxt.getText();
            String snils = SnilsTxt.getText();
            snils = snils.replaceAll("-", "");
            snils = snils.replaceAll(" ", "");
            if(CheckSnils(snils)){
                System.out.println("good work");
            }else{
                JOptionPane.showMessageDialog(null, 
                        "Проверьте правильность СНИЛС!");
            }
            String[] pass = PassTxt.getText().split(" ");
            int pass_ser = Integer.parseInt(pass[0]);
            int pass_num = Integer.parseInt(pass[1]);
            String oldPolice =" ";
            if(oldPoliceTxt.getText().trim().length() == 0){
                System.out.println("bad work baby");
            }else{
                oldPolice = oldPoliceTxt.getText();
            }
            String newPolice =" ";
            if(newPoliceTxt.getText().trim().length() == 0){
                System.out.println("bad work baby");
            }else{
                newPolice = oldPoliceTxt.getText();
            }
             String phone = phoneTxt.getText();
            int year = getCurrentYear();  
            Date now = null;
            DateFormat formatter = null;
            String birth = "";
                
            if(birthChooser.getDate()!= null){
                now = birthChooser.getDate();
                formatter = new SimpleDateFormat("dd.MM.yyyy");
                birth = formatter.format(now);
                if(birth != null){
                   birthdateTxt.setText(birth); 
                } 
           }else{
                birth = birthdateTxt.getText();
            }   
                
                int full_age = year - Integer.parseInt(getCurrentAge(birth)); // вычислили кол-во полных лет пациента
                // проверка таблицы с возрастом и полом 
                String age_tab = getIntSex(sex);  
                addPat.addPatient(surname, name, middle, sex, birth, adress, lpu_id,crbID);
                addPat.addPatientDocs(snils, pass_ser,pass_num, oldPolice, newPolice,phone);
                addPatDialog.setVisible(false);
            try {
                openAddPatient(String.valueOf(addPat.getId()),birth,sex,surname,name,middle);
            } catch (Exception ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            if( n == 1){
                
            addPatDialog.setVisible(true);
            }
            patSurTxt.setText("");
            patNameTxt.setText("");
            patMiddTxt.setText("");
            patAdressTxt.setText("");
            patLPUTxt.setText("");
            SnilsTxt.setText("");
            oldPoliceTxt.setText("");
            newPoliceTxt.setText("");
            birthChooser.setDate(null);
            PassTxt.setText("");
            phoneTxt.setText("");
            
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Ошибка при добавлении пациента!Проверьте  правильность ввода данных.");
        }
//</editor-fold>      
    }//GEN-LAST:event_addPatButActionPerformed

    private void openAddPatient(String id,String birth,String sex,String surname,String name,String middlename) throws Exception{
        //<editor-fold defaultstate="collapsed" desc="Открыть вновь добавленного пациента для диспы">
         birth = birth.replaceAll(" ", "");
        int year = getCurrentYear();        
        int full_age = year - Integer.parseInt(getCurrentAge(birth)); // вычислили кол-во полных лет пациента

        sex = sex.replaceAll(" ", "");
        
        // проверка таблицы с возрастом и полом 
        String age_tab = getIntSex(sex);       
        UUID uuid = UUID.randomUUID();
        String event = uuid.toString().toUpperCase();
        
        // добавление в PatientData параметров
        patient.setId(id);
        surnameTxt.setText(surname);
        nameTxt.setText(name);
        patrTxt.setText(middlename);
        String full_name = surname + " "+name + " "+middlename;
        patient.setName(full_name);
        birthTxt.setText(birth); 
        patient.setBirth_date(birth);
        patient.setSex(sex);
        patient.setUuid(event);
        patient.setSurname(surname);
        patient.setNname(name);
        patient.setMiddleName(middlename);
        if(pat_table.getPatientServiceFromAgeTab(String.valueOf(full_age),age_tab, con.getConnection()) == -1) {
            JOptionPane.showMessageDialog(null, "Данный пациент не подлежит диспансеризации.");
            return;
        }
        // добавление чел-а в первую таблицу вкладки
        
        PatientTableModel dtm = new PatientTableModel(true,con.getConnection(),pat_table,PatServTable);
        dtm.setDataSource(pat_table.showPatientTable(con.getConnection()));
        result_tab = new FillRezultTable(patient);
        PatServTable.setModel(dtm);
        for (int i = 0; i < PatServTable.getRowCount(); i++) {
            int ind = IndexRow();
            if(ind !=-1){
            dtm.removeRow(IndexRow());  
            }
        }
        result_tab.insertIntoMeasures(con.getConnection(), patient.getId());        
        BarcodeTableUpdating();
        ServiceListTableUpdating();
        ServiceStateObserv();
        sexTxt.setText(sex);
        ageTxt.setText(String.valueOf(full_age));
        SearchPanel.setVisible(false);
        Person.setVisible(true);
        PatientTable();
        PaperTableModify();
        PatientServiceTableModify();
        TabbledPane.setSelectedIndex(0);
//</editor-fold>
        // открыть найденного пациента     
    }
    private void cancelAddPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddPatActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Отмена в диалоге при добавлении нового пациента">
        Object[] options = {"Да",
                    "Нет"};
        int n = JOptionPane.showOptionDialog( addPatDialog,
        "Отменить добавление пациента?",
        "Отмена.",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);
        if (n == 0 ){
            addPatDialog.setVisible(false);
        }
        if( n == 1){          
            addPatDialog.setVisible(true);
        }
//</editor-fold>       
    }//GEN-LAST:event_cancelAddPatActionPerformed

    private void TabbledPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabbledPaneMouseClicked
        ServiceListTable.changeSelection(0, 0, false, false);
        ServiceListTable.requestFocus();
        changeServiceList();
    }//GEN-LAST:event_TabbledPaneMouseClicked

    private void AcTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AcTxtKeyReleased
       filterAcusher();
    }//GEN-LAST:event_AcTxtKeyReleased

    private void SnilsTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SnilsTxtKeyReleased
        //<editor-fold defaultstate="collapsed" desc="Проверка текст поля СНИЛС на контр четности">
        if(evt.getKeyCode() != KeyEvent.VK_ENTER){
        String snils = SnilsTxt.getText();
            snils = snils.replaceAll("-", "");
            snils = snils.replaceAll(" ", "");
        if(snils.length() == 11){
            if(CheckSnils(snils)){
                
            }else{               
                JOptionPane.showMessageDialog(null, 
                        "Ошибка при вводе номера СНИЛС! Проверьте корректность вводимых данных.");
                }
            }
        }
//</editor-fold>     
    }//GEN-LAST:event_SnilsTxtKeyReleased

    private void birthChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_birthChooserPropertyChange
        if(birthChooser.getDate()!= null){
        Date now = birthChooser.getDate();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formatter.format(now);
        if(birth != null){
           birthdateTxt.setText(birth); 
            } 
        }           
    }//GEN-LAST:event_birthChooserPropertyChange

    private void PatServTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PatServTableMouseReleased

    }//GEN-LAST:event_PatServTableMouseReleased

    private void synchronBut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronBut1ActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Обмен данными с сервером с вкладки выбора акушера">
//        if(checkInternetConnection() == false){
//            JOptionPane.showMessageDialog(null, "Ошибка при подключении к интернету.Проверьте соответствующее подключение.");
//            return;
//        }
//       if(CheckHostAvailability()== false){
//           JOptionPane.showMessageDialog(null, "Нет доступа к серверу.Проверьте настройки подключения к нему.");
//           return;
//       }
        Thread thread = new Thread(this);
        thread.start();   
        
//</editor-fold> 
    }//GEN-LAST:event_synchronBut1ActionPerformed

    private void synchronBut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronBut2ActionPerformed
        //<editor-fold defaultstate="collapsed" desc="Обмен данными с сервером с вкладки выбора ФАПа">
//        if(checkInternetConnection() == false){
//            JOptionPane.showMessageDialog(null, "Ошибка при подключении к интернету.Проверьте соответствующее подключение.");
//            return;
//        }
//       if(CheckHostAvailability()== false){
//           JOptionPane.showMessageDialog(null, "Нет доступа к серверу.Проверьте настройки подключения к нему.");
//           return;
//       }   
        session.setFap("");
        Thread thread = new Thread(this);
        thread.start();    
//</editor-fold>     
    }//GEN-LAST:event_synchronBut2ActionPerformed

    private void helpButMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpButMouseExited
        helpBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/help.png")));
        helpEcgDialog.setVisible(false);
        helpUrinoDialog.setVisible(false);
    }//GEN-LAST:event_helpButMouseExited

    private void helpButMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpButMouseReleased
        helpBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/help(exited).png")));
        String value = ServiceListTable.getValueAt(ServiceListTable.getSelectedRow(), 0).toString(); 
        switch (value) {
            case "06.Электрокардиография в покое":
                helpEcgDialog.setVisible(true);
                break;
            case "13.Общий анализ мочи":
                helpUrinoDialog.setVisible(true);
                break;
            default:
                break;
        }        
    }//GEN-LAST:event_helpButMouseReleased

    private void helpButMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpButMouseEntered
        helpBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/help(exited).png")));
    }//GEN-LAST:event_helpButMouseEntered

    private void perInfoChangeButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perInfoChangeButActionPerformed
        personalInfoDialog.setVisible(true);
    }//GEN-LAST:event_perInfoChangeButActionPerformed

    private void okPersInfoButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okPersInfoButActionPerformed
       personalInfoDialog.setVisible(false);
    }//GEN-LAST:event_okPersInfoButActionPerformed

    private void RezultTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RezultTableMousePressed

    }//GEN-LAST:event_RezultTableMousePressed
    
    private String addDotToDate(String txt){
        txt = txt.substring(0,2)+ "." + txt.substring(2,4) + "." + txt.substring(4,8);
        System.out.println(txt);
        return txt;
    }
    
    private void filterAcusher(){
        //<editor-fold defaultstate="collapsed" desc="Создние фильтра для таблицы с акушерами">
        TableRowSorter<TableModel> rowSorter  = new TableRowSorter<>(AcTable.getModel());
        AcTable.setRowSorter(rowSorter);
        String text = AcTxt.getText();
        if (text.trim().length() == 0) {
           rowSorter.setRowFilter(null);
        } else {
           rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
//</editor-fold>     
    }
    
    private void filterFap(){
        //<editor-fold defaultstate="collapsed" desc="Создание фильтра для таблицы с фапами">
             TableRowSorter<TableModel> rowSorter  = new TableRowSorter<>(FapTable.getModel());
     FapTable.setRowSorter(rowSorter);
       String text = FapTxt.getText();
        if (text.trim().length() == 0) {
           rowSorter.setRowFilter(null);
        } else {
           rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
//</editor-fold>
    }
    
   private void PatientTable(){   
       //<editor-fold defaultstate="collapsed" desc="Модификация 1-й таблицы со всеми услугами">
       cb = new JComboBox();
       cb.setModel(new DefaultComboBoxModel(new String[] {"Назначено", "Выполнено ранее", "Отказ"}));
       cb.setFont(new java.awt.Font("Dialog", 0, 16));
       TableColumn tc = PatServTable.getColumnModel().getColumn(2);       
       TableCellEditor tce = new DefaultCellEditor(cb);
       tc.setCellEditor(tce);       
       tc = PatServTable.getColumnModel().getColumn(3);
       JFormattedTextField jf = new JFormattedTextField();
       try {
            jf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.##.####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jf.setFont(new java.awt.Font("Arial", 0, 14));
        jf.setCaretPosition(0);
        DefaultCellEditor tcj = new DefaultCellEditor(jf);
        tcj.setClickCountToStart(1);
        mdc.gui.tools.JDateChooserCellEditor dateCol = new JDateChooserCellEditor();
        tc.setCellEditor(dateCol);
//</editor-fold>   
    }
    
    private JComboBox cb; 
    private ReadConfigXml config = new ReadConfigXml();
    private StartDB con = null;
    private final PatientData patient = new PatientData();
    private final SessionData session = new SessionData();
    private final FillPatientTable pat_table = new FillPatientTable(patient,session);
    private FillRezultTable result_tab ;
    private FillBarcodeTable barcode_tab ;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AcTable;
    private javax.swing.JTextField AcTxt;
    private javax.swing.JPanel Acusher;
    private javax.swing.JTable BarcodeTable;
    private javax.swing.JTable DirectTable;
    private javax.swing.JPanel ECG;
    private javax.swing.JTable ECGTable;
    private javax.swing.JTextField EcgTxt;
    private javax.swing.JPanel FAP;
    private javax.swing.JTable FapTable;
    private javax.swing.JTextField FapTxt;
    private javax.swing.JTable PaperTable;
    private javax.swing.JTable ParTable;
    private javax.swing.JTextField ParTxt;
    private javax.swing.JPanel Paramedic;
    private javax.swing.JFormattedTextField PassTxt;
    private javax.swing.JTable PatServTable;
    private javax.swing.JPanel Person;
    public javax.swing.JTable RezultTable;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JTable SearchTable;
    private javax.swing.JTable ServiceListTable;
    private javax.swing.JFormattedTextField SnilsTxt;
    private javax.swing.JTabbedPane TabbledPane;
    private javax.swing.JButton addPatBut;
    private javax.swing.JDialog addPatDialog;
    private javax.swing.JPanel addPatPanel;
    private javax.swing.JButton addPatient;
    public javax.swing.JButton addResBut;
    private javax.swing.JTextField ageTxt;
    private javax.swing.JButton backBut1;
    private javax.swing.JButton backBut2;
    private javax.swing.JButton backBut3;
    private javax.swing.JButton backBut4;
    private javax.swing.JButton backBut5;
    private javax.swing.JButton backBut6;
    private javax.swing.JButton backBut7;
    private com.toedter.calendar.JDateChooser birthChooser;
    private javax.swing.JTextField birthTxt;
    private javax.swing.JFormattedTextField birthdateTxt;
    private javax.swing.JButton cancelAddPat;
    private javax.swing.JButton cancelBut;
    private javax.swing.JButton confirmtBut;
    private javax.swing.JButton exitBut;
    private javax.swing.JLabel helpBut;
    private javax.swing.JDialog helpEcgDialog;
    private javax.swing.JDialog helpUrinoDialog;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    public javax.swing.JProgressBar jPrg;
    private javax.swing.JRadioButton jRad;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton launchECBBut;
    private javax.swing.JButton loginBut;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JTextField loginTxt;
    private javax.swing.JRadioButton mRad;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField newPoliceTxt;
    private javax.swing.JButton nextBut;
    private javax.swing.JButton nextBut1;
    private javax.swing.JButton nextBut2;
    private javax.swing.JButton nextBut3;
    private javax.swing.JButton nextBut4;
    private javax.swing.JButton nextBut6;
    private javax.swing.JButton okPersInfoBut;
    private javax.swing.JFormattedTextField oldPoliceTxt;
    private javax.swing.JButton openBut;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p3;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JTextField patAdressTxt;
    private javax.swing.JTextField patLPUTxt;
    private javax.swing.JTextField patMiddTxt;
    private javax.swing.JTextField patNameTxt;
    private javax.swing.ButtonGroup patSexGroup;
    private javax.swing.JTextField patSurTxt;
    private javax.swing.JTextField patrTxt;
    private javax.swing.JButton perInfoChangeBut;
    private javax.swing.JDialog personalInfoDialog;
    private javax.swing.JTextField phNumTxt;
    private javax.swing.JTextField phoneTxt;
    public javax.swing.JDialog prg;
    private javax.swing.JButton printBut;
    private javax.swing.JButton printCheck;
    private javax.swing.JButton searchAcusher;
    private javax.swing.JButton searchBut;
    private javax.swing.JButton searchFap;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTextField sexTxt;
    private javax.swing.JPanel subAc;
    private javax.swing.JPanel subECG;
    private javax.swing.JPanel subFAP;
    private javax.swing.JPanel subLogin;
    private javax.swing.JPanel subPar;
    private javax.swing.JTextField surnameTxt;
    public javax.swing.JButton synchronBut;
    public javax.swing.JButton synchronBut1;
    public javax.swing.JButton synchronBut2;
    // End of variables declaration//GEN-END:variables

  
}
