package org.drooms.strategy.tomason.tools;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgeDescr;
import org.drools.io.ResourceFactory;
import org.drools.lang.DrlDumper;
import org.drools.lang.api.DescrFactory;
import org.drools.lang.api.PackageDescrBuilder;
import org.drools.lang.descr.PackageDescr;

public abstract class DescriptorRuleSet implements RuleSet {

    @Override
    public boolean addToKnowledgeBuilder(KnowledgeBuilder kbuilder, ClassLoader loader) {
        KnowledgeDescr descriptor = buildRules();

        if (Boolean.getBoolean("tomason.debug.output"))
            System.out.println(new DrlDumper().dump((PackageDescr)descriptor));

        kbuilder.add(ResourceFactory.newDescrResource(descriptor), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors());
            kbuilder.undo();
            return false;
        }

        return true;
    }

    protected abstract KnowledgeDescr buildRules();

    protected PackageDescrBuilder buildBasicPackage(String name) {
        PackageDescrBuilder builder = DescrFactory.newPackage().name(name);
        addCommonImports(builder);
        addCommonDeclares(builder);
        addCommonGlobals(builder);

        return builder;
    }

    protected PackageDescrBuilder addCommonImports(PackageDescrBuilder builder) {
        builder.newImport().target("org.slf4j.Logger").end();
        builder.newImport().target("java.util.Random").end();
        builder.newImport().target("java.util.ArrayList").end();
        
        builder.newImport().target("org.drooms.api.Move").end();
        builder.newImport().target("org.drooms.api.Node").end();

        builder.newImport().target("org.drooms.impl.logic.PathTracker").end();
        builder.newImport().target("org.drooms.impl.logic.facts.Positioned").end();
        builder.newImport().target("org.drooms.impl.logic.facts.CurrentTurn").end();
        builder.newImport().target("org.drooms.impl.logic.facts.GameProperty").end();
        builder.newImport().target("org.drooms.impl.logic.facts.CurrentPlayer").end();
        builder.newImport().target("org.drooms.impl.logic.events.PlayerMoveEvent").end();
        builder.newImport().target("org.drooms.impl.logic.events.PlayerDeathEvent").end();
        builder.newImport().target("org.drooms.impl.logic.events.CollectibleRemovalEvent").end();
        builder.newImport().target("org.drooms.impl.logic.events.CollectibleAdditionEvent").end();

        builder.newImport().target("org.drooms.strategy.tomason.components.Functions").end();
        builder.newImport().target("org.drooms.strategy.tomason.components.PriorityMove").end();
        builder.newImport().target("org.drooms.strategy.tomason.components.status.Enemy").end();
        builder.newImport().target("org.drooms.strategy.tomason.components.status.Status").end();
        builder.newImport().target("org.drooms.strategy.tomason.components.history.PastMove").end();

        builder.newImport().target("org.drooms.strategy.tomason.components.collectibles.PositionedCollectible").end();

        return builder;
    }

    protected PackageDescrBuilder addCommonDeclares(PackageDescrBuilder builder) {
        builder.newDeclare().entryPoint().entryPointId("rewardEvents").end();
        builder.newDeclare().entryPoint().entryPointId("gameEvents").end();
        builder.newDeclare().entryPoint().entryPointId("playerEvents").end();

        builder.newDeclare().type().name("PlayerMoveEvent").newAnnotation("role").value("event").end().end();
        builder.newDeclare().type().name("PlayerDeathEvent").newAnnotation("role").value("event").end().end();
        builder.newDeclare().type().name("CollectibleAdditionEvent").newAnnotation("role").value("event").end().end();
        builder.newDeclare().type().name("CollectibleRemovalEvent").newAnnotation("role").value("event").end().end();
        
        return builder;
    }

    protected PackageDescrBuilder addCommonGlobals(PackageDescrBuilder builder) {
        builder.newGlobal().type("Logger").identifier("logger").end();
        builder.newGlobal().type("PathTracker").identifier("tracker").end();

        return builder;
    }
}
