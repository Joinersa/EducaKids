package br.com.projetoslabex.educakids.bdcore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joiner on 03/09/16.
 */
public class BDCore extends SQLiteOpenHelper {

    private static final String NOME_BD = "ranking";
    private static final int VERSAO_BD = 1;

    public BDCore(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE `pontuacao` (" +
                "`_id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`player` TEXT NOT NULL," +
                "`pontos` INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table pontuacao");
        onCreate(db);
    }

}
