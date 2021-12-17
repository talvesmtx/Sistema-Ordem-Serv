/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prjtkca.telas;

import br.com.prjtkca.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author talve
 */
public class TelaClientes extends javax.swing.JFrame {

    Connection conexao = null;
    private PreparedStatement pst;
    private ResultSet rs;

    /**
     * Creates new form TelaClientes
     */
    public TelaClientes() {
        initComponents();
        conexao = ModuloConexao.conector();

        btnSalvar.setVisible(false);
        btnVoltar.setVisible(false);
        txtId.setVisible(false);
    }

    private void cadastrarClientes() {
        var cpf = txtCpf.getText();
        var nomeCliente = txtNomecliente.getText();
        var endereco = txtEnderecoCliente.getText();
        var telefone = txtTelefoneCliente.getText();
        var email = txtEmailCliente.getText();

        if (cpf.isEmpty() || nomeCliente.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        String sql = "insert into ktcaclientes (nomecli,endcli,cpfcli,fonecli,emailcli) values ('%s','%s','%s','%s','%s');";
        sql = sql.formatted(nomeCliente, endereco, cpf, telefone, email);
        try {
            pst = conexao.prepareStatement(sql);
            pst.execute();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
            limpaCampos();
            controlaBotoes("Voltar");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu inserir: " + e);
        }
    }

    private void atualizar() {
        var id = txtId.getText();
        var cpf = txtCpf.getText();
        var nomeCliente = txtNomecliente.getText();
        var endereco = txtEnderecoCliente.getText();
        var telefone = txtTelefoneCliente.getText();
        var email = txtEmailCliente.getText();

        if (cpf.isEmpty() || nomeCliente.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        String sql = "update ktcaclientes SET nomecli=?, endcli=?, cpfcli=?, fonecli=?, emailcli=? where idcli=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeCliente);
            pst.setString(2, endereco);
            pst.setString(3, cpf);
            pst.setString(4, telefone);
            pst.setString(5, email);
            pst.setString(6, id);

            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            limpaCampos();
//            controlaBotoes("Voltar");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu atualizados: " + e);
        }
    }

    private void excluir() {
        String sql = "delete from ktcaclientes where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtId.getText());
            pst.execute();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
            limpaCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu excluir: " + e);
        }
    }

    private void pesquisarClientes() {
        String sql = "select idcli as Id, nomecli as Nome, endcli as Endereco, cpfcli as Cpf, fonecli as Fone, emailcli as Email from ktcaclientes where cpfcli=?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCpf.getText().toUpperCase());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtId.setText(rs.getString(1));
                txtNomecliente.setText(rs.getString(2));
                txtEnderecoCliente.setText(rs.getString(3));
                txtTelefoneCliente.setText(rs.getString(5));
                txtEmailCliente.setText(rs.getString(6));

            } else {
                limpaCampos();
                JOptionPane.showMessageDialog(null, "Dados não encontrados!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conectou no banco: " + e);
        }
    }

    private void controlaBotoes(String nomeBotao) {
        switch (nomeBotao) {
            case "Cadastro":

                btnPesquisar.setEnabled(false);

                btnCadastrar.setVisible(false);
                btnAlteracao.setVisible(false);
                btnExcluir.setVisible(false);

                btnSalvar.setVisible(true);
                btnVoltar.setVisible(true);

                limpaCampos();

                break;
            case "Alteracao":

                break;
            case "Consultar":

                break;
            case "Voltar":
                btnPesquisar.setEnabled(true);

                btnCadastrar.setVisible(true);
                btnAlteracao.setVisible(true);
                btnExcluir.setVisible(true);

                btnSalvar.setVisible(false);
                btnVoltar.setVisible(false);

                limpaCampos();

                break;

            default:
                System.out.println("......");
                break;
        }
    }

    private void limpaCampos() {
        txtId.setText("");
        txtCpf.setText("");
        txtNomecliente.setText("");
        txtEnderecoCliente.setText("");
        txtCpf.setText("");
        txtTelefoneCliente.setText("");
        txtEmailCliente.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTelefoneCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        txtNomecliente = new javax.swing.JTextField();
        btnAlteracao = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        txtEnderecoCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        txtId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setText("* Campos obrigatórios");

        jLabel4.setText("* Telefone");

        jLabel5.setText("Email");

        txtTelefoneCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneClienteActionPerformed(evt);
            }
        });

        btnCadastrar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jLabel1.setText("* Nome");

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        txtNomecliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeclienteActionPerformed(evt);
            }
        });

        btnAlteracao.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnAlteracao.setText("Alterar");
        btnAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteracaoActionPerformed(evt);
            }
        });

        jLabel2.setText("Endereço");

        btnExcluir.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        txtEnderecoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoClienteActionPerformed(evt);
            }
        });

        jLabel7.setText("Cpf");

        txtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        txtId.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomecliente, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                            .addComponent(txtEnderecoCliente)
                            .addComponent(txtEmailCliente)
                            .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnCadastrar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAlteracao)))
                                .addGap(18, 18, 18)
                                .addComponent(btnExcluir)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPesquisar)
                        .addComponent(jLabel7)
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtId))
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnAlteracao)
                    .addComponent(btnExcluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar)
                    .addComponent(btnVoltar))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefoneClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneClienteActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        pesquisarClientes();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtNomeclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeclienteActionPerformed

    private void txtEnderecoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoClienteActionPerformed

    private void txtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        limpaCampos();
        controlaBotoes("Cadastro");
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        cadastrarClientes();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        controlaBotoes("Voltar");
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteracaoActionPerformed
        atualizar();
    }//GEN-LAST:event_btnAlteracaoActionPerformed

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
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlteracao;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtEmailCliente;
    public static javax.swing.JTextField txtEnderecoCliente;
    private javax.swing.JLabel txtId;
    public static javax.swing.JTextField txtNomecliente;
    private javax.swing.JTextField txtTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
