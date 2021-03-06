/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.ide.common.assistant;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.drools.ide.common.assistant.option.AssistantOption;
import org.drools.ide.common.assistant.option.ReplaceAssistantOption;
import org.drools.ide.common.assistant.processor.AbstractRuleAssistantProcessor;
import org.drools.ide.common.assistant.processor.DRLRefactorProcessor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DRLAssistantTest {

    private AbstractRuleAssistantProcessor ruleAssistant;
    private String rule;

    @Before
    public void setUp() throws Exception {
        ruleAssistant = new DRLRefactorProcessor();
        rule = "package org.kie.assistant.test;\n\n";
        rule += "import org.kie.assistant.test.model.Company;\n";
        rule += "IMPORT org.kie.assistant.test.model.Employee;\n\n";
        rule += "import function org.kie.assistant.model.Class1.anotherFunction \n";
        rule += "import		function org.kie.assistant.model.Class1.mathFunction \n";
        rule += "global     org.kie.assistant.test.model.Class2    results \n";
        rule += "GLOBAL org.kie.assistant.test.model.Class3 current\n";
        rule += "expander help-expander.dsl\n";
        rule += "query \"all clients\"\n";
        rule += "	result : Clients()\n";
        rule += "end\n";
        rule += "query \"new query\"\n";
        rule += "	objects : Clients()\n";
        rule += "end\n";
        rule += "function String hello(String name) {\n";
        rule += "    return \"Hello \"+name+\"!\";\n";
        rule += "}\n";
        rule += "function String helloWithAge(String name, Integer age) {\n";
        rule += "    return \"Hello2 \"+name+\"! \" + age;\n";
        rule += "}\n";
        rule += "rule   \"My Test Rule\"\n";
        rule += "when\n";
        rule += "	$employee : Employee($company : company, $company1 : oldcompany, $age : age > 80, salary > 400)\n";
        rule += "	$result : Company(company==$company, retireAge <= $age)\n";
        rule += "then\n";
        rule += "	System.out.println(\"can retire\")\n";
        rule += "end\n";
        rule += "rule   \"My Second Rule\"\n";
        rule += "when\n";
        rule += "	Driver(licence = 1234, $name : name)\n";
        rule += "	$car : Car(company : $company, ownerLicense == licence, year == 2009)\n";
        rule += "then\n";
        rule += "	System.out.println(\"licence 1234 has a new car\")\n";
        rule += "end\n";
	}

    @Test
    public void testAssignSalaryFieldToVariable() throws Exception {
        List<AssistantOption> options = ruleAssistant.getRuleAssistant(rule, 780);
        assertEquals(options.size(), 1);
        ReplaceAssistantOption assistantOption = (ReplaceAssistantOption) options.get(0);
        assertEquals("\t$employee : Employee($company : company, $company1 : oldcompany, $age : age > 80, salary $ : > 400)", assistantOption.getContent());
    }

    @Test
    public void testDontAssignFieldInsideRHS() throws Exception {
        List<AssistantOption> options = ruleAssistant.getRuleAssistant(rule, 840);
        assertEquals(options.size(), 0);
    }

    @Test
    public void testAssignLicenseFromSecondRule() throws Exception {
        List<AssistantOption> options = ruleAssistant.getRuleAssistant(rule, 930);
        assertEquals(options.size(), 1);
        ReplaceAssistantOption assistantOption = (ReplaceAssistantOption) options.get(0);
        assertEquals("\tDriver($licence : licence = 1234, $name : name)", assistantOption.getContent());
    }

}
