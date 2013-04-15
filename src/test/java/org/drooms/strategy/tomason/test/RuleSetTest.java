package org.drooms.strategy.tomason.test;

import java.io.File;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.conf.DumpDirOption;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drooms.strategy.tomason.tools.RuleSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class RuleSetTest {
    protected KnowledgeBuilder kbuilder;
    protected ClassLoader loader;
    protected KnowledgeBase kbase;
    protected StatefulKnowledgeSession ksession;
    
    protected abstract RuleSet getTestedRules();

    @BeforeClass
    public static void setDebug() {
        // delete next line comment to enable rules debug output
        //System.setProperty("tomason.debug.output", "true");
    }
    
    @Before
    public void prepareKnowledge() {
        KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        conf.setOption(DumpDirOption.get(new File("target/drools-dump")));
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf);
        loader = getClass().getClassLoader();

        getTestedRules().addToKnowledgeBuilder(kbuilder, loader);

        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        ksession = kbase.newStatefulKnowledgeSession();
    }

    @Test
    public void testCompilability() {
        Assert.assertTrue("Problem building packages (see console", getTestedRules().addToKnowledgeBuilder(kbuilder, loader));
    }

    @After
    public void destroyKnowledge() {
        for (StatefulKnowledgeSession session : kbase.getStatefulKnowledgeSessions()) {
            session.dispose();
        }
    }
}
