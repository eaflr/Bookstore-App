package projects.bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDetails {

    // Inserts a book into the database with image path
    public void addBook(String title, String author, String isbn, double price, int stock, String imagePath) throws Exception {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO book_details (title, author, isbn, price, stock, image_path) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, isbn);
            statement.setDouble(4, price);
            statement.setInt(5, stock);
            statement.setString(6, imagePath); 
            statement.executeUpdate(); 
        }
    }

    // Updates a book in the database, including image path
    public void updateBook(String title, String author, String isbn, double price, int stock, String imagePath) throws Exception {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "UPDATE book_details SET title = ?, author = ?, price = ?, stock = ?, image_path = ? WHERE isbn = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setDouble(3, price);
            statement.setInt(4, stock);
            statement.setString(5, imagePath); 
            statement.setString(6, isbn); 
            statement.executeUpdate(); 
        }
    }

    // Deletes a book from the database
    public void deleteBook(String isbn) throws Exception {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM book_details WHERE isbn = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.executeUpdate(); // Execute the delete command

        }
    }

    // Fetches all books from the database
    public List<String[]> getAllBooks() throws Exception {
        List<String[]> books = new ArrayList<>(); // List to hold all the books
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "SELECT title, author, isbn, price, stock FROM book_details"; // SQL query to fetch all books
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(); // Execute the query and get results

            // Loop through the results and create Book objects
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                // Create a new Book object and add it to the list
                books.add(new String[]{title, author, isbn, String.valueOf(price), String.valueOf(stock)});
            }
        }
        return books; // Return the list of books
    }

    //Searches for book based on isbn, title or author
    public List<String[]> searchBooks(String searchText) throws Exception {
        List<String[]> books = new ArrayList<>();

        // SQL query to search by title, author, or ISBN
        String sql = "SELECT title, author, isbn, price, stock FROM book_details "
                + "WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            // Prepare the search term for the LIKE clause
            String searchPattern = "%" + searchText + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            ResultSet resultSet = statement.executeQuery();

            // Add each found book to the list
            while (resultSet.next()) {
                books.add(new String[]{
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("isbn"),
                    String.valueOf(resultSet.getDouble("price")),
                    String.valueOf(resultSet.getInt("stock"))
                });
            }
        }
        return books;
    }

    //Method to retrieve books that are out of stock
    public List<String[]> getOutOfStockBooks() throws Exception {
        List<String[]> outOfStockBooks = new ArrayList<>();

        // Establish database connection
        try (Connection conn = DatabaseConnector.getConnection()) {
            // Query to select only title, author, and ISBN where stock is 0
            String sql = "SELECT title, author, isbn FROM book_details WHERE stock = 0";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and add each book's details to the list
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");

                // Add book details as a new array of Strings
                outOfStockBooks.add(new String[]{title, author, isbn});
            }
        }
        return outOfStockBooks; // Return the list of out-of-stock books
    }

}
