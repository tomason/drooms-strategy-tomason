package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class PriorityPathRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.priority");

        builder.newRule()
                .name("Insert default moves into WM with default priority")
                .attribute("salience", "10000")
                .lhs()
                .pattern("Status")
                .bind("$x", "position.x", false)
                .bind("$y", "position.y", false)
                .end()
                .end()
                .rhs("insertLogical(new PriorityMove(Node.getNode($x - 1, $y), Move.LEFT, 0));"
                        + "insertLogical(new PriorityMove(Node.getNode($x + 1, $y), Move.RIGHT, 0));"
                        + "insertLogical(new PriorityMove(Node.getNode($x, $y + 1), Move.UP, 0));"
                        + "insertLogical(new PriorityMove(Node.getNode($x, $y - 1), Move.DOWN, 0));"
                        + "insertLogical(new PriorityMove(Node.getNode($x, $y), Move.STAY, -100));").end();

        builder.newRule()
                .name("Insert top priority")
                .attribute("salience", "-10")
                .lhs()
                .accumulate().multiFunction(true)
                .source()
                .pattern("PriorityMove")
                .bind("$priority", "priority", false).newAnnotation("watch").value("priority").end()
                .end()
                .end()
                .function("max", "$topPriority", "$priority")
                .end()
                .end()
                .rhs(//"logger.info(\"Found top priority {}\", $topPriority);" +
                        "insertLogical($topPriority);").end();

        builder.newRule()
                .name("Choose a top priority move to execute")
                .attribute("salience", "-100")
                .lhs()
                .pattern("Number").bind("$topPriority", "longValue", false).end()
                .pattern("ArrayList").id("$moves", false).bind("$size", "size", false).constraint("size > 0")
                .from().collect().pattern("PriorityMove").constraint("priority >= $topPriority").end().end()
                .end()
                .pattern("CurrentTurn").bind("$turn", "number", false).end()
                .end()
                .rhs(//"logger.info(\"Possible moves: {}\", $moves);" +
                        "Random random = new Random(System.nanoTime());" +
                        "Move move = ((PriorityMove)$moves.get(random.nextInt($size))).getMove();" +
                        //"logger.info(\"Chose move {}\", move);" +
                        "insert(new PastMove($turn, move));" +
                        "channels[\"decision\"].send(move);").end();

        return builder.getDescr();
    }

}
