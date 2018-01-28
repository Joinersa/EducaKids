package br.com.projetoslabex.educakids.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoslabex.educakids.R;
import br.com.projetoslabex.educakids.adapter.PontuacaoAdapter;
import br.com.projetoslabex.educakids.dao.PontuacaoDAO;
import br.com.projetoslabex.educakids.entidades.Pontuacao;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView rvPontuacao;
    private List<Pontuacao> pontuacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        inicializarViews();

        // RecyclerView
        rvPontuacao.setLayoutManager(new LinearLayoutManager(this));
        rvPontuacao.setItemAnimator(new DefaultItemAnimator());
        rvPontuacao.setHasFixedSize(true);


        PontuacaoDAO dao = new PontuacaoDAO(RankingActivity.this);
        pontuacoes = dao.getPontuacoes();

        rvPontuacao.setAdapter(new PontuacaoAdapter(this, pontuacoes, null));


    }

    private void inicializarViews() {
        rvPontuacao = (RecyclerView) findViewById(R.id.rv_pontuacao);
    }


    // OnClick Pontuacao
    /*
    private PontuacaoAdapter.PontuacaoOnClickListener onClickPontuacao() {
        return new PontuacaoAdapter.PontuacaoOnClickListener() {
            @Override
            public void onClickPontuacao(View view, int idx) {
                Toast.makeText(RankingActivity.this, "" + pontuacoes.get(idx).getPlayer(), Toast.LENGTH_SHORT).show();
            }
        };
    }
    */
    private Activity getActivity() {
                return this;
    }

}
