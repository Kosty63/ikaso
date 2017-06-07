package ru.ikaso63.android.ikaso.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.ikaso63.android.ikaso.model.ListContentItem;

import static ru.ikaso63.android.ikaso.db.ShemaContentDB.CreateBD.CONTENT_TABLE;
import static ru.ikaso63.android.ikaso.db.ShemaContentDB.CreateBD.Column.ID_CONTENT;

/**
 * Created by home on 25.02.2017.
 */

public class JobBd {

        private SQLiteDatabase mDataBase;

        private void connectBD(Context context){
            mDataBase = new ContentBaseHelper(context).getWritableDatabase();
        }



    private static ContentValues setContentValues(String idContent, String title, String preview,
                                                  String body, String kind_id, String type_id){
        ContentValues values = new ContentValues();
        values.put(ShemaContentDB.CreateBD.Column.ID_CONTENT, idContent);
        values.put(ShemaContentDB.CreateBD.Column.TITLE, title);
        values.put(ShemaContentDB.CreateBD.Column.PREVIEW, preview);
        values.put(ShemaContentDB.CreateBD.Column.BODY, body);
        values.put(ShemaContentDB.CreateBD.Column.KIND_ID, kind_id);
        values.put(ShemaContentDB.CreateBD.Column.TYPE_ID, type_id);
        return values;
    }

    public void addNewsBd(Context context, String idContent, String title, String preview,
                          String body, String kind_id, String type_id){
        connectBD(context);
        ContentValues values = setContentValues(idContent, title, preview, body, kind_id, type_id);
        mDataBase.insert(CONTENT_TABLE, null, values);

    }

    public List readContentBd(Context context){
        connectBD(context);
        final Cursor mCursor = mDataBase.query(
                CONTENT_TABLE,
                new String[]{ShemaContentDB.CreateBD.Column.ID_CONTENT, ShemaContentDB.CreateBD.Column.TITLE,
                        ShemaContentDB.CreateBD.Column.PREVIEW, ShemaContentDB.CreateBD.Column.BODY,
                        ShemaContentDB.CreateBD.Column.KIND_ID, ShemaContentDB.CreateBD.Column.TYPE_ID},
                null,
                null,
                null,
                null,
                null
        );
        //mCursor.moveToFirst();

        List list = new ArrayList();
        while (mCursor.moveToNext()){
            ListContentItem list2 = new ListContentItem();
            list2.setId(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.ID_CONTENT)));
            list2.setTitle(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.TITLE)));
            list2.setPreview(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.PREVIEW)));
            list2.setBody(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.BODY)));
            list2.setKindId(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.KIND_ID)));
            list2.setTypeId(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.TYPE_ID)));
            list.add(list2);
        }
        mCursor.close();
        /*list.add(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.TITLE)));
        list.add(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.PREVIEW)));
        list.add(mCursor.getString(mCursor.getColumnIndex(ShemaContentDB.CreateBD.Column.BODY)));*/
        return list;
    }

    public void deleteContentBd(Context context, String id){
        connectBD(context);
            mDataBase.delete(CONTENT_TABLE, ID_CONTENT + "= ?", new String[]{id});
    }
}
