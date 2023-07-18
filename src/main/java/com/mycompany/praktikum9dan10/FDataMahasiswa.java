/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.praktikum9dan10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author nayandra
 */
public class FDataMahasiswa extends javax.swing.JFrame {
    public Connection conn;
    public Statement cn;
    String inputFormat = "MMM dd, yyyy";
    String outputFormat = "yyyy-MM-dd";
    DateFormat inputFormatter = new SimpleDateFormat(inputFormat);
    DateFormat outputFormatter = new SimpleDateFormat(outputFormat);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Creates new form FDataMahasiswa
     */
    public FDataMahasiswa() {
        initComponents();
        jenis.removeAllItems();
        jenis.addItem("Laki-laki");
        jenis.addItem("Perempuan");
        fakultas.removeAllItems();
        fakultas.addItem("TEKNOLOGI INFORMASI");
        fakultas.addItem("TEKNOLOGI INDUSTRI");
        prodi.removeAllItems();
        prodi.addItem("SISTEM INFORMASI");
        prodi.addItem("TEKNIK KOMPUTER");
        prodi.addItem("MATEMATIKA");
        prodi.addItem("DESAIN KOMUNIKASI VISUAL");
        prodi.addItem("TEKNIK INDUSTRI");
        prodi.addItem("MANAJEMEN REKAYASA");
        prodi.addItem("PERDANGANAN INTERNASIONAL");
        tampilData();
        editTmbl.setEnabled(false);
        hapusTmbl.setEnabled(false);
    }
    
