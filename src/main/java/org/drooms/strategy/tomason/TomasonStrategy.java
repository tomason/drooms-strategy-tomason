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

        if (!new PriorityPathRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add basic set of rules");
        }
        if (!new PathRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add path rules");
        }
        if (!new DangerAvoidanceRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add danger avoidance rules");
        }
        if (!new StatusRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add status rules");
        }
        if (!new CollectiblesRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add collectibles rules");
        }

        if (!new CollisionDetectionRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add collision detection rules");
        }

        if (!new StopWatchRules().addToKnowledgeBuilder(kbuilder, cls)) {
            System.err.println("Unable to add stop watch rules");
        }

        return kbuilder;
    }

}
