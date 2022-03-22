package service;

import java.sql.Connection;

public class EditingPublishingService {

    ConnectionHelper connectionHelper = new ConnectionHelper();

    public void run() throws Exception{
        try(final Connection connection = connectionHelper.getConnection()) {
            System.out.println(connection.isValid(100));
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }
}
