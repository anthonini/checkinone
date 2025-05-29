package com.checkinone.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.checkinone.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.checkinone.thymeleaf.processor.MessageElementTagProcessor;

/**
 * @author Anthonini
 */
public class AppDialect extends AbstractProcessorDialect {

    public AppDialect() {
        super("CheckInOne", "ck", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processadores = new HashSet<>();
        processadores.add(new MessageElementTagProcessor(dialectPrefix));
        processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));

        return processadores;
    }

}
