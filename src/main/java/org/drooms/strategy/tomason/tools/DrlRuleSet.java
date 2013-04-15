package org.drooms.strategy.tomason.tools;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

public abstract class DrlRuleSet implements RuleSet {

    @Override
    public boolean addToKnowledgeBuilder(KnowledgeBuilder kbuilder, ClassLoader loader) {
        kbuilder.add(ResourceFactory.newClassPathResource(getPath(), loader), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors());
            kbuilder.undo();
            return false;
        }

        return true;
    }

    protected abstract String getPath();
}
