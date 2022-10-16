package com.cz.spring_cloud_alibaba.stream;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import java.util.*;
import java.util.stream.Stream;

public class MyBindingService extends BindingService {
    private final Map<String, List<Binding<?>>> consumerBindings = new HashMap<>();

    private BindingServiceProperties bindingServiceProperties;

    private CustomValidatorBean validator;

    public MyBindingService(BindingServiceProperties bindingServiceProperties, BinderFactory binderFactory, TaskScheduler taskScheduler) {
        super(bindingServiceProperties, binderFactory, taskScheduler);
        this. bindingServiceProperties= bindingServiceProperties;
        this.validator = new CustomValidatorBean();
        this.validator.afterPropertiesSet();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> Collection<Binding<T>> bindConsumer(T input, String inputName) {
        Collection<Binding<T>> bindings = new ArrayList<>();
        Class<?> inputClass = input.getClass();
        if (input instanceof Advised) {
            inputClass = Stream.of(((Advised) input).getProxiedInterfaces()).filter(c -> !c.getName().contains("org.springframework")).findFirst()
                    .orElse(inputClass);
        }
        Binder<T, ConsumerProperties, ?> binder = (Binder<T, ConsumerProperties, ?>) getBinder(
                inputName, inputClass);
        ConsumerProperties consumerProperties = this.bindingServiceProperties
                .getConsumerProperties(inputName);
        if (binder instanceof ExtendedPropertiesBinder) {
            Object extension = ((ExtendedPropertiesBinder) binder)
                    .getExtendedConsumerProperties(inputName);
            ExtendedConsumerProperties extendedConsumerProperties = new ExtendedConsumerProperties(
                    extension);
            BeanUtils.copyProperties(consumerProperties, extendedConsumerProperties);

            consumerProperties = extendedConsumerProperties;
        }

        validate(consumerProperties);

        String bindingTarget = this.bindingServiceProperties
                .getBindingDestination(inputName);

        if (consumerProperties.isMultiplex()) {
            bindings.add(doBindConsumer(input, inputName, binder, consumerProperties,
                    bindingTarget));
        }
        else {
            String[] bindingTargets = StringUtils
                    .commaDelimitedListToStringArray(bindingTarget);
            for (String target : bindingTargets) {
                if (!consumerProperties.isPartitioned() || consumerProperties.getInstanceIndexList().isEmpty()) {
                    Binding<T> binding = input instanceof PollableSource
                            ? doBindPollableConsumer(input, inputName, binder,
                            consumerProperties, target)
                            : doBindConsumer(input, inputName, binder, consumerProperties,
                            target);

                    bindings.add(binding);
                }
                else {
                    for (Integer index : consumerProperties.getInstanceIndexList()) {
                        if (index < 0) {
                            continue;
                        }

                        ConsumerProperties consumerPropertiesTemp = new ExtendedConsumerProperties<>(consumerProperties).getExtension();
                        BeanUtils.copyProperties(consumerProperties, consumerPropertiesTemp);
                        consumerPropertiesTemp.setInstanceIndex(index);

                        Binding<T> binding = input instanceof PollableSource
                                ? doBindPollableConsumer(input, inputName, binder,
                                consumerPropertiesTemp, target)
                                : doBindConsumer(input, inputName, binder, consumerPropertiesTemp,
                                target);

                        bindings.add(binding);
                    }
                }
            }
        }
        bindings = Collections.unmodifiableCollection(bindings);
        consumerBindings.put(inputName, new ArrayList<>(bindings));
        return bindings;
    }

    private void validate(Object properties) {
        DataBinder dataBinder = new DataBinder(properties);
        dataBinder.setValidator(this.validator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new IllegalStateException(dataBinder.getBindingResult().toString());
        }
    }
}
