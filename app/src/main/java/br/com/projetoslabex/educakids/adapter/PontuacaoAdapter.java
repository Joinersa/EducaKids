package br.com.projetoslabex.educakids.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.projetoslabex.educakids.R;
import br.com.projetoslabex.educakids.entidades.Pontuacao;

/**
 * Created by joiner on 03/09/16.
 */
public class PontuacaoAdapter extends RecyclerView.Adapter<PontuacaoAdapter.PontuacaoViewHolder> {
    private final List<Pontuacao> pontuacaos;
    private final Context context;
    private final PontuacaoOnClickListener onClickListener;

    public interface PontuacaoOnClickListener {
        public void onClickPontuacao(View view, int idx);
    }

    public PontuacaoAdapter(Context context, List<Pontuacao> pontuacaos, PontuacaoOnClickListener onClickListener) {
        this.context = context;
        this.pontuacaos = pontuacaos;
        this.onClickListener = onClickListener;
    }

    @Override
    public PontuacaoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_pontuacao, viewGroup, false);

        // Cria a classe ViewHolder
        PontuacaoViewHolder holder = new PontuacaoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PontuacaoViewHolder holder, final int position) {
        // Este método recebe o índice do elemento e atualiza as views que estão dentro do ViewHolder
        Pontuacao p = pontuacaos.get(position);
        // Atualiza os valores nas views
        holder.nomePlayer.setText(p.getPlayer());
        holder.pontos.setText("" + p.getPontos());

        holder.tvColocacao.setText((position+1) + "º");

        /*
        if(position == 0) {
            holder.ivColocacao.setImageResource(R.drawable.primeiro);
        }

        if(position == 1) {
            holder.ivColocacao.setImageResource(R.drawable.segundo);
        }

        if(position == 2) {
            holder.ivColocacao.setImageResource(R.drawable.terceiro);
        }
        */

        // Click
        /*
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Chama o listener para informar que clicou na pontuação
                    onClickListener.onClickPontuacao(holder.view, position);
                }
            });

        } */
    }

    @Override
    public int getItemCount() {
        return this.pontuacaos != null ? this.pontuacaos.size() : 0;
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class PontuacaoViewHolder extends RecyclerView.ViewHolder {
        public TextView nomePlayer;
        public TextView pontos;
        private View view;

        // -----------------------------------------------
        public TextView tvColocacao;
        // -----------------------------------------------

        public PontuacaoViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            nomePlayer = (TextView) view.findViewById(R.id.tv_nome_player);
            pontos = (TextView) view.findViewById(R.id.tv_pontos);

            tvColocacao = (TextView) view.findViewById(R.id.tv_colocacao);
        }
    }

}
