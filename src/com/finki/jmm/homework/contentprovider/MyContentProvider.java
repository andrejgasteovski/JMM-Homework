package com.finki.jmm.homework.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.finki.jmm.homework.database.MySQLiteHelper;

public class MyContentProvider extends ContentProvider{

	//URI for accessing the Content Provider using a Content Resolver
	public static final Uri CONTENT_URI = Uri.parse("content://com.finki.jmm.homework.contentprovider/elements");	

	//Constants used to distinguish between different URI requests
	public static final int ALL_ROWS = 1;
	public static final int SINGLE_ROW = 2;
	
	//URI mathcer do distinguish between different URI requests
	private static final UriMatcher uriMatcher;
	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.finki.jmm.homework.contentprovider", "elements", ALL_ROWS);
		uriMatcher.addURI("com.finki.jmm.homework.contentprovider", "elements/#", SINGLE_ROW);
	}
	
	private MySQLiteHelper dbHelper;	
	
	@Override
	public boolean onCreate() {
		//Database construction
		dbHelper = new MySQLiteHelper(getContext());
		
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteDatabase db;
		
		try{
			db = dbHelper.getWritableDatabase();
		}catch(SQLiteException e){
			db = dbHelper.getReadableDatabase();
		}
		
		//query builder to simplify query construction
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		switch(uriMatcher.match(uri)){
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(MySQLiteHelper.COLUMN_ID + " = " + rowID);
		default: break;
		}
		
		//set the table on which to perform the request
		queryBuilder.setTables(MySQLiteHelper.TABLE_STUDENTS);
		
		//execute the query
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		
		return cursor;
	}
	
	@Override
	public String getType(Uri uri) {
		// Return a string that identifies the MIME type
		// for a Content Provider URI
		switch (uriMatcher.match(uri)) {
			case ALL_ROWS: 
				return "vnd.android.cursor.dir/vnd.paad.elemental";
			case SINGLE_ROW: 
				return "vnd.android.cursor.item/vnd.paad.elemental";
			default: 
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		switch(uriMatcher.match(uri)){
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = MySQLiteHelper.COLUMN_ID + " = " + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default: break;
		}
		
		if(selection == null){
			selection = "1";
		}
		
		int deleteCount = db.delete(MySQLiteHelper.TABLE_STUDENTS, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		String nullColumnHack = null;
		
		long id = db.insert(MySQLiteHelper.TABLE_STUDENTS, nullColumnHack, values);
		
		//Construct and return the URI of the newly inserted row
		if(id > -1){
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		}
		else{
			return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		switch(uriMatcher.match(uri)){
		case SINGLE_ROW:
			String rowId = uri.getPathSegments().get(1);
			selection = MySQLiteHelper.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default: break;
		}
		
		int updateCount = db.update(MySQLiteHelper.TABLE_STUDENTS, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

}
