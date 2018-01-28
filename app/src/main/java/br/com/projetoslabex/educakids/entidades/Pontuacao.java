package br.com.projetoslabex.educakids.entidades;

/**
 * Created by joiner on 02/09/16.
 */
public class Pontuacao {
    private int id;
    private String player;
    private int pontos;

    public Pontuacao() {
    }

    public Pontuacao(int id, String player, int pontos) {
        this.id = id;
        this.player = player;
        this.pontos = pontos;
    }

    public Pontuacao(String player, int pontos) {
        this.player = player;
        this.pontos = pontos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
