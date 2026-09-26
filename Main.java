import java.util.*;

// ==========================================
// 1. DATA MODELS & UTILITIES
// ==========================================

class Student {
    private String id;
    private String name;
    private String programme;
    private double marks;

    public Student(String id, String name, String programme, double marks) {
        this.id = id;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getProgramme() { return programme; }
    public double getMarks() { return marks; }

    public void setName(String name) { this.name = name; }
    public void setProgramme(String programme) { this.programme = programme; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return String.format("ID: %-12s | Name: %-20s | Programme: %-15s | Marks: %.2f", id, name, programme, marks);
    }
}

// ==========================================
// 2. DATA STRUCTURE IMPLEMENTATIONS
// ==========================================

// --- Custom Linked List ---
class StudentLinkedList {
    private class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node head;

    public void add(Student student) {
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public Student find(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.getId().equalsIgnoreCase(id)) return temp.data;
            temp = temp.next;
        }
        return null;
    }

    public boolean remove(String id) {
        if (head == null) return false;
        if (head.data.getId().equalsIgnoreCase(id)) {
            head = head.next;
            return true;
        }
        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data.getId().equalsIgnoreCase(id)) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void display() {
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}

// --- Custom Stack (Action History) ---
class ActionStack {
    private class Node {
        String action;
        Node next;
        Node(String action) { this.action = action; }
    }

    private Node top;

    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
    }

    public void display() {
        if (top == null) {
            System.out.println("No recent actions logged.");
            return;
        }
        System.out.println("--- Recent Actions Log (LIFO) ---");
        Node temp = top;
        int count = 1;
        while (temp != null) {
            System.out.println(count++ + ". " + temp.action);
            temp = temp.next;
        }
    }
}

// --- Custom Queue (Service Requests) ---
class ServiceQueue {
    private class Node {
        String request;
        Node next;
        Node(String request) { this.request = request; }
    }

    private Node front, rear;

    public void enqueue(String request) {
        Node newNode = new Node(request);
        if (rear == null) {
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    public String dequeue() {
        if (front == null) return null;
        String req = front.request;
        front = front.next;
        if (front == null) rear = null;
        return req;
    }

    public void display() {
        if (front == null) {
            System.out.println("Service request queue is empty.");
            return;
        }
        System.out.println("--- Pending Requests (FIFO) ---");
        Node temp = front;
        int count = 1;
        while (temp != null) {
            System.out.println(count++ + ". " + temp.request);
            temp = temp.next;
        }
    }
}

// --- Custom Binary Search Tree (BST) ---
class StudentBST {
    private class Node {
        Student student;
        Node left, right;
        Node(Student student) { this.student = student; }
    }

    private Node root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private Node insertRec(Node root, Student student) {
        if (root == null) return new Node(student);
        if (student.getId().compareToIgnoreCase(root.student.getId()) < 0) {
            root.left = insertRec(root.left, student);
        } else if (student.getId().compareToIgnoreCase(root.student.getId()) > 0) {
            root.right = insertRec(root.right, student);
        } else {
            root.student = student;
        }
        return root;
    }

    public void delete(String id) {
        root = deleteRec(root, id);
    }

    private Node deleteRec(Node root, String id) {
        if (root == null) return null;
        if (id.compareToIgnoreCase(root.student.getId()) < 0) {
            root.left = deleteRec(root.left, id);
        } else if (id.compareToIgnoreCase(root.student.getId()) > 0) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            root.student = minValue(root.right);
            root.right = deleteRec(root.right, root.student.getId());
        }
        return root;
    }

    private Student minValue(Node root) {
        Student minv = root.student;
        while (root.left != null) {
            minv = root.left.student;
            root = root.left;
        }
        return minv;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.student);
            inOrderRec(root.right);
        }
    }
}

// --- Custom Hash Table ---
class StudentHashTable {
    private class HashNode {
        String key;
        Student value;
        HashNode next;
        HashNode(String key, Student value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int CAPACITY = 16;
    private HashNode[] table = new HashNode[CAPACITY];

    private int getIndex(String key) {
        return Math.abs(key.toUpperCase().hashCode()) % CAPACITY;
    }

    public void put(String key, Student student) {
        int index = getIndex(key);
        HashNode head = table[index];
        while (head != null) {
            if (head.key.equalsIgnoreCase(key)) {
                head.value = student;
                return;
            }
            head = head.next;
        }
        HashNode newNode = new HashNode(key, student);
        newNode.next = table[index];
        table[index] = newNode;
    }

    public Student get(String key) {
        int index = getIndex(key);
        HashNode head = table[index];
        while (head != null) {
            if (head.key.equalsIgnoreCase(key)) return head.value;
            head = head.next;
        }
        return null;
    }

    public void remove(String key) {
        int index = getIndex(key);
        HashNode head = table[index];
        HashNode prev = null;
        while (head != null) {
            if (head.key.equalsIgnoreCase(key)) {
                if (prev != null) prev.next = head.next;
                else table[index] = head.next;
                return;
            }
            prev = head;
            head = head.next;
        }
    }
}

// --- Custom Graph (Adjacency List using raw types to avoid generic parse errors) ---
@SuppressWarnings("unchecked")
class CampusGraph {
    private Map adjList = new HashMap();

