package org.drooms.strategy.tomason.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.drools.runtime.Channel;
import org.drools.runtime.ObjectFilter;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;
import org.drooms.api.Move;
import org.drooms.api.Node;
import org.drooms.impl.logic.facts.CurrentTurn;
import org.drooms.strategy.tomason.components.PriorityMove;
import org.drooms.strategy.tomason.components.status.Status;
import org.drooms.strategy.tomason.test.RuleSetTest;
import org.drooms.strategy.tomason.tools.RuleSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriorityPathRulesTest extends RuleSetTest {
    private static final RuleSet RULES = new PriorityPathRules();

    private static final String RULE_MOVES = "Insert default moves into WM with default priority";
    private static final String RULE_PRIORITY = "Insert top priority";
    private static final String RULE_SELECT = "Choose a top priority move to execute";

    private static final Logger LOGGER = LoggerFactory.getLogger(PriorityPathRulesTest.class);

    @Override
    protected RuleSet getTestedRules() {
        return RULES;
    }

    @Before
    public void setLogger() {
        ksession.setGlobal("logger", LOGGER);
    }
    
    @Test
    public void testMovesCreation() {
        final int x = 13;
        final int y = 5;
        final Status status = new Status();
        status.setSnake(new LinkedList<>(Arrays.asList(Node.getNode(x, y))));

        ksession.insert(status);
        int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_MOVES));
        Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

        Collection<Object> objects = ksession.getObjects(new TypeObjectFilter(PriorityMove.class));

        Assert.assertEquals("Wrong number of PriorityMove objects", 5, objects.size());
        Set<Move> moves = new HashSet<>();
        for (Object o : objects) {
            PriorityMove move = (PriorityMove) o;
            moves.add(move.getMove());
            switch (move.getMove()) {
            case STAY:
                Assert.assertEquals("Wrong target coordinates", Node.getNode(x, y), move.getTarget());
                Assert.assertTrue("Wrong STAY move priority", move.getPriority() < 0);
                break;
            case LEFT:
                Assert.assertEquals("Wrong target coordinates", Node.getNode(x - 1, y), move.getTarget());
                Assert.assertEquals("Wrong LEFT move priority", 0, move.getPriority());
                break;
            case RIGHT:
                Assert.assertEquals("Wrong target coordinates", Node.getNode(x + 1, y), move.getTarget());
                Assert.assertEquals("Wrong RIGHT move priority", 0, move.getPriority());
                break;
            case UP:
                Assert.assertEquals("Wrong target coordinates", Node.getNode(x, y + 1), move.getTarget());
                Assert.assertEquals("Wrong UP move priority", 0, move.getPriority());
                break;
            case DOWN:
                Assert.assertEquals("Wrong target coordinates", Node.getNode(x, y - 1), move.getTarget());
                Assert.assertEquals("Wrong DOWN move priority", 0, move.getPriority());
                break;
            }
        }

        Assert.assertTrue("STAY move is missing", moves.contains(Move.STAY));
        Assert.assertTrue("LEFT move is missing", moves.contains(Move.LEFT));
        Assert.assertTrue("RIGHT move is missing", moves.contains(Move.RIGHT));
        Assert.assertTrue("UP move is missing", moves.contains(Move.UP));
        Assert.assertTrue("DOWN move is missing", moves.contains(Move.DOWN));
    }

    @Test
    public void testTopPriority() {
        ksession.insert(new PriorityMove(null, Move.STAY, 0));
        ksession.insert(new PriorityMove(null, Move.STAY, 0));
        ksession.insert(new PriorityMove(null, Move.STAY, 0));

        int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_PRIORITY));
        Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

        Collection<Object> numbers = ksession.getObjects(new TypeObjectFilter(Number.class));
        Assert.assertEquals("Wrong number of Number objects", 1, numbers.size());

        Number n = (Number) numbers.iterator().next();
        Assert.assertEquals("Wrong priority counted", 0, n.intValue());
    }

    @Test
    public void testTopPriority2() {
        ksession.insert(new PriorityMove(null, Move.STAY, 0));
        ksession.insert(new PriorityMove(null, Move.STAY, 1));
        ksession.insert(new PriorityMove(null, Move.STAY, 0));

        int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_PRIORITY));
        Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

        Collection<Object> numbers = ksession.getObjects(new TypeObjectFilter(Number.class));
        Assert.assertEquals("Wrong number of Number objects", 1, numbers.size());

        Number n = (Number) numbers.iterator().next();
        Assert.assertEquals("Wrong priority counted", 1, n.intValue());
    }

    @Test
    public void testTopPriority3() {
        ksession.insert(new PriorityMove(null, Move.STAY, 0));
        ksession.insert(new PriorityMove(null, Move.STAY, 1));
        ksession.insert(new PriorityMove(null, Move.STAY, -1));

        int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_PRIORITY));
        Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

        Collection<Object> numbers = ksession.getObjects(new TypeObjectFilter(Number.class));
        Assert.assertEquals("Wrong number of Number objects", 1,  numbers.size());

        Number n = (Number) numbers.iterator().next();
        Assert.assertEquals("Wrong priority counted", 1, n.intValue());
    }

    @Test
    public void testTopPriority4() {
        for (int i = 0; i < 10; i++) {
            ksession.insert(new PriorityMove(null, Move.STAY, i * 10));

            int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_PRIORITY));
            Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

            Collection<Object> numbers = ksession.getObjects(new TypeObjectFilter(Number.class));
            Assert.assertEquals("Wrong number of Number objects", 1, numbers.size());

            Number n = (Number) numbers.iterator().next();
            Assert.assertEquals("Wrong priority counted", i * 10, n.intValue());
            
        }
    }

    @Test
    public void testMovePick() {
        QueueChannel channel = new QueueChannel();
        ksession.registerChannel("decision", channel);

        ksession.insert(new PriorityMove(null, Move.LEFT, 10));
        ksession.insert(new PriorityMove(null, Move.RIGHT, 8));
        ksession.insert(new Long(10));
        ksession.insert(new CurrentTurn(1));

        int firedRules = ksession.fireAllRules(new RuleNameAgendaFilter(RULE_SELECT));
        Assert.assertEquals("Wrong number of rules fired", 1, firedRules);

        Assert.assertTrue("No decision was made", channel.hasObject());
        Move move = (Move)channel.getObject();
        Assert.assertEquals("Wrong move selected", Move.LEFT, move);
    }
    
    private static class RuleNameAgendaFilter implements AgendaFilter {
        private final String ruleName;

        public RuleNameAgendaFilter(String ruleName) {
            this.ruleName = ruleName;
        }

        @Override
        public boolean accept(Activation activation) {
            return activation.getRule().getName().equals(ruleName);
        }
    }

    private static class TypeObjectFilter implements ObjectFilter {
        private final Class<?> clazz;

        public TypeObjectFilter(Class<?> clazz) {
            this.clazz = clazz;
        }

        public boolean accept(Object object) {
            return clazz.isAssignableFrom(object.getClass());
        }
    }

    private static class QueueChannel implements Channel {
        private final Queue<Object> queue = new LinkedList<>();

        @Override
        public void send(Object object) {
            queue.add(object);
        }

        public boolean hasObject() {
            return !queue.isEmpty();
        }

        public Object getObject() {
            return queue.poll();
        }
    }
}
