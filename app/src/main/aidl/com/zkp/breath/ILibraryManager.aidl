package com.zkp.breath;
import com.zkp.breath.Book;

// 暴漏给客户端的接口，创建完毕后要先build
interface ILibraryManager {
   // 近期新书查询
  List<String> getNewBookList();
   // 图书捐赠
   void donateBook(in String book);

   void testCustomDataType(inout Book book);
}
