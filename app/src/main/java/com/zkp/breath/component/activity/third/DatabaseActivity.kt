package com.zkp.breath.component.activity.third

import androidx.appcompat.app.AppCompatActivity

/**
 * GreenDao例子
 * https://www.jianshu.com/p/53083f782ea2
 */
class DatabaseActivity : AppCompatActivity() {

//    var recyclerView: RecyclerView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_databse)
//        initView()
//        initData()
//    }
//
//    private fun initData() {
//        val greenDaoManager = GreenDaoManager()
//        greenDaoManager.initGreenDao(this)
//        greenDaoManager.getStudentDao()
//
//        val arrayListOf = arrayListOf("insert", "insertOrReplace", "deleteData",
//                "deleteAll", "updateData", "queryAll", "queryData")
//        val dataBaseAdapter = DataBaseAdapter(arrayListOf)
//        dataBaseAdapter.setOnItemClickListener { adapter, view, position ->
//            when (arrayListOf[position]) {
//                "insert" -> {
//                    greenDaoManager.insert()
//                    ToastUtils.showShort("insert")
//                }
//                "insertOrReplace" -> {
//                    greenDaoManager.insertOrReplace()
//                    ToastUtils.showShort("insertOrReplace")
//                }
//                "deleteData" -> {
//                    val student = Student()
//                    student.id = 21L
//                    greenDaoManager.deleteData(student)
//                    ToastUtils.showShort("deleteData")
//                }
//                "deleteAll" -> {
//                    greenDaoManager.deleteAll()
//                    ToastUtils.showShort("deleteAll")
//                }
//                "updateData" -> {
//                    val student = Student()
//                    student.id = 30
//                    student.name = "更新名"
//                    greenDaoManager.updateData(student)
//                    ToastUtils.showShort("updateData")
//                }
//                "queryAll" -> {
//                    val queryAll = greenDaoManager.queryAll()
//                    ToastUtils.showShort("queryAll")
//                }
//                "queryData" -> {
//                    val queryAll = greenDaoManager.queryAll()
//                    if (queryAll.isEmpty()) return@setOnItemClickListener
//                    val stu = queryAll[0]
//                    val queryData = greenDaoManager.queryData(stu.id.toString())
//                    queryData.forEach {
//                        println("打印结果:$it")
//                        ToastUtils.showShort("queryData")
//                    }
//                }
//            }
//        }
//        recyclerView?.adapter = dataBaseAdapter
//    }
//
//    private fun initView() {
//        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView?.setHasFixedSize(true)
//        recyclerView?.overScrollMode = View.OVER_SCROLL_NEVER
//        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//    }

}