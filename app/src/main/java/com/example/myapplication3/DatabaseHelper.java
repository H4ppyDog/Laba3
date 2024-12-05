package  com.example.myapplication3;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Odnogruppniki";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIO = "FIO";
    private static final String COLUMN_TIMESTAMP = "AddedTime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIO + " TEXT, " +
                COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);

        resetTable(db);
    }

    public void resetTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_NAME);
        insertInitialRecords(db);
    }

    private void insertInitialRecords(SQLiteDatabase db) {
        String[] students = {
                "Желтиков Глеб Евгеньевич",
                "Нежелтиков Глеб Евгеньевич",
                "Ещежелтиков Глеб Евгеньевич",
                "Очереднойжелтиков Глеб Евгеньевич",
                "Последнийжелтиков Глеб Евгеньевич"
        };
        for (String student : students) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIO, student);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public long addRecord(String fio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIO, fio);
        return db.insert(TABLE_NAME, null, values);
    }

    public int updateLastRecord(String newFio) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            int lastId = cursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIO, newFio);
            cursor.close();
            return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(lastId)});
        }
        cursor.close();
        return -1;
    }

    public Cursor getAllRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
