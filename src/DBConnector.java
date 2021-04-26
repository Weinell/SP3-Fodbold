import java.sql.*;


public class DBConnector implements IO {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/SP4_gruppe_H";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "********";


    @Override
    public void teamSave(String filepath) {

        Connection conn = null;
        // Statement stmt = null;
        // for insert a new candidate
        ResultSet rs = null;

        //Insert/upsert
        String sql = "INSERT INTO Teams( id, teamName, teamGoals, teamPoints) "
                + "VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE id=?, teamName=?, teamGoals=?, teamPoints=?";

        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //STEP 2: Execute a query
            System.out.println("Creating statement...");
            //stmt = conn.createStatement();

            for(int i = 1; i < Controller.teams.size();i++){

                pstmt.setInt(1,Controller.getTeamByID(i).getTeamID());
                pstmt.setString(2,Controller.getTeamByID(i).getTeamName());
                pstmt.setInt(3,Controller.getTeamByID(i).getTeamGoals());
                pstmt.setInt(4,Controller.getTeamByID(i).getTeamPoints());
                // Update
                pstmt.setInt(5,Controller.getTeamByID(i).getTeamID());
                pstmt.setString(6,Controller.getTeamByID(i).getTeamName());
                pstmt.setInt(7,Controller.getTeamByID(i).getTeamGoals());
                pstmt.setInt(8,Controller.getTeamByID(i).getTeamPoints());

                pstmt.addBatch();

            }
            pstmt.executeBatch();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void matchSave(String filepath) {

    }

}
