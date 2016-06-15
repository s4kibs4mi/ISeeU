package net.codersgarage.iseeu.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.codersgarage.iseeu.models.Settings;

/**
 * Created by s4kib on 6/15/16.
 */

public class SettingsProvider extends SQLiteOpenHelper {
    private static String DB_NAME = "iSeeUSettings";
    private static int DB_VERSION = 1;

    private String TABLE_CREATE_QUERY = "CREATE TABLE settings(id INTEGER PRIMARY KEY,host TEXT, port INT, autoLogin INT);";

    public SettingsProvider(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_QUERY);
    }

    public void addSettings(Settings settings) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("host", settings.getHost());
        values.put("port", settings.getPort());
        values.put("autoLogin", settings.isAutoLogin() ? 1 : 0);
        db.update("settings", values, null, null);
        db.insertWithOnConflict("settings", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public Settings getSettings() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM settings WHERE id=1;", null);
        if (cursor.moveToFirst()) {
            Settings settings = new Settings();
            settings.setHost(cursor.getString(cursor.getColumnIndex("host")));
            settings.setPort(cursor.getInt(cursor.getColumnIndex("port")));
            settings.setAutoLogin(cursor.getInt(cursor.getColumnIndex("autoLogin")) == 1 ? true : false);
            return settings;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
