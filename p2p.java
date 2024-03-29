import Handler.P2PHandler;
import Utils.Config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p2p {

  private static boolean running = true;

  public static void main(String[] args) {

    Config.loadPortManage();
    P2PHandler p2pHandler = new P2PHandler();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Command commands = new Command(p2pHandler);

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    while (running) {
      System.out.print("p2p> ");
      try {
        String line = input.readLine();
        commands.Parse(line);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  // handle command input
  public static class Command {

    private P2PHandler p2pHandler;

    public Command(P2PHandler p2PHandler) {
      this.p2pHandler = p2PHandler;
    }

    void Parse(String args) {
      String[] params = args.toLowerCase().split(" ");
      switch (params[0]) {
        case "":
          return;
        case "connect":
          if (params.length != 3) {
            System.out.println("ileane input");
          } else {
            Connect(params[1], params[2]);
          }
          break;
        case "get":
          Get(params[1]);
          break;
        case "leave":
          Left();
          break;
        case "exit":
          Exit();
      }
    }


    // connect and broadcast information
    void Connect(String ip, String port) {
      try {
        // start to listen udp
        this.p2pHandler.Connect(ip, Integer.parseInt(port));
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }

    void Get(String filename) {
      this.p2pHandler.QueryFile(filename);
    }

    void Left() {
      this.p2pHandler.Leave();
    }

    void Exit() {
      running = false;
      this.p2pHandler.Exit();
    }
  }
}