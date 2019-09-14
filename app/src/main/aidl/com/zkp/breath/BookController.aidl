// BookController.aidl
package com.zkp.breath;
import com.zkp.breath.Book;


interface BookController {
    List<Book> getBookList();

    void addBookInOut(inout Book book);
}
