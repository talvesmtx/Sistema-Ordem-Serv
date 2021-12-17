/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prjtkca.telas;

import br.com.prjtkca.dal.ModuloConexao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author talve
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    private PreparedStatement pst;
    private ResultSet rs;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
        populaComboClientes();
        jListaOsVazia();

        btnSalvar.setVisible(false);
        btnVoltar.setVisible(false);
    }

    private void limpaCampos() {
        txtequipamento.setText("");
        txtdefeito.setText("");
        txtservico.setText("");
        txttecnico.setText("");
        txtvalor.setText("");
    }

    private void cadastrarOS() {
        var equipamento = txtequipamento.getText();
        var defeito = txtdefeito.getText();
        var servico = txtservico.getText();
        var tecnico = txttecnico.getText();
        var valor = txtvalor.getText();
        boolean tipoOrdemServico = radioOrdemServico.isSelected();
        boolean tipoOrcamento = radioOrcamento.isSelected();

        var idcli = comboCliente.getSelectedItem().toString();
        var _idcli = idcli.substring(0, idcli.indexOf("-"));

        Random r = new Random();
        int os = r.nextInt(100);

        var _tipoos = (tipoOrcamento ? 1 : 2);

        if (equipamento.isEmpty() || defeito.isEmpty() || servico.isEmpty() || tecnico.isEmpty() || valor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        if (!tipoOrdemServico && !tipoOrcamento) {
            JOptionPane.showMessageDialog(null, "Por favor selecione o tipo (Orçamento, ou Ordem de Serviço)!");
            return;
        }

        String sql = "insert into ktcaos (os, equipamento, defeito, servico, tecnico, valor, idcli, tipoos) values ('%s','%s','%s','%s','%s','%s','%s','%s');";
        sql = sql.formatted(os, equipamento, defeito, servico, tecnico, valor, _idcli, _tipoos);
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

    private void atualizarOs() {
        var equipamento = txtequipamento.getText();
        var defeito = txtdefeito.getText();
        var servico = txtservico.getText();
        var tecnico = txttecnico.getText();
        var valor = txtvalor.getText();

        var tipoos = (radioOrcamento.isSelected() ? "1" : "2");
        var numeroOs = retornaNumeroOsJlista();

        if (equipamento.isEmpty() || defeito.isEmpty() || servico.isEmpty() || tecnico.isEmpty() || valor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha todos campos!");
            return;
        }

        String sql = "update ktcaos SET equipamento=?, defeito=?, servico=?, tecnico=?, valor=?, tipoos=? where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, equipamento);
            pst.setString(2, defeito);
            pst.setString(3, servico);
            pst.setString(4, tecnico);
            pst.setString(5, valor);
            pst.setString(6, tipoos);
            pst.setString(7, numeroOs);

            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            limpaCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu atualizados: " + e);
        }
    }

    private void excluirOs() {
        var numeroOs = retornaNumeroOsJlista();
        if ("0".equals(numeroOs)) {
            JOptionPane.showMessageDialog(null, "excluirOs --> numeroOs está vazio!!");
            return;
        }

        String sql = "delete from ktcaos where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, numeroOs);
            pst.execute();
            pst.close();

            JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
            limpaCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "excluirOs --> Não conseguiu excluir: " + e);
        }

    }

    private void pesquisarOS(String numeroOs) {
        String sql = "select * from ktcaos where os=?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, numeroOs);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtequipamento.setText(rs.getString(3));
                txtdefeito.setText(rs.getString(4));
                txtservico.setText(rs.getString(5));
                txttecnico.setText(rs.getString(6));
                txtvalor.setText(rs.getString(7));

                if (Integer.parseInt(rs.getString(9)) == 1) {
                    radioOrcamento.setSelected(true);
                } else {
                    radioOrdemServico.setSelected(true);
                }
            } else {
                limpaCampos();
                JOptionPane.showMessageDialog(null, "pesquisarOS --> Dados não encontrados!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não conectou no banco: " + e);
        }
    }

    private void populaComboClientes() {
        String sql = "select idcli, nomecli from ktcaclientes";
        var groupNames = new ArrayList<String>();
        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            var selecione = "Selecione...";
            groupNames.add(selecione);

            while (rs.next()) {
                var cliente = rs.getString("idcli") + " - " + rs.getString("nomecli");
                groupNames.add(cliente);

            }
            // Populate the combo box
            DefaultComboBoxModel model = new DefaultComboBoxModel(groupNames.toArray());
            comboCliente.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void controlaBotoes(String nomeBotao) {
        switch (nomeBotao) {
            case "Cadastro":
                btnCadastrar.setVisible(false);
                btnAlteracao.setVisible(false);
                btnExcluir.setVisible(false);

                btnSalvar.setVisible(true);
                btnVoltar.setVisible(true);

                limpaCampos();
                jListaOsVazia();

                break;
            case "Alteracao":

                break;
            case "Consultar":

                break;
            case "Voltar":
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

    private void populaListaOs() {

        var idcli = comboCliente.getSelectedItem().toString();

        if ("Selecione...".equals(idcli)) {
            jListaOsVazia();
            return;
        }

        var _idcli = idcli.substring(0, idcli.indexOf("-"));

        String sql = "select * from ktcaos where idcli=?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, _idcli);
            rs = pst.executeQuery();

            var listModel = new DefaultListModel();

            while (rs.next()) {
                Date data = rs.getDate("data_os");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = formato.format(data);

                var modelo = "OS: " + rs.getString("os") + " - EQUIPAMENTO: " + rs.getString("equipamento") + " - DATA: " + dataFormatada;
                listModel.addElement(modelo);
                jListaOS.setModel(listModel);

            }

            if (listModel.isEmpty()) {
                jListaOsVazia();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "populaListaOs -> Não conectou no banco: " + e);
        }

    }

    private void jListaOsVazia() {
        var listModel = new DefaultListModel();
        listModel.addElement("Sem Dados...");
        jListaOS.setModel(listModel);

    }

    private String retornaNumeroOsJlista() {
        var numeroOs = "0";
        String valorJLista = (String) jListaOS.getSelectedValue();

        if (valorJLista != null && !valorJLista.isEmpty()) {
            numeroOs = valorJLista.substring(0, valorJLista.indexOf("-"));
            numeroOs = numeroOs.replace("OS: ", "");
        }
        return numeroOs;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField5 = new javax.swing.JTextField();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        radioOrcamento = new javax.swing.JRadioButton();
        radioOrdemServico = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtequipamento = new javax.swing.JTextField();
        txtdefeito = new javax.swing.JTextField();
        txtservico = new javax.swing.JTextField();
        txttecnico = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtvalor = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnAlteracao = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListaOS = new javax.swing.JList<>();
        comboCliente = new javax.swing.JComboBox<>();

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
        jScrollPane1.setViewportView(jTable1);

        jTextField5.setText("jTextField5");

        jPasswordField1.setText("jPasswordField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(600, 440));

        buttonGroup1.add(radioOrcamento);
        radioOrcamento.setText("Orçamento");

        buttonGroup1.add(radioOrdemServico);
        radioOrdemServico.setText("Ordem de Serviço");
        radioOrdemServico.setToolTipText("");

        jLabel7.setText("Equipamento");

        jLabel8.setText("Defeito");

        jLabel9.setText("Serviço");

        jLabel10.setText("Técnico");

        jLabel11.setText("Valor Total");

        txtvalor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtvalorActionPerformed(evt);
            }
        });

        btnCadastrar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnAlteracao.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnAlteracao.setText("Alterar");
        btnAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteracaoActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnVoltar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel5.setText("Cliente");

        jListaOS.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListaOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListaOSMouseClicked(evt);
            }
        });
        jListaOS.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListaOSValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListaOS);

        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlteracao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtdefeito, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txttecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtservico, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtequipamento, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(radioOrcamento)
                                        .addGap(87, 87, 87))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(radioOrdemServico)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25)
                        .addComponent(radioOrcamento)
                        .addGap(21, 21, 21)
                        .addComponent(radioOrdemServico))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtequipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtdefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtservico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txttecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnAlteracao)
                    .addComponent(btnExcluir)
                    .addComponent(btnSalvar)
                    .addComponent(btnVoltar))
                .addGap(57, 57, 57))
        );

        setBounds(0, 0, 575, 396);
    }// </editor-fold>//GEN-END:initComponents

    private void txtvalorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtvalorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtvalorActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        limpaCampos();
        controlaBotoes("Cadastro");
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        controlaBotoes("Voltar");
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void comboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClienteActionPerformed
        populaListaOs();
    }//GEN-LAST:event_comboClienteActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        cadastrarOS();
        populaListaOs();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jListaOSValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListaOSValueChanged

    }//GEN-LAST:event_jListaOSValueChanged

    private void btnAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteracaoActionPerformed
        atualizarOs();
        populaListaOs();
    }//GEN-LAST:event_btnAlteracaoActionPerformed

    private void jListaOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListaOSMouseClicked
        var numeroOs = retornaNumeroOsJlista();
        pesquisarOS(numeroOs);
    }//GEN-LAST:event_jListaOSMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirOs();
        populaListaOs();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlteracao;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListaOS;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JRadioButton radioOrcamento;
    private javax.swing.JRadioButton radioOrdemServico;
    private javax.swing.JTextField txtdefeito;
    private javax.swing.JTextField txtequipamento;
    private javax.swing.JTextField txtservico;
    private javax.swing.JTextField txttecnico;
    private javax.swing.JTextField txtvalor;
    // End of variables declaration//GEN-END:variables
}
