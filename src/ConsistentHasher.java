import java.util.*;

public class ConsistentHasher<T> {

    public class Server implements Comparable<Server> {
        private HashMap<String, T> map; // 5 points
        private String name;

        public Server(String name) {
            map = new HashMap<>();
            this.name = name;
        }

        T getFromDB(String key) {
            return (T) key;
            /* retrieves T form data base, no need to implement*/
        }

        T getValue(String key) { // 5 points
            if (map.containsKey(key)) {
                return map.get(key);
            }
            T t = getFromDB(key);
            map.put(key, t);
            return t;
        }

        public int hashCode() { // 0..359 by servers name, 5 points
            return name.hashCode() % 359;
        }

        public boolean equals(Server s) {
            return name.equals(s.name);
        }

        @Override
        public int compareTo(Server o) { // 5 points
            return hashCode() - o.hashCode();
        }
    }// end of inner class Server, back to ConsistentHasher class

    List<Server> servers;

    public ConsistentHasher() {
        servers = new LinkedList<>();
    }

    Server debugServer;
    int debugKeyIndex;

    public T getValue(String key) { // 10 points
        int index = key.hashCode() % 359; // the hash of key
        debugKeyIndex = index;
        int serverNumber = 360;
        for(int i =0; i < servers.size(); i++){
            if(servers.get(i).hashCode() > index && serverNumber > servers.get(i).hashCode()){
                  serverNumber = i;
            }
        }
        Server chosen = servers.get(serverNumber);
        debugServer = chosen;
        return chosen.getValue(key);
    }

    public void addServer(Server server) {
        servers.add(server);
        Collections.sort(servers);
    }

    public void removeServer(Server server) {
        servers.remove(server);
        Collections.sort(servers);
    }

    public void debug(String key) {
        for (Server s : servers) {
            System.out.println(s.name + "," + s.hashCode());
        }
        System.out.println();
        getValue(key);
        System.out.println(debugKeyIndex + " -> " + debugServer.name);
    }

    public static void main(String[] args) {
        ConsistentHasher<Integer> ch = new ConsistentHasher<>();
        ch.addServer(ch.new Server("AA"));
        ch.addServer(ch.new Server("BB"));
        ch.addServer(ch.new Server("CC"));
        ch.addServer(ch.new Server("DD"));
        ch.addServer(ch.new Server("EE"));
        ch.debug("Eli");


    }
}
