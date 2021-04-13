/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package milionerzy;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Karol
 */
public class Panel extends javax.swing.JFrame {

    /**
     * Creates new form Panel
     */
    int number;
    List<String> lista = new LinkedList<String>();
     List<Integer> listu = new ArrayList<Integer>();
    Statement statement;
    Random generator = new Random();
    
    Boolean a2 = false;
    Boolean a3 = false;
    Boolean a4 = false;
    Boolean a5 = false;
    Boolean polnapol = false;
    
    Boolean gameover = false;
    
    
    public void init() throws ClassNotFoundException, SQLException
    {
        number = -1;
        for (int i=1; i<=15; i++)
        {
            lista.add("Picture"+i+".png");
        }
              
        Class.forName("org.postgresql.Driver");
        Properties p = new Properties();
        p.setProperty("user", "postgres");
        p.setProperty("password", "1234");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/milionerzy", p);
        if(conn==null)
        {
            System.out.println("Error!");
        }
        this.statement = conn.createStatement(); 
        
    }
    public Panel() throws ClassNotFoundException, SQLException {
        initComponents();
        init();
        
    }
    
    public int Generuj()
    {
       int ile = 0;
       while(ile == 0){
                ile = generator.nextInt(17);
            }
       return ile;
    }
    
    public String GetQuestion()
    {   
            int ile = Generuj();
            
                if(listu.isEmpty())
                {
                  listu.add(ile);
                }  
            
            
             for(int i=0;i<listu.size()-1;i++)
            { 
                if(ile == listu.get(i))
                {
                    ile = Generuj();
                    i=0;
                }
            }
            
            listu.add(ile);
                        
            try {
            ResultSet rs = statement.executeQuery(String.format("SELECT pytanie FROM pytania WHERE pytania.id_pytanie = '%s'",ile));
            
            while(rs.next()){
                String x = rs.getString("pytanie");
                return x;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<String> GetAnswer(String pytanie)
    {       String x = null;
    
            List<String> listax = new LinkedList<String>();
            try {
            ResultSet rs = statement.executeQuery(String.format("SELECT id_pytanie FROM pytania WHERE pytania.pytanie = '%s'",pytanie));           
            while(rs.next()){
                 x = rs.getString("id_pytanie");
            }
            
            ResultSet ra = statement.executeQuery(String.format("SELECT odpowiedz FROM pytania,odpowiedzi WHERE pytania.id_pytanie = '%s' and odpowiedzi.idpytanie = pytania.id_pytanie;",x));           
            while(ra.next()){
            listax.add(ra.getString("odpowiedz"));
            }
            return listax;
            
        } catch (SQLException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public String GetRightAnswer(String pytanie,String odpowiedz)
    {       
            System.out.println(pytanie);
            System.out.println(odpowiedz);
            String idpyt = null;
            try {
            ResultSet rs = statement.executeQuery(String.format("SELECT id_pytanie FROM pytania WHERE pytania.pytanie = '%s'",pytanie));           
            while(rs.next()){
                 idpyt = rs.getString("id_pytanie");
                  System.out.println(idpyt);
            }
            
            ResultSet ra = statement.executeQuery(String.format("SELECT poprawna FROM odpowiedzi,pytania WHERE odpowiedzi.odpowiedz= '%s' AND pytania.id_pytanie = '%s' AND odpowiedzi.idpytanie = pytania.id_pytanie;",odpowiedz,idpyt));           
            while(ra.next()){
                String wynik = ra.getString("poprawna");
                System.out.println(wynik);
                return wynik;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
     
     public List<String> Shuffle(List<String> lista)
     {
         int zmiana1 = 0;
         int zmiana2 = 0;
         
            while(zmiana1 == zmiana2){
                zmiana1 = generator.nextInt(4);
                zmiana2 = generator.nextInt(4);
            }
            
            String aaa = lista.get(zmiana1);
            String bbb = lista.get(zmiana2);
 
         lista.set(zmiana2, aaa);       
         lista.set(zmiana1, bbb);
         
         return lista;
         
     }
     
    public void Reset()
    {
      a2 = false;
      a3 = false;
      a4 = false;
      a5 = false;
      jTextField4.setBackground(Color.black);
      jTextField4.setForeground(Color.white);
      jTextField5.setBackground(Color.black);
      jTextField5.setForeground(Color.white);
      jTextField3.setBackground(Color.black);
      jTextField3.setForeground(Color.white);
      jTextField2.setBackground(Color.black);
      jTextField2.setForeground(Color.white);  
    }
    
    public void RestertGry()
    {
    number=-1;
    Reset();
    List<String> listaodp = new LinkedList<String>();
    String x = GetQuestion();      
    jTextField1.setText(x);
    listaodp = GetAnswer(x);
    listaodp = Shuffle(listaodp);
    jTextField2.setText(listaodp.get(0));
    jTextField3.setText(listaodp.get(1));
    jTextField4.setText(listaodp.get(2));
    jTextField5.setText(listaodp.get(3));  
    jLabel3.setIcon(new ImageIcon("images/Picture01.png"));   
    jTextField6.setText("POPRAW");
    gameover = false;    
    listu.clear();
    polnapol = false;
    jButton2.setIcon(new ImageIcon("images/jpge50.jpg"));  
    }
    
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Milionerzy");
        setPreferredSize(new java.awt.Dimension(1200, 1000));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milionerzy/images/imagesCAG5P2IF.jpg"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milionerzy/images/Picture01.png"))); // NOI18N

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(0, 0, 0));
        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setBackground(new java.awt.Color(0, 0, 0));
        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setBackground(new java.awt.Color(0, 0, 0));
        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.setBackground(new java.awt.Color(0, 0, 0));
        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milionerzy/images/HUbert.jpg"))); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jTextField6.setBackground(new java.awt.Color(0, 0, 0));
        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("POPRAW");
        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 4));
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milionerzy/images/jpge50.jpg"))); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(148, 148, 148)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        this.getContentPane().setBackground(Color.black);
    }//GEN-LAST:event_formMouseMoved

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        List<String> listaodp = new LinkedList<String>();
        String x = GetQuestion();      
        jTextField1.setText(x);
        listaodp = GetAnswer(x);
        listaodp = Shuffle(listaodp);
        jTextField2.setText(listaodp.get(0));
        jTextField3.setText(listaodp.get(1));
        jTextField4.setText(listaodp.get(2));
        jTextField5.setText(listaodp.get(3));
    }//GEN-LAST:event_formWindowActivated

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        List<ImageIcon> listai = new LinkedList<ImageIcon>();
        String odp = null;
        
