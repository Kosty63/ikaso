package ru.ikaso63.android.ikaso.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ikaso63.android.ikaso.db.ShemaContentDB.CreateBD;

import static ru.ikaso63.android.ikaso.db.ShemaContentDB.CreateBD.CONTENT_TABLE;

/**
 * Created by home on 05.01.2017.
 */

public class ContentBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATA_BASE = "contentBase.db";

    public ContentBaseHelper(Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase base) {
        base.execSQL("create table " + CONTENT_TABLE + "(" +
                "_id integer primary key autoincrement, " +
                CreateBD.Column.ID_CONTENT + ", " +
                CreateBD.Column.TITLE + ", " +
                CreateBD.Column.PREVIEW + ", " +
                CreateBD.Column.BODY + ", " +
                CreateBD.Column.KIND_ID + ", " +
                CreateBD.Column.TYPE_ID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CONTENT_TABLE);
        this.onCreate(sqLiteDatabase);
    }
}
