package br.com.projetoslabex.educakids.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoslabex.educakids.bdcore.BDCore;
import br.com.projetoslabex.educakids.entidades.Pontuacao;

/**
 * Created by joiner on 03/09/16.
 */
public class PontuacaoDAO {

    private SQLiteDatabase bd;

    public PontuacaoDAO(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Pontuacao pontuacao) {
        ContentValues values = new ContentValues();
        values.put("player", pontuacao.getPlayer());
        values.put("pontos", pontuacao.getPontos());
        bd.insert("pontuacao", null, values);
    }

    public void deleteAll() {
        bd.execSQL("delete from pontuacao");
    }

    public List<Pontuacao> getPontuacoes() {
        List<Pontuacao> list = new ArrayList<>();
        String[] colunas = new String[] {"_id", "player", "pontos"};
        Cursor cursor = bd.query("pontuacao", colunas, null, null, null, null, "pontos DESC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Pontuacao pontuacao = new Pontuacao();
                pontuacao.setId(cursor.getInt(0));
                pontuacao.setPlayer(cursor.getString(1));
                pontuacao.setPontos(cursor.getInt(2));
                list.add(pontuacao);
            } while (cursor.moveToNext());
        }



        return list;
    }

}
