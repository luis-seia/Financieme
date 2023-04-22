package mz.ac.luis.seia.finacieme.model;

public class Artigo {
    private int capitulo;
    private String titulo;

    public Artigo(int capitulo, String titulo) {
        this.capitulo = capitulo;
        this.titulo = titulo;
    }

    public int getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(int capitulo) {
        this.capitulo = capitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
