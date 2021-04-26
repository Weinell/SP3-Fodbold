import java.sql.*;
import java.util.ArrayList;


public class DBConnector implements IO {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/SP4_gruppe_H";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "niko3460";


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
            System.out.println("Saving Team Data...");
            //stmt = conn.createStatement();

            for(int i = 1; i <= Controller.teams.size();i++){

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

    @Override
    public ArrayList<Team> readTeamData(String path) {
        ArrayList<Team> TeamList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            // Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Loading Team Data...");
            stmt = conn.createStatement();


            String sql = "SELECT * FROM Teams";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String teamName = rs.getString("teamName");
                int teamGoals = rs.getInt("teamGoals");
                int teamPoints = rs.getInt("teamPoints");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Team Name: " + teamName);
                System.out.print(", Goals: " + teamGoals);
                System.out.println(", Points: " + teamPoints);

                Team t = new Team(id, teamName, teamGoals, teamPoints);
                TeamList.add(t);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try


        return TeamList;
    }

    @Override
    public void playerSave(String filepath) {

        Connection conn = null;
        // Statement stmt = null;
        // for insert a new candidate
        ResultSet rs = null;

        //Insert/upsert
        String sql = "INSERT INTO Teams( id, team_id, playerName) "
                + "VALUES(?,?,?) ON DUPLICATE KEY UPDATE id=?, team_id=?, playerName=?";

        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //STEP 2: Execute a query
            System.out.println("Saving Player Data...");
            //stmt = conn.createStatement();


            for(int i = 1; i <= Controller.players.size();i++){

                pstmt.setInt(1,Controller.getPlayerByID(i).getPlayerID());
                pstmt.setInt(2,0);
                pstmt.setString(3,Controller.getPlayerByID(i).getPlayerName());

                // Update

                pstmt.setInt(4,Controller.getPlayerByID(i).getPlayerID());
                pstmt.setInt(5,0);
                pstmt.setString(6,Controller.getPlayerByID(i).getPlayerName());

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


    /*
    @Override
    public ArrayList<Player> readPlayerData(String path)    {





    }*/



}
