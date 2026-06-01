package app;

import model.Venda;
import service.CSVReader;
import service.RelatorioService;
import thread.VendaThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal que orquestra a leitura, divisão em lotes e disparo de threads.
 */
public class Main {
    public static void main(String[] args) {
        // Caminho do arquivo de dados dentro do projeto
        String caminhoCSV = "src/resources/vendas.csv";

        // 1. Leitura dos dados do arquivo CSV
        CSVReader reader = new CSVReader();
        List<Venda> todasAsVendas = reader.lerArquivo(caminhoCSV);

        if (todasAsVendas.isEmpty()) {
            System.out.println("Nenhum registro encontrado para processamento.");
            return;
        }

        // Definindo o número de threads do sistema
        int numeroDeThreads = 4; 
        int tamanhoTotal = todasAsVendas.size();
        // Define o tamanho do bloco dividindo os itens pelo número de threads
        int tamanhoBloco = (int) Math.ceil((double) tamanhoTotal / numeroDeThreads);

        List<Thread> threadsAtivas = new ArrayList<>();

        // 2. Divisão de blocos de dados e inicialização das Threads
        for (int i = 0; i < numeroDeThreads; i++) {
            int deIndex = i * tamanhoBloco;
            int ateIndex = Math.min(deIndex + tamanhoBloco, tamanhoTotal);

            // Se o índice inicial ultrapassar o tamanho, encerra a criação
            if (deIndex >= tamanhoTotal) break;

            // Extrai o bloco de dados específico para esta thread
            List<Venda> blocoThread = todasAsVendas.subList(deIndex, ateIndex);

            // Cria a tarefa Runnable e encapsula em um objeto Thread
            VendaThread tarefa = new VendaThread(blocoThread);
            Thread thread = new Thread(tarefa);
            
            threadsAtivas.add(thread);
            thread.start(); // Inicia o processamento paralelo
        }

        // 3. Sincronização: Aguarda o término de todas as threads antes de prosseguir
        for (Thread t : threadsAtivas) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("A execução de uma thread foi interrompida: " + e.getMessage());
            }
        }

        // 4. Geração e exibição do relatório consolidado final
        RelatorioService relatorioService = new RelatorioService();
        relatorioService.gerarRelatorioNoTerminal();
    }
}