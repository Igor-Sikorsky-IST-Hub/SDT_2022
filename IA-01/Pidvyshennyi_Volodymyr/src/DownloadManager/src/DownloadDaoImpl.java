import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadDaoImpl implements DownloadDao {

    private static final String sqlSelectAll = "SELECT url, size, downloaded, status FROM downloads";
    private static final String sqlInsert = "INSERT INTO downloads (url, size, downloaded, status) VALUES (?, ?, ?, ?)";
    private static final String sqlDelete = "DELETE FROM downloads WHERE url=?;";

    public Connection getConnection() throws SQLException{

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/downloaddb", "root", "Pvv3422479"
        );
    }


    public Download get(int id) {
        return null;
    }

    public List<Download> getAll() throws SQLException {
        List<Download> downloads = new ArrayList<>();
        Connection conn = getConnection();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlSelectAll);
            while (rs.next()){
                downloads.add(new Download(rs.getURL(1),
                        rs.getLong(2), rs.getLong(3),
                        Arrays.asList(Download.STATUSES).indexOf(rs.getString(4))));
            }
        }
        conn.close();
        return downloads;
    }

    public void add(Download download) throws SQLException {
        Connection conn = getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)){
            ps.setString(1, download.getUrl());
            ps.setInt(2, (int) download.getSize());
            ps.setInt(3, (int) download.getDownloaded());
            ps.setString(4, Download.STATUSES[download.getStatus()]);
            if(ps.executeUpdate() > 0){
                System.out.println("A download was added successfuly!");
            }
        }
        conn.close();
    }


    public void remove(Download download) throws SQLException {
        Connection conn = getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sqlDelete)){
            ps.setString(1, download.getUrl());
            if (ps.executeUpdate() > 0) {
                System.out.println("A download was deleted successfully!");
            }
        }
        conn.close();
    }
}
