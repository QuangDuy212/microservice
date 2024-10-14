package com.quangduy.bookservice.query.projection;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.quangduy.bookservice.command.data.Book;
import com.quangduy.bookservice.command.data.BookRepository;
import com.quangduy.bookservice.query.model.BookResponseModel;
import com.quangduy.bookservice.query.queries.GetAllBooksQuery;
import com.quangduy.bookservice.query.queries.GetBookQuery;
import com.quangduy.commonservice.model.BookResponseCommonModel;
import com.quangduy.commonservice.query.GetDetailsBookQuery;

@Component
public class BookProjection {
    private BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) {
        BookResponseModel model = new BookResponseModel();
        Book book = this.bookRepository.getById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
        List<Book> listEntity = this.bookRepository.findAll();
        List<BookResponseModel> listBook = listEntity.stream().map(x -> {
            BookResponseModel res = new BookResponseModel();
            BeanUtils.copyProperties(x, res);
            return res;
        }).collect(Collectors.toList());
        return listBook;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = this.bookRepository.getById(getDetailsBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }
}
