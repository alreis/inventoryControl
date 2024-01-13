

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private int amount;
      
    // Construtores, getters e setters

    public Product() {}

    public Product(String name, int amount) {
      this.name = name;
      this.amount = amount;
    }

      // Getters and setters
        
    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAmount() {
      return amount;
    }
    
    public void setAmount(int amount) {
      this.amount = amount;
    }
    
    // Métodos de interação com o banco de dados

    public void addProduct() {
      String sql = "INSERT INTO products (name, amount) VALUES (?, ?)";
      try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  
          preparedStatement.setString(1, name);
          preparedStatement.setInt(2, amount);
          preparedStatement.executeUpdate();
  
          try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
              if (generatedKeys.next()) {
                  id = generatedKeys.getInt(1);
              } else {
                  throw new SQLException("Failed to get the generated ID");
              }
          }
      } catch (SQLException e) {
          throw new DatabaseException("Error adding product", e);
      }
  }
  

    public void updateInventory() {
        String sql = "UPDATE products SET amount = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating inventory", e);
        }
    }

    public static List<Product> getReport() {
      List<Product> products = new ArrayList<>();
      String sql = "SELECT * FROM products";
      try (Connection connection = DatabaseConnection.getConnection();
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery(sql)) {

        while (resultSet.next()) {
          Product product = new Product();
          product.setId(resultSet.getInt("id"));
          product.setName(resultSet.getString("name"));
          product.setAmount(resultSet.getInt("amount"));
          products.add(product);
        }

      } catch (SQLException e) {
        throw new DatabaseException("Error getting report", e);
      }

      return products;
    }
    
    
}
