//import org.example.Grammar;
//import org.example.Pair;
//import org.example.ParserRecursiveDescendent;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//public class RecursiveDescendentParserTests {
//
//    private Grammar gr = new Grammar("src/main/resources/g1.txt");
//
//    @Test
//    public void TestExpand() {
//        // When head of the put stack is non-terminal
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.Expand();
//
//        Pair<String, Integer> workingStackPair = parser.getWorkingStack().peek();
//        Assert.assertEquals(workingStackPair.getFirstElement(), gr.initialState);
//        Assert.assertSame(0, workingStackPair.getSecondElement());
//        String prod = gr.setOfProductions.get(gr.initialState).getFirst();
//        Assert.assertEquals(parser.getInputStack().peek(), prod);
//    }
//
//    @Test
//    public void TestSuccess() {
//        // When position is bigger than string length
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.Success();
//
//        Assert.assertEquals(parser.getState(), "f");
//    }
//
//    @Test
//    public void TestAdvance() {
//        // when head of stack is terminal and equal with current position of input
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.Expand();
//
//        int pos = parser.getPosition();
//        String elem = parser.getInputStack().peek();
//        parser.Advance();
//
//        Assert.assertSame(parser.getPosition(), pos + 1);
//        Assert.assertEquals(parser.getWorkingStack().peek().getFirstElement(), Arrays.stream(elem.split(" ")).toList().getFirst());
//        Assert.assertNotEquals(parser.getInputStack().peek(), elem);
//    }
//
//    @Test
//    public void TestAnotherTry() {
//        // head of working stack is non-terminal
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.Expand();
//        Pair<String, Integer> pair1 = parser.getWorkingStack().peek();
//        int index1 = pair1.getSecondElement().intValue() + 1;
//        parser.AnotherTry();
//        Pair<String, Integer> pair2 = parser.getWorkingStack().peek();
//
//        Assert.assertEquals(parser.getState(), "q");
//        Assert.assertEquals(pair1.getFirstElement(), pair2.getFirstElement());
//        Assert.assertSame(index1, pair2.getSecondElement());
//        String prod = Arrays.stream(gr.setOfProductions.get(pair2.getFirstElement()).getFirst().split("\\s+"))
//                .toList().get(pair2.getSecondElement());
//        Assert.assertEquals(prod, Arrays.stream(parser.getInputStack().peek().split(" ")).toList().get(index1));
//
//    }
//
//    @Test
//    public void TestMomentaryInsuccess() {
//        //head of input stack is a terminal and != with current symbol in input
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.MomentaryInsuccess();
//
//        Assert.assertEquals(parser.getState(), "b");
//    }
//
//    @Test
//    public void TestBack() {
//        // when head of working stack is terminal
//        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
//        parser.config();
//        parser.Expand();
//        parser.Advance();
//        int pos = parser.getPosition();
//        String elem = parser.getWorkingStack().peek().getFirstElement();
//        parser.Back();
//
//        Assert.assertSame(parser.getPosition(), pos - 1);
//        Assert.assertEquals(Arrays.stream(parser.getInputStack().peek().split(" ")).toList().getFirst(), elem);
//        Assert.assertNotEquals(parser.getWorkingStack().peek().getFirstElement(), elem);
//    }
//}
