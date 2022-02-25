import java.util.*;

public class aestrela {

    public static class Node implements Comparable<Node> {
        private static int idCounter = 0;
        public int id;
  
        // Pais
        public Node parent = null;
  
        public List<Edge> neighbors;
  
        // Funções
        public double f = Double.MAX_VALUE;
        public double g = Double.MAX_VALUE;

        // Heurística
        public double h; 
        ArrayList<Double>[] graph;
  
        Node(double h){
              this.h = h;
              this.id = idCounter++;
              this.neighbors = new ArrayList<>();
        }

        // public void Graph(int n){
        //     graph = new ArrayList[n];
        //     for(int i=0;i<n;i++)
        //         graph[i]=new ArrayList<Double>();
        // }

        @Override
        public int compareTo(Node n) {
              return Double.compare(this.f, n.f);
        }
  
        public static class Edge {
              Edge(double weight, Node node){
                    this.weight = weight;
                    this.node = node;
              }
  
              public double weight;
              public Node node;
        }
  
        public void addBranch(double weight, Node node){
              Edge newEdge = new Edge(weight, node);
              neighbors.add(newEdge);
        }
  
        public double heuristic(Node end){
              return this.h;
        }
  }
  public static Node aStar(Node start, Node end){
    ArrayList<Node> closedList = new ArrayList<>();
    PriorityQueue<Node> openList = new PriorityQueue<>();
    Node last;

    start.f = start.g + start.heuristic(end);
    openList.add(start);

    
    while(!openList.isEmpty()){
        Node n = openList.peek();

        if(n.id == end.id){
            System.out.println("achou");
            return n;
        }
        
        System.out.println("achou" + n.id + "IGUAL" + end.id);

        for(Node.Edge edge : n.neighbors){
            Node m = edge.node;
            double totalWeight = n.g + edge.weight;
            if(!openList.contains(m) && !closedList.contains(m)){
                m.parent = n;
                m.g = totalWeight;
                m.f = m.g + m.heuristic(end);
                openList.add(m);

            } else {
                if(totalWeight < m.g){
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.heuristic(end);

                    if(closedList.contains(m)){
                        closedList.remove(m);
                        openList.add(m);
                        System.out.println("Cogitando E"+m.id);

                    }
                }
            }
        }
        System.out.println("Passando por E"+n.id);

        openList.remove(n);
        closedList.add(n);
        last = n;
    }
    
    return null;
}

// public static double calculateTime(Node end){
//     Node n = end;
//     double time = 2*(n.g);

//     if(n.equals(null))
//         return 0;

//     List<Integer> ids = new ArrayList<>();

//     while(n.parent != null){
//         // ids.add(n.id);
//         n = n.parent;
//         time += 4;
//     }
//     return time;
//     // ids.add(n.id);
//     // Collections.reverse(ids);

//     // for(int id : ids){
//     //     System.out.print(id + " ");
//     // }
//     // System.out.println("");
// }
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        String stationInit, stationFinal;
        int stationInitInt, stationFinalInt;
        // // Node start = new Node(3);
        double [][] directCost = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//E0
            {0, 0, 10, 18.5, 24.8, 36.4, 38.8, 35.8, 25.4, 17.6, 9.1, 16.7, 27.3, 27.6, 29.8}, //E1
            {0, 10, 0, 8.5, 14.8, 26.6, 29.1, 26.1, 17.3, 10, 3.5, 15.5, 20.9, 19.1, 21.8}, //E2
            {0, 18.5, 8.5, 0, 6.3, 18.2, 20.6, 17.6, 13.6, 9.4, 10.3, 19.5, 19.1, 12.1, 16.6}, //E3
            {0, 24.8, 14.8, 6.3, 0, 12, 14.4, 11.5, 12.4, 12.6, 16.7, 23.6, 18.6, 10.6, 15.4},//E4
            {0, 36.4, 26.6, 18.2, 12, 0, 3, 2.4, 19.4, 23.3, 28.2, 34.2, 24.8, 14.5, 17.9},//E5
            {0, 38.8, 29.1, 20.6, 14.4, 3, 0, 3.3, 22.3, 25.7, 30.3, 36.7, 27.6, 15.2, 18.2},//E6
            {0, 35.8, 26.1, 17.6, 11.5, 2.4, 3.3, 0, 20, 23, 27.3, 34.2, 25.7, 12.4, 15.6},//E7
            {0, 25.4, 17.3, 13.6, 12.4, 19.4, 22.3, 20, 0, 8.2, 20.3, 16.1, 6.4, 22.7, 27.6},//E8
            {0, 17.6, 10, 9.4, 12.6, 23.3, 25.7, 23, 8.2, 0, 13.5, 11.2, 10.9, 21.2, 26.6},//E9
            {0, 9.1, 3.5, 10.3, 16.7, 28.2, 30.3, 27.3, 20.3, 13.5, 0, 17.6, 24.2, 18.7, 21.2},//E10
            {0, 16.7, 15.5, 19.5, 23.6, 34.2, 36.7, 34.2, 16.1, 11.2, 17.6, 0, 14.2, 31.5, 35.5},//E11
            {0, 27.3, 20.9, 19.1, 18.6, 24.8, 27.6, 25.7, 6.4, 10.9, 24.2, 14.2, 0, 28.8, 33.6},//E12
            {0, 27.6, 19.1, 12.1, 10.6, 14.5, 15.2, 12.4, 22.7, 21.2, 18.7, 31.5, 28.8, 0, 5.1},//E13
            {0, 29.8, 21.8, 16.6, 15.4, 17.9, 18.2, 15.6, 27.6, 26.6, 21.2, 35.5, 33.6, 5.1, 0},//E14
        };

        System.out.println("Digite a estação de partida:");
        stationInit = in.nextLine();
        stationInit = stationInit.replace("E", "");
        
        System.out.println("Digite a estação de destino:");
		stationFinal = in.nextLine();
        stationFinal = stationFinal.replace("E", "");
        
        stationInitInt = Integer.parseInt(stationInit);
        stationFinalInt = Integer.parseInt(stationFinal);
        

        Node e0 = new Node(directCost[0][stationFinalInt]);
        Node e1 = new Node(directCost[1][stationFinalInt]);
        Node e2 = new Node(directCost[2][stationFinalInt]);
        Node e3 = new Node(directCost[3][stationFinalInt]);
        Node e4 = new Node(directCost[4][stationFinalInt]);
        Node e5 = new Node(directCost[5][stationFinalInt]);
        Node e6 = new Node(directCost[6][stationFinalInt]);
        Node e7 = new Node(directCost[7][stationFinalInt]);
        Node e8 = new Node(directCost[8][stationFinalInt]);
        Node e9 = new Node(directCost[9][stationFinalInt]);
        Node e10 = new Node(directCost[10][stationFinalInt]);
        Node e11 = new Node(directCost[11][stationFinalInt]);
        Node e12 = new Node(directCost[12][stationFinalInt]);
        Node e13 = new Node(directCost[13][stationFinalInt]);
        Node e14 = new Node(directCost[14][stationFinalInt]);
        
        e0.addBranch(0, e1);
        e1.addBranch(10, e2);
        e2.addBranch(8.5, e3);
        e2.addBranch(10, e9);
        e2.addBranch(3.5, e10);
        e3.addBranch(6.3, e4);
        e3.addBranch(9.4, e9);
        e3.addBranch(18.7, e13);
        e4.addBranch(13, e5);
        e4.addBranch(15.3, e8);
        e4.addBranch(12.8, e13);
        e5.addBranch(3, e6);
        e5.addBranch(2.4, e7);
        e5.addBranch(30, e8);
        e8.addBranch(9.6, e9);
        e8.addBranch(6.4, e12);
        e9.addBranch(12.2, e11);
        e13.addBranch(5.1, e14);

        // Node start = e4;
        // start.g = 0;
        // Node end = e10;
        // Node res = aStar(start, end);
        // printPath(res);
        // Node e1 = new Node(10.2);
        // Node e2 = new Node(2.2);
        // Node e3 = new Node(8.2);
        // Node e4 = new Node(2.2);
        // Node e5 = new Node(1.2);
        // Node e6 = new Node(1.2);
        // Node e7 = new Node(0.2);
        
        // e1.addBranch(1.1, e2);
        // e1.addBranch(5.1, e3);
        // e1.addBranch(2.1, e4);
        // e4.addBranch(1.1, e3);
        // e2.addBranch(7.1, e5);
        // e3.addBranch(4.1, e6);
        // e4.addBranch(6.1, e5);
        // e5.addBranch(3.1, e7);
        // e6.addBranch(1.1, e5);
        // e6.addBranch(3.1, e7);
        
        // Node head = n0;
        // head.g = 0;
        // Node res = aStar(head, target);
        // printPath(res);

        
        Node start = e1;
        start.g = 0;
        Node end = e6;
        Node res = aStar(start, end);
        // System.out.println("O trajeto total demorará "+calculateTime(res)+" minutos");
    }
}