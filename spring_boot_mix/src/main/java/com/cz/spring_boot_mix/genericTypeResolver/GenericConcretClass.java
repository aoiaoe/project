package com.cz.spring_boot_mix.genericTypeResolver;

public class GenericConcretClass implements GenericAbstracClass<Resource> {

    @Override
    public Resource doSth(String name) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setAmount(1);
        return resource;
    }

}
