package factory;

import service.VendaProcessor;
import service.CSVVendaProcessor;

/**
 * Fábrica responsável por decidir e criar instâncias de processadores de dados.
 */
public class ProcessorFactory {

    /**
     * Factory Method para fabricar processadores com base no tipo informado.
     */
    public static VendaProcessor criarProcessador(String tipo) {
        if (tipo == null) {
            return null;
        }
        if (tipo.equalsIgnoreCase("CSV")) {
            return new CSVVendaProcessor();
        }
        // Permite fácil extensão para novos formatos no futuro (Ex: JSON, XML)
        throw new IllegalArgumentException("Tipo de processador não suportado: " + tipo);
    }
}