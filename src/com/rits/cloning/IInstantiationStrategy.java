package com.rits.cloning;

public interface IInstantiationStrategy {
     <T> T newInstance(Class<T> c);
}
