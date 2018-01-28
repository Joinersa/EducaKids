package br.com.projetoslabex.educakids.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

import br.com.projetoslabex.educakids.R;


public class LetrasActivity extends AppCompatActivity implements View.OnClickListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    public MediaPlayer player = null, playerAux = null;// = new MediaPlayer();
    private ImageView btLetra1;
    private ImageView btLetra2;
    private ImageView btLetra3;
    private ImageView btLetra4;
    private ImageView ivCarinha;
    private Animation animationUp;
    private FloatingActionButton btAudioFab;
    private boolean primeiraVezAqui = true;
    private int buttonSelect_1, buttonSelect_2, buttonSelect_3, buttonSelect_4;
    private int somSelected; // numero do som
    private int[] somDeErro = {R.raw.errado, R.raw.tente_novamente};
    private int[] somAcerto = {R.raw.acertou, R.raw.muito_bem, R.raw.vc_acertou, R.raw.parabens};
    private int[] varButtonImages = {
            R.drawable.layout_botao_a, R.drawable.layout_botao_b, R.drawable.layout_botao_c,
            R.drawable.layout_botao_d, R.drawable.layout_botao_e, R.drawable.layout_botao_f,
            R.drawable.layout_botao_g, R.drawable.layout_botao_h, R.drawable.layout_botao_i,
            R.drawable.layout_botao_j, R.drawable.layout_botao_k, R.drawable.layout_botao_l,
            R.drawable.layout_botao_m, R.drawable.layout_botao_n, R.drawable.layout_botao_o,
            R.drawable.layout_botao_p, R.drawable.layout_botao_q, R.drawable.layout_botao_r,
            R.drawable.layout_botao_s, R.drawable.layout_botao_t, R.drawable.layout_botao_u,
            R.drawable.layout_botao_v, R.drawable.layout_botao_w, R.drawable.layout_botao_x,
            R.drawable.layout_botao_y, R.drawable.layout_botao_z // 26
    };
    private int[] varSons = {
            R.raw.a, R.raw.b, R.raw.c, R.raw.d, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i,
            R.raw.j, R.raw.k, R.raw.l, R.raw.m, R.raw.n, R.raw.o, R.raw.p, R.raw.q, R.raw.r,
            R.raw.s, R.raw.t, R.raw.u, R.raw.v, R.raw.w, R.raw.x, R.raw.y, R.raw.z
    };
    private int[] carinhaErrado = {
            R.drawable.ic_errado_01, R.drawable.ic_errado_02, R.drawable.ic_errado_03,
            R.drawable.ic_errado_04, R.drawable.ic_errado_05, R.drawable.ic_errado_06,
            R.drawable.ic_errado_07, R.drawable.ic_errado_08, R.drawable.ic_errado_09,
            R.drawable.ic_errado_10, R.drawable.ic_errado_11, R.drawable.ic_errado_12,
            R.drawable.ic_errado_13, R.drawable.ic_errado_14, R.drawable.ic_errado_15,
            R.drawable.ic_errado_16, R.drawable.ic_errado_17, R.drawable.ic_errado_18,
            R.drawable.ic_errado_19
    };
    private int[] carinhaCorreto = {
            R.drawable.ic_correto_01, R.drawable.ic_correto_02, R.drawable.ic_correto_03,
            R.drawable.ic_correto_04, R.drawable.ic_correto_05, R.drawable.ic_correto_06,
            R.drawable.ic_correto_07, R.drawable.ic_correto_08, R.drawable.ic_correto_09,
            R.drawable.ic_correto_10, R.drawable.ic_correto_11, R.drawable.ic_correto_12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_letras);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // bt voltar
        inicializarViews();
        animationUp = AnimationUtils.loadAnimation(LetrasActivity.this, R.anim.move_up);
        //onCompletion(player);
        //-----------------------------------------------------------------------------
        gerarRodada();
        //-----------------------------------------------------------------------------
        // botao de saida de audio da letra a escolher
        btAudioFab.setOnClickListener(LetrasActivity.this);
        // botao da primeira letra
        btLetra1.setOnClickListener(LetrasActivity.this);
        // botão da segunda letra
        btLetra2.setOnClickListener(LetrasActivity.this);
        // botão da terceira letra
        btLetra3.setOnClickListener(LetrasActivity.this);
        // botão da quarta letra
        btLetra4.setOnClickListener(LetrasActivity.this);

        ativaBotoes(false);

    }

    private void inicializarViews() {
        btLetra1 = (ImageView) findViewById(R.id.bt_letra_1);
        btLetra2 = (ImageView) findViewById(R.id.bt_letra_2);
        btLetra3 = (ImageView) findViewById(R.id.bt_letra_3);
        btLetra4 = (ImageView) findViewById(R.id.bt_letra_4);
        btAudioFab = (FloatingActionButton) findViewById(R.id.fab_audio);
        ivCarinha = (ImageView) findViewById(R.id.iv_carinha);
    }



    private void opcaoCorreta() {
        //player = MediaPlayer.create(LetrasActivity.this, somAcerto[new Random().nextInt(somAcerto.length)]);
        animationUp = AnimationUtils.loadAnimation(LetrasActivity.this, R.anim.move_up);

        player = MediaPlayer.create(LetrasActivity.this, R.raw.correto_01);

        playAudio();

        ivCarinha.setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]); // setar carinha
        ivCarinha.setAnimation(animationUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                gerarRodada();
                //ativaBotoes(true);
                ivCarinha.setBackgroundResource(R.drawable.vazio_01);
            }
        }, 1900);

    }

    private void opcaoIncorreta() {

        animationUp = AnimationUtils.loadAnimation(LetrasActivity.this, R.anim.move_up);

        //player = MediaPlayer.create(LetrasActivity.this, somDeErro[new Random().nextInt(somDeErro.length)]);
        player = MediaPlayer.create(LetrasActivity.this, R.raw.errado_01);

        playAudio();

        ivCarinha.setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);
        ivCarinha.setAnimation(animationUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivCarinha.setBackgroundResource(R.drawable.vazio_01);
                ativaBotoes(true);
            }
        }, 1900);
    }

    private void gerarImagens() {

        buttonSelect_1 = varButtonImages[new Random().nextInt(varButtonImages.length)];
        btLetra1.setBackgroundResource(buttonSelect_1);
        //System.out.println("BT1 = " + buttonSelect_1);
        //System.out.println("********* BOTÃO 1 = " + getResources().getResourceEntryName(buttonSelect_1));
        do {
            buttonSelect_2 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            //System.out.println("BT2 = " + buttonSelect_2);
        }
        while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_2)));

        btLetra2.setBackgroundResource(buttonSelect_2);
        //System.out.println("********* BOTÃO 2 = " + getResources().getResourceEntryName(buttonSelect_2));

        do {
            buttonSelect_3 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            //System.out.println("BT3 = " + buttonSelect_3);
        }
        while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_3)) ||
                getResources().getResourceEntryName(buttonSelect_2).equals(getResources().getResourceEntryName(buttonSelect_3)));

        btLetra3.setBackgroundResource(buttonSelect_3);
        //System.out.println("********* BOTÃO 3 = " + getResources().getResourceEntryName(buttonSelect_3));

        do {
            buttonSelect_4 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            //System.out.println("BT4 = " + buttonSelect_4);
        }
        while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_4)) ||
                getResources().getResourceEntryName(buttonSelect_2).equals(getResources().getResourceEntryName(buttonSelect_4)) ||
                getResources().getResourceEntryName(buttonSelect_3).equals(getResources().getResourceEntryName(buttonSelect_4)));

        btLetra4.setBackgroundResource(buttonSelect_4);
        //System.out.println("********* BOTÃO 4 = " + getResources().getResourceEntryName(buttonSelect_4));
    }

    private void gerarAudio() {
        do {
            somSelected = varSons[new Random().nextInt(varSons.length)];
            // modificado ***
        }
        while (!getResources().getResourceEntryName(buttonSelect_1).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_2).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_3).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_4).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)));

        //System.out.println("********* AUDIO = " + getResources().getResourceEntryName(somSelected));
        player = MediaPlayer.create(LetrasActivity.this, somSelected); // modificado ***
        playerAux = player; // aqui que tem a jogada pra não perder o audio da letra da pergunta
        playAudio(); //-- executa som que pergunta a letra ----------------------------------------
    }

    private void gerarRodada() {
        primeiraVezAqui = true;
        ativaBotoes(false);
        gerarImagens();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gerarAudio();
            }
        }, 500);
    }

    private void ativaBotoes(boolean b) {
        btLetra1.setClickable(b);
        btLetra2.setClickable(b);
        btLetra3.setClickable(b);
        btLetra4.setClickable(b);
        btAudioFab.setClickable(b);
    }

    private void playAudio() {
        if (player == null) {
            try {

                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        player.stop();
                        player.release();
                        player = null;
                    }
                });

                player.start();

                // ---------------------------------------
                player.setOnPreparedListener(this);
                player.setOnCompletionListener(this);
                // ---------------------------------------

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        } else {
            player.start();

            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
        }
    }

    private boolean flagSair = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flagSair = true;
        if (player != null) {
            player.stop(); // parar o audio, se já estiver tocando, ao voltar para activity inicial
            player.release();
            player = null;
        }
        /*
        if (playerAux != null) {
            playerAux.stop();
            playerAux.release();
            playerAux = null;
        }
        */
    }

    /*
        @Override
        public void onBackPressed() {
            if (player != null) {
                player.stop(); // parar o audio, se já estiver tocando, ao voltar para activity inicial
            }
            super.onBackPressed();
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //onBackPressed();
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.bt_letra_1:
                ativaBotoes(false); // desabilitar botões para não ser apertado mais de um..
                if (getResources().getResourceEntryName(buttonSelect_1).substring(13, 14).equals(getResources().getResourceEntryName(somSelected))) {
                    opcaoCorreta();
                } else {
                    opcaoIncorreta();
                }
                break;

            case R.id.bt_letra_2:
                ativaBotoes(false);
                if (getResources().getResourceEntryName(buttonSelect_2).substring(13, 14).equals(getResources().getResourceEntryName(somSelected))) {
                    opcaoCorreta();
                } else {
                    opcaoIncorreta();
                }
                break;

            case R.id.bt_letra_3:
                ativaBotoes(false);
                if (getResources().getResourceEntryName(buttonSelect_3).substring(13, 14).equals(getResources().getResourceEntryName(somSelected))) {
                    opcaoCorreta();
                } else {
                    opcaoIncorreta();
                }
                break;

            case R.id.bt_letra_4:
                ativaBotoes(false);
                if (getResources().getResourceEntryName(buttonSelect_4).substring(13, 14).equals(getResources().getResourceEntryName(somSelected))) {
                    opcaoCorreta();
                } else {
                    opcaoIncorreta();
                }
                break;

            case R.id.fab_audio:
                ativaBotoes(false);
                player = playerAux;
                playAudio(); // ouvir de novo a pergunta ao clicar no botão
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ativaBotoes(true);
                    }
                }, 1900);
                break;

        }
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i("Script", "onCompletion()");
        if (primeiraVezAqui) {
            ativaBotoes(true);
            primeiraVezAqui = false;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i("Script", "onPrepared()");

        mediaPlayer.start();

        if (flagSair) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}







/*
public class LetrasActivity extends AppCompatActivity {

    MediaPlayer player = null, playerAux = null;// = new MediaPlayer();

    //ImageButton bt1;
    //ImageButton bt2;
    //ImageButton bt3;
    //ImageButton bt4;

    private Button bt1, bt2, bt3, bt4;
    private boolean primeiraVezAqui = true;
    //ImageButton bt_inicio;
    int buttonSelect_1, buttonSelect_2, buttonSelect_3, buttonSelect_4;
    int somSelected; // numero do som
    int[] somDeErro = {
            R.raw.errado,
            R.raw.tente_novamente
    };
    int[] somAcerto = {
            R.raw.acertou,
            R.raw.muito_bem,
            R.raw.vc_acertou,
            R.raw.parabens
    };
    char[] letras = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z'
    };
    private int position1, position2, position3, position4; // posições do vetor de letras.
    int[] varButtonImages = {
            R.drawable.layout_botao_a,
            R.drawable.layout_botao_b,
            R.drawable.layout_botao_c,
            R.drawable.layout_botao_d,
            R.drawable.layout_botao_e,
            R.drawable.layout_botao_f,
            R.drawable.layout_botao_g,
            R.drawable.layout_botao_h,
            R.drawable.layout_botao_i,
            R.drawable.layout_botao_j,
            R.drawable.layout_botao_k,
            R.drawable.layout_botao_l,
            R.drawable.layout_botao_m,
            R.drawable.layout_botao_n,
            R.drawable.layout_botao_o,
            R.drawable.layout_botao_p,
            R.drawable.layout_botao_q,
            R.drawable.layout_botao_r,
            R.drawable.layout_botao_s,
            R.drawable.layout_botao_t,
            R.drawable.layout_botao_u,
            R.drawable.layout_botao_v,
            R.drawable.layout_botao_w,
            R.drawable.layout_botao_x,
            R.drawable.layout_botao_y,
            R.drawable.layout_botao_z // 26
    };
    int[] varSons = {
            R.raw.a, R.raw.b, R.raw.c, R.raw.d, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i,
            R.raw.j, R.raw.k, R.raw.l, R.raw.m, R.raw.n, R.raw.o, R.raw.p, R.raw.q, R.raw.r,
            R.raw.s, R.raw.t, R.raw.u, R.raw.v, R.raw.w, R.raw.x, R.raw.y, R.raw.z
    };

    int[] carinhaErrado = {
            R.drawable.errado_1,
            R.drawable.errado_2,
            R.drawable.errado_3,
            R.drawable.errado_4,
            R.drawable.errado_5,
            R.drawable.errado_6
    };

    int[] carinhaCorreto = {
            R.drawable.correto_1,
            R.drawable.correto_2,
            R.drawable.correto_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_letras);
        //toolbar.setTitle("Letras");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // bt voltar


        //bt1 = (ImageButton) findViewById(R.id.bt1);
        //bt2 = (ImageButton) findViewById(R.id.bt2);
        //bt3 = (ImageButton) findViewById(R.id.bt3);
        //bt4 = (ImageButton) findViewById(R.id.bt4);


        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);

        //-----------------------------------------------------------------------------
        gerarRodada();
        //-----------------------------------------------------------------------------

        // botao de saida de audio da letra a escolher
        FloatingActionButton btAudioFab = (FloatingActionButton) findViewById(R.id.fab_audio);
        btAudioFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = playerAux;
                playAudio(); // ouvir de novo a pergunta ao clicar no botão
            }
        });

        // botao da primeira letra
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                ativaBotoes(false); // desabilitar botões para não ser apertado mais de um..

                char letraSomSelecionado = getResources().getResourceEntryName(somSelected).charAt(0);

                //if(getResources().getResourceEntryName(buttonSelect_1).substring(13, 14).equals(getResources().getResourceEntryName(somSelected))) {
                if(letras[position1] == letraSomSelecionado) {

                    player = MediaPlayer.create(LetrasActivity.this, somAcerto[new Random().nextInt(somAcerto.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]); // setar carinha

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gerarRodada();
                            ativaBotoes(true);
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                        }
                    }, 1900);

                } else {

                    player = MediaPlayer.create(LetrasActivity.this, somDeErro[new Random().nextInt(somDeErro.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                            ativaBotoes(true);
                        }
                    }, 1900);

                }

            }
        });

        // botão da segunda letra
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ativaBotoes(false);

                char letraSomSelecionado = getResources().getResourceEntryName(somSelected).charAt(0);

                if(letras[position2] == letraSomSelecionado) {

                    player = MediaPlayer.create(LetrasActivity.this, somAcerto[new Random().nextInt(somAcerto.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]); // setar carinha

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gerarRodada();
                            ativaBotoes(true);
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                        }
                    }, 1900);

                } else {

                    player = MediaPlayer.create(LetrasActivity.this, somDeErro[new Random().nextInt(somDeErro.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                            ativaBotoes(true);
                        }
                    }, 1900);

                }

            }
        });

        // botão da terceira letra
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ativaBotoes(false);
                char letraSomSelecionado = getResources().getResourceEntryName(somSelected).charAt(0);

                if(letras[position3] == letraSomSelecionado) {

                    player = MediaPlayer.create(LetrasActivity.this, somAcerto[new Random().nextInt(somAcerto.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]); // setar carinha

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gerarRodada();
                            ativaBotoes(true);
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                        }
                    }, 1900);

                } else {

                    player = MediaPlayer.create(LetrasActivity.this, somDeErro[new Random().nextInt(somDeErro.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                            ativaBotoes(true);
                        }
                    }, 1900);

                }

            }
        });

        // botão da quarta letra
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ativaBotoes(false);
                char letraSomSelecionado = getResources().getResourceEntryName(somSelected).charAt(0);

                if(letras[position4] == letraSomSelecionado) {

                    player = MediaPlayer.create(LetrasActivity.this, somAcerto[new Random().nextInt(somAcerto.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaCorreto[new Random().nextInt(carinhaCorreto.length)]); // setar carinha

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gerarRodada();
                            ativaBotoes(true);
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                        }
                    }, 1900);

                } else {

                    player = MediaPlayer.create(LetrasActivity.this, somDeErro[new Random().nextInt(somDeErro.length)]);
                    playAudio();

                    findViewById(R.id.im_carinha).setBackgroundResource(carinhaErrado[new Random().nextInt(carinhaErrado.length)]);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.im_carinha).setBackgroundResource(R.drawable.vazio_01);
                            ativaBotoes(true);
                        }
                    }, 1900);

                }

            }
        });

    }

    public void gerarImagens() {

        //buttonSelect_1 = varButtonImages[new Random().nextInt(varButtonImages.length)];

        position1 = new Random().nextInt(26); // sorteando primeira posição para pegar uma letra para o primeiro botão

        do {
            position2 = new Random().nextInt(26); // sorteando segunda posição para pegar uma letra para o primeiro botão
        } while(position2 == position1);

        do {
            position3 = new Random().nextInt(26); // sorteando terceira posição para pegar uma letra para o primeiro botão
        } while(position3 == position1 || position3 == position2);

        do {
            position4 = new Random().nextInt(26); // sorteando quarta posição para pegar uma letra para o primeiro botão
        } while (position4 == position1 || position4 == position2 || position4 == position3);


        // setando letras sorteadas nos 4 botões:
        bt1.setText("" + letras[position1]); //findViewById(R.id.bt1).setBackgroundResource(buttonSelect_1);
        bt2.setText("" + letras[position2]);
        bt3.setText("" + letras[position3]);
        bt4.setText("" + letras[position4]);

        //System.out.println("BT1 = " + buttonSelect_1);
        //System.out.println("********* BOTÃO 1 = " + getResources().getResourceEntryName(buttonSelect_1));

        /*
        do {
            //buttonSelect_2 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            bt2.setText(letras[new Random().nextInt(26)]);
            //System.out.println("BT2 = " + buttonSelect_2);
        } while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_2)));

        findViewById(R.id.bt2).setBackgroundResource(buttonSelect_2); // setar
        System.out.println("********* BOTÃO 2 = " + getResources().getResourceEntryName(buttonSelect_2));

        do {
            buttonSelect_3 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            //System.out.println("BT3 = " + buttonSelect_3);
        } while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_3)) ||
                getResources().getResourceEntryName(buttonSelect_2).equals(getResources().getResourceEntryName(buttonSelect_3)));

        findViewById(R.id.bt3).setBackgroundResource(buttonSelect_3);
        System.out.println("********* BOTÃO 3 = " + getResources().getResourceEntryName(buttonSelect_3));


        do {
            buttonSelect_4 = varButtonImages[new Random().nextInt(varButtonImages.length)];
            //System.out.println("BT4 = " + buttonSelect_4);
        } while (getResources().getResourceEntryName(buttonSelect_1).equals(getResources().getResourceEntryName(buttonSelect_4)) ||
                getResources().getResourceEntryName(buttonSelect_2).equals(getResources().getResourceEntryName(buttonSelect_4)) ||
                getResources().getResourceEntryName(buttonSelect_3).equals(getResources().getResourceEntryName(buttonSelect_4)));

        findViewById(R.id.bt4).setBackgroundResource(buttonSelect_4);
        System.out.println("********* BOTÃO 4 = " + getResources().getResourceEntryName(buttonSelect_4));
        */
