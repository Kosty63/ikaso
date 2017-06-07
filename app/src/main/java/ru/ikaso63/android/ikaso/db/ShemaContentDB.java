package ru.ikaso63.android.ikaso.db;

/**
 * Created by home on 05.01.2017.
 */

public class ShemaContentDB {

    public static final class CreateBD{

        public static final String CONTENT_TABLE = "content";

        public static final class Column{
            public static final String ID_CONTENT = "id_content";
            public static final String TITLE = "title";
            public static final String PREVIEW = "preview";
            public static final String BODY = "body";
            public static final String KIND_ID = "kind_id";
            public static final String TYPE_ID = "type_id";
        }
    }
}
