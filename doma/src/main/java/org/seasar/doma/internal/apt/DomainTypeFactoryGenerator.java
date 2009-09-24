/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.internal.apt;

import static org.seasar.doma.internal.util.AssertionUtil.*;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

import org.seasar.doma.internal.apt.meta.DomainMeta;
import org.seasar.doma.internal.domain.DomainType;
import org.seasar.doma.internal.domain.DomainTypeFactory;
import org.seasar.doma.internal.util.PrimitiveWrapperUtil;

/**
 * 
 * @author taedium
 * 
 */
public class DomainTypeFactoryGenerator extends AbstractGenerator {

    protected final DomainMeta domainMeta;

    public DomainTypeFactoryGenerator(ProcessingEnvironment env,
            TypeElement domainElement, DomainMeta domainMeta)
            throws IOException {
        super(env, domainElement, null, null, Options.getDomainSuffix(env));
        assertNotNull(domainMeta);
        this.domainMeta = domainMeta;
    }

    public void generate() {
        printPackage();
        printClass();
    }

    protected void printPackage() {
        if (!packageName.isEmpty()) {
            iprint("package %1$s;%n", packageName);
            iprint("%n");
        }
    }

    protected void printClass() {
        printGenerated();
        iprint("public class %1$s implements %2$s<%3$s, %4$s> {%n", simpleName,
                DomainTypeFactory.class.getName(), domainMeta
                        .getValueTypeElement().getQualifiedName(), domainMeta
                        .getTypeElement().getQualifiedName());
        print("%n");
        indent();
        printMethods();
        printDomainTypeClass();
        unindent();
        iprint("}%n");
    }

    protected void printMethods() {
        iprint("@Override%n");
        iprint("public %1$s<%2$s, %3$s> createDomainType() {%n",
                DomainType.class.getName(), domainMeta.getValueTypeElement()
                        .getQualifiedName(), domainMeta.getTypeElement()
                        .getQualifiedName());
        iprint("    return new %1$sType();%n", domainMeta.getTypeElement()
                .getSimpleName());
        iprint("}%n");
    }

    protected void printDomainTypeClass() {
        iprint("public static class %1$sType implements %2$s<%3$s, %4$s> {%n",
                domainMeta.getTypeElement().getSimpleName(), DomainType.class
                        .getName(), domainMeta.getValueTypeElement()
                        .getQualifiedName(), domainMeta.getTypeElement()
                        .getQualifiedName());
        print("%n");
        indent();
        printTypeClassFields();
        printTypeClassMethods();
        unindent();
        iprint("}%n");
        print("%n");
    }

    protected void printTypeClassFields() {
        iprint("private final %1$s wrapper = new %1$s();%n", domainMeta
                .getWrapperTypeName());
        print("%n");
    }

    protected void printTypeClassMethods() {
        printTypeClassGetDomainMethod();
        printTypeClassGetDomainClassMethod();
        printTypeClassGetWrapperMethod();
    }

    protected void printTypeClassGetDomainMethod() {
        iprint("@Override%n");
        iprint("public %1$s getDomain() {%n", domainMeta.getTypeElement()
                .getQualifiedName());
        if (domainMeta.getValueType().getKind().isPrimitive()) {
            iprint("    return new %1$s(%2$s.toPrimitive(wrapper.get()));%n",
                    domainMeta.getTypeElement().getQualifiedName(),
                    PrimitiveWrapperUtil.class.getName());
        } else {
            iprint("    return new %1$s(wrapper.get());%n", domainMeta
                    .getTypeElement().getQualifiedName());
        }
        iprint("}%n");
        print("%n");
    }

    protected void printTypeClassGetDomainClassMethod() {
        iprint("@Override%n");
        iprint("public Class<%1$s> getDomainClass() {%n", domainMeta
                .getTypeElement().getQualifiedName());
        iprint("    return %1$s.class;%n", domainMeta.getTypeElement()
                .getQualifiedName());
        iprint("}%n");
        print("%n");
    }

    protected void printTypeClassGetWrapperMethod() {
        iprint("@Override%n");
        iprint("public %1$s getWrapper() {%n", domainMeta.getWrapperTypeName());
        iprint("    return wrapper;%n");
        iprint("}%n");
        print("%n");
    }

}