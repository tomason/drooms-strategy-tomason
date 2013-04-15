package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class PathRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.path");

        builder.newRule().name("Add extra priority to straight moves")
        .attribute("salience", "5")
        .lhs()
        .pattern("CurrentTurn").bind("$turn", "number", false).end()
        .pattern("PastMove")
        .constraint("turn == ($turn - 1)")
        .constraint("move != Move.STAY")
        .bind("$past", "move", false)
        .end()
        .pattern("PriorityMove").id("$move", false).constraint("move == $past").end()
        .end()
        .rhs("modify($move) { addPriority(1000) }")
        .end();

        builder.newRule().name("Insert priority move for every collectible")
        .lhs()
        .pattern("Status").bind("$position", "position", false).end()
        .pattern("PositionedCollectible").id("$col", false).end()
        .end()
        .rhs("insertLogical(" +
                "new PriorityMove($position, $col.getFirstStep()," +
                "Functions.getPriority($col.getCollectible().getPoints(), $col.getDistance())" +
                "));")
        .end();

        return builder.getDescr();
    }

}
