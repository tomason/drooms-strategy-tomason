package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class DangerAvoidanceRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.danger");

        builder.newRule().name("Retract blocked ways")
        .attribute("salience", "20")
        .lhs()
        .pattern("PriorityMove").id("$move", false)
        .constraint("move != Move.STAY")
        .bind("$to", "target", false)
        .end()
        .pattern("Positioned").constraint("node == $to").end()
        .end()
        .rhs("retract($move);")
        .end();

        builder.newRule().name("Decrease priority of STAY move")
        .attribute("salience", "10")
        .lhs()
        .pattern("Status").constraint("stayCount > 0").bind("$stay", "stayCount", false).end()
        .pattern("PriorityMove").id("$move", false).constraint("move == Move.STAY").end()
        .end()
        .rhs("modify($move) { addPriority(-10000 * $stay); }")
        .end();

        builder.newRule().name("Retract STAY move")
        .attribute("salience", "10")
        .lhs()
        .pattern("PriorityMove").id("$move", false).constraint("move == Move.STAY").end()
        .pattern("GameProperty").constraint("name == GameProperty.Name.MAX_INACTIVE_TURNS").bind("$max", "value", false).end()
        .pattern("Status").constraint("stayCount == $max").end()
        .end()
        .rhs("retract($move);")
        .end();

        builder.newRule().name("Remove possible colisions to the left")
        .attribute("salience", "10")
        .lhs()
        .pattern("PriorityMove").id("$move", false)
        .constraint("move != Move.STAY")
        .bind("$x", "target.x", false)
        .bind("$y", "target.y", false)
        .end()
        .pattern("Enemy").constraint("x == ($x - 1)").constraint("y == $y").end()
        .end()
        .rhs("modify($move) { addPriority(-500000); }")
        //.rhs("retract($move);")
        .end();

        builder.newRule().name("Remove possible colisions to the right")
        .attribute("salience", "10")
        .lhs()
        .pattern("PriorityMove").id("$move", false)
        .constraint("move != Move.STAY")
        .bind("$x", "target.x", false)
        .bind("$y", "target.y", false)
        .end()
        .pattern("Enemy").constraint("x == ($x + 1)").constraint("y == $y").end()
        .end()
        .rhs("modify($move) { addPriority(-500000); }")
        //.rhs("retract($move);")
        .end();

        builder.newRule().name("Remove possible colisions to the up")
        .attribute("salience", "10")
        .lhs()
        .pattern("PriorityMove").id("$move", false)
        .constraint("move != Move.STAY")
        .bind("$x", "target.x", false)
        .bind("$y", "target.y", false)
        .end()
        .pattern("Enemy").constraint("x == $x)").constraint("y == ($y + 1)").end()
        .end()
        .rhs("modify($move) { addPriority(-500000); }")
        //.rhs("retract($move);")
        .end();

        builder.newRule().name("Remove possible colisions to the down")
        .attribute("salience", "10")
        .lhs()
        .pattern("PriorityMove").id("$move", false)
        .constraint("move != Move.STAY")
        .bind("$x", "target.x", false)
        .bind("$y", "target.y", false)
        .end()
        .pattern("Enemy").constraint("x == $x").constraint("y == ($y - 1)").end()
        .end()
        .rhs("modify($move) { addPriority(-500000); }")
        //.rhs("retract($move);")
        .end();

        return builder.getDescr();
    }

}
