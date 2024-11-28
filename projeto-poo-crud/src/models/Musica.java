package models;

public class Musica {
    
    public Integer id;
    public String titulo;
    public String artista;
    public String album;
    public String genero;
    public String duracao;
    

    public Musica(){
    }

    public Musica(Integer id,String titulo, String artista, String album, String genero, String duracao) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.duracao = duracao;
    }


    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getArtista() {
        return artista;
    }
    public void setArtista(String artista) {
        this.artista = artista;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getDuracao() {
        return duracao;
    }
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Musica [id=" + id + ", titulo=" + titulo + ", artista=" + artista + ", album=" + album + ", genero="
                + genero + ", duracao=" + duracao + "]";
    }

}
