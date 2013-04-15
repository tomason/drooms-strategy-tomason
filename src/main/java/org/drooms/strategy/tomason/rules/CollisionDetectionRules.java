package org.drooms.strategy.tomason.rules;

import org.drooms.strategy.tomason.tools.DrlRuleSet;

public class CollisionDetectionRules
    //extends DescriptorRuleSet {
    extends DrlRuleSet {

//    @Override
//    protected KnowledgeDescr buildRules() {
//        PackageDescrBuilder builder = buildBasicPackage("org.drooms.strategy.tomason.collisions");
//        
//        builder.newQuery().name("isBlocked").parameter("Node", "start");
//        
//        builder.newRule().name("Detect small spaces");
//        
//        return builder.getDescr();
//    }
    
    @Override
    protected String getPath() {
        return "collision.drl";
    }
}
