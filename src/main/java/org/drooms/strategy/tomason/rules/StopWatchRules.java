package org.drooms.strategy.tomason.rules;

import org.drools.definition.KnowledgeDescr;
import org.drools.lang.api.PackageDescrBuilder;
import org.drooms.strategy.tomason.tools.DescriptorRuleSet;

public class StopWatchRules extends DescriptorRuleSet {

    @Override
    protected KnowledgeDescr buildRules() {
        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.stopwatch");

        builder.newImport().target("org.drooms.strategy.tomason.rules.StopWatchRules.TimeHolder").end();
        builder.newImport().target("org.drooms.strategy.tomason.components.status.StopProcessing");

        builder.newRule().name("Start the clock")
        .attribute("salience", "Integer.MAX_VALUE")
        .lhs()
        .pattern("CurrentTurn").bind("$turn", "number", false).end()
        .end()
        .rhs("insert(new TimeHolder(System.currentTimeMillis(), $turn));")
        .end();

        builder.newRule().name("Print the time")
        .attribute("salience", "Integer.MAX_VALUE")
        .lhs()
        .pattern("PastMove").bind("$turn", "turn", false).end()
        .pattern("TimeHolder").id("$holder", false).constraint("turn == $turn").end()
        .end()
        .rhs("logger.info(\"Time for turn {}: {} ms\", $turn, $holder.getDelta(System.currentTimeMillis()));")
        .end();

        return builder.getDescr();
    }

    public static class TimeHolder {
        private long time;
        private int turn;

        public TimeHolder(long time, int turn) {
            this.time = time;
            this.turn = turn;
        }

        public long getTime() {
            return time;
        }

        public int getTurn() {
            return turn;
        }
        
        public long getDelta(long newTime) {
            return newTime - time;
        }
    }
}
