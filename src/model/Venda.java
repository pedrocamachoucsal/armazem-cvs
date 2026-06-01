package model;

/**
 * Representa a entidade de uma venda de produto do arquivo CSV.
 */
public class Venda {
    private int id;
    private String produto;
    private String categoria;
    private double valor;
    private int quantidade;

    public Venda(int id, String produto, String categoria, double valor, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.categoria = categoria;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    // Getters para acesso aos dados
    public int getId() { return id; }
    public String getProduto() { return produto; }
    public String getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public int getQuantidade() { return quantidade; }
}