/*
    }

    public void gerarAudio() {

        char letraSom;

        do {
            somSelected = varSons[new Random().nextInt(varSons.length)];
            letraSom = getResources().getResourceEntryName(somSelected).charAt(0);
        } while (!(letras[position1] == letraSom) &&
                !(letras[position2] == letraSom) &&
                !(letras[position3] == letraSom) &&
                !(letras[position4] == letraSom));
        /*
        } while(!getResources().getResourceEntryName(buttonSelect_1).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_2).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_3).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)) &&
                !getResources().getResourceEntryName(buttonSelect_4).substring(13, 14).equals(getResources().getResourceEntryName(somSelected)));
                */
/*
        //System.out.println("********* AUDIO = " + getResources().getResourceEntryName(somSelected));
        player = MediaPlayer.create(LetrasActivity.this, somSelected); // modificado ***
        playerAux = player; // aqui que tem a jogada pra não perder o audio da letra da pergunta
        playAudio(); //-- executa som que pergunta a letra ----------------------------------------
    }

    public void gerarRodada() {
        gerarImagens();

        if(primeiraVezAqui) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gerarAudio();
                }
            }, 800);
            primeiraVezAqui = false;
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gerarAudio();
                }
            }, 300);
        }

    }

    private void ativaBotoes(boolean b) {
        bt1.setClickable(b);
        bt2.setClickable(b);
        bt3.setClickable(b);
        bt4.setClickable(b);
        //bt_audio.setClickable(b);
    }

    public void playAudio() {

        if(player == null) {
            try {
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        player.stop();
                        player.release();
                        //player = null;
                        //player.reset();
                    }
                });
                player.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        } else {
            player.start();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return true;
    }

}
*/

