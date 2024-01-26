package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex1{
    public static void main(String[] args) throws FileNotFoundException{
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        String directionsStack = "";
        if(reader.hasNextLine())
        {
            directionsStack = reader.nextLine();
        }
        System.out.println(directionsStack);
        reader.nextLine();

        //Nodes:
        HashMap<String, Node> mapOfNodes = new HashMap<>();
        while (reader.hasNextLine())
        {
            String line = reader.nextLine();
            Matcher matchNodes = Pattern.compile("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)").matcher(line);
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
        long counter = 0;
        Node start = mapOfNodes.get("AAA");
        Node end = mapOfNodes.get("ZZZ");
        Node currentNode = start;
        while (currentNode != end) {
            for(int i = 0; i < directionsStack.length(); ++i)
            {
                char stackValue = directionsStack.charAt(i);
                System.out.print("Current Node: " + currentNode.getName());
                if(stackValue == 'L' && currentNode.getLeftNode() != null)
                {
                    System.out.println(" - Going Left to: " + currentNode.getLeftNode().getName());
                    currentNode = currentNode.getLeftNode();
                }
                else if(stackValue == 'R' && currentNode.getRightNode() != null)
                {
                    System.out.println(" - Going right to: " + currentNode.getRightNode().getName());
                    currentNode = currentNode.getRightNode();
                }
                ++counter;
                if(currentNode == end)
                    break;
            }
        }
        System.out.println(counter);
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