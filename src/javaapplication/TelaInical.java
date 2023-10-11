
package javaapplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends JFrame {
    private List<Consulta> consultas = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable tabela;

    public TelaInicial() {
        setTitle("Boas-vindas ao sistema de agendamento");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"Paciente", "CPF", "Telefone", "Data", "Já era paciente?", "Consulta Realizada"}, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnNovaConsulta = new JButton("Nova Consulta");
        JButton btnExcluirConsulta = new JButton("Excluir Consulta");
        JButton btnFinalizarConsulta = new JButton("Finalizar Consulta");

        btnNovaConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirTelaCadastroConsulta();
            }
        });

        btnExcluirConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                excluirConsulta();
            }
        });

        btnFinalizarConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirTelaDetalhesConsulta();
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNovaConsulta);
        buttonPanel.add(btnExcluirConsulta);
        buttonPanel.add(btnFinalizarConsulta);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
        Object[] rowData = {consulta.getNome(), consulta.getCpf(), consulta.getTelefone(), consulta.getData(), consulta.isJaEraPaciente(), consulta.isConsultaRealizada()};
        tableModel.addRow(rowData);
    }

    private void excluirConsulta() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
            consultas.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para excluir.");
        }
    }

    private void abrirTelaCadastroConsulta() {
        TelaCadastroConsulta telaCadastro = new TelaCadastroConsulta(this);
        telaCadastro.setVisible(true);
    }

    private void abrirTelaDetalhesConsulta() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
            Consulta consulta = consultas.get(selectedRow);
            if (!consulta.isConsultaRealizada()) {
                TelaDetalhesConsulta telaDetalhes = new TelaDetalhesConsulta(consulta);
                telaDetalhes.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "A consulta já foi finalizada.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para finalizar.");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaInicial().setVisible(true);
            }
        });
    }
}
