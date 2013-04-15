package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class StatusRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.status");

        builder.newImport().target("org.drools.runtime.rule.FactHandle").end();
        builder.newImport().target("org.drools.runtime.rule.WorkingMemoryEntryPoint").end();

        builder.newRule().name("Insert current position fact if it is not available")
        .attribute("salience", "Integer.MAX_VALUE")
        .lhs()
        .not().pattern("Status").end().end()
        .end()
        .rhs("insert(new Status());")
        .end();

        builder.newRule().name("Update current position")
        .attribute("salience", "Integer.MAX_VALUE")
        .attribute("no-loop", "true")
        .lhs()
        .pattern("CurrentPlayer").bind("$p", "player", false).end()
        .pattern("PlayerMoveEvent").from().entryPoint("playerEvents")
        .constraint("player == $p")
        .bind("$nodes", "nodes", false)
        .bind("$dir", "move", false)
        .end()
        .pattern("Status").id("$pos", false).end()
        .end()
        .rhs("$pos.setSnake($nodes);" +
                "$pos.setDirection($dir);" +
                "update($pos);")
        .end();

        builder.newRule().name("Insert enemies")
        .attribute("salience", "100")
        .lhs()
        .pattern("CurrentPlayer").bind("$p", "player", false).end()
        .pattern("PlayerMoveEvent").from().entryPoint("playerEvents")
        .constraint("player != $p")
        .bind("$player", "player", false)
        .bind("$worm", "nodes", false)
        .end()
        .not().pattern("Enemy").constraint("player == $player").end().end()
        .end()
        .rhs("insert(new Enemy($player, $worm));")
        .end();

        builder.newRule().name("Update enemies")
        .attribute("salience", "100")
        .lhs()
        .pattern("PlayerMoveEvent").id("$event", false).from().entryPoint("playerEvents")
        .bind("$worm", "nodes", false)
        .bind("$player", "player", false)
        .end()
        .pattern("Enemy").id("$e", false).constraint("player == $player").end()
        .end()
        .rhs("modify($e) { setWorm($worm); };" +
                "WorkingMemoryEntryPoint point = drools.getEntryPoint(\"playerEvents\");" +
                "FactHandle handle = point.getFactHandle($event);" +
                "point.retract(handle);")
        .end();

        builder.newRule().name("Remove dead enemies")
        .attribute("salience", "99")
        .lhs()
        .pattern("PlayerDeathEvent").bind("$player", "player", false).from().entryPoint("playerEvents").end()
        .pattern("Enemy").id("$e", false).constraint("player == $player").end()
        .end()
        .rhs("retract($e);")
        .end();

        return builder.getDescr();
    }

}
