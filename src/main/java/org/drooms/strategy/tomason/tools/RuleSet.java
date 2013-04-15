package org.drooms.strategy.tomason.tools;

import org.drools.builder.KnowledgeBuilder;

public interface RuleSet {

    boolean addToKnowledgeBuilder(KnowledgeBuilder kbuilder, ClassLoader loader);

}
