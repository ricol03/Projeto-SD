package projetofinal;

import java.io.File;
import javax.swing.JOptionPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */
public class InterfacePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form InterfacePrincipal
     */
    boolean Disconnect = false;
    int id = generateId();
    Ligacao conn = null;
    Logs logs = null;

    public InterfacePrincipal() {
        initComponents();
        disableMainSection();
    }

    private void disableMainSection() {
        Download_Button.setEnabled(false);
        User_List.setEnabled(false);
        File_List.setEnabled(false);
        File_Label.setEnabled(false);
        User_Label.setEnabled(false);
        
        Folder_Button.setEnabled(true);
        Folder_Label2.setEnabled(true);
        Folder_Label.setEnabled(true);
        IP_Label.setEnabled(true);
        IP_Field.setEnabled(true);
        Port_Label.setEnabled(true);
        Port_Field.setEnabled(true);
        Name_Label.setEnabled(true);
        Name_Field.setEnabled(true);

        Connection_Button.setText("Conectar");
        Disconnect = false;
    }

    private void enableMainSection() {
        Download_Button.setEnabled(false);
        User_List.setEnabled(true);
        File_List.setEnabled(true);
        File_Label.setEnabled(true);
        User_Label.setEnabled(true);

        Folder_Button.setEnabled(false);
        Folder_Label2.setEnabled(false);
        Folder_Label.setEnabled(false);
        IP_Label.setEnabled(false);
        IP_Field.setEnabled(false);
        Port_Label.setEnabled(false);
        Port_Field.setEnabled(false);
        Name_Label.setEnabled(false);
        Name_Field.setEnabled(false);

        Connection_Button.setText("Desconectar");
        Disconnect = true;
    }

    private void checkFolderSelection() {
        String folderPath = "";
        // Using this process to invoke the constructor,
        // JFileChooser points to user's default directory
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.setDialogTitle("Select Folder");

        int result = j.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File pasta = j.getSelectedFile();
            folderPath = pasta.getAbsolutePath();
            System.out.println("Pasta guardada: " + folderPath);
            Folder_Label.setText(folderPath);
        }

        String texto = Folder_Label.getText();
        if (!texto.equals("Sem pasta selecionada...")) {
            Download_Button.setEnabled(true);
        }
        
        fileList(folderPath);
    }
    
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public void fileList(String folderPath) {
        listModel.clear(); // limpa antes de adicionar novos
        File_List.setModel(listModel); // garante que está ligado

        File directory = new File(folderPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("Arquivo: " + file.getName());
                    listModel.addElement(file.getName()); // ADICIONA AO MODELO!
                }
            }
        }
    }  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        File_List = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        User_List = new javax.swing.JList<>();
        Download_Button = new javax.swing.JButton();
        IP_Field = new javax.swing.JTextField();
        Port_Field = new javax.swing.JTextField();
        IP_Label = new javax.swing.JLabel();
        Port_Label = new javax.swing.JLabel();
        Name_Field = new javax.swing.JTextField();
        Name_Label = new javax.swing.JLabel();
        Folder_Label2 = new javax.swing.JLabel();
        Folder_Label = new javax.swing.JLabel();
        File_Label = new javax.swing.JLabel();
        User_Label = new javax.swing.JLabel();
        Connection_Button = new javax.swing.JButton();
        Folder_Button = new javax.swing.JButton();
        Logs_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(File_List);

        jScrollPane2.setViewportView(User_List);

        Download_Button.setText("Baixar");
        Download_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Download_ButtonActionPerformed(evt);
            }
        });

        IP_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IP_FieldActionPerformed(evt);
            }
        });

        Port_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Port_FieldActionPerformed(evt);
            }
        });

        IP_Label.setText("IP:");

        Port_Label.setText("Porto:");

        Name_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Name_FieldActionPerformed(evt);
            }
        });

        Name_Label.setText("Nome:");

        Folder_Label2.setText("Pasta a Partilhar:");

        Folder_Label.setText("Sem pasta selecionada...");

        File_Label.setText("Ficheiros:");

        User_Label.setText("Utilizadores:");

        Connection_Button.setText("Conectar");
        Connection_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Connection_ButtonActionPerformed(evt);
            }
        });

        Folder_Button.setText("Selecionar Pasta");
        Folder_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Folder_ButtonActionPerformed(evt);
            }
        });

        Logs_Button.setText("Logs");
        Logs_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Logs_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Name_Label)
                            .addComponent(Folder_Label2)
                            .addComponent(Folder_Label)
                            .addComponent(User_Label)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(Connection_Button))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IP_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IP_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Port_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Port_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Folder_Button)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(Logs_Button)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(File_Label)
                        .addGap(178, 178, 178))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Download_Button)
                        .addGap(84, 84, 84))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IP_Label)
                    .addComponent(File_Label)
                    .addComponent(Port_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IP_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Port_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Name_Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Connection_Button))
                        .addGap(28, 28, 28)
                        .addComponent(Folder_Label2)
                        .addGap(12, 12, 12)
                        .addComponent(Folder_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Folder_Label)
                        .addGap(29, 29, 29)
                        .addComponent(User_Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Download_Button)
                    .addComponent(Logs_Button))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void Port_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Port_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Port_FieldActionPerformed

    private void Name_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Name_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Name_FieldActionPerformed

    private void Download_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Download_ButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Download_ButtonActionPerformed

    private void Connection_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Connection_ButtonActionPerformed
        // TODO add your handling code here:
        String ip = IP_Field.getText();
        String name = Name_Field.getText();
        String port = Port_Field.getText();
        String folder = Folder_Label.getText();

        try {
            if (Disconnect) {
                logout(id);
                disableMainSection();
                Folder_Label.setText("Sem pasta selecionada...");
                listModel.clear();
            } else if (ip.isEmpty() || port.isEmpty() || name.isEmpty() || folder.equals("Sem pasta selecionada...")) {
                JOptionPane.showMessageDialog(this, "Nome, IP, Porto e Pasta não podem estar vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                conn = new Ligacao(ip, port, id);
                sendClientInfo(name, id, folder);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Connection_ButtonActionPerformed

    private void IP_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IP_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IP_FieldActionPerformed

    private void Folder_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Folder_ButtonActionPerformed
        // TODO add your handling code here:
        checkFolderSelection();
    }//GEN-LAST:event_Folder_ButtonActionPerformed

    private void Logs_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Logs_ButtonActionPerformed
        // TODO add your handling code here:
        logs.setVisible(true);
    }//GEN-LAST:event_Logs_ButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfacePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfacePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfacePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfacePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfacePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Connection_Button;
    private javax.swing.JButton Download_Button;
    private javax.swing.JLabel File_Label;
    private javax.swing.JList<String> File_List;
    private javax.swing.JButton Folder_Button;
    private javax.swing.JLabel Folder_Label;
    private javax.swing.JLabel Folder_Label2;
    private javax.swing.JTextField IP_Field;
    private javax.swing.JLabel IP_Label;
    private javax.swing.JButton Logs_Button;
    private javax.swing.JTextField Name_Field;
    private javax.swing.JLabel Name_Label;
    private javax.swing.JTextField Port_Field;
    private javax.swing.JLabel Port_Label;
    private javax.swing.JLabel User_Label;
    private javax.swing.JList<String> User_List;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private int generateId() {
        int id = 0;
        Random generator = new Random();
        id = generator.nextInt(1000)+1;
        return id;
    }

    private void logout(int id) {
        Client client = ClientBuilder.newClient();
        
        String URLBuilder = "http://" + conn.getIp() + ":" + 
                conn.getPort() + "/ProjetoFinalServidor/app/api/ads";
        
        Response answer = client.target(URLBuilder + "/" + id)
            .request()
            .delete();
        
        if (answer.getStatus() == 204) {
            JOptionPane.showMessageDialog(null, "Logout efetuado com sucesso", "Informação", JOptionPane.INFORMATION_MESSAGE );
        } else {
            String hello = answer.readEntity(String.class);
            JOptionPane.showMessageDialog(null, hello, "Erro", JOptionPane.INFORMATION_MESSAGE );

        }
    }
    
    private void listUsers() {
        Client client = ClientBuilder.newClient();
        
        String URLBuilder = "http://" + conn.getIp() + ":" + conn.getPort() + "/ProjetoFinalServidor/app/api/ads";
        
        Response answer = client.target(URLBuilder)
                            .request()
                            .accept("application/json")
                            .get();
        
        if (answer.getStatus() == 200) {
            
        }
    }
    
    private void sendClientInfo(String name, int id, String folder) {
        try {
            Client client = ClientBuilder.newClient();
            
            User user = new User(name, String.valueOf(id), folder, null);
            
            String URLBuilder = "http://" + conn.getIp() + ":" + conn.getPort() + "/ProjetoFinalServidor/app/api/ads";
            
            Response answer = client.target(URLBuilder)
                            .request()
                            .accept("application/json")
                            .post(Entity.json(user));
            
            if (answer.getStatus() == 201) {
                JOptionPane.showMessageDialog(null, "Iniciada sessão com sucesso", "Informação", JOptionPane.INFORMATION_MESSAGE);
                enableMainSection();
                logs = new Logs();
            } else {
                String hello = answer.readEntity(String.class);
                
                System.out.println(hello);
                JOptionPane.showMessageDialog(null, hello, "Erro", JOptionPane.OK_OPTION);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No info sent to server: " + e.getMessage());
        }
    }
    
    // criar uma thread que chama os endpoints listUsers listFiles logout e login

}
