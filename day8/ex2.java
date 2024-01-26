package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex2{
    public static void main(String[] args) throws FileNotFoundException{
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        String directionsStack = "";
        if(reader.hasNextLine())
        {
            directionsStack = reader.nextLine();
        }
        //System.out.println(directionsStack);
        reader.nextLine();

        //Nodes:
        HashMap<String, Node> mapOfNodes = new HashMap<>();
        while (reader.hasNextLine())
        {
            String line = reader.nextLine();
            Matcher matchNodes = Pattern.compile("([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)").matcher(line);
            matchNodes.find();
            String nameOfNode = matchNodes.group(1);
            String nameOfLeftNode = matchNodes.group(2);
            String nameOfRightNode = matchNodes.group(3);

            Node node;
            Node left;
            Node right;
            if(mapOfNodes.get(nameOfNode) == null)
            {
                node = new Node(nameOfNode);
                mapOfNodes.put(nameOfNode, node);
            }
            else
            {
                node = mapOfNodes.get(nameOfNode);
            }
            if(mapOfNodes.get(nameOfLeftNode) == null)
            {
                left = new Node(nameOfLeftNode);
                mapOfNodes.put(nameOfLeftNode, left);

            }
            else
            {
                left = mapOfNodes.get(nameOfLeftNode);
            }
            if(mapOfNodes.get(nameOfRightNode) == null)
            {
                right = new Node(nameOfRightNode);
                mapOfNodes.put(nameOfRightNode, right);
            }
            else
            {
                right = mapOfNodes.get(nameOfRightNode);
            }
            node.setLeftNode(left);
            node.setRightNode(right);
        }
        reader.close();

        // for (Map.Entry<String, Node> node : mapOfNodes.entrySet()) {
        //     System.out.println("Node: " + node.getKey() + "   Left: " + node.getValue().getLeftNode().getName() + "   Right: " + node.getValue().getRightNode().getName());
        // }
        ArrayList<Node> startsArrayList = new ArrayList<>();
        for (Map.Entry<String, Node> node : mapOfNodes.entrySet()) {
            if(node.getKey().endsWith("A"))
            {
                startsArrayList.add(node.getValue());
            }
        }

        int directionsStackPointer = 0;


        ArrayList <Long> results = new ArrayList<>();
        for(Node currentNode : startsArrayList)
        {
            long counter = 0;
            while(!currentNode.getName().endsWith("Z"))
            {
                char stackValue = directionsStack.charAt(directionsStackPointer);
                //System.out.print("Current Node: " + currentNode.getName());
                if(stackValue == 'L' && currentNode.getLeftNode() != null)
                {
                   // System.out.println(" - Going Left to: " + currentNode.getLeftNode().getName());
                    currentNode = currentNode.getLeftNode();
                }
                else if(stackValue == 'R' && currentNode.getRightNode() != null)
                {
                  //  System.out.println(" - Going right to: " + currentNode.getRightNode().getName());
                    currentNode = currentNode.getRightNode();
                }
                ++counter;
                directionsStackPointer = (directionsStackPointer + 1)%directionsStack.length();
            }
            results.add(counter);
        }
        System.out.println(results.toString());
        long max = results.get(0);
        for (long result : results) {
            max = max * result / gcd(max, result);
        }
        System.out.println(max);
    }
    static long gcd(long a, long b)
    {
        if(b==0) return a;
        return gcd(b, a%b);
    }
}

class Node {
    private String Name;
    private Node leftNode;
    private Node rightNode;
    Node(String nameString)
    {
        Name = nameString;
        leftNode = null;
        rightNode = null;
    }
    Node (String nameString, Node left, Node right)
    {
        Name = nameString;
        leftNode = left;
        rightNode = right;
    }
    public Node() {
        Name = null;
        leftNode = null;
        rightNode = null;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    public String getName() {
        return Name;
    }
    public Node getLeftNode() {
        return leftNode;
    }
    public Node getRightNode() {
        return rightNode;
    }
}