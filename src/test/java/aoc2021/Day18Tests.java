package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Tests {
    @Test
    void part1_explode_01() {
        String formula = "[[[[[9,8],1],2],3],4]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);
        Day18.SnailfishMath.reduce(root);

        assertEquals("[[[[0,9],2],3],4]", root.toString());
    }

    @Test
    void part1_explode_02() {
        String formula = "[7,[6,[5,[4,[3,2]]]]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);
        Day18.SnailfishMath.reduce(root);

        assertEquals("[7,[6,[5,[7,0]]]]", root.toString());
    }

    @Test
    void part1_explode_03() {
        String formula = "[[6,[5,[4,[3,2]]]],1]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);
        Day18.SnailfishMath.reduce(root);

        assertEquals("[[6,[5,[7,0]]],3]", root.toString());
    }

    @Test
    void part1_explode_04() {
        String formula = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);
        Day18.SnailfishMath.reduce(root);

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", root.toString());
    }

    @Test
    void part1_addition_pair_01() {
        String leftFormula = "[[[[4,3],4],4],[7,[[8,4],9]]]";
        String rightFormula = "[1,1]";

        Day18.Node left = Day18.SnailfishMath.parseFormula(leftFormula);
        Day18.Node right = Day18.SnailfishMath.parseFormula(rightFormula);
        Day18.Node root = Day18.SnailfishMath.addition(left, right);

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", root.toString());
    }

    @Test
    void part1_addition_pair_02() {
        String leftFormula = "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]";
        String rightFormula = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]";

        Day18.Node left = Day18.SnailfishMath.parseFormula(leftFormula);
        Day18.Node right = Day18.SnailfishMath.parseFormula(rightFormula);
        Day18.Node root = Day18.SnailfishMath.addition(left, right);

        assertEquals("[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]", root.toString());
    }

    @Test
    void part1_addition_list_01() {
        List<String> formulas = List.of(
            "[1,1]",
            "[2,2]",
            "[3,3]",
            "[4,4]");

        Day18.Node root = Day18.SnailfishMath.addition(formulas);

        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", root.toString());
    }

    @Test
    void part1_addition_list_02() {
        List<String> formulas = List.of(
            "[1,1]",
            "[2,2]",
            "[3,3]",
            "[4,4]",
            "[5,5]");

        Day18.Node root = Day18.SnailfishMath.addition(formulas);

        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", root.toString());
    }

    @Test
    void part1_addition_list_03() {
        List<String> formulas = List.of(
            "[1,1]",
            "[2,2]",
            "[3,3]",
            "[4,4]",
            "[5,5]",
            "[6,6]");

        Day18.Node root = Day18.SnailfishMath.addition(formulas);

        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", root.toString());
    }

    @Test
    void part1_addition_list_04() {
        List<String> formulas = List.of(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]");

        Day18.Node root = Day18.SnailfishMath.addition(formulas);

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", root.toString());
    }

    @Test
    void part1_magnitude_01() {
        String formula = "[[1,2],[[3,4],5]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(143, root.getMagnitude());
    }

    @Test
    void part1_magnitude_02() {
        String formula = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(1384, root.getMagnitude());
    }

    @Test
    void part1_magnitude_03() {
        String formula = "[[[[1,1],[2,2]],[3,3]],[4,4]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(445, root.getMagnitude());
    }

    @Test
    void part1_magnitude_04() {
        String formula = "[[[[3,0],[5,3]],[4,4]],[5,5]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(791, root.getMagnitude());
    }

    @Test
    void part1_magnitude_05() {
        String formula = "[[[[5,0],[7,4]],[5,5]],[6,6]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(1137, root.getMagnitude());
    }

    @Test
    void part1_magnitude_06() {
        String formula = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(3488, root.getMagnitude());
    }

    @Test
    void part1_magnitude_07() {
        String formula = "[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]";

        Day18.Node root = Day18.SnailfishMath.parseFormula(formula);

        assertEquals(3993, root.getMagnitude());
    }


    @Test
    void part1_example() {
        List<String> formulas = List.of(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]");

        long result = Day18.part1(formulas);

        assertEquals(4140, result);
    }

    @Test
    void part2_example() {
        List<String> formulas = List.of(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]");

        long result = Day18.part2(formulas);

        assertEquals(3993, result);
    }
}