    public void koneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/praktikum9_10","root", "");
            cn = conn.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"koneksi gagal..");
            System.out.println(e.getMessage());
        }
    }
    
    public void tampilData(){
        DefaultTableModel tabelnyo = new DefaultTableModel();
        tabelnyo.addColumn("NIM");
        tabelnyo.addColumn("NAMA");
        tabelnyo.addColumn("TEMPAT LAHIR");
        tabelnyo.addColumn("TANGGAL LAHIR");
        tabelnyo.addColumn("NO HP");
        tabelnyo.addColumn("ALAMAT");
        tabelnyo.addColumn("JENIS KELAMIN");
        tabelnyo.addColumn("NAMA ORTU");
        tabelnyo.addColumn("FAKULTAS");
        tabelnyo.addColumn("PROGRAM STUDI");
        
        try{
            koneksi();
            var sql = "SELECT * FROM mahasiswa";
            ResultSet rs = cn.executeQuery(sql);
            
            while (rs.next()) {  
                
                tabelnyo.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                });
            }
            jTable1.setModel(tabelnyo);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ada Kesalahan" + e.getMessage());
        }
    }
    
    public void bersih(){
        nim.setText("");
        nama.setText("");
        tempat.setText("");
        tanggal.setDate(null);
        nomor.setText("");
        alamat.setText("");
        jenis.setSelectedItem("");
        ortu.setText("");
        fakultas.setSelectedItem("");
        prodi.setSelectedItem("");
    }
    
    public void validasi(){
        try{
            koneksi();
            var sql = "SELECT * FROM mahasiswa WHERE nim = '" + nim.getText() + "'";
            ResultSet rs = cn.executeQuery(sql);
            if(rs.next()){
                nim.setText(rs.getString(1));
                nama.setText(rs.getString(2));
                tempat.setText(rs.getString(3));
                tanggal.setDateFormatString(rs.getString(4));
                nomor.setText(rs.getString(5));
                alamat.setText(rs.getString(6));
                jenis.setSelectedItem(rs.getString(7));
                ortu.setText(rs.getString(8));
                fakultas.setSelectedItem(rs.getString(9));
                prodi.setSelectedItem(rs.getString(10));
                
                editTmbl.setEnabled(true);
                simpanTmbl.setEnabled(false);
                
                try {
                    int kel = JOptionPane.showConfirmDialog(this, "NIM " + nim.getText() + " Sudah ada, mau diedit atau di hapus?", "INFORMASI", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(kel == JOptionPane.NO_OPTION) {
                        bersih();
                        editTmbl.setEnabled(true);
                        simpanTmbl.setEnabled(true);
                        nim.requestFocus();
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                }catch(Exception e) {
                    
                }
            }
        }catch(Exception e){
            
        }
    }
    
    public void cari(){
        var a = new String(cari.getText());
        if(a.equals("")){
            JOptionPane.showConfirmDialog(null, "Field masih kosong..", "INFORMASI", JOptionPane.INFORMATION_MESSAGE);
            cari.requestFocus();
        }else{
            var tabelnyo = new DefaultTableModel();
            tabelnyo.addColumn("NIM");
            tabelnyo.addColumn("NAMA");
            tabelnyo.addColumn("TEMPAT LAHIR");
            tabelnyo.addColumn("TANGGAL LAHIR");
            tabelnyo.addColumn("NO HP");
            tabelnyo.addColumn("ALAMAT");
            tabelnyo.addColumn("JENIS KELAMIN");
            tabelnyo.addColumn("NAMA ORTU");
            tabelnyo.addColumn("FAKULTAS");
            tabelnyo.addColumn("PROGRAM STUDI");
            
            try{
               koneksi();
               var sql = "SELECT * FROM mahasiswa WHERE nim LIKE '%" + cari.getText() + "%' or nama LIKE '%" + cari.getText() + "%'";
               ResultSet rs = cn.executeQuery(sql);
               while (rs.next()) {                      
                   tabelnyo.addRow(new Object[] {
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                   });
               }
               jTable1.setModel(tabelnyo);
           }catch(Exception e) {
               JOptionPane.showMessageDialog(null, "Data tidakditemukan..","INFORMASI",JOptionPane.INFORMATION_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cari = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        nim = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        tempat = new javax.swing.JTextField();
        tanggal = new com.toedter.calendar.JDateChooser();
        nomor = new javax.swing.JTextField();
        alamat = new javax.swing.JTextField();
        jenis = new javax.swing.JComboBox<>();
        ortu = new javax.swing.JTextField();
        fakultas = new javax.swing.JComboBox<>();
        prodi = new javax.swing.JComboBox<>();
        simpanTmbl = new javax.swing.JButton();
        editTmbl = new javax.swing.JButton();
        hapusTmbl = new javax.swing.JButton();
        keluarTmbl = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 3, 24)); // NOI18N
        jLabel1.setText("INPUT DATA MAHASISWA");

        jLabel2.setText("Cari Berdasarkan Nama/NIM Mahasiswa");

        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        jLabel3.setText("N I M mahasiswa");

        jLabel4.setText("Nama Mahasiswa");

        jLabel5.setText("Tempat Lahir Mahasiswa");

        jLabel6.setText("Tanggal Lahir Mahasiswa");

        jLabel7.setText("No Hp Mahasiswa");

        jLabel8.setText("Alamat Mahasiswa");

        jLabel9.setText("Jenis Kelamin");

        jLabel10.setText("Nama Ortu");

        jLabel11.setText("Fakultas");

        jLabel12.setText("Program Studi");

        nim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nimActionPerformed(evt);
            }
        });

        nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaActionPerformed(evt);
            }
        });

        tempat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempatActionPerformed(evt);
            }
        });

        nomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomorActionPerformed(evt);
            }
        });

        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ortu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ortuActionPerformed(evt);
            }
        });

        fakultas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        prodi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        simpanTmbl.setText("SIMPAN");
        simpanTmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanTmblActionPerformed(evt);
            }
        });

        editTmbl.setText("EDIT");
        editTmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTmblActionPerformed(evt);
            }
        });

        hapusTmbl.setText("HAPUS");
        hapusTmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusTmblActionPerformed(evt);
            }
        });

        keluarTmbl.setText("KELUAR");
        keluarTmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarTmblActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(simpanTmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editTmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusTmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keluarTmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nomor, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(alamat, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(jenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ortu, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(fakultas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(prodi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(26, 26, 26))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nama, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(tempat, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(nim)
                                            .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(83, 83, 83)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(140, 140, 140)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cari))))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tempat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(nomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ortu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(fakultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(prodi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanTmbl)
                    .addComponent(editTmbl)
                    .addComponent(hapusTmbl)
                    .addComponent(keluarTmbl))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_cariActionPerformed

    private void nimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nimActionPerformed
        // TODO add your handling code here:
        validasi();
    }//GEN-LAST:event_nimActionPerformed

    private void namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaActionPerformed

    private void tempatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempatActionPerformed

    private void nomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomorActionPerformed

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

    private void ortuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ortuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ortuActionPerformed

    private void simpanTmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanTmblActionPerformed
        // TODO add your handling code here:
        try{
            koneksi();
            Date date = inputFormatter.parse(((JTextField)tanggal.getDateEditor().getUiComponent()).getText());
            String formattedDate = outputFormatter.format(date);
            var sql = "INSERT INTO mahasiswa VALUES('"+ nim.getText() + "','" + nama.getText() + "','" + tempat.getText() + "','" + formattedDate + "','" + nomor.getText() + "','" + alamat.getText() + "','" + jenis.getSelectedItem() + "','" + ortu.getText() + "','" + fakultas.getSelectedItem() + "','" + prodi.getSelectedItem() + "')";
            cn.executeUpdate(sql);
            conn.close();
            tampilData();
            bersih();
            JOptionPane.showMessageDialog(null,"Data berhasil di simpan");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Proses penyimpanan gagal" + e);
        }
    }//GEN-LAST:event_simpanTmblActionPerformed

    private void editTmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTmblActionPerformed
        // TODO add your handling code here:
        try{
            koneksi();
            Date date = inputFormatter.parse(((JTextField)tanggal.getDateEditor().getUiComponent()).getText());
            String formattedDate = outputFormatter.format(date);
            var sql =  "update mahasiswa set nama='"+ nama.getText() +"',tempat_lahir='" + tempat.getText() + "', tanggal_lahir ='" + formattedDate + "', no_hp ='" + nomor.getText() + "', alamat='" + alamat.getText() + "', jenis_kelamin ='" + jenis.getSelectedItem() +"', nama_ortu='" + ortu.getText() + "', fakultas = '" + fakultas.getSelectedItem() + "', prodi = '" + prodi.getSelectedItem() +"' where nim='" + nim.getText() + "'";
            cn.executeUpdate(sql);
            cn.close();
            tampilData();
            bersih();
            nim.setEnabled(true);
            simpanTmbl.setEnabled(true);
            editTmbl.setEnabled(false);
            hapusTmbl.setEnabled(false);
            nim.requestFocus();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_editTmblActionPerformed

    private void hapusTmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusTmblActionPerformed
        // TODO add your handling code here:
        try{
            getToolkit().beep();
            int keluar = JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Meghapus Ini..?","PERINGATAN",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(keluar == JOptionPane.YES_OPTION){
                try{
                    koneksi();
                    var sql = "DELETE FROM mahasiswa WHERE nim = '" + nim.getText() + "'";
                    cn.executeUpdate(sql);
                    cn.close();
                    tampilData();
                    bersih();
                    nim.setEnabled(true);
                    simpanTmbl.setEnabled(true);
                    editTmbl.setEnabled(false);
                    hapusTmbl.setEnabled(false);
                    nim.requestFocus();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Deleting failed..");
                }
            }
        }catch(Exception e){
            
            
        }
    }//GEN-LAST:event_hapusTmblActionPerformed

    private void keluarTmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarTmblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keluarTmblActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        editTmbl.setEnabled(true);
        hapusTmbl.setEnabled(true);
        int tabel = jTable1.getSelectedRow();
        var a = jTable1.getValueAt(tabel, 0).toString() ;
        var b = jTable1.getValueAt(tabel, 1).toString() ;
        var c = jTable1.getValueAt(tabel, 2).toString() ;
        var d = jTable1.getValueAt(tabel, 3).toString() ;
        var e = jTable1.getValueAt(tabel, 4).toString() ;
        var f = jTable1.getValueAt(tabel, 5).toString() ;
        var g = jTable1.getValueAt(tabel, 6).toString() ;
        var h = jTable1.getValueAt(tabel, 7).toString() ;
        var i = jTable1.getValueAt(tabel, 8).toString() ;
        var j = jTable1.getValueAt(tabel, 9).toString() ;
        
        nim.setText(a);
        nama.setText(b);
        tempat.setText(c);
        try{
            tanggal.setDate(dateFormat.parse(d));
        }catch(Exception er){
            System.out.println(er.getMessage());
        }
        nomor.setText(e);
        alamat.setText(f);
        jenis.setSelectedItem(g);
        ortu.setText(h);
        fakultas.setSelectedItem(i);
        prodi.setSelectedItem(j);
        
        
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(FDataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataMahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamat;
    private javax.swing.JTextField cari;
    private javax.swing.JButton editTmbl;
    private javax.swing.JComboBox<String> fakultas;
    private javax.swing.JButton hapusTmbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> jenis;
    private javax.swing.JButton keluarTmbl;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nim;
    private javax.swing.JTextField nomor;
    private javax.swing.JTextField ortu;
    private javax.swing.JComboBox<String> prodi;
    private javax.swing.JButton simpanTmbl;
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JTextField tempat;
    // End of variables declaration//GEN-END:variables
}
