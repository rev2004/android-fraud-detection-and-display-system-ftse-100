package uk.starfish.marketalert;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="alertDB";
	
	static final String alertTable="Alert";
	static final String alertID="_id";
	static final String alertTime="time";
	static final String alertPoints="points";
	static final String alertCompany="company";

	static final String newsTable="News";
	static final String newsID="_id";
	static final String newsAlertID="alertID";
	static final String newsSource="source";
	static final String newsTime="time";
	static final String newsTitle="title";
	static final String newsBody="body";
	
	static final String rulesTable="Rules";
	static final String rulesAlertID="alertID";
	static final String rulesPoints="points";
	static final String rulesName="name";
	
	static final String viewEmps="ViewEmps";
	
	public DatabaseHelper(Context context) {
		  super(context, dbName, null, 33); 
		  }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE "+alertTable+" ("+alertID+ " INTEGER PRIMARY KEY , "
												  +alertTime+ " INTEGER NOT NULL, "
												  +alertPoints+ "INTEGER NOT NULL, "
												  +alertCompany+" TEXT NOT NULL)");
		
		db.execSQL("CREATE TABLE "+newsTable+" ("+newsID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				  								 +newsSource+ " TEXT NOT NULL, "
				  								 +newsTime+ "INTEGER NOT NULL, "
				  								 +newsTitle+" TEXT NOT NULL, "
				  								 +newsBody+" TEXT NOT NULL," +
				  								 "FOREIGN KEY ("+newsAlertID+") REFERENCES "+alertTable+" ("+alertID+")))");
		
		db.execSQL("CREATE TABLE "+rulesTable+" ("+rulesID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
												 +rulesPoints+ " INTEGER NOT NULL, "
												 +rulesName+ " TEXT NOT NULL, "+
				  								 "FOREIGN KEY ("+rulesAlertID+") REFERENCES "+alertTable+" ("+alertID+")))");
		  
		db.execSQL("CREATE TRIGGER fk_empalert_nalertid " +
				" BEFORE INSERT "+
				" ON "+newsTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+alertID+" FROM"+alertTable+
				" WHERE "+alertID+"=new."+newsAlertID+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
				
		db.execSQL("CREATE TRIGGER fk_empalert_ralertid " +
				" BEFORE INSERT "+
				" ON "+rulesTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+alertID+" FROM"+alertTable+
				" WHERE "+alertID+"=new."+rulesAlertID+" ) IS NULL)"+
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