    public boolean addLocation(String name) {
        String key = name.trim();
        if (adjList.containsKey(key)) return false;
        adjList.put(key, new ArrayList());
        return true;
    }

    public boolean removeLocation(String name) {
        String key = name.trim();
        if (!adjList.containsKey(key)) return false;
        adjList.remove(key);
        for (Object obj : adjList.values()) {
            List neighbors = (List) obj;
            neighbors.remove(key);
        }
        return true;
    }

    public boolean addConnection(String src, String dest) {
        String u = src.trim(), v = dest.trim();
        if (!adjList.containsKey(u) || !adjList.containsKey(v)) return false;
        List uNeighbors = (List) adjList.get(u);
        List vNeighbors = (List) adjList.get(v);
        if (uNeighbors.contains(v)) return false;
        uNeighbors.add(v);
        vNeighbors.add(u);
        return true;
    }

    public boolean removeConnection(String src, String dest) {
        String u = src.trim(), v = dest.trim();
        if (!adjList.containsKey(u) || !adjList.containsKey(v)) return false;
        List uNeighbors = (List) adjList.get(u);
        List vNeighbors = (List) adjList.get(v);
        boolean r1 = uNeighbors.remove(v);
        boolean r2 = vNeighbors.remove(u);
        return r1 || r2;
    }

