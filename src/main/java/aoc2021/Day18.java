package aoc2021;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day18 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day18.txt");

        System.out.printf("Day 18, part 1: %d\n", part1(data));
        System.out.printf("Day 18, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        Node root = SnailfishMath.addition(data);
        return root.getMagnitude();
    }

    static long part2(List<String> data) {
        List<Node> nodes = data.stream().map(SnailfishMath::parseFormula).collect(Collectors.toList());

        long bestMagnitude = 0;

        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                bestMagnitude = Math.max(bestMagnitude, SnailfishMath.addition(nodes.get(i).copy(), nodes.get(j).copy()).getMagnitude());
                bestMagnitude = Math.max(bestMagnitude, SnailfishMath.addition(nodes.get(j).copy(), nodes.get(i).copy()).getMagnitude());
            }
        }

        return bestMagnitude;
    }

    static class SnailfishMath {
        public static Node parseFormula(String formula) {
            Map<Integer, Node> nodeMap = new HashMap<>();
            int index = -1;

            Pattern p = Pattern.compile("(\\[(-?\\d+),(-?\\d+)])");
            Matcher m = p.matcher(formula);

            while (m.find()) {
                int leftNum = Integer.parseInt(m.group(2));
                int rightNum = Integer.parseInt(m.group(3));

                Node left = nodeMap.getOrDefault(leftNum, new ValueNode(leftNum));
                Node right = nodeMap.getOrDefault(rightNum, new ValueNode(rightNum));
                Node parent = new Node();
                parent.addChildren(left, right);
                nodeMap.put(index, parent);

                formula = m.replaceFirst(Integer.toString(index));
                m = p.matcher(formula);
                index--;
            }

            Node root = nodeMap.get(Integer.parseInt(formula));
            root.updateLevels(0);
            return root;
        }

        public static Node addition(Node left, Node right) {
            Node root = new Node();
            root.addChildren(left, right);
            root.updateLevels(0);

            reduce(root);

            return root;
        }

        public static Node addition(List<String> formulas) {
            Node root = parseFormula(formulas.get(0));

            for (int i = 1; i < formulas.size(); i++) {
                Node node = parseFormula(formulas.get(i));
                root = addition(root, node);
            }

            return root;
        }

        public static void reduce(Node root) {
            while (true) {
                Node explodeTarget = explodeTarget(root);
                if (explodeTarget != null) {
                    explode(explodeTarget);
                    continue;
                }

                ValueNode splitTarget = splitTarget(root);
                if (splitTarget != null) {
                    split(splitTarget);
                    continue;
                }

                break;
            }
        }

        private static void explode(Node explodeNode) {
            ValueNode leftValueNode = (ValueNode) explodeNode.left;
            ValueNode rightValueNode = (ValueNode) explodeNode.right;
            Node parent = explodeNode.parent;

            Node prev = parent;
            Node cur = parent.parent;

            if (parent.left.equals(explodeNode)) {
                while (cur != null && cur.left.equals(prev)) {
                    prev = cur;
                    cur = cur.parent;
                }

                if (cur != null) {
                    Node node = cur.left;

                    while (!node.hasValue()) {
                        node = node.right;
                    }

                    ((ValueNode) node).value += leftValueNode.value;
                }

                Node node = parent.right;
                while (!node.hasValue()) {
                    node = node.left;
                }

                ((ValueNode) node).value += rightValueNode.value;

                ValueNode zeroNode = new ValueNode(0);
                zeroNode.parent = parent;
                zeroNode.level = parent.level + 1;
                explodeNode.parent.left = zeroNode;
            } else {
                while (cur != null && cur.right.equals(prev)) {
                    prev = cur;
                    cur = cur.parent;
                }

                if (cur != null) {
                    Node node = cur.right;

                    while (!node.hasValue()) {
                        node = node.left;
                    }

                    ((ValueNode) node).value += rightValueNode.value;
                }

                Node node = parent.left;
                while (!node.hasValue()) {
                    node = node.right;
                }

                ((ValueNode) node).value += leftValueNode.value;

                ValueNode zeroNode = new ValueNode(0);
                zeroNode.parent = parent;
                zeroNode.level = parent.level + 1;
                explodeNode.parent.right = zeroNode;
            }
        }

        private static void split(ValueNode splitNode) {
            Node parent = splitNode.parent;

            Node leftValueNode = new ValueNode(splitNode.value / 2);
            Node rightValueNode = new ValueNode((splitNode.value / 2) + (splitNode.value % 2));

            Node pair = new Node();
            pair.addChildren(leftValueNode, rightValueNode);
            pair.updateLevels(parent.level + 1);

            if (parent.left.equals(splitNode)) {
                parent.left = pair;
                pair.parent = parent;
            } else {
                parent.right = pair;
                pair.parent = parent;
            }
        }

        private static Node explodeTarget(Node node) {
            if (node.hasValue()) {
                return null;
            }

            if (node.level == 4) {
                return node;
            }

            Node leftTarget = explodeTarget(node.left);
            if (leftTarget != null) {
                return leftTarget;
            }

            return explodeTarget(node.right);
        }

        private static ValueNode splitTarget(Node node) {
            if (node.hasValue()) {
                ValueNode vn = (ValueNode) node;
                if (vn.value >= 10) {
                    return vn;
                } else {
                    return null;
                }
            }

            ValueNode leftTarget = splitTarget(node.left);
            if (leftTarget != null) {
                return leftTarget;
            }

            return splitTarget(node.right);
        }
    }

    static class Node {
        protected Node parent;
        protected Node left;
        protected Node right;
        protected int level;

        public boolean hasValue() {
            return false;
        }

        @Override
        public String toString() {
            return String.format("[%s,%s]", left, right);
        }

        public void updateLevels() {
            if (left != null) {
                left.updateLevels(level + 1);
            }

            if (right != null) {
                right.updateLevels(level + 1);
            }
        }

        public void updateLevels(int level) {
            this.level = level;
            updateLevels();
        }

        public void addChildren(Node left, Node right) {
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        public long getMagnitude() {
            return 3 * left.getMagnitude() + 2 * right.getMagnitude();
        }

        public Node copy() {
            Node node = new Node();
            node.level = level;
            Node left = this.left.copy();
            Node right = this.right.copy();
            node.addChildren(left, right);
            return node;
        }

    }

    static class ValueNode extends Node {
        private int value;

        ValueNode(int value) {
            this.value = value;
        }

        @Override
        public boolean hasValue() {
            return true;
        }

        @Override
        public void addChildren(Node left, Node right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return String.format("%d", value);
        }

        @Override
        public long getMagnitude() {
            return value;
        }

        @Override
        public Node copy() {
            ValueNode valueNode = new ValueNode(value);
            valueNode.level = level;
            return valueNode;
        }
    }
}
