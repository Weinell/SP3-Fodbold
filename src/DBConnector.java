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
        String sql = "INSERT INTO Players(id, team_id, playerName) "
                + "VALUES(?,?,?) ON DUPLICATE KEY UPDATE id=?, team_id=?, playerName=?";

        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //STEP 2: Execute a query
            System.out.println("Saving Player Data...");
            //stmt = conn.createStatement();


            for(int i = 1; i <= Controller.players.size();i++){

                pstmt.setInt(1, Controller.getPlayerByID(i).getPlayerID());
                pstmt.setInt(2,Controller.getTeamIDbyPlayer(i));
                pstmt.setString(3,Controller.getPlayerByID(i).getPlayerName());

                // Update

                pstmt.setInt(4, Controller.getPlayerByID(i).getPlayerID());
                pstmt.setInt(5,Controller.getTeamIDbyPlayer(i));
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


    public ArrayList<Player> readPlayerData() {
        ArrayList<Player> PlayerList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            // Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Loading Player Data...");
            stmt = conn.createStatement();


            String sql = "SELECT * FROM Players";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                int team_id = rs.getInt("team_id");
                String playerName = rs.getString("playerName");


                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Team ID: " + team_id);
                System.out.println(", Player name: " + playerName);


                Player p = new Player(id, team_id, playerName);
                PlayerList.add(p);


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


        return PlayerList;
    }


    @Override
    public void matchSave(String filepath) {

        Connection conn = null;
        // Statement stmt = null;
        // for insert a new candidate
        ResultSet rs = null;

        //Insert/upsert
        String sql = "INSERT INTO Matches(id, round, played) "
                + "VALUES(?,?,?) ON DUPLICATE KEY UPDATE id=?, round=?, played=?";

        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //STEP 2: Execute a query
            System.out.println("Saving Match Data...");
            //stmt = conn.createStatement();


            for(int i = 1; i <= Controller.matches.size();i++){

                pstmt.setInt(1, Controller.getMatchByID(i).getMatchID());
                pstmt.setInt(2, Controller.getMatchByID(i).getMatchRound());
                pstmt.setBoolean(3, Controller.getMatchByID(i).getMatchPlayed());

                // Update

                pstmt.setInt(4, Controller.getMatchByID(i).getMatchID());
                pstmt.setInt(5, Controller.getMatchByID(i).getMatchRound());
                pstmt.setBoolean(6, Controller.getMatchByID(i).getMatchPlayed());

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


    public ArrayList<Match> readMatchData() {
        ArrayList<Match> MatchList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            // Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Loading Match Data...");
            stmt = conn.createStatement();


            String sql = "SELECT * FROM Matches";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                int round = rs.getInt("round");
                boolean played = rs.getBoolean("played");


                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Round: " + round);
                System.out.println(", Is game played?: " + played);


                Match m = new Match(id, round, played);
                MatchList.add(m);


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


        return MatchList;
    }


    @Override
    public void tournySave(String filepath) {

        Connection conn = null;
        // Statement stmt = null;
        // for insert a new candidate
        ResultSet rs = null;

        //Insert/upsert
        String sql = "INSERT INTO TeamMatches(id, match_id, team_id, score, points) "
                + "VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?, match_id=?, team_id=?, score, points";

        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //STEP 2: Execute a query
            System.out.println("Saving Tournament Data...");
            //stmt = conn.createStatement();


            for(int i = 1; i <= Controller.tournaments.size();i++){

                pstmt.setInt(1, Controller.getTeamMatchesByID(i).getTournamentID());
                pstmt.setInt(2, Controller.getTeamMatchesByID(i).getTournamentMatch()); // hvordan gør man med matches?
                pstmt.setInt(3, Controller.getTeamMatchesByID(i).getTournamentTeam1());
                pstmt.setInt(4, Controller.getTeamMatchesByID(i).getTournamentTeam2());
                pstmt.setInt(5, Controller.getTeamMatchesByID(i).getTournamentScore());
                pstmt.setInt(6, Controller.getTeamMatchesByID(i).getTournamentPoints());

                // Update

                pstmt.setInt(7, Controller.getTeamMatchesByID(i).getTournamentID());
                pstmt.setInt(8, Controller.getTeamMatchesByID(i).getTournamentMatch());
                pstmt.setInt(9, Controller.getTeamMatchesByID(i).getTournamentTeam1());
                pstmt.setInt(10, Controller.getTeamMatchesByID(i).getTournamentTeam2());
                pstmt.setInt(11, Controller.getTeamMatchesByID(i).getTournamentScore());
                pstmt.setInt(12, Controller.getTeamMatchesByID(i).getTournamentPoints());

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


    public ArrayList<Tournament> readTournyData() {
        ArrayList<Tournament> TournyList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            // Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Loading Tournament Data...");
            stmt = conn.createStatement();


            String sql = "SELECT * FROM TeamMatches";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                int match_id = rs.getInt("match_id");
                int team_id1 = rs.getInt("team_id");
                int team_id2 = rs.getInt("team_id");
                int score = rs.getInt("score");
                int points = rs.getInt("points");




                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Match: " + match_id);
                System.out.print(", Team: " + team_id1 + "vs. " + team_id2);
                System.out.print(", Score: " + score);
                System.out.println(", Points: " + points);


                Tournament t = new Tournament(id, match_id, team_id1, team_id2, score, points);
                TournyList.add(t);


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


        return TournyList;
    }


}
