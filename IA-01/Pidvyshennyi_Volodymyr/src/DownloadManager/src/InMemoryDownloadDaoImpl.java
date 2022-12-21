import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDownloadDaoImpl implements DownloadDao{

    private List<Download> downloadList = new ArrayList<>();

    @Override
    public Download get(int id) {
        return null;
    }

    @Override
    public List<Download> getAll() throws SQLException {
        return downloadList;
    }

    @Override
    public void add(Download t) throws SQLException {
        downloadList.add(t);
    }

    @Override
    public void remove(Download t) throws SQLException {
        System.out.println("A download was deleted successfuly!");
    }
}
