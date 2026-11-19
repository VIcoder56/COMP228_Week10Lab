package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationDAO {
    public void saveApplication(Application application) throws SQLException {
        String sql = """
                INSERT INTO employmentApplications
                (fullname, address, phone, email, educationalAttachment, gender,\s
                dateAvailable, desiredPosition, desiredSalary, isLegal, isRelative, explanation)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, application.getFullname());
            statement.setString(2, application.getAddress());
            statement.setString(3, application.getPhone());
            statement.setString(4, application.getEmail());
            statement.setString(5, application.getEducationAttachment());
            statement.setString(6, application.getGender());
            statement.setObject(7, application.getDateAvailable());
            statement.setString(8, application.getPosition());
            statement.setString(9, application.getSalary());
            statement.setBoolean(10, application.getLegal());
            statement.setBoolean(11, application.getRelative());
            statement.setString(12, application.getExplanation());
            statement.executeUpdate();
        }
    }
}
