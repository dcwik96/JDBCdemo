package com.example.jdbcdemo.service;

import com.example.jdbcdemo.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManagerJDBC implements BookManager {

    private Connection connection;
    private String url = "jdbc:hsqldb:hsql://localhost/workdb";

    private String createTableBook = "CREATE TABLE Book(id BIGINT GENERATED BY DEFAULT AS IDENTITY, title VARCHAR(30) NOT NULL UNIQUE, author VARCHAR(40) NOT NULL, price REAL NOT NULL)";

    private PreparedStatement addBookStmt;
    private PreparedStatement deleteAllBookStmt;
    private PreparedStatement deleteBookStmt;
    private PreparedStatement updateBookStmt;
    private PreparedStatement getAllBookStmt;
    private PreparedStatement findBookByIdStmt;
    private PreparedStatement findBookByTitleStmt;
    private PreparedStatement findBookByAuthorStmt;
    private PreparedStatement findBookByPriceStmt;

    private Statement statement;

    public BookManagerJDBC() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("Book".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableBook);

            addBookStmt = connection.prepareStatement("INSERT INTO Book (title, author, price) VALUES (?, ?, ?)");
            deleteAllBookStmt = connection.prepareStatement("DELETE FROM Book");
            deleteBookStmt = connection.prepareStatement("DELETE FROM Book WHERE id = ?");
            updateBookStmt = connection.prepareStatement("UPDATE Book SET title = ?, author = ?, price = ? WHERE title = ? AND author = ? AND price = ?");
            getAllBookStmt = connection.prepareStatement("SELECT id, title, author, price FROM Book");
            findBookByIdStmt = connection.prepareStatement("SELECT id, title, author, price FROM Book WHERE id=?");
            findBookByTitleStmt = connection.prepareStatement("SELECT id, title, author, price FROM Book WHERE title = ?");
            findBookByAuthorStmt = connection.prepareStatement("SELECT id, title, author, price FROM Book WHERE author = ?");
            findBookByPriceStmt = connection.prepareStatement("SELECT id, title, author, price FROM Book WHERE price = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        return connection;
    }

    @Override
    public int addBook(Book book) {
        int count = 0;
        try {
            addBookStmt.setString(1, book.getTitle());
            addBookStmt.setString(2, book.getAuthor());
            addBookStmt.setDouble(3, book.getPrice());

            count = addBookStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteBookById(Long id) {
        int count = 0;
        try {
            deleteBookStmt.setLong(1, id);

            count = deleteBookStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void deleteAllBooks() {
        try {
            deleteAllBookStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateWholeBook(Book book, String title, String author, double price) {
        int count = 0;

        try {
            updateBookStmt.setString(1, title);
            updateBookStmt.setString(2, author);
            updateBookStmt.setDouble(3, price);
            updateBookStmt.setString(4, book.getTitle());
            updateBookStmt.setString(5, book.getAuthor());
            updateBookStmt.setDouble(6, book.getPrice());

            count = updateBookStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public int updateBookTitle(Book book, String title) {
        return updateWholeBook(book, title, book.getAuthor(), book.getPrice());
    }

    @Override
    public int updateBookAuthor(Book book, String author) {
        return updateWholeBook(book, book.getTitle(), author, book.getPrice());
    }

    @Override
    public int updateBookPrice(Book book, double price) {
        return updateWholeBook(book, book.getTitle(), book.getAuthor(), price);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();

        try {
            ResultSet rs = getAllBookStmt.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                books.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<Book>();

        try {
            findBookByTitleStmt.setString(1, title);

            ResultSet rs = findBookByTitleStmt.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<Book>();

        try {
            findBookByAuthorStmt.setString(1, author);

            ResultSet rs = findBookByAuthorStmt.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book findById(long id) {
        Book b = new Book();


        try {
            findBookByIdStmt.setLong(1, id);
            ResultSet rs = findBookByIdStmt.executeQuery();
            if (rs.next()) {
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public List<Book> findByPrice(double price) {
        List<Book> books = new ArrayList<Book>();

        try {
            findBookByPriceStmt.setDouble(1, price);

            ResultSet rs = findBookByPriceStmt.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void addAllBooks(List<Book> books) {

        try {
            for (Book b : books) {
                connection.setAutoCommit(false);

                addBookStmt.setString(1, b.getTitle());
                addBookStmt.setString(2, b.getAuthor());
                addBookStmt.setDouble(3, b.getPrice());

                addBookStmt.executeUpdate();
            }
            connection.commit();
        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public int deleteSelectedBooks(List<Long> ids) {
        int count = 0;
        try {
            for (Long id : ids) {
                connection.setAutoCommit(false);

                deleteBookStmt.setLong(1, id);
                count = count + deleteBookStmt.executeUpdate();
            }
            if (count != ids.size()) {
                connection.rollback();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int updateSelectedBooks(List<Book> books) {
        int count = 0;
        Book temp;
        try {
            connection.setAutoCommit(false);
            for (Book b : books) {
                if (findById(b.getId()).getTitle() == null) {
                    throw new SQLException("No book in db!");
                } else {
                    temp = findById(b.getId());
                    updateBookStmt.setString(1, b.getTitle());
                    updateBookStmt.setString(2, b.getAuthor());
                    updateBookStmt.setDouble(3, b.getPrice());
                    updateBookStmt.setString(4, temp.getTitle());
                    updateBookStmt.setString(5, temp.getAuthor());
                    updateBookStmt.setDouble(6, temp.getPrice());

                    count = count + updateBookStmt.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return count;
    }
}
