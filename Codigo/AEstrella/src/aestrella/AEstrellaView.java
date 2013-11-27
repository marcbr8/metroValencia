/*
 * AEstrellaView.java
 */

package aestrella;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class AEstrellaView extends FrameView {

    public AEstrellaView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
                listadoOrigen.add("Aeroport");
        listadoOrigen.add("Alameda");
        listadoOrigen.add("Albalat dels Sorells");
        listadoOrigen.add("Alberic");
        listadoOrigen.add("Alboraya-Palmaret");
        listadoOrigen.add("Alboraya-Peris Arago");
        listadoOrigen.add("Alfauir");
        listadoOrigen.add("Alginet");
        listadoOrigen.add("Almassera");
        listadoOrigen.add("Amistat");
        listadoOrigen.add("Aragon");
        listadoOrigen.add("Ausias March");
        listadoOrigen.add("Av. del Cid");
        listadoOrigen.add("Ayora");
        listadoOrigen.add("A. Guimera");
        listadoOrigen.add("Bailen");
        listadoOrigen.add("Betera");
        listadoOrigen.add("Benaguasil 1r");
        listadoOrigen.add("Benaguasil 2n");
        listadoOrigen.add("Benicalap");
        listadoOrigen.add("Beniferri");
        listadoOrigen.add("Benimaclet");
        listadoOrigen.add("Benimamet");
        listadoOrigen.add("Benimodo");
        listadoOrigen.add("Burjassot");
        listadoOrigen.add("Burjassot-Godella");
        listadoOrigen.add("Campament");
        listadoOrigen.add("Campanar-La Fe");
        listadoOrigen.add("Campus");
        listadoOrigen.add("Cantereria");
        listadoOrigen.add("Carlet");
        listadoOrigen.add("Colon");
        listadoOrigen.add("Colálegi ElVedat");
        listadoOrigen.add("Dr. Lluch");
        listadoOrigen.add("El Clot");
        listadoOrigen.add("Empalme");
        listadoOrigen.add("Entrepins");
        listadoOrigen.add("Espioca");
        listadoOrigen.add("Estadi del Llevant");
        listadoOrigen.add("Eugenia Vi–el");
        listadoOrigen.add("Facultats");
        listadoOrigen.add("Faitanar");
        listadoOrigen.add("Fira Valencia");
        listadoOrigen.add("Florista");
        listadoOrigen.add("Foios");
        listadoOrigen.add("Font Almaguer");
        listadoOrigen.add("Francisco Cubells");
        listadoOrigen.add("Fuente del Jarro");
        listadoOrigen.add("Garbi");
        listadoOrigen.add("Godella");
        listadoOrigen.add("Grau-Canyamelar");
        listadoOrigen.add("Joaquin Sorolla");
        listadoOrigen.add("L'Alcudia");
        listadoOrigen.add("L'Eliana");
        listadoOrigen.add("La Cadena");
        listadoOrigen.add("La Canyada");
        listadoOrigen.add("La Carrasca");
        listadoOrigen.add("La Coma");
        listadoOrigen.add("La Granja");
        listadoOrigen.add("La Marina");
        listadoOrigen.add("La Pobla de Farnals");
        listadoOrigen.add("La Pobla de Vallbona");
        listadoOrigen.add("La Vallesa");
        listadoOrigen.add("Les Arenes");
        listadoOrigen.add("Les Carolines-Fira");
        listadoOrigen.add("Ll. Llarga Terramelar");
        listadoOrigen.add("Lliria");
        listadoOrigen.add("Machado");
        listadoOrigen.add("Manises");
        listadoOrigen.add("Maritim-Serreria");
        listadoOrigen.add("Marxalenes");
        listadoOrigen.add("Mas Del Rosari");
        listadoOrigen.add("Masies");
        listadoOrigen.add("Massalaves");
        listadoOrigen.add("Massamagrell");
        listadoOrigen.add("Massarrojos");
        listadoOrigen.add("Mediterrani");
        listadoOrigen.add("Meliana");
        listadoOrigen.add("Mislata");
        listadoOrigen.add("Mislata-Almassil");
        listadoOrigen.add("Montcada Alfara");
        listadoOrigen.add("Montesol");
        listadoOrigen.add("Montortal");
        listadoOrigen.add("Museros");
        listadoOrigen.add("Neptu");
        listadoOrigen.add("Nou d'Octubre");
        listadoOrigen.add("Omet");
        listadoOrigen.add("Orriols");
        listadoOrigen.add("Paiporta");
        listadoOrigen.add("Palau de Congressos");
        listadoOrigen.add("Paterna");
        listadoOrigen.add("Patraix");
        listadoOrigen.add("Picanya");
        listadoOrigen.add("Picassent");
        listadoOrigen.add("Pl. Espanya");
        listadoOrigen.add("Pont de Fusta");
        listadoOrigen.add("Primat Reig");
        listadoOrigen.add("Quart de Poblet");
        listadoOrigen.add("Rafelbunyol");
        listadoOrigen.add("Realon");
        listadoOrigen.add("Reus");
        listadoOrigen.add("Rocafor");
        listadoOrigen.add("Rosas");
        listadoOrigen.add("S. Psiquiatric");
        listadoOrigen.add("Safranar");
        listadoOrigen.add("Sagunt");
        listadoOrigen.add("Salt de l'Aigua");
        listadoOrigen.add("Sant Isidre");
        listadoOrigen.add("Sant Joan");
        listadoOrigen.add("Sant Miquel dels Reis");
        listadoOrigen.add("Sant Ramon");
        listadoOrigen.add("Santa Gemma Parc Cientific UV");
        listadoOrigen.add("Santa Rita");
        listadoOrigen.add("Seminari-CEU");
        listadoOrigen.add("Serreria");
        listadoOrigen.add("Tarongers");
        listadoOrigen.add("Turia");
        listadoOrigen.add("Tomas Y Valiente");
        listadoOrigen.add("Torre del Virrey");
        listadoOrigen.add("Torrent");
        listadoOrigen.add("Torrent Avinguda");
        listadoOrigen.add("Tossal del Rei");
        listadoOrigen.add("Transits");
        listadoOrigen.add("TVV");
        listadoOrigen.add("Universitat Politecnica");
        listadoOrigen.add("V. Andre E.");
        listadoOrigen.add("V. Zaragoza");
        listadoOrigen.add("Valencia Sud");
        listadoOrigen.add("Villanueva de Castellon");
        listadoOrigen.add("Xativa");

        listadoDestino.add("Aeroport");
        listadoDestino.add("Alameda");
        listadoDestino.add("Albalat dels Sorells");
        listadoDestino.add("Alberic");
        listadoDestino.add("Alboraya-Palmaret");
        listadoDestino.add("Alboraya-Peris Arago");
        listadoDestino.add("Alfauir");
        listadoDestino.add("Alginet");
        listadoDestino.add("Almassera");
        listadoDestino.add("Amistat");
        listadoDestino.add("Aragon");
        listadoDestino.add("Ausias March");
        listadoDestino.add("Av. del Cid");
        listadoDestino.add("Ayora");
        listadoDestino.add("A. Guimera");
        listadoDestino.add("Bailen");
        listadoDestino.add("Betera");
        listadoDestino.add("Benaguasil 1r");
        listadoDestino.add("Benaguasil 2n");
        listadoDestino.add("Benicalap");
        listadoDestino.add("Beniferri");
        listadoDestino.add("Benimaclet");
        listadoDestino.add("Benimamet");
        listadoDestino.add("Benimodo");
        listadoDestino.add("Burjassot");
        listadoDestino.add("Burjassot-Godella");
        listadoDestino.add("Campament");
        listadoDestino.add("Campanar-La Fe");
        listadoDestino.add("Campus");
        listadoDestino.add("Cantereria");
        listadoDestino.add("Carlet");
        listadoDestino.add("Colon");
        listadoDestino.add("Colálegi ElVedat");
        listadoDestino.add("Dr. Lluch");
        listadoDestino.add("El Clot");
        listadoDestino.add("Empalme");
        listadoDestino.add("Entrepins");
        listadoDestino.add("Espioca");
        listadoDestino.add("Estadi del Llevant");
        listadoDestino.add("Eugenia Vi–el");
        listadoDestino.add("Facultats");
        listadoDestino.add("Faitanar");
        listadoDestino.add("Fira Valencia");
        listadoDestino.add("Florista");
        listadoDestino.add("Foios");
        listadoDestino.add("Font Almaguer");
        listadoDestino.add("Francisco Cubells");
        listadoDestino.add("Fuente del Jarro");
        listadoDestino.add("Garbi");
        listadoDestino.add("Godella");
        listadoDestino.add("Grau-Canyamelar");
        listadoDestino.add("Joaquin Sorolla");
        listadoDestino.add("L'Alcudia");
        listadoDestino.add("L'Eliana");
        listadoDestino.add("La Cadena");
        listadoDestino.add("La Canyada");
        listadoDestino.add("La Carrasca");
        listadoDestino.add("La Coma");
        listadoDestino.add("La Granja");
        listadoDestino.add("La Marina");
        listadoDestino.add("La Pobla de Farnals");
        listadoDestino.add("La Pobla de Vallbona");
        listadoDestino.add("La Vallesa");
        listadoDestino.add("Les Arenes");
        listadoDestino.add("Les Carolines-Fira");
        listadoDestino.add("Ll. Llarga Terramelar");
        listadoDestino.add("Lliria");
        listadoDestino.add("Machado");
        listadoDestino.add("Manises");
        listadoDestino.add("Maritim-Serreria");
        listadoDestino.add("Marxalenes");
        listadoDestino.add("Mas Del Rosari");
        listadoDestino.add("Masies");
        listadoDestino.add("Massalaves");
        listadoDestino.add("Massamagrell");
        listadoDestino.add("Massarrojos");
        listadoDestino.add("Mediterrani");
        listadoDestino.add("Meliana");
        listadoDestino.add("Mislata");
        listadoDestino.add("Mislata-Almassil");
        listadoDestino.add("Montcada Alfara");
        listadoDestino.add("Montesol");
        listadoDestino.add("Montortal");
        listadoDestino.add("Museros");
        listadoDestino.add("Neptu");
        listadoDestino.add("Nou d'Octubre");
        listadoDestino.add("Omet");
        listadoDestino.add("Orriols");
        listadoDestino.add("Paiporta");
        listadoDestino.add("Palau de Congressos");
        listadoDestino.add("Paterna");
        listadoDestino.add("Patraix");
        listadoDestino.add("Picanya");
        listadoDestino.add("Picassent");
        listadoDestino.add("Pl. Espanya");
        listadoDestino.add("Pont de Fusta");
        listadoDestino.add("Primat Reig");
        listadoDestino.add("Quart de Poblet");
        listadoDestino.add("Rafelbunyol");
        listadoDestino.add("Realon");
        listadoDestino.add("Reus");
        listadoDestino.add("Rocafor");
        listadoDestino.add("Rosas");
        listadoDestino.add("S. Psiquiatric");
        listadoDestino.add("Safranar");
        listadoDestino.add("Sagunt");
        listadoDestino.add("Salt de l'Aigua");
        listadoDestino.add("Sant Isidre");
        listadoDestino.add("Sant Joan");
        listadoDestino.add("Sant Miquel dels Reis");
        listadoDestino.add("Sant Ramon");
        listadoDestino.add("Santa Gemma Parc Cientific UV");
        listadoDestino.add("Santa Rita");
        listadoDestino.add("Seminari-CEU");
        listadoDestino.add("Serreria");
        listadoDestino.add("Tarongers");
        listadoDestino.add("Turia");
        listadoDestino.add("Tomas Y Valiente");
        listadoDestino.add("Torre del Virrey");
        listadoDestino.add("Torrent");
        listadoDestino.add("Torrent Avinguda");
        listadoDestino.add("Tossal del Rei");
        listadoDestino.add("Transits");
        listadoDestino.add("TVV");
        listadoDestino.add("Universitat Politecnica");
        listadoDestino.add("V. Andre E.");
        listadoDestino.add("V. Zaragoza");
        listadoDestino.add("Valencia Sud");
        listadoDestino.add("Villanueva de Castellon");
        listadoDestino.add("Xativa");
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = AEstrellaApp.getApplication().getMainFrame();
            aboutBox = new AEstrellaAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        AEstrellaApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        calcular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaOutput = new javax.swing.JTextArea();
        listadoOrigen = new java.awt.Choice();
        listadoDestino = new java.awt.Choice();
        mapa = new javax.swing.JLabel();
        botonMostrar = new javax.swing.JButton();
        panelS = new javax.swing.JScrollPane();
        botonOcultar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        distancia = new javax.swing.JLabel();
        tiempo = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(aestrella.AEstrellaApp.class).getContext().getResourceMap(AEstrellaView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        calcular.setText(resourceMap.getString("calcular.text")); // NOI18N
        calcular.setName("calcular"); // NOI18N
        calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaOutput.setColumns(20);
        jTextAreaOutput.setEditable(false);
        jTextAreaOutput.setRows(1);
        jTextAreaOutput.setTabSize(800000);
        jTextAreaOutput.setDoubleBuffered(true);
        jTextAreaOutput.setName("jTextAreaOutput"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaOutput);

        listadoOrigen.setName("listadoOrigen"); // NOI18N

        listadoDestino.setName("listadoDestino"); // NOI18N

        mapa.setIcon(resourceMap.getIcon("mapa.icon")); // NOI18N
        mapa.setText(resourceMap.getString("mapa.text")); // NOI18N
        mapa.setName("mapa"); // NOI18N
        mapa.setVisible(false);

        botonMostrar.setText(resourceMap.getString("botonMostrar.text")); // NOI18N
        botonMostrar.setName("botonMostrar"); // NOI18N
        botonMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMostrarActionPerformed(evt);
            }
        });

        panelS.setName("panelS"); // NOI18N
        panelS.setVisible(false);

        botonOcultar.setText(resourceMap.getString("botonOcultar.text")); // NOI18N
        botonOcultar.setName("botonOcultar"); // NOI18N
        botonOcultar.setVisible(false);
        botonOcultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOcultarActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        distancia.setText(resourceMap.getString("distancia.text")); // NOI18N
        distancia.setName("distancia"); // NOI18N

        tiempo.setText(resourceMap.getString("tiempo.text")); // NOI18N
        tiempo.setName("tiempo"); // NOI18N

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(29, 29, 29)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listadoOrigen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(42, 42, 42)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listadoDestino, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(53, 53, 53)
                        .add(calcular))
                    .add(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(botonMostrar)
                        .add(18, 18, 18)
                        .add(botonOcultar)
                        .add(61, 61, 61)
                        .add(mapa, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(panelS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 625, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(32, 32, 32)
                        .add(jLabel3)
                        .add(61, 61, 61)
                        .add(distancia)
                        .add(140, 140, 140)
                        .add(jLabel4)
                        .add(40, 40, 40)
                        .add(tiempo)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2)
                    .add(listadoOrigen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1)
                    .add(calcular)
                    .add(listadoDestino, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(distancia)
                    .add(jLabel4)
                    .add(tiempo))
                .add(37, 37, 37)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(mapa, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(botonMostrar)
                        .add(botonOcultar)))
                .add(18, 18, 18)
                .add(panelS, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(aestrella.AEstrellaApp.class).getContext().getActionMap(AEstrellaView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                    .add(statusPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(statusMessageLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 722, Short.MAX_VALUE)
                        .add(statusAnimationLabel)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 31, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel))
                .add(3, 3, 3))
            .add(statusPanelLayout.createSequentialGroup()
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents


private void calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularActionPerformed
    Astar.tiempo=0;
    Astar.distancia=0;
    Astar.estaciones="";
    String origen = listadoOrigen.getSelectedItem();
    String destino = listadoDestino.getSelectedItem();
    Astar.calcularAStar(origen, destino);
    jTextAreaOutput.setText(Astar.estaciones);
    tiempo.setText(Integer.toString(Astar.tiempo)+" minutos");
    distancia.setText(Double.toString(Astar.distancia)+" km.");
}//GEN-LAST:event_calcularActionPerformed

private void botonMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMostrarActionPerformed
    panelS.setVisible(true);
    mapa.setVisible(true);
    panelS.setViewportView(mapa);
    botonOcultar.setVisible(true);
    botonMostrar.setVisible(false);
    // NOI18N
    
}//GEN-LAST:event_botonMostrarActionPerformed

private void botonOcultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOcultarActionPerformed
    panelS.setVisible(false);
    botonMostrar.setVisible(true);
    botonOcultar.setVisible(false);
}//GEN-LAST:event_botonOcultarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonMostrar;
    private javax.swing.JButton botonOcultar;
    private javax.swing.JButton calcular;
    private javax.swing.JLabel distancia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextAreaOutput;
    private java.awt.Choice listadoDestino;
    private java.awt.Choice listadoOrigen;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel mapa;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane panelS;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JSeparator statusPanelSeparator;
    private javax.swing.JLabel tiempo;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    
}
