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
	 * 获取DB管理实例
	 * @return DatabaseOperation
	 */
	public static synchronized DatabaseOperation getInstance(final Context context)throws Exception{
		if(dbo == null){
			dbo = new DatabaseOperation(context);
		}
		return dbo;
	}
	/**
	 * 打开数据库，若不存在则创建后打开
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
	 * 通过SQL创建表
	 * @param sql
	 */
	public void createBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * 通过SQL语句插入记录
	 * @param sql
	 */
	public void insertBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * 通过给定实体集合插入记录
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
	 * 通过SQL更新记录
	 * @param sql
	 */
	public void updateBySql(String sql)throws Exception{
		db.execSQL(sql);
	}

	/**
	 * 通过SQL删除记录
	 * @param sql
	 */
	public void deleteBySql(String sql)throws Exception{
		db.execSQL(sql);
	}
	
	/**
	 * 通过条件删除记录
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
	 * 清空数据表
	 * @param tableName
	 */
	public void dropTable(String tableName)throws Exception{
		if(tableName == null || "".equals(tableName.trim())){
			return;
		}
		db.execSQL("drop table "+tableName+";");
	}
	
	/**
	 * 根据给定条件判断记录是否存在
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
	 * 处理方法带的参数
	 * @param fields
	 * @param values
	 * @return
	 */
	private String dealParamArray(String[] fields,String[] values){
		String whereExp = "";
		if(fields.length!=values.length){
			Log.e("Debug","参数有误:字段数与值数不匹配!");
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
