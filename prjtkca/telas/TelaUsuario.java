/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prjtkca.telas;

/**
 *
 * @author talve
 */
import java.sql.*;
import br.com.prjtkca.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    // Variavel conexao do dal 
    Connection conexao = null;
    private PreparedStatement pst;
    private ResultSet rs;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();

        jButtonSalvar.setVisible(false);
        jButtonVoltar.setVisible(false);

    }

    private void limpaCampos() {
        txtUsuId.setText("");
        txtUsul.setText("");
        txtPerfil.setText("");
        txtCpf.setText("");
        txtFone.setText("");
        txtLogin.setText("");
        txtSenha.setText("");

    }

    private void consultarPorId() {
        String sql = "select * from ktcausuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsul.setText(rs.getString(2));
                txtPerfil.setText(rs.getString(7));
                txtCpf.setText(rs.getString(4));
                txtFone.setText(rs.getString(3));
                txtLogin.setText(rs.getString(5));
                txtSenha.setText(rs.getString(6));

            } else {
                limpaCampos();
                JOptionPane.showMessageDialog(null, "Dados não encontrados!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conectou no banco: " + e);
        }
    }

    private void excluir() {
        String sql = "delete from ktcausuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.execute();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
            limpaCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu excluir: " + e);
        }
    }

    private void atualizar() {
        var usuario = txtUsul.getText();
        var perfil = txtPerfil.getText();
        var cpf = txtCpf.getText();
        var fone = txtFone.getText();
        var login = txtLogin.getText();
        var senha = txtSenha.getText();

        if (usuario.isEmpty() || perfil.isEmpty() || cpf.isEmpty() || fone.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        String sql = "update ktcausuarios SET fone=?, cpf=? where iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, fone);
            pst.setString(2, cpf);
            pst.setString(3, txtUsuId.getText());

            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            limpaCampos();
//            controlaBotoes("Voltar");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu atualizados: " + e);
        }
    }

    private void salvar() {
        var usuario = txtUsul.getText();
        var perfil = txtPerfil.getText();
        var cpf = txtCpf.getText();
        var fone = txtFone.getText();
        var login = txtLogin.getText();
        var senha = txtSenha.getText();

        if (usuario.isEmpty() || perfil.isEmpty() || cpf.isEmpty() || fone.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        String sql = "insert into ktcausuarios (usuario,fone,cpf,login,senha,perfil) values ('%s','%s','%s','%s','%s','%s');";
        sql = sql.formatted(usuario, fone, cpf, login, senha, perfil);
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

    private void controlaBotoes(String nomeBotao) {
        switch (nomeBotao) {
            case "Cadastro":
                txtUsuId.setEnabled(false);

                jButtonCadastro.setVisible(false);
                jButtonAlteracao.setVisible(false);
                jButtonConsultar.setVisible(false);
                jButtonExcluir.setVisible(false);

                jButtonSalvar.setVisible(true);
                jButtonVoltar.setVisible(true);

                break;
            case "Alteracao":

                break;
            case "Consultar":

                break;
            case "Voltar":
                txtUsuId.setEnabled(true);

                jButtonCadastro.setVisible(true);
                jButtonAlteracao.setVisible(true);
                jButtonConsultar.setVisible(true);
                jButtonExcluir.setVisible(true);

                jButtonSalvar.setVisible(false);
                jButtonVoltar.setVisible(false);

                break;

            default:
                System.out.println("......");
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        txtFone = new javax.swing.JTextField();
        txtUsul = new javax.swing.JTextField();
        txtCpf = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        txtPerfil = new javax.swing.JTextField();
        jButtonAlteracao = new javax.swing.JButton();
        jButtonConsultar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonCadastro = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButtonSalvar = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setMinimumSize(new java.awt.Dimension(94, 34));
        setPreferredSize(new java.awt.Dimension(583, 425));

        jLabel1.setText("Id");

        jLabel2.setText("Usuario");

        jLabel3.setText("Fone");

        jLabel4.setText("Cpf");

        jLabel5.setText("Login");

        jLabel7.setText("Perfil");

        txtUsuId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuIdActionPerformed(evt);
            }
        });

        txtUsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsulActionPerformed(evt);
            }
        });

        txtPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPerfilActionPerformed(evt);
            }
        });

        jButtonAlteracao.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonAlteracao.setText("Alteração");
        jButtonAlteracao.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        jButtonAlteracao.setPreferredSize(new java.awt.Dimension(87, 27));
        jButtonAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlteracaoActionPerformed(evt);
            }
        });

        jButtonConsultar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonConsultar.setText("Consultar");
        jButtonConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        jButtonConsultar.setPreferredSize(new java.awt.Dimension(87, 27));
        jButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        jButtonExcluir.setPreferredSize(new java.awt.Dimension(87, 27));
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonCadastro.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonCadastro.setText("Cadastro");
        jButtonCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        jButtonCadastro.setPreferredSize(new java.awt.Dimension(87, 27));
        jButtonCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastroActionPerformed(evt);
            }
        });

        jLabel8.setText("Senha");

        jButtonSalvar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonVoltar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtUsul, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(txtLogin))
                                            .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(101, 101, 101)
                                        .addComponent(jLabel8)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonAlteracao, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAlteracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalvar)
                    .addComponent(jButtonVoltar))
                .addGap(94, 94, 94))
        );

        jButtonConsultar.getAccessibleContext().setAccessibleParent(jButtonConsultar);

        setBounds(0, 0, 583, 425);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastroActionPerformed
        limpaCampos();
        controlaBotoes("Cadastro");

    }//GEN-LAST:event_jButtonCadastroActionPerformed

    private void txtPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPerfilActionPerformed

    private void txtUsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsulActionPerformed
        // Chamando o metodo consultar
//      
    }//GEN-LAST:event_txtUsulActionPerformed

    private void jButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarActionPerformed

        consultarPorId();
    }//GEN-LAST:event_jButtonConsultarActionPerformed

    private void txtUsuIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuIdActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        controlaBotoes("Voltar");
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlteracaoActionPerformed
        atualizar();
    }//GEN-LAST:event_jButtonAlteracaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlteracao;
    private javax.swing.JButton jButtonCadastro;
    private javax.swing.JButton jButtonConsultar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPerfil;
    private javax.swing.JTextField txtSenha;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsul;
    // End of variables declaration//GEN-END:variables
}
