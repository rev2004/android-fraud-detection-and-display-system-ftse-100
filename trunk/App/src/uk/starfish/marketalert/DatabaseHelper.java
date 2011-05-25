package uk.starfish.marketalert;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="alertDB";
	
	static final String alertTable="AlertsTB";
	static final String colID="AlertID";
	static final String colDate="Alert Date-time Stamp";
	static final String colTotalPoints="Total Points";
	static final String colCompany="Company";

	static final String newsTable="NewsTB";
	static final String colNAlertID="AlertID";
	static final String colSource="News Source";
	static final String colNDate="News Date-time Stamp";
	static final String colTitle="News Title";
	static final String colBody="News Body";
	
	static final String rulesTable="RulesTB";
	static final String colRAlertID="AlertID";
	static final String colPoints="Rule Points";
	static final String colRule="Rule Info";
	
	static final String viewEmps="ViewEmps";
	
	public DatabaseHelper(Context context) {
		  super(context, dbName, null,33); 
		  }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE "+alertTable+" ("+colID+ " INTEGER PRIMARY KEY , "
												  +colDate+ " INTEGER NOT NULL, "
												  +colTotalPoints+ "INTEGER NOT NULL, "
												  +colCompany+" TEXT NOT NULL)");
		
		db.execSQL("CREATE TABLE "+newsTable+" ("+colNAlertID+ " INTEGER NOT NULL, "
				  								 +colSource+ " TEXT NOT NULL, "
				  								 +colNDate+ "INTEGER NOT NULL, "
				  								 +colTitle+" TEXT NOT NULL, "
				  								 +colBody+" TEXT NOT NULL," +
				  								 "FOREIGN KEY ("+colNAlertID+") REFERENCES "+alertTable+" ("+colID+")))");
		
		db.execSQL("CREATE TABLE "+rulesTable+" ("+colRAlertID+ " INTEGER NOT NULL , "
												 +colPoints+ " INTEGER NOT NULL, "
												 +colRule+ " TEXT NOT NULL, "+
				  								 "FOREIGN KEY ("+colNAlertID+") REFERENCES "+alertTable+" ("+colID+")))");
		  
		db.execSQL("CREATE TRIGGER fk_empalert_nalertid " +
				" BEFORE INSERT "+
				" ON "+newsTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colID+" FROM"+alertTable+
				" WHERE "+colID+"=new."+colNAlertID+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
				
		db.execSQL("CREATE TRIGGER fk_empalert_ralertid " +
				" BEFORE INSERT "+
				" ON "+rulesTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colID+" FROM"+alertTable+
				" WHERE "+colID+"=new."+colRAlertID+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public void onInsert () {}
	public void onDelete () {}
}