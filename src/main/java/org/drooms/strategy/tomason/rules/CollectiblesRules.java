package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class CollectiblesRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.collectibles");

        builder.newRule().name("Receive collectible")
        .attribute("salience", "100")
        .lhs()
        .pattern("CollectibleAdditionEvent").from().entryPoint("gameEvents")
        .bind("$col", "collectible", false)
        .bind("$node", "node", false)
        .end()
        .end()
        .rhs("insert(new PositionedCollectible($col, $node));")
        .end();

        builder.newRule().name("Delete collectible")
        .attribute("salience", "100")
        .lhs()
        .pattern("CollectibleRemovalEvent").bind("$pos", "node", false).from().entryPoint("gameEvents").end()
        .pattern("PositionedCollectible").id("$col", false).constraint("position == $pos").end()
        .end()
        .rhs("retract($col);")
        .end();

        builder.newRule().name("Delete collectible with 0 distance")
        .attribute("salience", "100")
        .lhs()
        .pattern("PositionedCollectible").id("$col", false).constraint("distance == 0").end()
        .end()
        .rhs("retract($col);")
        .end();

        builder.newRule().name("Recount distance from status")
        .attribute("salience", "90")
        .attribute("no-loop", "true")
        .lhs()
        .pattern("Status").bind("$start", "position", false).end()
        .pattern("PositionedCollectible").id("$col", false).bind("$end", "position", false).end()
        .end()
        .rhs("modify($col) { setPath(tracker.getPath($start, $end)); }")
        .end();

        return builder.getDescr();
    }

}
