package models;

public class Instrumentos {
    
    private static Integer id;
    private static String tipo;
    private static Double preco;
    private static String nome;

    public static Integer getId() {
        return id;
    }
    public static void setId(Integer id) {
        Instrumentos.id = id;
    }
    public static String getTipo() {
        return tipo;
    }
    public static void setTipo(String tipo) {
        Instrumentos.tipo = tipo;
    }
    public static Double getPreco() {
        return preco;
    }
    public static void setPreco(Double preco) {
        Instrumentos.preco = preco;
    }
    

    
    public static String getNome() {
        return nome;
    }
    public static void setNome(String nome) {
        Instrumentos.nome = nome;
    }

    @Override
    public String toString() {
        return "Instrumentos []";
    }
}
