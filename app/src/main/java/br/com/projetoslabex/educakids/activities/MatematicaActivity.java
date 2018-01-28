package br.com.projetoslabex.educakids.activities;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import br.com.projetoslabex.educakids.R;
import br.com.projetoslabex.educakids.dao.PontuacaoDAO;
import br.com.projetoslabex.educakids.entidades.Pontuacao;

public class MatematicaActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivValor1, ivValor2, ivOperacao;
    private Button btRespostaMat1, btRespostaMat2, btRespostaMat3;
    private int resultado = 0;
    private MediaPlayer player = null;
    private TextView tvPontuacao;
    private int pontos = 0;
    private ImageView ivEmojiCertoErrado;
    private boolean respostaCorreta = true;
    private Animation animationUp;
    private int[] carinhaErrado = {
            R.drawable.ic_errado_01, R.drawable.ic_errado_02, R.drawable.ic_errado_03,
            R.drawable.ic_errado_04, R.drawable.ic_errado_05, R.drawable.ic_errado_06,
            R.drawable.ic_errado_07, R.drawable.ic_errado_08, R.drawable.ic_errado_09,
            R.drawable.ic_errado_10, R.drawable.ic_errado_11, R.drawable.ic_errado_12,
            R.drawable.ic_errado_13, R.drawable.ic_errado_14, R.drawable.ic_errado_15,
            R.drawable.ic_errado_16, R.drawable.ic_errado_17, R.drawable.ic_errado_18,
            R.drawable.ic_errado_19, R.drawable.ic_errado_20, R.drawable.ic_errado_21,
            R.drawable.ic_errado_22
    };
    private int[] carinhaCorreto = {
            R.drawable.ic_correto_01, R.drawable.ic_correto_02, R.drawable.ic_correto_03,
            R.drawable.ic_correto_04, R.drawable.ic_correto_05, R.drawable.ic_correto_06,
            R.drawable.ic_correto_07, R.drawable.ic_correto_08, R.drawable.ic_correto_09,
            R.drawable.ic_correto_10, R.drawable.ic_correto_11, R.drawable.ic_correto_12,
            R.drawable.ic_correto_13, R.drawable.ic_correto_14, R.drawable.ic_correto_15,
            R.drawable.ic_correto_16
    };
    // alternativas:
    private ArrayList<Integer> vetorRespostas = new ArrayList<>(); // respostas (uma correta)
    private int[] valor = {
            R.drawable.m_01, R.drawable.m_02, R.drawable.m_03, R.drawable.m_04, R.drawable.m_05,
            R.drawable.m_06, R.drawable.m_07, R.drawable.m_08, R.drawable.m_09, R.drawable.m_10
    };
    private int[] operacao = {R.drawable.mais, R.drawable.menos};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematica);
        inicializarViews();
        animationUp = AnimationUtils.loadAnimation(MatematicaActivity.this, R.anim.move_up);
        //-----------------------------------------------------------
        gerarRodada();
        //-----------------------------------------------------------
        btRespostaMat1.setOnClickListener(this);
        btRespostaMat2.setOnClickListener(this);
        btRespostaMat3.setOnClickListener(this);
    }

    private void inicializarViews() {
        tvPontuacao = (TextView) findViewById(R.id.tv_pontuacao);
        ivValor1 = (ImageView) findViewById(R.id.iv_macas_1);
        ivValor2 = (ImageView) findViewById(R.id.iv_macas_2);
        ivOperacao = (ImageView) findViewById(R.id.iv_operacao);
        btRespostaMat1 = (Button) findViewById(R.id.bt_resposta_mat_1);
        btRespostaMat2 = (Button) findViewById(R.id.bt_resposta_mat_2);
        btRespostaMat3 = (Button) findViewById(R.id.bt_resposta_mat_3);
        ivEmojiCertoErrado = (ImageView) findViewById(R.id.iv_emoji_certo_errado);
    }

    private void ativaBotoes(boolean b) {
        btRespostaMat1.setClickable(b);
        btRespostaMat2.setClickable(b);
        btRespostaMat3.setClickable(b);
    }

    private void playAudio(View view) {

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                mp = null;
            }
        });

        player.start();
    }



    private int v1, v2, op;

    private void gerarRodada() {
        animationUp = AnimationUtils.loadAnimation(MatematicaActivity.this, R.anim.move_up);
        // escolhe aleatoriamente as quantidades de maçãs e a operação: ----
        v1 = valor[new Random().nextInt(valor.length)];
        v2 = valor[new Random().nextInt(valor.length)];
        op = operacao[new Random().nextInt(operacao.length)];
        // -----------------------------------------------------------------

        // seta as maçãs e a operação: -------------------------------------
        ivValor1.setBackgroundResource(v1);
        ivOperacao.setBackgroundResource(op);
        ivValor2.setBackgroundResource(v2);
        // -----------------------------------------------------------------

        if(getResources().getResourceEntryName(op).equals("mais")) { // soma

            resultado = Integer.parseInt(getResources().getResourceEntryName(v1).substring(2, 4)) +
                    Integer.parseInt(getResources().getResourceEntryName(v2).substring(2, 4));

        } else { // subtracao
            // transforma o nome da imagem png em string e depois em inteiro.
            int primeiroValor = Integer.parseInt(getResources().getResourceEntryName(v1).substring(2, 4));
            int segundoValor = Integer.parseInt(getResources().getResourceEntryName(v2).substring(2, 4));
            if (primeiroValor < segundoValor) {
                // calcula valores com resultado positivo:
                resultado = segundoValor - primeiroValor;
                // trocar imagens(maçãs) de lugar:
                ivValor1.setBackgroundResource(v2);
                ivValor2.setBackgroundResource(v1);
            } else {
                resultado = primeiroValor - segundoValor;
            }
        }

        //Toast.makeText(Matematica.this, "" + resultado, Toast.LENGTH_SHORT).show();
        //System.out.println("************** RESULTADO: " + resultado);
        geraAlternativas();

    }



    private void geraAlternativas() {

        // preencher lista numDesordenados com as imagens dos numeros, menos a resposta correta:
        ArrayList<Integer> numDesordenados = new ArrayList<>();

        for (int i = 0; i <= 20; i++) {

            if (i == resultado) {
                vetorRespostas.add(0, resultado); // resposta correta
            } else {
                numDesordenados.add(i);
            }
        }
        // embaralhar numeros:
        Collections.shuffle(numDesordenados);

        vetorRespostas.add(1, numDesordenados.get(0));// pegar a primeira posição de numDesordenados mesmo, pois ja está embaralhado.
        vetorRespostas.add(2, numDesordenados.get(1));// pegar a segunda posição numDesordenados mesmo, pois ja está embaralhado.
        Collections.shuffle(vetorRespostas); // embaralhar respostas :)
        // setar respostas nos botões:
        //Button button1 = (Button) findViewById(R.id.bt1);
        btRespostaMat1.setText("" + vetorRespostas.get(0));
        //Button button2 = (Button) findViewById(R.id.bt2);
        btRespostaMat2.setText("" + vetorRespostas.get(1));
        //Button button3 = (Button) findViewById(R.id.bt3);
        btRespostaMat3.setText("" + vetorRespostas.get(2));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            // evento de click do primeiro botão:
            case R.id.bt_resposta_mat_1:
                ativaBotoes(false); // desativa botoes para nao apertarem mais de um em  uma rodada
                // verifica se a resposta está certa ou errada:
                if (vetorRespostas.get(0) == resultado) {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.correto_01);
                    ivEmojiCertoErrado.setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    pontos++; //+= 10;
                    tvPontuacao.setText("" + pontos); // seta a pontuação na tela
                } else {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.errado_01);// primeira posição prq só tem um audio *****************************************
                    ivEmojiCertoErrado.setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    respostaCorreta = false;
                }
                playAudio(v); // tocar
                vetorRespostas.clear();// limpar lista de respostas
                // 1,4 segundos pra gerar outra rodada:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent i=new Intent(SearxhJobs.this,JobsTypes.class);
                        //startActivity(i);
                        ivEmojiCertoErrado.setBackgroundResource(R.drawable.vazio_01);
                        gerarRodada();
                        ativaBotoes(true);
                        if (!respostaCorreta) { // resposta errada
                            inserirPontuacaoBD();
                            respostaCorreta = true;
                            pontos = 0;
                            tvPontuacao.setText(""+pontos);
                        }
                    }
                }, 1400);

                break;

            // evento de click do segundo botão:
            case R.id.bt_resposta_mat_2:
                ativaBotoes(false); // desativa botoes para nao apertarem mais de um em  uma rodada
                //if (getResources().getResourceEntryName(vetorRespostas.get(1)).equals("layout_botao_" + resultado)) {
                if (vetorRespostas.get(1) == resultado) {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.correto_01);
                    ivEmojiCertoErrado.setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    pontos++; //+= 10;
                    tvPontuacao.setText("" + pontos); // seta a pontuação na tela

                } else {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.errado_01);// primeira posição prq só tem um audio *****************************************
                    ivEmojiCertoErrado.setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    respostaCorreta = false;
                }
                playAudio(v); // tocar
                vetorRespostas.clear();// limpar lista de respostas
                // 1,4 segundos pra gerar outra rodada:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent i=new Intent(SearxhJobs.this,JobsTypes.class);
                        //startActivity(i);
                        ivEmojiCertoErrado.setBackgroundResource(R.drawable.vazio_01);
                        gerarRodada();
                        ativaBotoes(true);
                        if (!respostaCorreta) { // resposta errada
                            inserirPontuacaoBD();
                            respostaCorreta = true;
                            pontos = 0;
                            tvPontuacao.setText("" + pontos);
                        }
                    }
                }, 1400);

                break;

            // evento de click do terceiro botão:
            case R.id.bt_resposta_mat_3:
                ativaBotoes(false); // desativa botoes para nao apertarem mais de um em  uma rodada
                if (vetorRespostas.get(2) == resultado) {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.correto_01);
                    ivEmojiCertoErrado.setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    pontos++; //pontos += 10;
                    tvPontuacao.setText("" + pontos);
                } else {
                    player = MediaPlayer.create(MatematicaActivity.this, R.raw.errado_01);// primeira posição prq só tem um audio *****************************************
                    ivEmojiCertoErrado.setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);
                    ivEmojiCertoErrado.setAnimation(animationUp);
                    respostaCorreta = false;
                }
                playAudio(v); // tocar
                vetorRespostas.clear();// limpar lista de respostas
                // 1,4 segundos pra gerar outra rodada:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent i=new Intent(SearxhJobs.this,JobsTypes.class);
                        //startActivity(i);
                        ivEmojiCertoErrado.setBackgroundResource(R.drawable.vazio_01);
                        gerarRodada();
                        ativaBotoes(true);

                        if (!respostaCorreta) { // resposta errada
                            inserirPontuacaoBD();
                            respostaCorreta = true;
                            pontos = 0;
                            tvPontuacao.setText("" + pontos);
                        }
                    }
                }, 1400);

                break;
        }

    }

    private boolean flagSairAntesDoDialog = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flagSairAntesDoDialog = true;

    }

    private void inserirPontuacaoBD() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(MatematicaActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_alert_ranking, null, false);

        final EditText etNomePlayer = (EditText) view.findViewById(R.id.et_nome_player_alert);
        final TextView tvPontuacao = (TextView) view.findViewById(R.id.tv_pontos_alert);
        Button btCancelar = (Button) view.findViewById(R.id.bt_alert_cancelar);
        Button btSalvar = (Button) view.findViewById(R.id.bt_alert_salvar);

        etNomePlayer.setText("Player");
        etNomePlayer.setSelection(6);//setSelection(etNomePlayer.getText().length()); // mover o cursor para o final no nome setado
        tvPontuacao.setText("" + pontos);

        alert.setView(view);
        alert.setCancelable(false);
        //alert.show();
        final AlertDialog dialog = alert.create();

        if (!flagSairAntesDoDialog) { // poderia cair em uma exceção, matando o app, caso voltasse para activity anterior antes de abrir o AlertDialog
            dialog.show();
        }


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PontuacaoDAO dao = new PontuacaoDAO(getApplicationContext());
                dao.inserir(new Pontuacao(etNomePlayer.getText().toString(), Integer.parseInt(tvPontuacao.getText().toString())));
                dialog.dismiss();
                Toast.makeText(MatematicaActivity.this, "Pontuação salva", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