        if(a2 == true)
        {
            odp = jTextField2.getText();
        }
        else if(a3 == true)
        {
            odp = jTextField3.getText();
        }
        else if(a4 == true)
        {
            odp = jTextField4.getText();
        }
        else if(a5 == true)
        {
            odp = jTextField5.getText();
        }
        
        String pytanie = jTextField1.getText();        
        String wynik = GetRightAnswer(pytanie,odp);
        
        if("1".equals(wynik))
        {    
        Reset();  
        number ++;
        List<String> listaodp = new LinkedList<String>();
        String x = GetQuestion();      
        jTextField1.setText(x);
        listaodp = GetAnswer(x);
        listaodp = Shuffle(listaodp);
        jTextField2.setText(listaodp.get(0));
        jTextField3.setText(listaodp.get(1));
        jTextField4.setText(listaodp.get(2));
        jTextField5.setText(listaodp.get(3));         
          
        for(int i =0;i<lista.size();i++)
        {
                listai.add(new ImageIcon("images/" + lista.get(i)));
        }
        
        if(number < listai.size())
        {
           jLabel3.setIcon(listai.get(number)); 
        }
        else
        {
            jTextField6.setText("RESET");
            gameover=true;
            jTextField1.setText("Wygrales 1mln !!!!");
            jTextField2.setText("Gratulacje");
            jTextField3.setText("Gratulacje");
            jTextField4.setText("Gratulacje");
            jTextField5.setText("Gratulacje");
            jTextField4.setBackground(Color.black);
            jTextField4.setForeground(Color.white);
            jTextField5.setBackground(Color.black);
            jTextField5.setForeground(Color.white);
            jTextField3.setBackground(Color.black);
            jTextField3.setForeground(Color.white);
            jTextField2.setBackground(Color.black);
            jTextField2.setForeground(Color.white); 
        }
        }
        else
        {
            jTextField6.setText("RESET");
            gameover = true;
            jTextField1.setText("Przegrałeś");  
            jTextField2.setText(" ");
            jTextField3.setText(" ");
            jTextField4.setText(" ");
            jTextField5.setText(" ");
            jTextField4.setBackground(Color.black);
            jTextField4.setForeground(Color.white);
            jTextField5.setBackground(Color.black);
            jTextField5.setForeground(Color.white);
            jTextField3.setBackground(Color.black);
            jTextField3.setForeground(Color.white);
            jTextField2.setBackground(Color.black);
            jTextField2.setForeground(Color.white); 
        }
        
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
         if(a2 == false && a3 == false && a5 == false)
         {
             a4 = true;
             jTextField4.setBackground(Color.blue);
             jTextField4.setForeground(Color.black);
         }
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseClicked
         if(a2 == false && a3 == false && a4 == false)
         {
             a5 = true;
             jTextField5.setBackground(Color.blue);
             jTextField5.setForeground(Color.black);
         }
    }//GEN-LAST:event_jTextField5MouseClicked

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
        if(a2 == false && a4 == false && a5 == false)
         {
             a3 = true;
             jTextField3.setBackground(Color.blue);
             jTextField3.setForeground(Color.black);
         }
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        if(a4 == false && a3 == false && a5 == false)
         {
             a2 = true;
             jTextField2.setBackground(Color.blue);
             jTextField2.setForeground(Color.black);
         }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
    if (gameover == false)  
    {
      a2 = false;
      a3 = false;
      a4 = false;
      a5 = false;
      jTextField4.setBackground(Color.black);
      jTextField4.setForeground(Color.white);
      jTextField5.setBackground(Color.black);
      jTextField5.setForeground(Color.white);
      jTextField3.setBackground(Color.black);
      jTextField3.setForeground(Color.white);
      jTextField2.setBackground(Color.black);
      jTextField2.setForeground(Color.white);
    }
    else
    {
     RestertGry();    
    }
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
       if(polnapol == false)
       {
       polnapol = true;
       jButton2.setIcon(new ImageIcon("images/jpge50X.jpg"));   
       String pytanie = jTextField1.getText();           
       String wynik = GetRightAnswer(pytanie,jTextField2.getText());
       String wynik1 = GetRightAnswer(pytanie,jTextField3.getText());
       String wynik2 = GetRightAnswer(pytanie,jTextField4.getText());
       String wynik3 = GetRightAnswer(pytanie,jTextField5.getText());
       
       if("1".equals(wynik))
       {
          jTextField3.setText(" ");
          jTextField5.setText(" ");        
       }
        if("1".equals(wynik1))
       {
          jTextField2.setText(" ");
          jTextField5.setText(" ");        
       }
        if("1".equals(wynik2))
       {
          jTextField3.setText(" ");
          jTextField2.setText(" ");        
       }
        if("1".equals(wynik3))
       {
          jTextField3.setText(" ");
          jTextField4.setText(" ");        
       }     
       }   
    }//GEN-LAST:event_jButton2MouseClicked

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
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Panel().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
