import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

public interface DownloadDao {

    Download get(int id);

    List<Download> getAll() throws SQLException;

    void add(Download t) throws SQLException;

    void remove(Download t) throws SQLException;
}
