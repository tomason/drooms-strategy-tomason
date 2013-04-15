package org.drooms.strategy.tomason;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drooms.api.Strategy;
import org.drooms.strategy.tomason.rules.CollectiblesRules;
import org.drooms.strategy.tomason.rules.CollisionDetectionRules;
import org.drooms.strategy.tomason.rules.DangerAvoidanceRules;
import org.drooms.strategy.tomason.rules.PathRules;
import org.drooms.strategy.tomason.rules.PriorityPathRules;
import org.drooms.strategy.tomason.rules.StatusRules;
import org.drooms.strategy.tomason.rules.StopWatchRules;

public class TomasonStrategy implements Strategy {

    public String getName() {
        return "Tomas Schlosser's strategy";
    }

    @Override
    public boolean enableAudit() {
        return false;
    }

    @Override
    public KnowledgeBuilder getKnowledgeBuilder(ClassLoader cls) {
        KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, cls);
        // conf.setOption(DumpDirOption.get(new File("target/droolsdump")));

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf);

        new PriorityPathRules().addToKnowledgeBuilder(kbuilder, cls);
        new PathRules().addToKnowledgeBuilder(kbuilder, cls);
        new DangerAvoidanceRules().addToKnowledgeBuilder(kbuilder, cls);
        new StatusRules().addToKnowledgeBuilder(kbuilder, cls);
        new CollectiblesRules().addToKnowledgeBuilder(kbuilder, cls);
        new CollisionDetectionRules().addToKnowledgeBuilder(kbuilder, cls);
        new StopWatchRules().addToKnowledgeBuilder(kbuilder, cls);

        return kbuilder;
    }

}
