package com.zkp.breath.database.greendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zkp.breath.database.greendao.DaoMaster;
import com.zkp.breath.database.greendao.DaoSession;
import com.zkp.breath.database.greendao.StudentDao;
import com.zkp.breath.database.greendao.entity.Student;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

public class GreenDaoManager {

    private DaoSession daoSession;
    private StudentDao studentDao;

    public void initGreenDao(Context context) {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "student.db");
        CusDevOpenHelper helper = new CusDevOpenHelper(context, "student.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void getStudentDao() {
        if (daoSession == null) {
            return;
        }
        studentDao = daoSession.getStudentDao();
    }

    /**
     * android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: STUDENT.STUDENT_NO (code 2067 SQLITE_CONSTRAINT_UNIQUE)
     * studentNo的注解为Unique，插入时在表中已经存在就会报上面的异常
     */
    public void insert() {
        Random mRandom = new Random();
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setStudentNo(i);
            int age = mRandom.nextInt(10) + 10;
            student.setAge(age);
            student.setTelPhone("电话" + i);
            student.setName("姓名" + i);
            student.setSex(i % 2 == 0 ? "男" : "女");
            student.setAddress("地址" + i);
            student.setGrade(age % 10 + "年纪");
            student.setSchoolName("学校" + i);
            studentDao.insert(student);
        }
    }

    public void insertOrReplace() {
        Random mRandom = new Random();
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setStudentNo(i);
            int age = mRandom.nextInt(10) + 10;
            student.setAge(age);
            student.setTelPhone("电话" + i);
            student.setName("姓名" + i);
            student.setSex("不男不女");
            student.setAddress("地址" + i);
            student.setGrade(age % 10 + "年纪");
            student.setSchoolName("学校" + i);
            studentDao.insertOrReplace(student);
        }
    }

    public void deleteData(Student s) {
        studentDao.delete(s);
    }

    public void deleteAll() {
        studentDao.deleteAll();
    }

    public void updateData(Student s) {
        studentDao.update(s);
    }

    public List<Student> queryAll() {
        return studentDao.loadAll();
    }

    public List<Student> queryData(String id) {
        return studentDao.queryRaw("where _id = ?", id);
    }

    public List<Student> queryList() {
        QueryBuilder<Student> studentQueryBuilder = studentDao.queryBuilder();
        return studentQueryBuilder.list();
    }

    /**
     * 如果
     */
    public List<Student> queryWhere() {
        QueryBuilder<Student> qb = studentDao.queryBuilder();
        Query<Student> query = qb.where(StudentDao.Properties.Sex.eq("男")).orderAsc(StudentDao.Properties.Id).build();
        return query.list();
    }

    public boolean deleteItem() {
        QueryBuilder<Student> where = studentDao.queryBuilder().where(StudentDao.Properties.Id.gt(5));
        // 构建删除器
        DeleteQuery<Student> deleteQuery = where.buildDelete();
        // 执行删除
        deleteQuery.executeDeleteWithoutDetachingEntities();
        return false;
    }

}
