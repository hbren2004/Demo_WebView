package com.ebusiness.database;

import java.io.File;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ebusiness.entity.UserBean;
import com.ebusiness.utils.Constants;

public class DatabaseOperation{

	private static DatabaseOperation dbo = null;
	private SQLiteDatabase db    = null;
	private DatabaseOperation(final Context context)throws Exception{
		openDatabase(context);
	}
	/**
	 * ��ȡDB����ʵ��
	 * @return DatabaseOperation
	 */
	public static synchronized DatabaseOperation getInstance(final Context context)throws Exception{
		if(dbo == null){
			dbo = new DatabaseOperation(context);
		}
		return dbo;
	}
	/**
	 * �����ݿ⣬���������򴴽����
	 * @param context
	 */
	private void openDatabase(final Context context)throws Exception{
		String databaseFilename = Constants._sys._appDir+Constants._sys._dbDir+Constants._sys._dbFile;
		File dir = new File(Constants._sys._appDir+Constants._sys._dbDir);
		if(!dir.exists()) dir.mkdirs();
		db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		Log.i("Debug", "open database Success!");
	}
	
	/**
	 * ͨ��SQL������
	 * @param sql
	 */
	public void createBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * ͨ��SQL�������¼
	 * @param sql
	 */
	public void insertBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * ͨ������ʵ�弯�ϲ����¼
	 * @param table
	 * @param list
	 */
	public void insertData(String tableName,List<UserBean> list)throws Exception{
		for(UserBean bean : list){
			ContentValues cv = new ContentValues();
			cv.put("userName", bean.getUserName());
			db.insert(tableName,null,cv);
		}
	}
	
	/**
	 * ͨ��SQL���¼�¼
	 * @param sql
	 */
	public void updateBySql(String sql)throws Exception{
		db.execSQL(sql);
	}

	/**
	 * ͨ��SQLɾ����¼
	 * @param sql
	 */
	public void deleteBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * ͨ������ɾ����¼
	 * @param table
	 * @param fields  
	 * @param value
	 */
	public boolean deleteData(String tableName,String[] fields,String[] values)throws Exception{
		String whereExp = dealParamArray(fields,values);
		if(null!=whereExp && !"".equals(whereExp)){
			db.delete(tableName,whereExp,values);
			return true;
		}
		return false;
	}

	/**
	 * ������ݱ�
	 * @param tableName
	 */
	public void dropTable(String tableName)throws Exception{
		if(tableName == null || "".equals(tableName.trim())){
			return;
		}
		db.execSQL("drop table "+tableName+";");
	}
	
	/**
	 * ���ݸ��������жϼ�¼�Ƿ����
	 * @param name
	 * @return
	 */
	public boolean isExistByWhereExp(String tableName,String[] fields,String[] values)throws Exception{
		String whereExp = dealParamArray(fields,values);
		Cursor cursor= db.rawQuery("select * from "+tableName+" where "+whereExp,values);
		if(cursor.moveToFirst()){
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * ���������Ĳ���
	 * @param fields
	 * @param values
	 * @return
	 */
	private String dealParamArray(String[] fields,String[] values){
		String whereExp = "";
		if(fields.length!=values.length){
			Log.e("Debug","��������:�ֶ�����ֵ����ƥ��!");
			return null;
		}
		for(int i=0;i<fields.length;i++){
			whereExp = whereExp + fields[i] +"=?";
			if(i<fields.length-1){
				whereExp = whereExp + " and ";
			}
		}
		return whereExp;
	}

}