    public void displayConnections() {
        if (adjList.isEmpty()) {
            System.out.println("No locations registered in campus graph.");
            return;
        }
        System.out.println("\n--- Campus Network Graph ---");
        for (Object obj : adjList.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void bfs(String start) {
        String key = start.trim();
        if (!adjList.containsKey(key)) {
            System.out.println("Error: Location not found!");
            return;
        }
        System.out.println("\n--- BFS Traversal starting from " + key + " ---");
        Set visited = new HashSet();
        Queue queue = new LinkedList();

        visited.add(key);
        queue.add(key);

        while (!queue.isEmpty()) {
            String curr = (String) queue.poll();
            System.out.print(curr + " ");
            List neighbors = (List) adjList.get(curr);
            for (Object neighborObj : neighbors) {
                String neighbor = (String) neighborObj;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void dfs(String start) {
        String key = start.trim();
        if (!adjList.containsKey(key)) {
            System.out.println("Error: Location not found!");
            return;
        }
        System.out.println("\n--- DFS Traversal starting from " + key + " ---");
        Set visited = new HashSet();
        dfsRec(key, visited);
        System.out.println();
    }

    private void dfsRec(String curr, Set visited) {
        visited.add(curr);
        System.out.print(curr + " ");
        List neighbors = (List) adjList.get(curr);
        for (Object neighborObj : neighbors) {
            String neighbor = (String) neighborObj;
            if (!visited.contains(neighbor)) {
                dfsRec(neighbor, visited);
            }
        }
    }
}

// ==========================================
// 3. MAIN APPLICATION & MENU SYSTEM
// ==========================================

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    private static StudentLinkedList linkedList = new StudentLinkedList();
    private static ActionStack actionStack = new ActionStack();
    private static ServiceQueue serviceQueue = new ServiceQueue();
    private static StudentBST bst = new StudentBST();
    private static StudentHashTable hashTable = new StudentHashTable();
    private static CampusGraph graph = new CampusGraph();

    public static void main(String[] args) {
        graph.addLocation("Main Gate");
        graph.addLocation("Library");
        graph.addLocation("IT Faculty");
        graph.addLocation("Canteen");
        graph.addConnection("Main Gate", "Library");
        graph.addConnection("Library", "IT Faculty");
        graph.addConnection("IT Faculty", "Canteen");

        while (true) {
            printMenu();
            int choice = readIntInput("Enter option (1-16): ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    linkedList.display();
                    break;
                case 5:
                    addServiceRequest();
                    break;
                case 6:
                    processServiceRequest();
                    break;
                case 7:
                    actionStack.display();
                    break;
                case 8:
                    bst.displayInOrder();
                    break;
                case 9:
                    searchStudentHash();
                    break;
                case 10:
                    addLocation();
                    break;
                case 11:
                    removeLocation();
                    break;
                case 12:
                    addConnection();
                    break;
                case 13:
                    removeConnection();
                    break;
                case 14:
                    graph.displayConnections();
                    break;
                case 15:
                    traverseGraph();
                    break;
                case 16:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Choice must be between 1 and 16.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=======================================================");
        System.out.println(" UNIVERSITY RECORD & CAMPUS ROUTE MANAGEMENT SYSTEM");
        System.out.println("=======================================================");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records (Linked List)");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions (Stack)");
        System.out.println(" 8. Display Students Sorted by ID (BST)");
        System.out.println(" 9. Search Student by ID (Hashing)");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections Graph");
        System.out.println("15. Traverse Campus Locations (BFS / DFS)");
        System.out.println("16. Exit");
        System.out.println("=======================================================");
    }

    private static void addStudent() {
        String id = readNonEmptyString("Enter Student ID: ");
        if (hashTable.get(id) != null) {
            System.out.println("Error: A student with ID '" + id + "' already exists!");
            return;
        }
        String name = readNonEmptyString("Enter Name: ");
        String prog = readNonEmptyString("Enter Programme: ");
        double marks = readDoubleInput("Enter Marks (0-100): ", 0.0, 100.0);

        Student s = new Student(id, name, prog, marks);
        linkedList.add(s);
        bst.insert(s);
        hashTable.put(id, s);
        actionStack.push("Added Student: " + id);

        System.out.println("Success: Student record added successfully.");
    }

    private static void updateStudent() {
        String id = readNonEmptyString("Enter Student ID to update: ");
        Student s = hashTable.get(id);
        if (s == null) {
            System.out.println("Error: Student record not found!");
            return;
        }
        System.out.println("Current details: " + s);
        String name = readNonEmptyString("Enter New Name: ");
        String prog = readNonEmptyString("Enter New Programme: ");
        double marks = readDoubleInput("Enter New Marks (0-100): ", 0.0, 100.0);

        s.setName(name);
        s.setProgramme(prog);
        s.setMarks(marks);
        actionStack.push("Updated Student: " + id);

        System.out.println("Success: Student record updated successfully.");
    }

    private static void deleteStudent() {
        String id = readNonEmptyString("Enter Student ID to delete: ");
        Student s = hashTable.get(id);
        if (s == null) {
            System.out.println("Error: Student record not found!");
            return;
        }
        linkedList.remove(id);
        bst.delete(id);
        hashTable.remove(id);
        actionStack.push("Deleted Student Record: " + id);

        System.out.println("Success: Student record deleted.");
    }

    private static void searchStudentHash() {
        String id = readNonEmptyString("Enter Student ID to search: ");
        Student s = hashTable.get(id);
        if (s != null) {
            System.out.println("\nRecord Found (via Hash Table Lookup):");
            System.out.println(s);
        } else {
            System.out.println("Error: Student ID not found in system.");
        }
    }

    private static void addServiceRequest() {
        String id = readNonEmptyString("Enter Student ID making request: ");
        String desc = readNonEmptyString("Enter Request Details: ");
        String reqStr = "StudentID: " + id + " | Details: " + desc;
        serviceQueue.enqueue(reqStr);
        actionStack.push("Enqueued Request for " + id);
        System.out.println("Success: Request added to queue.");
    }

    private static void processServiceRequest() {
        String processed = serviceQueue.dequeue();
        if (processed == null) {
            System.out.println("No service requests to process.");
        } else {
            System.out.println("Processing Request -> " + processed);
            actionStack.push("Processed Service Request");
        }
    }

    private static void addLocation() {
        String loc = readNonEmptyString("Enter Location Name: ");
        if (graph.addLocation(loc)) {
            actionStack.push("Added Location: " + loc);
            System.out.println("Success: Location added to map.");
        } else {
            System.out.println("Error: Location already exists.");
        }
    }

    private static void removeLocation() {
        String loc = readNonEmptyString("Enter Location Name to remove: ");
        if (graph.removeLocation(loc)) {
            actionStack.push("Removed Location: " + loc);
            System.out.println("Success: Location removed from graph.");
        } else {
            System.out.println("Error: Location not found.");
        }
    }

    private static void addConnection() {
        String src = readNonEmptyString("Enter Source Location: ");
        String dest = readNonEmptyString("Enter Destination Location: ");
        if (graph.addConnection(src, dest)) {
            actionStack.push("Added Road: " + src + " <-> " + dest);
            System.out.println("Success: Road connection added.");
        } else {
            System.out.println("Error: Invalid locations or road already exists.");
        }
    }

    private static void removeConnection() {
        String src = readNonEmptyString("Enter Source Location: ");
        String dest = readNonEmptyString("Enter Destination Location: ");
        if (graph.removeConnection(src, dest)) {
            actionStack.push("Removed Road: " + src + " <-> " + dest);
            System.out.println("Success: Road connection removed.");
        } else {
            System.out.println("Error: Connection or locations do not exist.");
        }
    }

    private static void traverseGraph() {
        String start = readNonEmptyString("Enter Starting Location: ");
        System.out.println("1. Breadth-First Search (BFS)");
        System.out.println("2. Depth-First Search (DFS)");
        int choice = readIntInput("Select Traversal Type (1-2): ");
        if (choice == 1) graph.bfs(start);
        else if (choice == 2) graph.dfs(start);
        else System.out.println("Invalid choice!");
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }

    private static double readDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                double val = Double.parseDouble(line);
                if (val >= min && val <= max) return val;
                System.out.println("Error: Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numerical value.");
            }
        }
    }
}