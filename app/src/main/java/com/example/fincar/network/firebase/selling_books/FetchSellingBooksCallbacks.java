package com.example.fincar.network.firebase.selling_books;

import com.example.fincar.models.book.SellingBook;
import com.example.fincar.network.Error;

import java.util.List;

public interface FetchSellingBooksCallbacks extends Error {
    void getSellingBooks(List<SellingBook> books);
}
