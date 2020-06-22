
import model.*;
import network.RemoteModel;
import network.RmiServer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * The ServerMain class stars the server
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class ServerMain
{
  public static void main(String[] args)
      throws SQLException, MalformedURLException, RemoteException
  {
    Model model = new ModelManager();
    RemoteModel server = new RmiServer(model);
  }

}
